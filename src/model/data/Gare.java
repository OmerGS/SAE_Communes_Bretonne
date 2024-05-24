package data;


public class Gare {
    private int codeGare;
    private String nomGare;
    private boolean estFret;
    private boolean estVoyageur;
    private Commune commune; 

    public Gare(int codeGare, String nomGare, boolean estFret, boolean estVoyageur, Commune commune) {
        this.codeGare = codeGare;
        this.nomGare = nomGare;
        this.estFret = estFret;
        this.estVoyageur = estVoyageur;
        this.commune = commune;
    }

    public int getCodeGare() {
        return codeGare;
    }

    public void setCodeGare(int codeGare) {
        this.codeGare = codeGare;
    }

    public String getNomGare() {
        return nomGare;
    }

    public void setNomGare(String nomGare) {
        this.nomGare = nomGare;
    }

    public boolean isEstFret() {
        return estFret;
    }

    public void setEstFret(boolean estFret) {
        this.estFret = estFret;
    }

    public boolean isEstVoyageur() {
        return estVoyageur;
    }

    public void setEstVoyageur(boolean estVoyageur) {
        this.estVoyageur = estVoyageur;
    }

    public Commune getCommune() {
        return commune;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }

    @Override
    public String toString() {
        return "Gare [codeGare=" + codeGare + ", nomGare=" + nomGare + ", estFret=" + estFret + ", estVoyageur=" + estVoyageur + "]";
    }

    public void updateGareName(String newNomGare) {
        this.nomGare = newNomGare;
    }
}
