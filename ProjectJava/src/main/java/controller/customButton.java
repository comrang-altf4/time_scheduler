package controller;

import backend.Event;
import backend.Sess1on;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;

public class customButton extends Button {
    public Event event;
    private double cellheight = 67.2 / 3;
    customButton()
    {
        super();
    }
    customButton(Event e)
    {
        super();
        event=e;
        updateButtonContent();
        setColor();
    }
    public void setColor()
    {
        this.setStyle(event.priorityColor[event.getPriority()]);
    }
    public void addEvent() throws IOException {
        showPopUp();
    }
    public void editEvent() throws IOException {
        Sess1on.tempEvent=new Event(event);
        showPopUp();
        if (Sess1on.deleteEvent==false) {
            event = new Event(Sess1on.tempEvent);
            updateEventlist();
            updateButtonContent();
        }
    }

    private void updateButtonContent() {
        int span = event.getDuration() / 15 + 1;
        this.setText(event.getName());
        this.setMaxHeight(cellheight * span);
        setColor();
    }

    private void updateEventlist() {
        Sess1on.eventList.set(Sess1on.findEvent(event.getID()),event);
    }

    private void showPopUp() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/add-event-view.fxml"));
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();

    }

}
