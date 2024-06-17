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
}
