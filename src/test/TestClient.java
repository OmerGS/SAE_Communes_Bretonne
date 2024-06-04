package test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestClient extends Application {

    @Override
    public void start(Stage primaryStage) {
        String serverURL = "http://omergs.com:50000/plot_graph"; // Assurez-vous que l'URL correspond à votre serveur
        List<Integer> cityIds = Arrays.asList(29292, 29241, 56260);  // Exemple de IDs de communes
        Image image = null;

        try {
            URL url = new URL(serverURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            String jsonInput = "{\"connections\": [" +
                    cityIds.stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(",")) +
                    "]}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            try (InputStream inputStream = connection.getInputStream()) {
                image = new Image(inputStream);
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (image != null) {
            ImageView imageView = new ImageView(image);
            // Ajuster la taille de l'image
            imageView.setFitWidth(800);  // Définir la largeur désirée
            imageView.setFitHeight(600); // Définir la hauteur désirée
            imageView.setPreserveRatio(true); // Conserver le ratio d'aspect

            StackPane root = new StackPane(imageView);
            Scene scene = new Scene(root, 800, 600);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Carte de la Bretagne");
            primaryStage.show();
        } else {
            System.out.println("Failed to load image.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
