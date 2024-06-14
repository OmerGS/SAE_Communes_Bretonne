package dao;

import data.Commune;
import data.Departement;
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

    public CommuneService() {
        this.departementService = new DepartementService();
        try {
            this.listeDepartement = departementService.getAllDepartement();
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

                try {
                    Commune commune = new Commune(lAnnee, idCommune, nomCommune, nbMaison, nbAppart, prixMoyen,
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
}
