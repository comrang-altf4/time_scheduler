package backend;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

public class Event {
    private int id;
    private String name;
    private String location;
    private int duration;
    private int priority;// red=0,yellow=1,green=2;
    public String[] priorityColor={"-fx-background-color: #ff0000;","-fx-background-color: #f57b42;","-fx-background-color: #8df542;"};
    public LocalDateTime date;

    Event(int id, String name, String location, int duration, LocalDateTime date, int priority) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.duration = duration;
        this.date = date;
        this.priority = priority;
    }

    public Event() {

        this.id = ID_management.getID();
        this.date = LocalDateTime.now();
    }
    public Event(String name,int dur,LocalDateTime sdate) {

        this.id = ID_management.getID();
        this.date = sdate;
        this.name=name;
        this.duration=dur;
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

    public int getPriority() {
        // Get priority
        return this.priority;
    }

    public void updateEvent(String name, String location, int duration, LocalDateTime date, int priority) {
        this.name = name;
        this.location = location;
        this.duration = duration;
        this.date=date;
        this.priority = priority;

    }
    public Event (Event other)
    {
        this.name = other.name;
        this.location = other.location;
        this.duration = other.duration;
        this.date=other.date;
        this.priority = other.priority;
    }
    public boolean equals(Event e) {
        return this.id == e.getID();
    }
    public String getName() {
        return this.name;
    }
}
