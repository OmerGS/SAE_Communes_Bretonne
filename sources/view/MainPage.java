package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import controller.Controller;
import data.Commune;
import javafx.util.Callback;

import java.util.List;

public class MainPage extends Application {
    private ListView<Commune> communeListView = new ListView<>();
    private TextField searchField;
    private Controller controller;
    private Label resultsLabel;
    private ImageView userIcon;
    private VBox menuBox;
    private Button transportButton;
    private Button cheminCourtButton;
    private Button toutesLesCommunes;
    private Button morbihanFilterButton;
    private Button finistereFilterButton;
    private Button coteArmorFilterButton;
    private Button illeEtVilaineFilterButton;
    private Button reloadDatabase;
    private Button editData;
    private int nbCommune;
    private Button exportDataButton;


    public MainPage(Controller controller){
        this.controller = controller;
        this.controller.setMainPage(this);
        loadCommunes();
    }


    @Override
    public void start(Stage primaryStage) {
        // Initialize resultsLabel
        this.resultsLabel = new Label(this.nbCommune + " r\u00e9sultats");
        this.resultsLabel.setStyle("-fx-font-size: 18px; -fx-padding: 10px; -fx-text-fill: white;");

        // Create filter button
        Button filterButton = new Button("Filtrer");
        filterButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: #fff; -fx-background-radius: 10px; -fx-border-radius: 10px;");

        // Checkboxes for filters
        this.toutesLesCommunes = new Button("Toutes");
        this.morbihanFilterButton = new Button("Morbihan");
        this.finistereFilterButton = new Button("Finist\u00E8re");
        this.coteArmorFilterButton = new Button("C\u00F4tes-d'Armor");
        this.illeEtVilaineFilterButton = new Button("Ille-et-Vilaine");

        String buttonStyle = "-fx-background-color: #C4C5CF; " + 
                     "-fx-text-fill: #000000; " +      
                     "-fx-background-radius: 10px; " +
                     "-fx-border-radius: 10px; " +
                     "-fx-padding: 10px 20px; " +
                     "-fx-font-size: 14px; " +
                     "-fx-cursor: hand;";


        // Appliquer le style à chaque bouton
        this.toutesLesCommunes.setStyle(buttonStyle);
        this.morbihanFilterButton.setStyle(buttonStyle);
        this.finistereFilterButton.setStyle(buttonStyle);
        this.coteArmorFilterButton.setStyle(buttonStyle);
        this.illeEtVilaineFilterButton.setStyle(buttonStyle);

        HBox communeFilterBox = new HBox(5, toutesLesCommunes, morbihanFilterButton, finistereFilterButton, coteArmorFilterButton, illeEtVilaineFilterButton);

        this.toutesLesCommunes.setOnAction(this.controller);
        this.morbihanFilterButton.setOnAction(this.controller);
        this.finistereFilterButton.setOnAction(this.controller);
        this.coteArmorFilterButton.setOnAction(this.controller);
        this.illeEtVilaineFilterButton.setOnAction(this.controller);

        // HBox for results label and filter button
        HBox resultsBox = new HBox(10);
        resultsBox.setAlignment(Pos.CENTER_LEFT);
        resultsBox.setPadding(new Insets(10));
        resultsBox.setStyle("-fx-background-color: #5F606D;");

        // Add a Region to take up the empty space
        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        // Add the resultsLabel, spacer, and filterButton to the HBox
        resultsBox.getChildren().addAll(this.resultsLabel, spacer2, communeFilterBox);

        // Logo image
        ImageView logo = new ImageView(new Image("file:../resources/image/logo_bretagne.png"));
        logo.setFitWidth(50);
        logo.setFitHeight(50);
        logo.setClip(new Circle(25, 25, 25));

        // Search bar
        HBox searchBox = new HBox(10);
        searchBox.setPadding(new Insets(10));
        searchBox.setStyle("-fx-background-color: #000000; -fx-padding: 10; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        this.searchField = new TextField();
        this.searchField.setPromptText("Rechercher une ville");
        this.searchField.setPrefWidth(300);
        this.searchField.setStyle("-fx-background-color: #fff; -fx-border-color: #FFFFFF; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        ImageView searchIcon = null;
        try {
            searchIcon = new ImageView(new Image("file:../resources/image/search2.png"));
            searchIcon.setFitHeight(20);
            searchIcon.setFitWidth(20);
        } catch (NullPointerException e) {
            System.out.println("search.png not found");
        }

        Region spacerBar = new Region();
        spacerBar.setPrefWidth(60);

        if (searchIcon != null) {
            searchBox.getChildren().addAll(spacerBar, searchIcon, this.searchField);
        } else {
            searchBox.getChildren().add(this.searchField);
        }

        // User bar
        HBox userBox = new HBox(10);
        userBox.setPadding(new Insets(10));
        userBox.setStyle("-fx-padding: 10;");

        this.userIcon = null;
        try {
            this.userIcon = new ImageView(new Image("file:../resources/image/user.png"));
            this.userIcon.setFitHeight(30);
            this.userIcon.setFitWidth(30);
        } catch (NullPointerException e) {
            System.out.println("user.png not found");
        }

        ImageView menuIcon = null;
        try {
            menuIcon = new ImageView(new Image("file:../resources/image/menu.png"));
            menuIcon.setFitHeight(30);
            menuIcon.setFitWidth(30);
        } catch (NullPointerException e) {
            System.out.println("menu.png not found");
        }

        if (userIcon != null && menuIcon != null) {
            userBox.getChildren().addAll(this.userIcon, menuIcon);
        } else {
            userBox.getChildren().add(filterButton);
        }

        menuIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> toggleMenu());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Top bar combining search and user bar
        HBox topBar = new HBox(10);
        topBar.setStyle("-fx-padding: 10; -fx-background-color: #000000; -fx-text-fill: #fff;");
        topBar.getChildren().addAll(logo, searchBox, spacer, userBox);

        // ListView to display communes
        communeListView.setPrefHeight(Region.USE_COMPUTED_SIZE);
        communeListView.setMaxWidth(1000);  // Set maximum width

        // Custom cell factory to display Commune objects
        communeListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Commune> call(ListView<Commune> listView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Commune commune, boolean empty) {
                        super.updateItem(commune, empty);
                        if (commune != null) {
                            HBox row = createResultRow(commune);
                            setGraphic(row);
                        }
                    }
                };
            }
        });

        // VBox to center the ListView and make it grow
        VBox centerBox = new VBox(10);
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerBox.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #6C7BD0 0%, white 50%, #6C7BD0 100%);");

        centerBox.getChildren().addAll(resultsBox, communeListView);
        VBox.setVgrow(communeListView, Priority.ALWAYS);

        // Menu box for the side menu
        menuBox = createMenuBox();
        menuBox.setVisible(false);

        this.controller.verifyAdmin();

        // StackPane to hold centerBox and menuBox
        StackPane mainPane = new StackPane();
        mainPane.getChildren().addAll(centerBox, menuBox);
        StackPane.setAlignment(menuBox, Pos.CENTER_RIGHT);


        // Main container with topBar and main content
        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(mainPane);
        root.setStyle("-fx-background-color: #ffffff;");

        Scene scene = new Scene(root, 1600, 900);
        scene.getStylesheets().add("file:../resources/css/style.css");

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Recherche de villes");
        primaryStage.show();

        this.searchField.setOnKeyTyped(event -> {
            String searchText = searchField.getText().trim();
            this.controller.handleSearchEvent(searchText);
        });

        this.userIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            this.controller.connectionClicked();
        });
    }

    private VBox createMenuBox() {
        VBox menuBox = new VBox(10);
        menuBox.setStyle("-fx-background-color: #000000; -fx-padding: 20px;");
        menuBox.setAlignment(Pos.TOP_LEFT);
        menuBox.setMaxWidth(400);
    
        this.cheminCourtButton = createButtonWithIcon("Chemin Entre 2 communes", "file:../resources/image/chemin.png");
        this.editData = new Button("Modifier les donn\u00E9es");
        editData.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-border-color: transparent;");
        editData.setOnAction(this.controller);
    
        ImageView icon = new ImageView(new Image("file:../resources/image/edit.png"));
        icon.setFitHeight(16); // Adjust the size as needed
        icon.setFitWidth(16);  // Adjust the size as needed
    
        editData.setGraphic(icon);
        editData.setContentDisplay(ContentDisplay.LEFT); // To position the icon on the left of the text
        this.exportDataButton = createButtonWithIcon("Exporter Donn\u00E9es", "file:../resources/image/export.png");
        this.reloadDatabase = createButtonWithIcon("Rechargez la base de donn\u00E9es", "file:../resources/image/reload.png");
    
        menuBox.getChildren().addAll(this.cheminCourtButton, this.editData, this.exportDataButton, this.reloadDatabase);
        return menuBox;
    }

    private Button createButtonWithIcon(String text, String iconPath) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-border-color: transparent;");
        button.setOnAction(this.controller);
    
        ImageView icon = new ImageView(new Image(iconPath));
        icon.setFitHeight(16); // Adjust the size as needed
        icon.setFitWidth(16);  // Adjust the size as needed
    
        button.setGraphic(icon);
        button.setContentDisplay(ContentDisplay.LEFT); // To position the icon on the left of the text
        return button;
    }



    private void toggleMenu() {
        menuBox.setVisible(!menuBox.isVisible());
    }
    
    private HBox createResultRow(Commune commune) {
        HBox returnHbox = new HBox();
        returnHbox.setAlignment(Pos.CENTER); // Centrer l'alignement du HBox
        returnHbox.setSpacing(40); // Espacement entre les éléments dans le HBox
        returnHbox.setPrefWidth(600); // Ajuster la largeur de la returnHbox
        returnHbox.setPrefHeight(100);
    

    
        // Infos1 label (Icône de l'importance)
        HBox info1Box = new HBox(5);
        info1Box.setAlignment(Pos.CENTER);
        ImageView importantIcon = new ImageView(new Image("file:../resources/image/important.png"));
        importantIcon.setFitHeight(40); // Ajuster la taille si nécessaire
        importantIcon.setFitWidth(40);  // Ajuster la taille si nécessaire
        Label importanceLabel = new Label();
        if (commune.getPopulation() == -1) {
            importanceLabel.setText("Indisponible");
        } else {
            importanceLabel.setText("" + commune.getPopulation());
        }
        importanceLabel.setStyle("-fx-font-size: 18px;");
        info1Box.getChildren().addAll(importantIcon, importanceLabel);
    
        // Infos2 label (Prix au m²)
        HBox info2Box = new HBox(5);
        info2Box.setAlignment(Pos.CENTER);
        ImageView prixM2Icon = new ImageView(new Image("file:../resources/image/metrecarre.png"));
        prixM2Icon.setFitHeight(40); // Ajuster la taille si nécessaire
        prixM2Icon.setFitWidth(40);  // Ajuster la taille si nécessaire
        Label priceM2Label = new Label("Prix \u33A1\n" + commune.getPrixM2Moyen() + " \u20AC");
        priceM2Label.setStyle("-fx-font-size: 18px;");
        info2Box.getChildren().addAll(prixM2Icon, priceM2Label);
    
    
        // VBox centrale pour le nom de la commune
        VBox middleVBox = new VBox(10); // Ajout d'un espacement entre les éléments
        middleVBox.setAlignment(Pos.CENTER);
        Label communeLabel = new Label(commune.getNomCommune());
        communeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        middleVBox.getChildren().add(communeLabel);
    
    
        // Infos3 label (Prix moyen)
        HBox info3Box = new HBox(5);
        info3Box.setAlignment(Pos.CENTER);
        ImageView avgPriceIcon = new ImageView(new Image("file:../resources/image/prix.png"));
        avgPriceIcon.setFitHeight(40); // Ajuster la taille si nécessaire
        avgPriceIcon.setFitWidth(40);  // Ajuster la taille si nécessaire
        Label avgPriceLabel = new Label("Prix moyen\n" + commune.getPrixMoyen() + " \u20AC");
        avgPriceLabel.setStyle("-fx-font-size: 18px;");
        info3Box.getChildren().addAll(avgPriceIcon, avgPriceLabel);
    
        // Infos4 label (Icône de la gare et disponibilité)
        HBox info4Box = new HBox(5);
        info4Box.setAlignment(Pos.CENTER);
        Label stationLabel = new Label("Gare : " +(commune.aUneGare() ? "Oui" : "Non"));

        stationLabel.setStyle("-fx-font-size: 18px;");
        info4Box.getChildren().addAll(stationLabel);
    
    
        // Bouton pour afficher plus de détails
        Button detailsButton = new Button("Voir plus");
        detailsButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: #fff; -fx-background-radius: 10px; -fx-border-radius: 10px;");
        detailsButton.setOnAction(event -> {
            this.controller.showCommuneDetails(commune);
        });
        middleVBox.getChildren().add(detailsButton);
    
        // Ajouter toutes les VBox au HBox principal
        returnHbox.getChildren().addAll(info1Box,info2Box, middleVBox, info3Box, info4Box);
    
        return returnHbox;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    

    private void loadCommunes() {
        List<Commune> communes = controller.getCommunesFromDataBase();
        this.nbCommune = communes.size();
        communeListView.getItems().addAll(communes);
    }


    public void loadCommunes(List<Commune> communes) {
        communeListView.getItems().addAll(communes);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void updateCommunesListView(List<Commune> communes) {
        this.communeListView.getItems().clear();
        this.communeListView.getItems().addAll(communes);

        // Redefine CellFactory to ensure it is applied
        communeListView.setCellFactory(new Callback<ListView<Commune>, ListCell<Commune>>() {
            @Override
            public ListCell<Commune> call(ListView<Commune> listView) {
                return new ListCell<Commune>() {
                    @Override
                    protected void updateItem(Commune commune, boolean empty) {
                        super.updateItem(commune, empty);
                        if (commune != null) {
                            HBox row = createResultRow(commune);
                            row.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-background-radius: 10px; -fx-border-color: #d3d3d3; -fx-border-radius: 10px;");
                            setGraphic(row);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });
    }

    public Button getExportButton(){
        return(this.exportDataButton);
    }

    public TextField getSearchField() {
        return this.searchField;
    }
    public Button getTransport(){
        return this.transportButton;
    }

    public Label getNumberOfRow() {
        return this.resultsLabel;
    }

    public ImageView getImageUserIcon() {
        return this.userIcon;
    }

    public Button getButtonCheminLePlusCourt(){
        return(this.cheminCourtButton);
    }

    public Button getToutesLesCommunes() {
        return toutesLesCommunes;
    }

    public Button getMorbihanFilterButton() {
        return morbihanFilterButton;
    }

    public Button getFinistereFilterButton() {
        return finistereFilterButton;
    }

    public Button getCoteArmorFilterButton() {
        return coteArmorFilterButton;
    }

    public Button getIlleEtVilaineFilterButton() {
        return illeEtVilaineFilterButton;
    }

    public Button getReloadDatabase() {
        return reloadDatabase;
    }

    public Button getEditData() {
        return editData;
    }
}