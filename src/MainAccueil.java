import javafx.application.Application;
import javafx.stage.Stage;

import view.*;

public class MainAccueil extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Accueil Accueil = new Accueil();
        Accueil.start(primaryStage);
    }
}