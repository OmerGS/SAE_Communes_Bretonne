package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ViewMainPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Image for the logo
        ImageView logo = new ImageView(new Image("file:logo_bretagne.png")); // Assurez-vous que le fichier logo_bretagne.png est dans le bon répertoire
        logo.setFitWidth(100);
        logo.setFitHeight(100);
        logo.setClip(new Circle(50, 50, 50)); // Rendre l'image circulaire

        // Label for the region
        Label lblRegion = new Label("Region Bretagne");
        lblRegion.setStyle("-fx-font-size: 24px; -fx-text-fill: #333333;");

        // Layout for logo and label
        VBox logoBox = new VBox(10, logo, lblRegion);
        logoBox.setAlignment(Pos.CENTER_LEFT);
        logoBox.setPadding(new Insets(20, 0, 20, 20));

        // Title for the connection section
        Label lblConnection = new Label("Connection");
        lblConnection.setStyle("-fx-font-size: 24px; -fx-font-family: 'Arial'; -fx-text-fill: #333333;");

        // Fields with placeholders
        TextField emailField = new TextField();
        emailField.setPromptText("e-mail");
        emailField.setStyle("-fx-pref-width: 250px; -fx-background-color: #f0f0f0; -fx-background-radius: 30px; -fx-padding: 10px; -fx-font-family: 'Arial'; -fx-border-color: #ddd; -fx-border-radius: 30px;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("mot de passe");
        passwordField.setStyle("-fx-pref-width: 250px; -fx-background-color: #f0f0f0; -fx-background-radius: 30px; -fx-padding: 10px; -fx-font-family: 'Arial'; -fx-border-color: #ddd; -fx-border-radius: 30px;");

        // Buttons
        Button btnLogin = new Button("Connection");
        btnLogin.setStyle("-fx-pref-width: 250px; -fx-background-color: #FFA500; -fx-text-fill: white; -fx-background-radius: 30px; -fx-font-family: 'Arial';");

        // Hyperlinks
        Hyperlink linkSignUp = new Hyperlink("S'inscrire");
        Hyperlink linkForgotPassword = new Hyperlink("mot de passe oublié");
        linkSignUp.setStyle("-fx-text-fill: #1a73e8; -fx-font-family: 'Arial';");
        linkForgotPassword.setStyle("-fx-text-fill: #1a73e8; -fx-font-family: 'Arial';");

        // VBox for fields and buttons
        VBox fieldBox = new VBox(15, lblConnection, emailField, passwordField, btnLogin);
        fieldBox.setPadding(new Insets(20));
        fieldBox.setAlignment(Pos.CENTER);

        // Separator
        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 10, 0));

        // VBox for links
        VBox linksBox = new VBox(10, linkSignUp, linkForgotPassword);
        linksBox.setPadding(new Insets(20, 0, 0, 0));
        linksBox.setAlignment(Pos.CENTER);

        // VBox for fields, separator, and links
        VBox centralBox = new VBox(20, fieldBox, separator, linksBox);
        centralBox.setAlignment(Pos.CENTER);

        // HBox for logo and central content
        HBox mainBox = new HBox(20, logoBox, centralBox);
        mainBox.setPadding(new Insets(20));
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setStyle("-fx-background-color: linear-gradient(to right, #FFA500, #FFFFFF);");

        // Scene
        Scene scene = new Scene(mainBox, 800, 600);

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