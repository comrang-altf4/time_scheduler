package controller;

import backend.IdentityManagement;
import backend.Sess1on;
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

import java.io.IOException;
import java.sql.SQLException;

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
    protected static final String[] dayOfWeek = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
            "Saturday" };
    protected static final String[] monthOfYear = { "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December" };
    public void doChangeview(ActionEvent e, String fxmlfile) throws IOException {
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource(fxmlfile));
        scene = new Scene(root);


        stage.setScene(scene);
        stage.show();
       }
   public void signOut(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
       IdentityManagement.updateToDB();
       new Sess1on().release();
       AnimationFX.transitionBackward("/login-view.fxml",(Parent) event.getSource());
   }
}