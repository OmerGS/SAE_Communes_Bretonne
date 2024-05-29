package dao;

import data.Utilisateur;
import view.misc.EmailService;
import view.misc.PasswordUtil;

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
import java.util.Random;

import javax.mail.MessagingException;

/**
* Class which contains userService, this class allow manipulate Utilisateur class. We can add the Utilisateur in the database
* Check if user is in database, add user in database.
*
* @author O.Gunes 
*/
public class UserService {

    /**
    * This method load database connection information from a file.
    * This allow to keep our connection information secret from public github repositories.
    * 
    * @return Properties which contain connection information.
    */
    private Properties loadDatabaseProperties() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("../properties/config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }


    /**
    * The method allow to create Utilisateur and hash the password.
    * The hash of password allow to had a security from database breach. 
    *
    * @param nom The surname of user
    * @param prenom The name of user
    * @param email The mail of user
    * @param plainPassword The password (without hash)
    */
    public void createUser(String nom, String prenom, String email, String plainPassword) {
        try {
            byte[] salt = PasswordUtil.getSalt();

            String hashedPassword = PasswordUtil.hashPassword(plainPassword, salt);

            // Créer une instance d'utilisateur
            Utilisateur utilisateur = new Utilisateur(nom, prenom, email, hashedPassword, Base64.getEncoder().encodeToString(salt));

            saveToDatabase(utilisateur);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    /**
    * Private method which check if user is admin
    * 
    * @param email The mail of user
    * @return 1 if admin, else return 0.
    */
    private int userIsAdmin(String email){
        int valeurReturn = 0;

        if(email.contains("@univ-ubs.fr")){
            valeurReturn = 1;
        }

        return(valeurReturn);
    }



    /**
    * Allow to save the user into the database.
    *
    * @param utilisateur The user to save into database.
    */
    private void saveToDatabase(Utilisateur utilisateur) {

        // Get connection information
        Properties props = loadDatabaseProperties();
        String url = props.getProperty("db.url");
        String userDB = props.getProperty("db.user");
        String motDePasseDB = props.getProperty("db.password");


        // Connection to database
        try (Connection connexion = DriverManager.getConnection(url, userDB, motDePasseDB)) {

            //The SQL request
            String requeteSQL = "INSERT INTO Utilisateur (nom, prenom, email, motDePasse, salt, isAdmin) VALUES (?, ?, ?, ?, ?, ?)";

            //We try to send the SQL request, we change the "?" in the SQL request with the values
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                
                // The parameters we send into databse.
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
    * Public method which allow to update password of user.
    *
    * @param email The mail of user
    * @param newPassword The plain password of user
    * @throws SQLException We can throw SQL exception if the connection don't establish
    */
    public void updatePassword(String email, String newPassword) throws SQLException {
        // Loading database connection information
        Properties props = loadDatabaseProperties();
        String url = props.getProperty("db.url");
        String userDB = props.getProperty("db.user");
        String motDePasseDB = props.getProperty("db.password");

        try {
            //We get a new salt (it's allow to hash the password)
            byte[] newSalt = PasswordUtil.getSalt();

            //We hash the new password
            String hashedPassword = PasswordUtil.hashPassword(newPassword, newSalt);

            //We encode the salt to transform it into String.
            String encodedSalt = Base64.getEncoder().encodeToString(newSalt);

            try (Connection connexion = DriverManager.getConnection(url, userDB, motDePasseDB)) {
                //SQL Requests
                String updateSQL = "UPDATE Utilisateur SET motDePasse = ?, salt = ? WHERE email = ?";

                try (PreparedStatement preparedStatement = connexion.prepareStatement(updateSQL)) {
                    preparedStatement.setString(1, hashedPassword);
                    preparedStatement.setString(2, encodedSalt);
                    preparedStatement.setString(3, email);

                    int rowsUpdated = preparedStatement.executeUpdate();
                    //If a row is updated then we print "Mot de passe mis a jour avec succès"
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
    * Public method, check if mail is in the database.
    * 
    * @param email The mail of user we know if he is exist in database
    * @return True if exists, else false.
    */
    public boolean emailExists(String email) {
        boolean returnValue = false;

        Properties props = loadDatabaseProperties();
        String url = props.getProperty("db.url");
        String userDB = props.getProperty("db.user");
        String motDePasseDB = props.getProperty("db.password");

        try (Connection connexion = DriverManager.getConnection(url, userDB, motDePasseDB)) {
            String requeteSQL = "SELECT COUNT(*) FROM Utilisateur WHERE email = ?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {

                    //If we have one user with mail then return true.
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
    * Check if the information is valid or not.
    * 
    * @param email
    * @param plainPassword
    * @return
    */
    public boolean validateLogin(String email, String plainPassword) {
        boolean returnValue = false;

        //Load database connection information
        Properties props = loadDatabaseProperties();
        String url = props.getProperty("db.url");
        String userDB = props.getProperty("db.user");
        String motDePasseDB = props.getProperty("db.password");


        try (Connection connexion = DriverManager.getConnection(url, userDB, motDePasseDB)) {
            //We get the password and the salt which was in database.
            String requeteSQL = "SELECT motDePasse, salt FROM Utilisateur WHERE email = ?";
            try (PreparedStatement preparedStatement = connexion.prepareStatement(requeteSQL)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        //We get hashed password of database.
                        String storedHashedPassword = resultSet.getString("motDePasse");

                        //We get the salt
                        String storedSalt = resultSet.getString("salt");

                        //We hash the password enter by the users.
                        String hashedPassword = PasswordUtil.hashPassword(plainPassword, Base64.getDecoder().decode(storedSalt));

                        //We check if the user entries match with the database.
                        returnValue = storedHashedPassword.equals(hashedPassword);
                    }
                }
            } catch (SQLException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return(returnValue);
    }


    /**
    * Send a verification mail with a code.
    * 
    * @param email The email of user
    * @param code The code we send
    * @throws IOException 
    * @throws MessagingException
    */
    public void sendVerificationEmail(String email, String code) throws IOException, MessagingException {
        EmailService emailService = new EmailService();

        String subject = "Code de v\u00e9rification";
        String message = "Votre code de v\u00e9rification est : " + code;

        emailService.sendEmail(email, subject, message);
    }


    /**
    * Generate a random code with 6-digits
    * @return the code
    */
    public int generateVerificationCode(){
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return code;
    }
    
}