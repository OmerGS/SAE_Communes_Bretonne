package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
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
    private Button cheminCourtButton;

    @Override
    public void start(Stage primaryStage) {
        this.controller = new Controller(this);

        // Initialize resultsLabel
        this.resultsLabel = new Label("55 résultats");
        this.resultsLabel.setStyle("-fx-font-size: 18px; -fx-padding: 10px;");

        // Create filter button
        Button filterButton = new Button("Filtrer");
        filterButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: #fff; -fx-background-radius: 10px; -fx-border-radius: 10px;");
        filterButton.setOnAction(event -> {
            // Handle filter action
        });

        // HBox for results label and filter button
        HBox resultsBox = new HBox(10);
        resultsBox.setAlignment(Pos.CENTER_LEFT);
        resultsBox.setPadding(new Insets(10));
        resultsBox.getChildren().addAll(this.resultsLabel, filterButton);
        HBox.setHgrow(filterButton, Priority.ALWAYS);

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
        communeListView.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px; -fx-padding: 10px;");

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

        // Load communes and display them in the ListView
        loadCommunes();

        // VBox to center the ListView and make it grow
        VBox centerBox = new VBox(10);
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerBox.setStyle("-fx-background-color: linear-gradient(to bottom, #f0f0f0, #c0c0c0);");
        centerBox.getChildren().addAll(resultsBox, communeListView);
        VBox.setVgrow(communeListView, Priority.ALWAYS);

        // Menu box for the side menu
        menuBox = createMenuBox();
        menuBox.setVisible(false);

        StackPane mainPane = new StackPane();
        mainPane.getChildren().addAll(centerBox, menuBox);

        VBox mainBox = new VBox(10);
        mainBox.getChildren().addAll(topBar, mainPane);
        VBox.setVgrow(mainPane, Priority.ALWAYS);

        Scene scene = new Scene(mainBox, 800, 600);
        scene.getStylesheets().add("file:../resources/css/style.css");

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
        menuBox.setPadding(new Insets(10));
        menuBox.setAlignment(Pos.CENTER_LEFT);

        Label graphsLabel = new Label("Graphes");
        graphsLabel.setStyle("-fx-text-fill: #fff; -fx-font-size: 16px;");
        this.cheminCourtButton = new Button("Chemin Entre 2 commune");
        this.cheminCourtButton.setStyle("-fx-text-fill: #fff; -fx-font-size: 16px;");

        this.cheminCourtButton.setOnAction(this.controller);

        Label mapLabel = new Label("Carte");
        mapLabel.setStyle("-fx-text-fill: #fff; -fx-font-size: 16px;");
        Label exportDataLabel = new Label("Exporter Données");
        exportDataLabel.setStyle("-fx-text-fill: #fff; -fx-font-size: 16px;");

        menuBox.getChildren().addAll(graphsLabel, this.cheminCourtButton, mapLabel, exportDataLabel);
        return menuBox;
    }

    private void toggleMenu() {
        menuBox.setVisible(!menuBox.isVisible());
    }

    private HBox createResultRow(Commune commune) {
        HBox row = new HBox(10);
        row.setPadding(new Insets(10));
        row.setStyle("-fx-background-color: #ffffff; -fx-border-color: #d3d3d3; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Label cityLabel = new Label(commune.getNomCommune());
        cityLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Label priceM2Label = new Label("Prix m² " + commune.getPrixM2Moyen() + "€");
        priceM2Label.setStyle("-fx-font-size: 12px;");

        Label averagePriceLabel = new Label("Prix moyen " + commune.getPrixMoyen() + "€");
        averagePriceLabel.setStyle("-fx-font-size: 12px;");

        Label availableLabel = new Label(commune.isMostImportant() ? "Oui" : "Non");
        availableLabel.setStyle("-fx-font-size: 12px;");

        Button detailsButton = new Button("Voir plus");
        detailsButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: #fff; -fx-background-radius: 10px; -fx-border-radius: 10px;");
        detailsButton.setOnAction(event -> {
            this.controller.showCommuneDetails(commune);
        });

        priceM2Label.setStyle("-fx-text-fill: #333;");
        averagePriceLabel.setStyle("-fx-text-fill: #333;");
        availableLabel.setStyle("-fx-text-fill: #333;");

        row.getChildren().addAll(cityLabelContainer, priceM2Label, averagePriceLabel, availableLabel, detailsButton);
        return row;
    }

    private void loadCommunes() {
        List<Commune> communes = controller.getCommunes();
        communeListView.getItems().addAll(communes);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void updateCommunesListView(List<Commune> communes) {
        System.out.println("Updating ListView with " + communes.size() + " communes.");
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

    public TextField getSearchField() {
        return this.searchField;
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
}
