inherit cargo

SRC_URI = "git://github.com/parallaxsecond/parsec-se-driver.git;rev=010627948f30457597111499f454a3de69f3c2db;protocol=https \
           git://github.com/ARMmbed/mbedtls.git;protocol=https;rev=ac15f842a5bb8d4cbceb7175c5d7ab50d96173c0;destsuffix=mbedtls"

LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SUMMARY = "Parsec Secure Element Driver"
HOMEPAGE = "https://github.com/parallaxsecond/parsec-se-driver"
LICENSE = "Apache-2.0"

S = "${WORKDIR}/git"
S_MBEDTLS = "${WORKDIR}/mbedtls"

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
