import java.util.ArrayList;
import java.util.Scanner;

public class GestionDesCommandes {
    static ArrayList<CommandeRestau> commandes = new ArrayList<>();
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
                    passerCommande();
                    break;
                case 2:
                    afficherCommandes();
                    break;
                case 3:
                    afficherCommandeDetail();
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
        System.out.println("\n======== Menu Gestion des Commandes ========");
        System.out.println("1: Passer une commande");
        System.out.println("2: Afficher toutes les commandes");
        System.out.println("3: Afficher une commande spécifique");
        System.out.println("0: Quitter");
        System.out.println("===========================================");
    }

    public static void passerCommande() {
        CommandeRestau commande = new CommandeRestau();
        int continuer = 1;
        while (continuer == 1) {
            System.out.print("Entrez l'ID du plat à ajouter à la commande : ");
            int platId = scanner.nextInt();
            scanner.nextLine(); // Consommer la ligne

            // Recherche du plat par son ID
            PlatRestau plat = trouverPlatParId(platId);
            if (plat != null) {
                commande.ajouterPlat(plat);
                System.out.println("✅ Plat ajouté à la commande !");
            } else {
                System.out.println("❌ Plat non trouvé !");
            }

            System.out.print("Voulez-vous ajouter un autre plat ? (1 = Oui, 0 = Non) : ");
            continuer = scanner.nextInt();
            scanner.nextLine(); // Consommer la ligne
        }

        commandes.add(commande);
        System.out.println("✅ Commande passée avec succès !");
        commande.afficherCommande();
    }

    public static void afficherCommandes() {
        if (commandes.isEmpty()) {
            System.out.println("❌ Aucun plat commandé.");
            return;
        }
        System.out.println("\n📋 Liste des commandes :");
        for (int i = 0; i < commandes.size(); i++) {
            System.out.println("Commande " + (i + 1) + " - Total : " + commandes.get(i).getTotal() + " FCFA");
        }
    }

    public static void afficherCommandeDetail() {
        System.out.print("Entrez le numéro de la commande : ");
        int numCommande = scanner.nextInt() - 1;
        if (numCommande >= 0 && numCommande < commandes.size()) {
            CommandeRestau commande = commandes.get(numCommande);
            commande.afficherCommande();
        } else {
            System.out.println("❌ Commande non trouvée !");
        }
    }

    // Méthode pour trouver un plat par ID (à ajouter dans GestionDesPlats par exemple)
    public static PlatRestau trouverPlatParId(int id) {
        // Supposons qu'on a une liste globale des plats disponibles (plutôt que de recréer une base de données)
        ArrayList<PlatRestau> platsDisponibles = new ArrayList<>();
        platsDisponibles.add(new PlatRestau(1, "Poulet rôti", 5000));
        platsDisponibles.add(new PlatRestau(2, "Poisson braisé", 4500));
        platsDisponibles.add(new PlatRestau(3, "Frites maison", 2500));

        for (PlatRestau plat : platsDisponibles) {
            if (plat.getId() == id) {
                return plat;
            }
        }
        return null;
    }
}
