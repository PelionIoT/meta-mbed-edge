DESCRIPTION="mbed-edge-examples"

LICENSE="Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=1dece7821bf3fd70fe1309eaa37d52a2"

# Patches for quilt goes to files directory
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = "gitsm://git@github.com/ARMmbed/mbed-edge-examples.git;protocol=ssh; \
           file://pt-example \
           file://blept-example \
           file://pt-example.service \
           file://pt-example-cfg.service \
           file://pt-example-cfg.sh \
           file://blept-example.service \
           file://blept-example-cfg.service \
           file://blept-example-cfg.sh \
           file://blept-devices.json"
SRCREV = "0.11.0"

# Installed packages
PACKAGES = "${PN} ${PN}-dbg"
FILES_${PN} += "/opt \
                /opt/arm \
                /opt/arm/pt-example \
                /opt/arm/blept-example"

FILES_${PN}-dbg += "/opt/arm/.debug \
                    /usr/src/debug/mbed-edge-examples"

S = "${WORKDIR}/git"

DEPENDS = " libcap mosquitto glib-2.0"
RDEPENDS_${PN} = " procps start-stop-daemon bash bluez5"

EXTRA_OECMAKE += " -DTARGET_TOOLCHAIN=yocto ${MBED_EDGE_CUSTOM_CMAKE_ARGUMENTS} "
inherit cmake systemd

do_install() {
    install -d "${D}/opt/arm"
    install "${WORKDIR}/build/bin/pt-example" "${D}/opt/arm"
    install "${WORKDIR}/build/bin/mqttpt-example" "${D}/opt/arm"
    install "${WORKDIR}/git/mqttpt-example/mqttgw_sim/mqtt_ep.sh" "${D}/opt/arm"
    install "${WORKDIR}/git/mqttpt-example/mqttgw_sim/mqtt_gw.sh" "${D}/opt/arm"
    install "${WORKDIR}/build/bin/blept-example" "${D}/opt/arm"
    install "${WORKDIR}/blept-devices.json" "${D}/opt/arm"

    install -d "${D}${sysconfdir}/logrotate.d"
    install -m 644 "${WORKDIR}/pt-example" "${D}${sysconfdir}/logrotate.d"
    install -m 644 "${WORKDIR}/blept-example" "${D}${sysconfdir}/logrotate.d"

    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/pt-example.service ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/blept-example.service ${D}${systemd_unitdir}/system/

    install "${WORKDIR}/pt-example-cfg.sh" "${D}/opt/arm"
    install "${WORKDIR}/blept-example-cfg.sh" "${D}/opt/arm"

    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/pt-example-cfg.service ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/blept-example-cfg.service ${D}${systemd_unitdir}/system/
}

SYSTEMD_SERVICE_${PN} = "pt-example.service pt-example-cfg.service blept-example.service blept-example-cfg.service"

