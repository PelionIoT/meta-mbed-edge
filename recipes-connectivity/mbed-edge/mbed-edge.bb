DESCRIPTION="mbed-edge"

LICENSE="Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=1dece7821bf3fd70fe1309eaa37d52a2"
SCRIPT_DIR = "${WORKDIR}/git/lib/mbed-cloud-client/update-client-hub/modules/pal-linux/scripts"

# Patches for quilt goes to files directory
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = "git://git@github.com/ARMmbed/mbed-edge.git;protocol=ssh; \
           file://mbed-edge-core.sh \
           file://mbed-edge-pt-example.sh \
           file://edge-core \
           file://pt-example"

SRCREV = "0.5.0"

RDEPENDS_${PN} = " procps start-stop-daemon bash"

# Installed packages
PACKAGES = "${PN} ${PN}-dbg"
FILES_${PN} += "/opt \
                /opt/arm \
                /opt/arm/edge-core \
                /opt/arm/pt-example"

FILES_${PN}-dbg += "/opt/arm/.debug \
                    /usr/src/debug/mbed-edge"

S = "${WORKDIR}/git"

EXTRA_OECMAKE += "-DTARGET_DEVICE=yocto -DTARGET_TOOLCHAIN=yocto ${MBED_EDGE_CUSTOM_CMAKE_ARGUMENTS}"
inherit cmake

do_configure_prepend() {
    cd ${S}
    git submodule init
    git submodule update
    cd ${WORKDIR}/build
}

do_install() {
    install -d "${D}/opt/arm"
    install "${WORKDIR}/build/bin/edge-core" "${D}/opt/arm"
    install "${WORKDIR}/build/bin/pt-example" "${D}/opt/arm"

    install -d "${D}${sysconfdir}/init.d"
    install "${WORKDIR}/mbed-edge-core.sh" "${D}${sysconfdir}/init.d"
    install "${WORKDIR}/mbed-edge-pt-example.sh" "${D}${sysconfdir}/init.d"

    install -d "${D}${sysconfdir}/logrotate.d"
    install -m 644 "${WORKDIR}/edge-core" "${D}${sysconfdir}/logrotate.d"
    install -m 644 "${WORKDIR}/pt-example" "${D}${sysconfdir}/logrotate.d"

    install -m 755 "${SCRIPT_DIR}/arm_update_cmdline.sh"                  "${D}/opt/arm"
    install -m 755 "${SCRIPT_DIR}/yocto_rpi/arm_update_activate.sh"       "${D}/opt/arm"
    install -m 755 "${SCRIPT_DIR}/yocto_rpi/arm_update_active_details.sh" "${D}/opt/arm"

    # The init scripts need to be linked by hand because there are two of them, one for the edge-core and
    # another for pt-example

    install -d ${D}${sysconfdir}/rc0.d
    install -d ${D}${sysconfdir}/rc1.d
    install -d ${D}${sysconfdir}/rc2.d
    install -d ${D}${sysconfdir}/rc3.d
    install -d ${D}${sysconfdir}/rc4.d
    install -d ${D}${sysconfdir}/rc5.d
    install -d ${D}${sysconfdir}/rc6.d

    ln -sf ../init.d/mbed-edge-core.sh      ${D}${sysconfdir}/rc0.d/K31mbed-edge-core.sh
    ln -sf ../init.d/mbed-edge-core.sh      ${D}${sysconfdir}/rc1.d/K31mbed-edge-core.sh
    ln -sf ../init.d/mbed-edge-core.sh      ${D}${sysconfdir}/rc2.d/S31mbed-edge-core.sh
    ln -sf ../init.d/mbed-edge-core.sh      ${D}${sysconfdir}/rc3.d/S31mbed-edge-core.sh
    ln -sf ../init.d/mbed-edge-core.sh      ${D}${sysconfdir}/rc4.d/S31mbed-edge-core.sh
    ln -sf ../init.d/mbed-edge-core.sh      ${D}${sysconfdir}/rc5.d/S31mbed-edge-core.sh
    ln -sf ../init.d/mbed-edge-core.sh      ${D}${sysconfdir}/rc6.d/K31mbed-edge-core.sh

    ln -sf ../init.d/mbed-edge-pt-example.sh      ${D}${sysconfdir}/rc0.d/K98mbed-edge-pt-example.sh
    ln -sf ../init.d/mbed-edge-pt-example.sh      ${D}${sysconfdir}/rc1.d/K98mbed-edge-pt-example.sh
    ln -sf ../init.d/mbed-edge-pt-example.sh      ${D}${sysconfdir}/rc2.d/S98mbed-edge-pt-example.sh
    ln -sf ../init.d/mbed-edge-pt-example.sh      ${D}${sysconfdir}/rc3.d/S98mbed-edge-pt-example.sh
    ln -sf ../init.d/mbed-edge-pt-example.sh      ${D}${sysconfdir}/rc4.d/S98mbed-edge-pt-example.sh
    ln -sf ../init.d/mbed-edge-pt-example.sh      ${D}${sysconfdir}/rc5.d/S98mbed-edge-pt-example.sh
    ln -sf ../init.d/mbed-edge-pt-example.sh      ${D}${sysconfdir}/rc6.d/K98mbed-edge-pt-example.sh
}

