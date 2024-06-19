package data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * Represents a department (Departement) with a unique ID, name, investment in culture for 2019,
 * and lists of associated communes and airports.
 * This class ensures that each department has a unique ID and provides methods to access and modify its properties.
 * It also maintains a static list of all department instances.
 * 
 * <p>Example usage:</p>
 * <pre>
 *     Departement dept = new Departement(1, "Example Department", 10000.0);
 *     Commune commune = new Commune(12345, "Example Commune", 1000, dept);
 *     dept.addCommune(commune);
 * </pre>
 * 
 * @author R.Peron
 */
public class Departement {

    /**
    * Set to store used IDs of departments.
    * This set ensures that each department has a unique ID.
    */
    private Set<Integer> idsUtilises = new HashSet<>();

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
     * The list of communes associated with the department.
     */
    private ArrayList<Aeroport> aeroports;

    /**
     * Static list to keep track of all department instances.
     */
    private static ArrayList<Departement> departements = new ArrayList<>();


     /**
     * Constructs a department with the specified ID, name, and investment in culture for 2019.
     * 
     * @param idDep The unique ID of the department.
     * @param nomDep The name of the department.
     * @param invesCulture2019 The investment in culture for 2019.
     * @throws RuntimeException if the ID is already used, or if the department name is null or empty,
     *         or if the investment amount is less than 0.
     */
    public Departement(int idDep, String nomDep, double invesCulture2019){
        if((!idsUtilises.contains(idDep)) && nomDep != null && invesCulture2019 > 0){
            this.idDep = idDep;
            this.nomDep = nomDep;
            this.invesCulture2019 = invesCulture2019;
            this.idsUtilises.add(idDep);
            this.communes = new ArrayList<Commune>();
            this.aeroports = new ArrayList<Aeroport>();
            departements.add(this);
        }else{
            throw new RuntimeException("parametre invalide");
        }
    }



   /**
     * Retrieves the unique ID of the department.
     * 
     * @return The ID of the department.
     */
    public int getIdDep() {
        return this.idDep;
    }

    /**
     * Retrieves the name of the department.
     * 
     * @return The name of the department.
     */
    public String getNomDep() {
        return this.nomDep;
    }

    /**
     * Retrieves the investment in culture for 2019.
     * 
     * @return The investment in culture for 2019.
     */
    public double getInvesCulture2019() {
        return this.invesCulture2019;
    }

    /**
     * Retrieves the list of communes associated with the department.
     * 
     * @return The list of communes associated with the department.
     */
    public ArrayList<Commune> getCommunes() {
        return this.communes;
    }

    /**
     * Retrieves the list of airports associated with the department.
     * 
     * @return The list of airports associated with the department.
     */
    public ArrayList<Aeroport> getAeroport() {
        return this.aeroports;
    }


    /**
     * Retrieves a department instance by its unique ID.
     * 
     * @param idDep The ID of the department to retrieve.
     * @return The department instance matching the given ID, or null if not found.
     */
    public static Departement getDepartementById(int idDep) {
        for (Departement dep : departements) {
            if (dep.getIdDep() == idDep) {
                return dep;
            }
        }
        return null;  // Retourne null si aucun département n'est trouvé avec l'identifiant donné
    }


    /**
     * Sets the unique ID of the department.
     * 
     * @param idDep The new ID to set for the department.
     * @throws IllegalArgumentException if the new ID is already in use.
     */
    public void setIdDep(int idDep) {
        if (this.idDep != idDep) {
            if (idsUtilises.contains(idDep)) {
                throw new IllegalArgumentException("ID already in use: " + idDep);
            }
            idsUtilises.remove(this.idDep);
            this.idDep = idDep;
            idsUtilises.add(idDep);
        }
    }

    /**
     * Sets the name of the department.
     * 
     * @param nomDep The new name to set for the department.
     * @throws RuntimeException if the new name is null or empty.
     */
    public void setNomDep(String nomDep){
        if (nomDep == null || nomDep.trim().isEmpty()) {
            throw new RuntimeException("The name of the department cannot be null or empty.");
        }
        this.nomDep = nomDep;
    }


    /**
     * Sets the investment in culture for 2019.
     * 
     * @param invesCulture2019 The new investment amount to set.
     * @throws RuntimeException if the new investment amount is less than 0.
     */
    public void setInvesCulture2019(double invesCulture2019){
        if(invesCulture2019 >= 0){
            this.invesCulture2019 = invesCulture2019;
        }else{
            throw new RuntimeException();
        }
    }

    /**
     * Sets the list of communes associated with the department.
     * 
     * @param communes The new list of communes to set.
     * @throws RuntimeException if the new list is null or empty.
     */
    public void setCommunes(ArrayList<Commune> communes) {
        if(communes != null && communes.size() > 0){
            this.communes = communes;
        }else{
            throw new RuntimeException();
        }
    }

    /**
     * Sets the list of airports associated with the department.
     * 
     * @param aeroports The new list of airports to set.
     * @throws RuntimeException if the new list is null or empty.
     */
    public void setAeroport(ArrayList<Aeroport> aeroports) {
        if(aeroports != null && aeroports.size() > 0){
            this.aeroports = aeroports;
        }else{
            throw new RuntimeException();
        }
    }


    /**
     * Retrieves a string representation of the department.
     * 
     * @return A string representation of the department.
     */
    @Override
    public String toString() {
        return "Departement [idDep=" + this.idDep + ", nomDep=" + this.nomDep + ", invesCulture2019=" + this.invesCulture2019 + "]";
    }

    /**
     * Retrieves the total population of all communes associated with the department.
     * 
     * @return The total population of all communes.
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
     * @param commune The commune to add.
     * @throws IllegalArgumentException if the commune does not belong to this department.
     */
    public void addCommune(Commune commune) {
        if (commune.getDepartement().getIdDep() == this.idDep) {
            this.communes.add(commune);
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    /**
     * Adds an airport to the list of airports associated with the department.
     * 
     * @param aeroport The airport to add.
     * @throws IllegalArgumentException if the airport does not belong to this department.
     */
    public void addAeroport(Aeroport aeroport) {
    
        if (aeroport.getDepartement().getIdDep() == this.idDep) {
            this.aeroports.add(aeroport);
        } else {
            throw new IllegalArgumentException();
        }
    }
}