package view;

import data.Commune;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * CommuneDetailsPage is a class that displays the details of a specific commune.
 */
public class CommuneDetailsPage {
    private CommuneDetailsPage() {
        // Constructor privé pour éviter l'instanciation
    }

    /**
     * This method displays a window with the details of the commune and its neighbors
     *
     * @param commune The commune whose details are to be displayed
     */
    public static void showCommune(Commune commune) {
        Stage detailsStage = new Stage();
        detailsStage.setTitle("Détails de la commune");

        VBox detailsBox = new VBox(10);
        detailsBox.setPadding(new Insets(10));
        detailsBox.setAlignment(Pos.CENTER);

        // Section commune details
        Label nameLabel = new Label("Nom: " + commune.getNomCommune());
        Label idLabel = new Label("ID: " + commune.getIdCommune());
        Label housesLabel = new Label("Nombre de maisons: " + commune.getNbMaison());
        Label apartmentsLabel = new Label("Nombre d'appartements: " + commune.getNbAppart());
        Label avgPriceLabel = new Label("Prix moyen: " + commune.getPrixMoyen());
        Label avgPricePerSqMLabel = new Label("Prix moyen par m²: " + commune.getPrixM2Moyen());
        Label avgSurfaceLabel = new Label("Surface moyenne: " + commune.getSurfaceMoy());
        Label totalCulturalExpensesLabel = new Label("Dépenses culturelles totales: " + commune.getDepCulturellesTotales());
        Label isImportantLabel = new Label("Importante: " + (commune.isMostImportant() ? "Oui" : "Non"));
        Label populationLabel = new Label("Population: " + commune.getPopulation());

        // Section neighboring communes
        Label neighborsLabel = new Label("Communes voisines:");
        VBox neighborsBox = new VBox(5);
        neighborsBox.setPadding(new Insets(10));
        neighborsBox.setAlignment(Pos.CENTER);

        ArrayList<Commune> neighbors = commune.getCommunesVoisines();
        if (neighbors.isEmpty()) {
            neighborsBox.getChildren().add(new Label("Aucune commune voisine."));
        } else {
            for (Commune neighbor : neighbors) {
                neighborsBox.getChildren().add(new Label(neighbor.getNomCommune()));
            }
        }

        Button closeButton = new Button("Fermer");
        closeButton.setOnAction(event -> detailsStage.close());

        detailsBox.getChildren().addAll(
                nameLabel, idLabel, housesLabel, apartmentsLabel, avgPriceLabel,
                avgPricePerSqMLabel, avgSurfaceLabel, totalCulturalExpensesLabel,
                populationLabel, isImportantLabel, neighborsLabel, neighborsBox, closeButton
        );

        Scene detailsScene = new Scene(detailsBox, 400, 500);
        detailsStage.setScene(detailsScene);
        detailsStage.initModality(Modality.APPLICATION_MODAL);
        detailsStage.showAndWait();
    }
}