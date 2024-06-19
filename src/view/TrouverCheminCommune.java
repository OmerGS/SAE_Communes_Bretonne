package view;

import controller.Controller;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class TrouverCheminCommune extends Application {
    private TextField startCommuneField;
    private ImageView userIcon;
    private TextField endCommuneField;
    private Label resultLabel;
    private Button findPathButton;
    private Controller controller;
    private HBox resultButtonBox;
    private ImageView imageView;
    private VBox menuBox;
    private Button pagePrincipale;
    private Button editData;
    private Button exportDataButton;
    private Button reloadDatabase;


    public TrouverCheminCommune(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #00bfff 10%, #FFFFFF);");
    
        // Création des éléments principaux
        HBox topBar = createTopBar();
    
        VBox contentBox = new VBox(10);
        contentBox.setAlignment(Pos.TOP_CENTER);
        contentBox.setPadding(new Insets(10));
    
        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setStyle("-fx-background-color: #000000; -fx-padding: 10; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        inputBox.setPadding(new Insets(10));
    
        startCommuneField = new TextField();
        startCommuneField.setPromptText("Commune de départ");
        startCommuneField.setPrefWidth(150);
        startCommuneField.setStyle("-fx-background-color: #fff; -fx-border-color: #FFFFFF; -fx-border-radius: 10px; -fx-background-radius: 10px;");
    
        endCommuneField = new TextField();
        endCommuneField.setPromptText("Commune d'arrivée");
        endCommuneField.setPrefWidth(150);
        endCommuneField.setStyle("-fx-background-color: #fff; -fx-border-color: #FFFFFF; -fx-border-radius: 10px; -fx-background-radius: 10px;");
    
        inputBox.getChildren().addAll(startCommuneField, endCommuneField);
    
        findPathButton = new Button("Trouver Chemin");
        findPathButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: #fff; -fx-background-radius: 10px; -fx-border-radius: 10px;");
        findPathButton.setOnAction(controller);
    
        resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
    
        resultButtonBox = new HBox();
    
        // ImageView avec une image de trajet
        Image cheminImage = new Image("file:chemin_image.png"); // Remplacez par le chemin de votre image
        imageView = new ImageView(cheminImage);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(800); // Ajustez en fonction de la taille réelle de votre image
        imageView.setFitHeight(600); // Ajustez en fonction de la taille réelle de votre image
    
        StackPane imagePane = new StackPane(imageView);
    
        // Ajout des éléments à contentBox
        contentBox.getChildren().addAll(topBar, inputBox, findPathButton, resultLabel, resultButtonBox, imagePane);
    
        // Création du menuBox
        menuBox = createMenuBox();
        menuBox.setVisible(false); // Initialiser comme invisible
    
        // Positionnement de contentBox au centre et menuBox en dessous dans le StackPane
        StackPane.setAlignment(contentBox, Pos.CENTER);
        StackPane.setAlignment(menuBox, Pos.BOTTOM_RIGHT);
    
        // Ajout de contentBox et menuBox au StackPane (root)
        root.getChildren().addAll(contentBox, menuBox);
    
        // ScrollPane pour envelopper root (si nécessaire)
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
    
        Scene scene = new Scene(scrollPane, 800, 600); // Taille initiale de la scène
        primaryStage.setScene(scene);
        primaryStage.setTitle("Trouver Chemin entre Communes");
    
        // Positionnement et affichage de la scène
        primaryStage.show();
    }


    
    private HBox createTopBar() {
        HBox topBar = new HBox(10);
        topBar.setStyle("-fx-padding: 10; -fx-background-color: #000000; -fx-text-fill: #fff;");
        topBar.setAlignment(Pos.CENTER_LEFT);

        // Logo
        ImageView logo = new ImageView("file:../resources/image/logo_bretagne.png");
        logo.setFitWidth(50);
        logo.setFitHeight(50);
        logo.setClip(new Circle(25, 25, 25));

        ImageView menuIcon = null;
        try {
            menuIcon = new ImageView(new Image("file:../resources/image/menu.png"));
            menuIcon.setFitHeight(30);
            menuIcon.setFitWidth(30);
        } catch (NullPointerException e) {
            System.out.println("menu.png not found");
        }

        menuIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> toggleMenu());


        this.userIcon = null;
        try {
            this.userIcon = new ImageView(new Image("file:../resources/image/user.png"));
            this.userIcon.setFitHeight(30);
            this.userIcon.setFitWidth(30);
        } catch (NullPointerException e) {
            System.out.println("user.png not found");
        }


        this.userIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            this.controller.connectionClickedTrouverCheminCommune();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(logo, spacer, this.userIcon, menuIcon);
        return topBar;
    }

    private VBox createMenuBox() {
        VBox menuBox = new VBox(10);
        menuBox.setStyle("-fx-background-color: #000000; -fx-padding: 20px;");
        menuBox.setAlignment(Pos.CENTER_RIGHT);
        menuBox.setMaxWidth(300);

        this.pagePrincipale = new Button("Page Principale");
        this.pagePrincipale.setStyle("-fx-text-fill: #fff; -fx-font-size: 16px;");
        this.pagePrincipale.setOnAction(this.controller);

        this.editData = new Button("Modifier les donn\u00e9es");
        this.editData.setStyle("-fx-text-fill: #fff; -fx-font-size: 16px;");
        this.editData.setOnAction(this.controller);

        this.exportDataButton = new Button("Exporter Donn\u00e9es");
        this.exportDataButton.setStyle("-fx-text-fill: #fff; -fx-font-size: 16px;");
        this.exportDataButton.setOnAction(this.controller);

        this.reloadDatabase = new Button("Rechargez la base de donn\u00e9es");
        this.reloadDatabase.setStyle("-fx-text-fill: #fff; -fx-font-size: 16px;");
        this.reloadDatabase.setOnAction(this.controller);

        menuBox.getChildren().addAll(this.pagePrincipale, editData, exportDataButton, this.reloadDatabase);
        return menuBox;
    }

    private void toggleMenu() {
        this.menuBox.setVisible(!this.menuBox.isVisible());
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setResultLabel(String label){
        this.resultLabel.setText(label);
    }

    public Button getButton(){
        return(this.findPathButton);
    }

    public TextField getStartCommuneName(){
        return(this.startCommuneField);
    }

    public TextField getEndCommuneName(){
        return(this.endCommuneField);
    }

    public HBox getHBoxBtnStorage(){
        return(this.resultButtonBox);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public ImageView getImageUserIcon() {
        return this.userIcon;
    }

    public Button getExportDataButton(){
        return this.exportDataButton;
    }

    public Button getPagePrincipalButton(){
        return this.pagePrincipale;
    }

    public Button getReloadDatabaseButton(){
        return this.reloadDatabase;
    }

    public Button getEditData(){
        return this.editData;
    }
}