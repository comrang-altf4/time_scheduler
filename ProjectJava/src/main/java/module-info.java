module project.projectjava {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires jBCrypt;
    requires java.mail;
    requires mysql.connector.java;

    opens project to javafx.fxml;
    opens controller to javafx.fxml;
    exports project;
    exports controller;
}