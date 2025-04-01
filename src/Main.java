import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.cell.CheckBoxListCell;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Système de Gestion de la Cantine");
        showLoginScene(primaryStage);
    }
    
    private void showLoginScene(Stage primaryStage) {
        VBox loginLayout = new VBox(10);
        loginLayout.setPadding(new Insets(20));
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.getStyleClass().add("login-container");

        Label titleLabel = new Label("CONNEXION");
        titleLabel.getStyleClass().add("title");
        
        Label userLabel = new Label("👤 Nom d'utilisateur:");
        userLabel.getStyleClass().add("icon-label");
        TextField userField = new TextField();
        
        Label passLabel = new Label("🔑 Mot de passe:");
        passLabel.getStyleClass().add("icon-label");
        PasswordField passField = new PasswordField();
        
        Button loginButton = new Button("Se connecter");
        loginButton.getStyleClass().add("primary-button");
        
        Label errorLabel = new Label("❌ Identifiants incorrects !");
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);

        loginButton.setOnAction(e -> {
            if (userField.getText().equals("admin") && passField.getText().equals("admin")) {
                showMainMenu(primaryStage);
            } else {
                errorLabel.setVisible(true);
            }
        });
        
        loginLayout.getChildren().addAll(
            titleLabel, new Separator(), userLabel, userField, 
            passLabel, passField, loginButton, errorLabel
        );
        
        Scene loginScene = new Scene(loginLayout, 350, 300);
        loginScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }
    
    private void showMainMenu(Stage primaryStage) {
        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("MENU PRINCIPAL");
        titleLabel.getStyleClass().add("title");
        
        Button[] buttons = {
            createMenuButton("Gérer les plats 🍽️"),
            createMenuButton("Gérer les commandes 🛒"),
            createMenuButton("Gérer les clients 👥"),
            createMenuButton("Gérer le personnel 🧑🍳"),
            createMenuButton("Quitter ❌")
        };

        buttons[0].setOnAction(e -> showGestionPlats(primaryStage));
        buttons[1].setOnAction(e -> showGestionCommandes(primaryStage));
        buttons[2].setOnAction(e -> showGestionClients(primaryStage));
        buttons[3].setOnAction(e -> showGestionPersonnels(primaryStage));
        buttons[4].setOnAction(e -> primaryStage.close());

        mainLayout.getChildren().addAll(titleLabel, new Separator());
        mainLayout.getChildren().addAll(buttons);
        
        Scene mainScene = new Scene(mainLayout, 500, 400);
        mainScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(mainScene);
    }

    // Les méthodes de gestion (showGestionPlats, showGestionCommandes, etc.) 
    // restent identiques à la version précédente mais avec l'ajout des styles CSS
    // ...
    
    
    // Méthode pour créer un bouton stylisé pour le menu
    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(250);
        button.setStyle("-fx-font-size: 14px;");
        return button;
    }
    
    // Affiche la vue de gestion des plats
    private void showGestionPlats(Stage primaryStage) {
        VBox layout = createModuleLayout("Gestion des Plats");
        
        Button ajouterButton = new Button("Ajouter un plat");
        Button supprimerButton = new Button("Supprimer un plat");
        Button modifierButton = new Button("Modifier un plat");
        Button listerButton = new Button("Lister les plats");
        Button retourButton = new Button("Retour au menu principal");
        
        retourButton.setOnAction(e -> showMainMenu(primaryStage));
        
        // Implémentations fonctionnelles
        ajouterButton.setOnAction(e -> showAjouterPlatDialog());
        supprimerButton.setOnAction(e -> showSupprimerPlatDialog());
        modifierButton.setOnAction(e -> showModifierPlatDialog());
        listerButton.setOnAction(e -> showListerPlatsDialog());
        
        layout.getChildren().addAll(
            ajouterButton, supprimerButton, modifierButton, listerButton,
            new Separator(), retourButton
        );
        
        Scene scene = new Scene(layout, 400, 350);
        primaryStage.setScene(scene);
    }
    
    // Dialogue pour ajouter un plat
    private void showAjouterPlatDialog() {
        Dialog<PlatRestau> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un plat");
        dialog.setHeaderText("Saisissez les informations du nouveau plat");
        
        // Appliquer le style au DialogPane
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        
        // Boutons
        ButtonType ajouterButtonType = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(ajouterButtonType, ButtonType.CANCEL);
        
        // Style les boutons
        Button ajouterButton = (Button) dialogPane.lookupButton(ajouterButtonType);
        ajouterButton.getStyleClass().add("action-button");
        
        // Grille pour le formulaire
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 10, 10));
        
        TextField nomField = new TextField();
        nomField.setPromptText("Nom du plat");
        TextField prixField = new TextField();
        prixField.setPromptText("Prix en FCFA");
        
        grid.add(new Label("Nom:"), 0, 0);
        grid.add(nomField, 1, 0);
        grid.add(new Label("Prix:"), 0, 1);
        grid.add(prixField, 1, 1);
        
        dialogPane.setContent(grid);
        
        // Validation et conversion des résultats
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ajouterButtonType) {
                try {
                    String nom = nomField.getText();
                    double prix = Double.parseDouble(prixField.getText());
                    
                    if (nom.isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Le nom du plat ne peut pas être vide.");
                        return null;
                    }
                    
                    int id = PlatDAO.getLastPlatId() + 1;
                    return new PlatRestau(id, nom, prix);
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Le prix doit être un nombre valide.");
                    return null;
                }
            }
            return null;
        });
        
        Optional<PlatRestau> result = dialog.showAndWait();
        
        result.ifPresent(plat -> {
            boolean success = PlatDAO.ajouterPlat(plat);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "✅ Plat ajouté avec succès !");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "❌ Erreur lors de l'ajout du plat.");
            }
        });
    }
    
    // Dialogue pour supprimer un plat
    private void showSupprimerPlatDialog() {
        List<PlatRestau> plats = PlatDAO.getAllPlats();
        
        if (plats.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Information", "❌ Aucun plat disponible.");
            return;
        }
        
        Dialog<PlatRestau> dialog = new Dialog<>();
        dialog.setTitle("Supprimer un plat");
        dialog.setHeaderText("Sélectionnez le plat à supprimer");
        
        ButtonType supprimerButtonType = new ButtonType("Supprimer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(supprimerButtonType, ButtonType.CANCEL);
        
        ComboBox<PlatRestau> platsCombo = new ComboBox<>();
        platsCombo.getItems().addAll(plats);
        platsCombo.setPromptText("Sélectionnez un plat");
        
        // Définir comment afficher les plats dans la combobox
        platsCombo.setCellFactory(lv -> new ListCell<PlatRestau>() {
            @Override
            protected void updateItem(PlatRestau item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.toString());
            }
        });
        
        platsCombo.setButtonCell(new ListCell<PlatRestau>() {
            @Override
            protected void updateItem(PlatRestau item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.toString());
            }
        });
        
        VBox vbox = new VBox(10);
        vbox.getChildren().add(platsCombo);
        vbox.setPadding(new Insets(20));
        
        dialog.getDialogPane().setContent(vbox);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == supprimerButtonType) {
                return platsCombo.getValue();
            }
            return null;
        });
        
        Optional<PlatRestau> result = dialog.showAndWait();
        
        result.ifPresent(plat -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmation");
            confirm.setHeaderText("Êtes-vous sûr de vouloir supprimer ce plat ?");
            confirm.setContentText(plat.toString());
            
            Optional<ButtonType> confirmation = confirm.showAndWait();
            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                boolean success = PlatDAO.deletePlat(plat.getId());
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "✅ Plat supprimé avec succès !");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "❌ Erreur lors de la suppression du plat.");
                }
            }
        });
    }
    
    // Dialogue pour modifier un plat
    private void showModifierPlatDialog() {
        List<PlatRestau> plats = PlatDAO.getAllPlats();
        
        if (plats.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Information", "❌ Aucun plat disponible.");
            return;
        }
        
        // Dialogue de sélection du plat
        Dialog<PlatRestau> selectionDialog = new Dialog<>();
        selectionDialog.setTitle("Modifier un plat");
        selectionDialog.setHeaderText("Sélectionnez le plat à modifier");
        
        ButtonType selectionnerButtonType = new ButtonType("Sélectionner", ButtonBar.ButtonData.OK_DONE);
        selectionDialog.getDialogPane().getButtonTypes().addAll(selectionnerButtonType, ButtonType.CANCEL);
        
        ComboBox<PlatRestau> platsCombo = new ComboBox<>();
        platsCombo.getItems().addAll(plats);
        platsCombo.setPromptText("Sélectionnez un plat");
        
        // Définir comment afficher les plats dans la combobox
        platsCombo.setCellFactory(lv -> new ListCell<PlatRestau>() {
            @Override
            protected void updateItem(PlatRestau item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.toString());
            }
        });
        
        platsCombo.setButtonCell(new ListCell<PlatRestau>() {
            @Override
            protected void updateItem(PlatRestau item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.toString());
            }
        });
        
        VBox vbox = new VBox(10);
        vbox.getChildren().add(platsCombo);
        vbox.setPadding(new Insets(20));
        
        selectionDialog.getDialogPane().setContent(vbox);
        
        selectionDialog.setResultConverter(dialogButton -> {
            if (dialogButton == selectionnerButtonType) {
                return platsCombo.getValue();
            }
            return null;
        });
        
        Optional<PlatRestau> selectionResult = selectionDialog.showAndWait();
        
        selectionResult.ifPresent(platSelectionne -> {
            // Dialogue de modification
            Dialog<PlatRestau> modifDialog = new Dialog<>();
            modifDialog.setTitle("Modifier un plat");
            modifDialog.setHeaderText("Modifiez les informations du plat");
            
            ButtonType modifierButtonType = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
            modifDialog.getDialogPane().getButtonTypes().addAll(modifierButtonType, ButtonType.CANCEL);
            
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            
            TextField nomField = new TextField(platSelectionne.getNom());
            TextField prixField = new TextField(String.valueOf(platSelectionne.getPrix()));
            
            grid.add(new Label("Nom:"), 0, 0);
            grid.add(nomField, 1, 0);
            grid.add(new Label("Prix:"), 0, 1);
            grid.add(prixField, 1, 1);
            
            modifDialog.getDialogPane().setContent(grid);
            
            modifDialog.setResultConverter(dialogButton -> {
                if (dialogButton == modifierButtonType) {
                    try {
                        String nom = nomField.getText();
                        double prix = Double.parseDouble(prixField.getText());
                        
                        if (nom.isEmpty()) {
                            showAlert(Alert.AlertType.ERROR, "Erreur", "Le nom du plat ne peut pas être vide.");
                            return null;
                        }
                        
                        platSelectionne.setNom(nom);
                        platSelectionne.setPrix(prix);
                        return platSelectionne;
                    } catch (NumberFormatException e) {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Le prix doit être un nombre valide.");
                        return null;
                    }
                }
                return null;
            });
            
            Optional<PlatRestau> modifResult = modifDialog.showAndWait();
            
            modifResult.ifPresent(platModifie -> {
                boolean success = PlatDAO.updatePlat(platModifie);
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "✅ Plat modifié avec succès !");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "❌ Erreur lors de la modification du plat.");
                }
            });
        });
    }
    
    // Dialogue pour lister les plats
    private void showListerPlatsDialog() {
        List<PlatRestau> plats = PlatDAO.getAllPlats();
        
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Liste des plats");
        dialog.setHeaderText("Voici tous les plats disponibles");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        
        if (plats.isEmpty()) {
            dialog.setContentText("❌ Aucun plat n'est disponible.");
        } else {
            ListView<PlatRestau> platListView = new ListView<>();
            platListView.getItems().addAll(plats);
            platListView.setCellFactory(lv -> new ListCell<PlatRestau>() {
                @Override
                protected void updateItem(PlatRestau item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.toString());
                }
            });
            
            VBox vbox = new VBox(10);
            vbox.getChildren().add(platListView);
            vbox.setPadding(new Insets(20));
            
            dialog.getDialogPane().setContent(vbox);
            dialog.getDialogPane().setPrefSize(400, 400);
        }
        
        dialog.showAndWait();
    }
    
    // Méthode utilitaire pour afficher des alertes
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    // Affiche la vue de gestion des commandes
    private void showGestionCommandes(Stage primaryStage) {
        VBox layout = createModuleLayout("Gestion des Commandes");
        
        Button passerButton = new Button("Passer une commande");
        Button afficherButton = new Button("Afficher toutes les commandes");
        Button detailButton = new Button("Afficher une commande spécifique");
        Button retourButton = new Button("Retour au menu principal");
        
        retourButton.setOnAction(e -> showMainMenu(primaryStage));
        
        // Implémentation des actions
        passerButton.setOnAction(e -> showPasserCommandeDialog());
        afficherButton.setOnAction(e -> showListerCommandesDialog());
        detailButton.setOnAction(e -> showCommandeDetailDialog());
        
        layout.getChildren().addAll(
            passerButton, afficherButton, detailButton,
            new Separator(), retourButton
        );
        
        Scene scene = new Scene(layout, 400, 350);
        primaryStage.setScene(scene);
    }
    
    // Dialogue pour passer une nouvelle commande
    private void showPasserCommandeDialog() {
        // Vérification des clients disponibles
        List<ClientRestau> clients = ClientDAO.getAllClients();
        if (clients.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Attention", "❌ Aucun client n'est enregistré. Impossible de passer une commande.");
            return;
        }
        
        // Vérification des plats disponibles
        List<PlatRestau> platsDisponibles = PlatDAO.getAllPlats();
        if (platsDisponibles.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Attention", "❌ Aucun plat n'est disponible. Impossible de passer une commande.");
            return;
        }
        
        // Dialogue de sélection du client
        Dialog<ClientRestau> clientDialog = new Dialog<>();
        clientDialog.setTitle("Passer une commande");
        clientDialog.setHeaderText("Sélectionnez le client qui passe la commande");
        
        ButtonType selectionnerButtonType = new ButtonType("Sélectionner", ButtonBar.ButtonData.OK_DONE);
        clientDialog.getDialogPane().getButtonTypes().addAll(selectionnerButtonType, ButtonType.CANCEL);
        
        ComboBox<ClientRestau> clientsCombo = new ComboBox<>();
        clientsCombo.getItems().addAll(clients);
        clientsCombo.setPromptText("Sélectionnez un client");
        
        // Définir comment afficher les clients dans la combobox
        clientsCombo.setCellFactory(lv -> new ListCell<ClientRestau>() {
            @Override
            protected void updateItem(ClientRestau item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.toString());
            }
        });
        
        clientsCombo.setButtonCell(new ListCell<ClientRestau>() {
            @Override
            protected void updateItem(ClientRestau item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.toString());
            }
        });
        
        VBox vbox = new VBox(10);
        vbox.getChildren().add(clientsCombo);
        vbox.setPadding(new Insets(20));
        
        clientDialog.getDialogPane().setContent(vbox);
        
        clientDialog.setResultConverter(dialogButton -> {
            if (dialogButton == selectionnerButtonType) {
                return clientsCombo.getValue();
            }
            return null;
        });
        
        Optional<ClientRestau> clientResult = clientDialog.showAndWait();
        
        clientResult.ifPresent(client -> {
            // Créer l'objet commande
            CommandeRestau commande = new CommandeRestau();
            
            // Dialogue de sélection des plats (liste multiple)
            Dialog<List<PlatRestau>> platsDialog = new Dialog<>();
            platsDialog.setTitle("Sélection des plats");
            platsDialog.setHeaderText("Sélectionnez les plats pour la commande de " + client.getPrenom() + " " + client.getNom());
            
            ButtonType ajouterPlatsButtonType = new ButtonType("Ajouter à la commande", ButtonBar.ButtonData.OK_DONE);
            platsDialog.getDialogPane().getButtonTypes().addAll(ajouterPlatsButtonType, ButtonType.CANCEL);
            
            // Créer une ListView avec checkboxes pour sélectionner plusieurs plats
            ListView<PlatRestau> platListView = new ListView<>();
            platListView.getItems().addAll(platsDisponibles);
            platListView.setCellFactory(CheckBoxListCell.forListView(item -> {
                BooleanProperty observable = new SimpleBooleanProperty();
                observable.addListener((obs, wasSelected, isNowSelected) -> {
                    if (isNowSelected) {
                        commande.ajouterPlat(item);
                    } else {
                        // Retirer le plat (si possible)
                        List<PlatRestau> platsCommande = commande.getPlatsCommandes();
                        if (platsCommande.contains(item)) {
                            platsCommande.remove(item);
                        }
                    }
                });
                return observable;
            }));
            
            // Afficher le total en temps réel
            Label totalLabel = new Label("Total: 0 FCFA");
            platListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                totalLabel.setText("Total: " + commande.getTotal() + " FCFA");
            });
            
            VBox platsVbox = new VBox(10);
            platsVbox.getChildren().addAll(new Label("Sélectionnez les plats (cochez pour ajouter):"), platListView, totalLabel);
            platsVbox.setPadding(new Insets(20));
            platsDialog.getDialogPane().setContent(platsVbox);
            platsDialog.getDialogPane().setPrefSize(450, 400);
            
            platsDialog.setResultConverter(dialogButton -> {
                if (dialogButton == ajouterPlatsButtonType) {
                    return commande.getPlatsCommandes();
                }
                return null;
            });
            
            Optional<List<PlatRestau>> platsResult = platsDialog.showAndWait();
            
            platsResult.ifPresent(plats -> {
                if (plats.isEmpty()) {
                    showAlert(Alert.AlertType.WARNING, "Attention", "❌ Aucun plat n'a été sélectionné. La commande a été annulée.");
                    return;
                }
                
                // Enregistrer la commande
                int commandeId = CommandeDAO.ajouterCommande(commande, client.getId());
                
                if (commandeId > 0) {
                    // Afficher un récapitulatif de la commande
                    Dialog<Void> confirmationDialog = new Dialog<>();
                    confirmationDialog.setTitle("Commande passée");
                    confirmationDialog.setHeaderText("✅ Commande #" + commandeId + " passée avec succès !");
                    confirmationDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                    
                    VBox recapVbox = new VBox(10);
                    recapVbox.getChildren().add(new Label("Client: " + client.getPrenom() + " " + client.getNom()));
                    recapVbox.getChildren().add(new Label("Plats commandés:"));
                    
                    ListView<PlatRestau> recapListView = new ListView<>();
                    recapListView.getItems().addAll(plats);
                    recapListView.setCellFactory(lv -> new ListCell<PlatRestau>() {
                        @Override
                        protected void updateItem(PlatRestau item, boolean empty) {
                            super.updateItem(item, empty);
                            setText(empty ? "" : item.toString());
                        }
                    });
                    
                    recapVbox.getChildren().add(recapListView);
                    recapVbox.getChildren().add(new Label("Total: " + commande.getTotal() + " FCFA"));
                    recapVbox.setPadding(new Insets(10));
                    
                    confirmationDialog.getDialogPane().setContent(recapVbox);
                    confirmationDialog.getDialogPane().setPrefSize(400, 350);
                    confirmationDialog.showAndWait();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "❌ Erreur lors de l'enregistrement de la commande.");
                }
            });
        });
    }
    
    // Dialogue pour lister toutes les commandes
    private void showListerCommandesDialog() {
        List<CommandeDAO.CommandeInfo> commandes = CommandeDAO.getAllCommandes();
        
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Liste des commandes");
        dialog.setHeaderText("Voici toutes les commandes enregistrées");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        
        if (commandes.isEmpty()) {
            dialog.setContentText("❌ Aucune commande n'est enregistrée.");
        } else {
            ListView<CommandeDAO.CommandeInfo> commandeListView = new ListView<>();
            commandeListView.getItems().addAll(commandes);
            commandeListView.setCellFactory(lv -> new ListCell<CommandeDAO.CommandeInfo>() {
                @Override
                protected void updateItem(CommandeDAO.CommandeInfo item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.toString());
                }
            });
            
            VBox vbox = new VBox(10);
            vbox.getChildren().add(commandeListView);
            vbox.setPadding(new Insets(20));
            
            dialog.getDialogPane().setContent(vbox);
            dialog.getDialogPane().setPrefSize(500, 400);
        }
        
        dialog.showAndWait();
    }
    
    // Dialogue pour afficher le détail d'une commande spécifique
    private void showCommandeDetailDialog() {
        List<CommandeDAO.CommandeInfo> commandes = CommandeDAO.getAllCommandes();
        
        if (commandes.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Information", "❌ Aucune commande n'est enregistrée.");
            return;
        }
        
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Détail commande");
        dialog.setHeaderText("Entrez l'ID de la commande à afficher");
        
        ButtonType rechercherButtonType = new ButtonType("Rechercher", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(rechercherButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField idField = new TextField();
        idField.setPromptText("ID de la commande");
        
        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == rechercherButtonType) {
                try {
                    return Integer.parseInt(idField.getText());
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "L'ID doit être un nombre entier valide.");
                    return null;
                }
            }
            return null;
        });
        
        Optional<Integer> result = dialog.showAndWait();
        
        result.ifPresent(id -> {
            CommandeDAO.CommandeDetail commande = CommandeDAO.getCommandeById(id);
            if (commande != null) {
                // Afficher les détails dans une nouvelle fenêtre
                Dialog<Void> detailDialog = new Dialog<>();
                detailDialog.setTitle("Détail de la commande #" + id);
                detailDialog.setHeaderText("Informations complètes de la commande");
                detailDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                
                VBox detailsVbox = new VBox(10);
                detailsVbox.getChildren().add(new Label("Date: " + commande.getDateCommande()));
                detailsVbox.getChildren().add(new Label("Client: " + commande.getClientPrenom() + " " + commande.getClientNom() + " (ID: " + commande.getClientId() + ")"));
                detailsVbox.getChildren().add(new Label("Plats commandés:"));
                
                // Regrouper les plats identiques pour afficher les quantités
                Map<Integer, Integer> platQuantites = new HashMap<>();
                Map<Integer, PlatRestau> platMap = new HashMap<>();
                
                for (PlatRestau plat : commande.getPlats()) {
                    int platId = plat.getId();
                    platQuantites.put(platId, platQuantites.getOrDefault(platId, 0) + 1);
                    platMap.put(platId, plat);
                }
                
                ListView<String> platsListView = new ListView<>();
                for (Map.Entry<Integer, Integer> entry : platQuantites.entrySet()) {
                    PlatRestau plat = platMap.get(entry.getKey());
                    int quantite = entry.getValue();
                    platsListView.getItems().add(plat.getNom() + " x" + quantite + " : " + 
                                  (plat.getPrix() * quantite) + " FCFA (" + plat.getPrix() + " FCFA l'unité)");
                }
                
                detailsVbox.getChildren().add(platsListView);
                detailsVbox.getChildren().add(new Label("Total: " + commande.getTotal() + " FCFA"));
                detailsVbox.setPadding(new Insets(10));
                
                detailDialog.getDialogPane().setContent(detailsVbox);
                detailDialog.getDialogPane().setPrefSize(450, 400);
                detailDialog.showAndWait();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "❌ Commande non trouvée.");
            }
        });
    }
    
    // Affiche la vue de gestion des clients
    private void showGestionClients(Stage primaryStage) {
        VBox layout = createModuleLayout("Gestion des Clients");
        
        Button ajouterButton = new Button("Ajouter un client");
        Button afficherButton = new Button("Afficher tous les clients");
        Button detailButton = new Button("Afficher un client spécifique");
        Button modifierButton = new Button("Modifier un client");
        Button supprimerButton = new Button("Supprimer un client");
        Button retourButton = new Button("Retour au menu principal");
        
        retourButton.setOnAction(e -> showMainMenu(primaryStage));
        
        // Implémentation des actions
        ajouterButton.setOnAction(e -> showAjouterClientDialog());
        afficherButton.setOnAction(e -> showListerClientsDialog());
        detailButton.setOnAction(e -> showClientDetailDialog());
        modifierButton.setOnAction(e -> showModifierClientDialog());
        supprimerButton.setOnAction(e -> showSupprimerClientDialog());
        
        layout.getChildren().addAll(
            ajouterButton, afficherButton, detailButton, modifierButton, supprimerButton,
            new Separator(), retourButton
        );
        
        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
    }
    
    // Dialogue pour ajouter un client
    private void showAjouterClientDialog() {
        Dialog<ClientRestau> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un client");
        dialog.setHeaderText("Saisissez les informations du nouveau client");
        
        // Boutons
        ButtonType ajouterButtonType = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ajouterButtonType, ButtonType.CANCEL);
        
        // Grille pour le formulaire
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField idField = new TextField();
        idField.setPromptText("ID du client");
        TextField nomField = new TextField();
        nomField.setPromptText("Nom du client");
        TextField prenomField = new TextField();
        prenomField.setPromptText("Prénom du client");
        
        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Nom:"), 0, 1);
        grid.add(nomField, 1, 1);
        grid.add(new Label("Prénom:"), 0, 2);
        grid.add(prenomField, 1, 2);
        
        dialog.getDialogPane().setContent(grid);
        
        // Validation et conversion des résultats
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ajouterButtonType) {
                try {
                    String nom = nomField.getText();
                    String prenom = prenomField.getText();
                    int id = Integer.parseInt(idField.getText());
                    
                    if (nom.isEmpty() || prenom.isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Le nom et le prénom ne peuvent pas être vides.");
                        return null;
                    }
                    
                    return new ClientRestau(id, nom, prenom);
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "L'ID doit être un nombre entier valide.");
                    return null;
                }
            }
            return null;
        });
        
        Optional<ClientRestau> result = dialog.showAndWait();
        
        result.ifPresent(client -> {
            boolean success = ClientDAO.ajouterClient(client);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "✅ Client ajouté avec succès !");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "❌ Erreur lors de l'ajout du client.");
            }
        });
    }
    
    // Dialogue pour lister tous les clients
    private void showListerClientsDialog() {
        List<ClientRestau> clients = ClientDAO.getAllClients();
        
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Liste des clients");
        dialog.setHeaderText("Voici tous les clients enregistrés");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        
        if (clients.isEmpty()) {
            dialog.setContentText("❌ Aucun client n'est enregistré.");
        } else {
            ListView<ClientRestau> clientListView = new ListView<>();
            clientListView.getItems().addAll(clients);
            clientListView.setCellFactory(lv -> new ListCell<ClientRestau>() {
                @Override
                protected void updateItem(ClientRestau item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.toString());
                }
            });
            
            VBox vbox = new VBox(10);
            vbox.getChildren().add(clientListView);
            vbox.setPadding(new Insets(20));
            
            dialog.getDialogPane().setContent(vbox);
            dialog.getDialogPane().setPrefSize(400, 400);
        }
        
        dialog.showAndWait();
    }
    
    // Dialogue pour afficher le détail d'un client spécifique
    private void showClientDetailDialog() {
        List<ClientRestau> clients = ClientDAO.getAllClients();
        
        if (clients.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Information", "❌ Aucun client n'est enregistré.");
            return;
        }
        
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Détail client");
        dialog.setHeaderText("Entrez l'ID du client à afficher");
        
        ButtonType rechercherButtonType = new ButtonType("Rechercher", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(rechercherButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField idField = new TextField();
        idField.setPromptText("ID du client");
        
        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == rechercherButtonType) {
                try {
                    return Integer.parseInt(idField.getText());
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "L'ID doit être un nombre entier valide.");
                    return null;
                }
            }
            return null;
        });
        
        Optional<Integer> result = dialog.showAndWait();
        
        result.ifPresent(id -> {
            ClientRestau client = ClientDAO.getClientById(id);
            if (client != null) {
                Alert detailAlert = new Alert(Alert.AlertType.INFORMATION);
                detailAlert.setTitle("Détail du client");
                detailAlert.setHeaderText("Informations du client #" + id);
                detailAlert.setContentText(client.toString());
                detailAlert.showAndWait();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "❌ Client non trouvé.");
            }
        });
    }
    
    // Dialogue pour modifier un client
    private void showModifierClientDialog() {
        List<ClientRestau> clients = ClientDAO.getAllClients();
        
        if (clients.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Information", "❌ Aucun client n'est enregistré.");
            return;
        }
        
        // Dialogue de sélection du client
        Dialog<ClientRestau> selectionDialog = new Dialog<>();
        selectionDialog.setTitle("Modifier un client");
        selectionDialog.setHeaderText("Sélectionnez le client à modifier");
        
        ButtonType selectionnerButtonType = new ButtonType("Sélectionner", ButtonBar.ButtonData.OK_DONE);
        selectionDialog.getDialogPane().getButtonTypes().addAll(selectionnerButtonType, ButtonType.CANCEL);
        
        ComboBox<ClientRestau> clientsCombo = new ComboBox<>();
        clientsCombo.getItems().addAll(clients);
        clientsCombo.setPromptText("Sélectionnez un client");
        
        // Définir comment afficher les clients dans la combobox
        clientsCombo.setCellFactory(lv -> new ListCell<ClientRestau>() {
            @Override
            protected void updateItem(ClientRestau item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.toString());
            }
        });
        
        clientsCombo.setButtonCell(new ListCell<ClientRestau>() {
            @Override
            protected void updateItem(ClientRestau item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.toString());
            }
        });
        
        VBox vbox = new VBox(10);
        vbox.getChildren().add(clientsCombo);
        vbox.setPadding(new Insets(20));
        
        selectionDialog.getDialogPane().setContent(vbox);
        
        selectionDialog.setResultConverter(dialogButton -> {
            if (dialogButton == selectionnerButtonType) {
                return clientsCombo.getValue();
            }
            return null;
        });
        
        Optional<ClientRestau> selectionResult = selectionDialog.showAndWait();
        
        selectionResult.ifPresent(clientSelectionne -> {
            // Dialogue de modification
            Dialog<ClientRestau> modifDialog = new Dialog<>();
            modifDialog.setTitle("Modifier un client");
            modifDialog.setHeaderText("Modifiez les informations du client");
            
            ButtonType modifierButtonType = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
            modifDialog.getDialogPane().getButtonTypes().addAll(modifierButtonType, ButtonType.CANCEL);
            
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            
            TextField nomField = new TextField(clientSelectionne.getNom());
            TextField prenomField = new TextField(clientSelectionne.getPrenom());
            
            grid.add(new Label("Nom:"), 0, 0);
            grid.add(nomField, 1, 0);
            grid.add(new Label("Prénom:"), 0, 1);
            grid.add(prenomField, 1, 1);
            
            modifDialog.getDialogPane().setContent(grid);
            
            modifDialog.setResultConverter(dialogButton -> {
                if (dialogButton == modifierButtonType) {
                    String nom = nomField.getText();
                    String prenom = prenomField.getText();
                    
                    if (nom.isEmpty() || prenom.isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Le nom et le prénom ne peuvent pas être vides.");
                        return null;
                    }
                    
                    clientSelectionne.setNom(nom);
                    clientSelectionne.setPrenom(prenom);
                    return clientSelectionne;
                }
                return null;
            });
            
            Optional<ClientRestau> modifResult = modifDialog.showAndWait();
            
            modifResult.ifPresent(clientModifie -> {
                boolean success = ClientDAO.updateClient(clientModifie);
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "✅ Client modifié avec succès !");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "❌ Erreur lors de la modification du client.");
                }
            });
        });
    }
    
    // Dialogue pour supprimer un client
    private void showSupprimerClientDialog() {
        List<ClientRestau> clients = ClientDAO.getAllClients();
        
        if (clients.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Information", "❌ Aucun client n'est enregistré.");
            return;
        }
        
        Dialog<ClientRestau> dialog = new Dialog<>();
        dialog.setTitle("Supprimer un client");
        dialog.setHeaderText("Sélectionnez le client à supprimer");
        
        ButtonType supprimerButtonType = new ButtonType("Supprimer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(supprimerButtonType, ButtonType.CANCEL);
        
        ComboBox<ClientRestau> clientsCombo = new ComboBox<>();
        clientsCombo.getItems().addAll(clients);
        clientsCombo.setPromptText("Sélectionnez un client");
        
        // Définir comment afficher les clients dans la combobox
        clientsCombo.setCellFactory(lv -> new ListCell<ClientRestau>() {
            @Override
            protected void updateItem(ClientRestau item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.toString());
            }
        });
        
        clientsCombo.setButtonCell(new ListCell<ClientRestau>() {
            @Override
            protected void updateItem(ClientRestau item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.toString());
            }
        });
        
        VBox vbox = new VBox(10);
        vbox.getChildren().add(clientsCombo);
        vbox.setPadding(new Insets(20));
        
        dialog.getDialogPane().setContent(vbox);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == supprimerButtonType) {
                return clientsCombo.getValue();
            }
            return null;
        });
        
        Optional<ClientRestau> result = dialog.showAndWait();
        
        result.ifPresent(client -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmation");
            confirm.setHeaderText("Êtes-vous sûr de vouloir supprimer ce client ?");
            confirm.setContentText(client.toString());
            
            Optional<ButtonType> confirmation = confirm.showAndWait();
            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                boolean success = ClientDAO.deleteClient(client.getId());
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "✅ Client supprimé avec succès !");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "❌ Erreur lors de la suppression du client.");
                }
            }
        });
    }
    
    // Affiche la vue de gestion du personnel
    private void showGestionPersonnels(Stage primaryStage) {
        VBox layout = createModuleLayout("Gestion du Personnel");
        
        Button ajouterButton = new Button("Ajouter un personnel");
        Button afficherButton = new Button("Afficher tous les personnels");
        Button detailButton = new Button("Afficher un personnel spécifique");
        Button modifierButton = new Button("Modifier un personnel");
        Button supprimerButton = new Button("Supprimer un personnel");
        Button retourButton = new Button("Retour au menu principal");
        
        retourButton.setOnAction(e -> showMainMenu(primaryStage));
        
        // Implémentation des actions
        ajouterButton.setOnAction(e -> showAjouterPersonnelDialog());
        afficherButton.setOnAction(e -> showListerPersonnelsDialog());
        detailButton.setOnAction(e -> showPersonnelDetailDialog());
        modifierButton.setOnAction(e -> showModifierPersonnelDialog());
        supprimerButton.setOnAction(e -> showSupprimerPersonnelDialog());
        
        layout.getChildren().addAll(
            ajouterButton, afficherButton, detailButton, modifierButton, supprimerButton,
            new Separator(), retourButton
        );
        
        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);
    }
    
    // Dialogue pour ajouter un personnel
    private void showAjouterPersonnelDialog() {
        Dialog<PersonnelRestau> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un personnel");
        dialog.setHeaderText("Saisissez les informations du nouveau personnel");
        
        // Boutons
        ButtonType ajouterButtonType = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ajouterButtonType, ButtonType.CANCEL);
        
        // Grille pour le formulaire
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField idField = new TextField();
        idField.setPromptText("ID du personnel");
        TextField nomField = new TextField();
        nomField.setPromptText("Nom du personnel");
        TextField prenomField = new TextField();
        prenomField.setPromptText("Prénom du personnel");
        TextField posteField = new TextField();
        posteField.setPromptText("Poste occupé");
        TextField salaireField = new TextField();
        salaireField.setPromptText("Salaire en FCFA");
        
        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Nom:"), 0, 1);
        grid.add(nomField, 1, 1);
        grid.add(new Label("Prénom:"), 0, 2);
        grid.add(prenomField, 1, 2);
        grid.add(new Label("Poste:"), 0, 3);
        grid.add(posteField, 1, 3);
        grid.add(new Label("Salaire:"), 0, 4);
        grid.add(salaireField, 1, 4);
        
        dialog.getDialogPane().setContent(grid);
        
        // Validation et conversion des résultats
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ajouterButtonType) {
                try {
                    String nom = nomField.getText();
                    String prenom = prenomField.getText();
                    String poste = posteField.getText();
                    int id = Integer.parseInt(idField.getText());
                    double salaire = Double.parseDouble(salaireField.getText());
                    
                    if (nom.isEmpty() || prenom.isEmpty() || poste.isEmpty()) {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs textuels doivent être remplis.");
                        return null;
                    }
                    
                    return new PersonnelRestau(id, nom, prenom, poste, salaire);
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "L'ID doit être un entier et le salaire un nombre décimal valide.");
                    return null;
                }
            }
            return null;
        });
        
        Optional<PersonnelRestau> result = dialog.showAndWait();
        
        result.ifPresent(personnel -> {
            boolean success = PersonnelDAO.ajouterPersonnel(personnel);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "✅ Personnel ajouté avec succès !");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "❌ Erreur lors de l'ajout du personnel.");
            }
        });
    }
    
    // Dialogue pour lister tous les personnels
    private void showListerPersonnelsDialog() {
        List<PersonnelRestau> personnels = PersonnelDAO.getAllPersonnels();
        
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Liste du personnel");
        dialog.setHeaderText("Voici tous les membres du personnel");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        
        if (personnels.isEmpty()) {
            dialog.setContentText("❌ Aucun personnel n'est enregistré.");
        } else {
            ListView<PersonnelRestau> personnelListView = new ListView<>();
            personnelListView.getItems().addAll(personnels);
            personnelListView.setCellFactory(lv -> new ListCell<PersonnelRestau>() {
                @Override
                protected void updateItem(PersonnelRestau item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.toString());
                }
            });
            
            VBox vbox = new VBox(10);
            vbox.getChildren().add(personnelListView);
            vbox.setPadding(new Insets(20));
            
            dialog.getDialogPane().setContent(vbox);
            dialog.getDialogPane().setPrefSize(500, 400);
        }
        
        dialog.showAndWait();
    }
    
    // Dialogue pour afficher le détail d'un personnel spécifique
    private void showPersonnelDetailDialog() {
        List<PersonnelRestau> personnels = PersonnelDAO.getAllPersonnels();
        
        if (personnels.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Information", "❌ Aucun personnel n'est enregistré.");
            return;
        }
        
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Détail personnel");
        dialog.setHeaderText("Entrez l'ID du personnel à afficher");
        
        ButtonType rechercherButtonType = new ButtonType("Rechercher", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(rechercherButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField idField = new TextField();
        idField.setPromptText("ID du personnel");
        
        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == rechercherButtonType) {
                try {
                    return Integer.parseInt(idField.getText());
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "L'ID doit être un nombre entier valide.");
                    return null;
                }
            }
            return null;
        });
        
        Optional<Integer> result = dialog.showAndWait();
        
        result.ifPresent(id -> {
            PersonnelRestau personnel = PersonnelDAO.getPersonnelById(id);
            if (personnel != null) {
                Dialog<Void> detailDialog = new Dialog<>();
                detailDialog.setTitle("Détail du personnel");
                detailDialog.setHeaderText("Informations du personnel #" + id);
                detailDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                
                GridPane detailGrid = new GridPane();
                detailGrid.setHgap(10);
                detailGrid.setVgap(10);
                detailGrid.setPadding(new Insets(20, 20, 10, 10));
                
                detailGrid.add(new Label("ID:"), 0, 0);
                detailGrid.add(new Label(String.valueOf(personnel.getId())), 1, 0);
                detailGrid.add(new Label("Nom:"), 0, 1);
                detailGrid.add(new Label(personnel.getNom()), 1, 1);
                detailGrid.add(new Label("Prénom:"), 0, 2);
                detailGrid.add(new Label(personnel.getPrenom()), 1, 2);
                detailGrid.add(new Label("Poste:"), 0, 3);
                detailGrid.add(new Label(personnel.getPoste()), 1, 3);
                detailGrid.add(new Label("Salaire:"), 0, 4);
                detailGrid.add(new Label(personnel.getSalaire() + " FCFA"), 1, 4);
                
                detailDialog.getDialogPane().setContent(detailGrid);
                detailDialog.showAndWait();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "❌ Personnel non trouvé.");
            }
        });
    }
    
    // Dialogue pour modifier un personnel
    private void showModifierPersonnelDialog() {
        List<PersonnelRestau> personnels = PersonnelDAO.getAllPersonnels();
        
        if (personnels.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Information", "❌ Aucun personnel n'est enregistré.");
            return;
        }
        
        // Dialogue de sélection du personnel
        Dialog<PersonnelRestau> selectionDialog = new Dialog<>();
        selectionDialog.setTitle("Modifier un personnel");
        selectionDialog.setHeaderText("Sélectionnez le personnel à modifier");
        
        ButtonType selectionnerButtonType = new ButtonType("Sélectionner", ButtonBar.ButtonData.OK_DONE);
        selectionDialog.getDialogPane().getButtonTypes().addAll(selectionnerButtonType, ButtonType.CANCEL);
        
        ComboBox<PersonnelRestau> personnelsCombo = new ComboBox<>();
        personnelsCombo.getItems().addAll(personnels);
        personnelsCombo.setPromptText("Sélectionnez un personnel");
        
        // Définir comment afficher les personnels dans la combobox
        personnelsCombo.setCellFactory(lv -> new ListCell<PersonnelRestau>() {
            @Override
            protected void updateItem(PersonnelRestau item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.toString());
            }
        });
        
        personnelsCombo.setButtonCell(new ListCell<PersonnelRestau>() {
            @Override
            protected void updateItem(PersonnelRestau item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.toString());
            }
        });
        
        VBox vbox = new VBox(10);
        vbox.getChildren().add(personnelsCombo);
        vbox.setPadding(new Insets(20));
        
        selectionDialog.getDialogPane().setContent(vbox);
        
        selectionDialog.setResultConverter(dialogButton -> {
            if (dialogButton == selectionnerButtonType) {
                return personnelsCombo.getValue();
            }
            return null;
        });
        
        Optional<PersonnelRestau> selectionResult = selectionDialog.showAndWait();
        
        selectionResult.ifPresent(personnelSelectionne -> {
            // Dialogue de modification
            Dialog<PersonnelRestau> modifDialog = new Dialog<>();
            modifDialog.setTitle("Modifier un personnel");
            modifDialog.setHeaderText("Modifiez les informations du personnel");
            
            ButtonType modifierButtonType = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
            modifDialog.getDialogPane().getButtonTypes().addAll(modifierButtonType, ButtonType.CANCEL);
            
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            
            TextField nomField = new TextField(personnelSelectionne.getNom());
            TextField prenomField = new TextField(personnelSelectionne.getPrenom());
            TextField posteField = new TextField(personnelSelectionne.getPoste());
            TextField salaireField = new TextField(String.valueOf(personnelSelectionne.getSalaire()));
            
            grid.add(new Label("Nom:"), 0, 0);
            grid.add(nomField, 1, 0);
            grid.add(new Label("Prénom:"), 0, 1);
            grid.add(prenomField, 1, 1);
            grid.add(new Label("Poste:"), 0, 2);
            grid.add(posteField, 1, 2);
            grid.add(new Label("Salaire:"), 0, 3);
            grid.add(salaireField, 1, 3);
            
            modifDialog.getDialogPane().setContent(grid);
            
            modifDialog.setResultConverter(dialogButton -> {
                if (dialogButton == modifierButtonType) {
                    try {
                        String nom = nomField.getText();
                        String prenom = prenomField.getText();
                        String poste = posteField.getText();
                        double salaire = Double.parseDouble(salaireField.getText());
                        
                        if (nom.isEmpty() || prenom.isEmpty() || poste.isEmpty()) {
                            showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs textuels doivent être remplis.");
                            return null;
                        }
                        
                        personnelSelectionne.setNom(nom);
                        personnelSelectionne.setPrenom(prenom);
                        personnelSelectionne.setPoste(poste);
                        personnelSelectionne.setSalaire(salaire);
                        return personnelSelectionne;
                    } catch (NumberFormatException e) {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Le salaire doit être un nombre décimal valide.");
                        return null;
                    }
                }
                return null;
            });
            
            Optional<PersonnelRestau> modifResult = modifDialog.showAndWait();
            
            modifResult.ifPresent(personnelModifie -> {
                boolean success = PersonnelDAO.updatePersonnel(personnelModifie);
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "✅ Personnel modifié avec succès !");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "❌ Erreur lors de la modification du personnel.");
                }
            });
        });
    }
    
    // Dialogue pour supprimer un personnel
    private void showSupprimerPersonnelDialog() {
        List<PersonnelRestau> personnels = PersonnelDAO.getAllPersonnels();
        
        if (personnels.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Information", "❌ Aucun personnel n'est enregistré.");
            return;
        }
        
        Dialog<PersonnelRestau> dialog = new Dialog<>();
        dialog.setTitle("Supprimer un personnel");
        dialog.setHeaderText("Sélectionnez le personnel à supprimer");
        
        ButtonType supprimerButtonType = new ButtonType("Supprimer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(supprimerButtonType, ButtonType.CANCEL);
        
        ComboBox<PersonnelRestau> personnelsCombo = new ComboBox<>();
        personnelsCombo.getItems().addAll(personnels);
        personnelsCombo.setPromptText("Sélectionnez un personnel");
        
        // Définir comment afficher les personnels dans la combobox
        personnelsCombo.setCellFactory(lv -> new ListCell<PersonnelRestau>() {
            @Override
            protected void updateItem(PersonnelRestau item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.toString());
            }
        });
        
        personnelsCombo.setButtonCell(new ListCell<PersonnelRestau>() {
            @Override
            protected void updateItem(PersonnelRestau item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.toString());
            }
        });
        
        VBox vbox = new VBox(10);
        vbox.getChildren().add(personnelsCombo);
        vbox.setPadding(new Insets(20));
        
        dialog.getDialogPane().setContent(vbox);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == supprimerButtonType) {
                return personnelsCombo.getValue();
            }
            return null;
        });
        
        Optional<PersonnelRestau> result = dialog.showAndWait();
        
        result.ifPresent(personnel -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmation");
            confirm.setHeaderText("Êtes-vous sûr de vouloir supprimer ce personnel ?");
            confirm.setContentText(personnel.toString());
            
            Optional<ButtonType> confirmation = confirm.showAndWait();
            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                boolean success = PersonnelDAO.deletePersonnel(personnel.getId());
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "✅ Personnel supprimé avec succès !");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "❌ Erreur lors de la suppression du personnel.");
                }
            }
        });
    }
    
    // Crée un layout de base pour un module
    private VBox createModuleLayout(String title) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        layout.getChildren().addAll(titleLabel, new Separator());
        
        return layout;
    }
    
    // Affiche une alerte pour les fonctionnalités non implémentées
    private void showNotImplementedAlert(String feature) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fonctionnalité non implémentée");
        alert.setHeaderText(null);
        alert.setContentText("La fonctionnalité \"" + feature + "\" doit être implémentée dans l'interface graphique.");
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}