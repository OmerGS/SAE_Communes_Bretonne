package data;

import java.util.ArrayList;



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
    


    public Commune(int idCommune, String nomCommune, int nbMaison, int nbAppart, double prixMoyen, double prixM2Moyen, double surfaceMoy, double depCulturellesTotales, double budgetTotal, int population, Departement departement) {
        this.communesVoisines = new ArrayList<Commune>();


        //idCommune
        if (isValidIdCommune(idCommune)) {
            this.idCommune = idCommune;
        } else {
            throw new RuntimeException("Numéro de commune invalide : " + idCommune);
        }

        //nomCommune
        if (nomCommune != null && !nomCommune.trim().isEmpty()) {
            this.nomCommune = nomCommune;
        } else {
            throw new RuntimeException("Nom de commune invalide : " + nomCommune);
        }

        if(departement != null && (departement.getIdDep() == 29 || departement.getIdDep() == 56 || departement.getIdDep() == 35 || departement.getIdDep() == 22)){
            this.departement= departement;
        } else {
            throw new RuntimeException("Département invalide : " + departement.getNomDep());
        }


        this.nbMaison = validateNonNegativeValueInt(nbMaison, "Nombre de maisons");
        this.nbAppart = validateNonNegativeValueInt(nbAppart, "Nombre d'appartement");
        this.prixMoyen = validateNonNegativeValueDouble(prixMoyen, "Prix moyen");
        this.prixM2Moyen = validateNonNegativeValueDouble(prixM2Moyen, "Prix m2 moyen");
        this.surfaceMoy = validateNonNegativeValueDouble(surfaceMoy, "Surface moyenne");
        this.depCulturellesTotales = validateNonNegativeValueDouble(depCulturellesTotales, "Dépenses culturelles totales");
        this.budgetTotal = validateNonNegativeValueDouble(budgetTotal, "Budget total");
        this.population = validateNonNegativeValueInt(population, "Population");


        
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
    private double validateNonNegativeValueDouble(double value, String fieldName) {
        double ret = -1;
        if (value >= 0 || value == -1) {
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
    private int validateNonNegativeValueInt(int value, String fieldName) {
        int ret = -1;
        if (value >= 0 || value == -1) {
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
    this.nbMaison = validateNonNegativeValueInt(nbMaison, "Number of houses");
}

/**
 * Sets the number of apartments in the commune.
 *
 * @param nbAppart The number of apartments.
 * @throws InvalidValueException If the number of apartments is negative.
 */
public void setNbAppart(int nbAppart) {
    this.nbAppart = validateNonNegativeValueInt(nbAppart, "Number of apartments");
}

/**
 * Sets the average price of properties in the commune.
 *
 * @param prixMoyen The average price.
 * @throws InvalidValueException If the average price is negative.
 */
public void setPrixMoyen(double prixMoyen) {
    this.prixMoyen = validateNonNegativeValueDouble(prixMoyen, "Average price");
}

/**
 * Sets the average price per square meter in the commune.
 *
 * @param prixM2Moyen The average price per square meter.
 * @throws InvalidValueException If the average price per square meter is negative.
 */
public void setPrixM2Moyen(double prixM2Moyen) {
    this.prixM2Moyen = validateNonNegativeValueDouble(prixM2Moyen, "Average price per square meter");
}

/**
 * Sets the average surface area in the commune.
 *
 * @param surfaceMoy The average surface area.
 * @throws InvalidValueException If the average surface area is negative.
 */
public void setSurfaceMoy(double surfaceMoy) {
    this.surfaceMoy = validateNonNegativeValueDouble(surfaceMoy, "Average surface area");
}

/**
 * Sets the total cultural expenses in the commune.
 *
 * @param depCulturellesTotales The total cultural expenses.
 * @throws InvalidValueException If the total cultural expenses are negative.
 */
public void setDepCulturellesTotales(double depCulturellesTotales) {
    this.depCulturellesTotales = validateNonNegativeValueDouble(depCulturellesTotales, "Total cultural expenses");
}

/**
 * Sets the total budget of the commune.
 *
 * @param budgetTotal The total budget.
 * @throws InvalidValueException If the total budget is negative.
 */
public void setBudgetTotal(double budgetTotal) {
    this.budgetTotal = validateNonNegativeValueDouble(budgetTotal, "Total budget");
}

/**
 * Sets the population of the commune.
 *
 * @param population The population.
 * @throws InvalidValueException If the population is negative.
 */
public void setPopulation(int population) {
    this.population = validateNonNegativeValueInt(population, "Population");
}

/**
 * Sets the department to which the commune belongs.
 *
 * @param departement The department.
 * @throws IllegalArgumentException If the provided department code is invalid.
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
        double ret;
        int totalProprietes = getNbMaison() + getNbAppart();
        if (totalProprietes == 0) {
            ret = 0;
        } else {
            ret = (getPrixMoyen() + getPrixM2Moyen()) / totalProprietes;
        }
        return ret;
    }
    
    
    
    public boolean hasNeighboringCommunes() {
        return communesVoisines != null && !communesVoisines.isEmpty();
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
        if (!hasNeighboringCommunes()) {
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
        if (!hasNeighboringCommunes()) {
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