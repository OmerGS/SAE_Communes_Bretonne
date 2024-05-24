javac --module-path %JavaGithubSAE% --add-modules javafx.controls -d ../class ../src/view/*.java ../src/controller/*.java ../src/model/data/exceptions/*.java ../src/model/dao/*.java ../src/model/data/*.java ../src/*.java

java --module-path %JavaGithubSAE% --add-modules javafx.controls -cp ../class MainAcc

pause