package controller;

import java.util.HashMap;

public class RemindTime {
    HashMap<Integer,String> remindtime=new HashMap<Integer,String>(){{
        put(10,"10 minutes");
        put(10*6,"1 hour");
        put(10*6*24*3,"3 days");
        put(10*6*24*7,"1 week");
    }};
    public int[] remindInt={10,10*6,10*6*24*3,10*6*24*7};
    int id;
    public RemindTime(int x)
    {
        id=x;
    }
    public int getTime()
    {
        return remindInt[id];
    }
    @Override
    public String toString()
    {
        return remindtime.get(remindInt[id]);
    }
}
