javac --module-path "..\lib\javafx-sdk-22.0.1\lib;..\lib\mysql-connector-j-8.4.0\mysql-connector-j-8.4.0.jar;..\lib\javax.mail.jar;..\lib\jaf-1.0.2\activation.jar;" --add-modules javafx.controls,javafx.web,java.mail -d ../classes ../sources/view/*.java ../sources/controller/*.java ../sources/model/data/exceptions/*.java ../sources/model/dao/*.java ../sources/model/data/*.java ../sources/view/misc/*.java ../sources/*.java

java --module-path "..\lib\javafx-sdk-22.0.1\lib;..\lib\mysql-connector-j-8.4.0\mysql-connector-j-8.4.0.jar;..\lib\javax.mail.jar;..\lib\jaf-1.0.2\activation.jar;" --add-modules javafx.controls,javafx.web,java.mail -cp ../classes Main

pause