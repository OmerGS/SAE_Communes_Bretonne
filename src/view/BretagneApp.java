package view;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BretagneApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Créer le VBox racine
        VBox root = new VBox();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: white;"); // Définir le fond de l'application en blanc
        root.setAlignment(Pos.TOP_CENTER);

        // Créer la barre de menu
        VBox menuBar = createMenuBar();
        root.getChildren().add(menuBar);

        // Créer le HBox pour contenir les cartes
        HBox cardContainer = new HBox(40);
        cardContainer.setAlignment(Pos.CENTER);
        cardContainer.setPadding(new Insets(20));

        // Créer les cartes
        StackPane communesCard = createCard("file:../resources/image/fonds/image5.jpg", "Les Communes Bretonnes", "Les communes de Bretagne sont connues pour leur riche patrimoine historique et culturel.");
        StackPane connectionCard = createCard("file:../resources/image/fonds/image3.jpg", "Connexion", "Veuillez vous connecter pour accéder à toutes les fonctionnalités.");

        // Ajouter les cartes au conteneur
        cardContainer.getChildren().addAll(communesCard, connectionCard);

        // Centrer le conteneur de cartes dans l'espace restant
        VBox.setVgrow(cardContainer, Priority.ALWAYS);
        root.getChildren().add(cardContainer);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 1200, 600);

        primaryStage.setTitle("Bretagne App");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    private VBox createMenuBar() {
        VBox menuBar = new VBox(2);
        menuBar.setAlignment(Pos.TOP_RIGHT);

        // Ajouter trois barres pour l'icône du menu
        for (int i = 0; i < 3; i++) {
            Rectangle bar = new Rectangle(30, 5, Color.BLACK);
            bar.setArcWidth(5);
            bar.setArcHeight(5);
            menuBar.getChildren().add(bar);
        }

        // Créer les options du menu
        VBox menuOptions = new VBox(10);
        menuOptions.setPadding(new Insets(10));
        menuOptions.setStyle("-fx-background-color: #333333; -fx-background-radius: 5;");
        menuOptions.setAlignment(Pos.CENTER);
        menuOptions.setVisible(false);

        Label option1 = new Label("Option 1");
        option1.setTextFill(Color.WHITE);
        option1.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label option2 = new Label("Option 2");
        option2.setTextFill(Color.WHITE);
        option2.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label option3 = new Label("Option 3");
        option3.setTextFill(Color.WHITE);
        option3.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        menuOptions.getChildren().addAll(option1, option2, option3);

        menuBar.setOnMouseClicked(e -> {
            if (menuOptions.isVisible()) {
                FadeTransition fadeOut = new FadeTransition(Duration.millis(300), menuOptions);
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);
                fadeOut.setOnFinished(event -> menuOptions.setVisible(false));
                fadeOut.play();
            } else {
                menuOptions.setVisible(true);
                FadeTransition fadeIn = new FadeTransition(Duration.millis(300), menuOptions);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            }
        });

        VBox menuContainer = new VBox(5);
        menuContainer.setAlignment(Pos.TOP_RIGHT);
        menuContainer.getChildren().addAll(menuBar, menuOptions);

        return menuContainer;
    }

    private StackPane createCard(String imagePath, String title, String description) {
        StackPane card = new StackPane();
        card.setPrefSize(500, 700);
        card.setMaxSize(500, 700);
        card.setStyle("-fx-background-image: url('" + imagePath + "'); -fx-background-size: cover; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: #444444; -fx-border-width: 1; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 0);");

        VBox content = new VBox();
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(15));

        Label titleLabel = new Label(title);
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        titleLabel.setPadding(new Insets(10, 0, 10, 0));
        titleLabel.setVisible(false);

        Text descriptionText = new Text(description);
        descriptionText.setFill(Color.WHITE);
        descriptionText.setWrappingWidth(180);
        descriptionText.setVisible(false);
        descriptionText.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-padding: 10px;");

        content.getChildren().addAll(titleLabel, descriptionText);
        card.getChildren().add(content);

        card.setOnMouseEntered(e -> onCardMouseEntered(titleLabel, descriptionText));
        card.setOnMouseExited(e -> onCardMouseExited(titleLabel, descriptionText));

        return card;
    }

    private void onCardMouseEntered(Label titleLabel, Text descriptionText) {
        FadeTransition fadeInTitle = new FadeTransition(Duration.millis(300), titleLabel);
        fadeInTitle.setFromValue(0.0);
        fadeInTitle.setToValue(1.0);
        titleLabel.setVisible(true);
        fadeInTitle.play();

        FadeTransition fadeInDescription = new FadeTransition(Duration.millis(300), descriptionText);
        fadeInDescription.setFromValue(0.0);
        fadeInDescription.setToValue(1.0);
        descriptionText.setVisible(true);
        fadeInDescription.play();
    }

    private void onCardMouseExited(Label titleLabel, Text descriptionText) {
        FadeTransition fadeOutTitle = new FadeTransition(Duration.millis(300), titleLabel);
        fadeOutTitle.setFromValue(1.0);
        fadeOutTitle.setToValue(0.0);
        fadeOutTitle.setOnFinished(event -> titleLabel.setVisible(false));
        fadeOutTitle.play();

        FadeTransition fadeOutDescription = new FadeTransition(Duration.millis(300), descriptionText);
        fadeOutDescription.setFromValue(1.0);
        fadeOutDescription.setToValue(0.0);
        fadeOutDescription.setOnFinished(event -> descriptionText.setVisible(false));
        fadeOutDescription.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}