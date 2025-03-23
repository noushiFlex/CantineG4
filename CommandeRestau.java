import java.util.ArrayList;
import java.util.List;

public class CommandeRestau {
    private List<PlatRestau> platsCommandes;
    private double total;

    public CommandeRestau() {
        this.platsCommandes = new ArrayList<>();
        this.total = 0;
    }

    // Ajouter un plat à la commande
    public void ajouterPlat(PlatRestau platRestau) {
        platsCommandes.add(platRestau);
        total += platRestau.getPrix();
    }

    // Afficher la commande
    public void afficherCommande() {
        System.out.println("\n---- Votre Commande ----");
        for (PlatRestau platRestau : platsCommandes) {
            System.out.println(platRestau);
        }
        System.out.println("Total à payer: " + total + " FCFA");
    }

    public double getTotal() {
        return total;
    }
}
