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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
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

    public Label getNameLabel() {
        return nameLabel;
    }

    private Stage detailsStage;

    private TitledPane generalInfoPane;
    private TitledPane housingStatsPane;

    private TextField nbMaisonsTextField;
    private TextField nbAppartTextField;
    private TextField prixMoyenTextField;
    private TextField prixM2MoyenTextField;
    private TextField surfaceMoyenneTextField;

    private TextField anneeTextField;
    private TextField populationTextField;
    private TextField depCulturellesTextField;
    private TextField budgetTotalField;

    private Label importanteLabel;
    private Label idRepLabel;
    private Label depRepLabel;
    private Label importanteRepLabel;
    private Label gareRepLabel;

    public Label getImportanteLabel() {
        return importanteLabel;
    }

    public Label getIdRepLabel() {
        return idRepLabel;
    }

    public Label getDepRepLabel() {
        return depRepLabel;
    }

    public Label getImportanteRepLabel() {
        return importanteRepLabel;
    }

    public Label getGareRepLabel() {
        return gareRepLabel;
    }

    private Commune communeAvantModif;

    public Commune getCommuneAvantModif() {
        return communeAvantModif;
    }

    public TextField getBudgetTotalField() {
        return budgetTotalField;
    }

    private Button saveButton;

    public void showCommune(Commune commune, Controller controller) {
        this.communeAvantModif = commune;

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
        saveButton.setStyle("-fx-background-color: #5F3384; -fx-text-fill: white; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-padding: 10px 20px; -fx-font-size: 14px; -fx-cursor: hand;");

        Button closeButton = new Button("Fermer");
        closeButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-padding: 10px 20px; -fx-font-size: 14px; -fx-cursor: hand;");
        closeButton.setOnAction(event -> detailsStage.close());

        this.saveButton.setOnAction(controller);


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
        box.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        Label idLabel = new Label("ID : ");
        idLabel.setStyle("-fx-font-size: 14px;");
        this.idRepLabel = new Label("" + commune.getIdCommune());

        Label depLabel = new Label("D\u00e9partement : ");
        depLabel.setStyle("-fx-font-size: 14px;");
        depRepLabel = new Label("" + commune.getDepartement().getIdDep());

        Label anneeLabel = new Label("Ann\u00e9e de donn\u00e9es : ");
        anneeLabel.setStyle("-fx-font-size: 14px;");
        anneeTextField = new TextField();

        Label populationLabel = new Label("Population : ");
        populationLabel.setStyle("-fx-font-size: 14px;");
        populationTextField = new TextField();


        importanteLabel = new Label("Importante : ");
        importanteRepLabel = new Label("" + commune.isMostImportant());

        Label depCulturellesLabel = new Label("D\u00e9penses culturelles totales : ");
        depCulturellesLabel.setStyle("-fx-font-size: 14px;");
        depCulturellesTextField = new TextField();

        
        Label budgetTotal = new Label("Budget Total : ");
        budgetTotal.setStyle("-fx-font-size: 14px;");
        budgetTotalField = new TextField();

        Label gareLabel = new Label("Gare : ");
        gareLabel.setStyle("-fx-font-size: 14px;");
        if (commune.aUneGare()) {
            gareRepLabel = new Label("" + commune.getGare().getNomGare());
        } else {
            gareRepLabel = new Label("Aucune");
        }



        grid.add(idLabel, 0, 0);
        grid.add(idRepLabel, 1, 0);
        grid.add(depLabel, 0, 1);
        grid.add(depRepLabel, 1, 1);
        grid.add(anneeLabel, 0, 2);
        grid.add(anneeTextField, 1, 2);
        grid.add(populationLabel, 0, 3);
        grid.add(populationTextField, 1, 3);
        grid.add(importanteLabel, 0, 4);
        grid.add(importanteRepLabel, 1, 4);
        grid.add(depCulturellesLabel, 0, 5);
        grid.add(depCulturellesTextField, 1, 5);
        grid.add(budgetTotal, 0, 6);
        grid.add(budgetTotalField, 1, 6);
        grid.add(gareLabel, 0, 7);
        grid.add(gareRepLabel, 1, 7);

        box.getChildren().addAll(grid);
        return box;
    }
    
    private VBox createHousingStatsBox(Commune commune) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setAlignment(Pos.CENTER_LEFT);
        box.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        Label nbMaisonLabel = new Label("Nombre de maisons : ");
        nbMaisonLabel.setStyle("-fx-font-size: 14px;");
        nbMaisonsTextField = new TextField(); // Initialisation du TextField

        Label nbAppartLabel = new Label("Nombre d'appartements : ");
        nbAppartLabel.setStyle("-fx-font-size: 14px;");
        nbAppartTextField = new TextField(); // Initialisation du TextField

        Label prixMoyenLabel = new Label("Prix moyen : ");
        prixMoyenLabel.setStyle("-fx-font-size: 14px;");
        prixMoyenTextField = new TextField(); // Initialisation du TextField

        Label prixM2MoyenLabel = new Label("Prix moyen par \u33A1 : ");
        prixM2MoyenLabel.setStyle("-fx-font-size: 14px;");
        prixM2MoyenTextField = new TextField(); // Initialisation du TextField

        Label surfaceMoyLabel = new Label("Surface moyenne : ");
        surfaceMoyLabel.setStyle("-fx-font-size: 14px;");
        surfaceMoyenneTextField = new TextField(); // Initialisation du TextField

        grid.add(nbMaisonLabel, 0, 0);
        grid.add(nbMaisonsTextField, 1, 0);
        grid.add(nbAppartLabel, 0, 1);
        grid.add(nbAppartTextField, 1, 1);
        grid.add(prixMoyenLabel, 0, 2);
        grid.add(prixMoyenTextField, 1, 2);
        grid.add(prixM2MoyenLabel, 0, 3);
        grid.add(prixM2MoyenTextField, 1, 3);
        grid.add(surfaceMoyLabel, 0, 4);
        grid.add(surfaceMoyenneTextField, 1, 4);

        box.getChildren().add(grid);
        return box;
    }
    
    


    private void updateGeneralInfoBox(Commune commune) {
        if (commune.getPopulation() < 0) {
            populationTextField.setText("Information indisponible");
        } else {
            populationTextField.setText(commune.getPopulation() + "");
        }
        anneeTextField.setText("" + commune.getAnnee().getAnnee());
        if (commune.getDepCulturellesTotales() < 0) {
            depCulturellesTextField.setText("Information indisponible");
        } else {
            depCulturellesTextField.setText(commune.getDepCulturellesTotales() + "");
        }
        if (commune.getBudgetTotal() < 0) {
            budgetTotalField.setText("Information indisponible");
        } else {
            budgetTotalField.setText(commune.getBudgetTotal() + "");
        }

        importanteRepLabel.setText((commune.isMostImportant() ? "Oui" : "Non"));
        

    }
    

    private void updateHousingStatsBox(Commune commune) {
        nbMaisonsTextField.setText(String.valueOf(commune.getNbMaison()));
        nbAppartTextField.setText(String.valueOf(commune.getNbAppart()));

        if (commune.getPrixMoyen() < 0) {
            prixMoyenTextField.setText("Information indisponible");
        } else {
            prixMoyenTextField.setText(commune.getPrixMoyen() + "");
        }

        if (commune.getPrixM2Moyen() < 0) {
            prixM2MoyenTextField.setText("Information indisponible");
        } else {
            prixM2MoyenTextField.setText(commune.getPrixM2Moyen() + "");
        }

        if (commune.getSurfaceMoy() < 0) {
            surfaceMoyenneTextField.setText("Information indisponible");
        } else {
            surfaceMoyenneTextField.setText(commune.getSurfaceMoy() + "");
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

    public String getAnneeTextFieldValue() {
        return anneeTextField.getText();
    }

    public String getPopulationTextFieldValue() {
        return populationTextField.getText();
    }

    public String getDepCulturellesTextFieldValue() {
        return depCulturellesTextField.getText();
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