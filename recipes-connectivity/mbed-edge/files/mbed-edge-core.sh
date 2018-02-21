CORE_PORT=22223
CORE_HTTP_PORT=8080

#Load optional configuration from /etc/default
test ! -r /etc/default/mbed-edge-core.sh || . /etc/default/mbed-edge-core.sh

NAME=edge-core
PIDFILE=/var/run/$NAME.pid
LOGFILE=/var/log/$NAME.log
DAEMON=/opt/arm/edge-core
DAEMON_OPTS="$CORE_PORT $CORE_HTTP_PORT"

start() {
  if [ "$ENABLE_COREFILES" == 1 ]; then
    echo "Enabling coredumps for "$NAME
    ulimit -c unlimited
  fi

  #edge-core will be launched into a new shell, where it's output is directed to a log file
  echo -n "Starting daemon: "$NAME
  start-stop-daemon --start --quiet --make-pidfile --pidfile $PIDFILE --background --exec /bin/sh -- -c "$DAEMON $DAEMON_OPTS >> $LOGFILE 2>&1"
  echo " - done"
}

stop() {
  echo -n "Stopping daemon: "$NAME
  #kill both the shell and the edge-core running in it
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

