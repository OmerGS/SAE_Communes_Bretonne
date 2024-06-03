package dao;

import data.Commune;
import data.Departement;
import data.exceptions.InvalidCommuneIdException;
import data.exceptions.InvalidCommuneNameException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommuneService {
    
    private DepartementService departementService;

    public CommuneService() {
        this.departementService = new DepartementService();
    }

    // Méthode pour récupérer toutes les communes depuis la base de données
    public List<Commune> getAllCommunes() throws SQLException {
        Map<Integer, Commune> communes = new HashMap<>();

        // Etape 1 creer une hashmap qui prend pour paramètre l'id du department et le department
        List<Departement> departementList = departementService.getAllDepartement();
        Map<Integer, Departement> departementMap = new HashMap<>();
        for (Departement departement : departementList) {
            departementMap.put(departement.getIdDep(), departement);
        }

        // Étape 2 : Récupérer toutes les communes et leurs données
        String query = "SELECT c.idCommune, c.nomCommune, c.leDepartement, " +
               "da.nbMaison, da.nbAppart, da.prixMoyen, da.prixM2Moyen, da.SurfaceMoy, " +
               "da.depensesCulturellesTotales, da.budgetTotal, da.population " +
               "FROM Commune c " +
               "JOIN DonneesAnnuelles da ON c.idCommune = da.laCommune " +
               "WHERE da.lAnnee = (SELECT MAX(lAnnee) FROM DonneesAnnuelles)";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int idCommune = resultSet.getInt("idCommune");
                String nomCommune = resultSet.getString("nomCommune");
                int leDepartementId = resultSet.getInt("leDepartement");

                // Get the corresponding department
                Departement departement = departementMap.get(leDepartementId);

                // Récupération des données annuelles
                int nbMaison = resultSet.getInt("nbMaison");
                int nbAppart = resultSet.getInt("nbAppart");
                double prixMoyen = resultSet.getDouble("prixMoyen");
                double prixM2Moyen = resultSet.getDouble("prixM2Moyen");
                double surfaceMoyenne = resultSet.getDouble("SurfaceMoy");
                double depensesCulturellesTotales = resultSet.getDouble("depensesCulturellesTotales");
                double budgetTotal = resultSet.getDouble("budgetTotal");
                int population = resultSet.getInt("population");

                try {
                    Commune commune = new Commune(idCommune, nomCommune, nbMaison, nbAppart, prixMoyen,
                                                  prixM2Moyen, surfaceMoyenne, depensesCulturellesTotales, budgetTotal, population, departement);
                    communes.put(idCommune, commune);
                } catch (InvalidCommuneIdException | InvalidCommuneNameException e) {
                    e.printStackTrace();
                }
            }
        }

        // Étape 3 : Récupérer toutes les relations de voisinage
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
            Commune commune = communes.get(voisinage[0]);
            Commune communeVoisine = communes.get(voisinage[1]);

            if (commune != null && communeVoisine != null) {
                commune.addVoisine(communeVoisine);
            }
        }

        return new ArrayList<>(communes.values());
    }
}
