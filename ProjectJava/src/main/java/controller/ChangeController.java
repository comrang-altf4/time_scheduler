package controller;

import backend.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import project.Main;
import transition.AnimationFX;

import java.io.IOException;
import java.sql.SQLException;

public class ChangeController {
    @FXML private AnchorPane anchorPane;
    @FXML private PasswordField password;
    @FXML private PasswordField repeat;

    @FXML private void onHomeAction(ActionEvent event) throws IOException {
        AnimationFX.transitionBackward("/login-view.fxml", anchorPane);
    }


    @FXML private void onConfirm() throws SQLException, ClassNotFoundException, IOException {
        if(password.getText().length()<8)
            System.out.println("Password must be at least 8 characters");
        else {
            if(!password.getText().equals(repeat.getText()))
                System.out.println("Password and Repeat password must be the same");
            else {
                Database.changePassword(Main.getSession().getEmail(), password.getText());
                AnimationFX.transitionForward("/login-view.fxml", anchorPane);
            }
        }
    }
}
