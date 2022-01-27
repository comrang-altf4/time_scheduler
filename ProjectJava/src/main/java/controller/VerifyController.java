package controller;

import backend.Email;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import project.Main;
import transition.AnimationFX;

import javax.mail.MessagingException;
import java.io.IOException;

public class VerifyController {
    @FXML private AnchorPane anchorPane;
    @FXML private TextField code;
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
    @FXML
    private void onVerify(ActionEvent event) throws IOException {
        if(code.getText().equals(Main.getSession().getCode()))
            AnimationFX.transitionForward("/change-view.fxml", anchorPane);
        else
            System.out.println("Wrong verification code!!!");
    }
}
