package backend;

import java.util.ArrayList;
import java.util.List;

public class Sess1on {
    private String username = "";
    private String password = "";
    private String email = "";
    private String code = "";
    private static List<Event> eventList=new ArrayList<>();

    public void setUsername(String username) {
        // Set username
        this.username = username;
    }
    public void setPassword(String password) {
        // Set password
        this.password = password;
    }
    public void setEmail(String email) {
        // Set email
        this.email = email;
    }

    public void setCode(String code) {
        // Set verification code
        this.code = code;
    }

    public String getUsername() {
        // Get username
        return this.username;
    }
    public String getPassword() {
        // Get password
        return this.password;
    }
    public String getEmail() {
        // Get email
        return this.email;
    }
    public String getCode() {
        // Get verification code
        return this.code;
    }

    public static Event findEvent(int id)
    {
        for (Event e: eventList)if (e.getID()==id)return e;
        return new Event();
    }

    public static List<Event> getEventList() {
        // Get list of events
        return eventList;
    }
}
