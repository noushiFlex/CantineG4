package com.example.profilepage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Profile.fxml")));
        Scene scene = new Scene(root, 400, 500);
        primaryStage.setTitle("Gestion de Profil");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
