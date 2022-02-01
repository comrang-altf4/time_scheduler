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
        showPopUp();
        refreshEvent();
        updateButtonContent();
    }

    private void updateButtonContent() {
        this.setText(event.getName());
    }

    private void refreshEvent() {
        event= Sess1on.findEvent(event.getID());
    }

    private void showPopUp() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/add-event-view.fxml"));
        Stage stage=new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();

    }

}
