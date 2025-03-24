import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonnelDAO {

    /**
     * Ajoute un nouveau personnel dans la base de données
     */
    public static boolean ajouterPersonnel(PersonnelRestau personnel) {
        Connection connection = null;
        PreparedStatement pstmtPersonne = null;
        PreparedStatement pstmtPersonnel = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false); // Début de la transaction

            // 1. Insérer dans la table Personne
            String sqlPersonne = "INSERT INTO Personne (id, nom, prenom) VALUES (?, ?, ?)";
            pstmtPersonne = connection.prepareStatement(sqlPersonne);
            pstmtPersonne.setInt(1, personnel.getId());
            pstmtPersonne.setString(2, personnel.getNom());
            pstmtPersonne.setString(3, personnel.getPrenom());
            pstmtPersonne.executeUpdate();

            // 2. Insérer dans la table PersonnelRestau
            String sqlPersonnel = "INSERT INTO PersonnelRestau (id, poste, salaire) VALUES (?, ?, ?)";
            pstmtPersonnel = connection.prepareStatement(sqlPersonnel);
            pstmtPersonnel.setInt(1, personnel.getId());
            pstmtPersonnel.setString(2, personnel.getPoste());
            pstmtPersonnel.setDouble(3, personnel.getSalaire());
            pstmtPersonnel.executeUpdate();

            connection.commit(); // Valider la transaction
            System.out.println("✅ Personnel ajouté avec succès dans la base de données !");
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout du personnel : " + e.getMessage());
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
                if (pstmtPersonnel != null) pstmtPersonnel.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("❌ Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }
    }

    /**
     * Récupère tous les personnels de la base de données
     */
    public static List<PersonnelRestau> getAllPersonnels() {
        List<PersonnelRestau> personnels = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = DatabaseConnection.getConnection();

            String sql = "SELECT p.id, p.nom, p.prenom, pr.poste, pr.salaire FROM Personne p " +
                    "JOIN PersonnelRestau pr ON p.id = pr.id";

            pstmt = connection.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String poste = rs.getString("poste");
                double salaire = rs.getDouble("salaire");

                PersonnelRestau personnel = new PersonnelRestau(id, nom, prenom, poste, salaire);
                personnels.add(personnel);
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des personnels : " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("❌ Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }

        return personnels;
    }

    /**
     * Récupère un personnel par son ID
     */
    public static PersonnelRestau getPersonnelById(int id) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PersonnelRestau personnel = null;

        try {
            connection = DatabaseConnection.getConnection();

            String sql = "SELECT p.id, p.nom, p.prenom, pr.poste, pr.salaire FROM Personne p " +
                    "JOIN PersonnelRestau pr ON p.id = pr.id " +
                    "WHERE p.id = ?";

            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String poste = rs.getString("poste");
                double salaire = rs.getDouble("salaire");

                personnel = new PersonnelRestau(id, nom, prenom, poste, salaire);
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération du personnel : " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("❌ Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }

        return personnel;
    }

    /**
     * Met à jour les informations d'un personnel
     */
    public static boolean updatePersonnel(PersonnelRestau personnel) {
        Connection connection = null;
        PreparedStatement pstmtPersonne = null;
        PreparedStatement pstmtPersonnel = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            // 1. Mettre à jour la table Personne
            String sqlPersonne = "UPDATE Personne SET nom = ?, prenom = ? WHERE id = ?";
            pstmtPersonne = connection.prepareStatement(sqlPersonne);
            pstmtPersonne.setString(1, personnel.getNom());
            pstmtPersonne.setString(2, personnel.getPrenom());
            pstmtPersonne.setInt(3, personnel.getId());
            pstmtPersonne.executeUpdate();

            // 2. Mettre à jour la table PersonnelRestau
            String sqlPersonnel = "UPDATE PersonnelRestau SET poste = ?, salaire = ? WHERE id = ?";
            pstmtPersonnel = connection.prepareStatement(sqlPersonnel);
            pstmtPersonnel.setString(1, personnel.getPoste());
            pstmtPersonnel.setDouble(2, personnel.getSalaire());
            pstmtPersonnel.setInt(3, personnel.getId());
            pstmtPersonnel.executeUpdate();

            connection.commit();
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la mise à jour du personnel : " + e.getMessage());
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    System.out.println("❌ Erreur lors du rollback : " + ex.getMessage());
                }
            }
            return false;
        } finally {
            try {
                if (pstmtPersonne != null) pstmtPersonne.close();
                if (pstmtPersonnel != null) pstmtPersonnel.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("❌ Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }
    }

    /**
     * Supprime un personnel de la base de données
     */
    public static boolean deletePersonnel(int id) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DatabaseConnection.getConnection();

            // Suppression de la personne (cascade supprimera le personnel)
            String sql = "DELETE FROM Personne WHERE id = ?";

            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la suppression du personnel : " + e.getMessage());
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