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
import java.util.List;

public class CommuneService {

    // Méthode pour récupérer toutes les communes depuis la base de données
    public List<Commune> getAllCommunes() throws SQLException {
        List<Commune> communes = new ArrayList<>();
        
        String query = "SELECT c.idCommune, c.nomCommune, c.leDepartement, " +
               "da.nbMaison, da.nbAppart, da.prixMoyen, da.prixM2Moyen, da.SurfaceMoy, " +
               "da.depensesCulturellesTotales, da.budgetTotal, da.population, " +
               "d.nomDep " +
               "FROM Commune c " +
               "JOIN DonneesAnnuelles da ON c.idCommune = da.laCommune " +
               "JOIN Departement d ON c.leDepartement = d.idDep " +
               "WHERE da.lAnnee = ( " + 
               "SELECT MAX(lAnnee) FROM DonneesAnnuelles)";


        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int idCommune = resultSet.getInt("idCommune");
                String nomCommune = resultSet.getString("nomCommune");
                int leDepartement = resultSet.getInt("leDepartement");

                // Récupération des données du département
                String nomDepartement = resultSet.getString("nomDep");
                Departement departement = new Departement(leDepartement, nomDepartement);

                // Récupération des données annuelles
                int nbMaison = resultSet.getInt("nbMaison");
                int nbAppart = resultSet.getInt("nbAppart");
                float prixMoyen = resultSet.getFloat("prixMoyen");
                float prixM2Moyen = resultSet.getFloat("prixM2Moyen");
                float surfaceMoyenne = resultSet.getFloat("SurfaceMoy");
                float depensesCulturellesTotales = resultSet.getFloat("depensesCulturellesTotales");
                float budgetTotal = resultSet.getFloat("budgetTotal");
                float population = resultSet.getFloat("population");

                try {
                    Commune commune = new Commune(idCommune, nomCommune, nbMaison, nbAppart, prixMoyen, 
                                                  prixM2Moyen, surfaceMoyenne, depensesCulturellesTotales, budgetTotal, population, departement);
                    communes.add(commune);
                } catch (InvalidCommuneIdException | InvalidCommuneNameException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return communes;
    }
}