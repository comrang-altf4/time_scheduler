package backend;

import java.util.Calendar;

public class Event {
    private int id;
    private int duration;
    private String name;
    private String location;
    private Calendar date;
    private int priority;

    Event(int id, String name, String location, int duration, Calendar date, int priority) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.duration = duration;
        this.date = date;
        this.priority = priority;
    }

    Event() {}
    public String getName() {
        // Get name
        return this.name;
    }

    public String getLocation() {
        // Get location
        return this.location;
    }

    public int getID() {
        // Get id
        return this.id;
    }

    public int getDuration() {
        // Get duration
        return this.duration;
    }

    public Calendar getDate() {
        // Get date
        return this.date;
    }

    public int getPriority() {
        // Get priority
        return this.priority;
    }

    public void updateEvent(String name, String location, int duration, Calendar date, int priority) {
        this.name = name;
        this.location = location;
        this.duration = duration;
        this.date = date;
        this.priority = priority;
    }
}
