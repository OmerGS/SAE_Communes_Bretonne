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
}
