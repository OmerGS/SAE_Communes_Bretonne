package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import controller.Controller;

/**
 * The AccountPage class represents the user account page GUI.
 * It displays user information and provides interaction options.
 * 
 * @author O.Gunes
 */
public class AccountPage extends Application {
    private Controller controller;
    private ImageView userIcon;
    private VBox menuBox;
    private Button cheminCourtButton;
    private Button reloadDatabase;
    private Label nameLabel;
    private Label firstNameLabel;
    private Hyperlink emailLink;
    private Button deleteButton;
    private Button disconnectButton;
    private Button modifyButton;
    private Button saveButton;
    private TextField nameField;
    private TextField firstNameField;
    private TextField emailField;
    private Label admin;

    /**
     * Public getter for Save
     * 
     * @return
     */
    public Button getSaveButton() {
        return saveButton;
    }

    public TextField getNameField() {
        return nameField;
    }

    public TextField getFirstNameField() {
        return firstNameField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getDisconnectButton() {
        return disconnectButton;
    }

    public Button getModifyButton() {
        return modifyButton;
    }

    public ImageView getUserIcon() {
        return userIcon;
    }

    public VBox getMenuBox() {
        return menuBox;
    }

    public Button getCheminCourtButton() {
        return cheminCourtButton;
    }

    public Button getReloadDatabase() {
        return reloadDatabase;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public Label getFirstNameLabel() {
        return firstNameLabel;
    }

    public Hyperlink getEmailLink() {
        return emailLink;
    }

    public AccountPage(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(menuBar(), 800, 600);
        scene.getStylesheets().add("file:../resources/css/style.css");

        primaryStage.setScene(scene);
        primaryStage.setTitle("Compte Utilisateur");
        primaryStage.show();

        this.userIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            this.controller.returnToMainPage();
        });

        this.deleteButton.setOnAction(this.controller);
        this.disconnectButton.setOnAction(this.controller);
        this.modifyButton.setOnAction(this.controller);
        this.saveButton.setOnAction(this.controller);
    }

    private VBox menuBar() {
        ImageView logo = new ImageView(new Image("file:../resources/image/logo_bretagne.png"));
        logo.setFitWidth(50);
        logo.setFitHeight(50);
        logo.setClip(new Circle(25, 25, 25));

        // User bar
        HBox userBox = new HBox(10);
        userBox.setPadding(new Insets(10));
        userBox.setStyle("-fx-padding: 10;");

        this.userIcon = null;
        try {
            this.userIcon = new ImageView(new Image("file:../resources/image/carte_bretagne.png"));
            this.userIcon.setFitHeight(30);
            this.userIcon.setFitWidth(30);
        } catch (NullPointerException e) {
            System.out.println("user.png not found");
        }

        VBox menuBar = new VBox(2);
        menuBar.setAlignment(Pos.TOP_RIGHT);

        // Ajouter trois barres pour l'icône du menu
        for (int i = 0; i < 3; i++) {
            Rectangle bar = new Rectangle(30, 5, Color.WHITE);
            bar.setArcWidth(5);
            bar.setArcHeight(5);
            menuBar.getChildren().add(bar);
        }

        menuBar.setOnMouseClicked(e -> {
            toggleMenu();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        userBox.getChildren().addAll(userIcon, spacer, menuBar);

        // Top bar combining search and user bar
        HBox topBar = new HBox(10);
        topBar.setStyle("-fx-padding: 10; -fx-background-color: #000000; -fx-text-fill: #fff;");
        topBar.getChildren().addAll(logo, spacer, userBox);

        // Menu box for the side menu
        menuBox = createMenuBox();
        menuBox.setVisible(false);

        StackPane mainPane = new StackPane();
        VBox infoPanel = createInfoPanel();
        infoPanel.setMaxWidth(400); // Set a maximum width for the info panel
        infoPanel.setMaxHeight(500); // Set a maximum height for the info panel
        mainPane.getChildren().addAll(infoPanel, menuBox);
        StackPane.setAlignment(infoPanel, Pos.CENTER); // Center the info panel
        StackPane.setAlignment(menuBox, Pos.TOP_RIGHT); // Position the menu box in the top right

        VBox mainBox = new VBox(10);
        mainBox.setStyle("-fx-background-color: #87CEEB;"); // Blue background
        mainBox.getChildren().addAll(topBar, mainPane);
        VBox.setVgrow(mainPane, Priority.ALWAYS);

        return mainBox;
    }

    private VBox createMenuBox() {
        VBox menuBox = new VBox(10);
        menuBox.setStyle("-fx-background-color: #000000; -fx-padding: 20px;");
        menuBox.setAlignment(Pos.CENTER_RIGHT);
        menuBox.setMaxWidth(400);

        this.cheminCourtButton = new Button("Chemin Entre 2 commune");
        this.cheminCourtButton.setStyle("-fx-text-fill: #fff; -fx-font-size: 16px;");
        this.cheminCourtButton.setOnAction(this.controller);

        Label exportDataLabel = new Label("Exporter Données");
        exportDataLabel.setStyle("-fx-text-fill: #fff; -fx-font-size: 16px;");

        this.reloadDatabase = new Button("Rechargez la base de données");
        this.reloadDatabase.setStyle("-fx-text-fill: #fff; -fx-font-size: 16px;");
        this.reloadDatabase.setOnAction(this.controller);

        menuBox.getChildren().addAll(this.cheminCourtButton, exportDataLabel, this.reloadDatabase);
        return menuBox;
    }

    private void toggleMenu() {
        menuBox.setVisible(!menuBox.isVisible());
    }

    private VBox createInfoPanel() {
        VBox infoPanel = new VBox(10);
        infoPanel.setPadding(new Insets(20));
        infoPanel.setStyle("-fx-background-color: #d3d3d3; -fx-background-radius: 20; -fx-padding: 20; -fx-border-radius: 20;");
        infoPanel.setAlignment(Pos.CENTER);

        HBox topRightBox = new HBox();
        topRightBox.setAlignment(Pos.TOP_RIGHT);
        this.modifyButton = new Button("MODIFIER");
        this.modifyButton.setStyle("-fx-background-color: green; -fx-text-fill: black;");
        topRightBox.getChildren().add(this.modifyButton);

        this.saveButton = new Button("SAVE");
        this.saveButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        this.saveButton.setVisible(false);

        this.nameLabel = new Label("Omer");
        this.firstNameLabel = new Label("GUNES");
        this.emailLink = new Hyperlink("27omerf@gmail.com");
        this.admin = new Label("Impossible de recuperer");

        this.nameField = new TextField();
        this.firstNameField = new TextField();
        this.emailField = new TextField();

        nameField.setVisible(false);
        firstNameField.setVisible(false);
        emailField.setVisible(false);

        // StackPanes to overlap labels and text fields
        StackPane nameStack = new StackPane(nameLabel, nameField);
        StackPane firstNameStack = new StackPane(firstNameLabel, firstNameField);
        StackPane emailStack = new StackPane(emailLink, emailField);

        // Set alignment for text fields
        StackPane.setAlignment(nameField, Pos.CENTER_LEFT);
        StackPane.setAlignment(firstNameField, Pos.CENTER_LEFT);
        StackPane.setAlignment(emailField, Pos.CENTER_LEFT);

        HBox nameBox = new HBox(10, new Label("Nom :"), nameStack);
        HBox firstNameBox = new HBox(10, new Label("Prénom :"), firstNameStack);
        HBox emailBox = new HBox(10, new Label("Email :"), emailStack);
        HBox adminBox = new HBox(10, new Label("Role :"), admin);

        this.deleteButton = new Button("SUPPRIMER");
        deleteButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white;");

        this.disconnectButton = new Button("Deconnexion");
        disconnectButton.setStyle("-fx-background-color: #8293D2; -fx-text-fill: black;");

        infoPanel.getChildren().addAll(topRightBox, nameBox, firstNameBox, emailBox, adminBox, deleteButton, disconnectButton, saveButton);

        return infoPanel;
    }


    public Label getAdmin() {
        return admin;
    }

    public static void main(String[] args) {
        launch(args);
    }
}