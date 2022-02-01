package controller;

public class customPriority {
    public int priority;
    String[] color={"Red","Orange","Green"};
    customPriority(int x)
    {
        priority=x;
    }
    @Override
    public String toString()
    {
        return color[priority];
    }
}
