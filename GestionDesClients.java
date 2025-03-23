import java.util.ArrayList;
import java.util.Scanner;

public class GestionDesClients {
    static ArrayList<ClientRestau> clients = new ArrayList<>();
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
        clients.add(client);
        System.out.println("✅ Client ajouté avec succès !");
    }

    public static void afficherClients() {
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
        ClientRestau client = trouverClientParId(id);
        if (client != null) {
            client.afficher();
        } else {
            System.out.println("❌ Client non trouvé !");
        }
    }

    public static ClientRestau trouverClientParId(int id) {
        for (ClientRestau client : clients) {
            if (client.getId() == id) {
                return client;
            }
        }
        return null;
    }
}
