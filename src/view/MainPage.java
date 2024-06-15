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
    private Button cheminCourtButton;
    private Button toutesLesCommunes;
    private Button morbihanFilterButton;
    private Button finistereFilterButton;
    private Button coteArmorFilterButton;
    private Button illeEtVilaineFilterButton;
    private Button reloadDatabase;
    private Button editData;
    private int nbCommune;


    public MainPage(Controller controller){
        this.controller = controller;
        this.controller.setMainPage(this);
        loadCommunes();
    }


    @Override
    public void start(Stage primaryStage) {
        // Initialize resultsLabel
        this.resultsLabel = new Label(this.nbCommune + " r\u00e9sultats");
        this.resultsLabel.setStyle("-fx-font-size: 18px; -fx-padding: 10px;");

        // Create filter button
        Button filterButton = new Button("Filtrer");
        filterButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: #fff; -fx-background-radius: 10px; -fx-border-radius: 10px;");



        // Checkboxes for filters
        this.toutesLesCommunes = new Button("Toutes");
        this.morbihanFilterButton = new Button("Morbihan");
        this.finistereFilterButton = new Button("Finistère");
        this.coteArmorFilterButton = new Button("Côtes-d'Armor");
        this.illeEtVilaineFilterButton = new Button("Ille-et-Vilaine");

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

        // VBox to center the ListView and make it grow
        VBox centerBox = new VBox(10);
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerBox.setStyle("-fx-background-color: linear-gradient(to bottom, #f0f0f0, #c0c0c0);");
        centerBox.getChildren().addAll(resultsBox, communeListView);
        VBox.setVgrow(communeListView, Priority.ALWAYS);

        // Menu box for the side menu
        menuBox = createMenuBox();
        menuBox.setVisible(false);

        this.controller.verifyAdmin();

        StackPane mainPane = new StackPane();
        mainPane.getChildren().addAll(centerBox, menuBox);
        StackPane.setAlignment(menuBox, Pos.CENTER_RIGHT);

        VBox mainBox = new VBox(10);
        mainBox.getChildren().addAll(topBar, mainPane);
        VBox.setVgrow(mainPane, Priority.ALWAYS);

        Scene scene = new Scene(mainBox, 800, 600);
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

        this.cheminCourtButton = new Button("Chemin Entre 2 commune");
        this.cheminCourtButton.setStyle("-fx-text-fill: #fff; -fx-font-size: 16px;");
        this.cheminCourtButton.setOnAction(this.controller);

        this.editData = new Button("Modifier les données");
        this.editData.setStyle("-fx-text-fill: #fff; -fx-font-size: 16px;");
        this.editData.setOnAction(this.controller);

        Label exportDataLabel = new Label("Exporter Données");
        exportDataLabel.setStyle("-fx-text-fill: #fff; -fx-font-size: 16px;");

        this.reloadDatabase = new Button("Rechargez la base de données");
        this.reloadDatabase.setStyle("-fx-text-fill: #fff; -fx-font-size: 16px;");
        this.reloadDatabase.setOnAction(this.controller);

        menuBox.getChildren().addAll(this.cheminCourtButton, editData, exportDataLabel, this.reloadDatabase);
        return menuBox;
    }

    private void toggleMenu() {
        menuBox.setVisible(!menuBox.isVisible());
    }
    
    private HBox createResultRow(Commune commune) {
        HBox returnHbox = new HBox();
        returnHbox.setAlignment(Pos.CENTER); // Center align the HBox
        
        VBox mainBox = new VBox(20); // Main VBox for entire structure with spacing of 10
        mainBox.setAlignment(Pos.CENTER); // Center align the VBox
        mainBox.setPadding(new Insets(10)); // Add padding for spacing around the VBox
        
        // HBox for left and right information labels
        HBox infoHBox = new HBox(40); // HBox for left and right info labels with spacing of 10
        infoHBox.setAlignment(Pos.CENTER); // Center align horizontally
        
        // Left VBox for infos1 and infos2
        HBox leftVBox = new HBox(20); // VBox for left info labels with spacing of 5
        leftVBox.setAlignment(Pos.CENTER_LEFT); // Align to the left vertically
        
        // Infos1 label
        Label info1Label = new Label("Important\n" + commune.isMostImportant());
        info1Label.setStyle("-fx-font-size: 12px;");
        
        // Infos2 label
        Label info2Label = new Label("Prix m2\n" + commune.getPrixM2Moyen());
        info2Label.setStyle("-fx-font-size: 12px;");
        
        leftVBox.getChildren().addAll(info1Label, info2Label);
        
        // Right VBox for infos3 and infos4
        HBox rightVBox = new HBox(10); // VBox for right info labels with spacing of 5
        rightVBox.setAlignment(Pos.CENTER_RIGHT); // Align to the right vertically
        
        // Infos3 label
        Label info3Label = new Label("Prix Moyen\n" + commune.getPrixMoyen());
        info3Label.setStyle("-fx-font-size: 12px;");
        
        // Infos4 label
        Label info4Label = new Label("Gare\n" + commune.aUneGare());
        info4Label.setStyle("-fx-font-size: 12px;");
        
        rightVBox.getChildren().addAll(info3Label, info4Label);
        
        // Commune name label
        Label communeLabel = new Label(commune.getNomCommune());
        communeLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        // Add left and right VBoxes to infoHBox
        infoHBox.getChildren().addAll(leftVBox, communeLabel, rightVBox);
        
        // Button for showing more details
        Button detailsButton = new Button("Voir plus");
        detailsButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: #fff; -fx-background-radius: 10px; -fx-border-radius: 10px;");
        detailsButton.setOnAction(event -> {
            this.controller.showCommuneDetails(commune);
        });
        
        // Align button to the center
        VBox.setMargin(detailsButton, new Insets(10, 0, 0, 0)); // Adjust top margin for button
        
        // Add elements to the main VBox
        mainBox.getChildren().addAll(infoHBox, detailsButton);
        returnHbox.getChildren().addAll(mainBox);
        
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
