#!/bin/sh

# WARNING: java still bypasses the tor proxy when sending DNS queries and
# this can reveal the fact that you are running Ruv, however blocks and
# transactions will be sent over tor only. Requires a tor proxy running
# at localhost:9050. Set ruv.shareMyAddress=false when using tor.

if [ -x jre/bin/java ]; then
    JAVA=./jre/bin/java
else
    JAVA=java
fi
${JAVA} -DsocksProxyHost=localhost -DsocksProxyPort=9050 -cp classes:lib/*:conf:addons/classes:addons/lib/* ruv.Ruv

