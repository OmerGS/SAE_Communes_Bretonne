package view;

import controller.Controller;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ResetPassword extends Application {

    private Controller controller;

    private Hyperlink linkForgotPassword;
    private Hyperlink linkConnectez;
    private PasswordField firstPassword;
    private PasswordField secondPassword;
    private Button btnLogin;
    private Label errorMessageLabel;

    public ResetPassword(Controller controller){
        this.controller = controller;
    }

    @Override
    public void start(Stage primaryStage) {
        // this.controller = new Controller(this);

        // Image pour le logo
        ImageView logo = new ImageView(new Image("file:../resources/image/logo_bretagne.png"));
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
        Label lblConnection = new Label("R\u00E9initialisation");
        lblConnection.setStyle("-fx-font-size: 35px; -fx-font-family: 'Arial'; -fx-text-fill: #333333;");

        // Champs avec placeholders
        this.firstPassword = new PasswordField();
        this.firstPassword.setPromptText("Nouveau mot de passe");
        this.firstPassword.setStyle("-fx-pref-width: 350px; -fx-background-color: #f0f0f0; -fx-background-radius: 30px; -fx-padding: 10px; -fx-font-family: 'Arial'; -fx-border-color: #ddd; -fx-border-radius: 30px;");

        this.secondPassword = new PasswordField();
        this.secondPassword.setPromptText("Nouveau mot de passe");
        this.secondPassword.setStyle("-fx-pref-width: 350px; -fx-background-color: #f0f0f0; -fx-background-radius: 30px; -fx-padding: 10px; -fx-font-family: 'Arial'; -fx-border-color: #ddd; -fx-border-radius: 30px;");

        // Bouton de connexion
        this.btnLogin = new Button("R\u00E9initialiser");
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
            // Arrêter l'animation si elle est en cours
            scaleTransition.stop();
            // Réinitialiser l'échelle du bouton à sa valeur par défaut
            btnLogin.setScaleX(1);
            btnLogin.setScaleY(1);
            // Lancer l'animation
            scaleTransition.play();
        });

        this.btnLogin.setOnMouseExited(event -> {
            // Arrêter l'animation si elle est en cours
            scaleTransition.stop();
            // Réinitialiser l'échelle du bouton à sa valeur par défaut
            btnLogin.setScaleX(1);
            btnLogin.setScaleY(1);
        });

        // Hyperliens
        Label linkMauvaisMail = new Label("Mauvais addresse mail ?");
        this.linkForgotPassword = new Hyperlink("Changez-le !");
        linkForgotPassword.setStyle("-fx-text-fill: #ffca10; -fx-font-family: 'Arial'; -fx-font-weight: bold;");

        Label linkMotDePasse = new Label("Vous connaissez votre mot de passe ?");
        this.linkConnectez = new Hyperlink("Connectez-vous !");
        this.linkConnectez.setStyle("-fx-text-fill: #ffca10; -fx-font-family: 'Arial'; -fx-font-weight: bold;");


        // HBox pour linkPasDeCompte et linkForgotPassword
        HBox signUpBox = new HBox(5, linkMauvaisMail, linkForgotPassword);
        signUpBox.setAlignment(Pos.CENTER);

        // VBox pour les liens
        VBox linksBox = new VBox(10, signUpBox, linkMotDePasse, linkConnectez);
        linksBox.setPadding(new Insets(20, 0, 0, 0));
        linksBox.setAlignment(Pos.CENTER);

        this.errorMessageLabel = new Label();
        this.errorMessageLabel.setTextFill(Color.RED);
        this.errorMessageLabel.setStyle("-fx-font-size: 14px;");
        this.errorMessageLabel.setVisible(false);

        // VBox pour les champs et les boutons
        VBox fieldBox = new VBox(15, lblConnection, firstPassword,secondPassword, errorMessageLabel, btnLogin);
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

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        
        primaryStage.show();

        this.firstPassword.setOnAction(this.controller);
        this.secondPassword.setOnAction(this.controller);
        this.linkConnectez.setOnAction(this.controller);
        this.btnLogin.setOnAction(this.controller);
        this.linkForgotPassword.setOnAction(this.controller);
    }



    public PasswordField getFirstPassword(){
        return(this.firstPassword);
    }

    public PasswordField getSecondPassword(){
        return(this.secondPassword);
    }

    public Button getBtnValidate(){
        return(this.btnLogin);
    }

    public Hyperlink getLinkConnection(){
        return(this.linkConnectez);
    }

    public Hyperlink getLinkWrongMail(){
        return(this.linkForgotPassword);
    }

    public Label getErrorMessageLabel(){
        return(this.errorMessageLabel);
    }
}