package dao;

import data.Departement;
import data.exceptions.InvalidCommuneIdException;
import data.exceptions.InvalidCommuneNameException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class to manage operations related to Departement entities.
 * Provides methods to retrieve department data from the database.
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * DepartementService departementService = new DepartementService();
 * List<Departement> departements = departementService.getAllDepartement();
 * // Use 'departements' for further processing
 * }
 * </pre>
 * 
 * @author R.Peron
 */
public class DepartementService {

    /**
     * Default constructor.
     */
    public DepartementService(){

    }

    /**
     * Retrieves all departments from the database.
     *
     * @return a list of Departement objects
     * @throws SQLException if there is an error accessing the database
     */
    public List<Departement> getAllDepartement() throws SQLException {
        List<Departement> departements = new ArrayList<>();
        
        String query = "SELECT * FROM Departement";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int idDep = resultSet.getInt("idDep");
                String nomDep = resultSet.getString("nomDep");
                double investissementCulturel2019 = resultSet.getDouble("investissementCulturel2019");

                try {
                    Departement departement = new Departement(idDep, nomDep, investissementCulturel2019);
                    departements.add(departement);
                } catch (InvalidCommuneIdException | InvalidCommuneNameException e) {
                    e.printStackTrace();
                }
            }
        }
        return departements;
    }




    public void updateDepartement(int idDep, String newNomDep, double newInvestCulture2019) {
        try (Connection connexion = ConnectionManager.getConnection()) {
            String requeteSQL = "UPDATE Departement SET nomDep = ?, investissementCulturel2019 = ? WHERE idDep = ?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.setString(1, newNomDep);
                preparedStatement.setDouble(2, newInvestCulture2019);
                preparedStatement.setInt(3, idDep);
                preparedStatement.executeUpdate();
                System.out.println("Département mis à jour avec succès.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertDepartement(Departement departement) {
        try (Connection connexion = ConnectionManager.getConnection()) {
            String requeteSQL = "INSERT INTO Departement (idDep, nomDep, investissementCulturel2019) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.setInt(1, departement.getIdDep());
                preparedStatement.setString(2, departement.getNomDep());
                preparedStatement.setDouble(3, departement.getInvesCulture2019());
                preparedStatement.executeUpdate();
                System.out.println("Département inséré avec succès dans la base de données.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void dropDepartement(int idDep) {
        try (Connection connexion = ConnectionManager.getConnection()) {
            String requeteSQL = "DELETE FROM Departement WHERE idDep = ?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.setInt(1, idDep);
                preparedStatement.executeUpdate();
                System.out.println("Département supprimé avec succès de la base de données.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void dropAllDepartements() {
        try (Connection connexion = ConnectionManager.getConnection()) {
            String requeteSQL = "DELETE FROM Departement";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.executeUpdate();
                System.out.println("Tous les départements ont été supprimés de la base de données.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}