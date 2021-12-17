java -cp classes ruv.tools.ManifestGenerator
del ruv.jar
jar cfm ruv.jar resource\ruv.manifest.mf -C classes .
del ruvservice.jar
jar cfm ruvservice.jar resource\ruvservice.manifest.mf -C classes .

echo "jar files generated successfully"