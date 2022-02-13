package controller;

import backend.Event;
import backend.Sess1on;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * This is the controller assigned to Day view
 * @author comrang-altf4
 */
public class ControllerDay extends Controller {
    static Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    @FXML
    VBox fpTodayEvent;

    /**
     * Generates contents for Day view
     * @param weekday   day in week (Monday, Tuesday,...)
     * @param day       day in month
     * @param month     order of current month (January is 1)
     * @param year      year
     */
    void display_DayCalendar(int weekday, int day, int month, int year) {
        txtWeekday.setText(dayOfWeek[weekday - 1]);
        txtDayAndMonth.setText(day + " " + monthOfYear[month - 1]);
        txtYear.setText(String.valueOf(year));
        currentDayMonth.setText(dayOfWeek[weekday - 1]);
        updateFlowPane();
    }

    @FXML
    void initialize() {
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        display_DayCalendar(dayOfWeek, day, month, year);
        updateFlowPane();
    }

    /**
     * Update the event displayed in the flow pane
     */
    void updateFlowPane() {
        fpTodayEvent.getChildren().clear();
        LocalDate lDate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        for (Event e : Sess1on.eventList) {
            if (e.getDate().toLocalDate().toString().equals(lDate.toString())) {
                Label lb = new Label(e.getName());
                lb.setPrefHeight(80);
                lb.setPrefWidth(580);
                lb.setStyle(e.priorityColor[e.getPriority()]);
                fpTodayEvent.getChildren().add(lb);
            }
        }
    }

    /**
     * Change the view to Month view
     * @param e             event trigger this function
     * @throws IOException
     */
    public void ChangeView(ActionEvent e) throws IOException {
        doChangeview(e, "/design-monthview.fxml");
    }

    /**
     * Increase or decrease the current day by 1
     * @param e event trigger this function
     */
    public void changeviewday(ActionEvent e) {
        if (e.getSource() == btnPrevDay)
            calendar.add(Calendar.DATE, -1);
        else if (e.getSource() == btnNextDay)
            calendar.add(Calendar.DATE, 1);
        int day = calendar.get(Calendar.DATE);
        // Note: +1 the month for current month
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        display_DayCalendar(dayOfWeek, day, month, year);
    }

    /**
     * Switch to week page
     * @param e event that trigger this function
     */
    public void switchWeekpage(ActionEvent e) {
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Weekpage wp = new Weekpage(LocalDate.now());
        scene = new Scene(wp, 650, 600);
        scene.getStylesheets().add(getClass().getResource("/calendar-view.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
