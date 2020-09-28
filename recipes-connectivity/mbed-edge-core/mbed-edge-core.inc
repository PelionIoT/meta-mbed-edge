DESCRIPTION="mbed-edge-core"

LICENSE="Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=1dece7821bf3fd70fe1309eaa37d52a2"

MBED_EDGE_CORE_CONFIG_TRACE_LEVEL ?= "DEBUG"
MBED_EDGE_CORE_CONFIG_FIRMWARE_UPDATE ?= "OFF"
MBED_EDGE_CORE_CONFIG_DEVELOPER_MODE ?= "ON"
MBED_EDGE_CORE_CONFIG_FACTORY_MODE ?= "OFF"
MBED_EDGE_CORE_CONFIG_BYOC_MODE ?= "OFF"

# Patches for quilt goes to files directory
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRCREV = "${@ d.getVar('MBED_EDGE_TAG', True) if d.getVar('MBED_EDGE_TAG', False) else '${AUTOREV}' }"
EDGE_BRANCH = "${@ 'branch=' + d.getVar('MBED_EDGE_BRANCH', True) + ';' if d.getVar('MBED_EDGE_BRANCH', False) else 'branch=master' }"

RM_WORK_EXCLUDE += "${PN}"

SRC_URI = "git://git@github.com/ARMmbed/mbed-edge.git;protocol=ssh; \
           file://edge-core.init \
           file://edge-core.service \
           file://edge-core.logrotate \
           file://toolchain.cmake \
           file://0001-disable-Doxygen.patch \
           file://0002-fix-libevent-build-with-CMake-in-Yocto.patch"
SRC_URI += "\
    ${@bb.utils.contains('MBED_EDGE_CORE_CONFIG_DEVELOPER_MODE','ON','file://mbed_cloud_dev_credentials.c','',d)} \
    ${@bb.utils.contains('MBED_EDGE_CORE_CONFIG_FIRMWARE_UPDATE','ON','file://update_default_resources.c','',d)} \
"

DEPENDS = " libcap mosquitto mercurial-native"
RDEPENDS_${PN} = " procps bash tar bzip2 rng-tools"

# Installed packages
PACKAGES = "${PN} ${PN}-dbg"
FILES_${PN} += "/usr \
                /usr/arm \
                /usr/arm/edge-core \
                /mnt \
                /mnt/config"

FILES_${PN}-dbg += "/usr/arm/debug \
                    /usr/src/debug/mbed-edge"

S = "${WORKDIR}/git"

inherit cmake update-rc.d systemd

EXTRA_OECMAKE += " \
    -DCMAKE_BUILD_TYPE=Debug \
    -DTARGET_TOOLCHAIN=yocto \
    -DMBED_CONF_MBED_TRACE_ENABLE=1 \
    -DTARGET_CONFIG_ROOT=${WORKDIR} \
    -DTRACE_LEVEL=${MBED_EDGE_CORE_CONFIG_TRACE_LEVEL} \
    -DFIRMWARE_UPDATE=${MBED_EDGE_CORE_CONFIG_FIRMWARE_UPDATE} \
    -DDEVELOPER_MODE=${MBED_EDGE_CORE_CONFIG_DEVELOPER_MODE} \
    -DFACTORY_MODE=${MBED_EDGE_CORE_CONFIG_FACTORY_MODE} \
    -DBYOC_MODE=${MBED_EDGE_CORE_CONFIG_BYOC_MODE} \
    ${MBED_EDGE_CUSTOM_CMAKE_ARGUMENTS} \
    "

INITSCRIPT_NAME = "mbed-edge-core"
INITSCRIPT_PARAMS = "defaults 85 15"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "edge-core.service"

do_configure_prepend() {
    cd ${S}
    git submodule update --init --recursive
    [ -f "${WORKDIR}/mbed_cloud_dev_credentials.c" ] && {
        mv "${WORKDIR}/mbed_cloud_dev_credentials.c" config/
    }
    [ -f "${WORKDIR}/update_default_resources.c" ] && {
        mv "${WORKDIR}/update_default_resources.c" config/
    }
    #dwqdwqd
    #mkdir -p ${WORKDIR}/build
    #cd ${WORKDIR}/build
}

do_install() {
    install -d "${D}/etc/init.d/"
    install -m 755 "${WORKDIR}/edge-core.init" "${D}/etc/init.d/${INITSCRIPT_NAME}"

    install -d "${D}${systemd_system_unitdir}"
    install -m 755 "${WORKDIR}/edge-core.service" "${D}${systemd_system_unitdir}/edge-core.service"

    install -d "${D}/usr/arm/"
    install "${WORKDIR}/build/bin/edge-core" "${D}/usr/arm/"

    install -d "${D}${sysconfdir}/logrotate.d/"
    install -m 644 "${WORKDIR}/edge-core.logrotate" "${D}${sysconfdir}/logrotate.d"

    install -d "${D}/mnt/config"
}

