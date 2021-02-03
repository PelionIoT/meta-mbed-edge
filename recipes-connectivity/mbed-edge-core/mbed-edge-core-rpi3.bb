COMPATIBLE_MACHINE = "raspberrypi3"

MBED_EDGE_CORE_CONFIG_TRACE_LEVEL ?= "INFO"
MBED_EDGE_CORE_CONFIG_FIRMWARE_UPDATE ?= "ON"
MBED_EDGE_CORE_CONFIG_FOTA_ENABLE ?= "OFF"
MBED_EDGE_CORE_CONFIG_FOTA_TRACE ?= "OFF"
MBED_EDGE_CORE_CONFIG_CURL_DYNAMIC_LINK ?= "OFF"
MBED_EDGE_CORE_CONFIG_DEVELOPER_MODE ?= "ON"
MBED_EDGE_CORE_CONFIG_FACTORY_MODE ?= "OFF"
MBED_EDGE_CORE_CONFIG_BYOC_MODE ?= "OFF"

require mbed-edge-core.inc

PROVIDES += " virtual/mbed-edge-core virtual/mbed-edge-core-dbg "
RPROVIDES_${PN} += " virtual/mbed-edge-core virtual/mbed-edge-core-dbg "

FILESEXTRAPATHS_prepend := "${THISDIR}/files/rpi3:"
SRC_URI += "file://target.cmake \
            file://sotp_fs_rpi3_yocto.h \
            file://arm_update_cmdline.sh \
            file://arm_update_activate.sh \
            file://arm_update_active_details.sh \
            file://0001-change-path-to-upgrade-scripts.patch \
            "

do_install_append() {
    install -m 755 "${WORKDIR}/arm_update_cmdline.sh"        "${D}/wigwag/mbed"
    install -m 755 "${WORKDIR}/arm_update_activate.sh"       "${D}/wigwag/mbed"
    install -m 755 "${WORKDIR}/arm_update_active_details.sh" "${D}/wigwag/mbed"
}
