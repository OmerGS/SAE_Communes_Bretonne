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

/**
* ConnectionPage which allows to connect to an account.
* @author O.Gunes, B.Campion. 
*/
public class ConnectionPage extends Application {

    /**
    * The controller of the application. 
    */
    private Controller controller;

    /**
    * The signup link, which redirects to InscriptionPage. 
    */
    private Hyperlink linkSignUp;

    /**
    * The forgot password link, which redirects to ForgotPassword page. 
    */
    private Hyperlink linkForgotPassword;

    /**
    * The email TextField, which allows to write email, with the format (mail@example.net) 
    */
    private TextField emailField;
    
    /**
    * The password PasswordField, which allows to write a password. 
    */
    private PasswordField passwordField;

    /**
    * The login button.
    */
    private Button btnLogin;

    /**
    * The errorMessageLabel, which prints variable message.
    */
    private Label errorMessageLabel;

    private Button returnButton;

    /**
    * The constructor of ConnectionPage, initializes the controller.
    * @param controller The controller.
    */
    public ConnectionPage(Controller controller){
        this.controller = controller;
    }

    /**
    * Launch the program.
    * @param primaryStage The Stage. 
    */
    @Override
    public void start(Stage primaryStage) {
        // Image for the logo
        ImageView logo = new ImageView(new Image("file:../resources/image/logo_bretagne.png"));
        logo.setFitWidth(100);
        logo.setFitHeight(100);
        logo.setClip(new Circle(50, 50, 50));

        // Label for the region
        Label lblRegion = new Label("Region");
        Label lblBretagne = new Label("Bretagne");
        lblRegion.setStyle("-fx-font-size: 24px; -fx-text-fill: #333333;");
        lblBretagne.setStyle("-fx-font-size: 24px; -fx-text-fill: #333333;");

        // Layout for the logo and the label
        VBox logoBox = new VBox(10, logo, lblRegion, lblBretagne);
        logoBox.setAlignment(Pos.CENTER);
        logoBox.setPadding(new Insets(20, 200, 20, 0));

        // Title for the connection section
        Label lblConnection = new Label("Connexion");
        lblConnection.setStyle("-fx-font-size: 35px; -fx-font-family: 'Arial'; -fx-text-fill: #333333;");

        // Fields with placeholders
        this.emailField = new TextField();
        this.emailField.setPromptText("e-mail");
        this.emailField.setStyle("-fx-pref-width: 350px; -fx-background-color: #f0f0f0; -fx-background-radius: 30px; -fx-padding: 10px; -fx-font-family: 'Arial'; -fx-border-color: #ddd; -fx-border-radius: 30px;");

        this.passwordField = new PasswordField();
        this.passwordField.setPromptText("mot de passe");
        this.passwordField.setStyle("-fx-pref-width: 350px; -fx-background-color: #f0f0f0; -fx-background-radius: 30px; -fx-padding: 10px; -fx-font-family: 'Arial'; -fx-border-color: #ddd; -fx-border-radius: 30px;");

        // Login button
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

        // Hyperlinks
        this.linkSignUp = new Hyperlink("S'inscrire");
        Label linkPasDeCompte = new Label("Pas de compte ?");
        this.linkForgotPassword = new Hyperlink("Mot de passe oubli\u00e9");
        linkSignUp.setStyle("-fx-text-fill: #1a73e8; -fx-font-family: 'Arial';-fx-font-weight: bold");
        linkForgotPassword.setStyle("-fx-text-fill: #1a73e8; -fx-font-family: 'Arial';-fx-font-weight: bold");

        // HBox for linkPasDeCompte and linkSignUp
        HBox signUpBox = new HBox(5, linkPasDeCompte, linkSignUp);
        signUpBox.setAlignment(Pos.CENTER);

        // VBox for the links
        VBox linksBox = new VBox(10, signUpBox, linkForgotPassword);
        linksBox.setPadding(new Insets(20, 0, 0, 0));
        linksBox.setAlignment(Pos.CENTER);

        // Label for error messages
        this.errorMessageLabel = new Label();
        this.errorMessageLabel.setTextFill(Color.RED);
        this.errorMessageLabel.setStyle("-fx-font-size: 14px;");
        this.errorMessageLabel.setVisible(false);

        // VBox for the fields and buttons
        VBox fieldBox = new VBox(15, lblConnection, emailField, passwordField, btnLogin, errorMessageLabel);
        fieldBox.setPadding(new Insets(20));
        fieldBox.setAlignment(Pos.CENTER);

        // Separator with custom color
        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 10, 0));
        separator.setStyle("-fx-color: #333333;"); // Change the color here

        // VBox for the fields, separator, and links
        VBox centralBox = new VBox(20, fieldBox, separator, linksBox);
        centralBox.setAlignment(Pos.CENTER);

        // HBox for the logo and central content
        HBox mainBox = new HBox(20, logoBox, centralBox);
        mainBox.setPadding(new Insets(20));
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setStyle("-fx-background-color: linear-gradient(to right, rgba(255,165,0,1) 0%, rgba(0,0,0,0) 50%, rgba(255,255,255,1) 100%);");

        // Scene
        Scene scene = new Scene(mainBox, 800, 600);

        // Window configuration
        primaryStage.setScene(scene);
        primaryStage.setTitle("Region Bretagne");
        
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        primaryStage.show();

        // Event bindings
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
    public Button getReturnButton(){
        return this.returnButton;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
