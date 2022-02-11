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

import static backend.Sess1on.tempEvent;

/**
 * This class provides Button with some important attributes
 * @author comrang-altf4
 */
public class customButton extends Button {
    public Event event;
    private double cellheight = 67.2 / 3;
    customButton()
    {
        super();
    }

    /**
     * Construct button and assign it with an event
     * @param e user Event
     */
    customButton(Event e)
    {
        super();
        event=e;
        updateButtonContent();
        setColor();
    }

    /**
     * Set color for event based on priority
     */
    public void setColor()
    {
        this.setStyle(event.priorityColor[event.getPriority()]);
    }

    /**
     * add new user Event
     * @throws IOException
     */
    public void addEvent() throws IOException {
        showPopUp();
    }

    /**
     * edit event assigned to button
     * @throws IOException
     */
    public void editEvent() throws IOException {
        tempEvent=new Event(event);
        showPopUp();
        if (Sess1on.deleteEvent==false) {
            event = new Event(tempEvent);
            updateEventlist();
            updateButtonContent();
        }
    }

    /**
     * update button content after editEvent()
     */
    private void updateButtonContent() {
        int span = event.getDuration() / 15 + 1;
        this.setText(event.getName());
        this.setMaxHeight(cellheight * span);
        setColor();
    }

    /**
     * update user event list with edited event
     */
    private void updateEventlist() {
        Sess1on.eventList.set(Sess1on.findEvent(event.getID()),event);
    }

    /**
     * show add/edit user event window
     * @throws IOException
     */
    private void showPopUp() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/add-event-view.fxml"));
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();

    }

}
