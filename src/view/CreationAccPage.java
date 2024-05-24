package view;

import javafx.animation.ScaleTransition;
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
import javafx.util.Duration;

public class CreationAccPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Image pour le logo
        ImageView logo = new ImageView(new Image("file:logo_bretagne.png")); // Assurez-vous que le fichier logo_bretagne.png est dans le bon répertoire
        logo.setFitWidth(100);
        logo.setFitHeight(100);
        logo.setClip(new Circle(50, 50, 50)); // Rendre l'image circulaire

        // Label pour la région
        Label lblRegion = new Label("Region");
        Label lblBretagne = new Label("Bretagne");
        lblRegion.setStyle("-fx-font-size: 24px; -fx-text-fill: #333333;");
        lblBretagne.setStyle("-fx-font-size: 24px; -fx-text-fill: #333333;");

        // Layout pour le logo et le label
        VBox logoBox = new VBox(10, logo, lblRegion, lblBretagne);
        logoBox.setAlignment(Pos.CENTER);
        logoBox.setPadding(new Insets(20, 0, 20, 200));

        // Titre pour la section de connexion
        Label lblConnection = new Label("Inscription");
        lblConnection.setStyle("-fx-font-size: 35px; -fx-font-family: 'Arial'; -fx-text-fill: #333333;");

        // Champs avec placeholders
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("Nom");
        firstNameField.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 30px; -fx-padding: 10px; -fx-font-family: 'Arial'; -fx-border-color: #ddd; -fx-border-radius: 30px;");

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Pr\u00E9nom");
        lastNameField.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 30px; -fx-padding: 10px; -fx-font-family: 'Arial'; -fx-border-color: #ddd; -fx-border-radius: 30px;");

        HBox nameBox = new HBox(10, firstNameField, lastNameField);
        nameBox.setAlignment(Pos.CENTER);

        TextField emailField = new TextField();
        emailField.setPromptText("e-mail");
        emailField.setStyle("-fx-pref-width: 350px; -fx-background-color: #f0f0f0; -fx-background-radius: 30px; -fx-padding: 10px; -fx-font-family: 'Arial'; -fx-border-color: #ddd; -fx-border-radius: 30px;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("mot de passe");
        passwordField.setStyle("-fx-pref-width: 350px; -fx-background-color: #f0f0f0; -fx-background-radius: 30px; -fx-padding: 10px; -fx-font-family: 'Arial'; -fx-border-color: #ddd; -fx-border-radius: 30px;");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("mot de passe");
        confirmPasswordField.setStyle("-fx-pref-width: 350px; -fx-background-color: #f0f0f0; -fx-background-radius: 30px; -fx-padding: 10px; -fx-font-family: 'Arial'; -fx-border-color: #ddd; -fx-border-radius: 30px;");

        // Bouton de connexion
        Button btnSignUp = new Button("Inscription");
        btnSignUp.setStyle("-fx-pref-width: 350px; -fx-background-color: #8ecae6;"
                + "-fx-padding: 15px 45px;"
                + "-fx-text-fill: white;"
                + "-fx-background-radius: 10px;"
                + "-fx-font-family: 'Arial';"
                + "-fx-font-size: 20px;");

                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), btnSignUp);
                scaleTransition.setToX(1.1);
                scaleTransition.setToY(1.1);
            
                btnSignUp.setOnMouseEntered(event -> {
                    // Arrêter l'animation si elle est en cours
                    scaleTransition.stop();
                    // Réinitialiser l'échelle du bouton à sa valeur par défaut
                    btnSignUp.setScaleX(1);
                    btnSignUp.setScaleY(1);
                    // Lancer l'animation
                    scaleTransition.play();
                });
            
                btnSignUp.setOnMouseExited(event -> {
                    // Arrêter l'animation si elle est en cours
                    scaleTransition.stop();
                    // Réinitialiser l'échelle du bouton à sa valeur par défaut
                    btnSignUp.setScaleX(1);
                    btnSignUp.setScaleY(1);
                });

        // Hyperliens
        Hyperlink linkLogin = new Hyperlink("Connectez-vous !");
        Label linkAlreadyAccount = new Label("D\u00E9j\u00e0 inscrit ?");
        linkAlreadyAccount.setStyle("-fx-font-size: 16px; -fx-text-fill: #000000; -fx-font-family: 'Arial';");
        linkLogin.setStyle("-fx-font-size: 16px; -fx-text-fill: #ff6b6b; -fx-font-family: 'Arial';");

        // HBox pour linkAlreadyAccount et linkLogin
        HBox loginBox = new HBox(5, linkAlreadyAccount, linkLogin);
        loginBox.setAlignment(Pos.CENTER);

        // VBox pour les champs et les boutons
        VBox fieldBox = new VBox(15, lblConnection, nameBox, emailField, passwordField, confirmPasswordField, btnSignUp);
        fieldBox.setPadding(new Insets(20));
        fieldBox.setAlignment(Pos.CENTER);
        fieldBox.setStyle("-fx-background-color: #e0f7fa; -fx-background-radius: 30px;");

        // Séparateur avec une couleur personnalisée
        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 10, 0));
        separator.setStyle("-fx-color: #333333;"); // Changez la couleur ici

        // VBox pour les champs, le séparateur et les liens
        VBox centralBox = new VBox(20, fieldBox, separator, loginBox);
        centralBox.setAlignment(Pos.CENTER);

        // HBox pour le logo et le contenu central
        HBox mainBox = new HBox(20, centralBox, logoBox);
        mainBox.setPadding(new Insets(20));
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setStyle("-fx-background-color: linear-gradient(to right, #ffffff 0%, #8ecae6 100%);");

        // Scène
        Scene scene = new Scene(mainBox, 800, 600);

        // Configuration de la fenêtre
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
