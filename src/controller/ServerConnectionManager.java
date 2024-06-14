package controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import javafx.scene.image.Image;
import view.TrouverCheminCommune;

public class ServerConnectionManager {
    private TrouverCheminCommune trouverCheminCommune;
    private String serverURL;

    public ServerConnectionManager(TrouverCheminCommune trouverCheminCommune, String serverURL) {
        this.trouverCheminCommune = trouverCheminCommune;
        this.serverURL = serverURL;
    }

    public void retrieveImage(List<Integer> cityIds) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(serverURL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"connections\": " + cityIds.toString() + "}"))
                    .build();

            HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
            int statusCode = response.statusCode();

            if (statusCode == 200) {
                Image image = new Image(response.body());
                this.trouverCheminCommune.getImageView().setImage(image);
                this.trouverCheminCommune.setResultLabel("Chemin trouvé :");
            } else {
                this.trouverCheminCommune.setResultLabel("Erreur lors de la récupération du chemin.");
            }
        } catch (IOException | InterruptedException e) {
            this.trouverCheminCommune.setResultLabel("Connection au serveur impossible, ressayer plus tard.");
        }
    }
}
