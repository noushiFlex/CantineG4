import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionDesPersonnels {
    static Scanner scanner = new Scanner(System.in);

    public static void main() {
        int choix;
        do {
            afficherMenu();
            System.out.print("Choisissez une option : ");
            while (!scanner.hasNextInt()) {
                System.out.println("Veuillez entrer un nombre valide !");
                scanner.next();
            }
            choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la ligne

            switch (choix) {
                case 1:
                    ajouterPersonnel();
                    break;
                case 2:
                    afficherPersonnels();
                    break;
                case 3:
                    afficherPersonnelDetail();
                    break;
                case 4:
                    modifierPersonnel();
                    break;
                case 5:
                    supprimerPersonnel();
                    break;
                case 0:
                    System.out.println("Retour au menu principal.");
                    break;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        } while (choix != 0);
    }

    public static void afficherMenu() {
        System.out.println("\n======== Menu Gestion du Personnel ========");
        System.out.println("1: Ajouter un personnel");
        System.out.println("2: Afficher tous les personnels");
        System.out.println("3: Afficher un personnel spécifique");
        System.out.println("4: Modifier un personnel");
        System.out.println("5: Supprimer un personnel");
        System.out.println("0: Quitter");
        System.out.println("===========================================");
    }

    public static void ajouterPersonnel() {
        System.out.print("Entrez l'ID du personnel : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consommer la ligne
        System.out.print("Entrez le nom : ");
        String nom = scanner.nextLine();
        System.out.print("Entrez le prénom : ");
        String prenom = scanner.nextLine();
        System.out.print("Entrez le poste : ");
        String poste = scanner.nextLine();
        System.out.print("Entrez le salaire : ");
        double salaire = scanner.nextDouble();

        PersonnelRestau personnel = new PersonnelRestau(id, nom, prenom, poste, salaire);
        boolean success = PersonnelDAO.ajouterPersonnel(personnel);
        
        if (success) {
            System.out.println("✅ Personnel ajouté avec succès dans la base de données !");
        } else {
            System.out.println("❌ Erreur lors de l'ajout du personnel.");
        }
    }

    public static void afficherPersonnels() {
        List<PersonnelRestau> personnels = PersonnelDAO.getAllPersonnels();

        if (personnels.isEmpty()) {
            System.out.println("❌ Aucun personnel enregistré.");
            return;
        }

        System.out.println("\n📋 Liste des personnels :");
        for (PersonnelRestau personnel : personnels) {
            personnel.afficher();
        }
    }

    public static void afficherPersonnelDetail() {
        System.out.print("Entrez l'ID du personnel : ");
        int id = scanner.nextInt();
        PersonnelRestau personnel = PersonnelDAO.getPersonnelById(id);
        
        if (personnel != null) {
            personnel.afficher();
        } else {
            System.out.println("❌ Personnel non trouvé !");
        }
    }
    
    public static void modifierPersonnel() {
        System.out.print("Entrez l'ID du personnel à modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consommer la ligne

        PersonnelRestau personnel = PersonnelDAO.getPersonnelById(id);
        if (personnel == null) {
            System.out.println("❌ Personnel non trouvé !");
            return;
        }

        System.out.print("Entrez le nouveau nom (" + personnel.getNom() + ") : ");
        String nom = scanner.nextLine();
        if (!nom.isEmpty()) personnel.setNom(nom);

        System.out.print("Entrez le nouveau prénom (" + personnel.getPrenom() + ") : ");
        String prenom = scanner.nextLine();
        if (!prenom.isEmpty()) personnel.setPrenom(prenom);
        
        System.out.print("Entrez le nouveau poste (" + personnel.getPoste() + ") : ");
        String poste = scanner.nextLine();
        if (!poste.isEmpty()) personnel.setPoste(poste);
        
        System.out.print("Entrez le nouveau salaire (" + personnel.getSalaire() + ") : ");
        String salaireStr = scanner.nextLine();
        if (!salaireStr.isEmpty()) {
            try {
                double salaire = Double.parseDouble(salaireStr);
                personnel.setSalaire(salaire);
            } catch (NumberFormatException e) {
                System.out.println("❌ Salaire invalide, l'ancien salaire sera conservé.");
            }
        }

        boolean success = PersonnelDAO.updatePersonnel(personnel);

        if (success) {
            System.out.println("✅ Personnel modifié avec succès !");
        } else {
            System.out.println("❌ Erreur lors de la modification du personnel.");
        }
    }

    public static void supprimerPersonnel() {
        System.out.print("Entrez l'ID du personnel à supprimer : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consommer la ligne

        PersonnelRestau personnel = PersonnelDAO.getPersonnelById(id);
        if (personnel == null) {
            System.out.println("❌ Personnel non trouvé !");
            return;
        }

        System.out.print("Êtes-vous sûr de vouloir supprimer ce personnel ? (O/N) : ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("O")) {
            boolean success = PersonnelDAO.deletePersonnel(id);

            if (success) {
                System.out.println("✅ Personnel supprimé avec succès !");
            } else {
                System.out.println("❌ Erreur lors de la suppression du personnel.");
            }
        } else {
            System.out.println("Suppression annulée.");
        }
    }
}
