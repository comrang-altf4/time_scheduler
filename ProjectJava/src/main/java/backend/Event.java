package backend;

import java.util.Calendar;

public class Event {
    private int id;
    private int duration;
    private String name;
    private String location;
    private Calendar date;
    private int priority;
    private String username;

    public Event(int id, String name, String location, int duration, Calendar date, int priority) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.duration = duration;
        this.date = date;
        this.priority = priority;
    }

    public Event() {}

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

    public void setDate(Calendar date) {
        // Set date
        this.date = date;
    }

    public void setName(String name) {
        // Set event's name
        this.name = name;
    }

    public void setLocation(String location) {
        // Set event's location
        this.location = location;
    }

    public void setUsername(String username) {
        // Set event's host
        this.username = username;
    }

    public void updateEvent(String name, String location, int duration, Calendar date, int priority) {
        this.name = name;
        this.location = location;
        this.duration = duration;
        this.date = date;
        this.priority = priority;
    }
}
