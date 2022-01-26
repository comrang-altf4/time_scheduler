package controller;

import backend.Database;
import backend.Email;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import project.Main;
import transition.AnimationFX;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgetController {
    @FXML private AnchorPane anchorPane;
    @FXML private Hyperlink sendCode;
    @FXML private TextField email;

    public static final Pattern validateEmail = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @FXML private void onHomeAction(ActionEvent event) throws IOException {
        AnimationFX.transitionBackward("/login-view.fxml", anchorPane);
    }

    @FXML private void onSendCode(ActionEvent event) throws MessagingException, SQLException, ClassNotFoundException, IOException {
        Matcher matcher = validateEmail.matcher(email.getText());
        if(!matcher.find()||!Database.checkEmail(email.getText())) {
            // Email is not correct
            System.out.println("Email is not valid/existed!!!");
        }
        else {
            Main.getSession().setEmail(email.getText());
            String code = Email.sendVerificationCode(email.getText());
            Main.getSession().setCode(code);
            AnimationFX.transitionForward("/verify-view.fxml", anchorPane);
        }
    }
}
