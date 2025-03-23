import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Configuration de la connexion à la base de données
    private static final String URL = "jdbc:postgresql://localhost:5432/cantine";
    private static final String USER = "cantine";
    private static final String PASSWORD = "cantine";

    /**
     * Établit une connexion à la base de données
     * @return Connection - l'objet connexion ou null en cas d'échec
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Chargement du driver JDBC (optionnel avec les versions récentes de Java)
            Class.forName("org.postgresql.Driver");

            // Établissement de la connexion
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connexion réussie !");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Driver PostgreSQL introuvable : " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ Erreur de connexion à la base de données : " + e.getMessage());
        }
        return connection;
    }

    /**
     * Ferme la connexion de manière sécurisée
     * @param connection - la connexion à fermer
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("✅ Connexion fermée avec succès");
            } catch (SQLException e) {
                System.out.println("❌ Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }
}