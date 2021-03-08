#!/bin/sh
# ----------------------------------------------------------------------------
# Parse command line
#
# HEADER
# FIRMWARE
# LOCATION
# OFFSET
# SIZE
#
. /wigwag/mbed/arm_update_cmdline.sh

set -x

echo "Info: Executing arm_update_active_details.sh"

# software version file after the firmware updated is expected to be changed
# thus for successful firmware update md5sum check should fail
if md5sum -c /userdata/mbed/version_checksum.md5 | grep -q 'FAILED'; then

    # copy stored header to expected location
    if ! cp /userdata/extended/header.bin $HEADER; then
        echo "Error: No active firmware header found!"
        exit 9
    fi

    echo "Info: Firmware update successful!";
    exit 0

else

    echo "Error: Firmware update failed, version file did not update!";
    exit 8

fi