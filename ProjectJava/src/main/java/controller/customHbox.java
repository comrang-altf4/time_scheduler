package controller;

import javafx.scene.layout.HBox;

/**
 * This class provides HBox with some important attributes
 * @author comrang-altf4
 */
public class customHbox extends HBox {
    public int firstRow;
    public int lastRow;
    public customHbox()
    {
        super();
    }

    /**
     * set HBox first row in gridpane
     * @param x row index
     */
    public void setFirstRow(int x)
    {
        firstRow=x;
    }

    /**
     * set HBox last row in gridpane
     * @param x row index
     */
    public void setLastRow(int x)
    {
        lastRow=x;
    }
}
