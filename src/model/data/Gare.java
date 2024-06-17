package data;

import java.util.HashSet;
import java.util.Set;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a train station (Gare) with a unique code, name, and information 
 * about its functionality for freight and passengers, along with its associated commune.
 * @author R.Péron, O.Gunes, B.Campion
 */
public class Gare {

    private static Set<Integer> idsUtilises = new HashSet<>();
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
     * @throws IllegalArgumentException if the provided parameters are invalid
     */
    public Gare(int codeGare, String nomGare, boolean estFret, boolean estVoyageur, Commune commune){
        if (codeGare <= 0) {
            throw new IllegalArgumentException("Identifiant de la gare invalide : " + codeGare);
        }
        if (nomGare == null || nomGare.trim().isEmpty()) {
            throw new IllegalArgumentException("Nom de la gare invalide : " + nomGare);
        }
        if(commune == null){
            throw new IllegalArgumentException("Commune invalide");
        }
        
        this.codeGare = codeGare;
        this.nomGare = nomGare;
        this.estFret = estFret;
        this.estVoyageur = estVoyageur;
        this.commune = commune;
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
     * @throws IllegalArgumentException if the new code is invalid or already in use
     */
    public void setCodeGare(int codeGare) {
        if (this.codeGare != codeGare) {
            if (idsUtilises.contains(codeGare)) {
                throw new IllegalArgumentException("Identifiant déjà utilisé : " + codeGare);
            }
            idsUtilises.remove(this.codeGare);
            this.codeGare = codeGare;
            idsUtilises.add(codeGare);
        }
    }

    /**
     * Sets the name of the train station.
     *
     * @param nomGare the new name of the train station
     * @throws IllegalArgumentException if the provided name is null or empty
     */
    public void setNomGare(String nomGare){
        if (nomGare == null || nomGare.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de la gare ne peut pas être vide ou null.");
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
     * @throws IllegalArgumentException if the provided commune is null , if the ID of the commune is invalid
     */
    public void setCommune(Commune commune){
        if(commune != null){
            if (!isValidIdCommune(commune.getIdCommune())) {
                throw new IllegalArgumentException("Identifiant de la commune invalide : " + commune.getIdCommune());
            }
            this.commune.removeGare();
            this.commune = commune;
            this.commune.addGare(this);
        }else{
            throw new IllegalArgumentException("Commune invalide.");
        }

    }

    /* ----- Other Methods ----- */

    /**
     * Returns a string representation of the train station.
     *
     * @return a string representation of the train station
     */
    @Override
    public String toString() {
        return "Gare [codeGare = " + codeGare + ", nomGare = " + nomGare + ", estFret = " + estFret + ", estVoyageur = " + estVoyageur + ", commune = " + commune + "]";
    }

    /**
     * Checks if the train station allows both freight and passenger trains.
     *
     * @return true if the train station allows both freight and passenger trains
     */
    public boolean isEstFretAndIsEstVoyageur() {
        return this.estFret && this.estVoyageur;
    }

    /**
     * Method which allows checking if the id of the commune is valid or not.
     * 
     * @param idCommune the id of the commune we want to validate
     * @return true if the ID is valid, false otherwise
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
