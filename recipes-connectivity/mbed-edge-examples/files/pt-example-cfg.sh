#!/bin/sh

echo "ETH0_MAC="$(cat /sys/class/net/eth0/address) >> /var/run/pt-example.conf

