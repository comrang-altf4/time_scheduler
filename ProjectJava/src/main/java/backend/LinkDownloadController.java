package backend;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
import backend.wagu.Block;
import backend.wagu.Board;
import backend.wagu.Table;
import java.util.List;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private ArrayList<ArrayList<String> > listEventDay = new ArrayList<ArrayList<String> > (1000);
    private ArrayList<ArrayList<Integer> > listEventDayColor = new ArrayList<ArrayList<Integer> > (1000);
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
    void btnEditClicked(ActionEvent event) {
        hpLink.setVisible(false);
        tLink.setVisible(true);
    }

    @FXML
    private void selectItem(ActionEvent event) {
        labItem = comBox.getSelectionModel().getSelectedItem().toString();
    }
    
//    @FXML
    public void btnDownloadClicked(LocalDate refDate,String mode) throws DocumentException, IOException {
        String file_name = System.getProperty("user.home") + "/Downloads/";
        //System.out.println(labItem);
        getEventDay(refDate);
        LocalDate startOfWeek = refDate.minusDays(refDate.getDayOfWeek().getValue() - 1);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        String title = "Time table";
        String start = String.valueOf(startOfWeek.getDayOfMonth()) + "." + String.valueOf(startOfWeek.getMonthValue()) + "." + String.valueOf(startOfWeek.getYear());
        String end = String.valueOf(endOfWeek.getDayOfMonth()) + "." + String.valueOf(endOfWeek.getMonthValue() + "." + String.valueOf(endOfWeek.getYear()));
        title = title + "\n" + start + " - " + end;
        labItem=mode;
        if (labItem == "PDF"){
            try {
                Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 9, Font.BOLD, new BaseColor(0, 0, 0)); 
                Font bf12 = new Font(FontFamily.TIMES_ROMAN, 9);
                int number = getFileName(file_name, "Schedule" + "_" + start + "-" + end, ".pdf");
                if (number != 0)
                    file_name += "Schedule" + "_" + start + "-" + end + " (" + String.valueOf(number) + ")" + ".pdf";
                else file_name += "Schedule" + "_" + start + "-" + end + ".pdf";
                Document document = new Document(PageSize.A4);
                PdfWriter.getInstance(document, new FileOutputStream(file_name));
                document.open();
                float[] col = {2f, 2f, 2f, 2f, 2f, 2f, 2f};
                PdfPTable table = new PdfPTable(col);
                table.setWidthPercentage(90f);
                insertCell(table, title, Element.ALIGN_CENTER, 7, bfBold12);
                insertCell(table, "Monday", Element.ALIGN_CENTER, 3, bfBold12);
                insertCell(table, "Tuesday", Element.ALIGN_CENTER, 3, bfBold12);
                insertCell(table, "Wednesday", Element.ALIGN_CENTER, 3, bfBold12);
                insertCell(table, "Thursday", Element.ALIGN_CENTER, 3, bfBold12);
                insertCell(table, "Friday", Element.ALIGN_CENTER, 3, bfBold12);
                insertCell(table, "Saturday", Element.ALIGN_CENTER, 3, bfBold12);
                insertCell(table, "Sunday", Element.ALIGN_CENTER, 3, bfBold12);
                for (int i = 0; i < listEventDay.get(0).size(); i++){
                    for (int j = 0; j < 7; j++){
                        insertCell(table, listEventDay.get(j).get(i), Element.ALIGN_CENTER, listEventDayColor.get(j).get(i), bfBold12);
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
        if (labItem == "Text"){
            int number = getFileName(file_name, "Schedule" + "_" + start + "-" + end, ".txt");
            if (number != 0)
                file_name += "Schedule" + "_" + start + "-" + end + " (" + String.valueOf(number) + ")" + ".txt";
            else file_name += "Schedule" + "_" + start + "-" + end + ".txt";
            FileWriter document = new FileWriter(file_name);
            Board board = new Board(650);
            Block bigblock = new Block(board, 216, 2, title);
            board.setInitialBlock(bigblock.setDataAlign(Block.DATA_CENTER));
            Block block2 = new Block(board, 30, 1, "Monday");
            bigblock.setBelowBlock(block2.setDataAlign(Block.DATA_CENTER));
            Block block3 = new Block(board, 30, 1, "Tuesday");
            block2.setRightBlock(block3.setDataAlign(Block.DATA_CENTER));
            Block block4 = new Block(board, 30, 1, "Wednesday");
            block3.setRightBlock(block4.setDataAlign(Block.DATA_CENTER));
            Block block5 = new Block(board, 30, 1, "Thursday");
            block4.setRightBlock(block5.setDataAlign(Block.DATA_CENTER));
            Block block6 = new Block(board, 30, 1, "Friday");
            block5.setRightBlock(block6.setDataAlign(Block.DATA_CENTER));
            Block block7 = new Block(board, 30, 1, "Saturday");
            block6.setRightBlock(block7.setDataAlign(Block.DATA_CENTER));
            Block block8 = new Block(board, 30, 1, "Sunday");
            block7.setRightBlock(block8.setDataAlign(Block.DATA_CENTER));
            makeCell(board, block2, listEventDay.get(0),listEventDay.get(0).size());
            makeCell(board, block3, listEventDay.get(1),listEventDay.get(1).size());
            makeCell(board, block4, listEventDay.get(2),listEventDay.get(2).size());
            makeCell(board, block5, listEventDay.get(3),listEventDay.get(3).size());
            makeCell(board, block6, listEventDay.get(4),listEventDay.get(4).size());
            makeCell(board, block7, listEventDay.get(5),listEventDay.get(5).size());
            makeCell(board, block8, listEventDay.get(6),listEventDay.get(6).size());
            document.write(board.invalidate().getPreview());
            document.close();
            System.out.println("Downloaded as Text!");
        }
    }
    private void insertCell(PdfPTable table, String text, int align, int color, Font font){
        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        if (color != 7) cell.setColspan(1);
        else cell.setColspan(7);
        if (color == 0) cell.setBackgroundColor(BaseColor.RED);
        if (color == 1) cell.setBackgroundColor(BaseColor.YELLOW);
        if (color == 2) cell.setBackgroundColor(BaseColor.GREEN);
        //in case there is no text and you wan to create an empty row
        if(text.trim().equalsIgnoreCase("")){
            cell.setMinimumHeight(10f);
        }
        //add the call to the table
        table.addCell(cell);
   }
    public static String getText(Event event)
    {
        String text = event.getName();
        LocalDateTime ee = event.getEndTime();
        String hour = String.valueOf(event.getDate().getHour());
        String minute = String.valueOf(event.getDate().getMinute());
        String hee = String.valueOf(ee.getHour());
        String mee = String.valueOf(ee.getMinute());
        text = text + "\n" + hour + ":" + minute + " - " + hee + ":" + mee;
        return text;
    }
    public void getEventDay(LocalDate refDate)
    {
        List<Event> temp=new Sess1on().gettEventInWeek(refDate);
        for (int i = 0; i < 7; i++){
            listEventDay.add(new ArrayList<String> (1000));
            listEventDayColor.add(new ArrayList<Integer> (1000));
        }
        for (Event e:temp) {
            listEventDay.get(e.getDate().getDayOfWeek().getValue()-1).add(getText(e));
            listEventDayColor.get(e.getDate().getDayOfWeek().getValue()-1).add(e.getPriority());
        }
        int maxx = 0;
        for (int i = 0; i < 7; i++){
            maxx = Math.max(listEventDay.get(i).size(), maxx);
        }
        for (int i = 0; i < 7; i++) {
            int len = listEventDay.get(i).size(); 
            if (len < maxx){
                for (int j = len; j < maxx; j++){               
                    listEventDay.get(i).add(j, "");
                    listEventDayColor.get(i).add(3);
                }
            }
        }
    }
    public static void makeCell(Board board, Block block, ArrayList<String> list, int numCell)
    {
        Block tmp = block;
        for (int i = 0; i < numCell; i++){
            Block bl = new Block(board, 30, 2, list.get(i));
            tmp.setBelowBlock(bl.setDataAlign(Block.DATA_CENTER));
            tmp = bl;
        }
    }
    public static int getFileName(String path, String fileName, String extension)
    {
        int number = 0;
        int idxEnd = fileName.length();
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();        
        for (int i = 0; i < listOfFiles.length; i++) {
          if (listOfFiles[i].isFile()) {
              String filee = listOfFiles[i].getName();
              
              if (filee.length() > fileName.length()){
                  String len = filee.substring(0, idxEnd);
                if (len.equals(fileName.substring(0, idxEnd))  && filee.substring(filee.length()-4, filee.length()).equals(extension))
                    number++;
              }
          } 
        }
        return number;
    }
}
