javac --module-path "C:\Users\omerf\Desktop\SAE\SAE 2.0 - Communes\App\javafx-sdk-22.0.1\lib" --add-modules javafx.controls -d ../class ../src/view/*.java
javac --module-path "C:\Users\omerf\Desktop\SAE\SAE 2.0 - Communes\App\javafx-sdk-22.0.1\lib" --add-modules javafx.controls -d ../class ../src/*.java

java --module-path "C:\Users\omerf\Desktop\SAE\SAE 2.0 - Communes\App\javafx-sdk-22.0.1\lib" --add-modules javafx.controls -cp ../class Main

pause