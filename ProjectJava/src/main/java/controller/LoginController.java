package controller;

import backend.Database;
import backend.Sess1on;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import project.Main;
import transition.AnimationFX;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private AnchorPane anchorPane;
    @FXML private Label incorrectInformationMessage;
    @FXML private void onLoginAction(ActionEvent event) throws SQLException, ClassNotFoundException, InterruptedException, IOException {
        if(username.getText()==null||username.getText().isEmpty()||password.getText()==null||password.getText().isEmpty()) {
            incorrectInformationMessage.setText("Username or Password is incorrect!");
            incorrectInformationMessage.setTextFill(Color.rgb(210, 39, 30));
        }
        else {
            boolean flag = Database.login(username.getText(), password.getText());
            if(flag) {
                // Login successfully
                Main.getSession().setUsername(username.getText());
                Main.getSession().setPassword(password.getText());
                Main.getSession().setEmail(Database.getEmail(username.getText()));
                Sess1on.eventList = Database.getEvents();
                new Controller().doChangeview(event,"/design.fxml");
            }
            else {
                incorrectInformationMessage.setText("Username or Password is incorrect!");
                incorrectInformationMessage.setTextFill(Color.rgb(210, 39, 30));
            }
        }
    }
    @FXML private void onForgetAction(ActionEvent event) throws IOException {
        AnimationFX.transitionForward("/forget-view.fxml", anchorPane);
    }

    @FXML private void onRegisterAction(ActionEvent event) throws IOException {
        AnimationFX.transitionForward("/register-view.fxml", anchorPane);
    }
}
