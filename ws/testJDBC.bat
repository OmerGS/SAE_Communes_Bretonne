javac -d ../class ..\src\model\dao\DatabaseConnection.java

java --module-path "..\lib\mysql-connector-j-8.4.0\mysql-connector-j-8.4.0.jar" -cp ../class dao.DatabaseConnection

pause