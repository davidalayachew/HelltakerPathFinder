javac --enable-preview --source 21 -d classes src/Helltaker/Main.java src/module-info.java
jar --create --file run/HelltakerPathFinder_run_me.jar --main-class Helltaker.Main -C classes .
rm -rf ../_CUSTOM_JAVA_RUNTIME
jlink --output ../_CUSTOM_JAVA_RUNTIME --add-modules davidalayachew.HelltakerPathFinder --module-path run
jpackage \
	 --input run \
	 --name HelltakerPathFinder \
	 --description "A path finding algorithm for the video game Helltaker -- written in Java 20" \
	 --main-jar HelltakerPathFinder_run_me.jar \
	 --win-console \
	 --java-options "--enable-preview"
	 #--runtime-image ../_CUSTOM_JAVA_RUNTIME
