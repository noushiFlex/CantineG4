import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlatDAO {

    /**
     * Ajoute un nouveau plat dans la base de données
     */
    public static boolean ajouterPlat(PlatRestau plat) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DatabaseConnection.getConnection();

            String sql = "INSERT INTO PlatRestau (id, nom, prix) VALUES (?, ?, ?)";

            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, plat.getId());
            pstmt.setString(2, plat.getNom());
            pstmt.setDouble(3, plat.getPrix());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout du plat : " + e.getMessage());
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
     * Récupère tous les plats de la base de données
     */
    public static List<PlatRestau> getAllPlats() {
        List<PlatRestau> plats = new ArrayList<>();
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            connection = DatabaseConnection.getConnection();

            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT id, nom, prix FROM PlatRestau ORDER BY id");

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                double prix = rs.getDouble("prix");

                PlatRestau plat = new PlatRestau(id, nom, prix);
                plats.add(plat);
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des plats : " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("❌ Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }

        return plats;
    }

    /**
     * Récupère un plat par son ID
     */
    public static PlatRestau getPlatById(int id) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PlatRestau plat = null;

        try {
            connection = DatabaseConnection.getConnection();

            String sql = "SELECT id, nom, prix FROM PlatRestau WHERE id = ?";

            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String nom = rs.getString("nom");
                double prix = rs.getDouble("prix");

                plat = new PlatRestau(id, nom, prix);
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération du plat : " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("❌ Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }

        return plat;
    }

    /**
     * Met à jour les informations d'un plat
     */
    public static boolean updatePlat(PlatRestau plat) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DatabaseConnection.getConnection();

            String sql = "UPDATE PlatRestau SET nom = ?, prix = ? WHERE id = ?";

            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, plat.getNom());
            pstmt.setDouble(2, plat.getPrix());
            pstmt.setInt(3, plat.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la mise à jour du plat : " + e.getMessage());
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
     * Supprime un plat de la base de données
     */
    public static boolean deletePlat(int id) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DatabaseConnection.getConnection();

            String sql = "DELETE FROM PlatRestau WHERE id = ?";

            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la suppression du plat : " + e.getMessage());
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
     * Obtient le dernier ID de plat dans la base de données
     */
    public static int getLastPlatId() {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        int lastId = 0;

        try {
            connection = DatabaseConnection.getConnection();

            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT MAX(id) as max_id FROM PlatRestau");

            if (rs.next()) {
                lastId = rs.getInt("max_id");
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération du dernier ID : " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("❌ Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }

        return lastId;
    }
}