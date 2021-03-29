#COMPATIBLE_MACHINE = "raspberrypi3"
COMPATIBLE_MACHINE = "^rpi$"

MBED_EDGE_CORE_CONFIG_TRACE_LEVEL ?= "WARN"
MBED_EDGE_CORE_CONFIG_FIRMWARE_UPDATE ?= "ON"
MBED_EDGE_CORE_CONFIG_FOTA_ENABLE ?= "ON"
MBED_EDGE_CORE_CONFIG_FOTA_TRACE ?= "ON"
MBED_EDGE_CORE_CONFIG_CURL_DYNAMIC_LINK ?= "ON"
MBED_EDGE_CORE_CONFIG_DEVELOPER_MODE ?= "ON"
MBED_EDGE_CORE_CONFIG_FACTORY_MODE ?= "OFF"
MBED_EDGE_CORE_CONFIG_BYOC_MODE ?= "OFF"
MBED_EDGE_CORE_CONFIG_PARSEC_TPM_SE_SUPPORT ?= "OFF"

require mbed-edge-core.inc

PROVIDES += " virtual/mbed-edge-core virtual/mbed-edge-core-dbg "
RPROVIDES_${PN} += " virtual/mbed-edge-core virtual/mbed-edge-core-dbg "

FILESEXTRAPATHS_prepend := "${THISDIR}/files/rpi3:"
SRC_URI += "file://target.cmake \
            file://sotp_fs_rpi3_yocto.h \
            file://deploy_ostree_delta_update.sh \
            file://0006-fota-callback.patch \
            file://0001-fix_psa_storage_location.patch \
            file://pal_plat_rpi3.c \
            file://0008-ordered-reboot.patch "

SCRIPT_DIR = "${WORKDIR}/git/lib/mbed-cloud-client/update-client-hub/modules/pal-linux/scripts"

do_configure_prepend() {
    mkdir -p ${S}/lib/mbed-cloud-client/mbed-client-pal/Source/Port/Reference-Impl/OS_Specific/Linux/Board_Specific/TARGET_rpi3
    cp ${WORKDIR}/pal_plat_rpi3.c ${S}/lib/mbed-cloud-client/mbed-client-pal/Source/Port/Reference-Impl/OS_Specific/Linux/Board_Specific/TARGET_rpi3/

}


do_install_append() {
    install -m 755 "${SCRIPT_DIR}/arm_update_cmdline.sh"                  "${D}/wigwag/mbed"
    install -m 755 "${SCRIPT_DIR}/yocto_generic/arm_update_activate.sh"       "${D}/wigwag/mbed"
    install -m 755 "${SCRIPT_DIR}/yocto_generic/arm_update_active_details.sh" "${D}/wigwag/mbed"
    install -m 755 "${WORKDIR}/deploy_ostree_delta_update.sh" "${D}/wigwag/mbed"
}
