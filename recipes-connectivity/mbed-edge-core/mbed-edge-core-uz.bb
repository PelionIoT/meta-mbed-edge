COMPATIBLE_MACHINE = "uz"

require mbed-edge-core.inc

PROVIDES += " virtual/mbed-edge-core virtual/mbed-edge-core-dbg "
RPROVIDES_${PN} += " virtual/mbed-edge-core virtual/mbed-edge-core-dbg "

FILESEXTRAPATHS_prepend := "${THISDIR}/files/uz:"
SRC_URI += "file://target.cmake \
            file://target-default.cmake \
            file://sotp_fs_uz_yocto.h \
            file://deploy_ostree_delta_update.sh \
            file://0001-fix_psa_storage_location.patch \
            file://pal_plat_uz.c \
            file://0008-ordered-reboot.patch "

do_configure_prepend() {
    mkdir -p ${S}/lib/mbed-cloud-client/mbed-client-pal/Source/Port/Reference-Impl/OS_Specific/Linux/Board_Specific/TARGET_uz
    cp ${WORKDIR}/pal_plat_uz.c ${S}/lib/mbed-cloud-client/mbed-client-pal/Source/Port/Reference-Impl/OS_Specific/Linux/Board_Specific/TARGET_uz/
}

do_install_append() {
    install -m 755 "${WORKDIR}/deploy_ostree_delta_update.sh" "${D}/wigwag/mbed"
}
