import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Shop {
    private Stage stage;
    private Main mainApp;

    public Shop(Stage stage, Main mainApp) {
        this.stage = stage;
        this.mainApp = mainApp;
    }

    public void showShopScene() {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: white; -fx-border-radius: 10;");

        // Image de fond
        Image backgroundImage = new Image("file:///C:/Users/kotch/git_clone/CantineG4/istockphoto-1367756031-612x612-1.png");
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(360);
        backgroundView.setFitHeight(529);
        StackPane.setAlignment(backgroundView, Pos.TOP_CENTER);
        backgroundView.setTranslateY(62);

        // Header
        HBox header = createHeader();
        StackPane.setAlignment(header, Pos.TOP_CENTER);

        // Contenu principal dans un ScrollPane
        VBox content = new VBox(10);
        content.getChildren().addAll(
            createSection("Near by Hotels", createHotelItems()),
            createSection("Most Rated Dishes", createRatedItems()),
            createSection("Combo offer", createComboItems())
        );
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(640);
        scrollPane.setStyle("-fx-background-color: transparent;");
        StackPane.setAlignment(scrollPane, Pos.CENTER);

        // Footer
        HBox footer = createFooter();
        StackPane.setAlignment(footer, Pos.BOTTOM_CENTER);

        root.getChildren().addAll(backgroundView, header, scrollPane, footer);
        Scene shopScene = new Scene(root, 360, 640);
        shopScene.getStylesheets().add("file:///C:/Users/kotch/git_clone/CantineG4/style_shop.css");
        stage.setTitle("Shop - CantineG4");
        stage.setScene(shopScene);
        stage.show();
    }

    private HBox createHeader() {
        Image groupnn2Image = new Image("file:///C:/Users/kotch/git_clone/CantineG4/groupnn-2.png");
        ImageView groupnn2View = new ImageView(groupnn2Image);
        groupnn2View.setFitWidth(35);
        groupnn2View.setFitHeight(36);

        Rectangle searchRect = new Rectangle(195, 27);
        searchRect.setStyle("-fx-fill: transparent; -fx-stroke: rgba(145.56, 142.77, 142.77, 0.50); -fx-arc-width: 20; -fx-arc-height: 20;");

        Image searchIconImage = new Image("file:///C:/Users/kotch/git_clone/CantineG4/ic-twotone-search.svg");
        ImageView searchIconView = new ImageView(searchIconImage);
        searchIconView.setFitWidth(11);
        searchIconView.setFitHeight(11);

        TextField searchField = new TextField();
        searchField.setPromptText("Search hotel/Food");
        searchField.setStyle("-fx-font-family: 'Itim'; -fx-font-size: 12px; -fx-text-fill: #BDBDBD; -fx-background-color: transparent;");
        searchField.setPrefWidth(160);

        HBox searchBox = new HBox(5, searchIconView, searchField);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setPadding(new Insets(0, 0, 0, 5));
        StackPane searchPane = new StackPane(searchRect, searchBox);
        searchPane.setAlignment(Pos.CENTER_LEFT);

        Image notificationImage = new Image("file:///C:/Users/kotch/git_clone/CantineG4/zondicons-notification.svg");
        ImageView notificationView = new ImageView(notificationImage);
        notificationView.setFitWidth(20);
        notificationView.setFitHeight(20);

        Image cartImage = new Image("file:///C:/Users/kotch/git_clone/CantineG4/vector-2.svg");
        ImageView cartView = new ImageView(cartImage);
        cartView.setFitWidth(20);
        cartView.setFitHeight(20);

        Image profileImage = new Image("file:///C:/Users/kotch/git_clone/CantineG4/ellipse-1.png");
        ImageView profileView = new ImageView(profileImage);
        profileView.setFitWidth(24);
        profileView.setFitHeight(24);

        HBox header = new HBox(10, groupnn2View, searchPane, notificationView, cartView, profileView);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(5, 0, 0, 16));
        return header;
    }

    private VBox createSection(String title, HBox items) {
        Label sectionTitle = new Label(title);
        sectionTitle.setStyle("-fx-font-family: 'Itim'; -fx-font-size: 10px; -fx-text-fill: black;");

        Label seeAll = new Label("See all");
        seeAll.setStyle("-fx-font-family: 'Itim'; -fx-font-size: 10px; -fx-text-fill: #928F8F;");

        HBox titleBox = new HBox(sectionTitle, seeAll);
        titleBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(sectionTitle, javafx.scene.layout.Priority.ALWAYS);
        seeAll.setPadding(new Insets(0, 0, 0, 200));

        VBox section = new VBox(5, titleBox, items);
        section.setPadding(new Insets(10));
        return section;
    }

    private HBox createHotelItems() {
        HBox items = new HBox(5);
        items.getChildren().addAll(
            createItem("SPRITE", "500FCFA", "file:///C:/Users/kotch/git_clone/CantineG4/sprite.png", 72, 78, "4.6"),
            createItem("COCA COLA", "500FCFA", "file:///C:/Users/kotch/git_clone/CantineG4/coca.png", 72, 78, "4.6"),
            createItem("Chicken Burger(medium)", "500FCFA", "file:///C:/Users/kotch/git_clone/CantineG4/burger.png", 64, 64, "4.6")
        );
        return items;
    }

    private HBox createRatedItems() {
        HBox items = new HBox(5);
        items.getChildren().addAll(
            createItem("Attieke poulet", "1000FCFA", "file:///C:/Users/kotch/git_clone/CantineG4/attieke.png", 93, 60, "4.9"),
            createItem("Alloco poulet", "1000FCFA", "file:///C:/Users/kotch/git_clone/CantineG4/alloco_poulet.png", 73, 57, "4.9"),
            createItem("Alloco simple", "500FCFA", "file:///C:/Users/kotch/git_clone/CantineG4/alloco.png", 67, 57, "4.8")
        );
        return items;
    }

    private HBox createComboItems() {
        HBox items = new HBox(5);
        items.getChildren().addAll(
            createItem("bouteille d’eau", "100FCFA", "file:///C:/Users/kotch/git_clone/CantineG4/water.png", 68, 72, "4.9"),
            createItem("LAIT CAILLE", "100FCFA", "file:///C:/Users/kotch/git_clone/CantineG4/lait.png", 84, 47, "4.9"),
            createItem("Attike poulet", "1000FCFA", "file:///C:/Users/kotch/git_clone/CantineG4/attieke.png", 93, 60, "4.9")
        );
        return items;
    }

    private VBox createItem(String name, String price, String imagePath, double imgWidth, double imgHeight, String rating) {
        VBox card = new VBox(5);
        card.setPrefSize(116, 147);
        card.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.25), 10, 0, 0, 4);");

        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitWidth(imgWidth);
        imageView.setFitHeight(imgHeight);
        imageView.setTranslateX((116 - imgWidth) / 2);

        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-family: 'Itim'; -fx-font-size: 10px; -fx-text-fill: black;");

        Label vendorLabel = new Label("OLIVIA’S PLATS");
        vendorLabel.setStyle("-fx-font-family: 'Itim'; -fx-font-size: 8px; -fx-text-fill: #928F8F;");

        Label priceLabel = new Label(price);
        priceLabel.setStyle("-fx-font-family: 'Itim'; -fx-font-size: 10px; -fx-text-fill: black;");

        Button addButton = new Button("Add to cart");
        addButton.setStyle("-fx-background-color: #EB4771; -fx-background-radius: 10; -fx-font-family: 'Itim'; -fx-font-size: 8px; -fx-text-fill: white;");
        addButton.setPrefSize(59, 14);
        addButton.setOnAction(e -> System.out.println("Added " + name + " to cart"));

        Label ratingLabel = new Label(rating);
        ratingLabel.setStyle("-fx-font-family: 'Itim'; -fx-font-size: 7px; -fx-text-fill: #928F8F;");

        Rectangle star = new Rectangle(6.67, 6.33);
        star.setStyle("-fx-fill: #E2E52E;");
        HBox ratingBox = new HBox(2, star, ratingLabel);
        ratingBox.setAlignment(Pos.CENTER_LEFT);

        VBox details = new VBox(2, nameLabel, vendorLabel, priceLabel, addButton);
        details.setAlignment(Pos.CENTER_LEFT);
        details.setPadding(new Insets(0, 0, 0, 10));

        card.getChildren().addAll(imageView, ratingBox, details);
        card.setAlignment(Pos.TOP_CENTER);
        return card;
    }

    private StackPane createFooter() {
        Rectangle footerRect = new Rectangle(195, 27);
        footerRect.setStyle("-fx-fill: transparent; -fx-stroke: #D9D9D9; -fx-arc-width: 20; -fx-arc-height: 20;");

        Image chatImage = new Image("file:///C:/Users/kotch/git_clone/CantineG4/vector-3.svg");
        ImageView chatView = new ImageView(chatImage);
        chatView.setFitWidth(20);
        chatView.setFitHeight(20);
        Circle chatDot = new Circle(2.5, javafx.scene.paint.Color.web("#EB4771"));
        StackPane chatBox = new StackPane(chatView, chatDot);
        chatBox.setAlignment(Pos.CENTER);
        chatDot.setTranslateX(7);
        chatDot.setTranslateY(-5);

        Circle homeCircle = new Circle(12.5, javafx.scene.paint.Color.web("#EBB1C0"));
        Image homeImage = new Image("file:///C:/Users/kotch/git_clone/CantineG4/ic-round-home.svg");
        ImageView homeView = new ImageView(homeImage);
        homeView.setFitWidth(19);
        homeView.setFitHeight(17);
        StackPane homeBox = new StackPane(homeCircle, homeView);
        homeBox.setAlignment(Pos.CENTER);

        Image profileImage = new Image("file:///C:/Users/kotch/git_clone/CantineG4/vector-4.svg");
        ImageView profileView = new ImageView(profileImage);
        profileView.setFitWidth(18);
        profileView.setFitHeight(18);

        HBox footer = new HBox(40, chatBox, homeBox, profileView);
        footer.setAlignment(Pos.CENTER);
        StackPane footerContainer = new StackPane(footerRect, footer);
        footerContainer.setAlignment(Pos.CENTER);
        footerContainer.setPadding(new Insets(0, 0, 10, 0));
        return footerContainer;
    }
}