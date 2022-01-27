package controller;

import backend.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import project.Main;
import transition.AnimationFX;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private AnchorPane anchorPane;
    @FXML private void onLoginAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(username.getText()==null||username.getText().isEmpty()||password.getText()==null||password.getText().isEmpty())
            System.out.println("Wrong username/password!!!");
        else {
            boolean flag = Database.login(username.getText(), password.getText());
            if(flag) {
                // Login successfully
                Main.getSession().setUsername(username.getText());
                Main.getSession().setPassword(password.getText());
                Main.getSession().setEmail(Database.getEmail(username.getText()));

                System.out.println("Welcome!!!");
            }
            else
                System.out.println("Wrong username/password!!!");
        }
    }
    @FXML private void onForgetAction(ActionEvent event) throws IOException {
        AnimationFX.transitionForward("/forget-view.fxml", anchorPane);
    }

    @FXML private void onRegisterAction(ActionEvent event) throws IOException {
        AnimationFX.transitionForward("/register-view.fxml", anchorPane);
    }
}
