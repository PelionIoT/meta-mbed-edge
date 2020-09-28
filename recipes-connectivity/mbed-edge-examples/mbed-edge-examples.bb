DESCRIPTION="mbed-edge-examples"

LICENSE="Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=1dece7821bf3fd70fe1309eaa37d52a2"

# Patches for quilt goes to files directory
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRCREV = "${@ d.getVar('MBED_EXAMPLES_TAG', True) if d.getVar('MBED_EXAMPLES_TAG', False) else '0.12.0' }"
EXAMPLES_BRANCH = "${@ 'branch=' + d.getVar('MBED_EXAMPLES_BRANCH', True) + ';' if d.getVar('MBED_EXAMPLES_BRANCH', False) else 'branch=master' }"

SRC_URI = "git://git@github.com/ARMmbed/mbed-edge-examples.git;protocol=ssh;${EXAMPLES_BRANCH} \
           file://pt-example \
           file://blept-example \
           file://blept-devices.json \
           file://0001-disable-doxygen.patch \
           file://0002-fix-libevent-build-with-CMake-in-Yocto.patch \
           file://0003-fix-CMake-test-Build-Type-Release.patch \
           file://mept-ble.init \
           "

# Installed packages
PACKAGES = "${PN} ${PN}-dbg"
FILES_${PN} += "/usr \
                /usr/arm \
                /usr/arm/pt-example \
                /usr/arm/blept-example \
                /usr/arm/blept-devices.json"

FILES_${PN}-dbg += "/usr/arm/.debug \
                    /usr/src/debug/mbed-edge-examples"

S = "${WORKDIR}/git"

DEPENDS = " libcap mosquitto glib-2.0 mercurial-native"
RDEPENDS_${PN} = " procps bash bluez5 virtual/mbed-edge-core"

EXTRA_OECMAKE += " \
    -DTARGET_DEVICE=yocto \
    -DTARGET_TOOLCHAIN=yocto \
    -DCMAKE_BUILD_TYPE=Release \
    ${MBED_EDGE_CUSTOM_CMAKE_ARGUMENTS} "
inherit cmake

inherit update-rc.d
INITSCRIPT_NAME = "mept-ble"
INITSCRIPT_PARAMS = "defaults 86 15"

do_configure_prepend() {
    cd ${S}
    git submodule update --init --recursive
}

do_install() {
    install -d "${D}/usr/arm"
    install "${WORKDIR}/build/bin/pt-example" "${D}/usr/arm"
    install "${WORKDIR}/build/bin/blept-example" "${D}/usr/arm"
    install "${WORKDIR}/blept-devices.json" "${D}/usr/arm"
    install "${WORKDIR}/build/bin/mqttpt-example" "${D}/usr/arm"
    install "${WORKDIR}/git/mqttpt-example/mqttgw_sim/mqtt_ep.sh" "${D}/usr/arm"
    install "${WORKDIR}/git/mqttpt-example/mqttgw_sim/mqtt_gw.sh" "${D}/usr/arm"

    install -d "${D}${sysconfdir}/logrotate.d"
    install -m 644 "${WORKDIR}/pt-example" "${D}${sysconfdir}/logrotate.d"
    install -m 644 "${WORKDIR}/blept-example" "${D}${sysconfdir}/logrotate.d"

    install -d "${D}${sysconfdir}/init.d/"
    install -m 755 "${WORKDIR}/mept-ble.init" "${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}"
}
