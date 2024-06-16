package data;

import java.util.ArrayList;



/**
* Class who represents a commune with different parameters, we can manipulate it with different method. 
* 
* 
*/
public class Commune {
    /**
     * The unique number representing the commune.
     */
    private int idCommune;

    /**
     * The name of the commune.
     */
    private String nomCommune;

    /**
     * The number of houses.
     */
    private int nbMaison;

    /**
     * The number of apartments.
     */
    private int nbAppart;

    /**
     * The average price of properties in the commune.
     */
    private double prixMoyen;

    /**
     * The average price per square meter in the commune.
     */
    private double prixM2Moyen;

    /**
     * The average surface area in the commune.
     */
    private double surfaceMoy;

    /**
     * The total cultural expenses in the commune.
     */
    private double depCulturellesTotales;

    /**
     * The total budget of the commune.
     */
    private double budgetTotal;

    /**
     * The population of the commune.
     */
    private int population;

    /**
     * The department of the commune.
     *
     * Allows connection with Departement.java
     */
    private Departement departement;

    /**
     * List of neighboring communes.
     *
     * Allows a connection with itself (Commune.java)
     */
    private ArrayList<Commune> communesVoisines;

    /**
     * The train station (Gare) associated with the commune.
     */
    private Gare gare;

    /**
     * The year associated with the commune.
     */
    private Annee annee;



    /**
     * Constructs a new Commune object with the specified parameters.
     *
     * @param annee                 the year associated with the commune
     * @param idCommune             the unique number which represents the commune
     * @param nomCommune            the name of the commune
     * @param nbMaison              the number of houses in the commune
     * @param nbAppart              the number of apartments in the commune
     * @param prixMoyen             the average price of properties in the commune
     * @param prixM2Moyen           the average price per square meter in the commune
     * @param surfaceMoy            the average surface area in the commune
     * @param depCulturellesTotales the total cultural expenses in the commune
     * @param budgetTotal           the total budget of the commune
     * @param population            the population of the commune
     * @param departement           the department of the commune
     * @throws IllegalArgumentException if any of the parameters are invalid:
     *                                  - if idCommune is not valid
     *                                  - if nomCommune is null or empty
     *                                  - if departement is null or invalid
     *                                  - if annee is null
     */
    public Commune(Annee annee, int idCommune, String nomCommune, int nbMaison, int nbAppart, double prixMoyen, double prixM2Moyen, double surfaceMoy, double depCulturellesTotales, double budgetTotal, int population, Departement departement) {
        this.communesVoisines = new ArrayList<>();

        if (!isValidIdCommune(idCommune)) {
            throw new IllegalArgumentException("Identifiant de commune invalide : " + idCommune);
        }
        if (nomCommune == null || nomCommune.trim().isEmpty()) {
            throw new IllegalArgumentException("Nom de commune invalide : " + nomCommune);
        }
        if (departement == null || !isValidDepartement(departement)) {
            throw new IllegalArgumentException("Département invalide : " + departement);
        }
        if (annee == null) {
            throw new IllegalArgumentException("Année invalide : " + annee);
        }

        this.annee = annee;
        this.idCommune = idCommune;
        this.nomCommune = nomCommune;
        this.nbMaison = nbMaison;
        this.nbAppart = nbAppart;
        this.prixMoyen = prixMoyen;
        this.prixM2Moyen = prixM2Moyen;
        this.surfaceMoy = surfaceMoy;
        this.depCulturellesTotales = depCulturellesTotales;
        this.budgetTotal = budgetTotal;
        this.population = population;
        this.departement = departement;
    }













/* ------ Getters ------ */

    /**
     * Returns the ID of the commune.
     *
     * @return The ID of the commune.
     */
    public int getIdCommune() {
        return idCommune;
    }

    /**
     * Returns the name of the commune.
     *
     * @return The name of the commune.
     */
    public String getNomCommune() {
        return nomCommune;
    }

    /**
     * Returns the number of houses in the commune.
     *
     * @return The number of houses.
     */
    public int getNbMaison() {
        return nbMaison;
    }

    /**
     * Returns the number of apartments in the commune.
     *
     * @return The number of apartments.
     */
    public int getNbAppart() {
        return nbAppart;
    }

    /**
     * Returns the average price of properties in the commune.
     *
     * @return The average price.
     */
    public double getPrixMoyen() {
        return prixMoyen;
    }

    /**
     * Returns the average price per square meter in the commune.
     *
     * @return The average price per square meter.
     */
    public double getPrixM2Moyen() {
        return prixM2Moyen;
    }

    /**
     * Returns the average surface area in the commune.
     *
     * @return The average surface area.
     */
    public double getSurfaceMoy() {
        return surfaceMoy;
    }

    /**
     * Returns the total cultural expenses in the commune.
     *
     * @return The total cultural expenses.
     */
    public double getDepCulturellesTotales() {
        return depCulturellesTotales;
    }

    /**
     * Returns the total budget of the commune.
     *
     * @return The total budget.
     */
    public double getBudgetTotal() {
        return budgetTotal;
    }

    /**
     * Returns the population of the commune.
     *
     * @return The population.
     */
    public int getPopulation() {
        return population;
    }


    /**
     * Returns the department to which the commune belongs.
     *
     * @return The department.
     */
    public Departement getDepartement() {
        return departement;
    }

    /**
     * Returns the list of neighboring communes.
     *
     * @return The list of neighboring communes.
     */
    public ArrayList<Commune> getCommunesVoisines() {
        return communesVoisines;
    }


    /**
     * Returns the year associated with this commune.
     *
     * @return the year associated with this commune.
     */
    public Annee getAnnee() {
        return this.annee;
    }




    /* ------ Setters ------ */

    /**
     * Sets the ID of the commune.
     *
     * @param idCommune The ID of the commune.
     * @throws IllegalArgumentException Si l'ID de la commune est invalide.
     */
    public void setIdCommune(int idCommune) {
        if (isValidIdCommune(idCommune)) {
            this.idCommune = idCommune;
        } else {
            throw new IllegalArgumentException("ID de commune invalide : " + idCommune);
        }
    }

    /**
     * Sets the name of the commune.
     *
     * @param nomCommune The name of the commune.
     * @throws IllegalArgumentException Si le nom de la commune est invalide.
     */
    public void setNomCommune(String nomCommune) {
        if (nomCommune != null && !nomCommune.trim().isEmpty()) {
            this.nomCommune = nomCommune;
        } else {
            throw new IllegalArgumentException("Nom de commune invalide : " + nomCommune);
        }
    }

    /**
     * Sets the number of houses in the commune.
     *
     * @param nbMaison The number of houses.
     */
    public void setNbMaison(int nbMaison) {
        this.nbMaison = nbMaison;
    }

   /**
     * Sets the number of apartments in the commune.
     *
     * @param nbAppart The number of apartments.
     */
    public void setNbAppart(int nbAppart) {
        this.nbAppart = nbAppart;
    }

    /**
     * Sets the average price of properties in the commune.
     *
     * @param prixMoyen The average price.
     */
    public void setPrixMoyen(double prixMoyen) {
        this.prixMoyen = prixMoyen;
    }

    /**
     * Sets the average price per square meter in the commune.
     *
     * @param prixM2Moyen The average price per square meter.
     */
    public void setPrixM2Moyen(double prixM2Moyen) {
        this.prixM2Moyen = prixM2Moyen;
    }

    /**
     * Sets the average surface area in the commune.
     *
     * @param surfaceMoy The average surface area.
     */
    public void setSurfaceMoy(double surfaceMoy) {
        this.surfaceMoy = surfaceMoy;
    }

    /**
     * Sets the total cultural expenses in the commune.
     *
     * @param depCulturellesTotales The total cultural expenses.
     */
    public void setDepCulturellesTotales(double depCulturellesTotales) {
        this.depCulturellesTotales = depCulturellesTotales;
    }

    /**
     * Sets the total budget of the commune.
     *
     * @param budgetTotal The total budget.
     */
    public void setBudgetTotal(double budgetTotal) {
        this.budgetTotal = budgetTotal;
    }

    /**
     * Sets the population of the commune.
     *
     * @param population The population.
     */
    public void setPopulation(int population) {
        this.population = population;
    }


    /**
     * Sets the department to which the commune belongs.
     *
     * @param departement The department.
     * @throws IllegalArgumentException Si le département est invalide.
     */
    public void setDepartement(Departement departement) {
        if (departement != null) {
            if (departement.getIdDep() == 56 || departement.getIdDep() == 29 || departement.getIdDep() == 22 || departement.getIdDep() == 35) {
                this.departement = departement;
            } else {
                throw new IllegalArgumentException("Département invalide : " + departement.getIdDep());
            }
        } else {
            throw new IllegalArgumentException("Département invalide : null");
        }
    }

    /**
     * Sets the year associated with the commune.
     *
     * @param annee The year.
     * @throws IllegalArgumentException Si l'année est négative.
     */
    public void setAnnee(Annee annee) {
        if (annee.getAnnee() >= 0) {
            this.annee = annee;
        } else {
            throw new IllegalArgumentException("Année invalide : " + annee.getAnnee());
        }
    }



    





/* ------ Other Method ------ */

   /**
     * Returns a string representation of the Commune object.
     *
     * @return a string representation of the Commune object
     */
    @Override
    public String toString() {
        return "Commune{" +
                "idCommune=" + idCommune +
                ", nomCommune='" + nomCommune + '\'' +
                ", nbMaison=" + nbMaison +
                ", nbAppart=" + nbAppart +
                ", prixMoyen=" + prixMoyen +
                ", prixM2Moyen=" + prixM2Moyen +
                ", surfaceMoy=" + surfaceMoy +
                ", depCulturellesTotales=" + depCulturellesTotales +
                ", budgetTotal=" + budgetTotal +
                ", population=" + population +
                '}';
    }

    /**
     * Adds a neighboring commune to the list of neighboring communes.
     *
     * @param commune the commune to add as a neighbor
     */
    public void addVoisine(Commune commune) {
        this.communesVoisines.add(commune);
    }

    /**
     * Calculates the total number of properties (houses and apartments) in the commune.
     *
     * @return the total number of properties in the commune
     */
    public int getProprieteTotal() {
        return nbMaison + nbAppart;
    }

    /**
     * Calculates the average price per property (house or apartment) in the commune.
     *
     * @return the average price per property in the commune
     */
    public double prixMoyenParPropriete() {
        double ret;
        int totalProprietes = getNbMaison() + getNbAppart();
        if (totalProprietes == 0) {
            ret = 0;
        } else {
            ret = (getPrixMoyen() + getPrixM2Moyen()) / totalProprietes;
        }
        return ret;
    }

    
    
    


   
    


    /**
    * Determines if the commune is the most significant among its neighbors based on various factors
    * including the total number of properties, total budget, and population.
    *
    * @return True if the commune is the most significant among its neighbors, otherwise false.
    */
    public boolean isMostImportant() {
        boolean ret = true;
        // Check if the commune has neighboring communes
        if (!(communesVoisines != null && !communesVoisines.isEmpty())) {
            ret = false;
        }
        if(ret){
            // Initialize variables to keep track of the maximum values
            int maxProperties = 0;
            double maxBudget = 0;
            double maxPopulation = 0;

            // Iterate through neighboring communes to find the maximum values
            for (Commune neighbor : communesVoisines) {
                maxProperties = Math.max(maxProperties, neighbor.getProprieteTotal());
                maxBudget = Math.max(maxBudget, neighbor.getBudgetTotal());
                maxPopulation = Math.max(maxPopulation, neighbor.getPopulation());
            }
            ret = (getProprieteTotal() >= maxProperties) && (getBudgetTotal() >= maxBudget) && (getPopulation() >= maxPopulation);
        }

        // Check if the current commune has the maximum values
        return ret;
    }


    /**
    * Identifies the commune with the highest average price per property among its neighbors.
    *
    * @return The name of the commune with the highest average price per property among its neighbors,
    *         or null if there are no neighboring communes.
    */
    public String highestNeighboursPrice() {
        String ret = null; 
        // Check if the commune has neighboring communes
        if (!(communesVoisines != null && !communesVoisines.isEmpty())) {
            return ret;
        }

        String highestPriceCommune = null;
        double highestAveragePrice = 0;

        // Iterate through neighboring communes to find the one with the highest average price per property
        for (Commune neighbor : communesVoisines) {
            double neighborAveragePrice = neighbor.prixMoyenParPropriete();
            if (neighborAveragePrice > highestAveragePrice) {
                highestAveragePrice = neighborAveragePrice;
                highestPriceCommune = neighbor.getNomCommune();
                ret = highestPriceCommune;
            }
        }

        return ret;
    }


    /**
    * Calculates the ratio of cultural expenses per inhabitant.
    *
    * @return The ratio of cultural expenses per inhabitant.
    */
    public double culturalExpensesPerInhabitant() {
        double ret;
        if (getPopulation() == 0) {
            ret = 0;
        }else{
            ret = getDepCulturellesTotales() / getPopulation();
        }
        

        return ret;
    }

   /**
     * Checks if the commune has a train station (Gare).
     *
     * @return true if the commune has a train station, otherwise false.
     */
    public boolean aUneGare() {
        return gare != null;
    }

    /**
     * Returns the train station (Gare) of the commune.
     *
     * @return the train station of the commune, or null if the commune has no train station.
     */
    public Gare getGare() {
        return gare;
    }

    /**
     * Adds a new train station (Gare) to the commune.
     *
     * @param nouvelleGare the new train station to associate with the commune.
     * @throws RuntimeException if the commune already has a train station.
     */
    public void addGare(Gare nouvelleGare) {
        if (gare == null) {
            gare = nouvelleGare;
        } else {
            throw new RuntimeException("Une commune ne peut avoir qu'une seule gare.");
        }
    }


    /**
     * Removes the association of this commune with a train station (Gare).
     * If the commune is currently associated with a train station, it removes the association by setting the gare to null.
     */
    public void removeGare() {
        if (gare != null) {
            gare = null;
        }
    }


    /**
     * Method which allows checking if the id of the commune is valid or not.
     * 
     * @param idCommune the id of the commune we want to validate
     * @return false if not valid, else return true.
     */
    private boolean isValidIdCommune(int idCommune) {
        boolean ret = true;
        String idString = String.valueOf(idCommune);

        // Check if the idCommune has exactly 5 digits
        if (idString.length() != 5) {
            ret = false;
        } else {
            // Check if the prefix is one of the valid values
            String prefix = idString.substring(0, 2);
            ret = prefix.equals("29") || prefix.equals("35") || prefix.equals("22") || prefix.equals("56");
        }

        return ret;
    }

    /**
     * Validates if a department is valid based on its ID.
     *
     * @param departement The department to validate.
     * @return True if the department ID is valid (56, 29, 22, or 35), false otherwise.
     */
    private boolean isValidDepartement(Departement departement) {
        int id = departement.getIdDep();
        boolean ret = false;
        if (id == 56 || id == 29 || id == 22 || id == 35) {
            ret = true;
        }
        return ret;
    }

}