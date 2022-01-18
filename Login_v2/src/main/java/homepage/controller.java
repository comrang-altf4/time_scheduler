package homepage;

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

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import com.example.login_v2.AnimationFX;
public class controller {
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
	public AnchorPane mainPane;
	protected static final String[] dayOfWeek = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
			"Saturday" };

	@FXML

	public void handleCloseButtonAction(ActionEvent event) {
		Stage stage = (Stage) btnClose.getScene().getWindow();
		stage.close();
	}

	public void doChangeview(ActionEvent e, String fxmlfile) throws IOException {
		test=1;
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource(fxmlfile));
		scene = new Scene(root);
		scene.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
			if (paneOn == 1)
				paneOn = 2;
			else if (paneOn == 2) {
				paneOn = 0;
				controller_monthview.popup2.hide();
			}
		});
		stage.setScene(scene);
		stage.show();
		System.out.println("c");
//		AnimationFX.transition(fxmlfile,(Node)e.getSource(),mainPane);
	}

	public void addEvent(ActionEvent e) {

	}

}
