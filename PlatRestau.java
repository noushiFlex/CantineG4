public class PlatRestau {
    private int id;
    private String nom;
    private double prix;

    // Constructeur
    public PlatRestau(int id, String nom, double prix) {
        this.id = id;
        this.nom = nom;
        setPrix(prix); // Utilisation du setter pour valider le prix
    }

    // Getter pour id
    public int getId() {
        return id;
    }

    // Getter pour nom
    public String getNom() {
        return nom;
    }

    // Getter pour prix
    public double getPrix() {
        return prix;
    }

    // Setter pour prix avec validation
    public void setPrix(double prix) {
        if (prix < 0) {
            System.out.println("❌ Le prix ne peut pas être négatif.");
        } else {
            this.prix = prix;
        }
    }

    // Setter pour nom
    public void setNom(String nom) {
        this.nom = nom;
    }

    // Méthode toString pour afficher un plat
    @Override
    public String toString() {
        return id + ". " + nom + " - " + prix + " FCFA";
    }
}
