/**
 * This class is the controller for the change password scene
 * @author Huy To Quang and Tan Nang Le
 */
package controller;

import backend.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import project.Main;
import transition.AnimationFX;

import java.io.IOException;
import java.sql.SQLException;

public class ChangeController {
    @FXML private AnchorPane anchorPane;
    @FXML private PasswordField password;
    @FXML private PasswordField repeat;
    @FXML private Label passwordMessage;
    @FXML private Label repeatPasswordMessage;

    /**
     * This function changes back to log in screen
     * @param event signal of action on home button
     * @throws IOException whenever there is a problem with file loading
     */
    @FXML private void onHomeAction(ActionEvent event) throws IOException {
        AnimationFX.transitionBackward("/login-view.fxml", anchorPane);
    }


    /**
     * This function changes back to log in screen after new password has been changed
     * @throws SQLException whenever there is a problem with database
     * @throws ClassNotFoundException whenever there is a problem with class loading
     * @throws IOException whenever there is a problem with file loading
     */
    @FXML private void onConfirm() throws SQLException, ClassNotFoundException, IOException {
        if(password.getText().length()<8) {
            passwordMessage.setText("Password must be at least 8 characters!");
            passwordMessage.setTextFill(Color.rgb(210, 39, 30));
        }
        else {
            if(!password.getText().equals(repeat.getText())) {
                repeatPasswordMessage.setText("Password and Repeat password must be the same!");
                repeatPasswordMessage.setTextFill(Color.rgb(210, 39, 30));
            }
            else {
                Database.changePassword(Main.getSession().getEmail(), password.getText());
                AnimationFX.transitionForward("/login-view.fxml", anchorPane);
            }
        }
    }
}
