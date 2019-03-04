do_install_append() {

    #
    # pt-example under sysvinit respawn to inittab
    #

    echo 'ex:12345:respawn:/opt/arm/pt-example -n pt-example --endpoint-postfix=-$(cat /sys/class/net/eth0/address) >> /var/log/pt-example.log' >> ${D}${sysconfdir}/inittab

    #
    # blept-example under sysvinit respawn to inittab
    #

    echo 'eb:12345:respawn:/opt/arm/blept-example -n blept-example -e ble -c -d /opt/arm/blept-devices.json >> /var/log/blept-example.log' >> ${D}${sysconfdir}/inittab
}
