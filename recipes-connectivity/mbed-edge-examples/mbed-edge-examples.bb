DESCRIPTION="mbed-edge-examples"

LICENSE="Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=1dece7821bf3fd70fe1309eaa37d52a2"

# Patches for quilt goes to files directory
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = "git://git@github.com/ARMmbed/mbed-edge-examples.git;protocol=ssh; \
           file://pt-example \
           file://blept-example \
           file://blept-devices.json"
SRCREV = "0.8.0"

# Installed packages
PACKAGES = "${PN} ${PN}-dbg"
FILES_${PN} += "/opt \
                /opt/arm \
                /opt/arm/pt-example \
                /opt/arm/blept-example \
                /opt/arm/blept-devices.json"

FILES_${PN}-dbg += "/opt/arm/.debug \
                    /usr/src/debug/mbed-edge-examples"

S = "${WORKDIR}/git"

DEPENDS = " libcap mosquitto glib-2.0"
RDEPENDS_${PN} = " procps start-stop-daemon bash bluez5 virtual/mbed-edge"

EXTRA_OECMAKE += " -DTARGET_TOOLCHAIN=yocto ${MBED_EDGE_CUSTOM_CMAKE_ARGUMENTS} "
inherit cmake

do_configure_prepend() {
    cd ${S}
    git submodule update --init --recursive
    cd ${WORKDIR}/build
}

do_install() {
    install -d "${D}/opt/arm"
    install "${WORKDIR}/build/bin/pt-example" "${D}/opt/arm"
    install "${WORKDIR}/build/bin/blept-example" "${D}/opt/arm"
    install "${WORKDIR}/blept-devices.json" "${D}/opt/arm"
    install "${WORKDIR}/build/bin/mqttpt-example" "${D}/opt/arm"
    install "${WORKDIR}/git/mqttpt-example/mqttgw_sim/mqtt_ep.sh" "${D}/opt/arm"
    install "${WORKDIR}/git/mqttpt-example/mqttgw_sim/mqtt_gw.sh" "${D}/opt/arm"

    install -d "${D}${sysconfdir}/logrotate.d"
    install -m 644 "${WORKDIR}/pt-example" "${D}${sysconfdir}/logrotate.d"
    install -m 644 "${WORKDIR}/blept-example" "${D}${sysconfdir}/logrotate.d"
}
