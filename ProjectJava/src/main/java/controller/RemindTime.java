package controller;

import java.util.HashMap;

/**
 * This class generates element for remind time combobox
 * @author comrang-altf4
 */
public class RemindTime {
    HashMap<Integer,String> remindtime=new HashMap<Integer,String>(){{
        put(10,"10 minutes");
        put(10*6,"1 hour");
        put(10*6*24*3,"3 days");
        put(10*6*24*7,"1 week");
    }};
    static public int[] remindInt={10,10*6,10*6*24*3,10*6*24*7};
    int id;

    /**
     * construct with combobox item id
     * @param x the id
     */
    public RemindTime(int x)
    {
        id=x;
    }

    /**
     *
     * @return corresponding remind time in minutes
     */
    public int getTime()
    {
        return remindInt[id];
    }

    /**
     * override toString() to displayed desired reminder string
     * @return  desired reminder string
     */
    @Override
    public String toString()
    {
        return remindtime.get(remindInt[id]);
    }
}
