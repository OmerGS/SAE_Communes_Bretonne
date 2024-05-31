import controller.Controller;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import view.*;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Controller controller = new Controller();

        primaryStage.getIcons().add(new Image("file:../resources/image/logo_bretagne.png"));

        MainPage mainPage = new MainPage();
        mainPage.start(primaryStage);
        
        /*
        ConnectionPage connectionPage = new ConnectionPage();
        connectionPage.start(primaryStage);
        */
        
    }
}