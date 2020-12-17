require mbed-edge-core.inc

COMPATIBLE_MACHINE = "uz3eg-iocc"

PROVIDES += " virtual/mbed-edge-core virtual/mbed-edge-core-dbg "
RPROVIDES_${PN} += " virtual/mbed-edge-core virtual/mbed-edge-core-dbg "

FILESEXTRAPATHS_prepend := "${THISDIR}/files/mbed-uz3eg:"
SRC_URI += "file://target.cmake \
            file://sotp_fs_uz3eg_yocto.h \
            file://pal_plat_uz3eg.c"

SCRIPT_DIR = "${WORKDIR}/git/lib/mbed-cloud-client/update-client-hub/modules/pal-linux/scripts"

do_configure_prepend() {
    mkdir -p ${S}/lib/mbed-cloud-client/mbed-client-pal/Source/Port/Reference-Impl/OS_Specific/Linux/Board_Specific/TARGET_uz3eg
    cp ${WORKDIR}/pal_plat_uz3eg.c ${S}/lib/mbed-cloud-client/mbed-client-pal/Source/Port/Reference-Impl/OS_Specific/Linux/Board_Specific/TARGET_uz3eg/
}

do_install_append() {
    install -m 755 "${SCRIPT_DIR}/arm_update_cmdline.sh"                  "${D}/wigwag/mbed"
    install -m 755 "${SCRIPT_DIR}/yocto_generic/arm_update_activate.sh"       "${D}/wigwag/mbed"
    install -m 755 "${SCRIPT_DIR}/yocto_generic/arm_update_active_details.sh" "${D}/wigwag/mbed"
}
