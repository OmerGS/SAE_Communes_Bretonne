package data;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;



/**
 * Represents a department (Departement) with a unique id, name, investment in culture for 2019,
 * and a list of associated communes.
 */
public class Departement {


    private static Set<Integer> idsUtilises = new HashSet<>();
    /**
     * The unique id of the department.
     */
    private int idDep;

    /**
     * The name of the department.
     */
    private String nomDep;

    /**
     * The investment in culture for 2019.
     */
    private double invesCulture2019;

    /**
     * The list of communes associated with the department.
     */
    private ArrayList<Commune> communes;

    /**
     * The list of communes associated with the department.
     */
    private ArrayList<Aeroport> aeroports;

    /**
     * Static list to keep track of all department instances.
     */
    private static ArrayList<Departement> departements = new ArrayList<>();


    public Departement(int idDep, String nomDep, double invesCulture2019){
        if((!idsUtilises.contains(idDep)) && nomDep != null && invesCulture2019 > 0){
            this.idDep = idDep;
            this.nomDep = nomDep;
            this.invesCulture2019 = invesCulture2019;
            idsUtilises.add(idDep);
            this.communes = new ArrayList<Commune>();
            this.aeroports = new ArrayList<Aeroport>();
            departements.add(this);
        }else{
            throw new RuntimeException("parametre invalide");
        }
    }


    public int getIdDep() {
        return this.idDep;
    }


    public String getNomDep() {
        return this.nomDep;
    }

 
    public double getInvesCulture2019() {
        return this.invesCulture2019;
    }


    public ArrayList<Commune> getCommunes() {
        return this.communes;
    }

    public ArrayList<Aeroport> getAeroport() {
        return this.aeroports;
    }


    // Méthode statique pour obtenir un département par son identifiant
    public static Departement getDepartementById(int idDep) {
        for (Departement dep : departements) {
            if (dep.getIdDep() == idDep) {
                return dep;
            }
        }
        return null;  // Retourne null si aucun département n'est trouvé avec l'identifiant donné
    }


    public void setIdDep(int idDep) {
        if (this.idDep != idDep) {
            if (idsUtilises.contains(idDep)) {
                throw new IllegalArgumentException("ID already in use: " + idDep);
            }
            idsUtilises.remove(this.idDep);
            this.idDep = idDep;
            idsUtilises.add(idDep);
        }
    }

    public void setNomDep(String nomDep){
        if (nomDep == null || nomDep.trim().isEmpty()) {
            throw new RuntimeException("The name of the department cannot be null or empty.");
        }
        this.nomDep = nomDep;
    }


    public void setInvesCulture2019(double invesCulture2019){
        if(invesCulture2019 >= 0){
            this.invesCulture2019 = invesCulture2019;
        }else{
            throw new RuntimeException();
        }
    }


    public void setCommunes(ArrayList<Commune> communes) {
        if(communes != null && communes.size() > 0){
            this.communes = communes;
        }else{
            throw new RuntimeException();
        }
    }

    public void setAeroport(ArrayList<Aeroport> aeroports) {
        if(aeroports != null && aeroports.size() > 0){
            this.aeroports = aeroports;
        }else{
            throw new RuntimeException();
        }
    }



    @Override
    public String toString() {
        return "Departement [idDep=" + this.idDep + ", nomDep=" + this.nomDep + ", invesCulture2019=" + this.invesCulture2019 + "]";
    }


    public int getTotalPopulation() {
        int totalPopulation = 0;
        for (Commune commune : this.communes) {
            totalPopulation += commune.getPopulation();
        }
        return totalPopulation;
    }

    public void addCommune(Commune commune) {
        if (commune.getDepartement().getIdDep() == this.idDep) {
            this.communes.add(commune);
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    public void addAeroport(Aeroport aeroport) {
    
        if (aeroport.getDepartement().getIdDep() == this.idDep) {
            this.aeroports.add(aeroport);
        } else {
            throw new IllegalArgumentException();
        }
    }






}