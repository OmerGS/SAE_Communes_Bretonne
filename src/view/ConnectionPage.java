package view;

import controller.Controller;
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

/**
* ConnectionPage which allow to connect to account.
* @author O.Gunes, B.Campion. 
*/
public class ConnectionPage extends Application {

    /**
    * The controller of the application. 
    */
    private Controller controller;

    /**
    * The signup link, which redirect to InscriptionPage. 
    */
    private Hyperlink linkSignUp;

    /**
    * The forgot password link, which redirect to ForgotPassword page. 
    */
    private Hyperlink linkForgotPassword;

    /**
    * The email TextField, which allow to write mail, with the format (user@example.net) 
    */
    private TextField emailField;
    
    /**
    * The password PasswordField, which allow to write a password. 
    */
    private PasswordField passwordField;

    /**
    * The login button.
    */
    private Button btnLogin;

    /**
    * The errorMessageLabel, which print variable message.
    */
    private Label errorMessageLabel;

    /**
    * The constructor of ConnectionPage, initialize the controller.
    * @param controller The controller.
    */
    public ConnectionPage(Controller controller){
        this.controller = controller;
    }

    /**
    * Launch the programs
    * @param primaryStage The Stage. 
    */
    @Override
    public void start(Stage primaryStage) {
        // Image pour le logo
        ImageView logo = new ImageView(new Image("file:../resources/image/logo_bretagne.png"));
        logo.setFitWidth(100);
        logo.setFitHeight(100);
        logo.setClip(new Circle(50, 50, 50));

        // Label pour la région
        Label lblRegion = new Label("Region");
        Label lblBretagne = new Label("Bretagne");
        lblRegion.setStyle("-fx-font-size: 24px; -fx-text-fill: #333333;");
        lblBretagne.setStyle("-fx-font-size: 24px; -fx-text-fill: #333333;");

        // Layout pour le logo et le label
        VBox logoBox = new VBox(10, logo, lblRegion, lblBretagne);
        logoBox.setAlignment(Pos.CENTER);
        logoBox.setPadding(new Insets(20, 200, 20, 0));

        // Titre pour la section de connexion
        Label lblConnection = new Label("Connexion");
        lblConnection.setStyle("-fx-font-size: 35px; -fx-font-family: 'Arial'; -fx-text-fill: #333333;");

        // Champs avec placeholders
        this.emailField = new TextField();
        this.emailField.setPromptText("e-mail");
        this.emailField.setStyle("-fx-pref-width: 350px; -fx-background-color: #f0f0f0; -fx-background-radius: 30px; -fx-padding: 10px; -fx-font-family: 'Arial'; -fx-border-color: #ddd; -fx-border-radius: 30px;");

        this.passwordField = new PasswordField();
        this.passwordField.setPromptText("mot de passe");
        this.passwordField.setStyle("-fx-pref-width: 350px; -fx-background-color: #f0f0f0; -fx-background-radius: 30px; -fx-padding: 10px; -fx-font-family: 'Arial'; -fx-border-color: #ddd; -fx-border-radius: 30px;");

        // Bouton de connexion
        this.btnLogin = new Button("Connexion");
        this.btnLogin.setStyle("-fx-pref-width: 350px; -fx-background-color: linear-gradient(to right, #FF512F 0%, #F09819 51%, #FF512F 100%);"
                + "-fx-padding: 15px 45px;"
                + "-fx-text-fill: white;"
                + "-fx-background-radius: 10px;"
                + "-fx-font-family: 'Arial';"
                + "-fx-font-size: 20px;"
                + "-fx-background-size: 200% auto;");

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), btnLogin);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);

        this.btnLogin.setOnMouseEntered(event -> {
            scaleTransition.stop();
            btnLogin.setScaleX(1);
            btnLogin.setScaleY(1);
            scaleTransition.play();
        });

        this.btnLogin.setOnMouseExited(event -> {
            scaleTransition.stop();
            btnLogin.setScaleX(1);
            btnLogin.setScaleY(1);
        });

        // Hyperliens
        this.linkSignUp = new Hyperlink("S'inscrire");
        Label linkPasDeCompte = new Label("Pas de compte ?");
        this.linkForgotPassword = new Hyperlink("Mot de passe oubli\u00e9");
        linkSignUp.setStyle("-fx-text-fill: #1a73e8; -fx-font-family: 'Arial';-fx-font-weight: bold");
        linkForgotPassword.setStyle("-fx-text-fill: #1a73e8; -fx-font-family: 'Arial';-fx-font-weight: bold");

        // HBox pour linkPasDeCompte et linkSignUp
        HBox signUpBox = new HBox(5, linkPasDeCompte, linkSignUp);
        signUpBox.setAlignment(Pos.CENTER);

        // VBox pour les liens
        VBox linksBox = new VBox(10, signUpBox, linkForgotPassword);
        linksBox.setPadding(new Insets(20, 0, 0, 0));
        linksBox.setAlignment(Pos.CENTER);

        // Label pour les messages d'erreur
        this.errorMessageLabel = new Label();
        this.errorMessageLabel.setTextFill(Color.RED);
        this.errorMessageLabel.setStyle("-fx-font-size: 14px;");
        this.errorMessageLabel.setVisible(false);

        // VBox pour les champs et les boutons
        VBox fieldBox = new VBox(15, lblConnection, emailField, passwordField, btnLogin, errorMessageLabel);
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

        // Liaison des événements
        this.linkSignUp.setOnAction(this.controller);
        this.linkForgotPassword.setOnAction(this.controller);
        this.emailField.setOnAction(this.controller);
        this.passwordField.setOnAction(this.controller);
        this.btnLogin.setOnAction(this.controller);
    }


    /* ----- Getters ----- */

    /**
    * Getters for the link, for inscription page.
    * @return The HyperLink.
    */
    public Hyperlink getLinkSignUp() {
        return linkSignUp;
    }

    /**
    * 
    * @return
    */
    public Hyperlink getLinkForgotPassword() {
        return linkForgotPassword;
    }

    /**
    * 
    * @return
    */
    public TextField getEmailField() {
        return emailField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Button getBtnLogin() {
        return btnLogin;
    }

    public Label getErrorMessageLabel() {
        return errorMessageLabel;
    }

    public static void main(String[] args) {
        launch(args);
    }
}