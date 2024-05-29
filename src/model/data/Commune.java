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

    /**
    * Average price of house and appartment 
    */
    private float prixMoyen;

    /**
    * Average price of one square meters. 
    */
    private float prixM2Moyen;

    /**
    * Average square meters of house and appartment. 
    */
    private float surfaceMoy;

    /**
    * The amount of money spend in cultural activities. 
    */
    private float depCulturellesTotales;

    /**
    * Amount of commune budget. 
    */
    private float budgetTotal;

    /**
    * The number of person who lives in the commune. 
    */
    private float population;

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
    
    /**
    * 
    * Constructor of Commune.
    *
    * @param idCommune 
    * @param nomCommune
    * @param nbMaison
    * @param nbAppart
    * @param prixMoyen
    * @param prixM2Moyen
    * @param surfaceMoy
    * @param depCulturellesTotales
    * @param budgetTotal
    * @param population
    * @param departement
    */
    public Commune(int idCommune, String nomCommune, int nbMaison, int nbAppart, float prixMoyen, float prixM2Moyen, float surfaceMoy, float depCulturellesTotales, float budgetTotal, float population, Departement departement) {
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
    * Signature for float and fieldName
    *
    * @param value the parameters which we want to check
    * @param fieldName the fieldname of global var.
    * @return float
    * @throws IllegalArgumentException if provided value is negative
    */
    private float validateNonNegativeValue(float value, String fieldName) {
        if (value >= 0) {
            return value;
        } else {
            throw new IllegalArgumentException(fieldName + " invalide : " + value);
        }
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
        if (value >= 0) {
            return value;
        } else {
            throw new IllegalArgumentException(fieldName + " invalide : " + value);
        }
    }

    /**
    * Method which allow to check if the id of commune is valid or not.
    * 
    * @param idCommune the id of commune which we wanted to create
    * @return False if don't valid, else return true.
    */
    private boolean isValidIdCommune(int idCommune) {
        String idString = String.valueOf(idCommune);
        if (idString.length() != 5) {
            return false;
        }
        String prefix = idString.substring(0, 2);
        return prefix.equals("29") || prefix.equals("35") || prefix.equals("22") || prefix.equals("56");
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
    public float getPrixMoyen() {
        return prixMoyen;
    }

    /**
     * Returns the average price per square meter in the commune.
     *
     * @return The average price per square meter.
     */
    public float getPrixM2Moyen() {
        return prixM2Moyen;
    }

    /**
     * Returns the average surface area in the commune.
     *
     * @return The average surface area.
     */
    public float getSurfaceMoy() {
        return surfaceMoy;
    }

    /**
     * Returns the total cultural expenses in the commune.
     *
     * @return The total cultural expenses.
     */
    public float getDepCulturellesTotales() {
        return depCulturellesTotales;
    }

    /**
     * Returns the total budget of the commune.
     *
     * @return The total budget.
     */
    public float getBudgetTotal() {
        return budgetTotal;
    }

    /**
     * Returns the population of the commune.
     *
     * @return The population.
     */
    public float getPopulation() {
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
    public void setPrixMoyen(float prixMoyen) {
        this.prixMoyen = validateNonNegativeValue(prixMoyen, "Average price");
    }

    /**
     * Sets the average price per square meter in the commune.
     *
     * @param prixM2Moyen The average price per square meter.
     */
    public void setPrixM2Moyen(float prixM2Moyen) {
        this.prixM2Moyen = validateNonNegativeValue(prixM2Moyen, "Average price per square meter");
    }

    /**
     * Sets the average surface area in the commune.
     *
     * @param surfaceMoy The average surface area.
     */
    public void setSurfaceMoy(float surfaceMoy) {
        this.surfaceMoy = validateNonNegativeValue(surfaceMoy, "Average surface area");
    }

    /**
     * Sets the total cultural expenses in the commune.
     *
     * @param depCulturellesTotales The total cultural expenses.
     */
    public void setDepCulturellesTotales(float depCulturellesTotales) {
        this.depCulturellesTotales = validateNonNegativeValue(depCulturellesTotales, "Total cultural expenses");
    }

    /**
     * Sets the total budget of the commune.
     *
     * @param budgetTotal The total budget.
     */
    public void setBudgetTotal(float budgetTotal) {
        this.budgetTotal = validateNonNegativeValue(budgetTotal, "Total budget");
    }

    /**
     * Sets the population of the commune.
     *
     * @param population The population.
     */
    public void setPopulation(float population) {
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
    
    public float prixMoyenParPropriete() {
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
    * Estimates the Gross Domestic Product (GDP) of the commune based on several factors,
    * such as the number of houses and apartments, the average price of properties,
    * the total cultural expenses, and the total budget of the commune.
    * 
    * The GDP is estimated by calculating the contribution of three main sectors:
    * 1. The real estate sector, based on the number of houses and apartments as well as the average price of properties.
    * 2. The cultural sector, based on the total cultural expenses of the commune.
    * 3. The services sector, estimated at 20% of the total budget of the commune.
    * 
    * This GDP estimation is then adjusted based on the population size.
    * 
    * @return An estimation of the commune's GDP.
    */
    public double estimateGDP() {
        // Coefficient to convert cultural expenses into GDP contribution
        final double CULTURAL_EXPENSES_COEFFICIENT = 0.1;
        
        // Contribution of the real estate sector to GDP
        double realEstateContribution = (getNbMaison() * getPrixMoyen() + getNbAppart() * getPrixM2Moyen()) * 0.8;
        
        // Contribution of the cultural sector to GDP
        double culturalContribution = getDepCulturellesTotales() * CULTURAL_EXPENSES_COEFFICIENT;
        
        // Contribution of the services sector to GDP (estimated at 20% of the total budget)
        double servicesContribution = getBudgetTotal() * 0.2;
        
        // Total contribution to GDP
        double estimatedGDP = realEstateContribution + culturalContribution + servicesContribution;
        
        // Adjustment based on population size
        estimatedGDP *= (getPopulation() / 1000); // Divided by 1000 to avoid excessively large values
        
        return(estimatedGDP);
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
        float maxBudget = 0;
        float maxPopulation = 0;

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
        float highestAveragePrice = 0;

        // Iterate through neighboring communes to find the one with the highest average price per property
        for (Commune neighbor : communesVoisines) {
            float neighborAveragePrice = neighbor.prixMoyenParPropriete();
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
    public float culturalExpensesPerInhabitant() {
        // Check if population is not zero
        if (getPopulation() == 0) {
            throw new ArithmeticException("Population cannot be zero.");
        }

        return getDepCulturellesTotales() / getPopulation();
    }


    /**
    * Estimates the potential economic growth of the commune based on various factors such as
    * population growth rate, budget allocation for infrastructure development, and real estate trends.
    *
    * @return The estimated potential economic growth of the commune.
    */
    public double estimatePotentialEconomicGrowth() {
        // Population growth rate (assumed)
        double populationGrowthRate = 0.02; // 2% growth rate
        
        // Infrastructure development budget allocation factor
        double infrastructureBudgetFactor = 0.15; // 15% of total budget allocated for infrastructure development
        
        // Real estate trend factor (percentage increase in property prices over the next year)
        double realEstateTrendFactor = 0.05; // 5% increase
        
        // Calculate the projected increase in population over the next year
        double projectedPopulationIncrease = getPopulation() * populationGrowthRate;
        
        // Calculate the projected increase in infrastructure development budget
        double projectedInfrastructureBudgetIncrease = getBudgetTotal() * infrastructureBudgetFactor;
        
        // Calculate the projected increase in real estate prices
        double projectedRealEstatePriceIncrease = (getPrixMoyen() + getPrixM2Moyen()) * realEstateTrendFactor;
        
        // Calculate the total projected economic growth
        double totalProjectedGrowth = projectedPopulationIncrease + projectedInfrastructureBudgetIncrease + projectedRealEstatePriceIncrease;
        
        return totalProjectedGrowth;
    }



}