package data;



/**
 * Represents an airport with a name, address, and a commune.
 * This class ensures that the associated commune has a valid ID.
 * The valid IDs have specific prefixes (29, 35, 22, 56) and are exactly 5 digits long.
 */
public class Aeroport {
    private String nom;
    private String adresse;
    private Departement departement;

    /**
     * Constructs a new Aeroport with the specified name, address, and commune.
     *
     * @param nom     the name of the airport
     * @param adresse the address of the airport
     * @param commune the commune associated with the airport


     */
    public Aeroport(String nom, String adresse, Departement departement) {
        setNom(nom);
        setAdresse(adresse);
        this.departement = departement;
    }

    /* ----- Getters ----- */

    /**
     * Returns the name of the airport.
     *
     * @return the name of the airport
     */
    public String getNom() {
        return nom;
    }

    /**
     * Sets the name of the airport.
     *
     * @param nom the new name of the airport
     */
    public void setNom(String nom){
        if (nom == null || nom.trim().isEmpty()) {
            throw new RuntimeException("The name of the airport cannot be null or empty.");
        }
        this.nom = nom;
    }

    /**
     * Returns the address of the airport.
     *
     * @return the address of the airport
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Sets the address of the airport.
     *
     * @param adresse the new address of the airport
     * @throws InvalidAddressException if the address is null or empty
     */
    public void setAdresse(String adresse){
        if (adresse == null || adresse.trim().isEmpty()) {
            throw new RuntimeException("The address of the airport cannot be null or empty.");
        }
        this.adresse = adresse;
    }

    /**
     * Returns the commune associated with the airport.
     *
     * @return the commune associated with the airport
     */
    public Departement getDepartement() {
        return departement;
    }

    
    

    /* ----- Other Methods ----- */

    /**
     * Returns a string representation of the airport.
     *
     * @return a string representation of the airport
     */
    @Override
    public String toString() {
        return "Aeroport [nom=" + nom + ", adresse=" + adresse + "departement "+departement +"]";
    }

    /**
     * Method which allows checking if the id of the commune is valid or not.
     * 
     * @param idCommune the id of the commune we want to validate
     * @return false if not valid, else return true.
     */
    private boolean isValidIdCommune(int idCommune) {
        boolean ret = true;
        String idString = String.valueOf(idCommune);

        // Check if the idCommune has exactly 5 digits
        if (idString.length() != 5) {
            ret = false;
        } else {
            // Check if the prefix is one of the valid values
            String prefix = idString.substring(0, 2);
            ret = prefix.equals("29") || prefix.equals("35") || prefix.equals("22") || prefix.equals("56");
        }

        return ret;
    }
}