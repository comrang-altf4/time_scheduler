package controller;

/**
 *  This class provides priority with some important functions and attributes
 * @author comrang-altf4
 */
public class customPriority {
    public int priority;
    String[] color={"Red","Orange","Green"};
    customPriority(int x)
    {
        priority=x;
    }

    /**
     * override toString() to display the priority in desired string
     * @return
     */
    @Override
    public String toString()
    {
        return color[priority];
    }
}
