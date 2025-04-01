import java.util.List;
import java.util.Scanner;

public class GestionDesClients {
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
                    ajouterClient();
                    break;
                case 2:
                    afficherClients();
                    break;
                case 3:
                    afficherClientDetail();
                    break;
                case 4:
                    modifierClient();
                    break;
                case 5:
                    supprimerClient();
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
        System.out.println("\n======== Menu Gestion des Clients ========");
        System.out.println("1: Ajouter un client");
        System.out.println("2: Afficher tous les clients");
        System.out.println("3: Afficher un client spécifique");
        System.out.println("4: Modifier un client");
        System.out.println("5: Supprimer un client");
        System.out.println("0: Quitter");
        System.out.println("===========================================");
    }

    public static void ajouterClient() {
        System.out.print("Entrez l'ID du client : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consommer la ligne
        System.out.print("Entrez le nom : ");
        String nom = scanner.nextLine();
        System.out.print("Entrez le prénom : ");
        String prenom = scanner.nextLine();

        ClientRestau client = new ClientRestau(id, nom, prenom);
        boolean success = ClientDAO.ajouterClient(client);

        if (success) {
            System.out.println("✅ Client ajouté avec succès dans la base de données !");
        } else {
            System.out.println("❌ Erreur lors de l'ajout du client.");
        }
    }

    public static void afficherClients() {
        List<ClientRestau> clients = ClientDAO.getAllClients();

        if (clients.isEmpty()) {
            System.out.println("❌ Aucun client enregistré.");
            return;
        }

        System.out.println("\n📋 Liste des clients :");
        for (ClientRestau client : clients) {
            client.afficher();
        }
    }

    public static void afficherClientDetail() {
        System.out.print("Entrez l'ID du client : ");
        int id = scanner.nextInt();
        ClientRestau client = ClientDAO.getClientById(id);

        if (client != null) {
            client.afficher();
        } else {
            System.out.println("❌ Client non trouvé !");
        }
    }

    public static void modifierClient() {
        System.out.print("Entrez l'ID du client à modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consommer la ligne

        ClientRestau client = ClientDAO.getClientById(id);
        if (client == null) {
            System.out.println("❌ Client non trouvé !");
            return;
        }

        System.out.print("Entrez le nouveau nom (" + client.getNom() + ") : ");
        String nom = scanner.nextLine();
        if (!nom.isEmpty()) client.setNom(nom);

        System.out.print("Entrez le nouveau prénom (" + client.getPrenom() + ") : ");
        String prenom = scanner.nextLine();
        if (!prenom.isEmpty()) client.setPrenom(prenom);

        boolean success = ClientDAO.updateClient(client);

        if (success) {
            System.out.println("✅ Client modifié avec succès !");
        } else {
            System.out.println("❌ Erreur lors de la modification du client.");
        }
    }

    public static void supprimerClient() {
        System.out.print("Entrez l'ID du client à supprimer : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consommer la ligne

        ClientRestau client = ClientDAO.getClientById(id);
        if (client == null) {
            System.out.println("❌ Client non trouvé !");
            return;
        }

        System.out.print("Êtes-vous sûr de vouloir supprimer ce client ? (O/N) : ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("O")) {
            boolean success = ClientDAO.deleteClient(id);

            if (success) {
                System.out.println("✅ Client supprimé avec succès !");
            } else {
                System.out.println("❌ Erreur lors de la suppression du client.");
            }
        } else {
            System.out.println("Suppression annulée.");
        }
    }
}