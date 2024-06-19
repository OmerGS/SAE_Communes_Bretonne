javac -d ../classes ..\sources\model\dao\printUserDatabase.java

java --module-path "..\lib\mysql-connector-j-8.4.0\mysql-connector-j-8.4.0.jar" -cp ../classes dao.printUserDatabase

pause