package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Accueil extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        // Create ImageViews
        ImageView connectionImageView = new ImageView("file:../resources/image/personnageF.png");
        ImageView inscriptionImageView = new ImageView("file:../resources/image/personnageH.png");

        // Set ImageView properties
        connectionImageView.setFitWidth(400); // Half of 800
        connectionImageView.setFitHeight(800); // Full height of 800
        inscriptionImageView.setFitWidth(400); // Half of 800
        inscriptionImageView.setFitHeight(800); // Full height of 800

        // Create HBox for both images
        HBox hbox = new HBox(connectionImageView, inscriptionImageView);
        hbox.setAlignment(Pos.CENTER);

        // Root layout
        StackPane root = new StackPane(hbox);

        // Scene
        Scene scene = new Scene(root, 800, 600);

        // Stage setup
        primaryStage.setScene(scene);
        primaryStage.setTitle("Region Bretagne");
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
