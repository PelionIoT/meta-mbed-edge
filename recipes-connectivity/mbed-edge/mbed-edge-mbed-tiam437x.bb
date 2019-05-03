require mbed-edge.inc

COMPATIBLE_MACHINE = "am437x-evm"

PROVIDES += " virtual/mbed-edge virtual/mbed-edge-dbg "
RPROVIDES_${PN} += " virtual/mbed-edge virtual/mbed-edge-dbg "

FILESEXTRAPATHS_prepend := "${THISDIR}/files/mbed-tiam437x:"
SRC_URI += "file://target.cmake \
            file://sotp_fs_tiam437x_yocto.h"

do_install_append() {
    install -m 755 "${SCRIPT_DIR}/arm_update_cmdline.sh"                  "${D}/opt/arm"

    # use rpi fw update scripts since am437x implementation is essentially a rpi clone.
    install -m 755 "${SCRIPT_DIR}/yocto_rpi/arm_update_activate.sh"       "${D}/opt/arm"
    install -m 755 "${SCRIPT_DIR}/yocto_rpi/arm_update_active_details.sh" "${D}/opt/arm"
}
