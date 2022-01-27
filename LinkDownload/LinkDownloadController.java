/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.awt.Desktop;
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
import javafx.scene.control.Cell;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
                Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0)); 
                Font bf12 = new Font(FontFamily.TIMES_ROMAN, 12);
                file_name += "Schedule.pdf";
                Document document = new Document(PageSize.A4);
                PdfWriter.getInstance(document, new FileOutputStream(file_name));
                document.open();
                float[] col = {1.25f, 1.75f, 1.75f, 2f, 1.75f, 1.75f, 1.75f, 1.75f};
                PdfPTable table = new PdfPTable(col);
                table.setWidthPercentage(90f);
                insertCell(table, "Time", Element.ALIGN_CENTER, 1, bfBold12);
                insertCell(table, "Monday", Element.ALIGN_CENTER, 1, bfBold12);
                insertCell(table, "Tuesday", Element.ALIGN_CENTER, 1, bfBold12);
                insertCell(table, "Wednesday", Element.ALIGN_CENTER, 1, bfBold12);
                insertCell(table, "Thursday", Element.ALIGN_CENTER, 1, bfBold12);
                insertCell(table, "Friday", Element.ALIGN_CENTER, 1, bfBold12);
                insertCell(table, "Saturday", Element.ALIGN_CENTER, 1, bfBold12);
                insertCell(table, "Sunday", Element.ALIGN_CENTER, 1, bfBold12);
                for (int i = 0; i < 24; i++){
                    String time = String.valueOf(i) + ":00";
                    insertCell(table, time, Element.ALIGN_CENTER, 1, bfBold12);
                    for (int j = 0; j < 7; j++){
                        insertCell(table, "", Element.ALIGN_CENTER, 1, bfBold12);
                    }
                }
                Paragraph para = new Paragraph();
                para.add(table);
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
    private void insertCell(PdfPTable table, String text, int align, int colspan, Font font){
        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        //in case there is no text and you wan to create an empty row
        if(text.trim().equalsIgnoreCase("")){
            cell.setMinimumHeight(10f);
        }
        //add the call to the table
        table.addCell(cell);
   }
    
}
