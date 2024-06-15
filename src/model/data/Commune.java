package data;

import java.util.ArrayList;



/**
* Class who represents a commune with different parameters, we can manipulate it with different method. 
* 
* 
*/
public class Commune {
    /**
    * The unique number which represent the commune. 
    */
    private int idCommune;

    /**
    * The name of the commune. 
    */
    private String nomCommune;

    /**
    * The number of house. 
    */
    private int nbMaison;

    /**
    * The number of appartment. 
    */
    private int nbAppart;


    private double prixMoyen;
    private double prixM2Moyen;
    private double surfaceMoy;
    private double depCulturellesTotales;
    private double budgetTotal;
    private int population;


    /**
    * The department of the commune. 
    * 
    * Allow connection with Departement.java 
    */
    private Departement departement;  

    
    /**
    * List of neighbours communes. 
    * 
    * Allow a connection with itself (Commune.java) 
    */
    private ArrayList<Commune> communesVoisines;

    private Gare gare;

    private Annee annee;
    


    public Commune(Annee annee, int idCommune, String nomCommune, int nbMaison, int nbAppart, double prixMoyen, 
                   double prixM2Moyen, double surfaceMoy, double depCulturellesTotales, double budgetTotal, 
                   int population, Departement departement) {
        this.communesVoisines = new ArrayList<>();

        if (!isValidIdCommune(idCommune)) {
            throw new IllegalArgumentException("Invalid commune ID: " + idCommune);
        }
        if (nomCommune == null || nomCommune.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid commune name: " + nomCommune);
        }
        if (departement == null || !isValidDepartement(departement)) {
            throw new IllegalArgumentException("Invalid department: " + departement);
        }
        if (annee == null) {
            throw new IllegalArgumentException("Invalid year: " + annee);
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


    public Annee getAnnee() {
        return this.annee;
    }




/* ------ Setters ------ */

/**
 * Sets the ID of the commune.
 *
 * @param idCommune The ID of the commune.
 */
public void setIdCommune(int idCommune) {
    if (isValidIdCommune(idCommune)) {
        this.idCommune = idCommune;
    } else {
        throw new RuntimeException("Invalid commune ID: " + idCommune);
    }
}

/**
 * Sets the name of the commune.
 *
 * @param nomCommune The name of the commune.
 */
public void setNomCommune(String nomCommune) {
    if (nomCommune != null && !nomCommune.trim().isEmpty()) {
        this.nomCommune = nomCommune;
    } else {
        throw new RuntimeException("Invalid commune name: " + nomCommune);
    }
}

/**
 * Sets the number of houses in the commune.
 *
 * @param nbMaison The number of houses.
 * @throws InvalidValueException If the number of houses is negative.
 */
public void setNbMaison(int nbMaison) {
    if(nbMaison >= 0){
        this.nbMaison = nbMaison;
    }else{
        throw new RuntimeException("Parametre invalide");
    }
}

/**
 * Sets the number of apartments in the commune.
 *
 * @param nbAppart The number of apartments.
 * @throws InvalidValueException If the number of apartments is negative.
 */
public void setNbAppart(int nbAppart) {
    if(nbAppart >= 0){
        this.nbAppart = nbAppart;
    }else{
        throw new RuntimeException("Parametre invalide");
    }
}

/**
 * Sets the average price of properties in the commune.
 *
 * @param prixMoyen The average price.
 * @throws InvalidValueException If the average price is negative.
 */
public void setPrixMoyen(double prixMoyen) {
    if(prixMoyen >= 0){
        this.prixMoyen = prixMoyen;
    }else{
        throw new RuntimeException("Parametre invalide");
    }
}

/**
 * Sets the average price per square meter in the commune.
 *
 * @param prixM2Moyen The average price per square meter.
 * @throws InvalidValueException If the average price per square meter is negative.
 */
public void setPrixM2Moyen(double prixM2Moyen) {
    if(prixM2Moyen >= 0){
        this.prixM2Moyen = prixM2Moyen;
    }else{
        throw new RuntimeException("Parametre invalide");
    }
}

/**
 * Sets the average surface area in the commune.
 *
 * @param surfaceMoy The average surface area.
 * @throws InvalidValueException If the average surface area is negative.
 */
public void setSurfaceMoy(double surfaceMoy) {
    if(surfaceMoy >= 0){
        this.surfaceMoy = surfaceMoy;
    }else{
        throw new RuntimeException("Parametre invalide");
    }
}

/**
 * Sets the total cultural expenses in the commune.
 *
 * @param depCulturellesTotales The total cultural expenses.
 * @throws InvalidValueException If the total cultural expenses are negative.
 */
public void setDepCulturellesTotales(double depCulturellesTotales) {
    if(depCulturellesTotales >= 0){
        this.depCulturellesTotales = depCulturellesTotales;
    }else{
        throw new RuntimeException("Parametre invalide");
    }
}

/**
 * Sets the total budget of the commune.
 *
 * @param budgetTotal The total budget.
 * @throws InvalidValueException If the total budget is negative.
 */
public void setBudgetTotal(double budgetTotal) {
    if(budgetTotal>= 0){
        this.budgetTotal = budgetTotal;
    }else{
        throw new RuntimeException("Parametre invalide");
    }
}

/**
 * Sets the population of the commune.
 *
 * @param population The population.
 * @throws InvalidValueException If the population is negative.
 */
public void setPopulation(int population) {
    if(population >= 0){
        this.population = population;
    }else{
        throw new RuntimeException("Parametre invalide");
    }
}

/**
 * Sets the department to which the commune belongs.
 *
 * @param departement The department.
 */
public void setDepartement(Departement departement) {
    if(departement != null){
        if (departement.getIdDep() == 56 || departement.getIdDep() == 29 || departement.getIdDep() == 22 || departement.getIdDep() == 35) {
            this.departement = departement;
        } else {
            throw new IllegalArgumentException();
        }
    }else{
        throw new IllegalArgumentException();
    }
}


public void setAnnee(Annee annee) {
    if(annee.getAnnee() >= 0){
        this.annee = annee;
    }else{
        throw new RuntimeException("Parametre invalide");
    }
}


    





/* ------ Other Method ------ */

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

    
    public void addVoisine(Commune commune) {
        this.communesVoisines.add(commune);
    }

    public int getProprieteTotal() {
        return nbMaison + nbAppart;
    }
    
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
     * Vérifie si la commune possède une gare.
     *
     * @return true si la commune possède une gare, sinon false.
     */
    public boolean aUneGare() {
        return gare != null;
    }

    /**
     * Renvoie la gare de la commune.
     *
     * @return la gare de la commune, ou null si la commune n'a pas de gare.
     */
    public Gare getGare() {
        return gare;
    }

    /**
     * Ajoute une nouvelle gare à la commune.
     *
     * @param nouvelleGare la nouvelle gare à associer à la commune
     */
    public void ajouterGare(Gare nouvelleGare) {
        if (gare == null) {
            gare = nouvelleGare;
        } else {
            throw new RuntimeException("Une commune ne peut avoir qu'une seule gare.");
        }
    }

    private boolean isValidDepartement(Departement departement) {
        int id = departement.getIdDep();
        boolean ret = false;
        if(id == 56 || id == 29 || id == 22 || id == 35){
            ret = true;
        }
        return ret;
    }
}