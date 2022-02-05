package backend;
import java.time.LocalDateTime;

public class Event {
    private int id;
    private int duration;
    private String name;
    private String location;
    private LocalDateTime date;
    private int priority;
    public String[] priorityColor={"-fx-background-color: #ff0000;","-fx-background-color: #f57b42;","-fx-background-color: #8df542;"};
    private String username;

    public Event(int id, String name, String location, int duration, LocalDateTime date, int priority) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.duration = duration;
        this.date = date;
        this.priority = priority;
    }
    public LocalDateTime getEndTime()
    {
        return date.plusMinutes(duration);
    }
    public Event() {
        this.name="thisisdummyEvent";
        this.id = 1;
        this.location = "";
        this.duration =10 ;
        this.date = LocalDateTime.now();
        this.priority = 1;
    }

    public Event(Event event) {
        this.id = event.getID();
        this.name = event.getName();
        this.location = event.getLocation();
        this.date = event.getDate();
        this.priority = event.getPriority();
        this.duration = event.getDuration();
    }

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

    public LocalDateTime getDate() {
        // Get date
        return this.date;
    }

    public int getPriority() {
        // Get priority
        return this.priority;
    }

    public void setDate(LocalDateTime date) {
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

    public void updateEvent(String name, String location, int duration, LocalDateTime date, int priority) {
        this.name = name;
        this.location = location;
        this.duration = duration;
        this.date = date;
        this.priority = priority;
    }
}