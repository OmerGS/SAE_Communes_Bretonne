use communeBretonne;

CREATE TABLE Departement
	(
	idDep INTEGER,
	nomDep VARCHAR(20),
    investissementCulturel2019 INTEGER,
	CONSTRAINT pk_Departement PRIMARY KEY(idDep)
	)
;


CREATE TABLE Commune
	(
	idCommune INTEGER,
	nomCommune VARCHAR(50),
    leDepartement INTEGER NOT NULL,
    CONSTRAINT pk_Commune PRIMARY KEY(idCommune),
    CONSTRAINT fk_Commune_Departement FOREIGN KEY(leDepartement) REFERENCES Departement (idDep)
	)
;
        
CREATE TABLE Gare
	(
	codeGare INTEGER,
	nomGare VARCHAR(40),
    estFret BOOLEAN,
    estVoyageur BOOLEAN,
    laCommune INTEGER NOT NULL,
	CONSTRAINT pk_Gare PRIMARY KEY(codeGare),
    CONSTRAINT fk_Gare_Commune FOREIGN KEY(laCommune) REFERENCES Commune(idCommune)
	)
;

CREATE TABLE Voisinage
	(
	commune INTEGER,
	communeVoisine INTEGER,
	
    CONSTRAINT pk_Voisinage PRIMARY KEY(commune,communeVoisine),
    CONSTRAINT fk_Voisinage_Commune FOREIGN KEY(commune) REFERENCES Commune(idCommune),
    CONSTRAINT fk_Voisinage_Commune2 FOREIGN KEY(communeVoisine) REFERENCES Commune(idCommune)
	)
;

CREATE TABLE Annee
	(
	annee INTEGER,
	tauxInflation FLOAT,
    CONSTRAINT pk_Annee PRIMARY KEY(annee)
	)
;

CREATE TABLE DonneesAnnuelles
	(
	lAnnee INTEGER,
	laCommune INTEGER,
    nbMaison INTEGER, 
    nbAppart INTEGER, 
    prixMoyen FLOAT,
    prixM2Moyen FLOAT,
    SurfaceMoy FLOAT,
    
    depensesCulturellesTotales FLOAT,
    budgetTotal FLOAT,

    population INTEGER,
    
	CONSTRAINT fk_DonneesAnnuelles_Annee FOREIGN KEY(lAnnee) REFERENCES Annee(annee),
    CONSTRAINT fk_DonneesAnnuelles_Commune FOREIGN KEY(laCommune) REFERENCES Commune(idCommune),
    CONSTRAINT pk_DonneesAnnuelles PRIMARY KEY(lAnnee,laCommune)
	)
;

CREATE TABLE Aeroport
	(
	nom VARCHAR(40),
	adresse VARCHAR(50),
    leDepartement INTEGER NOT NULL,
    CONSTRAINT fk_Aeroport_Departement FOREIGN KEY(leDepartement) REFERENCES Departement(idDep),
	CONSTRAINT pk_Aeroport PRIMARY KEY(nom)
	)
;


CREATE TABLE Utilisateur (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(30) NOT NULL,
    prenom VARCHAR(30) NOT NULL,
    email VARCHAR(40) NOT NULL UNIQUE,
    motDePasse VARCHAR(255) NOT NULL,
    salt VARCHAR(500) NOT NULL,
    isAdmin INT NOT NULL
);
