package data;

/**
* Utilisateur is a class which represent the user of app.
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
