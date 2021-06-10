export JAVA_HOME="C:\Program Files\Java\jdk-11.0.10"
export PATH=$JAVA_HOME/bin:/usr/bin:/bin:/c/Windows/system32:/c/Windows
export DERBY_HOME=/c/java/db-derby-10.15.2.0-bin
export CLASSPATH=$DERBY_HOME/lib/derby.jar:$DERBY_HOME/lib/derbytools.jar:$DERBY_HOME/lib/derbyoptionaltools.jar:$DERBY_HOME/lib/derbyshared.jar:.
java -Dij.database="jdbc:derby://localhost:1527/QuizDb;create=false;user=web;password=PmXdxY9bkrr5YqVP" org.apache.derby.tools.ij
