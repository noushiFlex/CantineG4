import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandeDAO {

    /**
     * Ajoute une nouvelle commande dans la base de données
     */
    public static int ajouterCommande(CommandeRestau commande, int clientId) {
        Connection connection = null;
        PreparedStatement pstmtCommande = null;
        PreparedStatement pstmtPlatCommande = null;
        ResultSet generatedKeys = null;
        int commandeId = -1;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false); // Début de la transaction

            // 1. Insérer la commande
            String sqlCommande = "INSERT INTO CommandeRestau (date_commande, total, client_id) VALUES (NOW(), ?, ?) RETURNING id";
            pstmtCommande = connection.prepareStatement(sqlCommande, Statement.RETURN_GENERATED_KEYS);
            pstmtCommande.setDouble(1, commande.getTotal());
            pstmtCommande.setInt(2, clientId);
            
            int affectedRows = pstmtCommande.executeUpdate();
            
            if (affectedRows > 0) {
                generatedKeys = pstmtCommande.getGeneratedKeys();
                if (generatedKeys.next()) {
                    commandeId = generatedKeys.getInt(1);
                    
                    // 2. Insérer les plats de la commande
                    String sqlPlatCommande = "INSERT INTO CommandePlats (commande_id, plat_id, quantite, prix_unitaire) VALUES (?, ?, 1, ?)";
                    pstmtPlatCommande = connection.prepareStatement(sqlPlatCommande);
                    
                    for (PlatRestau plat : commande.getPlatsCommandes()) {
                        pstmtPlatCommande.setInt(1, commandeId);
                        pstmtPlatCommande.setInt(2, plat.getId());
                        pstmtPlatCommande.setDouble(3, plat.getPrix());
                        pstmtPlatCommande.addBatch();
                    }
                    
                    pstmtPlatCommande.executeBatch();
                }
            }

            connection.commit(); // Valider la transaction
            System.out.println("✅ Commande ajoutée avec succès ! ID: " + commandeId);
            return commandeId;

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout de la commande : " + e.getMessage());
            if (connection != null) {
                try {
                    connection.rollback(); // Annuler la transaction en cas d'erreur
                } catch (SQLException ex) {
                    System.out.println("❌ Erreur lors du rollback : " + ex.getMessage());
                }
            }
            return -1;
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (pstmtCommande != null) pstmtCommande.close();
                if (pstmtPlatCommande != null) pstmtPlatCommande.close();
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("❌ Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }
    }

    /**
     * Récupère toutes les commandes de la base de données
     */
    public static List<CommandeInfo> getAllCommandes() {
        List<CommandeInfo> commandes = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = DatabaseConnection.getConnection();

            String sql = "SELECT c.id, c.date_commande, c.total, c.client_id, p.nom, p.prenom " +
                         "FROM CommandeRestau c " +
                         "JOIN ClientRestau cr ON c.client_id = cr.id " +
                         "JOIN Personne p ON cr.id = p.id " +
                         "ORDER BY c.date_commande DESC";

            pstmt = connection.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                Timestamp dateCommande = rs.getTimestamp("date_commande");
                double total = rs.getDouble("total");
                int clientId = rs.getInt("client_id");
                String clientNom = rs.getString("nom");
                String clientPrenom = rs.getString("prenom");
                
                CommandeInfo commandeInfo = new CommandeInfo(id, dateCommande, total, clientId, clientNom, clientPrenom);
                commandes.add(commandeInfo);
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des commandes : " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("❌ Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }

        return commandes;
    }

    /**
     * Récupère les détails d'une commande par son ID
     */
    public static CommandeDetail getCommandeById(int commandeId) {
        Connection connection = null;
        PreparedStatement pstmtCommande = null;
        PreparedStatement pstmtPlats = null;
        ResultSet rsCommande = null;
        ResultSet rsPlats = null;
        CommandeDetail commandeDetail = null;

        try {
            connection = DatabaseConnection.getConnection();

            // 1. Récupérer les informations générales de la commande
            String sqlCommande = "SELECT c.id, c.date_commande, c.total, c.client_id, p.nom, p.prenom " +
                               "FROM CommandeRestau c " +
                               "JOIN ClientRestau cr ON c.client_id = cr.id " +
                               "JOIN Personne p ON cr.id = p.id " +
                               "WHERE c.id = ?";

            pstmtCommande = connection.prepareStatement(sqlCommande);
            pstmtCommande.setInt(1, commandeId);
            rsCommande = pstmtCommande.executeQuery();

            if (rsCommande.next()) {
                Timestamp dateCommande = rsCommande.getTimestamp("date_commande");
                double total = rsCommande.getDouble("total");
                int clientId = rsCommande.getInt("client_id");
                String clientNom = rsCommande.getString("nom");
                String clientPrenom = rsCommande.getString("prenom");
                
                commandeDetail = new CommandeDetail(commandeId, dateCommande, total, clientId, clientNom, clientPrenom);

                // 2. Récupérer tous les plats de la commande
                String sqlPlats = "SELECT p.id, p.nom, p.prix, cp.quantite, cp.prix_unitaire " +
                                 "FROM CommandePlats cp " +
                                 "JOIN PlatRestau p ON cp.plat_id = p.id " +
                                 "WHERE cp.commande_id = ?";

                pstmtPlats = connection.prepareStatement(sqlPlats);
                pstmtPlats.setInt(1, commandeId);
                rsPlats = pstmtPlats.executeQuery();

                while (rsPlats.next()) {
                    int platId = rsPlats.getInt("id");
                    String platNom = rsPlats.getString("nom");
                    double platPrix = rsPlats.getDouble("prix_unitaire"); // Utiliser le prix au moment de la commande
                    int quantite = rsPlats.getInt("quantite");
                    
                    PlatRestau plat = new PlatRestau(platId, platNom, platPrix);
                    // Ajouter le plat autant de fois que la quantité indique
                    for (int i = 0; i < quantite; i++) {
                        commandeDetail.addPlat(plat);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération de la commande : " + e.getMessage());
        } finally {
            try {
                if (rsCommande != null) rsCommande.close();
                if (rsPlats != null) rsPlats.close();
                if (pstmtCommande != null) pstmtCommande.close();
                if (pstmtPlats != null) pstmtPlats.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("❌ Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }

        return commandeDetail;
    }
    
    /**
     * Ajoute un plat à une commande existante
     */
    public static boolean ajouterPlatCommande(int commandeId, int platId, int quantite) {
        Connection connection = null;
        CallableStatement cstmt = null;
        
        try {
            connection = DatabaseConnection.getConnection();
            // Utiliser la procédure stockée
            cstmt = connection.prepareCall("{CALL ajouter_plat_commande(?, ?, ?)}");
            cstmt.setInt(1, commandeId);
            cstmt.setInt(2, platId);
            cstmt.setInt(3, quantite);
            cstmt.execute();
            
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout du plat à la commande : " + e.getMessage());
            return false;
        } finally {
            try {
                if (cstmt != null) cstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("❌ Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }
    }

    /**
     * Classe interne pour stocker les informations de base d'une commande
     */
    public static class CommandeInfo {
        private int id;
        private Timestamp dateCommande;
        private double total;
        private int clientId;
        private String clientNom;
        private String clientPrenom;

        public CommandeInfo(int id, Timestamp dateCommande, double total, int clientId, String clientNom, String clientPrenom) {
            this.id = id;
            this.dateCommande = dateCommande;
            this.total = total;
            this.clientId = clientId;
            this.clientNom = clientNom;
            this.clientPrenom = clientPrenom;
        }

        public int getId() { return id; }
        public Timestamp getDateCommande() { return dateCommande; }
        public double getTotal() { return total; }
        public int getClientId() { return clientId; }
        public String getClientNom() { return clientNom; }
        public String getClientPrenom() { return clientPrenom; }
        
        @Override
        public String toString() {
            return "Commande #" + id + " - Client: " + clientPrenom + " " + clientNom + 
                   " - Date: " + dateCommande + " - Total: " + total + " FCFA";
        }
    }

    /**
     * Classe interne pour stocker les détails complets d'une commande
     */
    public static class CommandeDetail extends CommandeInfo {
        private List<PlatRestau> plats;
        
        public CommandeDetail(int id, Timestamp dateCommande, double total, int clientId, String clientNom, String clientPrenom) {
            super(id, dateCommande, total, clientId, clientNom, clientPrenom);
            this.plats = new ArrayList<>();
        }
        
        public void addPlat(PlatRestau plat) {
            plats.add(plat);
        }
        
        public List<PlatRestau> getPlats() {
            return plats;
        }
        
        public void afficherDetail() {
            System.out.println("\n====== Détail de la Commande #" + getId() + " ======");
            System.out.println("Date: " + getDateCommande());
            System.out.println("Client: " + getClientPrenom() + " " + getClientNom() + " (ID: " + getClientId() + ")");
            System.out.println("\nPlats commandés:");
            
            // Regrouper les plats identiques pour afficher les quantités
            Map<Integer, Integer> platQuantites = new HashMap<>();
            Map<Integer, PlatRestau> platMap = new HashMap<>();
            
            for (PlatRestau plat : plats) {
                int platId = plat.getId();
                platQuantites.put(platId, platQuantites.getOrDefault(platId, 0) + 1);
                platMap.put(platId, plat);
            }
            
            for (Map.Entry<Integer, Integer> entry : platQuantites.entrySet()) {
                PlatRestau plat = platMap.get(entry.getKey());
                int quantite = entry.getValue();
                System.out.println("  - " + plat.getNom() + " x" + quantite + " : " + 
                                  (plat.getPrix() * quantite) + " FCFA (" + plat.getPrix() + " FCFA l'unité)");
            }
            
            System.out.println("\nTotal: " + getTotal() + " FCFA");
            System.out.println("=======================================");
        }
    }
}