import java.util.ArrayList;
import java.util.Scanner;

public class GestionDesPersonnels {
    static ArrayList<PersonnelRestau> personnels = new ArrayList<>();
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
        personnels.add(personnel);
        System.out.println("✅ Personnel ajouté avec succès !");
    }

    public static void afficherPersonnels() {
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
        PersonnelRestau personnel = trouverPersonnelParId(id);
        if (personnel != null) {
            personnel.afficher();
        } else {
            System.out.println("❌ Personnel non trouvé !");
        }
    }

    public static PersonnelRestau trouverPersonnelParId(int id) {
        for (PersonnelRestau personnel : personnels) {
            if (personnel.getId() == id) {
                return personnel;
            }
        }
        return null;
    }
}
