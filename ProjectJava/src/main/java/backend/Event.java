package backend;

import java.util.Calendar;

public class Event {
    private int id;
    private String name;
    private String location;
    private int duration;
    private int priority;// red=0,yellow=1,green=2;
    public String[] priorityColor={"-fx-background-color: #ff0000;","-fx-background-color: #f57b42;","-fx-background-color: #8df542;"};
    public Calendar date;

    Event(int id, String name, String location, int duration, Calendar date, int priority) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.duration = duration;
        this.date = date;
        this.priority = priority;
    }

    public Event() {

        this.id = ID_management.getID();
        this.date = Calendar.getInstance();
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

    public void updateEvent(){//String name, String location, int duration, Calendar date, int priority) {
        int d = 30;// from timer box
        int day = 26;// from set date box
        int month = 0;// from set date box
        int year = 2022;// from set date box
        int hour = 2;// from set date box
        int minute = 0;// from set date box
        int p = 3;// from priority box
        String n = "from text box";
        String l = "from text box";
        this.name = n;
        this.location = l;
        this.duration = d;
        this.date.set(year, month, day, hour, minute);
        this.priority = p;
    }
    public boolean equals(Event e) {
        return this.id == e.getID();
    }
    public String getName() {
        return this.name;
    }
}
