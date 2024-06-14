package dao;

import data.Departement;
import data.exceptions.InvalidCommuneIdException;
import data.exceptions.InvalidCommuneNameException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartementService {

    public DepartementService(){

    }

    // Méthode pour récupérer toutes les communes depuis la base de données
    public List<Departement> getAllDepartement() throws SQLException {
        List<Departement> departements = new ArrayList<>();
        
        String query = "SELECT * FROM Departement";


        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int idDep = resultSet.getInt("idDep");
                String nomDep = resultSet.getString("nomDep");
                double investissementCulturel2019 = resultSet.getDouble("investissementCulturel2019");

                try {
                    Departement departement = new Departement(idDep, nomDep, investissementCulturel2019);
                    departements.add(departement);
                } catch (InvalidCommuneIdException | InvalidCommuneNameException e) {
                    e.printStackTrace();
                }
            }
        }
        return departements;
    }
}