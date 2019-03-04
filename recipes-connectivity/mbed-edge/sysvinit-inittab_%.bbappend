do_install_append() {

    #
    # add edge-core under sysvinit respawn to inittab
    #
    echo "ec:12345:respawn:/opt/arm/edge-core --http-port=8080 >> /var/log/edge-core.log" >> ${D}${sysconfdir}/inittab
}
