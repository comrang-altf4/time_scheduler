/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author minhn
 */
public class MainFXMLController implements Initializable {

    @FXML
    private TextField tLink;
    @FXML
    private Label lbLink;
    @FXML
    private Hyperlink hpLink;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnSaveClicked(ActionEvent event) {
        String link = tLink.getText();
        hpLink.setText(link);
    }

    @FXML
    private void openLink(ActionEvent event) throws URISyntaxException, IOException {
        System.out.println("Link clicked!");
        Desktop.getDesktop().browse(new URI(hpLink.getText()));
    }
    
}
