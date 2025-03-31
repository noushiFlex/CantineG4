import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    /**
     * Ajoute un nouveau client dans la base de données
     */
    public static boolean ajouterClient(ClientRestau client) {
        Connection connection = null;
        PreparedStatement pstmtPersonne = null;
        PreparedStatement pstmtClient = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false); // Début de la transaction

            // 1. Insérer dans la table Personne
            String sqlPersonne = "INSERT INTO Personne (id, nom, prenom) VALUES (?, ?, ?)";
            pstmtPersonne = connection.prepareStatement(sqlPersonne);
            pstmtPersonne.setInt(1, client.getId());
            pstmtPersonne.setString(2, client.getNom());
            pstmtPersonne.setString(3, client.getPrenom());
            pstmtPersonne.executeUpdate();

            // 2. Insérer dans la table ClientRestau
            String sqlClient = "INSERT INTO ClientRestau (id) VALUES (?)";
            pstmtClient = connection.prepareStatement(sqlClient);
            pstmtClient.setInt(1, client.getId());
            pstmtClient.executeUpdate();

            connection.commit(); // Valider la transaction
            System.out.println("✅ Client ajouté avec succès dans la base de données !");
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout du client : " + e.getMessage());
            if (connection != null) {
                try {
                    connection.rollback(); // Annuler la transaction en cas d'erreur
                } catch (SQLException ex) {
                    System.out.println("❌ Erreur lors du rollback : " + ex.getMessage());
                }
            }
            return false;
        } finally {
            try {
                if (pstmtPersonne != null) pstmtPersonne.close();
                if (pstmtClient != null) pstmtClient.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("❌ Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }
    }

    /**
     * Récupère tous les clients de la base de données
     */
    public static List<ClientRestau> getAllClients() {
        List<ClientRestau> clients = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = DatabaseConnection.getConnection();

            String sql = "SELECT p.id, p.nom, p.prenom FROM Personne p " +
                    "JOIN ClientRestau c ON p.id = c.id";

            pstmt = connection.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");

                ClientRestau client = new ClientRestau(id, nom, prenom);
                clients.add(client);
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des clients : " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("❌ Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }

        return clients;
    }

    /**
     * Récupère un client par son ID
     */
    public static ClientRestau getClientById(int id) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ClientRestau client = null;

        try {
            connection = DatabaseConnection.getConnection();

            String sql = "SELECT p.id, p.nom, p.prenom FROM Personne p " +
                    "JOIN ClientRestau c ON p.id = c.id " +
                    "WHERE p.id = ?";

            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");

                client = new ClientRestau(id, nom, prenom);
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération du client : " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("❌ Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }

        return client;
    }

    /**
     * Met à jour les informations d'un client
     */
    public static boolean updateClient(ClientRestau client) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DatabaseConnection.getConnection();

            String sql = "UPDATE Personne SET nom = ?, prenom = ? WHERE id = ?";

            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, client.getNom());
            pstmt.setString(2, client.getPrenom());
            pstmt.setInt(3, client.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la mise à jour du client : " + e.getMessage());
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("❌ Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }
    }

    /**
     * Supprime un client de la base de données
     */
    public static boolean deleteClient(int id) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DatabaseConnection.getConnection();

            // Suppression de la personne (cascade supprimera le client)
            String sql = "DELETE FROM Personne WHERE id = ?";

            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la suppression du client : " + e.getMessage());
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("❌ Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }
    }
}