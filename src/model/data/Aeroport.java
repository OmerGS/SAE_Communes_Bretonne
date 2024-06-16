package data;

/**
 * Represents an airport with a name, address, and a department.
 * This class ensures that the associated department has a valid code.
 * The valid codes have specific prefixes (29, 35, 22, 56).
 */
public class Aeroport {
    private String nom;
    private String adresse;
    private Departement departement;

    /**
     * Constructs a new Aeroport with the specified name, address, and department.
     *
     * @param nom         the name of the airport
     * @param adresse     the address of the airport
     * @param departement the department associated with the airport
     * @throws IllegalArgumentException if any parameter is invalid or the department ID is not valid
     */
    public Aeroport(String nom, String adresse, Departement departement) {
        if (nom == null || nom.trim().isEmpty() || adresse == null || adresse.trim().isEmpty() || departement == null) {
            throw new IllegalArgumentException("Paramètres invalides pour l'Aéroport.");
        }
        if (!isValidDepartement(departement)) {
            throw new IllegalArgumentException("ID de département invalide: " + departement.getIdDep());
        }
        this.nom = nom;
        this.adresse = adresse;
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
     * Returns the address of the airport.
     *
     * @return the address of the airport
     */
    public String getAdresse() {
        return adresse;
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
     * Sets the name of the airport.
     *
     * @param nom the new name of the airport
     * @throws IllegalArgumentException if the name is null or empty
     */
    public void setNom(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'aéroport ne peut pas être nul ou vide.");
        }
        this.nom = nom;
    }

    /**
     * Sets the address of the airport.
     *
     * @param adresse the new address of the airport
     * @throws IllegalArgumentException if the address is null or empty
     */
    public void setAdresse(String adresse) {
        if (adresse == null || adresse.trim().isEmpty()) {
            throw new IllegalArgumentException("L'adresse de l'aéroport ne peut pas être nulle ou vide.");
        }
        this.adresse = adresse;
    }

    /**
     * Sets the department associated with the airport.
     *
     * @param departement the new department associated with the airport
     * @throws IllegalArgumentException if the department is null or the ID is not valid
     */
    public void setDepartement(Departement departement) {
        if (departement == null) {
            throw new IllegalArgumentException("Le département ne peut pas être nul.");
        }
        if (!isValidDepartement(departement)) {
            throw new IllegalArgumentException("ID de département invalide: " + departement.getIdDep());
        }
        this.departement = departement;
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
     * @throws RuntimeException if one or both airports do not have a valid department
     */
    public boolean areInSameDepartment(Aeroport otherAirport) {
        if (otherAirport.departement == null) {
            throw new RuntimeException("Un ou les deux aéroports n'ont pas de département valide.");
        }
        return this.departement.getIdDep() == otherAirport.departement.getIdDep();
    }

    /**
     * Validates the department ID.
     *
     * @param departement the department to validate
     * @return true if the department ID is valid, false otherwise
     */
    private boolean isValidDepartement(Departement departement) {
        int id = departement.getIdDep();
        return id == 56 || id == 29 || id == 22 || id == 35;
    }
}
