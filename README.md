# Système de Gestion de Cantine

Une application console Java permettant de gérer les opérations d'une cantine, incluant la gestion des clients, du personnel, des plats et des commandes.

## Description du projet

Ce système de gestion de cantine permet aux administrateurs de:
- Gérer le menu de la cantine (ajouter, modifier, supprimer des plats)
- Gérer les clients (inscription, modification, suppression)
- Gérer le personnel (embauche, mise à jour des informations, licenciement)
- Traiter les commandes (création, consultation, détails)

## Fonctionnalités

### Menu principal
- Connexion au système avec authentification
- Navigation intuitive avec menus et sous-menus

### Gestion des plats
- Ajouter un nouveau plat au menu
- Modifier les informations d'un plat existant
- Supprimer un plat du menu
- Consulter la liste complète des plats

### Gestion des clients
- Enregistrer de nouveaux clients
- Modifier les informations des clients
- Supprimer des clients
- Afficher la liste de tous les clients

### Gestion du personnel
- Enregistrer de nouveaux membres du personnel avec leur poste et salaire
- Modifier les informations du personnel
- Supprimer des membres du personnel
- Consulter la liste du personnel

### Gestion des commandes
- Enregistrer de nouvelles commandes
- Ajouter des plats à une commande
- Calculer automatiquement le montant total
- Consulter l'historique des commandes et leurs détails

## Architecture technique

### Technologies utilisées
- Java (application console)
- PostgreSQL (base de données)
- JDBC (connexion à la base de données)

### Structure du projet
- Modèle de conception DAO (Data Access Object)
- Classes d'entités (Personne, Client, Personnel, Plat, Commande)
- Classes DAO pour l'accès aux données
- Classes de gestion pour l'interface utilisateur

## Installation et configuration

### Prérequis
- Java Development Kit (JDK) 8 ou supérieur
- PostgreSQL 10 ou supérieur
- Driver JDBC PostgreSQL

### Configuration de la base de données
1. Créer une base de données PostgreSQL nommée `cantine`
2. Exécuter le script SQL fourni pour créer les tables nécessaires
3. Configurer les identifiants de connexion dans `DatabaseConnection.java`

### Compilation et exécution
1. Compiler le projet: `javac -cp "lib/*" -d bin src/*.java`
2. Exécuter l'application: `java -cp "bin;lib/*" Main`

## Modèle de données

### Tables
- `Personne`: Information de base (id, nom, prénom)
- `ClientRestau`: Informations des clients (hérite de Personne)
- `PersonnelRestau`: Informations du personnel (hérite de Personne, poste, salaire)
- `PlatRestau`: Détails des plats (id, nom, prix)
- `CommandeRestau`: Informations des commandes (id, date, total, client)
- `CommandePlats`: Relation entre commandes et plats (commande_id, plat_id, quantité, prix_unitaire)

### Relations
- Héritage entre Personne et ClientRestau/PersonnelRestau
- Relation Many-to-One entre CommandeRestau et ClientRestau
- Relation Many-to-Many entre CommandeRestau et PlatRestau via CommandePlats

## Authentification

Pour accéder au système, utilisez:
- **Nom d'utilisateur**: admin
- **Mot de passe**: admin

## Auteurs

Yohann - noushiFlex
