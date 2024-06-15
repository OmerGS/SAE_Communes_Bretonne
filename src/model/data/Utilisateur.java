package data;

/**
 * The {@code Utilisateur} class represents a user of the application.
 * It stores basic information about the user including their name, email, password, and a salt for password hashing.
 * This class provides getter and setter methods for these fields.
 * 
 * <p>Example usage:</p>
 * <pre>
 *     Utilisateur user = new Utilisateur("Doe", "John", "john.doe@example.com", "password123", "salt123");
 *     System.out.println(user.getNom());
 * </pre>
 * 
 * @author O.Gunes
*/
public class Utilisateur {
    /**
    * The surname of the user 
    */
    private String nom;

    /**
    * The name of the user.
    */
    private String prenom;

    /**
    * The mail of the user.
    */
    private String email;

    public void setNom(String nom){
        if(nom != null){
            this.nom = nom;
        }
    }

    public void setPrenom(String prenom) {
        if(prenom != null){
            this.prenom = prenom;
        }
    }

    public void setEmail(String email) {
        if(email != null){
            this.email = email;
        }
    }

    /**
    * The password of the user. 
    */
    private String motDePasse;

    /**
    * The hash salt of the user. 
    */
    private String salt;

    /**
    * Getter of nom. 
    * @return Surname of the user.
    */
    public String getNom() {
        return nom;
    }

    /**
    * Getter of prenom
    * @return Name of the user.
    */
    public String getPrenom() {
        return prenom;
    }

    /**
    * Getter of the mail
    * @return Mail of user.
    */
    public String getEmail() {
        return email;
    }

    /**
    * Getter of password 
    * @return Password of user.
    */
    public String getMotDePasse() {
        return motDePasse;
    }

    /**
    * Getter of salt 
    * @return Salt of the password used in Hash
    */
    public String getSalt() {
        return salt;
    }

    /**
    * Constructor of the Utilisateur
    * 
    * @param nom Surname of the user
    * @param prenom Name of the user
    * @param email Mail of the user
    * @param motDePasse Password of the user
    * @param salt Salt used for hash of user.
    */
    public Utilisateur(String nom, String prenom, String email, String motDePasse, String salt) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.salt = salt;
    }
}
