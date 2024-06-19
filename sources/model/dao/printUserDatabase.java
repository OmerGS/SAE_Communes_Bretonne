package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Class to retrieve and print users from the database.
 * This class connects to the database using configuration properties
 * and executes a SQL query to fetch user information.
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * public static void main(String[] args) {
 *     printUserDatabase.printUsers();
 * }
 * </pre>
 * 
 * @author O.Gunes
 */
public class printUserDatabase {

    /**
     * Main method to retrieve and print users from the database.
     */
    public static void main(String[] args) {
        printUsers();
    }

    /**
     * Retrieves and prints users from the database.
     */
    public static void printUsers() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("../properties/config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Database connection information
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");
        
        // SQL query
        String sqlQuery = "SELECT * FROM Utilisateur";
        
        try {
            // Establishing database connection
            Connection connection = DriverManager.getConnection(url, user, password);
            
            // Creating statement
            Statement statement = connection.createStatement();
            
            // Executing SQL query
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            
            // Iterating through results and printing user information
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");
                String motDePasse = resultSet.getString("motDePasse");
                int isAdmin = resultSet.getInt("isAdmin");

                System.out.println("ID : " + id);
                System.out.println("Nom : " + nom);
                System.out.println("Prenom : " + prenom);
                System.out.println("Email : " + email);
                System.out.println("Mot de passe : " + motDePasse);
                System.out.println("Admin : " + isAdmin);
                System.out.println("------------------------------------------");
            }
            
            // Closing resources
            resultSet.close();
            statement.close();
            connection.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
