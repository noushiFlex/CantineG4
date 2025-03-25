import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Sign {
    private Stage stage;
    private Main mainApp;

    public Sign(Stage stage, Main mainApp) {
        this.stage = stage;
        this.mainApp = mainApp;
    }

    public void showSignUpScene() {
        Label title = new Label("Sign up");
        title.getStyleClass().add("text-wrapper-12");

        Image groupnn2Image = new Image("file:///C:/Users/kotch/git_clone/CantineG4/groupnn-2.png");
        ImageView groupnn2View = new ImageView(groupnn2Image);
        groupnn2View.setFitWidth(56);
        groupnn2View.setFitHeight(57);

        HBox header = new HBox(10, groupnn2View, title);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(4, 0, 0, 8));

        VBox formLayout = new VBox(10);
        formLayout.setPadding(new Insets(20, 15, 0, 15));
        formLayout.setMaxWidth(330);

        Label nameLabel = new Label("Name");
        nameLabel.getStyleClass().add("text-wrapper-2");
        TextField nameField = new TextField();
        nameField.setPromptText("Adarsh");
        nameField.getStyleClass().add("overlap-group-2");

        Label lastNameLabel = new Label("Last name");
        lastNameLabel.getStyleClass().add("text-wrapper-2");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("uk");
        lastNameField.getStyleClass().add("overlap-group-2");

        HBox nameRow = new HBox(22, new VBox(nameLabel, nameField), new VBox(lastNameLabel, lastNameField));
        nameRow.setAlignment(Pos.CENTER_LEFT);

        Label emailLabel = new Label("Email ID");
        emailLabel.getStyleClass().add("text-wrapper-2");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter emailID");
        emailField.getStyleClass().add("overlap-group");

        Label houseLabel = new Label("House no./House name");
        houseLabel.getStyleClass().add("text-wrapper-2");
        TextField houseField = new TextField();
        houseField.setPromptText("Enter house name");
        houseField.getStyleClass().add("div-wrapper");

        Label streetLabel = new Label("Street name");
        streetLabel.getStyleClass().add("text-wrapper-3");
        TextField streetField = new TextField();
        streetField.setPromptText("Enter street name");
        streetField.getStyleClass().add("overlap-group-2");

        HBox addressRow = new HBox(12, new VBox(houseLabel, houseField), new VBox(streetLabel, streetField));
        addressRow.setAlignment(Pos.CENTER_LEFT);

        Label stateLabel = new Label("State");
        stateLabel.getStyleClass().add("text-wrapper-2");
        TextField stateField = new TextField();
        stateField.setPromptText("Select");
        stateField.getStyleClass().add("overlap-group-2");

        Label cityLabel = new Label("City");
        cityLabel.getStyleClass().add("text-wrapper-6");
        TextField cityField = new TextField();
        cityField.setPromptText("Select");
        cityField.getStyleClass().add("overlap-group-2");

        HBox locationRow = new HBox(12, new VBox(stateLabel, stateField), new VBox(cityLabel, cityField));
        locationRow.setAlignment(Pos.CENTER_LEFT);

        Label pincodeLabel = new Label("Pincode");
        pincodeLabel.getStyleClass().add("text-wrapper-2");
        TextField pincodeField = new TextField();
        pincodeField.setPromptText("Enter pincode");
        pincodeField.getStyleClass().add("group-9");

        Label phoneLabel = new Label("Phone no.");
        phoneLabel.getStyleClass().add("text-wrapper-7");
        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter Phone number");
        phoneField.getStyleClass().add("overlap-group-3");

        HBox contactRow = new HBox(12, new VBox(pincodeLabel, pincodeField), new VBox(phoneLabel, phoneField));
        contactRow.setAlignment(Pos.CENTER_LEFT);

        Label passwordLabel = new Label("Password");
        passwordLabel.getStyleClass().add("text-wrapper-2");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.getStyleClass().add("enter-password-wrapper");

        Label confirmPasswordLabel = new Label("Confirm Password");
        confirmPasswordLabel.getStyleClass().add("text-wrapper-2");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Enter Password");
        confirmPasswordField.getStyleClass().add("enter-password-wrapper");

        Button doneButton = new Button("Done");
        doneButton.getStyleClass().add("overlap");
        doneButton.setOnAction(e -> {
            try {
                Shop shopPage = new Shop(stage, mainApp);
                shopPage.showShopScene();
            } catch (Exception ex) {
                System.err.println("Erreur lors de l'ouverture de Shop : " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        Button backButton = new Button("Back to Login");
        backButton.setOnAction(e -> mainApp.showLoginScene());

        VBox buttonLayout = new VBox(10, doneButton, backButton);
        buttonLayout.setAlignment(Pos.CENTER);

        formLayout.getChildren().addAll(
            nameRow, emailLabel, emailField, addressRow, locationRow, contactRow,
            passwordLabel, passwordField, confirmPasswordLabel, confirmPasswordField, buttonLayout
        );

        VBox mainLayout = new VBox(20, header, formLayout);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(0, 0, 20, 0));

        StackPane root = new StackPane(mainLayout);
        Scene signUpScene = new Scene(root, 360, 640);
        signUpScene.getStylesheets().add("file:///C:/Users/kotch/git_clone/CantineG4/style_sign.css");
        stage.setTitle("Inscription - CantineG4");
        stage.setScene(signUpScene);
        stage.show();
    }
}