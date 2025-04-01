import java.util.ArrayList;
import java.util.Scanner;

public class GestionDesPlats {
    static ArrayList<PlatRestau> plats = new ArrayList<>();

    public static void main() {
        Scanner scanner = new Scanner(System.in);
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
                    ajouterPlat(scanner);
                    break;
                case 2:
                    supprimerPlat(scanner);
                    break;
                case 3:
                    modifierPlat(scanner);
                    break;
                case 4:
                    listerPlats();
                    break;
                case 5:
                    dernierPlat();
                    break;
                case 0:
                    System.out.println("Retour au menu principal.");
                    break;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        } while (choix != 0);

        scanner.close();
    }

    public static void afficherMenu() {
        System.out.println("\n======== Menu Gestion des Plats ========");
        System.out.println("1: Ajouter un plat");
        System.out.println("2: Supprimer un plat");
        System.out.println("3: Modifier un plat");
        System.out.println("4: Lister les plats");
        System.out.println("5: Voir le dernier plat ajouté");
        System.out.println("0: Quitter");
        System.out.println("=======================================");
    }

    public static void ajouterPlat(Scanner scanner) {
        System.out.print("Entrez le nom du plat : ");
        String nom = scanner.nextLine();
        System.out.print("Entrez le prix du plat (en FCFA) : ");
        double prix = scanner.nextDouble();
        scanner.nextLine(); // Consommer la nouvelle ligne

        int id = plats.size() + 1; // Générer un ID unique
        PlatRestau plat = new PlatRestau(id, nom, prix);
        plats.add(plat);
        System.out.println("✅ Plat ajouté avec succès !");
    }

    public static void supprimerPlat(Scanner scanner) {
        if (plats.isEmpty()) {
            System.out.println("❌ Aucun plat à supprimer.");
            return;
        }
        listerPlats();
        System.out.print("Entrez l'index du plat à supprimer : ");
        int index = scanner.nextInt();
        if (index >= 0 && index < plats.size()) {
            plats.remove(index);
            System.out.println("✅ Plat supprimé !");
        } else {
            System.out.println("❌ Index invalide.");
        }
    }

    public static void modifierPlat(Scanner scanner) {
        if (plats.isEmpty()) {
            System.out.println("❌ Aucun plat à modifier.");
            return;
        }
        listerPlats();
        System.out.print("Entrez l'index du plat à modifier : ");
        int index = scanner.nextInt();
        scanner.nextLine();
        if (index >= 0 && index < plats.size()) {
            System.out.print("Entrez le nouveau nom : ");
            String nom = scanner.nextLine();
            System.out.print("Entrez le nouveau prix : ");
            double prix = scanner.nextDouble();

            PlatRestau plat = plats.get(index);
            plats.set(index, new PlatRestau(plat.getId(), nom, prix));
            System.out.println("✅ Plat modifié !");
        } else {
            System.out.println("❌ Index invalide.");
        }
    }

    public static void listerPlats() {
        if (plats.isEmpty()) {
            System.out.println("❌ Aucun plat enregistré.");
            return;
        }
        System.out.println("\n📋 Liste des plats :");
        for (int i = 0; i < plats.size(); i++) {
            System.out.print(i + " - ");
            System.out.println(plats.get(i));
        }
    }

    public static void dernierPlat() {
        if (plats.isEmpty()) {
            System.out.println("❌ Aucun plat ajouté.");
            return;
        }
        System.out.println("\n📌 Dernier plat ajouté :");
        System.out.println(plats.get(plats.size() - 1));
    }
}
