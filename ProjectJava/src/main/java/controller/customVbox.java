package controller;

import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class customVbox extends VBox {
    public LocalDate curDate;
    public int totalChildren=0;
    customVbox()
    {
        super();
    }
    public void setCurDate(int year,int month,int day)
    {
        curDate=LocalDate.of(year,month,day);
    }
    public LocalDate getCurDate()
    {
        return  curDate;
    }
}
