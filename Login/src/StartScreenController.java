package com.example.login_v2;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.sql.SQLException;

public class StartScreenController {
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Hyperlink createAccount;
    @FXML private Hyperlink forgetPassword;
    @FXML private AnchorPane anchorPane;
    @FXML
    private void onActionButton() throws SQLException, ClassNotFoundException {
        if(username.getText()==null||username.getText().isEmpty()||password.getText()==null||password.getText().isEmpty())
            System.out.println("Wrong username/password!!");
        else
            Database.loginUserDB(username.getText(), password.getText());
    }

    @FXML
    private void onForgetPassword() throws IOException {
        AnimationFX.transition("AccountRecovery_v2.fxml", forgetPassword, anchorPane);
    }

    @FXML
    private void onCreateAccount() throws IOException {
        AnimationFX.transition("SignUp_v2.fxml", createAccount, anchorPane);
    }
}
