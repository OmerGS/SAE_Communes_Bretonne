package data;

/**
 * Represents an airport with a name, address, and a department.
 * This class ensures that the associated department has a valid code.
 * The valid codes have specific prefixes (29, 35, 22, 56) and are exactly 2 digits long.
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * Departement departement = new Departement(29, "Finistère");
 * Aeroport aeroport = new Aeroport("Brest Bretagne Airport", "Rue de l'aéroport, 29490 Guipavas", departement);
 * }
 * </pre>
 * 
 * @see Departement
 * 
 * @autor R.Peron
 */
public class Aeroport {
    /**
     * Name of the Airport
     */
    private String nom;

    /**
     * Adress of the Airport
     */
    private String adresse;

     /**
     * The department to which the Airport belongs.
     */
    private Departement departement;

    /**
     * Constructs a new Aeroport with the specified name, address, and department.
     *
     * @param nom the name of the airport
     * @param adresse the address of the airport
     * @param departement the department associated with the airport
     * @throws RuntimeException if any parameter is null
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
     * Set a name to the airport.
     *
     * @param nom the new name of the airport
     * @throws RuntimeException if the name is null or empty
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
     * @return the address of the airport (A String)
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Sets an address to the airport.
     *
     * @param adresse the new address of the airport
     * @throws RuntimeException if the address is null or empty
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
     * @return the department associated with the airport (Departement object)
     */
    public Departement getDepartement() {
        return departement;
    }

    /**
     * Sets the department associated with the airport.
     *
     * @param departement the new department associated with the airport
     * @throws IllegalArgumentException if the department is null or has an invalid code
     */
    public void setDepartement(Departement departement) {
        if (departement != null) {
            if (departement.getIdDep() == 56 || departement.getIdDep() == 29 || departement.getIdDep() == 22 || departement.getIdDep() == 35) {
                this.departement = departement;
            } else {
                throw new IllegalArgumentException("Invalid department code.");
            }
        } else {
            throw new IllegalArgumentException("Department cannot be null.");
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
     * @throws RuntimeException if either airport does not have a valid department
     */
    public boolean areInSameDepartment(Aeroport otherAirport) {
        if (otherAirport.departement != null) {
            return this.departement.getIdDep() == otherAirport.departement.getIdDep();
        } else {
            throw new RuntimeException("One or both airports do not have a valid department.");
        }
    }   
}
