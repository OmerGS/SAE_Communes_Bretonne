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

            // Fetch all departments
            List<Departement> departements = departementService.getAllDepartement();
            System.out.println("All Departments:");
            /*for (Departement departement : departements) {
                System.out.println(departement);
            }*/

            // Fetch all communes
            List<Commune> communes = communeService.getAllCommunes();
            System.out.println("\nAll Communes:");
            /*for (Commune commune : communes) {
                System.out.println(commune);
            }*/

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
            }

            // Fetch all years
            List<Annee> annees = anneeService.getAllAnnee();
            System.out.println("\nAll Years:");
            for (Annee annee : annees) {
                System.out.println(annee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
