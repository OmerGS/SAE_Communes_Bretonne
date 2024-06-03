# SAE Communes Bretonne

Ce projet vise à développer une application permettant de valoriser les données publiques sur les communes bretonnes. L'objectif est de fournir des outils pour analyser l'attractivité des communes en utilisant divers critères tels que le prix moyen des logements, l'accessibilité (présence de gares et d'aéroports), et les dépenses culturelles.

## Contexte
La région Bretagne souhaite mieux comprendre les critères d'attractivité des communes bretonnes pour homogénéiser le flux de touristes et améliorer la qualité de vie des résidents. Ce projet s'inscrit dans le cadre pédagogique du BUT Informatique de Vannes.

## Les données 

Les données utilisées dans ce projet sont fournies par [le gouvernement](https://www.data.gouv.fr/fr/) : 

- departement.csv : Liste des numéros et noms des départements en Bretagne
- communesBretonnes.csv : Liste des codes Insee et noms des communes bretonnes
- voisinageCommunesBretonnes.csv : Liste des communes bretonnes adjacentes
- prixParCommune.csv : Prix moyen des logements par commune
- investissementCulturelParDep.csv : Montant de l'allocation du Ministère de la Culture par département
- gare.csv : Présence des gares par commune
- depensesCulturellesParCommune.csv : Montant des dépenses culturelles par commune
- aeroport.csv : Localisation des aéroports bretons
- tauxInflationParAn.csv : Taux d'inflation par an

## Installation de l'application
1. `git clone https://github.com/OmerGS/SAE_Communes_Bretonne.git`
2. `cd SAE_Communes_Bretonne`

Pour pouvoir lancé l'application vous devrez au préalable créer un dossier properties dans la racine du projet.
Ensuite vous devrez créer deux fichier distinctes, le premier devra être nommé `config.properties` contenant les informations de connexion vers votre base de données.

![image](https://github.com/OmerGS/SAE_Communes_Bretonne/assets/113857370/89175b7a-1c94-4e0f-ac35-3c1942a3f1e4)


Le deuxième fichier lui devra être nommée `mail.properties` et devra contenir les informations de connexion vers un mail avec le service SMTP activée. (Dans notre code nous utilisons le serveur SMTP de Microsoft)

![image](https://github.com/OmerGS/SAE_Communes_Bretonne/assets/113857370/c171df4f-20ac-4b9b-90b0-70d8568ff206)

3. Maintenant vous pouvez lancer le fichier `lancerApp.bat présent dans le dossier ws`

## L'application

L'application est composé de plusieurs partie : 
- `/resources` : Contient toutes les ressources de l'application (Image, données annexes, css...)
- `src/controller` : Contient le code necessaires pour tout les controllers.
- `src/model` : Contient deux packages
  1. `DAO` : C'est le package responsable de recuperer les données de la base de données et de créer des classes Java de celle-ci
  2. `data` : Package contenant la structure des classes pour pouvoir stocké les données de la base de données
- `src/view` : Package contenant toutes les classes permettant le bon fonctionnement de l'application JavaFX.


## Spécificité de l'application
Notre application stocke les comptes utilisateurs dans la base de données de manières chiffrées uniquement pour le mot de passe. La permission d'écriture de données sera uniquement accessibles aux administrateurs dans notre projet ceci sera donc attribué à toute personne ayant le domaine `@univ-ubs.fr`.
