package view;

import data.Commune;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
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

public class CommuneDetailsModifPage {
    private VBox detailsBox;
    private FlowPane neighborsPane;
    private ComboBox<Integer> yearsComboBox;
    private Label nameLabel;
    private Stage detailsStage;

    private TitledPane generalInfoPane;
    private TitledPane housingStatsPane;

    private TextField nbMaisonsTextField;
    private TextField nbAppartTextField;
    private TextField prixMoyenTextField;
    private TextField prixM2MoyenTextField;
    private TextField surfaceMoyenneTextField;


    private TextField idTextField;
    private TextField departementTextField;
    private TextField anneeTextField;
    private TextField populationTextField;
    private TextField depCulturellesTextField;
    private TextField gareTextField;

    private Button saveButton;

    public void showCommune(Commune commune, Controller controller) {
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
        saveButton = new Button("Sauvegarder");
        Button closeButton = new Button("Fermer");
        closeButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-padding: 10px 20px; -fx-font-size: 14px; -fx-cursor: hand;");
        closeButton.setOnAction(event -> detailsStage.close());

        detailsBox.getChildren().addAll(
            namePane, generalInfoPane, housingStatsPane, neighborsLabel, neighborsPane,
            yearsComboBox, saveButton, closeButton
        );

        ScrollPane scrollPane = new ScrollPane(detailsBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Scene detailsScene = new Scene(scrollPane, 700, 800);
        detailsStage.setScene(detailsScene);
        detailsStage.initModality(Modality.APPLICATION_MODAL);
        detailsStage.showAndWait();
    }

    private TitledPane createTitledPane(String title, VBox content) {
        TitledPane pane = new TitledPane();
        pane.setText(title);
        pane.setContent(content);
        pane.setStyle("-fx-font-size: 16px;");
        pane.setMaxWidth(Double.MAX_VALUE);
        return pane;
    }

    private VBox createGeneralInfoBox(Commune commune) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setAlignment(Pos.CENTER_LEFT);

        Label idLabel = new Label("ID : ");
        idLabel.setStyle("-fx-font-size: 14px;");
        idTextField = new TextField();
        idTextField.setEditable(false); // Le TextField ID est non modifiable
        StackPane idPane = new StackPane(idLabel, idTextField);

        Label depLabel = new Label("Département : ");
        depLabel.setStyle("-fx-font-size: 14px;");
        departementTextField = new TextField();
        departementTextField.setEditable(false);
        StackPane depPane = new StackPane(depLabel, departementTextField);

        Label anneeLabel = new Label("Année de données : ");
        anneeLabel.setStyle("-fx-font-size: 14px;");
        anneeTextField = new TextField();
        anneeTextField.setEditable(false);
        StackPane anneePane = new StackPane(anneeLabel, anneeTextField);

        Label populationLabel = new Label("Population : ");
        populationLabel.setStyle("-fx-font-size: 14px;");
        populationTextField = new TextField();
        populationTextField.setEditable(false);
        StackPane populationPane = new StackPane(populationLabel, populationTextField);

        Label importanteLabel = new Label("Importante : ");

        Label depCulturellesLabel = new Label("Dépenses culturelles totales : ");
        depCulturellesLabel.setStyle("-fx-font-size: 14px;");
        depCulturellesTextField = new TextField();
        depCulturellesTextField.setEditable(false);
        StackPane depCulturellesPane = new StackPane(depCulturellesLabel, depCulturellesTextField);

        Label gareLabel = new Label("Gare : ");
        gareLabel.setStyle("-fx-font-size: 14px;");
        gareTextField = new TextField();
        gareTextField.setEditable(false);
        StackPane garePane = new StackPane(gareLabel, gareTextField);

        box.getChildren().addAll(idPane, depPane, populationPane, importanteLabel, anneePane, depCulturellesPane, garePane);
        return box;
    }
    
    private VBox createHousingStatsBox(Commune commune) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setAlignment(Pos.CENTER_LEFT);

        Label nbMaisonLabel = new Label("Nombre de maisons : ");
        nbMaisonLabel.setStyle("-fx-font-size: 14px;");
        nbMaisonsTextField = new TextField(); // Initialisation du TextField
        HBox nbMaisonsBox = new HBox(10, nbMaisonLabel, nbMaisonsTextField);
        box.getChildren().add(nbMaisonsBox);

        Label nbAppartLabel = new Label("Nombre d'appartements : ");
        nbAppartLabel.setStyle("-fx-font-size: 14px;");
        nbAppartTextField = new TextField(); // Initialisation du TextField
        HBox nbAppartBox = new HBox(10, nbAppartLabel, nbAppartTextField);
        box.getChildren().add(nbAppartBox);

        Label prixMoyenLabel = new Label("Prix moyen : ");
        prixMoyenLabel.setStyle("-fx-font-size: 14px;");
        prixMoyenTextField = new TextField(); // Initialisation du TextField
        HBox prixMoyenBox = new HBox(10, prixMoyenLabel, prixMoyenTextField);
        box.getChildren().add(prixMoyenBox);

        Label prixM2MoyenLabel = new Label("Prix moyen par \u33A1 : ");
        prixM2MoyenLabel.setStyle("-fx-font-size: 14px;");
        prixM2MoyenTextField = new TextField(); // Initialisation du TextField
        HBox prixM2MoyenBox = new HBox(10, prixM2MoyenLabel, prixM2MoyenTextField);
        box.getChildren().add(prixM2MoyenBox);

        Label surfaceMoyLabel = new Label("Surface moyenne : ");
        surfaceMoyLabel.setStyle("-fx-font-size: 14px;");
        surfaceMoyenneTextField = new TextField(); // Initialisation du TextField
        HBox surfaceMoyenneBox = new HBox(10, surfaceMoyLabel, surfaceMoyenneTextField);
        box.getChildren().add(surfaceMoyenneBox);

        return box;
    }
    
    


    private void updateGeneralInfoBox(Commune commune) {
        idTextField.setText("ID : " + commune.getIdCommune());
        departementTextField.setText("Département : " + commune.getDepartement().getIdDep());
        if (commune.getPopulation() < 0) {
            populationTextField.setText("Population : Information indisponible");
        } else {
            populationTextField.setText("Population : " + commune.getPopulation() + " habitants");
        }
        anneeTextField.setText("Année de données : " + commune.getAnnee().getAnnee());
        if (commune.getDepCulturellesTotales() < 0) {
            depCulturellesTextField.setText("Dépenses culturelles totales : Information indisponible");
        } else {
            depCulturellesTextField.setText("Dépenses culturelles totales : " + commune.getDepCulturellesTotales() + " €");
        }
        if (commune.aUneGare()) {
            gareTextField.setText("Gare : " + commune.getGare().getNomGare());
        } else {
            gareTextField.setText("Gare : Aucune");
        }
    }
    

    private void updateHousingStatsBox(Commune commune) {
        nbMaisonsTextField.setText(String.valueOf(commune.getNbMaison()));
        nbAppartTextField.setText(String.valueOf(commune.getNbAppart()));

        if (commune.getPrixMoyen() < 0) {
            prixMoyenTextField.setText("Information indisponible");
        } else {
            prixMoyenTextField.setText(commune.getPrixMoyen() + " €");
        }

        if (commune.getPrixM2Moyen() < 0) {
            prixM2MoyenTextField.setText("Information indisponible");
        } else {
            prixM2MoyenTextField.setText(commune.getPrixM2Moyen() + " €");
        }

        if (commune.getSurfaceMoy() < 0) {
            surfaceMoyenneTextField.setText("Information indisponible");
        } else {
            surfaceMoyenneTextField.setText(commune.getSurfaceMoy() + " m²");
        }
    }

    private void updateNeighborsPane(Commune commune, Controller controller) {
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

    private ComboBox<Integer> createYearsComboBox(Commune commune, Controller controller) {
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


    public void updateCommuneDetails(Commune commune, Controller controller) {
        nameLabel.setText(commune.getNomCommune());
        updateNeighborsPane(commune, controller);
        updateGeneralInfoBox(commune);
        updateHousingStatsBox(commune);
    }


    public String getIdTextFieldValue() {
        return idTextField.getText();
    }

    public String getDepartementTextFieldValue() {
        return departementTextField.getText();
    }

    public String getAnneeTextFieldValue() {
        return anneeTextField.getText();
    }

    public String getPopulationTextFieldValue() {
        return populationTextField.getText();
    }

    public String getDepCulturellesTextFieldValue() {
        return depCulturellesTextField.getText();
    }

    public String getGareTextFieldValue() {
        return gareTextField.getText();
    }

    // Méthodes pour récupérer les valeurs des TextField
    public String getNbMaisonsText() {
        return nbMaisonsTextField.getText();
    }

    public String getNbAppartementsText() {
        return nbAppartTextField.getText();
    }

    public String getPrixMoyenText() {
        return prixMoyenTextField.getText();
    }

    public String getPrixM2MoyenText() {
        return prixM2MoyenTextField.getText();
    }

    public String getSurfaceMoyenneText() {
        return surfaceMoyenneTextField.getText();
    }

    public Button getSaveButton(){
        return this.saveButton;
    }

}