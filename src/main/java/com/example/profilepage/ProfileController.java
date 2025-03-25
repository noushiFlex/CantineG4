package com.example.profilepage;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import java.io.File;

public class ProfileController {

    @FXML private ImageView profileImage;
    @FXML private TextField txtNom, txtEmail, txtAdresse, txtTelephone;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnSave, btnChangeImage;
    @FXML private GridPane root;

    @FXML
    public void initialize() {
        chargerImageParDefaut();
        appliquerStylesCSS();

        // Événements des boutons
        btnSave.setOnAction(this::mettreAJourProfil);
        btnChangeImage.setOnAction(event -> uploadImage());
        chargerInformationsUtilisateur();
    }

    private void chargerImageParDefaut() {
        try {
            Image defaultImage = new Image(getClass().getResource("/images/profile.jpg").toExternalForm());
            profileImage.setImage(defaultImage);
        } catch (Exception e) {
            System.out.println("Image par défaut introuvable.");
        }
    }

    private void appliquerStylesCSS() {
        if (root != null) {
            root.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    newScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
                }
            });
        } else {
            System.out.println("Erreur : root est null.");
        }
    }

    private void chargerInformationsUtilisateur() {
        // Simulation du chargement des infos utilisateur
        txtNom.setText("John Doe");
        txtEmail.setText("john.doe@example.com");
        txtAdresse.setText("123 Rue Exemple");
        txtTelephone.setText("0123456789");
        txtPassword.setText("password123");
    }

    private void mettreAJourProfil(ActionEvent event) {
        String nom = txtNom.getText();
        String email = txtEmail.getText();
        String adresse = txtAdresse.getText();
        String telephone = txtTelephone.getText();
        String password = txtPassword.getText();

        if (nom.isEmpty() || email.isEmpty() || adresse.isEmpty() || telephone.isEmpty() || password.isEmpty()) {
            afficherAlerte("Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        // Logique de mise à jour du profil (ex: sauvegarde en base de données)
        System.out.println("Profil mis à jour : " + nom + " (" + email + ")");

        afficherAlerte("Succès", "Profil mis à jour avec succès !");
    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null && file.exists()) {
            profileImage.setImage(new Image(file.toURI().toString()));
        } else {
            System.out.println("Aucune image sélectionnée ou fichier invalide.");
        }
    }
}
