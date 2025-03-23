import java.util.ArrayList;
import java.util.List;

public class Commande {
    private List<Plat> platsCommandes;
    private double total;

    public Commande() {
        this.platsCommandes = new ArrayList<>();
        this.total = 0;
    }

    // Ajouter un plat à la commande
    public void ajouterPlat(Plat plat) {
        platsCommandes.add(plat);
        total += plat.getPrix();
    }

    // Afficher la commande
    public void afficherCommande() {
        System.out.println("\n---- Votre Commande ----");
        for (Plat plat : platsCommandes) {
            System.out.println(plat);
        }
        System.out.println("Total à payer: " + total + " FCFA");
    }

    public double getTotal() {
        return total;
    }
}
