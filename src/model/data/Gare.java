package data;


public class Gare {
    /**
    * The unique of the Train Station. 
    */
    private int codeGare;

    /**
    * The name of the Train Station 
    */
    private String nomGare;

    /**
    * True if the Train Station allow marchandise, else it's false. 
    */
    private boolean estFret;

    /**
    * True if the Train Station accept public train for the people, else it's false. 
    */
    private boolean estVoyageur;

    /**
    * The commune of the Train Station. 
    */
    private Commune commune; 

    /**
    * Constructors of Train Station (Gare)
    *
    * @param codeGare
    * @param nomGare
    * @param estFret
    * @param estVoyageur
    * @param commune
    */
    public Gare(int codeGare, String nomGare, boolean estFret, boolean estVoyageur, Commune commune) {
        this.codeGare = codeGare;
        this.nomGare = nomGare;
        this.estFret = estFret;
        this.estVoyageur = estVoyageur;
        this.commune = commune;
    }

    
    
    /* ----- Getters ----- */

    public int getCodeGare() {
        return codeGare;
    }

    public String getNomGare() {
        return nomGare;
    }

    public boolean isEstFret() {
        return estFret;
    }

    public boolean isEstVoyageur() {
        return estVoyageur;
    }

    public Commune getCommune() {
        return commune;
    }

    /* ----- Setters ------ */

    public void setCodeGare(int codeGare) {
        this.codeGare = codeGare;
    }

    public void setNomGare(String nomGare) {
        this.nomGare = nomGare;
    }

    public void setEstFret(boolean estFret) {
        this.estFret = estFret;
    }

    public void setEstVoyageur(boolean estVoyageur) {
        this.estVoyageur = estVoyageur;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }


    /* ----- Other Methods ----- */


    @Override
    public String toString() {
        return "Gare [codeGare=" + codeGare + ", nomGare=" + nomGare + ", estFret=" + estFret + ", estVoyageur=" + estVoyageur + "]";
    }
}
