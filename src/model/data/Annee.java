package data;

/**
 * Represents a year with an associated inflation rate.
 * <p>
 * This class encapsulates the year and its corresponding inflation rate, providing
 * methods to get and set these values.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * Annee annee2022 = new Annee(2022, 2.5);
 * Annee annee2023 = new Annee(2023, 3.0);
 * String comparaison = annee2022.compareInflation(annee2023);
 * }
 * </pre>
 *
 * @see InvalidIdException
 * @see InvalidDoubleException
 * 
 * @autor O.Gunes
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
     * @throws RuntimeException if the year is not positive or if the inflation rate is not between 0 and 100
     */
    public Annee(int annee, double tauxInflation) {
        setAnnee(annee);
        setTauxInflation(tauxInflation);
    }

    /* ----- Getters ----- */

    /**
     * Returns the year.
     *
     * @return the year (int)
     */
    public int getAnnee() {
        return annee;
    }

    /**
     * Returns the inflation rate.
     *
     * @return the inflation rate (int)
     */
    public double getTauxInflation() {
        return tauxInflation;
    }

    /* ----- Setters ----- */

    /**
     * Sets the year.
     *
     * @param annee the new year
     * @throws RuntimeException if the year is not positive
     */
    public void setAnnee(int annee) {
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
     * @throws RuntimeException if the inflation rate is not between 0 and 100
     */
    public void setTauxInflation(double tauxInflation) {
        if (tauxInflation < 0 || tauxInflation > 100) {
            throw new RuntimeException("Invalid inflation rate : " + tauxInflation);
        } else {
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
     * Compares the inflation rate of this year with another year.
     *
     * @param otherAnnee the other year to compare with
     * @return a string indicating whether this year has a higher, lower, or the same inflation rate as the other year
     */
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
