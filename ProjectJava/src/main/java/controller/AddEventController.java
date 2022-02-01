package controller;

import backend.Sess1on;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class AddEventController {
    private final LocalTime firstSlotStart = LocalTime.of(0, 0);
    private final java.time.Duration slotLength = java.time.Duration.ofMinutes(15);
    private final LocalTime lastSlotStart = LocalTime.of(23, 59);
    @FXML
    TextField eventName=new TextField();
    @FXML
    ComboBox<customLocalDateTime>cbStart=new ComboBox<customLocalDateTime>();
    @FXML
    ComboBox<customLocalDateTime>cbEnd=new ComboBox<customLocalDateTime>();
    @FXML
    ComboBox<customPriority>cbPriority=new ComboBox<customPriority>();
    @FXML
    DatePicker dpDate;
    @FXML
    void initialize()
    {
        LocalDate today = LocalDate.now();
        ObservableList<customLocalDateTime> timeslot= FXCollections.observableArrayList();
        ObservableList<customPriority> priority= FXCollections.observableArrayList();
        for (LocalDateTime startTime = today.atTime(firstSlotStart); !startTime
                .isAfter(today.atTime(lastSlotStart)); startTime = startTime.plus(slotLength))
        {
            timeslot.add(new customLocalDateTime(startTime));
        }
        for (int i=0;i<3;i++)priority.add(new customPriority(i));
        cbStart.setItems(timeslot);
        cbEnd.setItems(timeslot);
        cbPriority.setItems(priority);
        cbPriority.setValue(priority.get(0));
        cbStart.setValue(timeslot.get(0));
        cbEnd.setValue(timeslot.get(1));
        dpDate.setValue(today);
        eventName.setText("Untitled");
    }

    @FXML
    public void createEvent(ActionEvent e)
    {
        String name=eventName.getText();
        int duration = (int)ChronoUnit.MINUTES.between(cbStart.getValue().localDateTime, cbEnd.getValue().localDateTime);
        int tp=cbPriority.getValue().priority;
        String location="";
        LocalDateTime dateOfEvent=dpDate.getValue().atTime(cbStart.getValue().localDateTime.toLocalTime());
        Sess1on.tempEvent.updateEvent(name,location,duration,dateOfEvent,tp);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }
}
