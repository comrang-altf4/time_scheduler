package controller;

import backend.Database;
import backend.Email;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
    @FXML private Label usernameMessage;
    @FXML private Label passwordMessage;
    @FXML private Label emailMessage;
    @FXML private Label repeatPasswordMessage;
    public static final Pattern validateUsername = Pattern.compile("^[A-Z0-9]{8,16}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern validateEmail = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);



    @FXML private void onHomeAction(ActionEvent event) throws IOException {
        AnimationFX.transitionBackward("/login-view.fxml", anchorPane);
    }
    @FXML private void onSignUp(ActionEvent event) throws SQLException, ClassNotFoundException, IOException, MessagingException {
        boolean flag = true;
        Matcher matcher = validateUsername.matcher(username.getText());
        usernameMessage.setTextFill(Color.rgb(210, 39, 30));
        emailMessage.setTextFill(Color.rgb(210, 39, 30));


        if(!matcher.find()) {
            usernameMessage.setText("Username must have 8 - 16 characters!");
            flag = false;
        }
        else {
            if(Database.checkUsername(username.getText())) {
                usernameMessage.setText("Username is already taken!");
                flag = false;
            }
            else
                usernameMessage.setText("");
        }

        matcher = validateEmail.matcher(email.getText());
        if(!matcher.find()) {
            emailMessage.setText("Email does not exist!");
            flag = false;
        }
        else {
            if(Database.checkEmail(email.getText())) {
                emailMessage.setText("Email is already taken!");
                flag = false;
            }
            else
                emailMessage.setText("");
        }

        if(password.getText().length()<8) {
            passwordMessage.setText("Password must be at least 8 characters!");
            passwordMessage.setTextFill(Color.rgb(210, 39, 30));
            flag = false;
        }
        else
            passwordMessage.setText("");

        if(!repeat.getText().equals(password.getText())) {
            repeatPasswordMessage.setText("Password and Repeat password must be the same!");
            repeatPasswordMessage.setTextFill(Color.rgb(210, 39, 30));
            flag = false;
        }
        else
            repeatPasswordMessage.setText("");

        if(flag) {
            Main.getSession().setUsername(username.getText());
            Main.getSession().setPassword(password.getText());
            Main.getSession().setEmail(email.getText());
            String code = Email.sendVerificationCode(Main.getSession().getEmail());
            Main.getSession().setCode(code);
            AnimationFX.transitionForward("/verify-email-view.fxml", anchorPane);
        }

    }
}
