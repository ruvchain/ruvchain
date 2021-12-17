#!/bin/sh
java -cp "classes:lib/*:conf" ruv.tools.SignTransactionJSON $@
exit $?
