
package view;

import controller.Controller;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
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
    private Button mainPageButton;



    public Button getTransportButton() {
        return mainPageButton;
    }

    public TrouverCheminCommune(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void start(Stage primaryStage) {
        
        // Top Bar
        HBox topBar = createTopBar();
        
        
        // Input Box


        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setPadding(new Insets(10));
        inputBox.setStyle("-fx-background-color: #5F606D; -fx-padding: 10; -fx-border-radius: 10px; -fx-background-radius: 10px; ");
        
        this.startCommuneField = new TextField();
        this.startCommuneField.setPromptText("Commune de d\u00e9part");
        this.startCommuneField.setPrefWidth(150);
        this.startCommuneField.setStyle("-fx-background-color: #fff; -fx-border-color: #FFFFFF; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        this.endCommuneField = new TextField();
        this.endCommuneField.setPromptText("Commune d'arriv\u00e9e");
        this.endCommuneField.setPrefWidth(150);
        this.endCommuneField.setStyle("-fx-background-color: #fff; -fx-border-color: #FFFFFF; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        
        inputBox.getChildren().addAll(startCommuneField, endCommuneField);
        

        
        this.findPathButton = new Button("Trouver Chemin");
        this.findPathButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: #fff; -fx-background-radius: 10px; -fx-border-radius: 10px;");
        this.findPathButton.setOnAction(this.controller);
        

        
        // Result Label
        this.resultLabel = new Label();
        this.resultLabel.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        
        // Result Button Box
        this.resultButtonBox = new HBox();
        
        // Image View
        this.imageView = new ImageView();
        this.imageView.setImage(null);
        this.imageView.setFitWidth(800);
        this.imageView.setFitHeight(600);
        StackPane imagePane = new StackPane(this.imageView);

        VBox centerBox = new VBox(10);
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerBox.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #6C7BD0 0%, white 50%, #6C7BD0 100%);");
        centerBox.getChildren().addAll(inputBox,this.findPathButton,this.resultLabel,this.resultButtonBox,imagePane);

        

        
        // Menu Box (assuming menuBox is defined elsewhere)
        this.menuBox = createMenuBox();
        menuBox.setVisible(false);

        this.controller.verifyAdmin();

        StackPane mainPane = new StackPane();
        mainPane.getChildren().addAll(centerBox, menuBox);
        StackPane.setAlignment(menuBox, Pos.CENTER_RIGHT);


        // Main container with topBar and main content
        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(mainPane);
        root.setStyle("-fx-background-color: #ffffff;");
        
        // Scene setup
        Scene scene = new Scene(root, 1600, 900);
        

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Trouver Chemin entre Communes");
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
        //logo.setStyle("-fx-background-color: #ffffff; -fx-shape: \"M20 0 L40 20 L20 40 L0 20 Z\";");

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
        menuBox.setAlignment(Pos.TOP_LEFT);
        menuBox.setMaxWidth(400);

        this.mainPageButton = createButtonWithIcon("Page d'accueil", "file:../resources/image/home.png");
        this.editData = createButtonWithIcon("Modifier les données", "file:../resources/image/edit.png");
        this.exportDataButton = createButtonWithIcon("Exporter Données", "file:../resources/image/export.png");
        this.reloadDatabase = createButtonWithIcon("Rechargez la base de données", "file:../resources/image/reload.png");
    
        menuBox.getChildren().addAll(this.mainPageButton, this.editData, this.exportDataButton, this.reloadDatabase);
        return menuBox;
    }

    private Button createButtonWithIcon(String text, String iconPath) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-border-color: transparent;");
        //button.setOnAction(this.controller);
    
        ImageView icon = new ImageView(new Image(iconPath));
        icon.setFitHeight(16); // Adjust the size as needed
        icon.setFitWidth(16);  // Adjust the size as needed
    
        button.setGraphic(icon);
        button.setContentDisplay(ContentDisplay.LEFT); // To position the icon on the left of the text
        return button;
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
