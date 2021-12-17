#!/bin/sh
APPLICATION="RuvChain"
java -cp classes ruv.tools.ManifestGenerator
/bin/rm -f ${APPLICATION}.jar
jar cfm ${APPLICATION}.jar resource/ruv.manifest.mf -C classes . || exit 1
/bin/rm -f ${APPLICATION}service.jar
jar cfm ${APPLICATION}service.jar resource/ruvservice.manifest.mf -C classes . || exit 1

echo "jar files generated successfully"