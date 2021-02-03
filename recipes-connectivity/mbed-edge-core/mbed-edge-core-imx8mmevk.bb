COMPATIBLE_MACHINE = "imx8mmevk"

MBED_EDGE_CORE_CONFIG_TRACE_LEVEL ?= "INFO"
MBED_EDGE_CORE_CONFIG_FIRMWARE_UPDATE ?= "ON"
MBED_EDGE_CORE_CONFIG_FOTA_ENABLE ?= "ON"
MBED_EDGE_CORE_CONFIG_FOTA_TRACE ?= "ON"
MBED_EDGE_CORE_CONFIG_CURL_DYNAMIC_LINK ?= "ON"
MBED_EDGE_CORE_CONFIG_DEVELOPER_MODE ?= "ON"
MBED_EDGE_CORE_CONFIG_FACTORY_MODE ?= "OFF"
MBED_EDGE_CORE_CONFIG_BYOC_MODE ?= "OFF"

require mbed-edge-core.inc

PROVIDES += " virtual/mbed-edge-core virtual/mbed-edge-core-dbg "
RPROVIDES_${PN} += " virtual/mbed-edge-core virtual/mbed-edge-core-dbg "

FILESEXTRAPATHS_prepend := "${THISDIR}/files/mbed-imx8mmevk:"
SRC_URI += "file://target.cmake \
            file://sotp_fs_imx8mmevk_yocto.h \
            file://deploy_ostree_delta_update.sh \
            file://0006-fota-callback.patch \
            file://pal_plat_imx8.c"

SCRIPT_DIR = "${WORKDIR}/git/lib/mbed-cloud-client/update-client-hub/modules/pal-linux/scripts"

do_configure_prepend() {
    mkdir -p ${S}/lib/mbed-cloud-client/mbed-client-pal/Source/Port/Reference-Impl/OS_Specific/Linux/Board_Specific/TARGET_imx8
    cp ${WORKDIR}/pal_plat_imx8.c ${S}/lib/mbed-cloud-client/mbed-client-pal/Source/Port/Reference-Impl/OS_Specific/Linux/Board_Specific/TARGET_imx8/

}


do_install_append() {
    install -m 755 "${SCRIPT_DIR}/arm_update_cmdline.sh"                  "${D}/wigwag/mbed"
    install -m 755 "${SCRIPT_DIR}/yocto_generic/arm_update_activate.sh"       "${D}/wigwag/mbed"
    install -m 755 "${SCRIPT_DIR}/yocto_generic/arm_update_active_details.sh" "${D}/wigwag/mbed"
    install -m 755 "${WORKDIR}/deploy_ostree_delta_update.sh" "${D}/wigwag/mbed"
}
