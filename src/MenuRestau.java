import java.util.ArrayList;
import java.util.List;

public class MenuRestau {
    private List<PlatRestau> platRestaus;

    public MenuRestau() {
        this.platRestaus = new ArrayList<>();
    }

    // Ajouter un plat au menu
    public void ajouterPlat(PlatRestau platRestau) {
        platRestaus.add(platRestau);
    }

    // Afficher le menu
    public void afficherMenu() {
        System.out.println("\n---- Menu de la Cantine ----");
        for (PlatRestau platRestau : platRestaus) {
            System.out.println(platRestau);
        }
    }

    // Trouver un plat par ID
    public PlatRestau getPlatParId(int id) {
        for (PlatRestau platRestau : platRestaus) {
            if (platRestau.getId() == id) {
                return platRestau;
            }
        }
        return null;
    }
}
