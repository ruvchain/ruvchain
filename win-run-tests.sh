#!/bin/sh
CP="conf/;classes/;lib/*;testlib/*"
SP="src/java/;test/java/"
TESTS="ruv.crypto.Curve25519Test ruv.crypto.ReedSolomonTest"

/bin/rm -f RuvChain.jar
/bin/rm -rf classes
/bin/mkdir -p classes/

javac -encoding utf8 -sourcepath $SP -classpath $CP -d classes/ src/java/ruv/*.java src/java/ruv/*/*.java test/java/ruv/*/*.java || exit 1

java -classpath $CP org.junit.runner.JUnitCore $TESTS

