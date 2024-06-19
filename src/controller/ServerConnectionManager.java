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

/**
 * Manages the connection to the server for retrieving images based on city IDs.
 * This class handles the HTTP requests and updates the view with the retrieved image.
 * If there is an error during the connection or retrieval process, appropriate error messages are set in the view.
 * 
 * <p>This class is designed to be used with a {@link TrouverCheminCommune} view which displays the image
 * and shows result labels based on the server's response.</p>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * TrouverCheminCommune view = new TrouverCheminCommune();
 * ServerConnectionManager manager = new ServerConnectionManager(view, "http://example.com/branch");
 * List<Integer> cityIds = Arrays.asList(1, 2, 3);
 * manager.retrieveImage(cityIds);
 * }
 * </pre>
 * 
 * @see TrouverCheminCommune
 * @see HttpClient
 * @see HttpRequest
 * @see HttpResponse
 * @see Image
 * 
 * @author O.Gunes
 */
public class ServerConnectionManager {

    /**
     * Instance of TrouverCheminCommune to interact with the view.
     */
    private TrouverCheminCommune trouverCheminCommune;

    /**
     * URL of the server to connect to.
     */
    private String serverURL;

    /**
     * Constructor to initialize ServerConnectionManager with the given view and server URL.
     *
     * @param trouverCheminCommune the view to be updated
     * @param serverURL the URL of the server
     */
    public ServerConnectionManager(TrouverCheminCommune trouverCheminCommune, String serverURL) {
        this.trouverCheminCommune = trouverCheminCommune;
        this.serverURL = serverURL;
    }

    /**
     * Retrieves an image from the server based on the given list of city IDs.
     * The image is then set in the ImageView of the view.
     * If the server response is not successful, an error message is set in the view.
     *
     * @param cityIds the list of city IDs to be sent to the server
     */
    public void retrieveImage(List<Integer> cityIds) {
        try {
            // Create an HttpClient instance
            HttpClient httpClient = HttpClient.newHttpClient();
            
            // Build the HTTP POST request with cityIds as the payload
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(serverURL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"connections\": " + cityIds.toString() + "}"))
                    .build();

            // Send the request and receive the response
            HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
            int statusCode = response.statusCode();

            // Check if the response status code is 200 (OK)
            if (statusCode == 200) {
                // Create an Image from the InputStream and set it in the ImageView
                Image image = new Image(response.body());
                this.trouverCheminCommune.getImageView().setImage(image);
                this.trouverCheminCommune.setResultLabel("Chemin trouv\u00E9 :");
            } else {
                // Set an error message if the status code is not 200
                this.trouverCheminCommune.setResultLabel("Erreur lors de la r\u00E9cup\u00E9ration du chemin.");
            }
        } catch (IOException | InterruptedException e) {
            // Set an error message if an exception occurs
            this.trouverCheminCommune.setResultLabel("Connection au serveur impossible, ressayer plus tard.");
        }
    }
}
