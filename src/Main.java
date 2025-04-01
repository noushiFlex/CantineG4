import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

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
        userField.getStyleClass().add("text-field");
        
        Label passLabel = new Label("🔑 Mot de passe:");
        passLabel.getStyleClass().add("icon-label");
        PasswordField passField = new PasswordField();
        passField.getStyleClass().add("password-field");
        
        Button loginButton = new Button("Se connecter");
        loginButton.getStyleClass().addAll("button", "primary-button");
        
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

    private void showGestionPlats(Stage primaryStage) {
        VBox layout = createModuleLayout("Gestion des Plats");
        
        Button ajouterButton = new Button("Ajouter un plat");
        ajouterButton.getStyleClass().add("button");
        Button supprimerButton = new Button("Supprimer un plat");
        supprimerButton.getStyleClass().add("button");
        Button modifierButton = new Button("Modifier un plat");
        modifierButton.getStyleClass().add("button");
        Button listerButton = new Button("Lister les plats");
        listerButton.getStyleClass().add("button");
        Button retourButton = new Button("Retour au menu principal");
        retourButton.getStyleClass().add("button");
        
        retourButton.setOnAction(e -> showMainMenu(primaryStage));
        
        ajouterButton.setOnAction(e -> showAjouterPlatDialog());
        supprimerButton.setOnAction(e -> showSupprimerPlatDialog());
        modifierButton.setOnAction(e -> showModifierPlatDialog());
        listerButton.setOnAction(e -> showListerPlatsDialog());
        
        layout.getChildren().addAll(
            ajouterButton, supprimerButton, modifierButton, listerButton,
            new Separator(), retourButton
        );
        
        Scene scene = new Scene(layout, 400, 350);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
    }
    
    private void showAjouterPlatDialog() {
        Dialog<PlatRestau> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un plat");
        dialog.setHeaderText("Saisissez les informations du nouveau plat");
        
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        
        ButtonType ajouterButtonType = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(ajouterButtonType, ButtonType.CANCEL);
        
        Button ajouterButton = (Button) dialogPane.lookupButton(ajouterButtonType);
        ajouterButton.getStyleClass().addAll("button", "primary-button");
        Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
        cancelButton.getStyleClass().add("button");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 10, 10));
        
        TextField nomField = new TextField();
        nomField.setPromptText("Nom du plat");
        nomField.getStyleClass().add("text-field");
        TextField prixField = new TextField();
        prixField.setPromptText("Prix en FCFA");
        prixField.getStyleClass().add("text-field");
        
        Label nomLabel = new Label("Nom:");
        nomLabel.getStyleClass().add("icon-label");
        Label prixLabel = new Label("Prix:");
        prixLabel.getStyleClass().add("icon-label");
        
        grid.add(nomLabel, 0, 0);
        grid.add(nomField, 1, 0);
        grid.add(prixLabel, 0, 1);
        grid.add(prixField, 1, 1);
        
        dialogPane.setContent(grid);
        
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
    
    private void showSupprimerPlatDialog() {
        List<PlatRestau> plats = PlatDAO.getAllPlats();
        if (plats.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Information", "❌ Aucun plat disponible.");
            return;
        }
        
        Dialog<PlatRestau> dialog = new Dialog<>();
        dialog.setTitle("Supprimer un plat");
        dialog.setHeaderText("Sélectionnez le plat à supprimer");
        
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        
        ButtonType supprimerButtonType = new ButtonType("Supprimer", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(supprimerButtonType, ButtonType.CANCEL);
        
        Button supprimerButton = (Button) dialogPane.lookupButton(supprimerButtonType);
        supprimerButton.getStyleClass().addAll("button", "primary-button");
        Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
        cancelButton.getStyleClass().add("button");
        
        ComboBox<PlatRestau> platsCombo = new ComboBox<>();
        platsCombo.getItems().addAll(plats);
        platsCombo.setPromptText("Sélectionnez un plat");
        platsCombo.getStyleClass().add("combo-box");
        
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
        
        dialogPane.setContent(vbox);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == supprimerButtonType) {
                return platsCombo.getValue();
            }
            return null;
        });
        
        Optional<PlatRestau> result = dialog.showAndWait();
        result.ifPresent(plat -> extracted(plat));
    }

    private void extracted(PlatRestau plat) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.getDialogPane().getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        confirm.getDialogPane().getStyleClass().add("dialog-pane");
        confirm.setTitle("Confirmation");
        confirm.setHeaderText("Êtes-vous sûr de vouloir supprimer ce plat ?");
        confirm.setContentText(plat.toString());
        
        Button okButton = (Button) confirm.getDialogPane().lookupButton(ButtonType.OK);
        okButton.getStyleClass().add("button");
        Button cancelButton = (Button) confirm.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.getStyleClass().add("button");
        
        Optional<ButtonType> confirmation = confirm.showAndWait();
        if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
            boolean success = PlatDAO.deletePlat(plat.getId());
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "✅ Plat supprimé avec succès !");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "❌ Erreur lors de la suppression du plat.");
            }
        }
    }
    
    private void showModifierPlatDialog() {
        List<PlatRestau> plats = PlatDAO.getAllPlats();
        if (plats.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Information", "❌ Aucun plat disponible.");
            return;
        }
        
        Dialog<PlatRestau> selectionDialog = new Dialog<>();
        selectionDialog.setTitle("Modifier un plat");
        selectionDialog.setHeaderText("Sélectionnez le plat à modifier");
        
        DialogPane selectionPane = selectionDialog.getDialogPane();
        selectionPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        selectionPane.getStyleClass().add("dialog-pane");
        
        ButtonType selectionnerButtonType = new ButtonType("Sélectionner", ButtonBar.ButtonData.OK_DONE);
        selectionPane.getButtonTypes().addAll(selectionnerButtonType, ButtonType.CANCEL);
        
        Button selectionnerButton = (Button) selectionPane.lookupButton(selectionnerButtonType);
        selectionnerButton.getStyleClass().addAll("button", "primary-button");
        Button cancelButton1 = (Button) selectionPane.lookupButton(ButtonType.CANCEL);
        cancelButton1.getStyleClass().add("button");
        
        ComboBox<PlatRestau> platsCombo = new ComboBox<>();
        platsCombo.getItems().addAll(plats);
        platsCombo.setPromptText("Sélectionnez un plat");
        platsCombo.getStyleClass().add("combo-box");
        
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
        selectionPane.setContent(vbox);
        
        selectionDialog.setResultConverter(dialogButton -> {
            if (dialogButton == selectionnerButtonType) {
                return platsCombo.getValue();
            }
            return null;
        });
        
        Optional<PlatRestau> selectionResult = selectionDialog.showAndWait();
        selectionResult.ifPresent(platSelectionne -> {
            Dialog<PlatRestau> modifDialog = new Dialog<>();
            modifDialog.setTitle("Modifier un plat");
            modifDialog.setHeaderText("Modifiez les informations du plat");
            
            DialogPane modifPane = modifDialog.getDialogPane();
            modifPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            modifPane.getStyleClass().add("dialog-pane");
            
            ButtonType modifierButtonType = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
            modifPane.getButtonTypes().addAll(modifierButtonType, ButtonType.CANCEL);
            
            Button modifierButton = (Button) modifPane.lookupButton(modifierButtonType);
            modifierButton.getStyleClass().addAll("button", "primary-button");
            Button cancelButton2 = (Button) modifPane.lookupButton(ButtonType.CANCEL);
            cancelButton2.getStyleClass().add("button");
            
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            
            TextField nomField = new TextField(platSelectionne.getNom());
            nomField.getStyleClass().add("text-field");
            TextField prixField = new TextField(String.valueOf(platSelectionne.getPrix()));
            prixField.getStyleClass().add("text-field");
            
            Label nomLabel = new Label("Nom:");
            nomLabel.getStyleClass().add("icon-label");
            Label prixLabel = new Label("Prix:");
            prixLabel.getStyleClass().add("icon-label");
            
            grid.add(nomLabel, 0, 0);
            grid.add(nomField, 1, 0);
            grid.add(prixLabel, 0, 1);
            grid.add(prixField, 1, 1);
            
            modifPane.setContent(grid);
            
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
    
    private void showListerPlatsDialog() {
        List<PlatRestau> plats = PlatDAO.getAllPlats();
        
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Liste des plats");
        dialog.setHeaderText("Voici tous les plats disponibles");
        
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        dialogPane.getButtonTypes().add(ButtonType.OK);
        
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        okButton.getStyleClass().add("button");
        
        if (plats.isEmpty()) {
            Label emptyLabel = new Label("❌ Aucun plat n'est disponible.");
            emptyLabel.getStyleClass().add("error-label");
            dialogPane.setContent(emptyLabel);
        } else {
            ListView<PlatRestau> platListView = new ListView<>();
            platListView.getStyleClass().add("list-view");
            platListView.getItems().addAll(plats);
            platListView.setCellFactory(lv -> new ListCell<PlatRestau>() {
                @Override
                protected void updateItem(PlatRestau item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.toString());
                    if (!empty) {
                        getStyleClass().add("list-cell");
                    }
                }
            });
            
            VBox vbox = new VBox(10);
            vbox.getChildren().add(platListView);
            vbox.setPadding(new Insets(20));
            dialogPane.setContent(vbox);
            dialogPane.setPrefSize(400, 400);
        }
        
        dialog.showAndWait();
    }
    
    private void showGestionCommandes(Stage primaryStage) {
        VBox layout = createModuleLayout("Gestion des Commandes");
        
        Button passerButton = new Button("Passer une commande");
        passerButton.getStyleClass().add("button");
        Button afficherButton = new Button("Afficher toutes les commandes");
        afficherButton.getStyleClass().add("button");
        Button detailButton = new Button("Afficher une commande spécifique");
        detailButton.getStyleClass().add("button");
        Button retourButton = new Button("Retour au menu principal");
        retourButton.getStyleClass().add("button");
        
        retourButton.setOnAction(e -> showMainMenu(primaryStage));
        
        passerButton.setOnAction(e -> showPasserCommandeDialog());
        afficherButton.setOnAction(e -> showListerCommandesDialog());
        detailButton.setOnAction(e -> showCommandeDetailDialog());
        
        layout.getChildren().addAll(
            passerButton, afficherButton, detailButton,
            new Separator(), retourButton
        );
        
        Scene scene = new Scene(layout, 400, 350);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
    }
    
    private void showPasserCommandeDialog() {
        List<ClientRestau> clients = ClientDAO.getAllClients();
        if (clients.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Attention", "❌ Aucun client n'est enregistré.");
            return;
        }
        
        List<PlatRestau> platsDisponibles = PlatDAO.getAllPlats();
        if (platsDisponibles.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Attention", "❌ Aucun plat n'est disponible.");
            return;
        }
        
        Dialog<ClientRestau> clientDialog = new Dialog<>();
        clientDialog.setTitle("Passer une commande");
        clientDialog.setHeaderText("Sélectionnez le client");
        
        DialogPane clientPane = clientDialog.getDialogPane();
        clientPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        clientPane.getStyleClass().add("dialog-pane");
        
        ButtonType selectionnerButtonType = new ButtonType("Sélectionner", ButtonBar.ButtonData.OK_DONE);
        clientPane.getButtonTypes().addAll(selectionnerButtonType, ButtonType.CANCEL);
        
        Button selectionnerButton = (Button) clientPane.lookupButton(selectionnerButtonType);
        selectionnerButton.getStyleClass().addAll("button", "primary-button");
        Button cancelButton1 = (Button) clientPane.lookupButton(ButtonType.CANCEL);
        cancelButton1.getStyleClass().add("button");
        
        ComboBox<ClientRestau> clientsCombo = new ComboBox<>();
        clientsCombo.getItems().addAll(clients);
        clientsCombo.setPromptText("Sélectionnez un client");
        clientsCombo.getStyleClass().add("combo-box");
        
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
        clientPane.setContent(vbox);
        
        clientDialog.setResultConverter(dialogButton -> {
            if (dialogButton == selectionnerButtonType) {
                return clientsCombo.getValue();
            }
            return null;
        });
        
        Optional<ClientRestau> clientResult = clientDialog.showAndWait();
        clientResult.ifPresent(client -> {
            CommandeRestau commande = new CommandeRestau();
            
            Dialog<List<PlatRestau>> platsDialog = new Dialog<>();
            platsDialog.setTitle("Sélection des plats");
            platsDialog.setHeaderText("Sélectionnez les plats pour " + client.getPrenom() + " " + client.getNom());
            
            DialogPane platsPane = platsDialog.getDialogPane();
            platsPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            platsPane.getStyleClass().add("dialog-pane");
            
            ButtonType ajouterPlatsButtonType = new ButtonType("Ajouter à la commande", ButtonBar.ButtonData.OK_DONE);
            platsPane.getButtonTypes().addAll(ajouterPlatsButtonType, ButtonType.CANCEL);
            
            Button ajouterPlatsButton = (Button) platsPane.lookupButton(ajouterPlatsButtonType);
            ajouterPlatsButton.getStyleClass().addAll("button", "primary-button");
            Button cancelButton2 = (Button) platsPane.lookupButton(ButtonType.CANCEL);
            cancelButton2.getStyleClass().add("button");
            
            ListView<PlatRestau> platListView = new ListView<>();
            platListView.getStyleClass().add("list-view");
            platListView.getItems().addAll(platsDisponibles);
            platListView.setCellFactory(CheckBoxListCell.forListView(item -> {
                BooleanProperty observable = new SimpleBooleanProperty();
                observable.addListener((obs, wasSelected, isNowSelected) -> {
                    if (isNowSelected) {
                        commande.ajouterPlat(item);
                    } else {
                        List<PlatRestau> platsCommande = commande.getPlatsCommandes();
                        if (platsCommande.contains(item)) {
                            platsCommande.remove(item);
                        }
                    }
                });
                return observable;
            }));
            
            Label totalLabel = new Label("Total: 0 FCFA");
            totalLabel.getStyleClass().add("icon-label");
            platListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                totalLabel.setText("Total: " + commande.getTotal() + " FCFA");
            });
            
            VBox platsVbox = new VBox(10);
            Label instructionLabel = new Label("Sélectionnez les plats (cochez pour ajouter):");
            instructionLabel.getStyleClass().add("icon-label");
            platsVbox.getChildren().addAll(instructionLabel, platListView, totalLabel);
            platsVbox.setPadding(new Insets(20));
            platsPane.setContent(platsVbox);
            platsPane.setPrefSize(450, 400);
            
            platsDialog.setResultConverter(dialogButton -> {
                if (dialogButton == ajouterPlatsButtonType) {
                    return commande.getPlatsCommandes();
                }
                return null;
            });
            
            Optional<List<PlatRestau>> platsResult = platsDialog.showAndWait();
            platsResult.ifPresent(plats -> {
                if (plats.isEmpty()) {
                    showAlert(Alert.AlertType.WARNING, "Attention", "❌ Aucun plat sélectionné.");
                    return;
                }
                
                int commandeId = CommandeDAO.ajouterCommande(commande, client.getId());
                if (commandeId > 0) {
                    Dialog<Void> confirmationDialog = new Dialog<>();
                    confirmationDialog.setTitle("Commande passée");
                    confirmationDialog.setHeaderText("✅ Commande #" + commandeId + " passée avec succès !");
                    
                    DialogPane confirmPane = confirmationDialog.getDialogPane();
                    confirmPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                    confirmPane.getStyleClass().add("dialog-pane");
                    confirmPane.getButtonTypes().add(ButtonType.OK);
                    
                    Button okButton = (Button) confirmPane.lookupButton(ButtonType.OK);
                    okButton.getStyleClass().add("button");
                    
                    VBox recapVbox = new VBox(10);
                    Label clientLabel = new Label("Client: " + client.getPrenom() + " " + client.getNom());
                    clientLabel.getStyleClass().add("icon-label");
                    Label platsLabel = new Label("Plats commandés:");
                    platsLabel.getStyleClass().add("icon-label");
                    
                    ListView<PlatRestau> recapListView = new ListView<>();
                    recapListView.getStyleClass().add("list-view");
                    recapListView.getItems().addAll(plats);
                    recapListView.setCellFactory(lv -> new ListCell<PlatRestau>() {
                        @Override
                        protected void updateItem(PlatRestau item, boolean empty) {
                            super.updateItem(item, empty);
                            setText(empty ? "" : item.toString());
                            if (!empty) {
                                getStyleClass().add("list-cell");
                            }
                        }
                    });
                    
                    Label totalFinalLabel = new Label("Total: " + commande.getTotal() + " FCFA");
                    totalFinalLabel.getStyleClass().add("icon-label");
                    
                    recapVbox.getChildren().addAll(clientLabel, platsLabel, recapListView, totalFinalLabel);
                    recapVbox.setPadding(new Insets(10));
                    confirmPane.setContent(recapVbox);
                    confirmPane.setPrefSize(400, 350);
                    confirmationDialog.showAndWait();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "❌ Erreur lors de l'enregistrement.");
                }
            });
        });
    }
    
    private void showListerCommandesDialog() {
        List<CommandeDAO.CommandeInfo> commandes = CommandeDAO.getAllCommandes();
        
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Liste des commandes");
        dialog.setHeaderText("Voici toutes les commandes enregistrées");
        
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        dialogPane.getButtonTypes().add(ButtonType.OK);
        
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        okButton.getStyleClass().add("button");
        
        if (commandes.isEmpty()) {
            Label emptyLabel = new Label("❌ Aucune commande enregistrée.");
            emptyLabel.getStyleClass().add("error-label");
            dialogPane.setContent(emptyLabel);
        } else {
            ListView<CommandeDAO.CommandeInfo> commandeListView = new ListView<>();
            commandeListView.getStyleClass().add("list-view");
            commandeListView.getItems().addAll(commandes);
            commandeListView.setCellFactory(lv -> new ListCell<CommandeDAO.CommandeInfo>() {
                @Override
                protected void updateItem(CommandeDAO.CommandeInfo item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.toString());
                    if (!empty) {
                        getStyleClass().add("list-cell");
                    }
                }
            });
            
            VBox vbox = new VBox(10);
            vbox.getChildren().add(commandeListView);
            vbox.setPadding(new Insets(20));
            dialogPane.setContent(vbox);
            dialogPane.setPrefSize(500, 400);
        }
        
        dialog.showAndWait();
    }
    
    private void showCommandeDetailDialog() {
        List<CommandeDAO.CommandeInfo> commandes = CommandeDAO.getAllCommandes();
        if (commandes.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Information", "❌ Aucune commande enregistrée.");
            return;
        }
        
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Détail commande");
        dialog.setHeaderText("Entrez l'ID de la commande");
        
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        
        ButtonType rechercherButtonType = new ButtonType("Rechercher", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(rechercherButtonType, ButtonType.CANCEL);
        
        Button rechercherButton = (Button) dialogPane.lookupButton(rechercherButtonType);
        rechercherButton.getStyleClass().addAll("button", "primary-button");
        Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
        cancelButton.getStyleClass().add("button");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField idField = new TextField();
        idField.setPromptText("ID de la commande");
        idField.getStyleClass().add("text-field");
        
        Label idLabel = new Label("ID:");
        idLabel.getStyleClass().add("icon-label");
        
        grid.add(idLabel, 0, 0);
        grid.add(idField, 1, 0);
        dialogPane.setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == rechercherButtonType) {
                try {
                    return Integer.parseInt(idField.getText());
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "L'ID doit être un nombre valide.");
                    return null;
                }
            }
            return null;
        });
        
        Optional<Integer> result = dialog.showAndWait();
        result.ifPresent(id -> {
            CommandeDAO.CommandeDetail commande = CommandeDAO.getCommandeById(id);
            if (commande != null) {
                Dialog<Void> detailDialog = new Dialog<>();
                detailDialog.setTitle("Détail de la commande #" + id);
                detailDialog.setHeaderText("Informations complètes");
                
                DialogPane detailPane = detailDialog.getDialogPane();
                detailPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                detailPane.getStyleClass().add("dialog-pane");
                detailPane.getButtonTypes().add(ButtonType.OK);
                
                Button okButton = (Button) detailPane.lookupButton(ButtonType.OK);
                okButton.getStyleClass().add("button");
                
                VBox detailsVbox = new VBox(10);
                Label dateLabel = new Label("Date: " + commande.getDateCommande());
                dateLabel.getStyleClass().add("icon-label");
                Label clientLabel = new Label("Client: " + commande.getClientPrenom() + " " + commande.getClientNom() + " (ID: " + commande.getClientId() + ")");
                clientLabel.getStyleClass().add("icon-label");
                Label platsLabel = new Label("Plats commandés:");
                platsLabel.getStyleClass().add("icon-label");
                
                Map<Integer, Integer> platQuantites = new HashMap<>();
                Map<Integer, PlatRestau> platMap = new HashMap<>();
                for (PlatRestau plat : commande.getPlats()) {
                    int platId = plat.getId();
                    platQuantites.put(platId, platQuantites.getOrDefault(platId, 0) + 1);
                    platMap.put(platId, plat);
                }
                
                ListView<String> platsListView = new ListView<>();
                platsListView.getStyleClass().add("list-view");
                for (Map.Entry<Integer, Integer> entry : platQuantites.entrySet()) {
                    PlatRestau plat = platMap.get(entry.getKey());
                    int quantite = entry.getValue();
                    platsListView.getItems().add(plat.getNom() + " x" + quantite + " : " + 
                            (plat.getPrix() * quantite) + " FCFA (" + plat.getPrix() + " FCFA l'unité)");
                }
                
                Label totalLabel = new Label("Total: " + commande.getTotal() + " FCFA");
                totalLabel.getStyleClass().add("icon-label");
                
                detailsVbox.getChildren().addAll(dateLabel, clientLabel, platsLabel, platsListView, totalLabel);
                detailsVbox.setPadding(new Insets(10));
                detailPane.setContent(detailsVbox);
                detailPane.setPrefSize(450, 400);
                detailDialog.showAndWait();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "❌ Commande non trouvée.");
            }
        });
    }
    
    private void showGestionClients(Stage primaryStage) {
        VBox layout = createModuleLayout("Gestion des Clients");
        
        Button ajouterButton = new Button("Ajouter un client");
        ajouterButton.getStyleClass().add("button");
        Button afficherButton = new Button("Afficher tous les clients");
        afficherButton.getStyleClass().add("button");
        Button detailButton = new Button("Afficher un client spécifique");
        detailButton.getStyleClass().add("button");
        Button modifierButton = new Button("Modifier un client");
        modifierButton.getStyleClass().add("button");
        Button supprimerButton = new Button("Supprimer un client");
        supprimerButton.getStyleClass().add("button");
        Button retourButton = new Button("Retour au menu principal");
        retourButton.getStyleClass().add("button");
        
        retourButton.setOnAction(e -> showMainMenu(primaryStage));
        
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
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
    }
    
    private void showAjouterClientDialog() {
        Dialog<ClientRestau> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un client");
        dialog.setHeaderText("Saisissez les informations du nouveau client");
        
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        
        ButtonType ajouterButtonType = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(ajouterButtonType, ButtonType.CANCEL);
        
        Button ajouterButton = (Button) dialogPane.lookupButton(ajouterButtonType);
        ajouterButton.getStyleClass().addAll("button", "primary-button");
        Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
        cancelButton.getStyleClass().add("button");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField idField = new TextField();
        idField.setPromptText("ID du client");
        idField.getStyleClass().add("text-field");
        TextField nomField = new TextField();
        nomField.setPromptText("Nom du client");
        nomField.getStyleClass().add("text-field");
        TextField prenomField = new TextField();
        prenomField.setPromptText("Prénom du client");
        prenomField.getStyleClass().add("text-field");
        
        Label idLabel = new Label("ID:");
        idLabel.getStyleClass().add("icon-label");
        Label nomLabel = new Label("Nom:");
        nomLabel.getStyleClass().add("icon-label");
        Label prenomLabel = new Label("Prénom:");
        prenomLabel.getStyleClass().add("icon-label");
        
        grid.add(idLabel, 0, 0);
        grid.add(idField, 1, 0);
        grid.add(nomLabel, 0, 1);
        grid.add(nomField, 1, 1);
        grid.add(prenomLabel, 0, 2);
        grid.add(prenomField, 1, 2);
        
        dialogPane.setContent(grid);
        
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
                    showAlert(Alert.AlertType.ERROR, "Erreur", "L'ID doit être un nombre valide.");
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
    
    private void showListerClientsDialog() {
        List<ClientRestau> clients = ClientDAO.getAllClients();
        
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Liste des clients");
        dialog.setHeaderText("Voici tous les clients enregistrés");
        
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        dialogPane.getButtonTypes().add(ButtonType.OK);
        
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        okButton.getStyleClass().add("button");
        
        if (clients.isEmpty()) {
            Label emptyLabel = new Label("❌ Aucun client enregistré.");
            emptyLabel.getStyleClass().add("error-label");
            dialogPane.setContent(emptyLabel);
        } else {
            ListView<ClientRestau> clientListView = new ListView<>();
            clientListView.getStyleClass().add("list-view");
            clientListView.getItems().addAll(clients);
            clientListView.setCellFactory(lv -> new ListCell<ClientRestau>() {
                @Override
                protected void updateItem(ClientRestau item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.toString());
                    if (!empty) {
                        getStyleClass().add("list-cell");
                    }
                }
            });
            
            VBox vbox = new VBox(10);
            vbox.getChildren().add(clientListView);
            vbox.setPadding(new Insets(20));
            dialogPane.setContent(vbox);
            dialogPane.setPrefSize(400, 400);
        }
        
        dialog.showAndWait();
    }
    
    private void showClientDetailDialog() {
        List<ClientRestau> clients = ClientDAO.getAllClients();
        if (clients.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Information", "❌ Aucun client enregistré.");
            return;
        }
        
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Détail client");
        dialog.setHeaderText("Entrez l'ID du client");
        
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        
        ButtonType rechercherButtonType = new ButtonType("Rechercher", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(rechercherButtonType, ButtonType.CANCEL);
        
        Button rechercherButton = (Button) dialogPane.lookupButton(rechercherButtonType);
        rechercherButton.getStyleClass().addAll("button", "primary-button");
        Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
        cancelButton.getStyleClass().add("button");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField idField = new TextField();
        idField.setPromptText("ID du client");
        idField.getStyleClass().add("text-field");
        
        Label idLabel = new Label("ID:");
        idLabel.getStyleClass().add("icon-label");
        
        grid.add(idLabel, 0, 0);
        grid.add(idField, 1, 0);
        dialogPane.setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == rechercherButtonType) {
                try {
                    return Integer.parseInt(idField.getText());
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "L'ID doit être un nombre valide.");
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
                detailAlert.getDialogPane().getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                detailAlert.getDialogPane().getStyleClass().add("dialog-pane");
                detailAlert.setTitle("Détail du client");
                detailAlert.setHeaderText("Informations du client #" + id);
                Label contentLabel = new Label(client.toString());
                contentLabel.getStyleClass().add("icon-label");
                detailAlert.getDialogPane().setContent(contentLabel);
                
                Button okButton = (Button) detailAlert.getDialogPane().lookupButton(ButtonType.OK);
                okButton.getStyleClass().add("button");
                detailAlert.showAndWait();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "❌ Client non trouvé.");
            }
        });
    }
    
    private void showModifierClientDialog() {
        List<ClientRestau> clients = ClientDAO.getAllClients();
        if (clients.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Information", "❌ Aucun client enregistré.");
            return;
        }
        
        Dialog<ClientRestau> selectionDialog = new Dialog<>();
        selectionDialog.setTitle("Modifier un client");
        selectionDialog.setHeaderText("Sélectionnez le client à modifier");
        
        DialogPane selectionPane = selectionDialog.getDialogPane();
        selectionPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        selectionPane.getStyleClass().add("dialog-pane");
        
        ButtonType selectionnerButtonType = new ButtonType("Sélectionner", ButtonBar.ButtonData.OK_DONE);
        selectionPane.getButtonTypes().addAll(selectionnerButtonType, ButtonType.CANCEL);
        
        Button selectionnerButton = (Button) selectionPane.lookupButton(selectionnerButtonType);
        selectionnerButton.getStyleClass().addAll("button", "primary-button");
        Button cancelButton1 = (Button) selectionPane.lookupButton(ButtonType.CANCEL);
        cancelButton1.getStyleClass().add("button");
        
        ComboBox<ClientRestau> clientsCombo = new ComboBox<>();
        clientsCombo.getItems().addAll(clients);
        clientsCombo.setPromptText("Sélectionnez un client");
        clientsCombo.getStyleClass().add("combo-box");
        
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
        selectionPane.setContent(vbox);
        
        selectionDialog.setResultConverter(dialogButton -> {
            if (dialogButton == selectionnerButtonType) {
                return clientsCombo.getValue();
            }
            return null;
        });
        
        Optional<ClientRestau> selectionResult = selectionDialog.showAndWait();
        selectionResult.ifPresent(clientSelectionne -> {
            Dialog<ClientRestau> modifDialog = new Dialog<>();
            modifDialog.setTitle("Modifier un client");
            modifDialog.setHeaderText("Modifiez les informations du client");
            
            DialogPane modifPane = modifDialog.getDialogPane();
            modifPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            modifPane.getStyleClass().add("dialog-pane");
            
            ButtonType modifierButtonType = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
            modifPane.getButtonTypes().addAll(modifierButtonType, ButtonType.CANCEL);
            
            Button modifierButton = (Button) modifPane.lookupButton(modifierButtonType);
            modifierButton.getStyleClass().addAll("button", "primary-button");
            Button cancelButton2 = (Button) modifPane.lookupButton(ButtonType.CANCEL);
            cancelButton2.getStyleClass().add("button");
            
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            
            TextField nomField = new TextField(clientSelectionne.getNom());
            nomField.getStyleClass().add("text-field");
            TextField prenomField = new TextField(clientSelectionne.getPrenom());
            prenomField.getStyleClass().add("text-field");
            
            Label nomLabel = new Label("Nom:");
            nomLabel.getStyleClass().add("icon-label");
            Label prenomLabel = new Label("Prénom:");
            prenomLabel.getStyleClass().add("icon-label");
            
            grid.add(nomLabel, 0, 0);
            grid.add(nomField, 1, 0);
            grid.add(prenomLabel, 0, 1);
            grid.add(prenomField, 1, 1);
            modifPane.setContent(grid);
            
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
                    showAlert(Alert.AlertType.ERROR, "Erreur", "❌ Erreur lors de la modification.");
                }
            });
        });
    }
    
    private void showSupprimerClientDialog() {
        List<ClientRestau> clients = ClientDAO.getAllClients();
        if (clients.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Information", "❌ Aucun client enregistré.");
            return;
        }
        
        Dialog<ClientRestau> dialog = new Dialog<>();
        dialog.setTitle("Supprimer un client");
        dialog.setHeaderText("Sélectionnez le client à supprimer");
        
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        
        ButtonType supprimerButtonType = new ButtonType("Supprimer", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(supprimerButtonType, ButtonType.CANCEL);
        
        Button supprimerButton = (Button) dialogPane.lookupButton(supprimerButtonType);
        supprimerButton.getStyleClass().addAll("button", "primary-button");
        Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
        cancelButton.getStyleClass().add("button");
        
        ComboBox<ClientRestau> clientsCombo = new ComboBox<>();
        clientsCombo.getItems().addAll(clients);
        clientsCombo.setPromptText("Sélectionnez un client");
        clientsCombo.getStyleClass().add("combo-box");
        
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
        dialogPane.setContent(vbox);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == supprimerButtonType) {
                return clientsCombo.getValue();
            }
            return null;
        });
        
        Optional<ClientRestau> result = dialog.showAndWait();
        result.ifPresent(client -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.getDialogPane().getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            confirm.getDialogPane().getStyleClass().add("dialog-pane");
            confirm.setTitle("Confirmation");
            confirm.setHeaderText("Êtes-vous sûr de vouloir supprimer ce client ?");
            Label contentLabel = new Label(client.toString());
            contentLabel.getStyleClass().add("icon-label");
            confirm.getDialogPane().setContent(contentLabel);
            
            Button okButton = (Button) confirm.getDialogPane().lookupButton(ButtonType.OK);
            okButton.getStyleClass().add("button");
            Button cancelButton2 = (Button) confirm.getDialogPane().lookupButton(ButtonType.CANCEL);
            cancelButton2.getStyleClass().add("button");
            
            Optional<ButtonType> confirmation = confirm.showAndWait();
            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                boolean success = ClientDAO.deleteClient(client.getId());
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "✅ Client supprimé avec succès !");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "❌ Erreur lors de la suppression.");
                }
            }
        });
    }
    
    private void showGestionPersonnels(Stage primaryStage) {
        VBox layout = createModuleLayout("Gestion du Personnel");
        
        Button ajouterButton = new Button("Ajouter un personnel");
        ajouterButton.getStyleClass().add("button");
        Button afficherButton = new Button("Afficher tous les personnels");
        afficherButton.getStyleClass().add("button");
        Button detailButton = new Button("Afficher un personnel spécifique");
        detailButton.getStyleClass().add("button");
        Button modifierButton = new Button("Modifier un personnel");
        modifierButton.getStyleClass().add("button");
        Button supprimerButton = new Button("Supprimer un personnel");
        supprimerButton.getStyleClass().add("button");
        Button retourButton = new Button("Retour au menu principal");
        retourButton.getStyleClass().add("button");
        
        retourButton.setOnAction(e -> showMainMenu(primaryStage));
        
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
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
    }
    
    private void showAjouterPersonnelDialog() {
        Dialog<PersonnelRestau> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un personnel");
        dialog.setHeaderText("Saisissez les informations du nouveau personnel");
        
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        
        ButtonType ajouterButtonType = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(ajouterButtonType, ButtonType.CANCEL);
        
        Button ajouterButton = (Button) dialogPane.lookupButton(ajouterButtonType);
        ajouterButton.getStyleClass().addAll("button", "primary-button");
        Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
        cancelButton.getStyleClass().add("button");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField idField = new TextField();
        idField.setPromptText("ID du personnel");
        idField.getStyleClass().add("text-field");
        TextField nomField = new TextField();
        nomField.setPromptText("Nom du personnel");
        nomField.getStyleClass().add("text-field");
        TextField prenomField = new TextField();
        prenomField.setPromptText("Prénom du personnel");
        prenomField.getStyleClass().add("text-field");
        TextField posteField = new TextField();
        posteField.setPromptText("Poste occupé");
        posteField.getStyleClass().add("text-field");
        TextField salaireField = new TextField();
        salaireField.setPromptText("Salaire en FCFA");
        salaireField.getStyleClass().add("text-field");
        
        Label idLabel = new Label("ID:");
        idLabel.getStyleClass().add("icon-label");
        Label nomLabel = new Label("Nom:");
        nomLabel.getStyleClass().add("icon-label");
        Label prenomLabel = new Label("Prénom:");
        prenomLabel.getStyleClass().add("icon-label");
        Label posteLabel = new Label("Poste:");
        posteLabel.getStyleClass().add("icon-label");
        Label salaireLabel = new Label("Salaire:");
        salaireLabel.getStyleClass().add("icon-label");
        
        grid.add(idLabel, 0, 0);
        grid.add(idField, 1, 0);
        grid.add(nomLabel, 0, 1);
        grid.add(nomField, 1, 1);
        grid.add(prenomLabel, 0, 2);
        grid.add(prenomField, 1, 2);
        grid.add(posteLabel, 0, 3);
        grid.add(posteField, 1, 3);
        grid.add(salaireLabel, 0, 4);
        grid.add(salaireField, 1, 4);
        
        dialogPane.setContent(grid);
        
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
                    showAlert(Alert.AlertType.ERROR, "Erreur", "L'ID et le salaire doivent être des nombres valides.");
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
    
    private void showListerPersonnelsDialog() {
        List<PersonnelRestau> personnels = PersonnelDAO.getAllPersonnels();
        
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Liste du personnel");
        dialog.setHeaderText("Voici tous les membres du personnel");
        
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        dialogPane.getButtonTypes().add(ButtonType.OK);
        
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        okButton.getStyleClass().add("button");
        
        if (personnels.isEmpty()) {
            Label emptyLabel = new Label("❌ Aucun personnel enregistré.");
            emptyLabel.getStyleClass().add("error-label");
            dialogPane.setContent(emptyLabel);
        } else {
            ListView<PersonnelRestau> personnelListView = new ListView<>();
            personnelListView.getStyleClass().add("list-view");
            personnelListView.getItems().addAll(personnels);
            personnelListView.setCellFactory(lv -> new ListCell<PersonnelRestau>() {
                @Override
                protected void updateItem(PersonnelRestau item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.toString());
                    if (!empty) {
                        getStyleClass().add("list-cell");
                    }
                }
            });
            
            VBox vbox = new VBox(10);
            vbox.getChildren().add(personnelListView);
            vbox.setPadding(new Insets(20));
            dialogPane.setContent(vbox);
            dialogPane.setPrefSize(500, 400);
        }
        
        dialog.showAndWait();
    }
    
    private void showPersonnelDetailDialog() {
        List<PersonnelRestau> personnels = PersonnelDAO.getAllPersonnels();
        if (personnels.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Information", "❌ Aucun personnel enregistré.");
            return;
        }
        
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Détail personnel");
        dialog.setHeaderText("Entrez l'ID du personnel");
        
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        
        ButtonType rechercherButtonType = new ButtonType("Rechercher", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(rechercherButtonType, ButtonType.CANCEL);
        
        Button rechercherButton = (Button) dialogPane.lookupButton(rechercherButtonType);
        rechercherButton.getStyleClass().addAll("button", "primary-button");
        Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
        cancelButton.getStyleClass().add("button");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField idField = new TextField();
        idField.setPromptText("ID du personnel");
        idField.getStyleClass().add("text-field");
        
        Label idLabel = new Label("ID:");
        idLabel.getStyleClass().add("icon-label");
        
        grid.add(idLabel, 0, 0);
        grid.add(idField, 1, 0);
        dialogPane.setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == rechercherButtonType) {
                try {
                    return Integer.parseInt(idField.getText());
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "L'ID doit être un nombre valide.");
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
                
                DialogPane detailPane = detailDialog.getDialogPane();
                detailPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                detailPane.getStyleClass().add("dialog-pane");
                detailPane.getButtonTypes().add(ButtonType.OK);
                
                Button okButton = (Button) detailPane.lookupButton(ButtonType.OK);
                okButton.getStyleClass().add("button");
                
                GridPane detailGrid = new GridPane();
                detailGrid.setHgap(10);
                detailGrid.setVgap(10);
                detailGrid.setPadding(new Insets(20, 20, 10, 10));
                
                Label idDetailLabel = new Label("ID:");
                idDetailLabel.getStyleClass().add("icon-label");
                Label nomLabel = new Label("Nom:");
                nomLabel.getStyleClass().add("icon-label");
                Label prenomLabel = new Label("Prénom:");
                prenomLabel.getStyleClass().add("icon-label");
                Label posteLabel = new Label("Poste:");
                posteLabel.getStyleClass().add("icon-label");
                Label salaireLabel = new Label("Salaire:");
                salaireLabel.getStyleClass().add("icon-label");
                
                detailGrid.add(idDetailLabel, 0, 0);
                detailGrid.add(new Label(String.valueOf(personnel.getId())), 1, 0);
                detailGrid.add(nomLabel, 0, 1);
                detailGrid.add(new Label(personnel.getNom()), 1, 1);
                detailGrid.add(prenomLabel, 0, 2);
                detailGrid.add(new Label(personnel.getPrenom()), 1, 2);
                detailGrid.add(posteLabel, 0, 3);
                detailGrid.add(new Label(personnel.getPoste()), 1, 3);
                detailGrid.add(salaireLabel, 0, 4);
                detailGrid.add(new Label(personnel.getSalaire() + " FCFA"), 1, 4);
                
                detailPane.setContent(detailGrid);
                detailDialog.showAndWait();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "❌ Personnel non trouvé.");
            }
        });
    }
    
    private void showModifierPersonnelDialog() {
        List<PersonnelRestau> personnels = PersonnelDAO.getAllPersonnels();
        if (personnels.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Information", "❌ Aucun personnel enregistré.");
            return;
        }
        
        Dialog<PersonnelRestau> selectionDialog = new Dialog<>();
        selectionDialog.setTitle("Modifier un personnel");
        selectionDialog.setHeaderText("Sélectionnez le personnel à modifier");
        
        DialogPane selectionPane = selectionDialog.getDialogPane();
        selectionPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        selectionPane.getStyleClass().add("dialog-pane");
        
        ButtonType selectionnerButtonType = new ButtonType("Sélectionner", ButtonBar.ButtonData.OK_DONE);
        selectionPane.getButtonTypes().addAll(selectionnerButtonType, ButtonType.CANCEL);
        
        Button selectionnerButton = (Button) selectionPane.lookupButton(selectionnerButtonType);
        selectionnerButton.getStyleClass().addAll("button", "primary-button");
        Button cancelButton1 = (Button) selectionPane.lookupButton(ButtonType.CANCEL);
        cancelButton1.getStyleClass().add("button");
        
        ComboBox<PersonnelRestau> personnelsCombo = new ComboBox<>();
        personnelsCombo.getItems().addAll(personnels);
        personnelsCombo.setPromptText("Sélectionnez un personnel");
        personnelsCombo.getStyleClass().add("combo-box");
        
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
        selectionPane.setContent(vbox);
        
        selectionDialog.setResultConverter(dialogButton -> {
            if (dialogButton == selectionnerButtonType) {
                return personnelsCombo.getValue();
            }
            return null;
        });
        
        Optional<PersonnelRestau> selectionResult = selectionDialog.showAndWait();
        selectionResult.ifPresent(personnelSelectionne -> {
            Dialog<PersonnelRestau> modifDialog = new Dialog<>();
            modifDialog.setTitle("Modifier un personnel");
            modifDialog.setHeaderText("Modifiez les informations du personnel");
            
            DialogPane modifPane = modifDialog.getDialogPane();
            modifPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            modifPane.getStyleClass().add("dialog-pane");
            
            ButtonType modifierButtonType = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
            modifPane.getButtonTypes().addAll(modifierButtonType, ButtonType.CANCEL);
            
            Button modifierButton = (Button) modifPane.lookupButton(modifierButtonType);
            modifierButton.getStyleClass().addAll("button", "primary-button");
            Button cancelButton2 = (Button) modifPane.lookupButton(ButtonType.CANCEL);
            cancelButton2.getStyleClass().add("button");
            
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            
            TextField nomField = new TextField(personnelSelectionne.getNom());
            nomField.getStyleClass().add("text-field");
            TextField prenomField = new TextField(personnelSelectionne.getPrenom());
            prenomField.getStyleClass().add("text-field");
            TextField posteField = new TextField(personnelSelectionne.getPoste());
            posteField.getStyleClass().add("text-field");
            TextField salaireField = new TextField(String.valueOf(personnelSelectionne.getSalaire()));
            salaireField.getStyleClass().add("text-field");
            
            Label nomLabel = new Label("Nom:");
            nomLabel.getStyleClass().add("icon-label");
            Label prenomLabel = new Label("Prénom:");
            prenomLabel.getStyleClass().add("icon-label");
            Label posteLabel = new Label("Poste:");
            posteLabel.getStyleClass().add("icon-label");
            Label salaireLabel = new Label("Salaire:");
            salaireLabel.getStyleClass().add("icon-label");
            
            grid.add(nomLabel, 0, 0);
            grid.add(nomField, 1, 0);
            grid.add(prenomLabel, 0, 1);
            grid.add(prenomField, 1, 1);
            grid.add(posteLabel, 0, 2);
            grid.add(posteField, 1, 2);
            grid.add(salaireLabel, 0, 3);
            grid.add(salaireField, 1, 3);
            
            modifPane.setContent(grid);
            
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
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Le salaire doit être un nombre valide.");
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
                    showAlert(Alert.AlertType.ERROR, "Erreur", "❌ Erreur lors de la modification.");
                }
            });
        });
    }
    
    private void showSupprimerPersonnelDialog() {
        List<PersonnelRestau> personnels = PersonnelDAO.getAllPersonnels();
        if (personnels.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Information", "❌ Aucun personnel enregistré.");
            return;
        }
        
        Dialog<PersonnelRestau> dialog = new Dialog<>();
        dialog.setTitle("Supprimer un personnel");
        dialog.setHeaderText("Sélectionnez le personnel à supprimer");
        
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        
        ButtonType supprimerButtonType = new ButtonType("Supprimer", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(supprimerButtonType, ButtonType.CANCEL);
        
        Button supprimerButton = (Button) dialogPane.lookupButton(supprimerButtonType);
        supprimerButton.getStyleClass().addAll("button", "primary-button");
        Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
        cancelButton.getStyleClass().add("button");
        
        ComboBox<PersonnelRestau> personnelsCombo = new ComboBox<>();
        personnelsCombo.getItems().addAll(personnels);
        personnelsCombo.setPromptText("Sélectionnez un personnel");
        personnelsCombo.getStyleClass().add("combo-box");
        
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
        dialogPane.setContent(vbox);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == supprimerButtonType) {
                return personnelsCombo.getValue();
            }
            return null;
        });
        
        Optional<PersonnelRestau> result = dialog.showAndWait();
        result.ifPresent(personnel -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.getDialogPane().getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            confirm.getDialogPane().getStyleClass().add("dialog-pane");
            confirm.setTitle("Confirmation");
            confirm.setHeaderText("Êtes-vous sûr de vouloir supprimer ce personnel ?");
            Label contentLabel = new Label(personnel.toString());
            contentLabel.getStyleClass().add("icon-label");
            confirm.getDialogPane().setContent(contentLabel);
            
            Button okButton = (Button) confirm.getDialogPane().lookupButton(ButtonType.OK);
            okButton.getStyleClass().add("button");
            Button cancelButton2 = (Button) confirm.getDialogPane().lookupButton(ButtonType.CANCEL);
            cancelButton2.getStyleClass().add("button");
            
            Optional<ButtonType> confirmation = confirm.showAndWait();
            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                boolean success = PersonnelDAO.deletePersonnel(personnel.getId());
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "✅ Personnel supprimé avec succès !");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "❌ Erreur lors de la suppression.");
                }
            }
        });
    }
    
    private VBox createModuleLayout(String title) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("title");
        
        layout.getChildren().addAll(titleLabel, new Separator());
        return layout;
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        
        Label contentLabel = new Label(content);
        contentLabel.getStyleClass().add(alertType == Alert.AlertType.ERROR ? "error-label" : "icon-label");
        dialogPane.setContent(contentLabel);
        
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        if (okButton != null) {
            okButton.getStyleClass().add("button");
        }
        
        alert.showAndWait();
    }
    
    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(250);
        button.getStyleClass().add("button");
        return button;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}