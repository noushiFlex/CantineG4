public class Personnel extends Personne {
    private String poste;
    private double salaire;

    // Constructeur
    public Personnel(int id, String nom, String prenom, String poste, double salaire) {
        super(id, nom, prenom);
        this.poste = poste;
        this.salaire = salaire;
    }

    // Getters
    public String getPoste() {
        return poste;
    }

    public double getSalaire() {
        return salaire;
    }

    // Setters
    public void setPoste(String poste) {
        this.poste = poste;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    // Affichage des informations
    @Override
    public String toString() {
        return super.toString() + ", Poste: " + poste + ", Salaire: " + salaire + " FCFA";
    }

    public void afficher() {
        System.out.println(toString());
    }
}
