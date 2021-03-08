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

echo "Info: Executing arm_update_activate.sh"

# store the header into partition which survies factory reset
mkdir -p /userdata/extended
if ! cp $HEADER /userdata/extended/header.bin; then 
    echo "Error: Failed to store the header!"
    exit 8
fi

# backup the system and journal logs
mkdir -p /userdata/.logs-before-upgrade
cp -R /var/log/* /userdata/.logs-before-upgrade/

# save version file checksum
md5sum /wigwag/etc/versions.json > /userdata/mbed/version_checksum.md5

# save the firmware image to /upgrades partition
# /upgrades is where the Pelion Edge's upgrade utility expects the image to be
# checkout out https://github.com/PelionIoT/scripts-pelion-edge to learn more about the update image packaging
mv $FIRMWARE /upgrades/firmware.tar.gz
if ! tar -xzf /upgrades/firmware.tar.gz -C /upgrades/; then
    echo "Error: Image copy failed!"
    exit 5
fi

# go to middle boot and try applying the update
reboot

echo "Info: Exiting arm_update_activate.sh"
exit 0