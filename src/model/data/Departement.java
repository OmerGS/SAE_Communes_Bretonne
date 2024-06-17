package data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * Represents a department (Departement) with a unique id, name, investment in culture for 2019,
 * and a list of associated communes and airports.
 * @author R.Péron, O.Gunes, B.Campion
 */
public class Departement {
    
    /**
     * The unique id of the department.
     */
    private int idDep;

    /**
     * The name of the department.
     */
    private String nomDep;

    /**
     * The investment in culture for 2019.
     */
    private double invesCulture2019;

    /**
     * The list of communes associated with the department.
     */
    private ArrayList<Commune> communes;

    /**
     * The list of airports associated with the department.
     */
    private ArrayList<Aeroport> aeroports;

    /**
     * Static list to keep track of all department instances(DAO).
     */
    private static ArrayList<Departement> departements = new ArrayList<>();

    /**
     * Constructs a new Departement with the specified id, name, and investment in culture for 2019.
     *
     * @param idDep            the unique id of the department
     * @param nomDep           the name of the department
     * @param invesCulture2019 the investment in culture for 2019
     * @throws IllegalArgumentException if the parameters are invalid
     */
    public Departement(int idDep, String nomDep, double invesCulture2019){
        if(idDep <= 0 || nomDep == null || nomDep.trim().isEmpty() || invesCulture2019 <= 0) {
            throw new IllegalArgumentException("Paramètres invalides pour le département.");
        }
        this.idDep = idDep;
        this.nomDep = nomDep;
        this.invesCulture2019 = invesCulture2019;
        this.communes = new ArrayList<>();
        this.aeroports = new ArrayList<>();
        departements.add(this);
    }

    /**
     * Gets the unique id of the department.
     *
     * @return the unique id of the department
     */
    public int getIdDep() {
        return this.idDep;
    }

    /**
     * Gets the name of the department.
     *
     * @return the name of the department
     */
    public String getNomDep() {
        return this.nomDep;
    }

    /**
     * Gets the investment in culture for 2019.
     *
     * @return the investment in culture for 2019
     */
    public double getInvesCulture2019() {
        return this.invesCulture2019;
    }

    /**
     * Gets the list of communes associated with the department.
     *
     * @return the list of communes
     */
    public ArrayList<Commune> getCommunes() {
        return this.communes;
    }

    /**
     * Gets the list of airports associated with the department.
     *
     * @return the list of airports
     */
    public ArrayList<Aeroport> getAeroport() {
        return this.aeroports;
    }

    /**
     * Static method to get a department by its id.
     *
     * @param idDep the id of the department to retrieve
     * @return the department with the specified id, or null if not found
     */
    public static Departement getDepartementById(int idDep) {
        Departement ret = null;
        for (Departement dep : departements) {
            if (dep.getIdDep() == idDep) {
                ret = dep;
            }
        }
        return ret;  // Returns null if no department is found with the given id
    }

    /**
     * Sets the name of the department.
     *
     * @param nomDep the new name of the department
     * @throws IllegalArgumentException if the name is null or empty
     */
    public void setNomDep(String nomDep){
        if (nomDep == null || nomDep.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du département ne peut pas être nul ou vide.");
        }
        this.nomDep = nomDep;
    }

    /**
     * Sets the investment in culture for 2019.
     *
     * @param invesCulture2019 the new investment in culture for 2019
     * @throws IllegalArgumentExceptionption if the investment is negative
     */
    public void setInvesCulture2019(double invesCulture2019){
        if(invesCulture2019 >= 0){
            this.invesCulture2019 = invesCulture2019;
        }else{
            throw new IllegalArgumentException("L'investissement culturel ne peut pas être négatif.");
        }
    }

    /**
     * Sets the list of communes associated with the department.
     *
     * @param communes the new list of communes
     * @throws IllegalArgumentException if the list is null or empty
     */
    public void setCommunes(ArrayList<Commune> communes) {
        if(communes != null && communes.size() > 0){
            this.communes = communes;
        }else{
            throw new IllegalArgumentException("La liste des communes ne peut pas être nulle ou vide.");
        }
    }

    /**
     * Sets the list of airports associated with the department.
     *
     * @param aeroports the new list of airports
     * @throws RuntimeException if the list is null or empty
     */
    public void setAeroport(ArrayList<Aeroport> aeroports) {
        if(aeroports != null && aeroports.size() > 0){
            this.aeroports = aeroports;
        }else{
            throw new IllegalArgumentException("La liste des aéroports ne peut pas être nulle ou vide.");
        }
    }

    @Override
    public String toString() {
        return "Departement [idDep=" + this.idDep + ", nomDep=" + this.nomDep + ", invesCulture2019=" + this.invesCulture2019 + "]";
        return "Departement [idDep=" + this.idDep + ", nomDep=" + this.nomDep + ", invesCulture2019=" + this.invesCulture2019 + "]";
    }

    /**
     * Calculates the total population of the department by summing the populations of its communes.
     *
     * @return the total population of the department
     */
    public int getTotalPopulation() {
        int totalPopulation = 0;
        for (Commune commune : this.communes) {
            totalPopulation += commune.getPopulation();
        }
        return totalPopulation;
    }

    /**
     * Adds a commune to the list of communes associated with the department.
     *
     * @param commune the commune to add
     * @throws IllegalArgumentException if the commune does not belong to this department
     */
    public void addCommune(Commune commune) {
        if (commune.getDepartement().getIdDep() == this.idDep) {
            this.communes.add(commune);
        } else {
            throw new IllegalArgumentException("La commune ne correspond pas à ce département.");
        }
    }
    
    /**
     * Adds an airport to the list of airports associated with the department.
     *
     * @param aeroport the airport to add
     * @throws IllegalArgumentException if the airport does not belong to this department
     */
    public void addAeroport(Aeroport aeroport) {
        if (aeroport.getDepartement().getIdDep() == this.idDep) {
            this.aeroports.add(aeroport);
        } else {
            throw new IllegalArgumentException("L'aéroport ne correspond pas à ce département.");
        }
    }
}
