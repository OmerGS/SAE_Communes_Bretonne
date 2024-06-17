package view;

import controller.Controller;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class TrouverCheminCommune extends Application {
    private TextField startCommuneField;
    private TextField endCommuneField;
    private Label resultLabel;
    private Button findPathButton;
    private Controller controller;
    private HBox resultButtonBox;
    private ImageView imageView;

    public TrouverCheminCommune(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void start(Stage primaryStage) {

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.TOP_CENTER);

        HBox topBar = createTopBar();

        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setPadding(new Insets(10));
        inputBox.setStyle("-fx-background-color: #000000; -fx-padding: 10; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        this.startCommuneField = new TextField();
        this.startCommuneField.setPromptText("Commune de d\u00e9part");
        this.startCommuneField.setPrefWidth(150);
        this.startCommuneField.setStyle("-fx-background-color: #fff; -fx-border-color: #FFFFFF; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        this.endCommuneField = new TextField();
        this.endCommuneField.setPromptText("Commune d'arriv\u00e9e");
        this.endCommuneField.setPrefWidth(150);
        this.endCommuneField.setStyle("-fx-background-color: #fff; -fx-border-color: #FFFFFF; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        inputBox.getChildren().addAll(this.startCommuneField, this.endCommuneField);

        this.findPathButton = new Button("Trouver Chemin");
        this.findPathButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: #fff; -fx-background-radius: 10px; -fx-border-radius: 10px;");
        this.findPathButton.setOnAction(this.controller);

        this.resultLabel = new Label();
        this.resultLabel.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        this.resultButtonBox = new HBox();

        // Ajout de l'ImageView
        this.imageView = new ImageView();
        this.imageView.setImage(null);
        this.imageView.setFitWidth(800);
        this.imageView.setFitHeight(600);
        StackPane imagePane = new StackPane(this.imageView);

        root.getChildren().addAll(topBar, inputBox, this.findPathButton, this.resultLabel, this.resultButtonBox, imagePane);

        Scene scene = new Scene(root, 800, 1000);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Trouver Chemin entre Communes");

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        
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

        ImageView menuIcon = new ImageView(new Image("file:../resources/image/menu.png"));
        menuIcon.setFitHeight(30);
        menuIcon.setFitWidth(30);

        ImageView userIcon = new ImageView("file:../resources/image/user.png");
        userIcon.setFitHeight(30);
        userIcon.setFitWidth(30);
        //userIcon.setStyle("-fx-background-color: #ffffff; -fx-shape: \"M0 0 L0 30 L30 15 Z\";");


        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(logo, spacer, userIcon, menuIcon);
        return topBar;
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
}