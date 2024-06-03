package dao;

import data.Aeroport;
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

public class AeroportService {

    private DepartementService departementService;

    public AeroportService() {
        this.departementService = new DepartementService();
    }

    public List<Aeroport> getAllAeroport() throws SQLException {
        List<Aeroport> aeroports = new ArrayList<>();

        // Fetch all departments
        List<Departement> departementList = departementService.getAllDepartement();
        Map<Integer, Departement> departementMap = new HashMap<>();
        for (Departement departement : departementList) {
            departementMap.put(departement.getIdDep(), departement);
        }

        String query = "SELECT a.nom, a.adresse, a.leDepartement " +
                       "FROM Aeroport a " +
                       "JOIN Departement d ON a.leDepartement = d.idDep";

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String adresse = resultSet.getString("adresse");
                int leDepartementId = resultSet.getInt("leDepartement");


                // Get the corresponding department
                Departement departement = departementMap.get(leDepartementId);

                try {
                    Aeroport aeroport = new Aeroport(nom, adresse, departement);
                    aeroports.add(aeroport);
                } catch (InvalidCommuneIdException | InvalidCommuneNameException e) {
                    e.printStackTrace();
                }
            }
        }
        return aeroports;
    }
}
