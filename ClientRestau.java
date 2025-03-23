public class ClientRestau extends Personne {

    // Constructeur qui appelle celui de Personne
    public ClientRestau(int id, String nom, String prenom) {
        super(id, nom, prenom);
    }

    // Méthode pour afficher les informations du client
    public void afficher() {
        System.out.println("Client: " + toString());
    }
}
