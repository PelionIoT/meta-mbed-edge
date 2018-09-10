DESCRIPTION="mbed-edge-examples"

LICENSE="Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=1dece7821bf3fd70fe1309eaa37d52a2"

# Patches for quilt goes to files directory
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = "git://git@github.com/ARMmbed/mbed-edge-examples.git;protocol=ssh; \
           file://mbed-edge-pt-example.sh \
           file://pt-example"
SRCREV = "0.6.0"

# Installed packages
PACKAGES = "${PN} ${PN}-dbg"
FILES_${PN} += "/opt \
                /opt/arm \
                /opt/arm/pt-example"

FILES_${PN}-dbg += "/opt/arm/.debug \
                    /usr/src/debug/mbed-edge-examples"

S = "${WORKDIR}/git"

DEPENDS = " libcap mosquitto"
RDEPENDS_${PN} = " procps start-stop-daemon bash"

EXTRA_OECMAKE += "-DTARGET_DEVICE=yocto -DTARGET_TOOLCHAIN=yocto ${MBED_EDGE_CUSTOM_CMAKE_ARGUMENTS}"
inherit cmake

do_configure_prepend() {
    cd ${S}
    git submodule update --init --recursive
    cd ${WORKDIR}/build
}

do_install() {
    install -d "${D}/opt/arm"
    install "${WORKDIR}/build/bin/pt-example" "${D}/opt/arm"
    install "${WORKDIR}/build/bin/mqttpt-example" "${D}/opt/arm"
    install "${WORKDIR}/git/mqttpt-example/mqttgw_sim/mqtt_ep.sh" "${D}/opt/arm"
    install "${WORKDIR}/git/mqttpt-example/mqttgw_sim/mqtt_gw.sh" "${D}/opt/arm"

    install -d "${D}${sysconfdir}/init.d"
    install "${WORKDIR}/mbed-edge-pt-example.sh" "${D}${sysconfdir}/init.d"

    install -d "${D}${sysconfdir}/logrotate.d"
    install -m 644 "${WORKDIR}/pt-example" "${D}${sysconfdir}/logrotate.d"

    install -d ${D}${sysconfdir}/rc0.d
    install -d ${D}${sysconfdir}/rc1.d
    install -d ${D}${sysconfdir}/rc2.d
    install -d ${D}${sysconfdir}/rc3.d
    install -d ${D}${sysconfdir}/rc4.d
    install -d ${D}${sysconfdir}/rc5.d
    install -d ${D}${sysconfdir}/rc6.d

    ln -sf ../init.d/mbed-edge-pt-example.sh      ${D}${sysconfdir}/rc0.d/K98mbed-edge-pt-example.sh
    ln -sf ../init.d/mbed-edge-pt-example.sh      ${D}${sysconfdir}/rc1.d/K98mbed-edge-pt-example.sh
    ln -sf ../init.d/mbed-edge-pt-example.sh      ${D}${sysconfdir}/rc2.d/S98mbed-edge-pt-example.sh
    ln -sf ../init.d/mbed-edge-pt-example.sh      ${D}${sysconfdir}/rc3.d/S98mbed-edge-pt-example.sh
    ln -sf ../init.d/mbed-edge-pt-example.sh      ${D}${sysconfdir}/rc4.d/S98mbed-edge-pt-example.sh
    ln -sf ../init.d/mbed-edge-pt-example.sh      ${D}${sysconfdir}/rc5.d/S98mbed-edge-pt-example.sh
    ln -sf ../init.d/mbed-edge-pt-example.sh      ${D}${sysconfdir}/rc6.d/K98mbed-edge-pt-example.sh
}
