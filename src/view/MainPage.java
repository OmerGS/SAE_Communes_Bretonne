package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
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

    @Override
    public void start(Stage primaryStage) {
        this.controller = new Controller(this);

        // Image pour le logo
        ImageView logo = new ImageView(new Image("file:../resources/image/logo_bretagne.png"));
        logo.setFitWidth(50);
        logo.setFitHeight(50);
        logo.setClip(new Circle(25, 25, 25));

        // Barre de recherche en haut
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

        Region SpacerBar = new Region();
        SpacerBar.setPrefWidth(60);

        if (searchIcon != null) {
            searchBox.getChildren().addAll(SpacerBar,searchIcon,this.searchField);
        } else {
            searchBox.getChildren().add(this.searchField);
        }

        // Barre d'utilisateur en haut à droite
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
            System.out.println("user.png not found");
        }

        Button filterButton = new Button("Filtrer");
        filterButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: #fff; -fx-background-radius: 10px; -fx-border-radius: 10px;");
        if (userIcon != null) {
            userBox.getChildren().addAll(this.userIcon, menuIcon);
        } else {
            userBox.getChildren().add(filterButton);
        }

        // Region to push the filter button to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Combiner la barre de recherche et la barre d'utilisateur
        HBox topBar = new HBox(10);
        topBar.setStyle("-fx-padding: 10; -fx-background-color: #000000; -fx-text-fill: #fff;");
        topBar.getChildren().addAll(logo,searchBox, spacer, userBox);

        // Résultats
        HBox resultsBox = new HBox(10);
        resultsBox.setPadding(new Insets(10));
        resultsBox.setStyle("-fx-background-color: #d3d3d3; -fx-padding: 10; -fx-background-radius: 10px;");
        this.resultsLabel = new Label();
        resultsLabel.setStyle("-fx-text-fill: #333;");
        
        // Region to push the filter button to the right
        Region spacerFilter = new Region();
        HBox.setHgrow(spacerFilter, Priority.ALWAYS);

        resultsBox.getChildren().addAll(resultsLabel, spacerFilter, filterButton);

        // ListView pour afficher les communes
        communeListView.setPrefSize(400, 400);

        // Custom cell factory to display Commune objects
        communeListView.setCellFactory(new Callback<ListView<Commune>, ListCell<Commune>>() {
            @Override
            public ListCell<Commune> call(ListView<Commune> listView) {
                return new ListCell<Commune>() {
                    @Override
                    protected void updateItem(Commune commune, boolean empty) {
                        super.updateItem(commune, empty);
                        if (commune != null) {
                            HBox row = createResultRow(commune.getNomCommune(), commune.getPrixM2Moyen(), commune.getPrixMoyen(), commune.isMostImportant());
                            row.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-background-radius: 10px; -fx-border-color: #d3d3d3; -fx-border-radius: 10px;");
                            setGraphic(row);
                        }
                    }
                };
            }
        });

        // Charger les communes et les afficher dans la ListView
        loadCommunes();

        VBox mainBox = new VBox(10);
        mainBox.getChildren().addAll(topBar, resultsBox, communeListView);

        Scene scene = new Scene(mainBox, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Recherche de villes");
        primaryStage.show();

        // Ajout de l'écouteur d'événements sur le champ de recherche
        this.searchField.setOnKeyTyped(event -> {
            String searchText = searchField.getText().trim();
            this.controller.handleSearchEvent(searchText);
        });

        this.userIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            this.controller.connectionClicked();
        });
    }

    private HBox createResultRow(String cityName, double d, double e, boolean isAvailable) {
        HBox row = new HBox(10);
        row.setPadding(new Insets(10));

        Label cityLabel = new Label(cityName);
        Label priceM2Label = new Label("Prix m² " + d + "€");
        Label averagePriceLabel = new Label("Prix moyen " + e + "€");
        Label availableLabel = new Label(isAvailable ? "Oui" : "Non");
        Button detailsButton = new Button("Voir plus");
        detailsButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: #fff; -fx-background-radius: 10px; -fx-border-radius: 10px;");

        cityLabel.setStyle("-fx-text-fill: #333;");
        priceM2Label.setStyle("-fx-text-fill: #333;");
        averagePriceLabel.setStyle("-fx-text-fill: #333;");
        availableLabel.setStyle("-fx-text-fill: #333;");

        row.getChildren().addAll(cityLabel, priceM2Label, averagePriceLabel, availableLabel, detailsButton);
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

        // Redéfinir la CellFactory pour s'assurer qu'elle est appliquée
        communeListView.setCellFactory(new Callback<ListView<Commune>, ListCell<Commune>>() {
            @Override
            public ListCell<Commune> call(ListView<Commune> listView) {
                return new ListCell<Commune>() {
                    @Override
                    protected void updateItem(Commune commune, boolean empty) {
                        super.updateItem(commune, empty);
                        if (commune != null) {
                            HBox row = createResultRow(commune.getNomCommune(), commune.getPrixM2Moyen(), commune.getPrixMoyen(), commune.isMostImportant());
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

    public ImageView getImageUserIcon(){
        return(this.userIcon);
    }
}