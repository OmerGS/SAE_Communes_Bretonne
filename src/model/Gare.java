public class Gare{
    private int codeGare;
    private String nomGare;
    private boolean estFret;
    private boolean estVoyageur;


    public Gare(int codeGare, String nomGare, boolean estFret, boolean estVoyageur){
        if(codeGare > 0){
            this.codeGare = codeGare;
        } else {
            throw new RuntimeException("Le code de la gare doit être positive.");
        }
        
        if(nomGare != null && nomGare != ""){
            this.nomGare = nomGare;
        } else {
            throw new RuntimeException("Le nom de la gare doit être un nom qui n'est pas nulle et pas vide.");
        }

        this.estFret = estFret;
        this.estVoyageur = estVoyageur;
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


    

}