javac --module-path "..\lib\javafx-sdk-22.0.1\lib;..\lib\json-simple-1.1.1.jar" --add-modules javafx.controls -d ../class ../src/test/*.java

java --module-path "..\lib\javafx-sdk-22.0.1\lib;..\lib\json-simple-1.1.1.jar" --add-modules javafx.controls -cp ../class test.TestClient

pause