package backend;
import controller.RemindTime;
import project.Main;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Event is a class contains all information and  about an event
 * @author      comrang-altf4
 */
public class Event {
    private int id;
    private int duration;
    private String name;
    private String location;
    private String meetinglink="";
    private LocalDateTime date;
    private int priority;
    public String[] priorityColor={"-fx-background-color: #ff0000;","-fx-background-color:#f2f542;","-fx-background-color: #8df542;"};
    private String username;
    private int time=10;
    private int timeID=1;
    private List<String> listParticipants=new ArrayList<>();

    /**
     *
     * @return the combobox item index of the selected reminder time
     */
    public int getTimeID()
    {
        return timeID;
    }

    /**
     *
     * @return the reminder time in minute
     */
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

    /**
     * Construct an event
     * @param id
     * @param name
     * @param location
     * @param duration
     * @param date
     * @param priority
     * @param hyperlink
     */
    public Event(int id, String name, String location, int duration, LocalDateTime date, int priority, String hyperlink) {
        this.username= Main.getSession().getUsername();
        this.id = id;
        this.name = name;
        this.location = location;
        this.duration = duration;
        this.date = date;
        this.date.truncatedTo(ChronoUnit.SECONDS);
        this.priority = priority;
        this.meetinglink = hyperlink;
    }

    /**
     *
     * @return  the time when the event end
     */
    public LocalDateTime getEndTime()
    {
        return date.plusMinutes(duration);
    }

    /**
     * Construct a dummy event
     */
    public Event() {
        this.name="thisisdummyEvent";
        this.id = (int)1e6;
        this.location = "";
        this.duration =10 ;
        this.date = LocalDateTime.now();
        this.priority = 1;
    }

    /**
     * Assign id for event
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return  the meeting link (if any)
     */
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
        for (int i=0;i<4;i++)if (this.time== RemindTime.remindInt[i]){this.timeID=i;break;}
    }

    /**
     *
     * @return  the event name
     */
    public String getName() {
        // Get name
        return this.name;
    }

    /**
     *
     * @return  the event location
     */
    public String getLocation() {
        // Get location
        return this.location;
    }

    /**
     *
     * @return the host of the event
     */
    public String getUsername() {
        // Get host of event
        return this.username;
    }

    /**
     *
     * @return  the ID of the event
     */
    public int getID() {
        // Get id
        return this.id;
    }

    /**
     *
     * @return the duration of the event in minute
     */
    public int getDuration() {
        // Get duration
        return this.duration;
    }

    /**
     *
     * @return  the date of the event
     */
    public LocalDateTime getDate() {
        // Get date
        return this.date;
    }

    /**
     *
     * @return  the priority of the event
     */
    public int getPriority() {
        // Get priority
        return this.priority;
    }

    /**
     *
     * @param date  the date to assign to the event date
     */
    public void setDate(LocalDateTime date) {
        // Set date
        this.date = date;
    }

    /**
     *
     * @param name  the name to assign to the event
     */
    public void setName(String name) {
        // Set event's name
        this.name = name;
    }

    /**
     *
     * @param location  the location to assign to the event
     */
    public void setLocation(String location) {
        // Set event's location
        this.location = location;
    }

    /**
     *
     * @param username  the host to assign to the event
     */
    public void setUsername(String username) {
        // Set event's host
        this.username = username;
    }

    /**
     *
     * @return  the email list of people who participate the event
     */
    public List<String> getListParticipants()
    {
        return  listParticipants;
    }

    /**
     * This function update the event with provided arguments
     * @param name              name of the event
     * @param location          location of the event
     * @param duration          duration of the event
     * @param date              date of the event
     * @param priority          priority of the event
     * @param meetinglink       the meeting link of the event
     * @param listParticipants  the list of people who participate in the event
     * @param time              the remind time of the event
     */
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

    /**
     * This function set the event participants
     * @param participants  list of participants
     */
    public void setListParticipants(List<String>participants)
    {
        this.listParticipants.addAll(participants);
    }
}