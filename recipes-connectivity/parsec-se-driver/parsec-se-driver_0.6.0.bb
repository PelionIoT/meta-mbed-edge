
SUMMARY = "Parsec Secure Element Driver"
HOMEPAGE = "https://github.com/parallaxsecond/parsec-se-driver"
LICENSE = "Apache-2.0"

inherit cargo
SRC_URI = "git://github.com/parallaxsecond/parsec-se-driver.git;protocol=https;branch=main \
           git://github.com/ARMmbed/mbedtls.git;protocol=https;destsuffix=mbedtls;name=mbedtls;branch=archive/development_2.x"

SRCREV_pn-${PN} = "${PV}"
SRCREV_mbedtls = "mbedtls-2.27.0"

S = "${WORKDIR}/git"
S_MBEDTLS = "${WORKDIR}/mbedtls"

TOOLCHAIN = "clang"

do_compile_prepend() {
    export MBEDTLS_INCLUDE_DIR="${S_MBEDTLS}/include"
}

do_install() {
    install -d "${D}/${libdir}"
    install -m 755 "${B}/target/${TARGET_SYS}/release/libparsec_se_driver.a" "${D}/${libdir}"
}

include parsec-se-driver_${PV}.inc
