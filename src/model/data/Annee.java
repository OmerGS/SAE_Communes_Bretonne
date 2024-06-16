package data;

/**
 * Represents a year with an associated inflation rate.
 * @author R.Péron, O.Gunes, B.Campion
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
     * @throws IllegalArgumentException if the year is not positive or the inflation rate is not between 0 and 100
     */
    public Annee(int annee, double tauxInflation) {
        if (annee <= 0 || tauxInflation < 0 || tauxInflation > 100) {
            throw new IllegalArgumentException("L'année doit être positive et le taux d'inflation doit être compris entre 0 et 100: " + annee + ", " + tauxInflation);
        }
        this.annee = annee;
        this.tauxInflation = tauxInflation;
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
     * @throws IllegalArgumentException if the year is not positive
     */
    public void setAnnee(int annee) {
        if (annee > 0) {
            this.annee = annee;
        } else {
            throw new IllegalArgumentException("L'année doit être positive: " + annee);
        }
    }

    /**
     * Sets the inflation rate.
     *
     * @param tauxInflation the new inflation rate
     * @throws IllegalArgumentException if the inflation rate is not between 0 and 100
     */
    public void setTauxInflation(double tauxInflation) {
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
     * @throws IllegalArgumentException if the inflation rate is not between 0 and 100
     */
    private boolean isValidTauxInflation(double tauxInflation) {
        if (tauxInflation < 0 || tauxInflation > 100) {
            throw new IllegalArgumentException("Taux d'inflation invalide: " + tauxInflation);
        }
        return true;
    }

    /**
     * Compares the inflation rate of this year with another year.
     *
     * @param otherAnnee the other year to compare with
     * @return a string describing the comparison result
     */
    public String compareInflation(Annee otherAnnee) {
        String ret;
        if (this.tauxInflation > otherAnnee.getTauxInflation()) {
            ret = this.annee + " a un taux d'inflation plus élevé que " + otherAnnee.getAnnee();
        } else if (this.tauxInflation < otherAnnee.getTauxInflation()) {
            ret = this.annee + " a un taux d'inflation plus bas que " + otherAnnee.getAnnee();
        } else {
            ret = "Les deux années ont le même taux d'inflation.";
        }
        return ret;
    }
}
