package data;


import java.util.ArrayList;

import javax.naming.InvalidNameException;


/**
 * Represents a department (Departement) with a unique id, name, investment in culture for 2019,
 * and a list of associated communes.
 */
public class Departement {
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
        this.idDep = idDep;
        setNomDep(nomDep);
        this.invesCulture2019 = invesCulture2019;
        this.communes = new ArrayList<Commune>();
        this.aeroports = new ArrayList<Aeroport>();
        departements.add(this);
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
        this.idDep = idDep;
    }


    public void setNomDep(String nomDep) throws RuntimeException {
        if (nomDep == null || nomDep.trim().isEmpty()) {
            throw new RuntimeException("The name of the department cannot be null or empty.");
        }
        this.nomDep = nomDep;
    }


    public void setInvesCulture2019(double invesCulture2019){
        this.invesCulture2019 = invesCulture2019;
    }


    public void setCommunes(ArrayList<Commune> communes) {
        this.communes = communes;
    }

    public void setAeroport(ArrayList<Aeroport> aeroports) {
        this.aeroports = aeroports;
    }

    public void addCommune(Commune commune) {
        this.communes.add(commune);
    }



    public void addAeroport(Aeroport aeroport) {
        this.aeroports.add(aeroport);
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








}