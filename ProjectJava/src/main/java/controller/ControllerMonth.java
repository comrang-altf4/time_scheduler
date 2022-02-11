package controller;


import backend.Event;
import backend.Sess1on;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
/**
 * This is the controller class assigned to the Month view.
 * @author      comrang-altf4
 */
public class ControllerMonth extends Controller {
    static Calendar cal = Calendar.getInstance(TimeZone.getDefault());
    customVbox[] vBoxes = new customVbox[42];

    @FXML
    public void ChangeView(ActionEvent e) throws IOException {
        doChangeview(e, "/design.fxml");
    }

    @FXML
    GridPane gridpane_monthview = new GridPane();

    @FXML
    AnchorPane mainPane = new AnchorPane();
    int[] dayOrder = new int[42];

    @FXML
    void initialize() {
        int numRows = 6;
        int numColumns = 7;
        for (int i = 0; i < numRows; i++)
            for (int j = 0; j < numColumns; j++) {
                vBoxes[i * numColumns + j] = new customVbox();
                vBoxes[i * numColumns + j].maxHeight(Double.MAX_VALUE);
                vBoxes[i * numColumns + j].maxWidth(Double.MAX_VALUE);
                gridpane_monthview.add(vBoxes[i * numColumns + j], j, i);
            }
        displayMonthCalendar();

    }

    /**
     * gets the order of all days displayed in Month view
     */
    private void getDayOrder() {
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int totaldays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int pivot = cal.get(Calendar.DAY_OF_WEEK);
        int i = 0;
        while (i < totaldays) {
            dayOrder[pivot - 1 + i] = i + 1;
            i++;
        }
        cal.add(Calendar.MONTH, -1);
        int totaldays_neighbor = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int j = pivot - 2; j >= 0; j--) {
            dayOrder[j] = totaldays_neighbor;
            totaldays_neighbor--;
        }
        int nextmonthday = 1;
        for (; i + pivot - 1 < 42; i++) {
            dayOrder[i + pivot - 1] = nextmonthday;
            nextmonthday++;
        }
        cal.add(Calendar.MONTH, 1);

    }

    /**
     * Changes to month view
     * @param   e the event trigger this function
     */
    public void changeviewmonth(ActionEvent e) {
        if (e.getSource() == btnPrevDay)
            cal.add(Calendar.MONTH, -1);
        else if (e.getSource() == btnNextDay)
            cal.add(Calendar.MONTH, 1);
        displayMonthCalendar();
    }

    /**
     * Generates contents for the Month view
     */
    private void displayMonthCalendar() {
        for (int i=0;i<42;i++)vBoxes[i].getChildren().clear();
        getDayOrder();
        cal.add(Calendar.MONTH, -1);
        int curMonth = cal.get(Calendar.MONTH);
        cal.add(Calendar.MONTH, 1);
        for (int i = 0; i < 42; i++) {
            vBoxes[i].getChildren().add(new Label(String.valueOf(dayOrder[i])));
            vBoxes[i].setOnMouseClicked(e->{switchWeekpage(e,((customVbox)e.getSource()).curDate);});
            if (dayOrder[i] == 1) curMonth = (curMonth + 1) % 12;
            vBoxes[i].setCurDate(cal.get(Calendar.YEAR), curMonth + 1, dayOrder[i]);
            for (Event e : Sess1on.eventList) {
                if (e.getDate().toLocalDate().toString().equals(vBoxes[i].curDate.toString())) {
                    Label lb = new Label(e.getName());
                    lb.setMaxHeight(Double.MAX_VALUE);
                    lb.setMaxWidth(Double.MAX_VALUE);
                    lb.setStyle(e.priorityColor[e.getPriority()]);
                    vBoxes[i].totalChildren+=1;
                    if (vBoxes[i].totalChildren>=4)
                    {
                        vBoxes[i].getChildren().set(3,new Label(String.format("and %d more",vBoxes[i].totalChildren-2)));
                    }
                    else vBoxes[i].getChildren().add(lb);
                }
            }
        }
        currentDayMonth.setText(monthOfYear[cal.get(Calendar.MONTH)] + " " + cal.get(Calendar.YEAR));
    }

    /**
     * Changes to week page corresponding to chosen date
     * @param e     the event trigger this function
     * @param lDate the chosen date
     */
    public void switchWeekpage(javafx.event.Event e, LocalDate lDate)
    {
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Weekpage wp=new Weekpage(lDate);
        scene = new Scene(wp,600,600);
        scene.getStylesheets().add(getClass().getResource("/calendar-view.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}