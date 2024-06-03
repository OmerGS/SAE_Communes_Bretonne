package view;

import controller.Controller;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TrouverCheminCommune extends Application {
    private TextField startCommuneField;
    private TextField endCommuneField;
    private Label resultLabel;
    private Button findPathButton;
    private Controller controller;
    private HBox resultButtonBox;

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
        this.startCommuneField.setPromptText("Commune de départ");
        this.startCommuneField.setPrefWidth(150);
        this.startCommuneField.setStyle("-fx-background-color: #fff; -fx-border-color: #FFFFFF; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        this.endCommuneField = new TextField();
        this.endCommuneField.setPromptText("Commune d'arrivée");
        this.endCommuneField.setPrefWidth(150);
        this.endCommuneField.setStyle("-fx-background-color: #fff; -fx-border-color: #FFFFFF; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        inputBox.getChildren().addAll(this.startCommuneField, this.endCommuneField);

        this.findPathButton = new Button("Trouver Chemin");
        this.findPathButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: #fff; -fx-background-radius: 10px; -fx-border-radius: 10px;");
        this.findPathButton.setOnAction(this.controller);

        this.resultLabel = new Label();
        this.resultLabel.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        this.resultButtonBox = new HBox();

        root.getChildren().addAll(topBar, inputBox, this.findPathButton, this.resultLabel, this.resultButtonBox);

        Scene scene = new Scene(root, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Trouver Chemin entre Communes");
        primaryStage.show();
    }

    private HBox createTopBar() {
        HBox topBar = new HBox(10);
        topBar.setStyle("-fx-padding: 10; -fx-background-color: #000000; -fx-text-fill: #fff;");
        topBar.setAlignment(Pos.CENTER_LEFT);

        // Logo
        Label logo = new Label("Logo");
        logo.setPrefWidth(50);
        logo.setPrefHeight(50);
        logo.setStyle("-fx-background-color: #ffffff; -fx-shape: \"M20 0 L40 20 L20 40 L0 20 Z\";");


        Label userIcon = new Label("User");
        userIcon.setPrefHeight(30);
        userIcon.setPrefWidth(30);
        userIcon.setStyle("-fx-background-color: #ffffff; -fx-shape: \"M0 0 L0 30 L30 15 Z\";");


        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(logo, spacer, userIcon);
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
    
}