<<<<<<< HEAD
import data.Commune;
import data.Departement;

public class ScenarioSAE {
    public static void main(String[] args) {

        Departement finistere = new Departement(29, "Finistère", 1000000000);
        Departement illeetvillaine = new Departement(35, "Ille-Et-Villaine", 1000000000);
        Departement morbihan = new Departement(56, "morbihan", 1000000000);



        // Création d'une commune
        Commune commune1 = new Commune(29000, "Brest", 1500, 2000, 200000, 3000, 150, 50000, 1000000, 100000, finistere);

        // Ajout de communes voisines
        Commune commune2 = new Commune(35000, "Rennes", 2000, 2500, 250000, 3500, 200, 60000, 1200000, 120000, illeetvillaine);
        Commune commune3 = new Commune(56000, "Lorient", 1000, 1500, 180000, 2800, 120, 40000, 800000, 80000, morbihan);
        commune1.addVoisine(commune2);
        commune1.addVoisine(commune3);

        // Vérification si la commune est la plus importante parmi ses voisines
        boolean isMostImportant = commune1.isMostImportant();
        System.out.println("Est-ce que la commune de " + commune1.getNomCommune() + " est la plus importante parmi ses voisines ? " + isMostImportant);

        // Identification de la commune avec le prix moyen le plus élevé par propriété parmi ses voisines
        String highestPriceCommune = commune1.highestNeighboursPrice();
        System.out.println("La commune avec le prix moyen le plus élevé par propriété parmi les voisines de " + commune1.getNomCommune() + " est : " + highestPriceCommune);

    }
}
=======
>>>>>>> 6bbeec101af73dbd229729206d2b35326b038aec
