import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<Plat> plats;

    public Menu() {
        this.plats = new ArrayList<>();
    }

    // Ajouter un plat au menu
    public void ajouterPlat(Plat plat) {
        plats.add(plat);
    }

    // Afficher le menu
    public void afficherMenu() {
        System.out.println("\n---- Menu de la Cantine ----");
        for (Plat plat : plats) {
            System.out.println(plat);
        }
    }

    // Trouver un plat par ID
    public Plat getPlatParId(int id) {
        for (Plat plat : plats) {
            if (plat.getId() == id) {
                return plat;
            }
        }
        return null;
    }
}
