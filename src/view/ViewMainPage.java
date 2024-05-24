package view;
// todo
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ViewMainPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label lblRegion = new Label("Region Bretagne");

        // Layout for logo and label
        HBox logoBox = new HBox(10, lblRegion);
        logoBox.setAlignment(Pos.CENTER);

        // Fields with placeholders
        TextField emailField = new TextField();
        emailField.setPromptText("e-mail");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("mot de passe");

        // Buttons
        Button btnLogin = new Button("Connection");
        Hyperlink linkSignUp = new Hyperlink("S'inscrire");
        Hyperlink linkForgotPassword = new Hyperlink("mot de passe oublié");

        // VBox for fields and buttons
        VBox fieldBox = new VBox(10, emailField, passwordField, btnLogin);
        fieldBox.setPadding(new Insets(20));
        fieldBox.setAlignment(Pos.CENTER);

        // HBox for the sign-up link
        HBox signUpBox = new HBox(linkSignUp);
        signUpBox.setAlignment(Pos.CENTER);

        // HBox for the forgot password link
        HBox forgotPasswordBox = new HBox(linkForgotPassword);
        forgotPasswordBox.setAlignment(Pos.CENTER);

        // Main VBox for all components
        VBox mainBox = new VBox(20, logoBox, fieldBox, signUpBox, forgotPasswordBox);
        mainBox.setPadding(new Insets(20));
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setStyle("-fx-background-color: linear-gradient(to right, #FFA500, #FFFFFF);");

        // Scene
        Scene scene = new Scene(mainBox, 400, 400);

        // Stage setup
        primaryStage.setScene(scene);
        primaryStage.setTitle("Region Bretagne");
        primaryStage.show();
    }
}