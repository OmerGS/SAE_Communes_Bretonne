package dao;

import data.Gare;
import data.Commune;
import data.exceptions.InvalidCommuneIdException;
import data.exceptions.InvalidCommuneNameException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class to manage operations related to Gare entities.
 * Provides methods to retrieve railway station data from the database.
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * GareService gareService = new GareService();
 * List<Gare> gares = gareService.getAllGares();
 * // Use 'gares' for further processing
 * }
 * </pre>
 * 
 * @author R.Peron
 */
public class GareService {

    private CommuneService communeService;

    /**
     * Default constructor.
     * Initializes the CommuneService instance.
     */
    public GareService() {
        this.communeService = new CommuneService();
    }

    /**
     * Retrieves all railway stations from the database.
     * This method fetches all communes first and then associates each railway station
     * with its corresponding commune.
     *
     * @return a list of Gare objects
     * @throws SQLException if there is an error accessing the database
     */
    public List<Gare> getAllGares() throws SQLException {
        List<Gare> gares = new ArrayList<>();

        // Fetch all communes
        List<Commune> communeList = communeService.getAllCommunes();
        Map<Integer, Commune> communeMap = new HashMap<>();
        for (Commune commune : communeList) {
            communeMap.put(commune.getIdCommune(), commune);
        }

        String query = "SELECT g.codeGare, g.nomGare, g.estFret, g.estVoyageur, g.laCommune " +
                       "FROM Gare g " +
                       "JOIN Commune c ON g.laCommune = c.idCommune";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int codeGare = resultSet.getInt("codeGare");
                String nomGare = resultSet.getString("nomGare");
                boolean estFret = resultSet.getBoolean("estFret");
                boolean estVoyageur = resultSet.getBoolean("estVoyageur");
                int laCommuneId = resultSet.getInt("laCommune");

                // Get the corresponding commune from the map
                Commune commune = communeMap.get(laCommuneId);

                try {
                    // Create a new Gare object and add it to the list
                    Gare gare = new Gare(codeGare, nomGare, estFret, estVoyageur, commune);
                    gares.add(gare);
                } catch (InvalidCommuneIdException | InvalidCommuneNameException e) {
                    e.printStackTrace();
                }
            }
        }
        return gares;
    }
}
