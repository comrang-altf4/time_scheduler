/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javafx.scene.control.ComboBox;
/**
 * FXML Controller class
 *
 * @author minhn
 */
public class LinkDownloadController implements Initializable {
    @FXML
    private Hyperlink hpLink;
    @FXML
    private TextField tLink;
    @FXML
    private ComboBox<String> comBox;
    private String labItem;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        comBox.setItems(FXCollections.observableArrayList("Save as PDF", "Save as Text"));
    }    

    @FXML
    private void openLink(ActionEvent event) throws URISyntaxException, IOException {
        System.out.println("Link clicked!");
        Desktop.getDesktop().browse(new URI(hpLink.getText()));
    }

    @FXML
    private void btnSaveClicked(ActionEvent event) {
        String link = tLink.getText();
        hpLink.setText(link);
    }

    @FXML
    private void selectItem(ActionEvent event) {
        labItem = comBox.getSelectionModel().getSelectedItem().toString();
    }
    
    @FXML
    private void btnDownloadClicked(ActionEvent event) throws DocumentException, IOException {
        String file_name = System.getProperty("user.home") + "/Downloads/";
        System.out.println(labItem);
        if (labItem == "Save as PDF"){
            try {
                file_name += "Schedule.pdf";
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(file_name));
                document.open();
                Paragraph para = new Paragraph(tLink.getText());
                document.add(para);
                document.close();
                System.out.println("Downloaded as PDF!");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(LinkDownloadController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (labItem == "Save as Text"){
            file_name += "Schedule.txt";
            FileWriter document = new FileWriter(file_name);
            document.write(tLink.getText());
            document.close();
            System.out.println("Downloaded as Text!");
        }
    }
    
}
