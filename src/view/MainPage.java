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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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


    @Override
    public void start(Stage primaryStage) {
        this.controller = new Controller(this);

        // Barre de recherche en haut
        HBox searchBox = new HBox(10);
        searchBox.setPadding(new Insets(10));
        this.searchField = new TextField();
        this.searchField.setPromptText("Rechercher une ville");
        this.searchField.setPrefWidth(300);

        ImageView searchIcon = null;
        try {
            searchIcon = new ImageView(new Image("file:../resources/image/search.png"));
            searchIcon.setFitHeight(20);
            searchIcon.setFitWidth(20);
        } catch (NullPointerException e) {
            System.out.println("search.png not found");
        }

        if (searchIcon != null) {
            searchBox.getChildren().addAll(this.searchField, searchIcon);
        } else {
            searchBox.getChildren().add(this.searchField);
        }

        // Barre d'utilisateur en haut à droite
        HBox userBox = new HBox(10);
        userBox.setPadding(new Insets(10));

        ImageView userIcon = null;
        try {
            userIcon = new ImageView(new Image("file:../resources/image/user.png"));
            userIcon.setFitHeight(20);
            userIcon.setFitWidth(20);
        } catch (NullPointerException e) {
            System.out.println("user.png not found");
        }

        Button filterButton = new Button("Filtrer");
        if (userIcon != null) {
            userBox.getChildren().addAll(userIcon, filterButton);
        } else {
            userBox.getChildren().add(filterButton);
        }

        // Combiner la barre de recherche et la barre d'utilisateur
        HBox topBar = new HBox(10);
        topBar.getChildren().addAll(searchBox, userBox);

        // Résultats
        VBox resultsBox = new VBox(10);
        resultsBox.setPadding(new Insets(10));
        resultsBox.setStyle("-fx-background-color: #d3d3d3; -fx-padding: 10; -fx-background-radius: 10;");
        this.resultsLabel = new Label();
        resultsBox.getChildren().add(resultsLabel);

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
    }

    private HBox createResultRow(String cityName, float priceM2, float averagePrice, boolean isAvailable) {
        HBox row = new HBox(10);
        row.setPadding(new Insets(10));
        row.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-background-radius: 10; -fx-border-color: #d3d3d3; -fx-border-radius: 10;");

        Label cityLabel = new Label(cityName);
        Label priceM2Label = new Label("Prix m² " + priceM2 + "€");
        Label averagePriceLabel = new Label("Prix moyen " + averagePrice + "€");
        Label availableLabel = new Label(isAvailable ? "Oui" : "Non");
        Button detailsButton = new Button("Voir plus");

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
                            setGraphic(row);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });
    
        
        
        // Pour le débogage
        //System.out.println("\033[H\033[2J");
        //for (Commune commune : communes) {
        //    System.out.println(commune.getNomCommune());
        //}
    }
    

    public TextField getSearchField() {
        return this.searchField;
    }

    public Label getNumberOfRow(){
        return(this.resultsLabel);
    }
}