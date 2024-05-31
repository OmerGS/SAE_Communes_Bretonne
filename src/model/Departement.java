import java.util.List;

public class Departement {
    private int idDep;
    private String nomDep;
    private float invesCulture2019;
    private List<Commune> communes;

    
    public Departement(int idDep, String nomDep, float invesCulture2019) {
        this.idDep = idDep;
        this.nomDep = nomDep;
        this.invesCulture2019 = invesCulture2019;
    }

    public int getIdDep() {
        return idDep;
    }

    public void setIdDep(int idDep) {
        this.idDep = idDep;
    }

    public String getNomDep() {
        return nomDep;
    }

    public void setNomDep(String nomDep) {
        this.nomDep = nomDep;
    }

    public float getInvesCulture2019() {
        return invesCulture2019;
    }

    public void setInvesCulture2019(float invesCulture2019) {
        this.invesCulture2019 = invesCulture2019;
    }

    public List<Commune> getCommunes() {
        return communes;
    }

    public void setCommunes(List<Commune> communes) {
        this.communes = communes;
    }

    public void addCommune(Commune commune) {
        this.communes.add(commune);
    }

    @Override
    public String toString() {
        return "Departement [idDep=" + idDep + ", nomDep=" + nomDep + ", invesCulture2019=" + invesCulture2019 + "]";
    }
}
