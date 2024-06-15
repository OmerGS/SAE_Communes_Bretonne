

import data.Aeroport;
import data.Annee;
import data.Commune;
import data.Departement;
import data.Gare;

public class MainScenarioModel {
    private static Departement departement1;
    private static Departement departement2;
    private static Departement departement3;

    public static void main(String[] args) {
        /*testCreationDepartement();
        testAjoutCommune();
        testAjoutAeroport();
        testAreInSameDepartment();
        testCreationAnnee();
        testCompareInflation();
        testCreationCommune();
        testIsMostImportant();
        testHighestNeighboursPrice();
        testCulturalExpensesPerInhabitant();
        testCreationGare();
        testIsEstFretAndIsEstVoyageur();*/
    }

   /* public static void testCreationDepartement() {
        System.out.println("Testing creation of Departement...");
        departement1 = new Departement(29, "Finistère", 100000);
        System.out.println("Department created: " + departement1);
    }

    public static void testAjoutCommune() {
        System.out.println("\nTesting adding commune to Departement..."); Departement departement = new Departement(22, "Cote d'Armor", 200000.0);
       /*Commune commune = new Commune(2019, 22651, "TestCommune", 100, 50, 200000.0, 1500.0, 100.0, 50000.0, 100000.0, 1000, departement1);
        departement.addCommune(commune);
        System.out.println("Commune added to department: " + departement1);
    }

    public static void testAjoutAeroport() {
        System.out.println("\nTesting adding airport to Departement...");
        departement2 = new Departement(35, "Ille-et-Vilaine", 120000);
        Aeroport aeroport = new Aeroport("TestAirport", "TestAddress", departement2);
        departement2.addAeroport(aeroport);
        System.out.println("Airport added to department: " + departement2);
    }

    public static void testAreInSameDepartment() {

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



    public static void testCreationCommune() {
        try {

            departement3 = new Departement(56, "Morbihan", 1000000.0);
            // Création de communes avec des identifiants correspondant aux départements
            Commune commune1 = new Commune(2019, 29651, "Brest", 100, 50, 200000.0, 1500.0, 100.0, 50000.0, 100000.0, 1000, departement1);
            System.out.println("Création de la commune 1 : " + commune1);

            Commune commune2 = new Commune(2019, 35651, "Rennes", 100, 50, 200000.0, 1500.0, 100.0, 50000.0, 100000.0, 1000, departement2);
            System.out.println("Création de la commune 2 : " + commune2);

            Commune commune3 = new Commune(2019, 56651, "Vannes", 100, 50, 200000.0, 1500.0, 100.0, 50000.0, 100000.0, 1000, departement3);
            System.out.println("Création de la commune 3 : " + commune3);
        } catch (RuntimeException e) {
            System.out.println("Erreur lors de la création de la commune : " + e.getMessage());
        }
    }

    public static void testIsMostImportant() {
        Commune commune1 = new Commune(2019, 29651, "Brest", 50, 150, 200000.0, 1500.0, 100.0000, 50000.0, 100000.0, 100000, departement1);
        Commune commune2 = new Commune(2019, 35651, "Rennes", 100, 50, 2000.0, 1500.0000, 100.0, 50000.0000, 100000.0, 1000, departement2);
        Commune commune3 = new Commune(2019, 56651, "Vannes", 200, 50, 20000000.0, 1500.0, 100.0, 50000.0, 100000000.0, 1000, departement3);
        commune3.addVoisine(commune1);
        commune3.addVoisine(commune2);

        System.out.println("La commune est la plus importante parmi ses voisines : " + commune3.isMostImportant());
    }

    public static void testHighestNeighboursPrice() {
        Commune commune4 = new Commune(2019, 29681, "Brest", 100, 50, 200000.0, 1500.0, 100.0, 50000.0, 1000000.0, 50000, departement1);
        Commune commune5 = new Commune(2019, 35681, "Rennes", 200, 100, 300000.0, 2000.0, 150.0, 60000.0, 1200000.0, 70000, departement2);
        Commune commune6 = new Commune(2019, 56681, "Vannes", 1000, 500, 200000.0, 1500.0, 100.0, 50000.0, 1000000.0, 50000, departement3);
        commune6.addVoisine(commune4);
        commune6.addVoisine(commune5);

        System.out.println("Commune avec le prix moyen le plus élevé parmi ses voisines : " + commune6.highestNeighboursPrice());
    }

    public static void testCulturalExpensesPerInhabitant() {
        Commune commune = new Commune(2019, 29000, "Quimper", 1000, 500, 200000.0, 1500.0, 100.0, 50000.0, 1000000.0, 50000, departement3);
        double culturalExpensesPerInhabitant = commune.culturalExpensesPerInhabitant();
        System.out.println("Dépenses culturelles par habitant : " + culturalExpensesPerInhabitant);
    }

    public static void testCreationGare() {
        System.out.println("Testing Gare constructor...");
        Commune commune7 = new Commune(2019, 56000, "Vannes", 1000, 500, 200000.0, 1500.0, 100.0, 50000.0, 1000000.0, 50000, departement3);
        Gare gare = new Gare(1, "Gare de Test", true, true, commune7);
        System.out.println("Création de la gare :" + gare);
    }

    public static void testIsEstFretAndIsEstVoyageur() {
        System.out.println("Testing isEstFretAndIsEstVoyageur...");
        Commune commune8 = new Commune(2019, 56001, "Vannes", 1000, 500, 200000.0, 1500.0, 100.0, 50000.0, 1000000.0, 50000, departement3);
        Gare gare1 = new Gare(1, "Gare de Test 1", true, true, commune8);
        System.out.println("La gare " + gare1.getNomGare() + " est fret et voyageur :" + gare1.isEstFretAndIsEstVoyageur());
        Commune commune9 = new Commune(2019, 56002, "Vannes", 1000, 500, 200000.0, 1500.0, 100.0, 50000.0, 1000000.0, 50000, departement3);
        Gare gare2 = new Gare(1, "Gare de Test 2", false, true, commune9);
        System.out.println("La gare " + gare2.getNomGare() + " est fret et voyageur :" + gare2.isEstFretAndIsEstVoyageur());

        Commune commune10 = new Commune(2019, 56002, "Vannes", 1000, 500, 200000.0, 1500.0, 100.0, 50000.0, 1000000.0, 50000, departement3);
        Gare gare3 = new Gare(1, "Gare de Test 3", false, false, commune10);
        System.out.println("La gare " + gare3.getNomGare() + " est fret et voyageur :" + gare3.isEstFretAndIsEstVoyageur());
    }


    public static void scenarioDiagrammeSequence() {
        Departement departement = new Departement(22, "Cote d'Armor", 100000.0);
        Commune commune = new Commune(2019, 22142, "CommuneTest", 12, 14, 12, 11, 15, 14, 36, 14, departement);
        commune.ajouterGare(new Gare(12546, "La gare", false, false, commune));
        Gare gare = commune.getGare();
        if(gare.isEstFretAndIsEstVoyageur()){
            System.out.println("La gare peut avoir des voyageurs et du fret");

        }else{
            System.out.println("La gare ne peut pas avoir de voyageur ou de fret");
        }
        Commune communeTwo = new Commune(2019,22459 , "Commune Test 2", 10, 15, 15, 15, 15, 15, 154, 12, departement);
        gare.setCommune(communeTwo);
        
        
    }*/

    
}

