package data;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Properties;

import dao.PasswordUtil;

public class UserService {

    private Properties loadDatabaseProperties() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("../config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    public void createUser(String nom, String prenom, String email, String plainPassword) {
        try {
            // Générer le sel
            byte[] salt = PasswordUtil.getSalt();

            // Hacher le mot de passe
            String hashedPassword = PasswordUtil.hashPassword(plainPassword, salt);

            // Créer une instance d'utilisateur
            Utilisateur utilisateur = new Utilisateur(nom, prenom, email, hashedPassword, Base64.getEncoder().encodeToString(salt));

            saveToDatabase(utilisateur);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private int userIsAdmin(String email){
        int valeurReturn = 0;

        if(email.contains("@univ-ubs.fr")){
            valeurReturn = 1;
        }

        return(valeurReturn);
    }

    private void saveToDatabase(Utilisateur utilisateur) {
        Properties props = loadDatabaseProperties();
        String url = props.getProperty("db.url");
        String userDB = props.getProperty("db.user"); 
        String motDePasseDB = props.getProperty("db.password");

        try (Connection connexion = DriverManager.getConnection(url, userDB, motDePasseDB)) {
            String requeteSQL = "INSERT INTO Utilisateur (nom, prenom, email, motDePasse, salt, isAdmin) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                // Définir les valeurs des paramètres
                preparedStatement.setString(1, utilisateur.getNom());
                preparedStatement.setString(2, utilisateur.getPrenom());
                preparedStatement.setString(3, utilisateur.getEmail());
                preparedStatement.setString(4, utilisateur.getMotDePasse());
                preparedStatement.setString(5, utilisateur.getSalt());
                preparedStatement.setInt(6, userIsAdmin(utilisateur.getEmail()));

                // Exécuter la requête d'insertion
                preparedStatement.executeUpdate();
                System.out.println("Utilisateur enregistré avec succès dans la base de données.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean emailExists(String email) {
        Properties props = loadDatabaseProperties();
        String url = props.getProperty("db.url");
        String userDB = props.getProperty("db.user"); 
        String motDePasseDB = props.getProperty("db.password");

        try (Connection connexion = DriverManager.getConnection(url, userDB, motDePasseDB)) {
            String requeteSQL = "SELECT COUNT(*) FROM Utilisateur WHERE email = ?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validateLogin(String email, String plainPassword) {
        Properties props = loadDatabaseProperties();
        String url = props.getProperty("db.url");
        String userDB = props.getProperty("db.user"); 
        String motDePasseDB = props.getProperty("db.password");

        try (Connection connexion = DriverManager.getConnection(url, userDB, motDePasseDB)) {
            String requeteSQL = "SELECT motDePasse, salt FROM Utilisateur WHERE email = ?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String storedHashedPassword = resultSet.getString("motDePasse");
                        String storedSalt = resultSet.getString("salt");
                        String hashedPassword = PasswordUtil.hashPassword(plainPassword, Base64.getDecoder().decode(storedSalt));
                        return storedHashedPassword.equals(hashedPassword);
                    }
                }
            } catch (SQLException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}