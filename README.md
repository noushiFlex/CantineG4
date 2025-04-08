# Cantine Scolaire - Système de Gestion

Ce projet est un système de gestion de cantine scolaire développé en Java, disponible en deux versions :
- Une version console (branche `console`)  
- Une interface graphique en JavaFX (branche `interface`)

## 📋 Fonctionnalités

Le système permet de gérer :
- Les plats et menus 🍽️
- Les commandes des utilisateurs 🛒
- Les clients (élèves, enseignants) 👥
- Le personnel de cantine 🧑‍🍳

## 🏗️ Architecture

Les diagrammes UML du projet sont disponibles sur le dépôt [organisation-pauses](https://github.com/noushiFlex/organisation-pauses).

## 💻 Installation et démarrage

### Prérequis
- Java JDK 11 ou supérieur
- PostgreSQL

### Structure du projet
- `src` : Code source de l'application
- `lib` : Bibliothèques et dépendances
- `bin` : Fichiers compilés

### Démarrage de l'application console
```bash
# Compilation
javac -d bin src/*.java

# Exécution
java -cp bin Main
```

## 🔒 Connexion

Pour vous connecter à l'application, utilisez :
- Nom d'utilisateur : `admin`
- Mot de passe : `admin`

## 💾 Base de données

Cette application utilise PostgreSQL comme système de gestion de base de données.

### Configuration de la base de données

1. Installez PostgreSQL sur votre machine (si ce n'est pas déjà fait)
2. Exécutez les commandes SQL suivantes pour créer et configurer la base de données:

```sql
-- Création de la base de données
CREATE DATABASE cantine;

-- Connexion à la base de données
\c cantine

-- Création d'un utilisateur et attribution des droits
CREATE USER cantine WITH PASSWORD 'cantine';
GRANT ALL PRIVILEGES ON DATABASE cantine TO cantine;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO cantine;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO cantine;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON TABLES TO cantine;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON SEQUENCES TO cantine;

-- Tables principales

-- Table Personne (classe de base pour Client et Personnel)
CREATE TABLE Personne (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL
);

-- Table ClientRestau
CREATE TABLE ClientRestau (
    id INTEGER PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES Personne(id) ON DELETE CASCADE
);

-- Table PersonnelRestau
CREATE TABLE PersonnelRestau (
    id INTEGER PRIMARY KEY,
    poste VARCHAR(100) NOT NULL,
    salaire DECIMAL(10, 2) NOT NULL CHECK (salaire >= 0),
    FOREIGN KEY (id) REFERENCES Personne(id) ON DELETE CASCADE
);

-- Table PlatRestau
CREATE TABLE PlatRestau (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prix DECIMAL(10, 2) NOT NULL CHECK (prix >= 0)
);

-- Table CommandeRestau
CREATE TABLE CommandeRestau (
    id SERIAL PRIMARY KEY,
    date_commande TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10, 2) NOT NULL CHECK (total >= 0),
    client_id INTEGER NOT NULL,
    FOREIGN KEY (client_id) REFERENCES ClientRestau(id) ON DELETE CASCADE
);

-- Table de jointure CommadePlats (relation entre commandes et plats)
CREATE TABLE CommandePlats (
    commande_id INTEGER NOT NULL,
    plat_id INTEGER NOT NULL,
    quantite INTEGER NOT NULL DEFAULT 1 CHECK (quantite > 0),
    prix_unitaire DECIMAL(10, 2) NOT NULL CHECK (prix_unitaire >= 0),
    PRIMARY KEY (commande_id, plat_id),
    FOREIGN KEY (commande_id) REFERENCES CommandeRestau(id) ON DELETE CASCADE,
    FOREIGN KEY (plat_id) REFERENCES PlatRestau(id) ON DELETE CASCADE
);

-- Procédure stockée pour ajouter un plat à une commande existante
CREATE OR REPLACE PROCEDURE ajouter_plat_commande(
    p_commande_id INTEGER, 
    p_plat_id INTEGER,
    p_quantite INTEGER
) 
LANGUAGE plpgsql
AS $$
DECLARE
    v_prix_plat DECIMAL(10, 2);
    v_plat_existant BOOLEAN;
BEGIN
    -- Récupérer le prix du plat
    SELECT prix INTO v_prix_plat FROM PlatRestau WHERE id = p_plat_id;
    
    -- Vérifier si le plat est déjà dans la commande
    SELECT EXISTS (
        SELECT 1 FROM CommandePlats 
        WHERE commande_id = p_commande_id AND plat_id = p_plat_id
    ) INTO v_plat_existant;
    
    IF v_plat_existant THEN
        -- Mettre à jour la quantité du plat
        UPDATE CommandePlats 
        SET quantite = quantite + p_quantite
        WHERE commande_id = p_commande_id AND plat_id = p_plat_id;
    ELSE
        -- Ajouter le nouveau plat à la commande
        INSERT INTO CommandePlats (commande_id, plat_id, quantite, prix_unitaire)
        VALUES (p_commande_id, p_plat_id, p_quantite, v_prix_plat);
    END IF;
    
    -- Mettre à jour le total de la commande
    UPDATE CommandeRestau
    SET total = total + (v_prix_plat * p_quantite)
    WHERE id = p_commande_id;
END;
$$;
```

## 🔄 Autres versions

- **Console** : Version console sur la branche `console`
- **Interface** : Version avec interface graphique JavaFX disponible sur la branche `interface`

## 🔗 Liens utiles

Dépôt principal avec les diagrammes UML : [https://github.com/noushiFlex/organisation-pauses](https://github.com/noushiFlex/organisation-pauses)