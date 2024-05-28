javac --module-path "..\lib\javafx-sdk-22.0.1\lib;..\lib\mysql-connector-j-8.4.0\mysql-connector-j-8.4.0.jar;..\lib\javax.mail.jar;..\lib\jaf-1.0.2\activation.jar;" --add-modules javafx.controls,java.mail -d ../class ../src/view/*.java ../src/controller/*.java ../src/model/data/exceptions/*.java ../src/model/dao/*.java ../src/model/data/*.java ../src/*.java

java --module-path "..\lib\javafx-sdk-22.0.1\lib;..\lib\mysql-connector-j-8.4.0\mysql-connector-j-8.4.0.jar;..\lib\javax.mail.jar;..\lib\jaf-1.0.2\activation.jar;" --add-modules javafx.controls,java.mail -cp ../class Main


pause