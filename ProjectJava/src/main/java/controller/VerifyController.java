/**
 * This class is the controller for verify email screen when changing password
 * @author Huy To Quang
 */
package controller;

import backend.Email;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import project.Main;
import transition.AnimationFX;

import javax.mail.MessagingException;
import java.io.IOException;

public class VerifyController {
    @FXML private AnchorPane anchorPane;
    @FXML private TextField code;
    @FXML private Label verifyMessage;
    @FXML
    private void onHomeAction(ActionEvent event) throws IOException {
        AnimationFX.transitionBackward("/login-view.fxml", anchorPane);
    }
    @FXML
    private void onBack(ActionEvent event) throws IOException {
        AnimationFX.transitionBackward("/forget-view.fxml", anchorPane);
    }
    @FXML
    private void onResendCode(ActionEvent event) throws MessagingException {
        String newCode = Email.sendVerificationCode(Main.getSession().getEmail());
        Main.getSession().setCode(newCode);
    }

    /**
     * This function changes to changing password view when email authentication is valid
     * @param event signal of action on verify button
     * @throws IOException whenever there is a problem with file loading
     */
    @FXML
    private void onVerify(ActionEvent event) throws IOException {
        if(code.getText().equals(Main.getSession().getCode()))
            AnimationFX.transitionForward("/change-view.fxml", anchorPane);
        else {
            verifyMessage.setText("Wrong verification code!");
            verifyMessage.setTextFill(Color.rgb(210, 39, 30));
        }
    }
}
