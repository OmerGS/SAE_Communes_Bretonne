/*package view;

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
import data.Aeroport;
import data.Commune;
import data.Gare;
import javafx.util.Callback;

import java.util.List;

public class MainPageTransport extends Application {
    private ListView<Object> transporListView = new ListView<>();
    private ListView<Aeroport> aeroportListView = new ListView<>();
    private ListView<Gare> gareListView = new ListView<>();
    private TextField searchField;
    private Controller controller;
    private Label resultsLabel;
    private ImageView userIcon;
    private VBox menuBox;
    private Button aeroportButton;
    private Button gareButton;
    private Button reloadDatabase;
    private Button editData;
    private int nbAeroport;
    private int nbGare;
    private Button exportDataButton;
    private Button touts;
    private Button communeButton;


    public MainPageTransport(Controller controller){
        this.controller = controller;
        this.controller.setMainPageTransport(this);
        loadCommunes();
    }


    @Override
    public void start(Stage primaryStage) {
        // Initialize resultsLabel
        this.resultsLabel = new Label(this.nbAeroport + " r\u00e9sultats");
        this.resultsLabel.setStyle("-fx-font-size: 18px; -fx-padding: 10px; -fx-text-fill: white;");

        // Create filter button
        Button filterButton = new Button("Filtrer");
        filterButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: #fff; -fx-background-radius: 10px; -fx-border-radius: 10px;");

        // Checkboxes for filters
        this.touts = new Button("Touts");
        this.aeroportButton = new Button("Aeroport");
        this.gareButton = new Button("Gare");

        String buttonStyle = "-fx-background-color: #C4C5CF; " + 
                     "-fx-text-fill: #000000; " +      
                     "-fx-background-radius: 10px; " +
                     "-fx-border-radius: 10px; " +
                     "-fx-padding: 10px 20px; " +
                     "-fx-font-size: 14px; " +
                     "-fx-cursor: hand;";


        // Appliquer le style à chaque bouton
        this.touts.setStyle(buttonStyle);
        this.aeroportButton.setStyle(buttonStyle);
        this.gareButton.setStyle(buttonStyle);

        HBox communeFilterBox = new HBox(5, touts, aeroportButton, gareButton);

        this.touts.setOnAction(this.controller);
        this.aeroportButton.setOnAction(this.controller);
        this.gareButton.setOnAction(this.controller);

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
        aeroportListView.setPrefHeight(Region.USE_COMPUTED_SIZE);
        aeroportListView.setMaxWidth(800);  // Set maximum width

        // Custom cell factory to display Commune objects
        transporListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Aeroport> call(ListView<Object> list) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Aeroport aeroport, boolean empty) {
                        super.updateItem(aeroport, empty);
                        if (aeroport != null) {
                            HBox row = createResultRow(aeroport);
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

        centerBox.getChildren().addAll(resultsBox, transportListView);
        VBox.setVgrow(transportListView, Priority.ALWAYS);

        // Menu box for the side menu
        menuBox = createMenuBox();
        menuBox.setVisible(false);

        this.controller.verifyAdmin();

        // StackPane to hold centerBox and menuBox
        StackPane mainPane = new StackPane();
        mainPane.getChildren().addAll(centerBox, menuBox);
        StackPane.setAlignment(menuBox, Pos.CENTER_RIGHT);

        // Ensure menuBox is tightly aligned to the right side of centerBox
        menuBox.translateXProperty().bind(menuBox.widthProperty().add(mainPane.widthProperty()).subtract(110).negate());

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
        menuBox.setAlignment(Pos.CENTER_RIGHT);
        menuBox.setMaxWidth(400);

        this.communeButton = new Button("Commune");
        this.communeButton.setStyle("-fx-text-fill: #fff; -fx-font-size: 16px;");
        this.communeButton.setOnAction(this.controller);

        this.editData = new Button("Modifier les données");
        this.editData.setStyle("-fx-text-fill: #fff; -fx-font-size: 16px;");
        this.editData.setOnAction(this.controller);

        this.exportDataButton = new Button("Exporter Données");
        this.exportDataButton.setStyle("-fx-text-fill: #fff; -fx-font-size: 16px;");
        this.exportDataButton.setOnAction(this.controller);

        this.reloadDatabase = new Button("Rechargez la base de données");
        this.reloadDatabase.setStyle("-fx-text-fill: #fff; -fx-font-size: 16px;");
        this.reloadDatabase.setOnAction(this.controller);

        menuBox.getChildren().addAll(editData, exportDataButton, this.reloadDatabase);
        return menuBox;
    }

    private void toggleMenu() {
        menuBox.setVisible(!menuBox.isVisible());
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    

    private void loadCommunes() {
        List<Aeroport> aeroports = controller.getAeroportsFromDataBase();
        this.nbAeroport = aeroports.size();
        aeroportListView.getItems().addAll(aeroports);
    }


    public void loadCommunes(List<Aeroport> aeroports) {
        aeroportListView.getItems().addAll(aeroports);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void updateCommunesListView(List<Aeroport> communes) {
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

    public Button getExportButton(){
        return(this.exportDataButton);
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

    public Button getCommuneButton(){
        return this.communeButton;
    }

    public Button getReloadDatabase() {
        return reloadDatabase;
    }

    public Button getEditData() {
        return editData;
    }
}*/