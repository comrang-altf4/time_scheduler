package controller;

import backend.IdentityManagement;
import backend.Sess1on;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Parent;
import project.Main;
import transition.AnimationFX;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;

/**
 *  Parent class for day controller and month controller, provide core functions and attributes\
 * @author comrang-altf4
 */
public class Controller {
    static protected Stage stage;
    static protected Scene scene;
    static protected Parent root;
    static protected int test=0;
    static  protected int paneOn=0;
    @FXML
    protected Button btnPrevDay;
    @FXML
    private Button btnChangeview;
    @FXML
    protected Button btnNextDay;
    @FXML
    protected Button addEventBtn;
    @FXML
    protected Text currentDayMonth=new Text();
    @FXML
    public Button btnClose;
    @FXML
    public Text txtDay;
    @FXML
    public Text txtWeekday;
    @FXML
    public Text txtMonth;
    @FXML
    public Text txtYear;
    @FXML
    public Text txtDayAndMonth;
    @FXML
    public AnchorPane mainPane;
    @FXML
    public Text quote;
    @FXML
    public Text author;
    protected static final String[] dayOfWeek = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
            "Saturday" };
    protected static final String[] monthOfYear = { "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December" };

    /**
     * This function change the current scene to destined scene
     * @param e             event trigger this function
     * @param fxmlfile      name of the fxml of the destined scene
     * @throws IOException
     */
    public void doChangeview(ActionEvent e, String fxmlfile) throws IOException {
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource(fxmlfile));
        Group group = new Group(root);
        scene = new Scene(group);
        stage.setScene(scene);
        stage.show();
       }

    /**
     * This function closes the current session, the user will be signed out
     * @param event                     event trigger this function
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IOException
     */
   public void signOut(ActionEvent event) throws IOException {
       new Sess1on().release();
       doChangeview(event,"/login-view.fxml");
   }
}