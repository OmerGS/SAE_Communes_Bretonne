javac --module-path %JavaGithubSAE% --add-modules javafx.controls,java.mail -d ../class ../src/view/*.java ../src/controller/*.java ../src/model/data/exceptions/*.java ../src/model/dao/*.java ../src/model/data/*.java ../src/*.java

java --module-path %JavaGithubSAE% --add-modules javafx.controls,java.mail -cp ../class Main


pause