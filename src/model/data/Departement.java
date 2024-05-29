package data;

import data.exceptions.InvalidNameException;

import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

/**
 * Represents a department (Departement) with a unique id, name, investment in culture for 2019,
 * and a list of associated communes.
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
     * Constructs a new Departement with the specified parameters.
     *
     * @param idDep             the unique id of the department
     * @param nomDep            the name of the department
     * @param invesCulture2019  the investment in culture for 2019
     * @throws InvalidNameException       if the name of the department is null or empty
     */
    public Departement(int idDep, String nomDep, double invesCulture2019) throws InvalidNameException{
        setIdDep(idDep);
        setNomDep(nomDep);
        setInvesCulture2019(invesCulture2019);
        communes = new ArrayList<Commune>();
    }

    /* ----- Getters ----- */

    /**
     * Returns the unique id of the department.
     *
     * @return the unique id of the department
     */
    public int getIdDep() {
        return idDep;
    }

    /**
     * Returns the name of the department.
     *
     * @return the name of the department
     */
    public String getNomDep() {
        return nomDep;
    }

    /**
     * Returns the investment in culture for 2019.
     *
     * @return the investment in culture for 2019
     */
    public double getInvesCulture2019() {
        return invesCulture2019;
    }

    /**
     * Returns the list of communes associated with the department.
     *
     * @return the list of communes associated with the department
     */
    public List<Commune> getCommunes() {
        return communes;
    }

    /* ----- Setters ----- */

    /**
     * Sets the unique id of the department.
     *
     * @param idDep the new unique id of the department
     */
    public void setIdDep(int idDep) {
        validateNonNegativeValueInt(idDep,"id Départment");
    }

    /**
     * Sets the name of the department.
     *
     * @param nomDep the new name of the department
     * @throws InvalidNameException if the name is null or empty
     */
    public void setNomDep(String nomDep) throws InvalidNameException {
        if (nomDep == null || nomDep.trim().isEmpty()) {
            throw new InvalidNameException("The name of the department cannot be null or empty.");
        }
        this.nomDep = nomDep;
    }

    /**
     * Sets the investment in culture for 2019.
     *
     * @param invesCulture2019 the new investment in culture for 2019
     */
    public void setInvesCulture2019(double invesCulture2019){
        validateNonNegativeValueDouble(invesCulture2019,"Investissement Culture 2019");
    }

    /**
     * Sets the list of communes associated with the department.
     *
     * @param communes the new list of communes associated with the department
     */
    public void setCommunes(ArrayList<Commune> communes) {
        this.communes = communes;
    }

    /**
     * Adds a commune to the list of communes associated with the department.
     *
     * @param commune the commune to add
     */
    public void addCommune(Commune commune) {
        this.communes.add(commune);
    }

    /* ----- Other Methods ----- */

    /**
     * Returns a string representation of the department.
     *
     * @return a string representation of the department
     */
    @Override
    public String toString() {
        return "Departement [idDep=" + idDep + ", nomDep=" + nomDep + ", invesCulture2019=" + invesCulture2019 + "]";
    }

    /**
     * Returns the total population of the department by summing the population of all its communes.
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
    * Method which allow to check if parameters is >= 0. 
    * Signature for double and fieldName
    *
    * @param value the parameters which we want to check
    * @param fieldName the fieldname of global var.
    * @return double
    * @throws IllegalArgumentException if provided value is negative
    */
    private double validateNonNegativeValueDouble(double value, String fieldName) {
        double ret = -1;
        if (value >= 0) {
            ret = value;
        } else {
            throw new IllegalArgumentException(fieldName + " invalide : " + value);
        }
        return ret;
    }

    /**
    * Method which allow to check if parameters is >= 0. 
    * Signature for int and fieldName
    *
    * @param value the parameters which we want to check
    * @param fieldName the fieldname of global var.
    * @return int
    * @throws IllegalArgumentException if provided value is negative
    */
    private int validateNonNegativeValueInt(int value, String fieldName) {
        int ret = -1;
        if (value >= 0) {
            ret = value;
        } else {
            throw new IllegalArgumentException(fieldName + " invalide : " + value);
        }
        return ret;
    }



}
