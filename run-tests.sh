#!/bin/sh
CP=conf/:classes/:lib/*:testlib/*
SP=src/java/:test/java/

if [ $# -eq 0 ]; then
TESTS="ruv.crypto.Curve25519Test ruv.crypto.ReedSolomonTest ruv.peer.HallmarkTest ruv.TokenTest ruv.FakeForgingTest
ruv.FastForgingTest ruv.ManualForgingTest"
else
TESTS=$@
fi

/bin/rm -f RuvChain.jar
/bin/rm -rf classes
/bin/mkdir -p classes/

javac -encoding utf8 -sourcepath ${SP} -classpath ${CP} -d classes/ src/java/ruv/*.java src/java/ruv/*/*.java test/java/ruv/*.java test/java/ruv/*/*.java || exit 1

for TEST in ${TESTS} ; do
java -classpath ${CP} org.junit.runner.JUnitCore ${TEST} ;
done



