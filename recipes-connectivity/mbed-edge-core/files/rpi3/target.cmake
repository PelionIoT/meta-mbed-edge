MESSAGE ("Building Yocto Linux target")
SET (OS_BRAND Linux)
SET (MBED_CLOUD_CLIENT_DEVICE "Yocto_Generic")
SET (PAL_TARGET_DEVICE "Yocto_Generic")

SET (PAL_USER_DEFINED_CONFIGURATION "\"${TARGET_CONFIG_ROOT}/sotp_fs_rpi3_yocto.h\"")
SET (BIND_TO_ALL_INTERFACES 0)
SET (PAL_UPDATE_FIRMWARE_DIR "\"/upgrades\"")

if (${FIRMWARE_UPDATE})
  SET (MBED_CLOUD_CLIENT_UPDATE_STORAGE ARM_UCP_LINUX_YOCTO_RPI)
endif()

include(${TARGET_CONFIG_ROOT}/target-default.cmake)
