package controller;

import javafx.scene.layout.VBox;

import java.time.LocalDate;

/**
 * This class provides VBox with some important attributes and functions
 * @author comrang-altf4
 */
public class customVbox extends VBox {
    public LocalDate curDate;
    public int totalChildren=0;
    customVbox()
    {
        super();
    }

    /**
     * set the current date assigned to the VBox
     * @param year
     * @param month
     * @param day
     */
    public void setCurDate(int year,int month,int day)
    {
        curDate=LocalDate.of(year,month,day);
    }

    /**
     *
     * @return  date assigned to VBox
     */
    public LocalDate getCurDate()
    {
        return  curDate;
    }
}
