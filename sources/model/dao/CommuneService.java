package dao;

import data.Annee;
import data.Commune;
import data.Departement;
import data.Gare;
import data.exceptions.InvalidCommuneIdException;
import data.exceptions.InvalidCommuneNameException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Service class for handling operations related to Commune objects and their annual data in the database.
 */
public class CommuneService {

    /**
    * Instance of DepartementService allowing to make operation on the database 
    */
    private DepartementService departementService;

    /**
    * List containing all of the departement 
    */
    private List<Departement> listeDepartement;
    
    /**
    * Instance of AnneeService allowing to make operation on the database 
    */
    private AnneeService anneeService;

    /**
    * List containing all of the years
    */
    private List<Annee> listeAnnee;

    /**
    * Instance of GareService allowingto make operation on the database. 
    */
    private GareService gareService;

    /**
    * List containing all of the TrainStation 
    */
    private List<Gare> listeGare;

    /**
    * Constructs a new CommuneService instance. Initializes the DepartementService,
    * AnneeService, and GareService to populate lists of departments, years, and train stations.
    * Any SQLException encountered during data retrieval is printed to standard error output.
    */
    public CommuneService() {
        //Recover all of Departement from the database
        this.departementService = new DepartementService();
        try {
            this.listeDepartement = departementService.getAllDepartement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Recover all of the years from the database
        this.anneeService = new AnneeService();
        try {
            this.listeAnnee = anneeService.getAllAnnee();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //recover all of the TrainStation from the database
        this.gareService = new GareService();
        try {
            this.listeGare = gareService.getAllGares();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
    * Finds and returns the Departement object from the list of departments (listeDepartement)
    * that matches the given departementId.
    *
    * @param departementId The ID of the department to find.
    * @return The Departement object with the specified ID, or null if not found.
    */
    private Departement findDepartementById(int departementId) {
        for (Departement departement : listeDepartement) {
            if (departement.getIdDep() == departementId) {
                return departement;
            }
        }
        return null;
    }

    /**
    * Finds and returns the Annee object from the list of years (listeAnnee) that matches
    * the given year (lAnnee).
    *
    * @param lAnnee The year to find.
    * @return The Annee object with the specified year, or null if not found.
    */
    private Annee findAnnneByYear(int lAnnee) {
        for (Annee annee : listeAnnee) {
            if (annee.getAnnee() == lAnnee) {
                return annee;
            }
        }
        return null;
    }

    /**
    * Finds and returns the Gare object from the list of train stations (listeGare) that matches
    * the given commune ID.
    *
    * @param communeId The ID of the commune for which to find the train station.
    * @return The Gare object associated with the specified commune ID, or null if not found.
    */
    private Gare findGareByCommuneId(int communeId){
        for(Gare gare : listeGare){
            if(gare.getCommune() == communeId){
                return gare;
            }
        }
        return null;
    }

    /**
     * Get all of the commune which is in the database.
     * @return List<Commune>.
     * @throws SQLException
     */
    public List<Commune> getAllCommunes() throws SQLException {
        Map<Integer, List<Commune>> communesByYear = new HashMap<>();
        Map<Integer, Commune> communesById = new HashMap<>();
        List<Commune> allCommunes = new ArrayList<>();

        String query = "SELECT c.idCommune, c.nomCommune, c.leDepartement, " +
                "da.nbMaison, da.nbAppart, da.prixMoyen, da.prixM2Moyen, da.SurfaceMoy, " +
                "da.depensesCulturellesTotales, da.budgetTotal, da.population, " +
                "d.nomDep, da.lAnnee " +
                "FROM Commune c " +
                "JOIN DonneesAnnuelles da ON c.idCommune = da.laCommune " +
                "JOIN Departement d ON c.leDepartement = d.idDep";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int idCommune = resultSet.getInt("idCommune");
                String nomCommune = resultSet.getString("nomCommune");
                int leDepartement = resultSet.getInt("leDepartement");

                // Find the corresponding Departement object
                Departement departementDeLaCommune = findDepartementById(leDepartement);
                Gare gare = findGareByCommuneId(idCommune);

                // Récupération des données annuelles
                int nbMaison = resultSet.getInt("nbMaison");
                int nbAppart = resultSet.getInt("nbAppart");
                double prixMoyen = resultSet.getDouble("prixMoyen");
                double prixM2Moyen = resultSet.getDouble("prixM2Moyen");
                double surfaceMoyenne = resultSet.getDouble("SurfaceMoy");
                double depensesCulturellesTotales = resultSet.getDouble("depensesCulturellesTotales");
                double budgetTotal = resultSet.getDouble("budgetTotal");
                int population = resultSet.getInt("population");
                int lAnnee = resultSet.getInt("lAnnee");
                
                Annee anneeCommune = findAnnneByYear(lAnnee);

                try {
                    Commune commune = new Commune(gare, anneeCommune, idCommune, nomCommune, nbMaison, nbAppart, prixMoyen,
                            prixM2Moyen, surfaceMoyenne, depensesCulturellesTotales, budgetTotal, population, departementDeLaCommune);

                    // Ajouter la commune à la liste correspondante par année
                    List<Commune> communesForYear = communesByYear.getOrDefault(lAnnee, new ArrayList<>());
                    communesForYear.add(commune);
                    communesByYear.put(lAnnee, communesForYear);

                    // Ajouter la commune à la map des communes par ID
                    communesById.put(idCommune, commune);

                    // Ajouter la commune à la liste globale
                    allCommunes.add(commune);
                } catch (InvalidCommuneIdException | InvalidCommuneNameException e) {
                }
            }
        }

        String voisinageQuery = "SELECT commune, communeVoisine FROM Voisinage";
        List<int[]> voisinages = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(voisinageQuery);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int idCommune = resultSet.getInt("commune");
                int idCommuneVoisine = resultSet.getInt("communeVoisine");
                voisinages.add(new int[]{idCommune, idCommuneVoisine});
            }
        }

        for (int[] voisinage : voisinages) {
            Commune commune = communesById.get(voisinage[0]);
            Commune communeVoisine = communesById.get(voisinage[1]);

            if (commune != null && communeVoisine != null) {
                // Add the neighbor to all instances of the commune, regardless of the year
                for (Commune c : allCommunes) {
                    if (c.getIdCommune() == commune.getIdCommune()) {
                        c.addVoisine(communeVoisine);
                    }
                }
            }
        }

        return allCommunes;
    }

    /**
     * Return the path of commune for go to commune start to commune end. 
     * @param startId The commune of start
     * @param endId The commune of end
     * @param allCommunes All of the commune
     * @return List<Commune> contains path.
     * @throws SQLException
     */
    public List<Commune> cheminEntreCommune(int startId, int endId, List<Commune> allCommunes) throws SQLException {
        Map<Integer, Commune> communeMap = new HashMap<>();
        for (Commune commune : allCommunes) {
            communeMap.put(commune.getIdCommune(), commune);
        }

        Queue<Commune> queue = new LinkedList<>();
        Set<Commune> visited = new HashSet<>();
        Map<Commune, Commune> previous = new HashMap<>();

        Commune startCommune = communeMap.get(startId);
        Commune endCommune = communeMap.get(endId);

        queue.add(startCommune);
        visited.add(startCommune);

        while (!queue.isEmpty()) {
            Commune current = queue.poll();
            if (current.equals(endCommune)) {
                List<Commune> path = new LinkedList<>();
                for (Commune communeTrouve = endCommune; communeTrouve != null; communeTrouve = previous.get(communeTrouve)) {
                    path.add(0, communeTrouve);
                }
                return path;
            }
            for (Commune neighbor : current.getCommunesVoisines()) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    previous.put(neighbor, current);
                }
            }
        }
        return Collections.emptyList();
    }

    /**
     * Return a Commune for a String which correspond of commune name.
     * @param communeName The String of the name of the commune
     * @return A commune if the commune with the String name exists
     * @throws SQLException 
     */
    public Commune getCommuneByName(String communeName, List<Commune> allCommunes) throws SQLException {
        for (Commune commune : allCommunes) {
            if (commune.getNomCommune().equalsIgnoreCase(communeName)) {
                return commune;
            }
        }
        return null;
    }


    /**
    * Inserts a Commune and its annual data into the database.
    * This method performs two SQL insert operations:
    * 1. Inserts the Commune details into the 'Commune' table.
    * 2. Inserts the annual data of the Commune into the 'DonneesAnnuelles' table.
    *
    * @param commune The Commune object containing all necessary details to be inserted.
    * @throws SQLException If a database access error occurs.
    */
    public void insertCommuneEtDonneesAnnuelles(Commune commune) {
        try (Connection connexion = ConnectionManager.getConnection()) {
    
            String insertCommuneSQL = "INSERT INTO Commune (idCommune, nomCommune, leDepartement) VALUES (?, ?, ?)";
            String insertDonneesAnnuellesSQL = "INSERT INTO DonneesAnnuelles (lAnnee, laCommune, nbMaison, nbAppart, prixMoyen, prixM2Moyen, SurfaceMoy, depensesCulturellesTotales, budgetTotal, population) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
            try (PreparedStatement communeStatement = connexion.prepareStatement(insertCommuneSQL);
                 PreparedStatement donneesStatement = connexion.prepareStatement(insertDonneesAnnuellesSQL)) {
    
                // Insérer les données de la commune
                communeStatement.setInt(1, commune.getIdCommune());
                communeStatement.setString(2, commune.getNomCommune());
                communeStatement.setInt(3, commune.getDepartement().getIdDep());
                communeStatement.executeUpdate();
    
                // Insérer les données annuelles
                donneesStatement.setInt(1, commune.getAnnee().getAnnee());
                donneesStatement.setInt(2, commune.getIdCommune());
                donneesStatement.setInt(3, commune.getNbMaison());
                donneesStatement.setInt(4, commune.getNbAppart());
                donneesStatement.setDouble(5, commune.getPrixMoyen());
                donneesStatement.setDouble(6, commune.getPrixM2Moyen());
                donneesStatement.setDouble(7, commune.getSurfaceMoy());
                donneesStatement.setDouble(8, commune.getDepCulturellesTotales());
                donneesStatement.setDouble(9, commune.getBudgetTotal());
                donneesStatement.setInt(10, commune.getPopulation());
                donneesStatement.executeUpdate();
    
                System.out.println("Commune et données annuelles enregistrées avec succès dans la base de données.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
    * Inserts annual data of a Commune into the 'DonneesAnnuelles' table for a new year.
    *
    * @param commune The Commune object containing the annual data to be inserted.
    * @throws SQLException If a database access error occurs.
    */
    public void insertCommuneEtDonneesAnnuellesNewYear(Commune commune) {
        try (Connection connexion = ConnectionManager.getConnection()) {
    
            String insertDonneesAnnuellesSQL = "INSERT INTO DonneesAnnuelles (lAnnee, laCommune, nbMaison, nbAppart, prixMoyen, prixM2Moyen, SurfaceMoy, depensesCulturellesTotales, budgetTotal, population) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
            try (PreparedStatement donneesStatement = connexion.prepareStatement(insertDonneesAnnuellesSQL)) {
                // Insérer les données annuelles
                donneesStatement.setInt(1, commune.getAnnee().getAnnee());
                donneesStatement.setInt(2, commune.getIdCommune());
                donneesStatement.setInt(3, commune.getNbMaison());
                donneesStatement.setInt(4, commune.getNbAppart());
                donneesStatement.setDouble(5, commune.getPrixMoyen());
                donneesStatement.setDouble(6, commune.getPrixM2Moyen());
                donneesStatement.setDouble(7, commune.getSurfaceMoy());
                donneesStatement.setDouble(8, commune.getDepCulturellesTotales());
                donneesStatement.setDouble(9, commune.getBudgetTotal());
                donneesStatement.setInt(10, commune.getPopulation());
                donneesStatement.executeUpdate();
    
                System.out.println("Commune et données annuelles enregistrées avec succès dans la base de données.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
    * Deletes a Commune and its annual data from the database.
    *
    * @param idCommune The ID of the Commune to delete along with its annual data.
    * @throws SQLException If a database access error occurs.
    */
    public void dropCommuneEtDonneesAnnuelles(int idCommune) {
        try (Connection connexion = ConnectionManager.getConnection()) {
    
            String deleteCommuneSQL = "DELETE FROM Commune WHERE idCommune = ?";
            String deleteDonneesAnnuellesSQL = "DELETE FROM DonneesAnnuelles WHERE laCommune = ?";
    
            try (PreparedStatement communeStatement = connexion.prepareStatement(deleteCommuneSQL);
                 PreparedStatement donneesStatement = connexion.prepareStatement(deleteDonneesAnnuellesSQL)) {
    
                // Supprimer les données annuelles
                donneesStatement.setInt(1, idCommune);
                donneesStatement.executeUpdate();
    
                // Supprimer la commune
                communeStatement.setInt(1, idCommune);
                communeStatement.executeUpdate();
    
                System.out.println("Commune et données annuelles supprimées avec succès de la base de données.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
    * Updates the annual data of a Commune in the database.
    *
    * @param idCommune                       The ID of the Commune whose data needs to be updated.
    * @param newNbMaison                     The new number of houses for the Commune.
    * @param newNbAppart                     The new number of apartments for the Commune.
    * @param newPrixMoyen                    The new average price for the Commune.
    * @param newPrixM2Moyen                  The new average price per square meter for the Commune.
    * @param newSurfaceMoy                   The new average surface area for the Commune.
    * @param newDepensesCulturellesTotales   The new total cultural expenses for the Commune.
    * @param newBudgetTotal                  The new total budget for the Commune.
    * @param newPopulation                   The new population count for the Commune.
    * @param newAnnee                        The new year for which the data is updated.
    * @throws SQLException                  If a database access error occurs.
    */
    public void updateCommuneEtDonneesAnnuelles(int idCommune, int newNbMaison, int newNbAppart, double newPrixMoyen, double newPrixM2Moyen, double newSurfaceMoy, double newDepensesCulturellesTotales, double newBudgetTotal, int newPopulation, int newAnnee) {
        try (Connection connexion = ConnectionManager.getConnection()) {
    
            String updateDonneesAnnuellesSQL = "UPDATE DonneesAnnuelles SET nbMaison = ?, nbAppart = ?, prixMoyen = ?, prixM2Moyen = ?, SurfaceMoy = ?, depensesCulturellesTotales = ?, budgetTotal = ?, population = ?, lAnnee = ? WHERE laCommune = ? AND lAnnee = ?";
    
                PreparedStatement donneesStatement = connexion.prepareStatement(updateDonneesAnnuellesSQL); {
    
                    // Mettre à jour les données annuelles
                    donneesStatement.setInt(1, newNbMaison);
                    donneesStatement.setInt(2, newNbAppart);
                    donneesStatement.setDouble(3, newPrixMoyen);
                    donneesStatement.setDouble(4, newPrixM2Moyen);
                    donneesStatement.setDouble(5, newSurfaceMoy);
                    donneesStatement.setDouble(6, newDepensesCulturellesTotales);
                    donneesStatement.setDouble(7, newBudgetTotal);
                    donneesStatement.setInt(8, newPopulation);
                    donneesStatement.setInt(9, newAnnee);
                    donneesStatement.setInt(10, idCommune);
                    donneesStatement.setInt(11, newAnnee);
                    donneesStatement.executeUpdate();
        
                    System.out.println("Commune et données annuelles mises à jour avec succès dans la base de données.");
                 }
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }

    /**
    * Deletes all Communes and associated annual data and neighborhood information from the database.
    * This operation is irreversible and removes all records from the 'Commune', 'DonneesAnnuelles',
    * and 'Voisinage' tables.
    *
    * @throws SQLException If a database access error occurs.
    */
    public void dropAllCommunes() {
        try (Connection connexion = ConnectionManager.getConnection()) {
            String requeteCommuneSQL = "DELETE FROM Commune";
            String requeteDonneeAnneeSQL = "DELETE FROM DonneesAnnuelles";
            String requeteVoisinSQL = "DELETE FROM Voisinage";
            try (PreparedStatement preparedStatementCommune = connexion.prepareStatement(requeteCommuneSQL); 
            PreparedStatement preparedStatementDonneeAnnee = connexion.prepareStatement(requeteDonneeAnneeSQL);
            PreparedStatement preparedStatementVoisin = connexion.prepareStatement(requeteVoisinSQL)) {
                preparedStatementCommune.executeUpdate();
                preparedStatementDonneeAnnee.executeUpdate();
                preparedStatementVoisin.executeUpdate();
                System.out.println("Toutes les communes ont été supprimées de la base de données.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    

    /**
    * Retrieves the list of all train stations (Gares).
    *
    * @return The list of train stations (Gares).
    */
    public List<Gare> getListeGare(){
        return(this.listeGare);
    }

    /**
    * Retrieves the list of all years (Annees).
    *
    * @return The list of years (Annees).
    */
    public List<Annee> getListeAnnee(){
        return(this.listeAnnee);
    }
}