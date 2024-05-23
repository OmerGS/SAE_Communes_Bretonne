public class Commune {
    private int idComunne;
    private String nomCommune;

    public Commune(int idCommune, String nomCommune){
        if(idCommune > 0){
            this.idComunne = idCommune;
        } else {
            throw new RuntimeException("L'ID de la commune doit être positive.");
        }

        if(nomCommune != null && nomCommune != ""){
            this.nomCommune = nomCommune;
        } else {
            throw new RuntimeException("Le nom de la commune doit être renseigné.");
        }
    }

    public void setIdComunne(int idComunne) {
        this.idComunne = idComunne;
    }


    public void setNomCommune(String nomCommune) {
        this.nomCommune = nomCommune;
    }

    public int getIdComunne() {
        return idComunne;
    }

    public String getNomCommune() {
        return nomCommune;
    }

    public Commune getCommune(){
        return(this);
    }
}
