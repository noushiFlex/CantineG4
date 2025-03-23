public class Client extends Personne {

    // Constructeur qui appelle celui de Personne
    public Client(int id, String nom, String prenom) {
        super(id, nom, prenom);
    }

    // Méthode pour afficher les informations du client
    public void afficher() {
        System.out.println("Client: " + toString());
    }
}
