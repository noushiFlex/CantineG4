import java.util.List;
import java.util.Scanner;

public class GestionDesPlats {

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

        int id = PlatDAO.getLastPlatId() + 1; // Générer un ID basé sur le dernier ID + 1
        PlatRestau plat = new PlatRestau(id, nom, prix);

        boolean success = PlatDAO.ajouterPlat(plat);
        if (success) {
            System.out.println("✅ Plat ajouté avec succès dans la base de données !");
        } else {
            System.out.println("❌ Erreur lors de l'ajout du plat.");
        }
    }

    public static void supprimerPlat(Scanner scanner) {
        listerPlats();
        System.out.print("Entrez l'ID du plat à supprimer : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consommer la ligne

        PlatRestau plat = PlatDAO.getPlatById(id);
        if (plat == null) {
            System.out.println("❌ Plat non trouvé !");
            return;
        }

        System.out.print("Êtes-vous sûr de vouloir supprimer ce plat ? (O/N) : ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("O")) {
            boolean success = PlatDAO.deletePlat(id);
            if (success) {
                System.out.println("✅ Plat supprimé avec succès !");
            } else {
                System.out.println("❌ Erreur lors de la suppression du plat.");
            }
        } else {
            System.out.println("Suppression annulée.");
        }
    }

    public static void modifierPlat(Scanner scanner) {
        listerPlats();
        System.out.print("Entrez l'ID du plat à modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consommer la ligne

        PlatRestau plat = PlatDAO.getPlatById(id);
        if (plat == null) {
            System.out.println("❌ Plat non trouvé !");
            return;
        }

        System.out.print("Entrez le nouveau nom (" + plat.getNom() + ") : ");
        String nom = scanner.nextLine();
        if (!nom.isEmpty()) plat.setNom(nom);

        System.out.print("Entrez le nouveau prix (" + plat.getPrix() + ") : ");
        String prixStr = scanner.nextLine();
        if (!prixStr.isEmpty()) {
            try {
                double prix = Double.parseDouble(prixStr);
                plat.setPrix(prix);
            } catch (NumberFormatException e) {
                System.out.println("❌ Prix invalide, l'ancien prix sera conservé.");
            }
        }

        boolean success = PlatDAO.updatePlat(plat);
        if (success) {
            System.out.println("✅ Plat modifié avec succès !");
        } else {
            System.out.println("❌ Erreur lors de la modification du plat.");
        }
    }

    public static void listerPlats() {
        List<PlatRestau> plats = PlatDAO.getAllPlats();

        if (plats.isEmpty()) {
            System.out.println("❌ Aucun plat enregistré.");
            return;
        }

        System.out.println("\n📋 Liste des plats :");
        for (PlatRestau plat : plats) {
            System.out.println(plat);
        }
    }

    public static void dernierPlat() {
        int lastId = PlatDAO.getLastPlatId();
        if (lastId == 0) {
            System.out.println("❌ Aucun plat ajouté.");
            return;
        }

        PlatRestau plat = PlatDAO.getPlatById(lastId);
        if (plat != null) {
            System.out.println("\n📌 Dernier plat ajouté :");
            System.out.println(plat);
        } else {
            System.out.println("❌ Impossible de récupérer le dernier plat.");
        }
    }
}