package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class printUserDatabase {
    public static void main(String[] args) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("../properties/config.properties")) {
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
        String requeteSQL = "SELECT * FROM Utilisateur";
        
        try {
            // Connexion à la base de données
            Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
            
            // Création de la déclaration
            Statement declaration = connexion.createStatement();
            
            // Exécution de la requête SQL
            ResultSet resultat = declaration.executeQuery(requeteSQL);
            
            // Parcours et affichage des résultats
            while (resultat.next()) {
                int id = resultat.getInt("id");
                String nom = resultat.getString("nom");
                String prenom = resultat.getString("prenom");
                String email = resultat.getString("email");
                String mot2Passe = resultat.getString("motDePasse");
                int admin = resultat.getInt("isAdmin");

                System.out.println("ID : " + id);
                System.out.println("Nom : " + nom);
                System.out.println("Prenom : " + prenom);
                System.out.println("Email : " + email);
                System.out.println("mot2Passe : " + mot2Passe);
                System.out.println("Admin : " + admin);
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