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

public class CodeAlert {
    private Controller controller;
    private TextField codeField;
    private Button closeButton;

    public CodeAlert(Controller controller){
        this.controller = controller;
    }

    /**
    * This constructor allow to display a window title and the content
    *
    * @param title The title of notification
    * @param content The content of notification
    */
    public void askCode(String email) {
        Stage alertStage = new Stage();
        alertStage.initModality(Modality.APPLICATION_MODAL);
        alertStage.setTitle("Validation d'idendité");
        alertStage.setResizable(false); // Rendre la fenêtre immuable
        alertStage.setMaximized(false); // Empêcher le plein écran

        Label contentLabel = new Label("Un mail a ete envoye a l'addresse " + email);
        contentLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495E;");
        contentLabel.setWrapText(true);

        this.codeField = new TextField();
        codeField.setOnAction(this.controller);

        this.closeButton = new Button("OK");
        closeButton.setStyle("-fx-background-color: #FF007F; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-background-radius: 5px;");
        closeButton.setOnAction(this.controller);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(contentLabel, codeField, closeButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setMaxWidth(280); // Limiter la largeur du layout pour garder la fenêtre petite

        Scene scene = new Scene(layout);
        alertStage.setScene(scene);
        alertStage.showAndWait();
    }

    public TextField getCodeField(){
        return this.codeField;
    }

    public Button getCloseButton(){
        return(this.closeButton);
    }
}
