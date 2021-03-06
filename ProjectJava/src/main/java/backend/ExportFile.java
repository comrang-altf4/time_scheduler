package backend;
import backend.wagu.Block;
import backend.wagu.Board;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.stage.DirectoryChooser;

public class ExportFile {
    @FXML
    private Hyperlink hpLink;
    @FXML
    private TextField tLink;
    @FXML
    private ComboBox<String> comBox;
    private String labItem;
    private ArrayList<ArrayList<String> > listEventDay = new ArrayList<ArrayList<String> > (1000);
    private ArrayList<ArrayList<Integer> > listEventDayColor = new ArrayList<ArrayList<Integer> > (1000);
    private ArrayList<Integer> fileAppearance = new ArrayList<Integer> (1000);

    /**
     * Creating file with chosen extension in the Downloads directory.
     * @param refDate Gets the information of an object from class LocalDate.
     * @param mode Gets the option PDF or TXT.
     * @throws DocumentException Signals that an error has occurred in a Document.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
//    @FXML
    public void btnDownloadClicked(LocalDate refDate,String mode) throws DocumentException, IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        String file_path = null;
        if(selectedDirectory != null){
            file_path = selectedDirectory.getAbsolutePath()+"/";
        }
        else return;
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
                int number = getFileName(file_path, "Schedule" + "_" + start + "-" + end, ".pdf");
                if (number != 0)
                    file_path += "Schedule" + "_" + start + "-" + end + " (" + String.valueOf(number) + ")" + ".pdf";
                else file_path += "Schedule" + "_" + start + "-" + end + ".pdf";
                Document document = new Document(PageSize.A4);
                PdfWriter.getInstance(document, new FileOutputStream(file_path));
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
                Logger.getLogger(ExportFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (labItem == "Text"){
            int number = getFileName(file_path, "Schedule" + "_" + start + "-" + end, ".txt");
            if (number != 0)
                file_path += "Schedule" + "_" + start + "-" + end + " (" + String.valueOf(number) + ")" + ".txt";
            else file_path += "Schedule" + "_" + start + "-" + end + ".txt";
            FileWriter document = new FileWriter(file_path);
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
    /**
     * 
     * @param table The table with defined number of columns.
     * @param text The content in each cell.
     * @param align Sets the position of content.
     * @param color color with value 0, 1, 2, or 3 is the priority of an event for red, yellow, green, or white color, respectively; with value 7 is merging 7 columns for 1 cell.
     * @param font defined font.
     * @return the timetable with content.
     */
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
    /**
     * 
     * @param event Gets information of an event. 
     * @return The time of the event in string.
     */
    public static String getText(Event event)
    {
        String text = event.getName();
        LocalDateTime ee = event.getEndTime();
        String hour = String.valueOf(event.getDate().getHour());
        String minute = String.valueOf(event.getDate().getMinute());
        String hee = String.valueOf(ee.getHour());
        String mee = String.valueOf(ee.getMinute());
        if (event.getDate().getHour() < 10) hour = "0" + hour;
        if (event.getDate().getMinute() < 10) minute = "0" + minute;
        if (ee.getHour() < 10) hee = "0" + hee;
        if (ee.getMinute() < 10) mee = "0" + mee;
        text = text + "\n" + hour + ":" + minute + " - " + hee + ":" + mee;
        return text;
    }
    /**
     *Get all name and priority (color) of events in each date (Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday).  
     */
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
    /**
     * 
     * @param board A defined board is the base to create cells.
     * @param block Block (cells) in a timetable.
     * @param list The information of all events in a date.
     * @param numCell The maximum number of rows in the timetable.
     * Create all blocks for a date in timetable.
     */
    public static void makeCell(Board board, Block block, ArrayList<String> list, int numCell)
    {
        Block tmp = block;
        for (int i = 0; i < numCell; i++){
            Block bl = new Block(board, 30, 2, list.get(i));
            tmp.setBelowBlock(bl.setDataAlign(Block.DATA_CENTER));
            tmp = bl;
        }
    }
    /**
     * 
     * @param path Gets the directory which files are downloaded.
     * @param fileName The file name which is checked how many files in the directory have the same name with it.
     * @param extension The extension PDF or TXT.
     * @return The number appearance of the new file will be in the directory. 
     */
    public int getFileName(String path, String fileName, String extension)
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
                {
                    if (idxEnd+2 < filee.length()-5)
                        fileAppearance.add(Integer.valueOf(filee.substring(idxEnd+2, filee.length()-5)));
                    else fileAppearance.add(0);
                }
                    
              }
          } 
        }
        Collections.sort(fileAppearance);  
        if (fileAppearance.size() > 0)
            number = fileAppearance.get(fileAppearance.size()-1)+1;
        else number = 0;
        return number;    
    }
}
