package view; 

import data.Commune;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.mail.event.MailEvent;

import controller.Controller;

/**
 * CommuneDetailsPage is a class that displays the details of a specific commune.
 * @autor O.Gunes
 */
public class CommuneDetailsPage {
    private CommuneDetailsPage() {
    }

    /**
     * This method displays a window with the details of the commune and its neighbors.
     *
     * @param commune The commune whose details are to be displayed.
     */
    public static void showCommune(Commune commune, Controller controller) {
        Stage detailsStage = new Stage();
        detailsStage.setTitle("Détails de la commune");

        VBox detailsBox = new VBox(20);
        detailsBox.setPadding(new Insets(20));
        detailsBox.setAlignment(Pos.TOP_CENTER);

        // Load random background image
        File folder = new File("../resources/image/fonds/");
        File[] listOfFiles = folder.listFiles();
        Random rand = new Random();
        int randomIndex = rand.nextInt(listOfFiles.length);
        Image backgroundImage = new Image(listOfFiles[randomIndex].toURI().toString());
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(400);
        backgroundImageView.setPreserveRatio(true);

        // Section commune name with background
        StackPane namePane = new StackPane();
        namePane.setAlignment(Pos.CENTER);
        namePane.setPadding(new Insets(20));
        namePane.setStyle("-fx-background-image: url('" + listOfFiles[randomIndex].toURI().toString() + "'); -fx-background-size: cover;");

        Label nameLabel = new Label(commune.getNomCommune());
        nameLabel.getStyleClass().add("name-label");
        namePane.getChildren().add(nameLabel);

        // Section commune details
        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(10);
        infoGrid.setVgap(10);
        infoGrid.setAlignment(Pos.CENTER);

        String[] infoTexts = {
            "ID: " + commune.getIdCommune(),
            "Nombre de maisons: " + commune.getNbMaison(),
            "Nombre d'appartements: " + commune.getNbAppart(),
            "Prix moyen: " + commune.getPrixMoyen(),
            "Prix moyen par m²: " + commune.getPrixM2Moyen(),
            "Surface moyenne: " + commune.getSurfaceMoy(),
            "Dépenses culturelles totales: " + commune.getDepCulturellesTotales(),
            "Population: " + commune.getPopulation(),
            "Importante: " + (commune.isMostImportant() ? "Oui" : "Non"),
            "Données recoltées en : " + commune.getlAnnee(),
        };

        for (int i = 0; i < infoTexts.length; i++) {
            VBox infoBox = createInfoBox(infoTexts[i]);
            infoGrid.add(infoBox, i % 2, i / 2);
        }

        // Section neighboring communes
        Label neighborsLabel = new Label("Communes voisines:");
        neighborsLabel.getStyleClass().add("section-label");
        
        FlowPane neighborsPane = new FlowPane();
        neighborsPane.setHgap(10);
        neighborsPane.setVgap(10);
        neighborsPane.setAlignment(Pos.CENTER);

        ArrayList<Commune> neighbors = commune.getCommunesVoisines();
        if (neighbors.isEmpty()) {
            neighborsPane.getChildren().add(new Label("Aucune commune voisine."));
        } else {
            for (Commune neighbor : neighbors) {
                Button neighborButton = new Button(neighbor.getNomCommune());
                neighborButton.getStyleClass().add("neighbor-button");
                neighborButton.setOnAction(event -> showCommune(neighbor, controller));
                neighborsPane.getChildren().add(neighborButton);
            }
        }

        // Create ComboBox containing years
        ComboBox<Integer> yearsComboBox = createYearsComboBox(commune, controller);

        Button closeButton = new Button("Fermer");
        closeButton.getStyleClass().add("close-button");
        closeButton.setOnAction(event -> detailsStage.close());

        detailsBox.getChildren().addAll(
                namePane, infoGrid, neighborsLabel, neighborsPane, yearsComboBox, closeButton
        );

        ScrollPane scrollPane = new ScrollPane(detailsBox);
        scrollPane.setFitToWidth(true);

        Scene detailsScene = new Scene(scrollPane, 600, 700);
        detailsScene.getStylesheets().add("file:../resources/css/CommuneDetailsPage.css");
        detailsStage.setScene(detailsScene);
        detailsStage.initModality(Modality.APPLICATION_MODAL);
        detailsStage.showAndWait();
    }

    /**
    * Create the InfoBox. 
    * @param infoText The text of the InfoBox.
    * @return The box.
    */
    private static VBox createInfoBox(String infoText) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(5));  // Reduced padding
        box.setMinSize(100, 50);  // Reduced size
        box.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: #f0f0f0;");
        Label label = new Label(infoText);
        label.getStyleClass().add("detail-label");
        box.getChildren().add(label);
        return box;
    }

    /**
     * Create a ComboBox containing the years for which data is available for the given commune.
     *
     * @param commune The commune for which to create the ComboBox.
     * @return The ComboBox containing the years.
     */
    private static ComboBox<Integer> createYearsComboBox(Commune commune, Controller controller) {
        ComboBox<Integer> yearsComboBox = new ComboBox<>();

        // Get the years for the commune
        ArrayList<Integer> years = controller.getYearsForCommune(commune);








        

        /* ---- A NETTOYER ET A METTRE LE EVENT DANS LE CONTROLLER ---- */
        //! TODO




        // Add the years to the ComboBox
        for (int year : years) {
            yearsComboBox.getItems().add(year);
        }

        // Set default selection to the first year, if available
        if (years.size() > 0) {
            yearsComboBox.setValue(commune.getlAnnee());
        }


        yearsComboBox.setOnAction(event -> {
            int selectedYear = yearsComboBox.getValue();
            // Get commune details for the selected year
            Commune selectedCommune = controller.getCommuneForYearAndCommune(commune.getNomCommune(), selectedYear);
            CommuneDetailsPage.showCommune(selectedCommune, controller);
        });

        // Style the ComboBox as needed
        yearsComboBox.setPromptText("Sélectionnez une année");
        yearsComboBox.setMaxWidth(Double.MAX_VALUE);

        return yearsComboBox;
    }
}
