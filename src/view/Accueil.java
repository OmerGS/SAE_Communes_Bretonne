package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Accueil extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Create ImageViews
        ImageView connectionImageView = new ImageView(new Image("file:../resources/image/personnageF.jpg"));
        ImageView inscriptionImageView = new ImageView(new Image("file:../resources/image/personnageH.jpg"));

        // Ensure the images maintain their aspect ratio and fit the available space
        connectionImageView.setPreserveRatio(false);
        connectionImageView.setSmooth(true);
        inscriptionImageView.setPreserveRatio(false);
        inscriptionImageView.setSmooth(true);

        // Create Buttons
        Button connectionButton = new Button("Connection");
        Button inscriptionButton = new Button("Inscription");

        // Style Buttons
        connectionButton.setStyle("-fx-background-color: #D9C44A; -fx-font-size: 20px; -fx-text-fill: white; -fx-background-radius: 20px; -fx-padding: 10px 20px;");
        inscriptionButton.setStyle("-fx-background-color: #62A9C4; -fx-font-size: 20px; -fx-text-fill: white; -fx-background-radius: 20px; -fx-padding: 10px 20px;");

        // Create StackPanes for image and button
        StackPane connectionStack = new StackPane(connectionImageView, connectionButton);
        connectionStack.setAlignment(Pos.BOTTOM_CENTER); // Align the button at the bottom center
        StackPane inscriptionStack = new StackPane(inscriptionImageView, inscriptionButton);
        inscriptionStack.setAlignment(Pos.BOTTOM_CENTER); // Align the button at the bottom center

        // Bind ImageView sizes to the scene size
        connectionImageView.fitWidthProperty().bind(primaryStage.widthProperty().divide(2));
        connectionImageView.fitHeightProperty().bind(primaryStage.heightProperty());
        inscriptionImageView.fitWidthProperty().bind(primaryStage.widthProperty().divide(2));
        inscriptionImageView.fitHeightProperty().bind(primaryStage.heightProperty());

        // Create HBox for both StackPanes
        HBox hbox = new HBox(connectionStack, inscriptionStack);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(0); // No spacing between the two StackPanes to align them closely

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
