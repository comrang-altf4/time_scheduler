package controller;

import javafx.scene.layout.HBox;

public class customHbox extends HBox {
    public int firstRow;
    public int lastRow;
    public customHbox()
    {
        super();
    }
    public void setFirstRow(int x)
    {
        firstRow=x;
    }
    public void setLastRow(int x)
    {
        lastRow=x;
    }
}
