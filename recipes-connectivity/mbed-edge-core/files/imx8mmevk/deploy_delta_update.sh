#!/bin/sh

# Deploy a tar ball as an ostree static delta.
#
# $1 is expected to contain the compressed tarball of the deployment.
# This should contain the static delta files along with a metadata file containing the shas of the base and the updated commits:
#
#    ./
#    ./0
#    ./metadata
#    ./superblock


echo "Installing delta update $1"

# Exit on error
set -e

# Add trap to delete tempory folder on exit.
trap 'echo "removing /tmp/$$"; rm -rf /tmp/$$' INT TERM EXIT


# Create temp directory
mkdir -p /tmp/$$


# Extract the metadata. This contins the from and to sha information.
tar -x --directory /tmp/$$ -f $1 ./metadata

from_sha=$(grep From-sha /tmp/$$/metadata | cut -d":" -f2)
to_sha=$(grep To-sha /tmp/$$/metadata | cut -d":" -f2)

# Clear exit on error
set +e

# check that the ostree repo deployed on the device contains the base sha.
ostree ls $from_sha > /dev/null 2>&1
RESULT=$?

if [ $RESULT -eq 0 ]; then

    # Extract the rest of the tarball
    tar -x --directory /tmp/$$ -f $1

    # Apply the delta
    ostree static-delta apply-offline -v  /tmp/$$/superblock
    RESULT=$?

    if [ $RESULT -eq 0 ]; then

        # Make the new deployment.
        ostree admin deploy $to_sha

        # sync filesystems to make sure everything is flushed.
        sync

        RESULT=$?
        if [ $RESULT -eq 0 ]; then
            echo "Delta deployed correctly"
        else
            echo "Delta failed to deploy"
            exit 1
        fi

    else

        echo "Error: Could not apply the static delta"
        exit 1

    fi
else
        echo "Error: Could not find $from_sha in the deployed ostree repo"
        exit 2
fi

