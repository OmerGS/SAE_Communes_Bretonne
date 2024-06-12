package dao;

import data.Annee;
import data.exceptions.InvalidCommuneIdException;
import data.exceptions.InvalidCommuneNameException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnneeService {

    public List<Annee> getAllAnnee() throws SQLException {
        List<Annee> annees = new ArrayList<>();
        
        String query = "SELECT * FROM Annee";


        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int annee = resultSet.getInt("annee");
                double tauxInflation = resultSet.getDouble("tauxInflation");


                try {
                    Annee lannee = new Annee(annee, tauxInflation);
                    annees.add(lannee);
                } catch (InvalidCommuneIdException | InvalidCommuneNameException e) {
                    e.printStackTrace();
                }
            }
        }
        return annees;
    }
}