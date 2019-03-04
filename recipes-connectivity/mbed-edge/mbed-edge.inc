DESCRIPTION="mbed-edge"

LICENSE="Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=1dece7821bf3fd70fe1309eaa37d52a2"
SCRIPT_DIR = "${WORKDIR}/git/lib/mbed-cloud-client/update-client-hub/modules/pal-linux/scripts"

# Patches for quilt goes to files directory
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = "git://git@github.com/ARMmbed/mbed-edge.git;protocol=ssh; \
           file://edge-core \
           file://toolchain.cmake "

SRCREV = "0.8.0"

RDEPENDS_${PN} = " procps start-stop-daemon bash tar bzip2"

# Installed packages
PACKAGES = "${PN} ${PN}-dbg"
FILES_${PN} += "/opt \
                /opt/arm \
                /opt/arm/edge-core"

FILES_${PN}-dbg += "/opt/arm/.debug \
                    /usr/src/debug/mbed-edge"

S = "${WORKDIR}/git"

EXTRA_OECMAKE += "-DTARGET_CONFIG_ROOT=${WORKDIR} ${MBED_EDGE_CUSTOM_CMAKE_ARGUMENTS}"
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

    install -d "${D}${sysconfdir}/logrotate.d"
    install -m 644 "${WORKDIR}/edge-core" "${D}${sysconfdir}/logrotate.d"
}

