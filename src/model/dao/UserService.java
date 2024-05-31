package dao;

import data.Utilisateur;
import view.misc.EmailService;
import view.misc.PasswordUtil;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Random;
import javax.mail.MessagingException;

/**
* Class which contains userService, this class allows manipulating Utilisateur class.
* We can add the Utilisateur to the database, check if user is in database, add user in database.
*
* @author O.Gunes
*/
public class UserService {

    /**
    * This method creates Utilisateur and hashes the password.
    * The hash of password allows security from database breaches.
    *
    * @param nom The surname of user
    * @param prenom The name of user
    * @param email The email of user
    * @param plainPassword The password (without hash)
    */
    public void createUser(String nom, String prenom, String email, String plainPassword) {
        try {
            byte[] salt = PasswordUtil.getSalt();

            String hashedPassword = PasswordUtil.hashPassword(plainPassword, salt);

            // Create an instance of Utilisateur
            Utilisateur utilisateur = new Utilisateur(nom, prenom, email, hashedPassword, Base64.getEncoder().encodeToString(salt));

            saveToDatabase(utilisateur);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
    * Private method which checks if the user is an admin
    * 
    * @param email The email of the user
    * @return 1 if admin, else return 0.
    */
    private int userIsAdmin(String email){
        return email.contains("@univ-ubs.fr") ? 1 : 0;
    }

    /**
    * Saves the user to the database.
    *
    * @param utilisateur The user to save into the database.
    */
    private void saveToDatabase(Utilisateur utilisateur) {
        // Connection to the database
        try (Connection connexion = ConnectionManager.getConnection()) {
            // The SQL query
            String requeteSQL = "INSERT INTO Utilisateur (nom, prenom, email, motDePasse, salt, isAdmin) VALUES (?, ?, ?, ?, ?, ?)";

            // Sending the SQL query, replacing "?" with the values
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                
                // The parameters to send into the database.
                preparedStatement.setString(1, utilisateur.getNom());
                preparedStatement.setString(2, utilisateur.getPrenom());
                preparedStatement.setString(3, utilisateur.getEmail());
                preparedStatement.setString(4, utilisateur.getMotDePasse());
                preparedStatement.setString(5, utilisateur.getSalt());
                preparedStatement.setInt(6, userIsAdmin(utilisateur.getEmail()));

                preparedStatement.executeUpdate();
                System.out.println("Utilisateur enregistré avec succès dans la base de données.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
    * Public method which allows updating the password of the user.
    *
    * @param email The email of the user
    * @param newPassword The plain password of the user
    * @throws SQLException We can throw SQL exception if the connection doesn't establish
    */
    public void updatePassword(String email, String newPassword) throws SQLException {
        try {
            // We get a new salt (it allows to hash the password)
            byte[] newSalt = PasswordUtil.getSalt();

            // We hash the new password
            String hashedPassword = PasswordUtil.hashPassword(newPassword, newSalt);

            // We encode the salt to transform it into a String.
            String encodedSalt = Base64.getEncoder().encodeToString(newSalt);

            try (Connection connexion = ConnectionManager.getConnection()) {
                // SQL query
                String updateSQL = "UPDATE Utilisateur SET motDePasse = ?, salt = ? WHERE email = ?";

                try (PreparedStatement preparedStatement = connexion.prepareStatement(updateSQL)) {
                    preparedStatement.setString(1, hashedPassword);
                    preparedStatement.setString(2, encodedSalt);
                    preparedStatement.setString(3, email);

                    int rowsUpdated = preparedStatement.executeUpdate();
                    // If a row is updated then we print "Mot de passe mis à jour avec succès"
                    if (rowsUpdated > 0) {
                        System.out.println("Mot de passe mis à jour avec succès.");
                    } else {
                        System.out.println("Aucun utilisateur trouvé avec cet email.");
                    }
                }
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
    * Public method, checks if the email is in the database.
    * 
    * @param email The email of the user to check if it exists in the database
    * @return True if exists, else false.
    */
    public boolean emailExists(String email) {
        boolean returnValue = false;

        try (Connection connexion = ConnectionManager.getConnection()) {
            String requeteSQL = "SELECT COUNT(*) FROM Utilisateur WHERE email = ?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {

                    // If we have one user with the email then return true.
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        returnValue = count > 0;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    /**
    * Checks if the information is valid or not.
    * 
    * @param email The email of the user
    * @param plainPassword The plain password of the user
    * @return True if valid, else false.
    */
    public boolean validateLogin(String email, String plainPassword) {
        boolean returnValue = false;

        try (Connection connexion = ConnectionManager.getConnection()) {
            // We get the password and the salt which was in the database.
            String requeteSQL = "SELECT motDePasse, salt FROM Utilisateur WHERE email = ?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // We get the hashed password from the database.
                        String storedHashedPassword = resultSet.getString("motDePasse");

                        // We get the salt
                        String storedSalt = resultSet.getString("salt");

                        // We hash the password entered by the user.
                        String hashedPassword = PasswordUtil.hashPassword(plainPassword, Base64.getDecoder().decode(storedSalt));

                        // We check if the user entries match with the database.
                        returnValue = storedHashedPassword.equals(hashedPassword);
                    }
                }
            } catch (SQLException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    /**
    * Sends a verification email with a code.
    * 
    * @param email The email of the user
    * @param code The code to send
    * @throws IOException 
    * @throws MessagingException
    */
    public void sendVerificationEmail(String email, String code) throws IOException, MessagingException {
        EmailService emailService = new EmailService();

        String subject = "Code de vérification";
        String message = "Votre code de vérification est : " + code;

        emailService.sendEmail(email, subject, message);
    }

    /**
    * Generates a random code with 6 digits.
    * @return the code
    */
    public int generateVerificationCode(){
        Random random = new Random();
        return random.nextInt(900000) + 100000;
    }
}