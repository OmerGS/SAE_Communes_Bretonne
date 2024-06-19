package data;



import java.sql.SQLException;
import java.util.List;

import dao.AeroportService;
import dao.AnneeService;
import dao.CommuneService;
import dao.DepartementService;
import dao.GareService;

public class MainScenario {

    public static void main(String[] args) {
        try {
            // Initialize services
            DepartementService departementService = new DepartementService();
            CommuneService communeService = new CommuneService();
            AeroportService aeroportService = new AeroportService();
            GareService gareService = new GareService();
            AnneeService anneeService = new AnneeService();
            
            Departement dep1 = null;
            Annee an1 = null;
            Gare gare1 = null;
    
            // Fetch all departments
            List<Departement> departements = departementService.getAllDepartement();
            System.out.println("All Departments:");
            for (Departement departement : departements) {
                System.out.println(departement);
                dep1 = departement;
            }
    
            // Fetch all years
            List<Annee> annees = anneeService.getAllAnnee();
            System.out.println("\nAll Years:");
            for (Annee annee : annees) {
                System.out.println(annee);
                an1 = annee;
            }
    
            // Fetch all communes
            List<Commune> communes = communeService.getAllCommunes();
            System.out.println("\nAll Communes:");
            for (Commune commune : communes) {
                System.out.println(commune);
            }
    
            // Fetch all airports
            List<Aeroport> aeroports = aeroportService.getAllAeroport();
            System.out.println("\nAll Airports:");
            for (Aeroport aeroport : aeroports) {
                System.out.println(aeroport);
            }
    
            // Fetch all train stations
            List<Gare> gares = gareService.getAllGares();
            System.out.println("\nAll Train Stations:");
            for (Gare gare : gares) {
                System.out.println(gare);
                gare1 = gare;
            }
    
            if (dep1 != null && an1 != null && gare1 != null) {
                // Example update call for Commune
                int idCommune = 29999;  // Assuming you have a commune with this ID
                String newNomCommune = "Nouvelle Commune";
                int newDepartementId = dep1.getIdDep();
                int newNbMaison = 100;
                int newNbAppart = 200;
                double newPrixMoyen = 300000.00;
                double newPrixM2Moyen = 4000.00;
                double newSurfaceMoy = 100.0;
                double newDepensesCulturellesTotales = 50000.00;
                double newBudgetTotal = 1000000.00;
                int newPopulation = 15000;
                int newAnnee = an1.getAnnee();
    
                //communeService.updateCommuneEtDonneesAnnuelles(idCommune, newNomCommune, newDepartementId, newNbMaison, newNbAppart, newPrixMoyen, newPrixM2Moyen, newSurfaceMoy, newDepensesCulturellesTotales, newBudgetTotal, newPopulation, newAnnee);
    
                // Example update call for Departement
                int idDep = dep1.getIdDep();
                String newNomDep = "Nouveau Département";
                double newInvestCulture2019 = 250000.00;
    
                departementService.updateDepartement(idDep, newNomDep, newInvestCulture2019);
    
                // Example insert call for Departement
                Departement newDepartement = new Departement(30000, "Département Test", 500000.00);
                departementService.insertDepartement(newDepartement);
    
                // Example delete call for Departement
                int idDepToDelete = 30000;  // Assuming you have a department with this ID to delete
                departementService.dropDepartement(idDepToDelete);
    
                // Example insert call for Annee
                Annee newAnnee1 = new Annee(2025, 2.5);
                anneeService.insertAnnee(newAnnee1);
    
                // Example update call for Annee
                anneeService.updateAnnee(2025, 3.0);
    
                // Example delete call for Annee
                int anneeToDelete = 2025;  // Assuming you have a year with this ID to delete
                anneeService.dropAnnee(anneeToDelete);
    
                // Example insert call for Aeroport
                Aeroport newAeroport = new Aeroport("Aeroport Test", "Adresse Test", dep1);
                aeroportService.insertAeroport(newAeroport);
    
                // Example update call for Aeroport
                aeroportService.updateAeroport("Aeroport Test", "Nouvelle Adresse", dep1.getIdDep());
    
                // Example delete call for Aeroport
                String aeroportToDelete = "Aeroport Test";  // Assuming you have an airport with this name to delete
                aeroportService.dropAeroport(aeroportToDelete);
    
                // Example insert call for Gare
                Gare newGare = new Gare(1001, "Gare Test", true, false, idCommune);  // Assuming idCommune is defined
                gareService.insertGare(newGare);
    
                // Example update call for Gare
                gareService.updateGare(1001, "Nouvelle Gare", false, true, newDepartementId);
    
                // Example delete call for Gare
                int gareToDelete = 1001;  // Assuming you have a train station with this code to delete
                gareService.dropGare(gareToDelete);
            } else {
                System.out.println("Les données de la commune, du département, de l'aéroport ou de la gare ne peuvent pas être mises à jour car les objets nécessaires ne sont pas disponibles.");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
