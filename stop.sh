#!/bin/sh
APPLICATION="RuvChain"
if [ -e ~/.${APPLICATION}/ruv.pid ]; then
    PID=`cat ~/.${APPLICATION}/ruv.pid`
    ps -p $PID > /dev/null
    STATUS=$?
    echo "stopping"
    while [ $STATUS -eq 0 ]; do
        kill `cat ~/.${APPLICATION}/ruv.pid` > /dev/null
        sleep 5
        ps -p $PID > /dev/null
        STATUS=$?
    done
    rm -f ~/.${APPLICATION}/ruv.pid
    echo "Ruv server stopped"
fi

