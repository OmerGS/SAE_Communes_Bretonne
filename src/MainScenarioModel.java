

import data.Aeroport;
import data.Annee;
import data.Commune;
import data.Departement;
import data.Gare;
/**
 * @author R.Péron, O.Gunes, B.Campion
*/
public class MainScenarioModel {

    private static Annee annee = new Annee(2020, 2.5);;

    public static void main(String[] args) {
        testCreationDepartement();
        testCreationAnnee();
        testAjoutCommune();
        testAjoutAeroport();
        testAreInSameDepartment();
        testCompareInflation();
        testCreationCommune();
        testIsMostImportant();
        testHighestNeighboursPrice();
        testCulturalExpensesPerInhabitant();
        testCreationGare();
        testIsEstFretAndIsEstVoyageur();
        scenarioDiagrammeSequence();
    }

   public static void testCreationDepartement() {
        System.out.println("Test creation  Departement :");
        System.out.println("Cas Normal");
        try{
            Departement departement = new Departement(29, "Finistère", 100000);
            System.out.println("Departement : " + departement);
            System.out.println("Test Réussi");
        }catch(IllegalArgumentException e){
            System.out.println("Echec du Test : " + e.getMessage());
        }

        System.out.println("Cas d'erreur");
        try{
            Departement departement = new Departement(29, null, 100000);
            System.out.println("Departement : " + departement);
            System.out.println("Echec du Test");
        }catch(IllegalArgumentException e){
            System.out.println("Test Réussi : " + e.getMessage());
        }

        try{
            Departement departement = new Departement(-5, "Finistère", 100000);
            System.out.println("Departement : " + departement);
            System.out.println("Echec du Test");
        }catch(RuntimeException e){
            System.out.println("Test Réussi : " + e.getMessage());
        }
    }

    public static void testCreationAnnee() {
        System.out.println("Test creation  Annee :");
        System.out.println("Cas normal");
        try {
            Annee annee1 = new Annee(2020, 2.5);
            System.out.println("Année : " + annee1);
            System.out.println("Test Réussi");
        } catch (RuntimeException e) {
            System.out.println("Echec du test : " + e.getMessage());
        }

        System.out.println("Cas d'erreur");
        try {
            annee = new Annee(2020, -5);
            System.out.println("Année : " + annee);
            System.out.println("Echec du Test");
        } catch (RuntimeException e) {
            System.out.println("Test Réussi : " + e.getMessage());
        }
    }

    public static void testAjoutCommune() {
        System.out.println("Test ajout Commune au Département :");
        
        System.out.println("Cas Normal");
        try {
            Departement departement = new Departement(22, "Cote d'Armor", 200000.0);
            Commune commune = new Commune(annee, 22651, "TestCommune", 100, 50, 200000.0, 1500.0, 100.0, 50000.0, 100000.0, 1000, departement);
            departement.addCommune(commune);
            System.out.println("Commune ajoutée au département : " + departement);
            System.out.println("Test Réussi");
        } catch (RuntimeException e) {
            System.out.println("Echec du Test : " + e.getMessage());
        }
    
        System.out.println("Cas d'erreur");
        try {
            Departement departement = new Departement(22, "Cote d'Armor", 200000.0);
            departement.addCommune(null);
            System.out.println("Commune ajoutée au département : " + departement);
            System.out.println("Echec du Test");
        } catch (RuntimeException e) {
            System.out.println("Test Réussi : " + e.getMessage());
        }
    }

    public static void testAjoutAeroport() {
        System.out.println("Test ajout Aéroport au Département :");
        
        System.out.println("Cas Normal");
        try {
            Departement departement = new Departement(35, "Ille-et-Vilaine", 120000);
            Aeroport aeroport = new Aeroport("TestAirport", "TestAddress", departement);
            departement.addAeroport(aeroport);
            System.out.println("Aéroport ajouté au département : " + departement);
            System.out.println("Test Réussi");
        } catch (RuntimeException e) {
            System.out.println("Echec du Test : " + e.getMessage());
        }
    
        System.out.println("Cas d'erreur");
        try {
            Departement departement = new Departement(35, "Ille-et-Vilaine", 120000);
            departement.addAeroport(null);
            System.out.println("Aéroport ajouté au département : " + departement);
            System.out.println("Echec du Test");
        } catch (RuntimeException e) {
            System.out.println("Test Réussi: " + e.getMessage());
        }
    }

    public static void testAreInSameDepartment() {
        System.out.println("Test AreInSameDepartment :");
    
        try {
            Departement departement1 = new Departement(29, "Finistère", 100000);
            Departement departement2 = new Departement(35, "Ille-et-Vilaine", 120000);
    
            Aeroport aeroport1 = new Aeroport("Aeroport1", "Adresse1", departement1);
            Aeroport aeroport2 = new Aeroport("Aeroport2", "Adresse2", departement1);
            Aeroport aeroport3 = new Aeroport("Aeroport3", "Adresse3", departement2);
    
            if (aeroport1.areInSameDepartment(aeroport2)) {
                System.out.println("Les deux aéroports sont dans le même département.");
            } else {
                System.out.println("Les deux aéroports ne sont pas dans le même département.");
            }
    
            if (!aeroport1.areInSameDepartment(aeroport3)) {
                System.out.println("Les deux aéroports ne sont pas dans le même département.");
            } else {
                System.out.println("Les deux aéroports sont dans le même département.");
            }
            System.out.println("Test Réussi");
        } catch (RuntimeException e) {
            System.out.println("Echec du Test : " + e.getMessage());
        }
    }


    public static void testCompareInflation() {
        System.out.println("Test compareInflation :");
    
        try {
            Annee annee2020 = new Annee(2020, 2.5);
            Annee annee2021 = new Annee(2021, 3.0);
            Annee annee2022 = new Annee(2022, 3.0);
            Annee annee2023 = new Annee(2023, 2.5);
    
            System.out.println("Comparaison des taux d'inflation entre 2020 et 2021 : " + annee2020.compareInflation(annee2021));
            System.out.println("Comparaison des taux d'inflation entre 2021 et 2022 : " + annee2021.compareInflation(annee2022));
            System.out.println("Comparaison des taux d'inflation entre 2022 et 2023 : " + annee2022.compareInflation(annee2023));
            System.out.println("Test Réussi");
        } catch (RuntimeException e) {
            System.out.println("Echec du Test : " + e.getMessage());
        }
    }



    public static void testCreationCommune() {
        System.out.println("Test creation Commune :");
        
        try {
            Departement departement1 = new Departement(29, "Finistère", 100000);
            Departement departement2 = new Departement(35, "Ille-et-Vilaine", 120000);
            Departement departement3 = new Departement(56, "Morbihan", 1000000.0);
    
            Commune commune1 = new Commune(annee, 29651, "Brest", 100, 50, 200000.0, 1500.0, 100.0, 50000.0, 100000.0, 1000, departement1);
            Commune commune2 = new Commune(annee, 35651, "Rennes", 100, 50, 200000.0, 1500.0, 100.0, 50000.0, 100000.0, 1000, departement2);
            Commune commune3 = new Commune(annee, 56651, "Vannes", 100, 50, 200000.0, 1500.0, 100.0, 50000.0, 100000.0, 1000, departement3);
    
            System.out.println("Création de la commune 1 : " + commune1);
            System.out.println("Création de la commune 2 : " + commune2);
            System.out.println("Création de la commune 3 : " + commune3);
            System.out.println("Test Réussi");
        } catch (RuntimeException e) {
            System.out.println("Echec du Test : " + e.getMessage());
        }
    }

    public static void testIsMostImportant() {
        System.out.println("Test isMostImportant :");
    
        try {
            Departement departement1 = new Departement(29, "Finistère", 100000);
            Departement departement2 = new Departement(35, "Ille-et-Vilaine", 120000);
            Departement departement3 = new Departement(56, "Morbihan", 1000000.0);
    
            Commune commune1 = new Commune(annee, 29651, "Brest", 50, 150, 200000.0, 1500.0, 100.0, 50000.0, 100000.0, 100000, departement1);
            Commune commune2 = new Commune(annee, 35651, "Rennes", 100, 50, 2000.0, 1500.0, 100.0, 50000.0, 100000.0, 1000, departement2);
            Commune commune3 = new Commune(annee, 56651, "Vannes", 200, 50, 20000000.0, 1500.0, 100.0, 50000.0, 100000000.0, 1000, departement3);
    
            commune3.addVoisine(commune1);
            commune3.addVoisine(commune2);
    
            System.out.println("La commune est la plus importante parmi ses voisines : " + commune3.isMostImportant());
            System.out.println("Test Réussi");
        } catch (RuntimeException e) {
            System.out.println("Echec du Test : " + e.getMessage());
        }
    }

    public static void testHighestNeighboursPrice() {
        System.out.println("Test highestNeighboursPrice :");
    
        try {
            Departement departement1 = new Departement(29, "Finistère", 100000);
            Departement departement2 = new Departement(35, "Ille-et-Vilaine", 120000);
            Departement departement3 = new Departement(56, "Morbihan", 1000000.0);
    
            Commune commune4 = new Commune(annee, 29681, "Brest", 100, 50, 200000.0, 1500.0, 100.0, 50000.0, 1000000.0, 50000, departement1);
            Commune commune5 = new Commune(annee, 35681, "Rennes", 200, 100, 300000.0, 2000.0, 150.0, 60000.0, 1200000.0, 70000, departement2);
            Commune commune6 = new Commune(annee, 56681, "Vannes", 1000, 500, 200000.0, 1500.0, 100.0, 50000.0, 1000000.0, 50000, departement3);
    
            commune6.addVoisine(commune4);
            commune6.addVoisine(commune5);
    
            System.out.println("Commune avec le prix moyen le plus élevé parmi ses voisines : " + commune6.highestNeighboursPrice());
            System.out.println("Test Réussi");
        } catch (RuntimeException e) {
            System.out.println("Echec du Test : " + e.getMessage());
        }
    }

    public static void testCulturalExpensesPerInhabitant() {
        System.out.println("Test culturalExpensesPerInhabitant :");
    
        try {
            Departement departement3 = new Departement(56, "Morbihan", 1000000.0);
    
            Commune commune = new Commune(annee, 29000, "Quimper", 1000, 500, 200000.0, 1500.0, 100.0, 50000.0, 1000000.0, 50000, departement3);
            double culturalExpensesPerInhabitant = commune.culturalExpensesPerInhabitant();
            System.out.println("Dépenses culturelles par habitant : " + culturalExpensesPerInhabitant);
            System.out.println("Test Réussi");
        } catch (RuntimeException e) {
            System.out.println("Echec du Test : " + e.getMessage());
        }
    }

    public static void testCreationGare() {
        System.out.println("Test creation Gare :");
    
        try {
            Departement departement3 = new Departement(56, "Morbihan", 1000000.0);
            Commune commune7 = new Commune(annee, 56000, "Vannes", 1000, 500, 200000.0, 1500.0, 100.0, 50000.0, 1000000.0, 50000, departement3);
    
            Gare gare = new Gare(1, "Gare de Test", true, true, commune7);
            System.out.println("Création de la gare : " + gare);
            System.out.println("Test Réussi");
        } catch (RuntimeException e) {
            System.out.println("Echec du Test : " + e.getMessage());
        }
    }

    public static void testIsEstFretAndIsEstVoyageur() {
        System.out.println("Test isEstFretAndIsEstVoyageur :");
    
        try {
            Departement departement3 = new Departement(56, "Morbihan", 1000000.0);
    
            Commune commune8 = new Commune(annee, 56001, "Vannes", 1000, 500, 200000.0, 1500.0, 100.0, 50000.0, 1000000.0, 50000, departement3);
            Gare gare1 = new Gare(1, "Gare de Test 1", true, true, commune8);
            System.out.println("La gare " + gare1.getNomGare() + " est fret et voyageur : " + gare1.isEstFretAndIsEstVoyageur());
    
            Commune commune9 = new Commune(annee, 56002, "Vannes", 1000, 500, 200000.0, 1500.0, 100.0, 50000.0, 1000000.0, 50000, departement3);
            Gare gare2 = new Gare(1, "Gare de Test 2", false, true, commune9);
            System.out.println("La gare " + gare2.getNomGare() + " est fret et voyageur : " + gare2.isEstFretAndIsEstVoyageur());
    
            Commune commune10 = new Commune(annee, 56003, "Vannes", 1000, 500, 200000.0, 1500.0, 100.0, 50000.0, 1000000.0, 50000, departement3);
            Gare gare3 = new Gare(1, "Gare de Test 3", false, false, commune10);
            System.out.println("La gare " + gare3.getNomGare() + " est fret et voyageur : " + gare3.isEstFretAndIsEstVoyageur());
            System.out.println("Test Réussi");
        } catch (RuntimeException e) {
            System.out.println("Echec du Test : " + e.getMessage());
        }
    }


    public static void scenarioDiagrammeSequence() {
        //Diagramme de séquence (298 à 311)
        Departement d = new Departement(22, "Cote d'Armor", 100000.0);
        System.out.println(d + "\n");
        Annee a = new Annee(2019, 5.5);
        System.out.println(a + "\n");
        Commune c1 = new Commune(a, 22142, "CommuneTest", 12, 14, 12, 11, 15, 14, 36, 14, d);
        System.out.println(c1 + "\n");
        c1.addGare(new Gare(12546, "La gare", false, false, c1));
        Gare g = c1.getGare();
        System.out.println(g + "\n");
        Commune c2 = new Commune(a,22459 , "Commune Test 2", 10, 15, 15, 15, 15, 15, 154, 12, d);
        System.out.println(c2 + "\n");
        g.setCommune(c2);
        System.out.println(g + "\n");

        //
        Aeroport ae = new Aeroport("aeroport 1", "adresse de l'aeroport", d);
        d.addAeroport(ae);
        d.addCommune(c2);
        d.addCommune(c1);
        System.out.println(d.getAeroport() + "\n");
        System.out.println(d.getCommunes());

        
        
    }

    
}

