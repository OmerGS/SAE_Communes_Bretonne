import javafx.application.Application;
import javafx.stage.Stage;

//Add page

import view.*;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ViewMainPage viewMainPage = new ViewMainPage();
        viewMainPage.start(primaryStage);
    }
}