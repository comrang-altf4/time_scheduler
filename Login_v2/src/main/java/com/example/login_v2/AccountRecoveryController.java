package com.example.login_v2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.login_v2.Database.checkDB;

public class AccountRecoveryController {
    @FXML private TextField email;
    @FXML private TextField code;
    @FXML private Button verify;
    @FXML private Hyperlink sendCode;
    @FXML private Hyperlink back;
    @FXML private AnchorPane anchorPane;
    @FXML private AnchorPane subAnchorPane;
    @FXML private AnchorPane anoAnchorPane;
    @FXML private PasswordField newPassword;
    @FXML private PasswordField conPassword;
    @FXML private Button confirm;

    private static String verification;
    private static String receiver;
    public static final Pattern validateEmail = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @FXML
    private void onSendCode() throws MessagingException, IOException, SQLException, ClassNotFoundException {
        receiver = email.getText();
        Matcher matcher = validateEmail.matcher(receiver);
        if(!matcher.find()||!checkDB(receiver))
            System.out.println("Email is not existed!!");
        else {
            verification = Email.sendVerificationCode(receiver);
            AnimationFX.transition("AccountRecovery_v3.fxml", sendCode, anchorPane);
        }
    }

    @FXML
    private void onResendCode() throws MessagingException {
        verification = Email.sendVerificationCode(receiver);
    }

    @FXML
    private void onBack() throws IOException {
        AnimationFX.transitionBack("AccountRecovery_v2.fxml", back, subAnchorPane);
    }

    @FXML
    private void onVerifyButton() throws IOException, SQLException, ClassNotFoundException {
        if(code.getText().equals(verification)) {
            AnimationFX.transition("NewPassword_v2.fxml", verify, subAnchorPane);
        }
        else {
            System.out.println("Wrong verification code!!");
        }
    }

    @FXML
    private void onConfirm() throws SQLException, ClassNotFoundException, IOException {
        if(newPassword.getText().length()<8) {
            System.out.println("Password must be at least 8 characters!!");
        }
        else {
            if(!newPassword.getText().equals(conPassword.getText())) {
                System.out.println("New password must be the same with Confirm new password");
            }
            else {
                Database.changePasswordDB(receiver, newPassword.getText());
                AnimationFX.transition("WelcomeScreen_v2.fxml", confirm, anoAnchorPane);
            }
        }
    }
}
