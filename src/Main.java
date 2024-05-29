import controller.Controller;
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
        Controller controller = new Controller();

        ConnectionPage connectionPage = new ConnectionPage();
        connectionPage.start(primaryStage);
    }
}