package controller;

import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class customVbox extends VBox {
    public LocalDate curDate;
    customVbox()
    {
        super();
    }
    public void setCurDate(int year,int month,int day)
    {
        curDate=LocalDate.of(year,month,day);
    }
}
