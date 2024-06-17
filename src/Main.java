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

        primaryStage.getIcons().add(new Image("file:../resources/image/logo_bretagne.png"));
        Controller controller = new Controller();
        
        //AccountPage mainPage = new AccountPage(controller);
        //mainPage.start(primaryStage);
        

        
        MainPage connectionPage = new MainPage(controller);
        connectionPage.start(primaryStage);
        
        
    }
}