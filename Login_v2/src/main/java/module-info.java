module com.example.login_v2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;
    requires mysql.connector.java;
    requires java.mail;


    opens com.example.login_v2 to javafx.fxml;
    exports com.example.login_v2;
}