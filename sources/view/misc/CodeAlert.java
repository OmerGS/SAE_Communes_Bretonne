package view.misc;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class represents a custom alert window designed to prompt the user for a validation code.
 * The alert window is typically used to validate a user's identity by sending a code to their email address.
 * The user is expected to enter the received code into the provided text field within the alert window.
 * 
 * The `CodeAlert` class is closely tied to a controller that handles the events triggered by user actions, such as
 * entering the code and pressing the submit button.
 * 
 * This class provides methods to display the alert window (`askCode`), and to retrieve the entered code, the button, 
 * and the alert label for further handling and validation.
 * 
 * @author O.Gunes
 */
public class CodeAlert {
    /**
     * Controller to handle events 
     */
    private Controller controller;

    /**
     * TextField where the user write the code sent by mail
     */
    private TextField codeField;

    /**
     * The submit and close button
     */
    private Button closeButton;

    /**
     * Label to inform the user.
     */
    private Label alertLabel;

    /**
     * Constructor to initialize the CodeAlert with the given controller.
     *
     * @param controller The controller to handle events.
     */
    public CodeAlert(Controller controller){
        this.controller = controller;
    }

    /**
     * Displays the alert window with the specified email address.
     * The window informs the user that an email has been sent and
     * provides a text field for entering the validation code.
     *
     * @param email The email address to which the validation code has been sent.
     */
    public void askCode(String email) {
        Stage alertStage = new Stage();
        alertStage.initModality(Modality.APPLICATION_MODAL);
        alertStage.setTitle("Validation d'identité");
        alertStage.setResizable(false); // Make the window non-resizable
        alertStage.setMaximized(false); // Prevent fullscreen mode

        Label contentLabel = new Label("Un mail a été envoyé à l'adresse " + email);
        contentLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495E;");
        contentLabel.setWrapText(true);

        this.codeField = new TextField();
        codeField.setOnAction(this.controller);

        this.alertLabel = new Label();
        this.alertLabel.setVisible(false);        

        this.closeButton = new Button("OK");
        closeButton.setStyle("-fx-background-color: #FF007F; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-background-radius: 5px;");
        closeButton.setOnAction(this.controller);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(contentLabel, codeField, closeButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setMaxWidth(280); // Limit the layout width to keep the window compact

        Scene scene = new Scene(layout);
        alertStage.setScene(scene);
        alertStage.showAndWait();
    }

    /**
     * Gets the text field where the user enters the validation code.
     *
     * @return The text field for the validation code.
     */
    public TextField getCodeField(){
        return this.codeField;
    }

    /**
     * Gets the close button used to submit the entered validation code.
     *
     * @return The close button.
     */
    public Button getCloseButton(){
        return this.closeButton;
    }

    /**
     * Gets the alert label used for displaying messages to the user.
     *
     * @return The alert label.
     */
    public Label getAlertLabel(){
        return this.alertLabel;
    }
}
