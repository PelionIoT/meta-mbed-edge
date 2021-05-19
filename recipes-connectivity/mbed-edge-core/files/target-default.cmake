if (${FOTA_ENABLE})
  add_definitions(
      -DMBED_CLOUD_CLIENT_FOTA_LINUX_HEADER_FILENAME="/userdata/fota_fw_metadata"
      -DMBED_CLOUD_CLIENT_FOTA_LINUX_UPDATE_STORAGE_FILENAME="/userdata/fota_candidate"
      -DMBED_CLOUD_CLIENT_FOTA_LINUX_CANDIDATE_FILENAME="/userdata/fota_raw_candidate"
  )

  SET (MBED_CLOUD_CLIENT_MIDDLEWARE curl)
  SET (PLATFORM_TARGET Yocto_Generic_YoctoLinux_mbedtls)
endif()

# When PARSEC_TPM_SE_SUPPORT is enabled, most likely you are using PSA trusted
# storage rather than default ESFS to save the provisioning data. In that case, these
# macros are irrelevant. To control the psa storage file location, please modify the
# config/psa_storage_user_config.h.
SET (PAL_FS_MOUNT_POINT_PRIMARY "\"/userdata/mbed/mcc_config\"")
SET (PAL_FS_MOUNT_POINT_SECONDARY "\"/userdata/mbed/mcc_config\"")

if(${PARSEC_TPM_SE_SUPPORT})
  SET (MBED_CLOUD_CLIENT_MIDDLEWARE trusted_storage mbedtls parsec_se_driver)
  SET (PLATFORM_TARGET Yocto_Generic_YoctoLinux_mbedtls)
  SET (MBED_CLOUD_CLIENT_OS Linux_Yocto_v2.2)
  SET (MBED_CLOUD_CLIENT_SDK )
  SET (MBED_CLOUD_CLIENT_TOOLCHAIN )
  SET (MBED_CLOUD_CLIENT_BUILD_SYS_MIN_VER 2)
  SET (MBED_CLOUD_CLIENT_NATIVE_SDK False)
endif()
