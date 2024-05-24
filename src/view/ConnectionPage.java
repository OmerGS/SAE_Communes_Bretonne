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

public class ConnectionPage extends Application {

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
        // Ajustement du padding pour déplacer le logoBox vers la gauche
        logoBox.setPadding(new Insets(20, 200, 20, 0));

        // Titre pour la section de connexion
        Label lblConnection = new Label("Connexion");
        lblConnection.setStyle("-fx-font-size: 35px; -fx-font-family: 'Arial'; -fx-text-fill: #333333;");

        // Champs avec placeholders
        TextField emailField = new TextField();
        emailField.setPromptText("e-mail");
        emailField.setStyle("-fx-pref-width: 350px; -fx-background-color: #f0f0f0; -fx-background-radius: 30px; -fx-padding: 10px; -fx-font-family: 'Arial'; -fx-border-color: #ddd; -fx-border-radius: 30px;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("mot de passe");
        passwordField.setStyle("-fx-pref-width: 350px; -fx-background-color: #f0f0f0; -fx-background-radius: 30px; -fx-padding: 10px; -fx-font-family: 'Arial'; -fx-border-color: #ddd; -fx-border-radius: 30px;");

        // Bouton de connexion
        Button btnLogin = new Button("Connexion");
        btnLogin.setStyle("-fx-pref-width: 350px; -fx-background-color: linear-gradient(to right, #FF512F 0%, #F09819 51%, #FF512F 100%);"
                + "-fx-padding: 15px 45px;"
                + "-fx-text-fill: white;"
                + "-fx-background-radius: 10px;"
                + "-fx-font-family: 'Arial';"
                + "-fx-font-size: 20px;"
                + "-fx-box-shadow: 0 0 20px #eee;"
                + "-fx-background-size: 200% auto;");

                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), btnLogin);
                scaleTransition.setToX(1.1);
                scaleTransition.setToY(1.1);
            
                btnLogin.setOnMouseEntered(event -> {
                    // Arrêter l'animation si elle est en cours
                    scaleTransition.stop();
                    // Réinitialiser l'échelle du bouton à sa valeur par défaut
                    btnLogin.setScaleX(1);
                    btnLogin.setScaleY(1);
                    // Lancer l'animation
                    scaleTransition.play();
                });
            
                btnLogin.setOnMouseExited(event -> {
                    // Arrêter l'animation si elle est en cours
                    scaleTransition.stop();
                    // Réinitialiser l'échelle du bouton à sa valeur par défaut
                    btnLogin.setScaleX(1);
                    btnLogin.setScaleY(1);
                });

        // Hyperliens
        Hyperlink linkSignUp = new Hyperlink("S'inscrire");
        Label linkPasDeCompte = new Label("Pas de compte ?");
        Hyperlink linkForgotPassword = new Hyperlink("mot de passe oublié");
        linkSignUp.setStyle("-fx-text-fill: #1a73e8; -fx-font-family: 'Arial';");
        linkForgotPassword.setStyle("-fx-text-fill: #1a73e8; -fx-font-family: 'Arial';");

        // HBox pour linkPasDeCompte et linkSignUp
        HBox signUpBox = new HBox(5, linkPasDeCompte, linkSignUp);
        signUpBox.setAlignment(Pos.CENTER);

        // VBox pour les liens
        VBox linksBox = new VBox(10, signUpBox, linkForgotPassword);
        linksBox.setPadding(new Insets(20, 0, 0, 0));
        linksBox.setAlignment(Pos.CENTER);

        // VBox pour les champs et les boutons
        VBox fieldBox = new VBox(15, lblConnection, emailField, passwordField, btnLogin);
        fieldBox.setPadding(new Insets(20));
        fieldBox.setAlignment(Pos.CENTER);

        // Séparateur avec une couleur personnalisée
        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 10, 0));
        separator.setStyle("-fx-color: #333333;"); // Changez la couleur ici

        // VBox pour les champs, le séparateur et les liens
        VBox centralBox = new VBox(20, fieldBox, separator, linksBox);
        centralBox.setAlignment(Pos.CENTER);

        // HBox pour le logo et le contenu central
        HBox mainBox = new HBox(20, logoBox, centralBox);
        mainBox.setPadding(new Insets(20));
        mainBox.setAlignment(Pos.CENTER);
        // Syntaxe du dégradé linéaire corrigée pour JavaFX CSS
        mainBox.setStyle("-fx-background-color: linear-gradient(to right, rgba(255,165,0,1) 0%, rgba(0,0,0,0) 50%, rgba(255,255,255,1) 100%);");

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