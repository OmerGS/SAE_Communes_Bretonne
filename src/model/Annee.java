public class Annee {
    private int annee;
    private float tauxInflation;

    public Annee(int annee, float tauxInflation) {
        this.annee = annee;
        this.tauxInflation = tauxInflation;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public float getTauxInflation() {
        return tauxInflation;
    }

    public void setTauxInflation(float tauxInflation) {
        this.tauxInflation = tauxInflation;
    }

    @Override
    public String toString() {
        return "Annee [annee=" + annee + ", tauxInflation=" + tauxInflation + "]";
    }

    public void adjustTauxInflation(float newTaux) {
        this.tauxInflation = newTaux;
    }
}
