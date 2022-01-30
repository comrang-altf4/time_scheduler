package controller;

import javafx.scene.layout.HBox;

public class CustomHBox extends HBox {
    public int firstRow;
    public int lastRow;
    public CustomHBox()
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
