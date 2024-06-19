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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

import controller.Controller;

public class CommuneCreatePage {
    private VBox detailsBox;
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

    private TextField nomTextField;
    private TextField idTextField;
    private ComboBox<String> departementComboBox;
    private TextField anneeTextField;
    private TextField populationTextField;
    private TextField depCulturellesTextField;
    private TextField budgetTotalField;

    private Commune communeAvantModif;

    public Commune getCommuneAvantModif() {
        return communeAvantModif;
    }

    public TextField getBudgetTotalField() {
        return budgetTotalField;
    }

    private Button saveButton;


    public void showCommune(Controller controller) {

        detailsStage = new Stage();
        detailsStage.setTitle("Création de la commune");

        detailsBox = new VBox(20);
        detailsBox.setPadding(new Insets(20));
        detailsBox.setAlignment(Pos.TOP_CENTER);
        detailsBox.setStyle("-fx-background-color: linear-gradient(to right, #E0F7FA 0%, #0288D1 100%);");




        // Create TitledPanes for general information and housing statistics
        generalInfoPane = createTitledPane("Informations g\u00e9n\u00e9rales", createGeneralInfoBox(controller));
        housingStatsPane = createTitledPane("Statistiques de logement", createHousingStatsBox());


        saveButton = new Button("Sauvegarder");
        Button closeButton = new Button("Fermer");
        closeButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-padding: 10px 20px; -fx-font-size: 14px; -fx-cursor: hand;");
        closeButton.setOnAction(event -> detailsStage.close());

        this.saveButton.setOnAction(controller);


        detailsBox.getChildren().addAll(
            generalInfoPane, housingStatsPane,
            saveButton, closeButton
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

    private VBox createGeneralInfoBox(Controller controller) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setAlignment(Pos.CENTER_LEFT);
        box.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        Label nomLabel = new Label("Nom de Ville : ");
        nomLabel.setStyle("-fx-font-size: 14px;");
        nomTextField = new TextField();



        Label idLabel = new Label("ID : ");
        idLabel.setStyle("-fx-font-size: 14px;");
        idTextField = new TextField();

        
        Label depLabel = new Label("D\u00e9partement : ");
        depLabel.setStyle("-fx-font-size: 14px;");
        departementComboBox = new ComboBox<>();
        
        departementComboBox = new ComboBox<>();
    
        List<String> departements = controller.getDepartementNameFromDatabase();
        
        departementComboBox.getItems().addAll(departements);

        Label anneeLabel = new Label("Ann\u00e9e de donn\u00e9es : ");
        anneeLabel.setStyle("-fx-font-size: 14px;");
        anneeTextField = new TextField();


        Label populationLabel = new Label("Population : ");
        populationLabel.setStyle("-fx-font-size: 14px;");
        populationTextField = new TextField();


        Label depCulturellesLabel = new Label("D\u00e9penses culturelles totales : ");
        depCulturellesLabel.setStyle("-fx-font-size: 14px;");
        depCulturellesTextField = new TextField();

        
        Label budgetTotal = new Label("Budget Total : ");
        budgetTotal.setStyle("-fx-font-size: 14px;");
        budgetTotalField = new TextField();



        grid.add(nomLabel, 0, 0);
        grid.add(nomTextField, 1, 0);
        grid.add(idLabel, 0, 1);
        grid.add(idTextField, 1, 1);
        grid.add(depLabel, 0, 2);
        grid.add(departementComboBox, 1, 2);
        grid.add(anneeLabel, 0, 3);
        grid.add(anneeTextField, 1, 3);
        grid.add(populationLabel, 0, 4);
        grid.add(populationTextField, 1, 4);
        grid.add(depCulturellesLabel, 0, 5);
        grid.add(depCulturellesTextField, 1, 5);
        grid.add(budgetTotal, 0, 6);
        grid.add(budgetTotalField, 1, 6);

        box.getChildren().addAll(grid);
        return box;
    }
    
    private VBox createHousingStatsBox() {
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
    
    


    public String getNomTextFieldValue(){
        return nomTextField.getText();
    }

    public String getIdTextFieldValue() {
        return idTextField.getText();
    }

    public ComboBox<String> getDepartementComboBox() {
        return departementComboBox;
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