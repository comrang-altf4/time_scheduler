package backend;
import project.Main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Event is a class contains all information and  about an event
 */
public class Event {
    private int id;
    private int duration;
    private String name;
    private String location;
    private String meetinglink="";
    private LocalDateTime date;
    private int priority;
    public String[] priorityColor={"-fx-background-color: #ff0000;","-fx-background-color: #f57b42;","-fx-background-color: #8df542;"};
    private String username;
    private int time=10;
    private int timeID=1;
    private List<String> listParticipants=new ArrayList<>();
    /**
     * Get the starting time of an event
     * @return <code>LocalDateTime</code>
     */
    public int getTimeID()
    {
        return timeID;
    }
    public int getTime()
    {
        return time;
    }

    public Event(int id, String name, String location, int duration, LocalDateTime date, int priority) {
        this.username= Main.getSession().getUsername();
        this.id = id;
        this.name = name;
        this.location = location;
        this.duration = duration;
        this.date = date;
        this.priority = priority;
    }

    public Event(int id, String name, String location, int duration, LocalDateTime date, int priority, String hyperlink) {
        this.username= Main.getSession().getUsername();
        this.id = id;
        this.name = name;
        this.location = location;
        this.duration = duration;
        this.date = date;
        this.priority = priority;
        this.meetinglink = hyperlink;
    }

    public LocalDateTime getEndTime()
    {
        return date.plusMinutes(duration);
    }

    public Event() {
        this.name="thisisdummyEvent";
        this.id = (int)1e6;
        this.location = "";
        this.duration =10 ;
        this.date = LocalDateTime.now();
        this.priority = 1;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getMeetinglink()
    {
        return meetinglink;
    }
    public Event(Event event, Object... mode) {
        if (mode.length==0)this.id = event.getID();
        else this.id=IdentityManagement.getID();
        this.name = event.getName();
        this.location = event.getLocation();
        this.date = event.getDate();
        this.priority = event.getPriority();
        this.duration = event.getDuration();
        this.meetinglink = event.meetinglink;
        this.listParticipants= new ArrayList<>(event.listParticipants);
        this.time=event.time;
    }

    public String getName() {
        // Get name
        return this.name;
    }

    public String getLocation() {
        // Get location
        return this.location;
    }

    public String getUsername() {
        // Get host of event
        return this.username;
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
    public List<String> getListParticipants()
    {
        return  listParticipants;
    }
    public void updateEvent(String name, String location, int duration, LocalDateTime date, int priority,String meetinglink,List<String>listParticipants,int time) {
//        this.id=ID_management.getID();
        this.time=time;
        this.listParticipants= new ArrayList<>(listParticipants);
        this.meetinglink=meetinglink;
        this.name = name;
        this.location = location;
        this.duration = duration;
        this.date = date;
        this.priority = priority;
    }
}