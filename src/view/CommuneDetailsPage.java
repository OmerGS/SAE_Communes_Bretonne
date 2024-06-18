package view;

import data.Commune;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import controller.Controller;

public class CommuneDetailsPage {
    private static VBox detailsBox;
    private static FlowPane neighborsPane;
    private static ComboBox<Integer> yearsComboBox;
    private static Label nameLabel;
    private static Stage detailsStage;
    private static TitledPane generalInfoPane;
    private static TitledPane housingStatsPane;
    
    public static void showCommune(Commune commune, Controller controller) {
        detailsStage = new Stage();
        detailsStage.setTitle("D\u00e9tails de la commune");

        detailsBox = new VBox(20);
        detailsBox.setPadding(new Insets(20));
        detailsBox.setAlignment(Pos.TOP_CENTER);
        detailsBox.setStyle("-fx-background-color: linear-gradient(to right, #E0F7FA 0%, #0288D1 100%);");

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

        nameLabel = new Label(commune.getNomCommune());
        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white; -fx-effect: dropshadow(one-pass-box, black, 8, 0.0, 2, 0);");
        namePane.getChildren().add(nameLabel);

        // Create TitledPanes for general information and housing statistics
        generalInfoPane = createTitledPane("Informations g\u00e9n\u00e9rales", createGeneralInfoBox(commune));
        housingStatsPane = createTitledPane("Statistiques de logement", createHousingStatsBox(commune));

        updateGeneralInfoBox(commune);
        updateHousingStatsBox(commune);

        // Section neighboring communes
        Label neighborsLabel = new Label("Communes voisines:");
        neighborsLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        neighborsPane = new FlowPane();
        neighborsPane.setHgap(10);
        neighborsPane.setVgap(10);
        neighborsPane.setAlignment(Pos.CENTER);

        updateNeighborsPane(commune, controller);

        // Create ComboBox containing years
        yearsComboBox = createYearsComboBox(commune, controller);
        yearsComboBox.setStyle("-fx-max-width: 150px; -fx-font-size: 14px;");

        Button closeButton = new Button("Fermer");
        closeButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-padding: 10px 20px; -fx-font-size: 14px; -fx-cursor: hand;");
        closeButton.setOnAction(event -> detailsStage.close());

        detailsBox.getChildren().addAll(
            namePane, generalInfoPane, housingStatsPane, neighborsLabel, neighborsPane,
            yearsComboBox, closeButton
        );

        ScrollPane scrollPane = new ScrollPane(detailsBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Scene detailsScene = new Scene(scrollPane, 700, 800);
        detailsStage.setScene(detailsScene);
        detailsStage.initModality(Modality.APPLICATION_MODAL);
        detailsStage.showAndWait();
    }

    private static TitledPane createTitledPane(String title, VBox content) {
        TitledPane pane = new TitledPane();
        pane.setText(title);
        pane.setContent(content);
        pane.setStyle("-fx-font-size: 16px;");
        pane.setMaxWidth(Double.MAX_VALUE);
        return pane;
    }

    private static VBox createGeneralInfoBox(Commune commune) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setAlignment(Pos.CENTER_LEFT);
    
        Label idLabel = new Label("ID : ");
        idLabel.setStyle("-fx-font-size: 14px;");
    
        Label depLabel = new Label("D\u00e9partement : ");
        depLabel.setStyle("-fx-font-size: 14px;");
    
        Label anneeLabel = new Label("Ann\u00e9e de donn\u00e9es : ");
        anneeLabel.setStyle("-fx-font-size: 14px;");
        
        Label populationLabel = new Label("Population : ");
        populationLabel.setStyle("-fx-font-size: 14px;");
        
        Label importanteLabel = new Label("Importante : ");
        importanteLabel.setStyle("-fx-font-size: 14px;");
    
        Label depCulturellesLabel = new Label("D\u00e9penses culturelles totales : ");
        depCulturellesLabel.setStyle("-fx-font-size: 14px;");
    
        Label gareLabel = new Label("Gare : ");
        depCulturellesLabel.setStyle("-fx-font-size: 14px;");
        
        box.getChildren().addAll(idLabel, depLabel, populationLabel, importanteLabel, anneeLabel, depCulturellesLabel, gareLabel);
        return box;
    }
    
    private static VBox createHousingStatsBox(Commune commune) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setAlignment(Pos.CENTER_LEFT);
    
        Label nbMaisonLabel = new Label("Nombre de maisons : ");
        nbMaisonLabel.setStyle("-fx-font-size: 14px;");
        
        Label nbAppartLabel = new Label("Nombre d'appartements : ");
        nbAppartLabel.setStyle("-fx-font-size: 14px;");
        
        Label prixMoyenLabel = new Label("Prix moyen : ");
        prixMoyenLabel.setStyle("-fx-font-size: 14px;");
        
        Label prixM2MoyenLabel = new Label("Prix moyen par \u33A1 : ");
        prixM2MoyenLabel.setStyle("-fx-font-size: 14px;");
        
        Label surfaceMoyLabel = new Label("Surface moyenne : ");
        surfaceMoyLabel.setStyle("-fx-font-size: 14px;");
    
        box.getChildren().addAll(nbMaisonLabel, nbAppartLabel, prixMoyenLabel, prixM2MoyenLabel, surfaceMoyLabel);
        return box;
    }
    


    private static void updateGeneralInfoBox(Commune commune) {
        ((VBox) generalInfoPane.getContent()).getChildren().forEach(node -> {
            if (node instanceof Label) {
                String labelText = ((Label) node).getText();
                if (labelText.startsWith("ID : ")) {
                    ((Label) node).setText("ID : " + commune.getIdCommune());
                } else if (labelText.startsWith("Population : ")) {
                    ((Label) node).setText("Population : " + commune.getPopulation() + " habitants");
                    if(commune.getPopulation() < 0){
                        ((Label) node).setText("Population : Information indisponible");
                    }else{
                        ((Label) node).setText("Population : " + commune.getPopulation() + " habitants");
                    }
                } else if (labelText.startsWith("Importante : ")) {
                    ((Label) node).setText("Importante : " + (commune.isMostImportant() ? "Oui" : "Non"));
                } else if (labelText.startsWith("Ann\u00e9e de donn\u00e9es : ")) {
                    ((Label) node).setText("Ann\u00e9e de donn\u00e9es : " + commune.getAnnee().getAnnee());
                } else if (labelText.startsWith("D\u00e9penses culturelles totales : ")) {
                    if(commune.getDepCulturellesTotales() < 0){
                        ((Label) node).setText("D\u00e9penses culturelles totales : Information indisponible");
                    }else{
                        ((Label) node).setText("D\u00e9penses culturelles totales : " + commune.getDepCulturellesTotales() + " \u20ac");
                    }
                } else if (labelText.startsWith("D\u00e9partement : ")) {
                    ((Label) node).setText("D\u00e9partement : " + commune.getDepartement().getIdDep());
                }else if (labelText.startsWith("Gare : ")) {
                    if(commune.aUneGare()){
                        ((Label) node).setText("Gare : " + commune.getGare().getNomGare());
                    }else{
                        ((Label) node).setText("Gare : Aucune");
                    }
                }
            }
        });
    }

    private static void updateHousingStatsBox(Commune commune) {
        ((VBox) housingStatsPane.getContent()).getChildren().forEach(node -> {
            if (node instanceof Label) {
                String labelText = ((Label) node).getText();
                if (labelText.startsWith("Nombre de maisons : ")) {
                    ((Label) node).setText("Nombre de maisons : " + commune.getNbMaison());
                } else if (labelText.startsWith("Nombre d'appartements : ")) {
                    ((Label) node).setText("Nombre d'appartements : " + commune.getNbAppart());
                } else if (labelText.startsWith("Prix moyen : ")) {
                    if(commune.getPrixMoyen() < 0){
                        ((Label) node).setText("Prix moyen : Information indisponible");
                    }else{
                        ((Label) node).setText("Prix moyen : " + commune.getPrixMoyen() +  " \u20ac");
                    }
                } else if (labelText.startsWith("Prix moyen par \u33A1 : ")) {
                    if(commune.getPrixM2Moyen() < 0){
                        ((Label) node).setText("Prix moyen par \u33A1 : Information indisponible");
                    }else{
                        ((Label) node).setText("Prix moyen par \u33A1 : " + commune.getPrixM2Moyen() +  " \u20ac");
                    }
                } else if (labelText.startsWith("Surface moyenne : ")) {
                    if(commune.getSurfaceMoy() < 0){
                        ((Label) node).setText("Surface moyenne : Information indisponible");
                    }else{
                        ((Label) node).setText("Surface moyenne : " + commune.getSurfaceMoy() +  " \u20ac");
                    }
                }
            }
        });
    }

    private static void updateNeighborsPane(Commune commune, Controller controller) {
        neighborsPane.getChildren().clear();
        ArrayList<Commune> neighbors = commune.getCommunesVoisines();
        if (neighbors.isEmpty()) {
            neighborsPane.getChildren().add(new Label("Aucune commune voisine."));
        } else {
            for (Commune neighbor : neighbors) {
                Button neighborButton = new Button(neighbor.getNomCommune());
                neighborButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-padding: 10px 20px; -fx-font-size: 14px; -fx-cursor: hand;");
                neighborButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-padding: 10px 20px; -fx-font-size: 14px; -fx-cursor: hand;");
                neighborButton.setOnAction(event -> showCommune(neighbor, controller));
                neighborsPane.getChildren().add(neighborButton);
            }
        }
    }

    private static ComboBox<Integer> createYearsComboBox(Commune commune, Controller controller) {
        yearsComboBox = new ComboBox<>();

        ArrayList<Integer> years = controller.getYearsForCommune(commune);

        for (int year : years) {
            yearsComboBox.getItems().add(year);
        }

        yearsComboBox.setValue(commune.getAnnee().getAnnee());

        yearsComboBox.setOnAction(event -> {
            int selectedYear = yearsComboBox.getValue();

            Commune selectedCommune = controller.getCommuneForYearAndCommune(commune.getNomCommune(), selectedYear);
            updateCommuneDetails(selectedCommune, controller);
        });

        yearsComboBox.setPromptText("S\u00e9lectionnez une ann\u00e9e");
        yearsComboBox.setMaxWidth(Double.MAX_VALUE);

        return yearsComboBox;
    }


    public static void updateCommuneDetails(Commune commune, Controller controller) {
        nameLabel.setText(commune.getNomCommune());
        updateNeighborsPane(commune, controller);
        updateGeneralInfoBox(commune);
        updateHousingStatsBox(commune);
    }

}