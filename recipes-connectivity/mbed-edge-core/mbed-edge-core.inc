DESCRIPTION="mbed-edge-core"

LICENSE="Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=1dece7821bf3fd70fe1309eaa37d52a2"

# Patches for quilt goes to files directory
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRCREV = "0.18.0"

RM_WORK_EXCLUDE += "${PN}"

SRC_URI = "gitsm://github.com/PelionIoT/mbed-edge.git \
           file://toolchain.cmake \
           file://mbed_cloud_client_user_config.h \
           file://0001-disable-Doxygen.patch \
           file://0001-parsec-se-driver-should-be-compiled-separately-in-Yo.patch \
           file://0002-fix-libevent-build-with-CMake-in-Yocto.patch \
          "

SRC_URI += "\
    ${@bb.utils.contains('MBED_EDGE_CORE_CONFIG_DEVELOPER_MODE','ON','file://mbed_cloud_dev_credentials.c','',d)} \
    ${@bb.utils.contains('MBED_EDGE_CORE_CONFIG_DEVELOPER_MODE','ON','file://update_default_resources.c','',d)} \
    ${@bb.utils.contains('MBED_EDGE_CORE_CONFIG_BYOC_MODE','OFF','file://edge-core.service','',d)} \
    ${@bb.utils.contains('MBED_EDGE_CORE_CONFIG_BYOC_MODE','ON','file://edge-core-byoc.service','',d)} \
    ${@bb.utils.contains('MBED_EDGE_CORE_CONFIG_BYOC_MODE','ON','file://launch-byoc-edge-core.sh','',d)} \
"

DEPENDS = " libcap mosquitto mercurial-native curl python3-native python3-pip-native python3 python3-setuptools-native python3-setuptools-scm"
DEPENDS += "${@ 'parsec-se-driver' if d.getVar('MBED_EDGE_CORE_CONFIG_PARSEC_TPM_SE_SUPPORT') == 'ON' else ' '}"

RDEPENDS_${PN} = " procps bash tar bzip2 rng-tools glibc-utils"
RDEPENDS_${PN} += "${@ 'edge-tool' if d.getVar('MBED_EDGE_CORE_CONFIG_BYOC_MODE') == 'ON' else ' '}"

# Installed packages
PACKAGES = "${PN} ${PN}-dbg"
FILES_${PN} += "/wigwag \
                /wigwag/mbed \
                /wigwag/mbed/edge-core \
                /userdata"

FILES_${PN}-dbg += "/wigwag/mbed/debug \
                    /usr/src/debug/mbed-edge"

wbindir= "/wigwag/system/bin"

S = "${WORKDIR}/git"

inherit cmake update-rc.d systemd python3native

MBED_EDGE_CMAKE_BUILD_TYPE ?= "Debug"

lcl_maybe_fortify = '-D_FORTIFY_SOURCE=0'

EXTRA_OECMAKE += " \
    -DCMAKE_BUILD_TYPE=${MBED_EDGE_CMAKE_BUILD_TYPE} \
    -DTARGET_TOOLCHAIN=yocto \
    -DMBED_CONF_MBED_TRACE_ENABLE=1 \
    -DTARGET_CONFIG_ROOT=${WORKDIR} \
    -DCLOUD_CLIENT_CONFIG=${WORKDIR}/mbed_cloud_client_user_config.h \
    -DTRACE_LEVEL=${MBED_EDGE_CORE_CONFIG_TRACE_LEVEL} \
    -DFIRMWARE_UPDATE=${MBED_EDGE_CORE_CONFIG_FIRMWARE_UPDATE} \
    -DMBED_CLOUD_CLIENT_CURL_DYNAMIC_LINK=${MBED_EDGE_CORE_CONFIG_CURL_DYNAMIC_LINK} \
    -DFOTA_ENABLE=${MBED_EDGE_CORE_CONFIG_FOTA_ENABLE} \
    -DFOTA_TRACE=${MBED_EDGE_CORE_CONFIG_FOTA_TRACE} \
    -DDEVELOPER_MODE=${MBED_EDGE_CORE_CONFIG_DEVELOPER_MODE} \
    -DFACTORY_MODE=${MBED_EDGE_CORE_CONFIG_FACTORY_MODE} \
    -DBYOC_MODE=${MBED_EDGE_CORE_CONFIG_BYOC_MODE} \
    -DPARSEC_TPM_SE_SUPPORT=${MBED_EDGE_CORE_CONFIG_PARSEC_TPM_SE_SUPPORT} \
    ${MBED_EDGE_CUSTOM_CMAKE_ARGUMENTS} \
    "

INITSCRIPT_NAME = "mbed-edge-core"
INITSCRIPT_PARAMS = "defaults 85 15"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "edge-core.service"

do_configure_prepend() {
    cd ${S}

    [ -f "${WORKDIR}/mbed_cloud_dev_credentials.c" ] && {
        mv "${WORKDIR}/mbed_cloud_dev_credentials.c" config/
    }
    [ -f "${WORKDIR}/update_default_resources.c" ] && {
        mv "${WORKDIR}/update_default_resources.c" config/
    }

    export HTTP_PROXY=${HTTP_PROXY}
    export HTTPS_PROXY=${HTTPS_PROXY}

}

do_compile_prepend() {

    if [ "${MBED_EDGE_CORE_CONFIG_PARSEC_TPM_SE_SUPPORT}" = "ON" ]; then
        if [ "${MBED_EDGE_CMAKE_BUILD_TYPE}" = "Debug" ]; then
            mkdir -p ${S}/lib/pal-platform/Middleware/parsec_se_driver/parsec_se_driver/target/debug
            cp ${PKG_CONFIG_SYSROOT_DIR}/usr/lib/libparsec_se_driver.a ${S}/lib/pal-platform/Middleware/parsec_se_driver/parsec_se_driver/target/debug/
        else
            mkdir -p ${S}/lib/pal-platform/Middleware/parsec_se_driver/parsec_se_driver/target/release
            cp ${PKG_CONFIG_SYSROOT_DIR}/usr/lib/libparsec_se_driver.a ${S}/lib/pal-platform/Middleware/parsec_se_driver/parsec_se_driver/target/release/
        fi
    fi

}

do_install() {

    install -d "${D}${systemd_system_unitdir}"

    if [ "${MBED_EDGE_CORE_CONFIG_BYOC_MODE}" = "ON" ]; then
        install -d "${D}${wbindir}"
        install -m 755 "${WORKDIR}/edge-core-byoc.service" "${D}${systemd_system_unitdir}/edge-core.service"
        install -m 755 "${WORKDIR}/launch-byoc-edge-core.sh" "${D}${wbindir}/"
    else
        install -m 755 "${WORKDIR}/edge-core.service" "${D}${systemd_system_unitdir}/edge-core.service"
    fi

    install -d "${D}/wigwag/mbed"
    install "${WORKDIR}/build/bin/edge-core" "${D}/wigwag/mbed"

    install -d "${D}/etc/tmpfiles.d"
    echo "d /var/rootdirs/userdata 0755 root root -" >> "${D}/etc/tmpfiles.d/userdata-tmpfiles.conf"
    ln -sf "/var/rootdirs/userdata" "${D}/userdata"
}
