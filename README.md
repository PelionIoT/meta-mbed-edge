This README file contains information on the contents of the
meta-mbed-edge layer.

This meta-mbed-edge layer contains the Mbed Edge recipe. The recipe can
be used to extend the meta-mbed-raspberrypi meta-layer with the Mbed
Edge functionality.

To add the Mbed Edge to your build, insert following line to your local.conf:

`CORE_IMAGE_EXTRA_INSTALL += " mbed-edge-released "`

To set the path to developer certificate file, please set following variable in your local.conf

`MBED_CLOUD_IDENTITY_CERT_FILE = "/path/to/your/mbed_cloud_dev_credentials.c"`

To set the path to update resource file, please set following variable in your local.conf

`MBED_UPDATE_RESOURCE_FILE = "/path/to/your/update_default_resources.c"`

# Dependencies

The Mbed Edge is currently tested on top of the Morty-version of the
Yocto. The following repositories are required for the build:

[poky](https://git.yoctoproject.org/cgit/cgit.cgi/poky/)

[meta-openembedded](http://cgit.openembedded.org/meta-openembedded/)

[meta-raspberrypi](https://git.yoctoproject.org/cgit/cgit.cgi/meta-raspberrypi/)

# Adding the meta-mbed-edge layer to your build

In order to use this layer, you need to make the build system aware of
it.

Assuming the meta-mbed-edge layer exists at the top-level of your
yocto build tree, you can add it to the build system by adding the
location of the meta-mbed-edge layer to bblayers.conf, along with any
other layers needed. e.g.:

```
  BBLAYERS ?= " \
    /path/to/yocto/meta \
    /path/to/yocto/meta-poky \
    /path/to/yocto/meta-mbed-raspberrypi \
    /path/to/yocto/meta-mbed-edge \
    "
```
