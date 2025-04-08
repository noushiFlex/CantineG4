# Cantine Scolaire - Système de Gestion

Ce projet est un système de gestion de cantine scolaire développé en Java, disponible en deux versions :
- Une version console (branche actuelle)  
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

## 🔄 Autres versions

- **Console** : Version actuelle
- **Interface** : Version avec interface graphique JavaFX disponible sur la branche `interface`

## 🔗 Liens utiles

Dépôt principal avec les diagrammes UML : [https://github.com/noushiFlex/organisation-pauses](https://github.com/noushiFlex/organisation-pauses)