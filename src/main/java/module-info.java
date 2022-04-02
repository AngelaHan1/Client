module com.example.networkdemo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.networkdemo to javafx.fxml;
    exports com.example.networkdemo;
}