DESCRIPTION = "Tool to convert the development certificate to CBOR formatted object"

LICENSE="Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=1dece7821bf3fd70fe1309eaa37d52a2"

SRCREV = "0.18.0"
SRC_URI = "git://github.com/PelionIoT/mbed-edge.git"

S = "${WORKDIR}/git/edge-tool"

FILES_${PN} = "/wigwag/mbed/edge-tool/*"

RDEPENDS_${PN} += "python3-cbor2 python3-cryptography python3-docopt python3-six python3-pyclibrary"

inherit setuptools3

do_install() {
    install -d "${D}/wigwag/mbed"
    cp -r ${S} ${D}/wigwag/mbed/edge-tool
}
