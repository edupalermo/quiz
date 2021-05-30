export JAVA_HOME="C:\Program Files\Java\jdk-11.0.10"
export PATH=$JAVA_HOME/bin:/usr/bin:/bin:/c/Windows/system32:/c/Windows
export DERBY_HOME=/c/java/db-derby-10.15.2.0-bin
cd $DERBY_HOME/lib
java -Dderby.system.home=$DERBY_HOME/db -jar derbyrun.jar server start
