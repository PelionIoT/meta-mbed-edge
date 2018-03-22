# meta-mbed-edge

This README file contains information on the contents of the `meta-mbed-edge` layer.

This `meta-mbed-edge` layer contains the Mbed Edge recipe.

This recipe does not contain dependecies to any BSP. For building Mbed Edge
on Raspberry Pi 3 there is `meta-mbed-raspberrypi` layer available.

To add the Mbed Edge to your build, insert following line to your local.conf:

`CORE_IMAGE_EXTRA_INSTALL += " mbed-edge-released "`

To set the path to developer certificate file, please set following variable
in your `local.conf`:

`MBED_CLOUD_IDENTITY_CERT_FILE = "/path/to/your/mbed_cloud_dev_credentials.c"`

To set the path to update resource file, please set following variable in
your `local.conf`

`MBED_UPDATE_RESOURCE_FILE = "/path/to/your/update_default_resources.c"`

# Dependencies

The Mbed Edge is currently tested on top of the `morty`-version of the
Yocto. The following repositories are required for the build:

[poky](https://git.yoctoproject.org/cgit/cgit.cgi/poky/)

[meta-openembedded](http://cgit.openembedded.org/meta-openembedded/)

For Raspberry Pi 3 BSP and Mbed Cloud Client firmware update support:

[meta-mbed-raspberrypi](https://github.com/ARMmbed/meta-mbed-raspberrypi/)

# Adding the `meta-mbed-edge` layer to your build

In order to use this layer, you need to make the build system aware of
it.

Assuming the `meta-mbed-edge` layer exists at the top-level of your
Yocto build tree, you can add it to the build system by adding the
location of the `meta-mbed-edge` layer to `bblayers.conf`, along with any
other layers needed. e.g.:

```
  BBLAYERS ?= " \
    /path/to/yocto/meta \
    /path/to/yocto/meta-poky \
    /path/to/yocto/meta-openembedded \
    /path/to/yocto/meta-raspberrypi \
    /path/to/yocto/meta-mbed-raspberrypi \
    /path/to/yocto/meta-mbed-edge \
    "
```

The BSP support for Raspberry Pi 3 is included in the example above.
