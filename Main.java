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
        if (nomUtilisateur.equals("admin") && motDePasse.equals("1234")) {
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
            System.out.println("1. Afficher les plats 🍽️");
            System.out.println("2. Voir les commandes 🛒");
            System.out.println("3. Voir les clients 👥");
            System.out.println("4. Voir le personnel 🧑‍🍳");
            System.out.println("5. Quitter ❌");
            System.out.print("👉 Choisissez une option: ");
            int choix = scanner.nextInt();

            switch (choix) {
                case 1:
                    System.out.println("\n📜 Voici les plats disponibles...");
                    break;
                case 2:
                    System.out.println("\n🛒 Voici la liste des commandes...");
                    break;
                case 3:
                    System.out.println("\n👥 Liste des clients...");
                    break;
                case 4:
                    System.out.println("\n🧑‍🍳 Liste du personnel...");
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
