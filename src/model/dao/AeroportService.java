package dao;

import data.Aeroport;
import data.Departement;
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
 * Service class to manage operations related to Aeroport entities.
 * This class provides methods to retrieve airport data from the database.
 * 
 * <p>This class relies on {@link DepartementService} to fetch department data
 * and associates each airport with its corresponding department.</p>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * AeroportService aeroportService = new AeroportService();
 * List<Aeroport> aeroports = aeroportService.getAllAeroport();
*   for (Aeroport aeroport : aeroports) {
*     System.out.println(aeroport.getNom());
*   }
 * }
 * </pre>
 * 
 * @see DepartementService
 * @see Aeroport
 * @see Departement
 * @see ConnectionManager
 * 
 * @see InvalidCommuneIdException
 * @see InvalidCommuneNameException
 * 
 * @author R.Peron
 */
public class AeroportService {

    /**
     * Service to manage operations related to Departement.
     */
    private DepartementService departementService;

    /**
     * Constructor to initialize AeroportService.
     */
    public AeroportService() {
        this.departementService = new DepartementService();
    }

    /**
     * Retrieves all airports from the database.
     * This method fetches all departments first and then uses a list
     * associate each airport with its corresponding department.
     *
     * @return a list of Aeroport objects
     * @throws SQLException if there is an error accessing the database
     */
    public List<Aeroport> getAllAeroport() throws SQLException {
        List<Aeroport> aeroports = new ArrayList<>();

        // Fetch all departments and map them by their IDs
        List<Departement> departementList = departementService.getAllDepartement();
        Map<Integer, Departement> departementMap = new HashMap<>();
        for (Departement departement : departementList) {
            departementMap.put(departement.getIdDep(), departement);
        }

        String query = "SELECT a.nom, a.adresse, a.leDepartement " +
                       "FROM Aeroport a " +
                       "JOIN Departement d ON a.leDepartement = d.idDep";

        // Establish a connection and execute the query
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Iterate through the result set and create Aeroport objects
            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String adresse = resultSet.getString("adresse");
                int leDepartementId = resultSet.getInt("leDepartement");

                // Get the corresponding department from the map
                Departement departement = departementMap.get(leDepartementId);

                try {
                    // Create a new Aeroport object and add it to the list
                    Aeroport aeroport = new Aeroport(nom, adresse, departement);
                    aeroports.add(aeroport);
                } catch (InvalidCommuneIdException | InvalidCommuneNameException e) {
                    e.printStackTrace();
                }
            }
        }
        return aeroports;
    }


    public void insertAeroport(Aeroport aeroport) {
        try (Connection connexion = ConnectionManager.getConnection()) {
            String requeteSQL = "INSERT INTO Aeroport (nom, adresse, leDepartement) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.setString(1, aeroport.getNom());
                preparedStatement.setString(2, aeroport.getAdresse());
                preparedStatement.setInt(3, aeroport.getDepartement().getIdDep());
                preparedStatement.executeUpdate();
                System.out.println("Aéroport inséré avec succès dans la base de données.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAeroport(String nom, String newAdresse, int newDepartementId) {
        try (Connection connexion = ConnectionManager.getConnection()) {
            String requeteSQL = "UPDATE Aeroport SET adresse = ?, leDepartement = ? WHERE nom = ?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.setString(1, newAdresse);
                preparedStatement.setInt(2, newDepartementId);
                preparedStatement.setString(3, nom);
                preparedStatement.executeUpdate();
                System.out.println("Aéroport mis à jour avec succès.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropAeroport(String nom) {
        try (Connection connexion = ConnectionManager.getConnection()) {
            String requeteSQL = "DELETE FROM Aeroport WHERE nom = ?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.setString(1, nom);
                preparedStatement.executeUpdate();
                System.out.println("Aéroport supprimé avec succès de la base de données.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void dropAllAeroports() {
        try (Connection connexion = ConnectionManager.getConnection()) {
            String requeteSQL = "DELETE FROM Aeroport";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.executeUpdate();
                System.out.println("Tous les aéroports ont été supprimés de la base de données.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
