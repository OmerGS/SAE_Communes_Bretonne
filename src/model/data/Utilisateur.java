package data;

class Utilisateur {
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String salt;

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getSalt() {
        return salt;
    }

    public Utilisateur(String nom, String prenom, String email, String motDePasse, String salt) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.salt = salt;
    }

    
}
