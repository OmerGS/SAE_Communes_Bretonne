package data;


/**
 * Represents a year with an associated inflation rate.
 */
public class Annee {
    /**
     * The year.
     */
    private int annee;

    /**
     * The inflation rate for the year.
     */
    private double tauxInflation;

    /**
     * Constructs a new Annee with the specified year and inflation rate.
     *
     * @param annee         the year
     * @param tauxInflation the inflation rate
     * @throws InvalidIdException      if the year is not positive
     * @throws InvalidDoubleException  if the inflation rate is not between 0 and 100
     */
    public Annee(int annee, double tauxInflation){
        setAnnee(annee);
        setTauxInflation(tauxInflation);
    }

    /* ----- Getters ----- */

    /**
     * Returns the year.
     *
     * @return the year
     */
    public int getAnnee() {
        return annee;
    }

    /**
     * Returns the inflation rate.
     *
     * @return the inflation rate
     */
    public double getTauxInflation() {
        return tauxInflation;
    }

    /* ----- Setters ----- */

    /**
     * Sets the year.
     *
     * @param annee the new year
     * @throws InvalidIdException if the year is not positive
     */
    public void setAnnee(int annee){
        if (annee > 0) {
            this.annee = annee;
        } else {
            throw new RuntimeException("The year must be positive: " + annee);
        }
    }

    /**
     * Sets the inflation rate.
     *
     * @param tauxInflation the new inflation rate
     * @throws InvalidDoubleException if the inflation rate is not between 0 and 100
     */
    public void setTauxInflation(double tauxInflation){
        if (isValidTauxInflation(tauxInflation)) {
            this.tauxInflation = tauxInflation;
        }
    }

    /* ----- Other Methods ----- */

    /**
     * Returns a string representation of the year and its inflation rate.
     *
     * @return a string representation of the year and its inflation rate
     */
    @Override
    public String toString() {
        return "Annee [annee=" + annee + ", tauxInflation=" + tauxInflation + "]";
    }

    /**
     * Checks if the inflation rate is valid.
     * The rate is considered valid if it is between 0 and 100 (inclusive).
     *
     * @param tauxInflation the inflation rate to validate
     * @return true if the rate is valid, false otherwise
     * @throws InvalidDoubleException if the inflation rate is not between 0 and 100
     */
    private boolean isValidTauxInflation(double tauxInflation){
        if (tauxInflation < 0 || tauxInflation > 100) {
            throw new RuntimeException("Invalid inflation rate: " + tauxInflation);
        }
        return true;
    }


    public String compareInflation(Annee otherAnnee) {
        String ret = null;
        if (this.tauxInflation > otherAnnee.getTauxInflation()) {
            ret = this.annee + " has a higher inflation rate than " + otherAnnee.getAnnee();
        } else if (this.tauxInflation < otherAnnee.getTauxInflation()) {
            ret = this.annee + " has a lower inflation rate than " + otherAnnee.getAnnee();
        } else {
            ret = "Both years have the same inflation rate.";
        }
        return ret;
    }
}