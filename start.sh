#!/bin/sh
APPLICATION="RuvChain"
if [ -e ~/.${APPLICATION}/ruv.pid ]; then
    PID=`cat ~/.${APPLICATION}/ruv.pid`
    ps -p $PID > /dev/null
    STATUS=$?
    if [ $STATUS -eq 0 ]; then
        echo "Ruv server already running"
        exit 1
    fi
fi
mkdir -p ~/.${APPLICATION}/
DIR=`dirname "$0"`
cd "${DIR}"
if [ -x jre/bin/java ]; then
    JAVA=./jre/bin/java
else
    JAVA=java
fi
nohup ${JAVA} -cp classes:lib/*:conf:addons/classes:addons/lib/* -Druv.runtime.mode=desktop ruv.Ruv > /dev/null 2>&1 &
echo $! > ~/.${APPLICATION}/ruv.pid
cd - > /dev/null
