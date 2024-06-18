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
    
            
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
