package data;

import java.util.ArrayList;
import java.util.List;
import data.exceptions.*;

/**
* Class who represents a commune with different parameters, we can manipulate it with different method. 
* 
* @author O.Gunes, R.Peron, C.Brayan
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
    private double population;

    /**
    * List of airport.
    * 
    * Allow connection with Aeroport.java 
    */
    private List<Aeroport> aeroports;

    /**
    * List of trainstation.
    *
    * Allow conneciton with Gare.java 
    */
    private List<Gare> gares;

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
    private List<Commune> communesVoisines;
    






    

    public Commune(int idCommune, String nomCommune, int nbMaison, int nbAppart, double prixMoyen, double prixM2Moyen, double surfaceMoy, double depCulturellesTotales, double budgetTotal, double population, Departement departement) {
        this.aeroports = new ArrayList<Aeroport>();
        this.gares = new ArrayList<Gare>();
        this.communesVoisines = new ArrayList<Commune>();


        //idCommune
        if (isValidIdCommune(idCommune)) {
            this.idCommune = idCommune;
        } else {
            throw new InvalidCommuneIdException("Numéro de commune invalide : " + idCommune);
        }

        //nomCommune
        if (nomCommune != null && !nomCommune.trim().isEmpty()) {
            this.nomCommune = nomCommune;
        } else {
            throw new InvalidCommuneNameException("Nom de commune invalide : " + nomCommune);
        }


        this.nbMaison = validateNonNegativeValue(nbMaison, "Nombre de maisons");
        this.nbAppart = validateNonNegativeValue(nbAppart, "Nombre d'appartement");
        this.prixMoyen = validateNonNegativeValue(prixMoyen, "Prix moyen");
        this.prixM2Moyen = validateNonNegativeValue(prixM2Moyen, "Prix m2 moyen");
        this.surfaceMoy = validateNonNegativeValue(surfaceMoy, "Surface moyenne");
        this.depCulturellesTotales = validateNonNegativeValue(depCulturellesTotales, "Dépenses culturelles totales");
        this.budgetTotal = validateNonNegativeValue(budgetTotal, "Budget total");
        this.population = validateNonNegativeValue(population, "Population");


        this.departement = departement;
    }

    /**
    * Method which allow to check if parameters is >= 0. 
    * Signature for double and fieldName
    *
    * @param value the parameters which we want to check
    * @param fieldName the fieldname of global var.
    * @return double
    * @throws IllegalArgumentException if provided value is negative
    */
    private double validateNonNegativeValue(double value, String fieldName) {
        double ret = -1;
        if (value >= 0) {
            ret = value;
        } else {
            throw new IllegalArgumentException(fieldName + " invalide : " + value);
        }
        return ret;
    }

    /**
    * Method which allow to check if parameters is >= 0. 
    * Signature for int and fieldName
    *
    * @param value the parameters which we want to check
    * @param fieldName the fieldname of global var.
    * @return int
    * @throws IllegalArgumentException if provided value is negative
    */
    private int validateNonNegativeValue(int value, String fieldName) {
        int ret = -1;
        if (value >= 0) {
            ret = value;
        } else {
            throw new IllegalArgumentException(fieldName + " invalide : " + value);
        }
        return ret;
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
    public double getPopulation() {
        return population;
    }

    /**
     * Returns the list of airports in the commune.
     *
     * @return The list of airports.
     */
    public List<Aeroport> getAeroports() {
        return aeroports;
    }

    /**
     * Returns the list of train stations in the commune.
     *
     * @return The list of train stations.
     */
    public List<Gare> getGares() {
        return gares;
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
    public List<Commune> getCommunesVoisines() {
        return communesVoisines;
    }




/* ------ Setters ------ */

    /**
    * Sets the ID of the commune.
    *
    * @param idCommune The ID of the commune.
    * @throws InvalidCommuneIdException If the provided ID is invalid.
    */
    public void setIdCommune(int idCommune) {
        if (isValidIdCommune(idCommune)) {
            this.idCommune = idCommune;
        } else {
            throw new InvalidCommuneIdException("Invalid commune ID: " + idCommune);
        }
    }

    /**
     * Sets the name of the commune.
     *
     * @param nomCommune The name of the commune.
     * @throws InvalidCommuneNameException If the provided name is invalid.
     */
    public void setNomCommune(String nomCommune) {
        if (nomCommune != null && !nomCommune.trim().isEmpty()) {
            this.nomCommune = nomCommune;
        } else {
            throw new InvalidCommuneNameException("Invalid commune name: " + nomCommune);
        }
    }

    /**
     * Sets the number of houses in the commune.
     *
     * @param nbMaison The number of houses.
     */
    public void setNbMaison(int nbMaison) {
        this.nbMaison = validateNonNegativeValue(nbMaison, "Number of houses");
    }

    /**
     * Sets the number of apartments in the commune.
     *
     * @param nbAppart The number of apartments.
     */
    public void setNbAppart(int nbAppart) {
        this.nbAppart = validateNonNegativeValue(nbAppart, "Number of apartments");
    }

    /**
     * Sets the average price of properties in the commune.
     *
     * @param prixMoyen The average price.
     */
    public void setPrixMoyen(double prixMoyen) {
        this.prixMoyen = validateNonNegativeValue(prixMoyen, "Average price");
    }

    /**
     * Sets the average price per square meter in the commune.
     *
     * @param prixM2Moyen The average price per square meter.
     */
    public void setPrixM2Moyen(double prixM2Moyen) {
        this.prixM2Moyen = validateNonNegativeValue(prixM2Moyen, "Average price per square meter");
    }

    /**
     * Sets the average surface area in the commune.
     *
     * @param surfaceMoy The average surface area.
     */
    public void setSurfaceMoy(double surfaceMoy) {
        this.surfaceMoy = validateNonNegativeValue(surfaceMoy, "Average surface area");
    }

    /**
     * Sets the total cultural expenses in the commune.
     *
     * @param depCulturellesTotales The total cultural expenses.
     */
    public void setDepCulturellesTotales(double depCulturellesTotales) {
        this.depCulturellesTotales = validateNonNegativeValue(depCulturellesTotales, "Total cultural expenses");
    }

    /**
     * Sets the total budget of the commune.
     *
     * @param budgetTotal The total budget.
     */
    public void setBudgetTotal(double budgetTotal) {
        this.budgetTotal = validateNonNegativeValue(budgetTotal, "Total budget");
    }

    /**
     * Sets the population of the commune.
     *
     * @param population The population.
     */
    public void setPopulation(double population) {
        this.population = validateNonNegativeValue(population, "Population");
    }

    /**
    * Sets the list of airports in the commune.
    *
    * @param aeroports The list of airports.
    * @throws IllegalArgumentException If the provided list is null.
    */
    public void setAeroports(List<Aeroport> aeroports) {
        if (aeroports != null) {
            this.aeroports = aeroports;
        } else {
            throw new IllegalArgumentException("The list of airports cannot be null.");
        }
    }

    /**
     * Sets the list of train stations in the commune.
     *
     * @param gares The list of train stations.
     * @throws IllegalArgumentException If the provided list is null.
     */
    public void setGares(List<Gare> gares) {
        if (gares != null) {
            this.gares = gares;
        } else {
            throw new IllegalArgumentException("The list of train stations cannot be null.");
        }
    }

    /**
    * Sets the department to which the commune belongs.
    *
    * @param departement The department.
    * @throws RuntimeException If the provided department code is invalid.
    */
    public void setDepartement(Departement departement) {
        String departementString = String.valueOf(departement);
        if (departementString.length() == 2) {
            String prefix = departementString.substring(0, 2);
            if (prefix.equals("29") || prefix.equals("22") || prefix.equals("56") || prefix.equals("35")) {
                this.departement = departement;
            } else {
                throw new IllegalArgumentException("The department code must be 29, 22, 56, or 35.");
            }
        } else {
            throw new IllegalArgumentException("The department code must be a two-digit number.");
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
        int totalProprietes = getNbMaison() + getNbAppart();
        if (totalProprietes == 0) {
            return 0;
        } else {
            return (getPrixMoyen() + getPrixM2Moyen()) / totalProprietes;
        }
    }
    
    
    public boolean hasAirports() {
        return aeroports != null && !aeroports.isEmpty();
    }
    
    public boolean hasNeighboringCommunes() {
        return communesVoisines != null && !communesVoisines.isEmpty();
    }

    public boolean hasTrainStation() {
        return gares != null && !gares.isEmpty();
    }    
    


    /**
    * Determines if the commune is the most significant among its neighbors based on various factors
    * including the total number of properties, total budget, and population.
    *
    * @return True if the commune is the most significant among its neighbors, otherwise false.
    */
    public boolean isMostImportant() {
        // Check if the commune has neighboring communes
        if (!hasNeighboringCommunes()) {
            return false;
        }

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

        // Check if the current commune has the maximum values
        return (getProprieteTotal() >= maxProperties) && (getBudgetTotal() >= maxBudget) && (getPopulation() >= maxPopulation);
    }


    /**
    * Identifies the commune with the highest average price per property among its neighbors.
    *
    * @return The name of the commune with the highest average price per property among its neighbors,
    *         or null if there are no neighboring communes.
    */
    public String highestNeighboursPrice() {
        // Check if the commune has neighboring communes
        if (!hasNeighboringCommunes()) {
            return null;
        }

        String highestPriceCommune = null;
        double highestAveragePrice = 0;

        // Iterate through neighboring communes to find the one with the highest average price per property
        for (Commune neighbor : communesVoisines) {
            double neighborAveragePrice = neighbor.prixMoyenParPropriete();
            if (neighborAveragePrice > highestAveragePrice) {
                highestAveragePrice = neighborAveragePrice;
                highestPriceCommune = neighbor.getNomCommune();
            }
        }

        return highestPriceCommune;
    }


    /**
    * Calculates the ratio of cultural expenses per inhabitant.
    *
    * @return The ratio of cultural expenses per inhabitant.
    * @throws ArithmeticException If the population is zero.
    */
    public double culturalExpensesPerInhabitant() {
        // Check if population is not zero
        if (getPopulation() == 0) {
            throw new ArithmeticException("Population cannot be zero.");
        }

        return getDepCulturellesTotales() / getPopulation();
    }





}