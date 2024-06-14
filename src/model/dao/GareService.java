package dao;

import data.Gare;
import data.Commune;
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

public class GareService {

    private CommuneService communeService;

    public GareService() {
        this.communeService = new CommuneService();
    }

    public List<Gare> getAllGares() throws SQLException {
        List<Gare> gares = new ArrayList<>();

        // Fetch all communes
        List<Commune> communeList = communeService.getAllCommunes();
        Map<Integer, Commune> communeMap = new HashMap<>();
        for (Commune commune : communeList) {
            communeMap.put(commune.getIdCommune(), commune);
        }

        String query = "SELECT g.codeGare, g.nomGare, g.estFret, g.estVoyageur, g.laCommune " +
                       "FROM Gare g " +
                       "JOIN Commune c ON g.laCommune = c.idCommune";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int codeGare = resultSet.getInt("codeGare");
                String nomGare = resultSet.getString("nomGare");
                boolean estFret = resultSet.getBoolean("estFret");
                boolean estVoyageur = resultSet.getBoolean("estVoyageur");
                int laCommuneId = resultSet.getInt("laCommune");

                // Get the corresponding commune
                Commune commune = communeMap.get(laCommuneId);

                try {
                    Gare gare = new Gare(codeGare, nomGare, estFret, estVoyageur, commune);
                    gares.add(gare);
                } catch (InvalidCommuneIdException | InvalidCommuneNameException e) {
                    e.printStackTrace();
                }
            }
        }
        return gares;
    }
}