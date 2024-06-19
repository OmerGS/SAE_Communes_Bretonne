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
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class InscriptionPage extends Application {

    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private Button btnSignUp;
    private Hyperlink linkLogin;
    private Label errorMessageLabel; // Ajout du label pour les messages d'erreur

    private Controller controller;

    public InscriptionPage(Controller controller){
        this.controller = controller;
    }

    @Override
    public void start(Stage primaryStage) {
        // Image pour le logo
        ImageView logo = new ImageView(new Image("file:../resources/image/logo_bretagne.png")); // Assurez-vous que le fichier logo_bretagne.png est dans le bon répertoire
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
        this.firstNameField = new TextField();
        this.firstNameField.setPromptText("Pr\\u00E9nom");
        this.firstNameField.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 30px; -fx-padding: 10px; -fx-font-family: 'Arial'; -fx-border-color: #ddd; -fx-border-radius: 30px;");

        this.lastNameField = new TextField();
        this.lastNameField.setPromptText("Nom");
        this.lastNameField.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 30px; -fx-padding: 10px; -fx-font-family: 'Arial'; -fx-border-color: #ddd; -fx-border-radius: 30px;");

        HBox nameBox = new HBox(10, firstNameField, lastNameField);
        nameBox.setAlignment(Pos.CENTER);

        this.emailField = new TextField();
        this.emailField.setPromptText("e-mail");
        this.emailField.setStyle("-fx-pref-width: 350px; -fx-background-color: #f0f0f0; -fx-background-radius: 30px; -fx-padding: 10px; -fx-font-family: 'Arial'; -fx-border-color: #ddd; -fx-border-radius: 30px;");

        this.passwordField = new PasswordField();
        this.passwordField.setPromptText("mot de passe");
        this.passwordField.setStyle("-fx-pref-width: 350px; -fx-background-color: #f0f0f0; -fx-background-radius: 30px; -fx-padding: 10px; -fx-font-family: 'Arial'; -fx-border-color: #ddd; -fx-border-radius: 30px;");

        this.confirmPasswordField = new PasswordField();
        this.confirmPasswordField.setPromptText("mot de passe");
        this.confirmPasswordField.setStyle("-fx-pref-width: 350px; -fx-background-color: #f0f0f0; -fx-background-radius: 30px; -fx-padding: 10px; -fx-font-family: 'Arial'; -fx-border-color: #ddd; -fx-border-radius: 30px;");

        // Label pour les messages d'erreur
        this.errorMessageLabel = new Label();
        this.errorMessageLabel.setStyle("-fx-text-fill: red;");

        // Bouton de connexion
        this.btnSignUp = new Button("Inscription");
        this.btnSignUp.setStyle("-fx-pref-width: 350px; -fx-background-color: #8ecae6;"
                + "-fx-padding: 15px 45px;"
                + "-fx-text-fill: white;"
                + "-fx-background-radius: 10px;"
                + "-fx-font-family: 'Arial';"
                + "-fx-font-size: 20px;");

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), btnSignUp);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
            
        this.btnSignUp.setOnMouseEntered(event -> {
            // Arrêter l'animation si elle est en cours
            scaleTransition.stop();
            // Réinitialiser l'échelle du bouton à sa valeur par défaut
            btnSignUp.setScaleX(1);
            btnSignUp.setScaleY(1);
            // Lancer l'animation
            scaleTransition.play();
        });
            
        this.btnSignUp.setOnMouseExited(event -> {
            // Arrêter l'animation si elle est en cours
            scaleTransition.stop();
            // Réinitialiser l'échelle du bouton à sa valeur par défaut
            btnSignUp.setScaleX(1);
            btnSignUp.setScaleY(1);
        });

        // Hyperliens
        this.linkLogin = new Hyperlink("Connectez-vous !");
        Label linkAlreadyAccount = new Label("D\u00E9j\u00E0 inscrit ?");
        linkAlreadyAccount.setStyle("-fx-font-size: 16px; -fx-text-fill: #000000; -fx-font-family: 'Arial';");
        this.linkLogin.setStyle("-fx-font-size: 16px; -fx-text-fill: #ffca10; -fx-font-family: 'Arial';-fx-font-weight: bold");

        // HBox pour linkAlreadyAccount et linkLogin
        HBox loginBox = new HBox(5, linkAlreadyAccount, linkLogin);
        loginBox.setAlignment(Pos.CENTER);

        // VBox pour les champs et les boutons
        VBox fieldBox = new VBox(15, lblConnection, nameBox, emailField, passwordField, confirmPasswordField, btnSignUp, errorMessageLabel); // Ajout du label d'erreur ici
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

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Region Bretagne");

        primaryStage.show();

        // Liaison des événements
        this.firstNameField.setOnAction(this.controller);
        this.lastNameField.setOnAction(this.controller);
        this.emailField.setOnAction(this.controller);
        this.passwordField.setOnAction(this.controller);
        this.btnSignUp.setOnAction(this.controller);
        this.linkLogin.setOnAction(this.controller);
    }

    public TextField getFirstNameField(){
        return this.firstNameField;
    }

    public TextField getLastNameField() {
        return this.lastNameField;
    }

    public TextField getEmailField() {
        return this.emailField;
    }

    public PasswordField getPasswordField() {
        return this.passwordField;
    }

    public PasswordField getConfirmPasswordField() {
        return this.confirmPasswordField;
    }

    public Button getBtnSignUp() {
        return this.btnSignUp;
    }

    public Hyperlink getLinkLogin() {
        return this.linkLogin;
    }

    public Label getErrorMessageLabel() {
        return this.errorMessageLabel;
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}