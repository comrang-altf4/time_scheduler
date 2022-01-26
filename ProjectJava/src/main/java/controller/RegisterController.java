package controller;

import backend.Database;
import backend.Email;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import project.Main;
import transition.AnimationFX;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController {
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private TextField email;
    @FXML private PasswordField repeat;
    @FXML private AnchorPane anchorPane;
    public static final Pattern validateUsername = Pattern.compile("^[A-Z0-9]{6,16}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern validateEmail = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @FXML private void onHomeAction(ActionEvent event) throws IOException {
        AnimationFX.transitionBackward("/login-view.fxml", anchorPane);
    }
    @FXML private void onSignUp(ActionEvent event) throws SQLException, ClassNotFoundException, IOException, MessagingException {
        if(Database.checkUsername(username.getText())) {
            System.out.println("Username is already existed!!!");
            return;
        }
        if(Database.checkEmail(email.getText())) {
            System.out.println("Email is already existed!!!");
            return;
        }
        if(!repeat.getText().equals(password.getText())) {
            System.out.println("Password and Repeat password must be the same");
            return;
        }
        Matcher matcher = validateUsername.matcher(username.getText());
        if(!matcher.find())
            System.out.println("User name must be 6 - 8 letters, letters and numbers only!!!");
        else {
            matcher = validateEmail.matcher(email.getText());
            if(!matcher.find())
                System.out.println("Email is not existed!!!");
            else {
                Main.getSession().setUsername(username.getText());
                Main.getSession().setPassword(password.getText());
                Main.getSession().setEmail(email.getText());
                Email.sendVerificationCode(Main.getSession().getEmail());
                AnimationFX.transitionForward("/verify-email-view.fxml", anchorPane);
            }
        }

    }
}
