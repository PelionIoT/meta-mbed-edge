#!/bin/sh

PROTOCOL_TRANSLATOR_NAME=blept-example
CORE_HTTP_PORT=8080
ENDPOINT_POSTFIX=$(cat /sys/class/net/eth0/address)
#Load optional configuration from /etc/default
test ! -r /etc/default/mbed-edge-blept-example.sh || . /etc/default/mbed-edge-blept-example.sh

NAME=blept-example
PIDFILE=/var/run/$NAME.pid
LOGFILE=/var/log/$NAME.log
DAEMON=/opt/arm/blept-example
DAEMON_OPTS="--edge-domain-socket=/tmp/edge.sock --protocol-translator-name=$PROTOCOL_TRANSLATOR_NAME --endpoint-postfix=-$ENDPOINT_POSTFIX"

start() {
  if [ "$ENABLE_COREFILES" == 1 ]; then
    echo "Enabling coredumps for "$NAME
    ulimit -c unlimited
  fi
  echo -n "Starting daemon: "$NAME
  #BLEPT-example can't be started before edge-core is up & running, try three times to start the blept-example
  for i in 0 1 2
  do
    #try to get the status from the blept-example status api to check if it has started
    wget -qO- 127.0.0.1:$CORE_HTTP_PORT/status &> /dev/null
    if [ $? -eq 0 ]; then
      #edge-core will be launched into a new shell, where it's output is directed to a log file
      start-stop-daemon --start --quiet --make-pidfile --pidfile $PIDFILE --background --exec /bin/sh -- -c "$DAEMON $DAEMON_OPTS >> $LOGFILE 2>&1"
      echo " - done"
      return 0
    fi
    sleep 3
  done
  echo " - FAILED"
}

stop() {
  echo -n "Stopping daemon: "$NAME
  #kill both the shell and the blept-example running in it
  bashPID=$(cat $PIDFILE); [ -n "$bashPID" ] && pkill -P "$bashPID"
  start-stop-daemon --stop --quiet --oknodo --pidfile $PIDFILE
  rm $PIDFILE
  echo " - done"
}

case "$1" in
  start)
    start
    ;;
  stop)
    stop
    ;;
  *)
    echo "Usage: $0 {start|stop}"
esac

