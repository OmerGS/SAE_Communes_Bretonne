package data;

import javax.naming.InvalidNameException;

/**
 * Represents a train station (Gare) with a unique code, name, and information 
 * about its functionality for freight and passengers, along with its associated commune.
 */
public class Gare {
    /**
     * The unique code of the train station.
     */
    private int codeGare;

    /**
     * The name of the train station.
     */
    private String nomGare;

    /**
     * True if the train station allows freight, false otherwise.
     */
    private boolean estFret;

    /**
     * True if the train station accepts passenger trains, false otherwise.
     */
    private boolean estVoyageur;

    /**
     * The commune of the train station.
     */
    private Commune commune; 

    /**
     * Constructs a new Gare with the specified parameters.
     *
     * @param codeGare    the unique code of the train station
     * @param nomGare     the name of the train station
     * @param estFret     true if the train station allows freight, false otherwise
     * @param estVoyageur true if the train station accepts passenger trains, false otherwise
     * @param commune     the commune of the train station
     * @throws InvalidNameException if the name of the train station is null or empty
     * @throws InvalidIdException   if the id of the commune is invalid
     */
    public Gare(int codeGare, String nomGare, boolean estFret, boolean estVoyageur, Commune commune){
        setCodeGare(codeGare);
        setNomGare(nomGare);
        setEstFret(estFret);
        setEstVoyageur(estVoyageur);
        setCommune(commune);
    }

    /* ----- Getters ----- */

    /**
     * Returns the unique code of the train station.
     *
     * @return the unique code of the train station
     */
    public int getCodeGare() {
        return codeGare;
    }

    /**
     * Returns the name of the train station.
     *
     * @return the name of the train station
     */
    public String getNomGare() {
        return nomGare;
    }

    /**
     * Returns true if the train station allows freight.
     *
     * @return true if the train station allows freight
     */
    public boolean isEstFret() {
        return estFret;
    }

    /**
     * Returns true if the train station accepts passenger trains.
     *
     * @return true if the train station accepts passenger trains
     */
    public boolean isEstVoyageur() {
        return estVoyageur;
    }

    /**
     * Returns the commune of the train station.
     *
     * @return the commune of the train station
     */
    public Commune getCommune() {
        return commune;
    }

    /* ----- Setters ----- */

    /**
     * Sets the unique code of the train station.
     *
     * @param codeGare the new unique code of the train station
     */
    public void setCodeGare(int codeGare) {
        validateNonNegativeValueInt(codeGare,"Code gare");
    }

    /**
     * Sets the name of the train station.
     *
     * @param nomGare the new name of the train station
     * @throws InvalidNameException if the name is null or empty
     */
    public void setNomGare(String nomGare){
        if (nomGare == null || nomGare.trim().isEmpty()) {
            throw new RuntimeException("The name of the train station cannot be null or empty.");
        }
        this.nomGare = nomGare;
    }

    /**
     * Sets whether the train station allows freight.
     *
     * @param estFret true if the train station allows freight, false otherwise
     */
    public void setEstFret(boolean estFret) {
        this.estFret = estFret;
    }

    /**
     * Sets whether the train station accepts passenger trains.
     *
     * @param estVoyageur true if the train station accepts passenger trains, false otherwise
     */
    public void setEstVoyageur(boolean estVoyageur) {
        this.estVoyageur = estVoyageur;
    }

    /**
     * Sets the commune of the train station.
     *
     * @param commune the new commune of the train station
     * @throws InvalidIdException if the id of the commune is invalid
     */
    public void setCommune(Commune commune){
        if (!isValidIdCommune(commune.getIdCommune())) {
            throw new RuntimeException("Invalid commune ID: " + commune.getIdCommune());
        }
        this.commune = commune;
    }

    /* ----- Other Methods ----- */

    /**
     * Returns a string representation of the train station.
     *
     * @return a string representation of the train station
     */
    @Override
    public String toString() {
        return "Gare [codeGare=" + codeGare + ", nomGare=" + nomGare + ", estFret=" + estFret + ", estVoyageur=" + estVoyageur + "]";
    }

    /**
     * Checks if the train station allows both freight and passenger trains.
     *
     * @param isEstFret     true if the train station allows freight
     * @param isEstVoyageur true if the train station accepts passenger trains
     * @return true if the train station allows both freight and passenger trains
     */
    public boolean isEstFretAndIsEstVoyageur(boolean isEstFret, boolean isEstVoyageur) {
        return isEstFret && isEstVoyageur;
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