package data;

public class Aeroport {
    private String nom;
    private String adresse;
    private Commune commune;  // Many-to-one relationship with Commune

    public Aeroport(String nom, String adresse, Commune commune) {
        this.nom = nom;
        this.adresse = adresse;
        this.commune = commune;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Commune getCommune() {
        return commune;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }

    @Override
    public String toString() {
        return "Aeroport [nom=" + nom + ", adresse=" + adresse + "]";
    }


}
