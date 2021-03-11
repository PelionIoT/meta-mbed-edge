inherit cargo

SUMMARY = "Parsec Secure Element Driver"
HOMEPAGE = "https://github.com/parallaxsecond/parsec-se-driver"
LICENSE = "Apache-2.0"

SRC_URI = "git://github.com/parallaxsecond/parsec-se-driver.git;protocol=https \
           git://github.com/ARMmbed/mbedtls.git;protocol=https;destsuffix=mbedtls;name=mbedtls"

SRCREV_pn-${PN} = "${PV}"
SRCREV_mbedtls = "mbedtls-2.22.0"

S = "${WORKDIR}/git"
S_MBEDTLS = "${WORKDIR}/mbedtls"

LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

BBCLASSEXTEND = "native"

TOOLCHAIN = "clang"

CARGO_DISABLE_BITBAKE_VENDORING = "1"
CARGO_VENDORING_DIRECTORY="${S}/vendor"

do_configure_append() {
    cd ${S}
    cargo vendor
}

do_compile_prepend() {
    export MBEDTLS_INCLUDE_DIR="${S_MBEDTLS}/include"
}

do_install() {
    install -d "${D}/usr/lib/"
    install -m 755 "${B}/target/${TARGET_SYS}/release/libparsec_se_driver.a" "${D}/usr/lib/libparsec_se_driver.a"
}
