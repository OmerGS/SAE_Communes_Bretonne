package dao;

import data.Annee;
import data.Commune;
import data.Departement;
import data.Gare;
import data.Utilisateur;
import data.exceptions.InvalidCommuneIdException;
import data.exceptions.InvalidCommuneNameException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CommuneService {

    private DepartementService departementService;
    private List<Departement> listeDepartement;
    
    private AnneeService anneeService;
    private List<Annee> listeAnnee;

    private GareService gareService;
    private List<Gare> listeGare;
    
    public CommuneService() {
        this.departementService = new DepartementService();
        try {
            this.listeDepartement = departementService.getAllDepartement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.anneeService = new AnneeService();
        try {
            this.listeAnnee = anneeService.getAllAnnee();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.gareService = new GareService();
        try {
            this.listeGare = gareService.getAllGares();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    private Departement findDepartementById(int departementId) {
        for (Departement departement : listeDepartement) {
            if (departement.getIdDep() == departementId) {
                return departement;
            }
        }
        return null;
    }

    private Annee findAnnneByYear(int lAnnee) {
        for (Annee annee : listeAnnee) {
            if (annee.getAnnee() == lAnnee) {
                return annee;
            }
        }
        return null;
    }

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
                    e.printStackTrace();
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


    public void updateCommuneEtDonneesAnnuelles(int idCommune, String newNomCommune, int newDepartementId, int newNbMaison, int newNbAppart, double newPrixMoyen, double newPrixM2Moyen, double newSurfaceMoy, double newDepensesCulturellesTotales, double newBudgetTotal, int newPopulation, int newAnnee) {
        try (Connection connexion = ConnectionManager.getConnection()) {
    
            String updateCommuneSQL = "UPDATE Commune SET nomCommune = ?, leDepartement = ? WHERE idCommune = ?";
            String updateDonneesAnnuellesSQL = "UPDATE DonneesAnnuelles SET nbMaison = ?, nbAppart = ?, prixMoyen = ?, prixM2Moyen = ?, SurfaceMoy = ?, depensesCulturellesTotales = ?, budgetTotal = ?, population = ?, lAnnee = ? WHERE laCommune = ?";
    
            try (PreparedStatement communeStatement = connexion.prepareStatement(updateCommuneSQL);
                 PreparedStatement donneesStatement = connexion.prepareStatement(updateDonneesAnnuellesSQL)) {
    
                // Mettre à jour les données de la commune
                communeStatement.setString(1, newNomCommune);
                communeStatement.setInt(2, newDepartementId);
                communeStatement.setInt(3, idCommune);
                communeStatement.executeUpdate();
    
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
                donneesStatement.executeUpdate();
    
                System.out.println("Commune et données annuelles mises à jour avec succès dans la base de données.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    


    public List<Gare> getListeGare(){
        return(this.listeGare);
    }

    public List<Annee> getListeAnnee(){
        return(this.listeAnnee);
    }
}