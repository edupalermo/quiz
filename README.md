BOOTSTRAP: https://getbootstrap.com/docs/3.4/css/
SIDE Bar: https://www.w3schools.com/howto/howto_css_sidebar_responsive.asp

WebSocket: https://www.mokkapps.de/blog/sending-message-to-specific-anonymous-user-on-spring-websocket
WebSocket: https://spring.io/guides/gs/messaging-stomp-websocket/

Login: https://bootsnipp.com/snippets/GaZG0


Reference: https://github.com/eugenp/tutorials/tree/master/spring-security-modules/spring-security-web-login


Thymeleaf: https://www.thymeleaf.org/doc/articles/springsecurity.html

HTTPS: https://www.thomasvitale.com/https-spring-boot-ssl-certificate/

Password generator
https://passwordsgenerator.net/



Derby


export JAVA_HOME="C:\Program Files\Java\jdk-11.0.10"
export PATH=$JAVA_HOME/bin:$PATH


export DERBY_HOME=/c/java/db-derby-10.15.2.0-bin
export DERBY_INSTALL=/c/java/db-derby-10.15.2.0-bin
// cd $DERBY_HOME/bin
// export CLASSPATH=$DERBY_HOME/lib/derbytools.jar:$DERBY_HOME/lib/derbynet.jar:.
cd $DERBY_HOME/lib
java -Dderby.system.home=$DERBY_HOME/db -jar derbyrun.jar server start

//export DERBY_INSTALL=/c/java/db-derby-10.15.2.0-bin



export CLASSPATH=$DERBY_HOME/lib/derby.jar:$DERBY_HOME/lib/derbytools.jar:$DERBY_HOME/lib/derbyoptionaltools.jar:$DERBY_HOME/lib/derbyshared.jar:$DERBY_HOME/lib/derbynet.jar:.

connect 'jdbc:derby://localhost:1527/QuizDb;create=true;user=web;password=PmXdxY9bkrr5YqVP';
connect 'jdbc:derby://localhost:1527/QuizDb;create=false;user=web;password=PmXdxY9bkrr5YqVP';


connect 'jdbc:derby://localhost:1527/QuizDb;create=true;user=app;password=PmXdxY9bkrr5YqVP';


        //.show-grid [class^=col-] {
        //    padding-top: 10px;
        //    padding-bottom: 10px;
        //    background-color: #eee;
        //    background-color: rgba(86,61,124,.15);
        //    border: 1px solid #ddd;
        //    border: 1px solid rgba(86,61,124,.2);
        //}
