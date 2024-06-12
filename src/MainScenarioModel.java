

import data.Aeroport;
import data.Annee;
import data.Commune;
import data.Departement;

public class MainScenarioModel {


    public static void main(String[] args) {
       /*  testCreationDepartement();
        testAjoutCommune();
        testAjoutAeroport();*/
        //testAreInSameDepartment();
        testCreationAnnee();
        testCompareInflation();
    }

    public static void testCreationDepartement() {
        System.out.println("Testing creation of Departement...");
        Departement departement = new Departement(29, "TestDepartement", 100000.0);
        System.out.println("Department created: " + departement);
    }

    public static void testAjoutCommune() {
        System.out.println("\nTesting adding commune to Departement...");
        Departement departement = new Departement(22, "TestDepartement2", 200000.0);
        Commune commune = new Commune(22651, "TestCommune", 100, 50, 200000.0, 1500.0, 100.0, 50000.0, 100000.0, 1000, departement);
        departement.addCommune(commune);
        System.out.println("Commune added to department: " + departement);
    }

    public static void testAjoutAeroport() {
        System.out.println("\nTesting adding airport to Departement...");
        Departement departement = new Departement(35, "TestDepartement3", 300000.0);
        Aeroport aeroport = new Aeroport("TestAirport", "TestAddress", departement);
        departement.addAeroport(aeroport);
        System.out.println("Airport added to department: " + departement);
    }

    public static void testAreInSameDepartment() {
        // Création de deux départements
        Departement departement1 = new Departement(29, "Finistère", 100000);
        Departement departement2 = new Departement(35, "Ille-et-Vilaine", 120000);

        // Création de deux aéroports dans le même département
        Aeroport aeroport1 = new Aeroport("Aeroport1", "Adresse1", departement1);
        Aeroport aeroport2 = new Aeroport("Aeroport2", "Adresse2", departement1);

        // Vérification que les deux aéroports sont dans le même département
        if (aeroport1.areInSameDepartment(aeroport2)) {
            System.out.println("Les deux aéroports sont dans le même département.");
        } else {
            System.out.println("Les deux aéroports ne sont pas dans le même département.");
        }

        // Création d'un troisième aéroport dans un autre département
        Aeroport aeroport3 = new Aeroport("Aeroport3", "Adresse3", departement2);

        // Vérification que les deux aéroports ne sont pas dans le même département
        if (!aeroport1.areInSameDepartment(aeroport3)) {
            System.out.println("Les deux aéroports ne sont pas dans le même département.");
        } else {
            System.out.println("Les deux aéroports sont dans le même département.");
        }
    }

    public static void testCreationAnnee() {
        try {
            Annee annee = new Annee(2020, 2.5);
            System.out.println("Création de l'année : " + annee);
        } catch (RuntimeException e) {
            System.out.println("Erreur lors de la création de l'année : " + e.getMessage());
        }
    }

    public static void testCompareInflation() {
        Annee annee2020 = new Annee(2020, 2.5);
        Annee annee2021 = new Annee(2021, 3.0);

        String comparaison = annee2020.compareInflation(annee2021);
        System.out.println("Comparaison des taux d'inflation entre 2020 et 2021 : " + comparaison);

        // Tester pour deux années avec le même taux d'inflation
        Annee annee2022 = new Annee(2022, 3.0);
        comparaison = annee2021.compareInflation(annee2022);
        System.out.println("Comparaison des taux d'inflation entre 2021 et 2022 : " + comparaison);

        // Tester pour deux années avec le même taux d'inflation
        Annee annee2023 = new Annee(2023, 2.5);
        comparaison = annee2022.compareInflation(annee2023);
        System.out.println("Comparaison des taux d'inflation entre 2022 et 2023 : " + comparaison);
    }
}

