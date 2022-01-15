package com.example.login_v2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpController {
    @FXML private TextField username;
    @FXML private TextField email;
    @FXML private TextField code;
    @FXML private PasswordField password;
    @FXML private PasswordField confirm;
    @FXML private Button button;
    @FXML private Button verify;
    @FXML private AnchorPane anchorPane;
    @FXML private AnchorPane subAnchorPane;
    public static final Pattern validateUsername = Pattern.compile("^[A-Z0-9]{6,16}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern validateEmail = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static String verification;
    private static String receiver;
    private static String user;
    private static String passcode;

    @FXML
    private void onLoginButton() throws IOException, MessagingException {
        Matcher matcher = validateUsername.matcher(username.getText());
        if(!matcher.find())
            System.out.println("Username is not correct!!");
        else {
            matcher = validateEmail.matcher(email.getText());
            if(!matcher.find())
                System.out.println("Email is not existed!!");
            else {
                if(password.getText().length()<8)
                    System.out.println("Password must be at least 8 characters!!");
                else {
                    if(!password.getText().equals(confirm.getText()))
                        System.out.println("Password and Confirm password must be the same!!");
                    else {
                        receiver = email.getText();
                        user = username.getText();
                        passcode = password.getText();
                        verification = Email.sendVerificationCode(receiver);
                        AnimationFX.transition("VerificationCode_v2.fxml", button, anchorPane);
                    }
                }
            }
        }
    }

    @FXML
    private void onVerifyButton() throws IOException, SQLException, ClassNotFoundException {
        if(code.getText().equals(verification)) {
            AnimationFX.transition("WelcomeScreen_v2.fxml", verify, subAnchorPane);
            Database.addUserDB(user, receiver, passcode);
        }
        else {
            System.out.println("Wrong verification code!!");
        }
    }

    @FXML
    private void onResendCode() throws MessagingException {
        verification = Email.sendVerificationCode(receiver);
    }
}

