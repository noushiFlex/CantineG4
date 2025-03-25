module com.example.profilepage {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.profilepage to javafx.fxml;
    exports com.example.profilepage;
}