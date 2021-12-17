#!/bin/sh
if [ -x jre/bin/java ]; then
    JAVA=./jre/bin/java
else
    JAVA=java
fi
${JAVA} -cp classes:lib/*:conf:addons/classes:addons/lib/* -Druv.runtime.mode=desktop -Druv.runtime.dirProvider=ruv.env.DefaultDirProvider ruv.Ruv
