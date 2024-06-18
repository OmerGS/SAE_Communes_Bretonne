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
 * @autor R.Peron
 */
public class GareService {
    /**
     * Constructor with dependency injection for CommuneService.
     * 
     * @param communeService an instance of CommuneService
     */
    public GareService() {
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

                try {
                    Gare gare = new Gare(codeGare, nomGare, estFret, estVoyageur, laCommuneId);
                    gares.add(gare);
                } catch (InvalidCommuneIdException | InvalidCommuneNameException e) {
                    e.printStackTrace();
                }
            }
        }
        return gares;
    }

    public void insertGare(Gare gare) {
        try (Connection connexion = ConnectionManager.getConnection()) {
            String requeteSQL = "INSERT INTO Gare (codeGare, nomGare, estFret, estVoyageur, laCommune) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.setInt(1, gare.getCodeGare());
                preparedStatement.setString(2, gare.getNomGare());
                preparedStatement.setBoolean(3, gare.isEstFret());
                preparedStatement.setBoolean(4, gare.isEstVoyageur());
                preparedStatement.setInt(5, gare.getCommune());

                preparedStatement.executeUpdate();
                System.out.println("Gare insérée avec succès dans la base de données.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGare(int codeGare, String newNomGare, boolean newEstFret, boolean newEstVoyageur, int newLaCommune) {
        try (Connection connexion = ConnectionManager.getConnection()) {
            String requeteSQL = "UPDATE Gare SET nomGare = ?, estFret = ?, estVoyageur = ?, laCommune = ? WHERE codeGare = ?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.setString(1, newNomGare);
                preparedStatement.setBoolean(2, newEstFret);
                preparedStatement.setBoolean(3, newEstVoyageur);
                preparedStatement.setInt(4, newLaCommune);
                preparedStatement.setInt(5, codeGare);

                preparedStatement.executeUpdate();
                System.out.println("Gare mise à jour avec succès.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropGare(int codeGare) {
        try (Connection connexion = ConnectionManager.getConnection()) {
            String requeteSQL = "DELETE FROM Gare WHERE codeGare = ?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.setInt(1, codeGare);

                preparedStatement.executeUpdate();
                System.out.println("Gare supprimée avec succès de la base de données.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropAllGares() {
        try (Connection connexion = ConnectionManager.getConnection()) {
            String requeteSQL = "DELETE FROM Gare";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.executeUpdate();
                System.out.println("Toutes les gares ont été supprimées de la base de données.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
