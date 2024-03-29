# This file is created  using cargo-bitbake tool from parsec-se-driver repository Cargo.lock

SRC_URI += " \
    crate://crates.io/aho-corasick/0.7.15 \
    crate://crates.io/ansi_term/0.11.0 \
    crate://crates.io/anyhow/1.0.40 \
    crate://crates.io/atty/0.2.14 \
    crate://crates.io/autocfg/1.0.1 \
    crate://crates.io/bincode/1.3.3 \
    crate://crates.io/bindgen/0.56.0 \
    crate://crates.io/bitflags/1.2.1 \
    crate://crates.io/bytes/0.5.6 \
    crate://crates.io/cc/1.0.67 \
    crate://crates.io/cexpr/0.4.0 \
    crate://crates.io/cfg-if/1.0.0 \
    crate://crates.io/clang-sys/1.2.0 \
    crate://crates.io/clap/2.33.3 \
    crate://crates.io/cmake/0.1.45 \
    crate://crates.io/derivative/2.2.0 \
    crate://crates.io/either/1.6.1 \
    crate://crates.io/env_logger/0.7.1 \
    crate://crates.io/env_logger/0.8.3 \
    crate://crates.io/fixedbitset/0.2.0 \
    crate://crates.io/form_urlencoded/1.0.1 \
    crate://crates.io/getrandom/0.2.2 \
    crate://crates.io/glob/0.3.0 \
    crate://crates.io/hashbrown/0.9.1 \
    crate://crates.io/heck/0.3.2 \
    crate://crates.io/hermit-abi/0.1.18 \
    crate://crates.io/humantime/1.3.0 \
    crate://crates.io/humantime/2.1.0 \
    crate://crates.io/idna/0.2.3 \
    crate://crates.io/indexmap/1.6.2 \
    crate://crates.io/itertools/0.8.2 \
    crate://crates.io/lazy_static/1.4.0 \
    crate://crates.io/lazycell/1.3.0 \
    crate://crates.io/libc/0.2.93 \
    crate://crates.io/libloading/0.7.0 \
    crate://crates.io/log/0.4.14 \
    crate://crates.io/matches/0.1.8 \
    crate://crates.io/memchr/2.3.4 \
    crate://crates.io/multimap/0.8.3 \
    crate://crates.io/nom/5.1.2 \
    crate://crates.io/num-bigint/0.3.2 \
    crate://crates.io/num-complex/0.3.1 \
    crate://crates.io/num-derive/0.3.3 \
    crate://crates.io/num-integer/0.1.44 \
    crate://crates.io/num-iter/0.1.42 \
    crate://crates.io/num-rational/0.3.2 \
    crate://crates.io/num-traits/0.2.14 \
    crate://crates.io/num/0.3.1 \
    crate://crates.io/parsec-client/0.12.0 \
    crate://crates.io/parsec-interface/0.24.0 \
    crate://crates.io/peeking_take_while/0.1.2 \
    crate://crates.io/percent-encoding/2.1.0 \
    crate://crates.io/petgraph/0.5.1 \
    crate://crates.io/ppv-lite86/0.2.10 \
    crate://crates.io/proc-macro2/1.0.26 \
    crate://crates.io/prost-build/0.6.1 \
    crate://crates.io/prost-derive/0.6.1 \
    crate://crates.io/prost-types/0.6.1 \
    crate://crates.io/prost/0.6.1 \
    crate://crates.io/psa-crypto-sys/0.8.0 \
    crate://crates.io/psa-crypto/0.8.0 \
    crate://crates.io/quick-error/1.2.3 \
    crate://crates.io/quote/1.0.9 \
    crate://crates.io/rand/0.8.3 \
    crate://crates.io/rand_chacha/0.3.0 \
    crate://crates.io/rand_core/0.6.2 \
    crate://crates.io/rand_hc/0.3.0 \
    crate://crates.io/redox_syscall/0.2.6 \
    crate://crates.io/regex-syntax/0.6.23 \
    crate://crates.io/regex/1.4.5 \
    crate://crates.io/remove_dir_all/0.5.3 \
    crate://crates.io/rustc-hash/1.1.0 \
    crate://crates.io/same-file/1.0.6 \
    crate://crates.io/secrecy/0.7.0 \
    crate://crates.io/serde/1.0.125 \
    crate://crates.io/serde_derive/1.0.125 \
    crate://crates.io/shlex/0.1.1 \
    crate://crates.io/strsim/0.8.0 \
    crate://crates.io/syn/1.0.69 \
    crate://crates.io/synstructure/0.12.4 \
    crate://crates.io/tempfile/3.2.0 \
    crate://crates.io/termcolor/1.1.2 \
    crate://crates.io/textwrap/0.11.0 \
    crate://crates.io/tinyvec/1.2.0 \
    crate://crates.io/tinyvec_macros/0.1.0 \
    crate://crates.io/unicode-bidi/0.3.5 \
    crate://crates.io/unicode-normalization/0.1.17 \
    crate://crates.io/unicode-segmentation/1.7.1 \
    crate://crates.io/unicode-width/0.1.8 \
    crate://crates.io/unicode-xid/0.2.1 \
    crate://crates.io/url/2.2.1 \
    crate://crates.io/users/0.10.0 \
    crate://crates.io/uuid/0.8.2 \
    crate://crates.io/vec_map/0.8.2 \
    crate://crates.io/version_check/0.9.3 \
    crate://crates.io/walkdir/2.3.2 \
    crate://crates.io/wasi/0.10.2+wasi-snapshot-preview1 \
    crate://crates.io/which/3.1.1 \
    crate://crates.io/winapi-i686-pc-windows-gnu/0.4.0 \
    crate://crates.io/winapi-util/0.1.5 \
    crate://crates.io/winapi-x86_64-pc-windows-gnu/0.4.0 \
    crate://crates.io/winapi/0.3.9 \
    crate://crates.io/zeroize/1.2.0 \
    crate://crates.io/zeroize_derive/1.0.1 \
"

# FIXME: update generateme with the real MD5 of the license file
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
"
