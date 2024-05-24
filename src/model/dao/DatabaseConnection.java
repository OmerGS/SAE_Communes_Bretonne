package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseConnection {
    public static void main(String[] args) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Informations de connexion à la base de données
        String url = props.getProperty("db.url");
        String utilisateur = props.getProperty("db.user"); 
        String motDePasse = props.getProperty("db.password");
        
        // Requête SQL
        String requeteSQL = "SELECT * FROM Aeroport";
        
        try {
            // Connexion à la base de données
            Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
            
            // Création de la déclaration
            Statement declaration = connexion.createStatement();
            
            // Exécution de la requête SQL
            ResultSet resultat = declaration.executeQuery(requeteSQL);
            
            // Parcours et affichage des résultats
            while (resultat.next()) {
                String nom = resultat.getString("nom");
                String adresse = resultat.getString("adresse");
                int leDepartement = resultat.getInt("leDepartement");
                
                System.out.println("Nom de l'aéroport : " + nom);
                System.out.println("Adresse : " + adresse);
                System.out.println("Département : " + leDepartement);
                System.out.println("------------------------------------------");
            }
            
            // Fermeture des ressources
            resultat.close();
            declaration.close();
            connexion.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}