package homepage;

import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

public class controller_changeview {
	@FXML
	private void mouseEntered(MouseEvent e) {
		Node source = (Node) e.getSource();
		System.out.println(source);
		Integer colIndex = GridPane.getColumnIndex(source);
		Integer rowIndex = GridPane.getRowIndex(source);
		System.out.printf("Mouse entered cell [%d, %d]%n", colIndex.intValue(), rowIndex.intValue());
	}
}
