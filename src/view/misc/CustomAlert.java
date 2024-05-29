package view.misc;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CustomAlert {

    public static void showAlert(String title, String header, String content) {
        Stage alertStage = new Stage();
        alertStage.initModality(Modality.APPLICATION_MODAL);
        alertStage.setTitle(title);
        alertStage.setResizable(false); // Rendre la fenêtre immuable
        alertStage.setMaximized(false); // Empêcher le plein écran

        Label headerLabel = new Label(header);
        headerLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");

        Label contentLabel = new Label(content);
        contentLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495E;");
        contentLabel.setWrapText(true); // Permettre l'habillage du texte

        Button closeButton = new Button("OK");
        closeButton.setStyle("-fx-background-color: #FF512F; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-background-radius: 5px;");
        closeButton.setOnAction(e -> alertStage.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(headerLabel, contentLabel, closeButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setMaxWidth(280); // Limiter la largeur du layout pour garder la fenêtre petite

        Scene scene = new Scene(layout);
        alertStage.setScene(scene);
        alertStage.showAndWait();
    }

    // Méthode surchargée pour afficher l'alerte sans en-tête
    public static void showAlert(String title, String content) {
        Stage alertStage = new Stage();
        alertStage.initModality(Modality.APPLICATION_MODAL);
        alertStage.setTitle(title);
        alertStage.setResizable(false); // Rendre la fenêtre immuable
        alertStage.setMaximized(false); // Empêcher le plein écran

        Label contentLabel = new Label(content);
        contentLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495E;");
        contentLabel.setWrapText(true); // Permettre l'habillage du texte

        Button closeButton = new Button("OK");
        closeButton.setStyle("-fx-background-color: #FF007F; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-background-radius: 5px;");
        closeButton.setOnAction(e -> alertStage.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(contentLabel, closeButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setMaxWidth(280); // Limiter la largeur du layout pour garder la fenêtre petite

        Scene scene = new Scene(layout);
        alertStage.setScene(scene);
        alertStage.showAndWait();
    }
}