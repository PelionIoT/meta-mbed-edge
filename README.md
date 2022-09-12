# meta-mbed-edge

This README file contains information on the contents of the `meta-mbed-edge` layer.

This `meta-mbed-edge` layer contains the Mbed Edge and Mbed Edge protocol translator example recipes.

This recipe does not contain dependecies to any BSP. For building Mbed Edge
on Raspberry Pi 3 or Texas Instruments AM437x there exists separate BSP-layers.
See the dependencies section of this README for further information.

To add the Mbed Edge to your build, insert following line to your local.conf:

`IMAGE_INSTALL += " virtual/mbed-edge mbed-edge-examples "`

The Mbed Edge CMake configuration can be injected with `MBED_EDGE_CUSTOM_CMAKE_ARGUMENTS`
environment variable. The content of the variable is the CMake configuration line
to inject. Please note that the `MBED_EDGE_CUSTOM_CMAKE_ARGUMENTS` needs to be listed in the `BB_ENV_PASSTHROUGH_ADDITIONS` environment, otherwise it will not have effect in the build.

Please consult the [Mbed Edge](https://github.com/ARMmbed/mbed-edge) repository
how to configure the Mbed Edge build and check the `recipes-connectivity/mbed-edge/files`-folder
for the configuration files.

If the protocol translator examples are installed image, please note that the bluetooth
needs to be enabled and the firmware for the bluetooth chip needs to be installed. On the Raspberry Pi this
can be done by adding following lines to the local.conf:

```
DISTRO_FEATURES:append = " bluetooth "

CORE_IMAGE_EXTRA_INSTALL += " linux-firmware-bcm43430 linux-firmware-bcm43430a1-hcd "
```

# Dependencies

The Mbed Edge is currently tested on top of the `sumo`-version of the
Yocto. The following repositories are required for the build:

[poky](https://git.yoctoproject.org/cgit/cgit.cgi/poky/)

[meta-openembedded](http://cgit.openembedded.org/meta-openembedded/)

For Raspberry Pi 3 BSP and Mbed Cloud Client firmware update support:

[meta-mbed-raspberrypi](https://github.com/ARMmbed/meta-mbed-raspberrypi/)

For Texas Instruments AM437x BSP and Mbed Cloud Client firmware update support:

[meta-mbed-am437x](https://github.com/ARMmbed/meta-mbed-am437x/)

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
    /path/to/yocto/meta-openembedded/meta-oe \
    /path/to/yocto/meta-raspberrypi \
    /path/to/yocto/meta-mbed-raspberrypi \
    /path/to/yocto/meta-mbed-edge \
    "
```

The BSP support for Raspberry Pi 3 is included in the example above.
