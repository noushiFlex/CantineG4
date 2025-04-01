import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Demande des identifiants
        System.out.println("\n===== CONNEXION =====");
        System.out.print("👤 Nom d'utilisateur: ");
        String nomUtilisateur = scanner.nextLine();

        System.out.print("🔑 Mot de passe: ");
        String motDePasse = scanner.nextLine();

        // Vérification des identifiants (simple)
        if (nomUtilisateur.equals("admin") && motDePasse.equals("admin")) {
            System.out.println("\n✅ Connexion réussie !");
            afficherMenu(scanner);
        } else {
            System.out.println("\n❌ Nom d'utilisateur ou mot de passe incorrect !");
        }

        scanner.close();
    }

    public static void afficherMenu(Scanner scanner) {
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n===== MENU DE LA CANTINE =====");
            System.out.println("1. Afficher et gérer les plats 🍽️");
            System.out.println("2. Voir les commandes 🛒");
            System.out.println("3. Voir les clients 👥");
            System.out.println("4. Voir le personnel 🧑‍🍳");
            System.out.println("5. Quitter ❌");
            System.out.print("👉 Choisissez une option: ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la ligne

            switch (choix) {
                case 1:
                    // Appel de la gestion des plats
                    GestionDesPlats.main();
                    break;
                case 2:
                    GestionDesCommandes.main();
                    break;
                case 3:
                    GestionDesClients.main();
                    break;
                case 4:
                    GestionDesPersonnels.main();
                    break;
                case 5:
                    System.out.println("\n👋 Merci d'avoir utilisé la cantine ! À bientôt !");
                    continuer = false;
                    break;
                default:
                    System.out.println("\n❌ Option invalide ! Veuillez choisir un nombre entre 1 et 5.");
            }
        }
    }
}
