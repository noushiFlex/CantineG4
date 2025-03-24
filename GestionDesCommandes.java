import java.util.List;
import java.util.Scanner;

public class GestionDesCommandes {
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
        // Afficher la liste des clients
        List<ClientRestau> clients = ClientDAO.getAllClients();
        if (clients.isEmpty()) {
            System.out.println("❌ Aucun client n'est enregistré. Impossible de passer une commande.");
            return;
        }
        
        System.out.println("\n📋 Choisissez un client :");
        for (ClientRestau client : clients) {
            client.afficher();
        }
        
        System.out.print("Entrez l'ID du client qui passe la commande : ");
        int clientId = scanner.nextInt();
        scanner.nextLine(); // Consommer la ligne
        
        ClientRestau client = ClientDAO.getClientById(clientId);
        if (client == null) {
            System.out.println("❌ Client non trouvé ! Commande annulée.");
            return;
        }
        
        CommandeRestau commande = new CommandeRestau();
        int continuer = 1;
        
        // Afficher la liste des plats disponibles
        List<PlatRestau> platsDisponibles = PlatDAO.getAllPlats();
        if (platsDisponibles.isEmpty()) {
            System.out.println("❌ Aucun plat n'est disponible. Impossible de passer une commande.");
            return;
        }
        
        System.out.println("\n📋 Plats disponibles :");
        for (PlatRestau plat : platsDisponibles) {
            System.out.println(plat);
        }
        
        while (continuer == 1) {
            System.out.print("Entrez l'ID du plat à ajouter à la commande : ");
            int platId = scanner.nextInt();
            scanner.nextLine(); // Consommer la ligne

            // Recherche du plat par son ID
            PlatRestau plat = PlatDAO.getPlatById(platId);
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

        if (commande.getPlatsCommandes().isEmpty()) {
            System.out.println("❌ Aucun plat n'a été ajouté à la commande. Commande annulée.");
            return;
        }
        
        // Ajouter la commande à la base de données
        int commandeId = CommandeDAO.ajouterCommande(commande, clientId);
        
        if (commandeId > 0) {
            System.out.println("✅ Commande passée avec succès ! ID: " + commandeId);
            commande.afficherCommande();
        } else {
            System.out.println("❌ Erreur lors de l'enregistrement de la commande.");
        }
    }

    public static void afficherCommandes() {
        List<CommandeDAO.CommandeInfo> commandes = CommandeDAO.getAllCommandes();

        if (commandes.isEmpty()) {
            System.out.println("❌ Aucune commande enregistrée.");
            return;
        }

        System.out.println("\n📋 Liste des commandes :");
        for (CommandeDAO.CommandeInfo commande : commandes) {
            System.out.println(commande);
        }
    }

    public static void afficherCommandeDetail() {
        System.out.print("Entrez l'ID de la commande : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consommer la ligne
        
        CommandeDAO.CommandeDetail commande = CommandeDAO.getCommandeById(id);
        
        if (commande != null) {
            commande.afficherDetail();
        } else {
            System.out.println("❌ Commande non trouvée !");
        }
    }
}
