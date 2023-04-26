javac --enable-preview --source 21 -d classes Helltaker/Main.java
jar --create --file run/HelltakerPathFinder_run_me.jar --main-class Helltaker.Main -C classes .