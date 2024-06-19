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
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import javax.mail.MessagingException;

/**
 * Class which contains userService, this class allows manipulating Utilisateur class.
 * We can add the Utilisateur to the database, check if user is in database, add user in database.
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * UserService userService = new UserService();
 * userService.createUser("Doe", "John", "john.doe@example.com", "password123");
 * }
 * </pre>
 * 
 * @see data.Utilisateur
 * @see view.misc.EmailService
 * @see view.misc.PasswordUtil
 * @see java.sql.Connection
 * @see java.sql.PreparedStatement
 * @see java.sql.ResultSet
 * 
 * @autor O.Gunes
 */
public class UserService {

    /**
     * Constructs a new UserService instance.
     */
    public UserService() {
    }

    /**
     * This method creates a Utilisateur and hashes the password.
     * The hash of the password provides security from database breaches.
     *
     * @param nom The surname of the user
     * @param prenom The first name of the user
     * @param email The email of the user
     * @param plainPassword The password (without hash)
     */
    public void createUser(String nom, String prenom, String email, String plainPassword) {
        try {
            byte[] salt = PasswordUtil.getSalt();
            String hashedPassword = PasswordUtil.hashPassword(plainPassword, salt);

            Utilisateur utilisateur = new Utilisateur(nom, prenom, email, hashedPassword, Base64.getEncoder().encodeToString(salt));
            saveToDatabase(utilisateur);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * Public method which checks if the user is an admin.
     * 
     * @param email The email of the user
     * @return 1 if admin, else 0.
     */
    public int userIsAdmin(String email) {
        int nbReturn = 0;
        if(email.contains("@etud.univ-ubs.fr") || email.contains("@univ-ubs.fr")){
            nbReturn = 1;
        }

        return(nbReturn);
    }

    /**
     * Saves the user to the database.
     *
     * @param utilisateur The user to save into the database.
     */
    private void saveToDatabase(Utilisateur utilisateur) {
        try (Connection connexion = ConnectionManager.getConnection()) {
            String requeteSQL = "INSERT INTO Utilisateur (nom, prenom, email, motDePasse, salt, isAdmin) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
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
     * @throws SQLException If the connection to the database fails
     */
    public void updatePassword(String email, String newPassword) throws SQLException {
        try {
            byte[] newSalt = PasswordUtil.getSalt();
            String hashedPassword = PasswordUtil.hashPassword(newPassword, newSalt);
            String encodedSalt = Base64.getEncoder().encodeToString(newSalt);

            try (Connection connexion = ConnectionManager.getConnection()) {
                String updateSQL = "UPDATE Utilisateur SET motDePasse = ?, salt = ? WHERE email = ?";

                try (PreparedStatement preparedStatement = connexion.prepareStatement(updateSQL)) {
                    preparedStatement.setString(1, hashedPassword);
                    preparedStatement.setString(2, encodedSalt);
                    preparedStatement.setString(3, email);

                    int rowsUpdated = preparedStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Mot de passe mis à jour avec succès.");
                    } else {
                        System.out.println("Aucun utilisateur trouvé avec cet email.");
                    }
                }
            }
        } catch(Exception e) {
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
     * Checks if the login information is valid or not.
     * 
     * @param email The email of the user
     * @param plainPassword The plain password of the user
     * @return True if valid, else false.
     */
    public boolean validateLogin(String email, String plainPassword) {
        boolean returnValue = false;

        try (Connection connexion = ConnectionManager.getConnection()) {
            String requeteSQL = "SELECT motDePasse, salt FROM Utilisateur WHERE email = ?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String storedHashedPassword = resultSet.getString("motDePasse");
                        String storedSalt = resultSet.getString("salt");
                        String hashedPassword = PasswordUtil.hashPassword(plainPassword, Base64.getDecoder().decode(storedSalt));
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
        String message = "Votre code de v\u00e9rification est : " + code;
        emailService.sendEmail(email, subject, message);
    }

    /**
     * Generates a random verification code with 6 digits.
     * 
     * @return The generated code
     */
    public int generateVerificationCode() {
        Random random = new Random();
        return random.nextInt(900000) + 100000;
    }

    /**
     * Loads all users from the database into a list.
     * 
     * @return ArrayList of Utilisateur objects.
     */
    public ArrayList<Utilisateur> loadAllUsers() {
        ArrayList<Utilisateur> utilisateurs = new ArrayList<>();

        try (Connection connexion = ConnectionManager.getConnection()) {
            String requeteSQL = "SELECT nom, prenom, email, motDePasse, salt FROM Utilisateur";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");
                    String email = resultSet.getString("email");
                    String motDePasse = resultSet.getString("motDePasse");
                    String salt = resultSet.getString("salt");

                    Utilisateur utilisateur = new Utilisateur(nom, prenom, email, motDePasse, salt);
                    utilisateurs.add(utilisateur);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateurs;
    }

    /**
     * Deletes a user from the database.
     * 
     * @param email The email of the user to delete
     */
    public void dropUser(String email) {
        try (Connection connexion = ConnectionManager.getConnection()) {
            String requeteSQL = "DELETE FROM Utilisateur WHERE email=?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.setString(1, email);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a user's email in the database.
     * 
     * @param email The current email of the user
     * @param newPendingMail The new email to update to
     */
    public void updateUserMail(String email, String newPendingMail) {
        try (Connection connexion = ConnectionManager.getConnection()) {
            String requeteSQL = "UPDATE Utilisateur SET email = ? WHERE email = ?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.setString(1, newPendingMail);
                preparedStatement.setString(2, email);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates user information in the database.
     * 
     * @param currentEmail The current email of the user
     * @param newName The new name of the user
     * @param newFirstName The new first name of the user
     * @param newEmail The new email of the user
     */
    public void updateUser(String currentEmail, String newName, String newFirstName, String newEmail) {
        try (Connection connexion = ConnectionManager.getConnection()) {
            String requeteSQL = "UPDATE Utilisateur SET nom = ?, prenom = ?, email = ? WHERE email = ?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.setString(1, newName);
                preparedStatement.setString(2, newFirstName);
                preparedStatement.setString(3, newEmail);
                preparedStatement.setString(4, currentEmail);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
