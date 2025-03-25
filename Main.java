import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        showLoginScene();
    }

    public void showLoginScene() {
        Label title = new Label("BIENVENU\nCHEZ OLIVIA'S PLATS");
        title.getStyleClass().add("login-form");

        Image groupnnImage = new Image("file:///C:/Users/kotch/git_clone/CantineG4/Group94.png");
        ImageView groupnnView = new ImageView(groupnnImage);
        groupnnView.setFitWidth(240);
        groupnnView.setFitHeight(150);

        Image rectangle3Image = new Image("file:///C:/Users/kotch/git_clone/CantineG4/rectangle-3.png");
        ImageView rectangle3View = new ImageView(rectangle3Image);
        rectangle3View.setFitWidth(161);
        rectangle3View.setFitHeight(184);

        Image imageImage = new Image("file:///C:/Users/kotch/git_clone/CantineG4/image.png");
        ImageView imageView = new ImageView(imageImage);
        imageView.setFitWidth(129);
        imageView.setFitHeight(132);

        Image rectangle4Image = new Image("file:///C:/Users/kotch/git_clone/CantineG4/rectangle-4.png");
        ImageView rectangle4View = new ImageView(rectangle4Image);
        rectangle4View.setFitWidth(129);
        rectangle4View.setFitHeight(132);

        Label emailLabel = new Label("Email ID");
        emailLabel.getStyleClass().add("text-wrapper-2");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter emailID");
        emailField.getStyleClass().add("text-field");

        Label passLabel = new Label("Password");
        passLabel.getStyleClass().add("text-wrapper-2");
        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter Password");
        passField.getStyleClass().add("password-field");

        Button loginButton = new Button("Sign in");
        loginButton.getStyleClass().add("sign-in-button");
        Label resultLabel = new Label("");

        loginButton.setOnAction(e -> {
            String email = emailField.getText();
            String motDePasse = passField.getText();
            if (email.equals("admin") && motDePasse.equals("admin")) {
                resultLabel.setText("Connexion réussie !");
                resultLabel.getStyleClass().clear();
                resultLabel.getStyleClass().add("success");
                showMenuScene();
            } else {
                resultLabel.setText("Erreur : identifiants incorrects");
                resultLabel.getStyleClass().clear();
                resultLabel.getStyleClass().add("error");
            }
        });

        Label dontHaveLabel = new Label("Dont have a account? ");
        dontHaveLabel.getStyleClass().add("dont-have-a-account");
        Button signupButton = new Button("Sign up now");
        signupButton.getStyleClass().add("signup-link");
        signupButton.setOnAction(e -> {
            Sign signPage = new Sign(primaryStage, this);
            signPage.showSignUpScene();
        });

        VBox signupBox = new VBox(dontHaveLabel, signupButton);
        signupBox.setAlignment(Pos.CENTER);

        VBox formLayout = new VBox(10, emailLabel, emailField, passLabel, passField, loginButton, resultLabel);
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setMaxWidth(316);
        formLayout.setTranslateY(50);

        VBox mainLayout = new VBox(20, title, groupnnView, formLayout, signupBox);
        mainLayout.setAlignment(Pos.CENTER);

        StackPane root = new StackPane();
        root.getChildren().addAll(mainLayout, rectangle3View, imageView, rectangle4View);
        StackPane.setAlignment(rectangle3View, Pos.TOP_CENTER);
        rectangle3View.setTranslateY(-228);
        StackPane.setAlignment(imageView, Pos.TOP_LEFT);
        imageView.setTranslateX(-115);
        imageView.setTranslateY(-200);
        StackPane.setAlignment(rectangle4View, Pos.TOP_RIGHT);
        rectangle4View.setTranslateX(115);
        rectangle4View.setTranslateY(-200);

        Scene loginScene = new Scene(root, 360, 640);
        loginScene.getStylesheets().add("file:///C:/Users/kotch/git_clone/CantineG4/style.css");
        primaryStage.setTitle("Connexion - CantineG4");
        primaryStage.setScene(loginScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void showMenuScene() {
        Label title = new Label("Menu CantineG4");
        title.getStyleClass().add("title");

        Button platsButton = new Button("Gérer les plats 🍽️");
        platsButton.setOnAction(e -> showPlatsScene());

        Button commandesButton = new Button("Voir les commandes 🛒");
        commandesButton.setOnAction(e -> System.out.println("Commandes (à implémenter)"));

        Button clientsButton = new Button("Voir les clients 👥");
        clientsButton.setOnAction(e -> System.out.println("Clients (à implémenter)"));

        Button personnelButton = new Button("Voir le personnel 🧑‍🍳");
        personnelButton.setOnAction(e -> System.out.println("Personnel (à implémenter)"));

        Button quitterButton = new Button("Quitter ❌");
        quitterButton.setOnAction(e -> primaryStage.close());

        VBox menuLayout = new VBox(15, title, platsButton, commandesButton, clientsButton, personnelButton, quitterButton);
        menuLayout.setAlignment(Pos.CENTER);
        Scene menuScene = new Scene(menuLayout, 360, 640);
        menuScene.getStylesheets().add("file:///C:/Users/kotch/git_clone/CantineG4/style.css");
        primaryStage.setTitle("Menu - CantineG4");
        primaryStage.setScene(menuScene);
    }

    private void showPlatsScene() {
        Label title = new Label("Gestion des Plats 🍽️");
        title.getStyleClass().add("title");

        Label platsList = new Label("1. Pizza\n2. Burger\n3. Salade");
        Button addButton = new Button("Ajouter un plat");
        Button backButton = new Button("Retour au menu");
        backButton.setOnAction(e -> showMenuScene());

        VBox platsLayout = new VBox(15, title, platsList, addButton, backButton);
        platsLayout.setAlignment(Pos.CENTER);
        Scene platsScene = new Scene(platsLayout, 360, 640);
        platsScene.getStylesheets().add("file:///C:/Users/kotch/git_clone/CantineG4/style.css");
        primaryStage.setTitle("Gestion des Plats - CantineG4");
        primaryStage.setScene(platsScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}