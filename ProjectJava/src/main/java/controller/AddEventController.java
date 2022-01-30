package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    void initialize()
    {
        LocalDate today = LocalDate.now();
        ObservableList<customLocalDateTime> timeslot= FXCollections.observableArrayList();
        for (LocalDateTime startTime = today.atTime(firstSlotStart); !startTime
                .isAfter(today.atTime(lastSlotStart)); startTime = startTime.plus(slotLength))
        {
            timeslot.add(new customLocalDateTime(startTime));
        }
        cbStart.setItems(timeslot);
        cbEnd.setItems(timeslot);
    }
    @FXML
    public void getCbItem()
    {
        LocalDateTime temp=cbStart.getValue().localDateTime;
        System.out.println(temp);
    }
}
