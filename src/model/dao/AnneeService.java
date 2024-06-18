package dao;

import data.Annee;
import data.exceptions.InvalidCommuneIdException;
import data.exceptions.InvalidCommuneNameException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class to manage operations related to Annee entities.
 * This class provides methods to retrieve year data from the database.
 * 
 * <p>This class interacts with the database to fetch all year records and
 * creates {@link Annee} objects based on the retrieved data.</p>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * AnneeService anneeService = new AnneeService();
 * List<Annee> annees = anneeService.getAllAnnee();
 *   for (Annee annee : annees) {
 *     System.out.println(annee.getAnnee() + ": " + annee.getTauxInflation());
 *   }
 * }
 * </pre>
 * 
 * @see Annee
 * @see ConnectionManager
 * 
 * @see InvalidCommuneIdException
 * @see InvalidCommuneNameException
 * 
 * @author R.Peron
 */
public class AnneeService {

    /**
     * Retrieves all years from the database.
     * This method fetches all records from the "Annee" table and creates a list of {@link Annee} objects.
     *
     * @return a list of Annee objects
     * @throws SQLException if there is an error accessing the database
     */
    public List<Annee> getAllAnnee() throws SQLException {
        List<Annee> annees = new ArrayList<>();
        
        String query = "SELECT * FROM Annee";

        // Establish a connection and execute the query
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Iterate through the result set and create Annee objects
            while (resultSet.next()) {
                int annee = resultSet.getInt("annee");
                double tauxInflation = resultSet.getDouble("tauxInflation");

                try {
                    // Create a new Annee object and add it to the list
                    Annee lannee = new Annee(annee, tauxInflation);
                    annees.add(lannee);
                } catch (InvalidCommuneIdException | InvalidCommuneNameException e) {
                    e.printStackTrace();
                }
            }
        }
        return annees;
    }



    public void insertAnnee(Annee annee) {
        try (Connection connexion = ConnectionManager.getConnection()) {
            String requeteSQL = "INSERT INTO Annee (annee, tauxInflation) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.setInt(1, annee.getAnnee());
                preparedStatement.setDouble(2, annee.getTauxInflation());
                preparedStatement.executeUpdate();
                System.out.println("Année insérée avec succès dans la base de données.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateAnnee(int annee, double newTauxInflation) {
        try (Connection connexion = ConnectionManager.getConnection()) {
            String requeteSQL = "UPDATE Annee SET tauxInflation = ? WHERE annee = ?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.setDouble(1, newTauxInflation);
                preparedStatement.setInt(2, annee);
                preparedStatement.executeUpdate();
                System.out.println("Année mise à jour avec succès.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void dropAnnee(int annee) {
        try (Connection connexion = ConnectionManager.getConnection()) {
            String requeteSQL = "DELETE FROM Annee WHERE annee = ?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.setInt(1, annee);
                preparedStatement.executeUpdate();
                System.out.println("Année supprimée avec succès de la base de données.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropAllAnnees() {
        try (Connection connexion = ConnectionManager.getConnection()) {
            String requeteSQL = "DELETE FROM Annee";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.executeUpdate();
                System.out.println("Toutes les années ont été supprimées de la base de données.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
