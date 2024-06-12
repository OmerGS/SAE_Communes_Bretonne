package data;

/**
 * Represents an airport with a name, address, and a department.
 * This class ensures that the associated department has a valid code.
 * The valid codes have specific prefixes (29, 35, 22, 56) and are exactly 2 digits long.
 */
public class Aeroport {
    private String nom;
    private String adresse;
    private Departement departement;

    /**
     * Constructs a new Aeroport with the specified name, address, and department.
     *
     * @param nom     the name of the airport
     * @param adresse the address of the airport
     * @param departement the department associated with the airport
     */
    public Aeroport(String nom, String adresse, Departement departement) {
        if (nom != null && adresse != null && departement != null) {
            this.nom = nom;
            this.adresse = adresse;
            this.departement = departement;
        } else {
            throw new RuntimeException("Invalid parameter");
        }
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
    public void setNom(String nom) {
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
     */
    public void setAdresse(String adresse) {
        if (adresse == null || adresse.trim().isEmpty()) {
            throw new RuntimeException("The address of the airport cannot be null or empty.");
        }
        this.adresse = adresse;
    }

    /**
     * Returns the department associated with the airport.
     *
     * @return the department associated with the airport
     */
    public Departement getDepartement() {
        return departement;
    }

    /**
     * Sets the department associated with the airport.
     *
     * @param departement the new department associated with the airport
     */
    public void setDepartement(Departement departement) {
        if(departement != null){
            if (departement.getIdDep() == 56 || departement.getIdDep() == 29 || departement.getIdDep() == 22 || departement.getIdDep() == 35) {
                this.departement = departement;
            } else {
                throw new IllegalArgumentException();
            }
        }else{
            throw new IllegalArgumentException();
        }
    }

    


    /**
     * Returns a string representation of the airport.
     *
     * @return a string representation of the airport
     */
    @Override
    public String toString() {
        return "Aeroport [nom=" + nom + ", adresse=" + adresse + ", departement=" + departement + "]";
    }

    /**
     * Checks if this airport and another airport are in the same department.
     *
     * @param otherAirport the other airport to compare with
     * @return true if both airports are in the same department, false otherwise
     */
    public boolean areInSameDepartment(Aeroport otherAirport) {
        boolean ret = false;
        if (otherAirport.departement != null) {
            ret = this.departement.getIdDep() == otherAirport.departement.getIdDep();
        } else {
            throw new RuntimeException("One or both airports do not have a valid department.");
        }
        return ret;
    }   
}