package backend;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Sess1on {
    private String username = "";
    private String password = "";
    private String email = "";
    private String code = "";
    public static Boolean isCreatingEvent=false;
    public static Boolean deleteEvent=false;
    public static Event tempEvent = new Event();
    public static List<Event> eventList = new ArrayList<>();

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

    public List<Event> gettEventInWeek(LocalDate refWeek) {
        LocalDate startOfWeek = refWeek.minusDays(refWeek.getDayOfWeek().getValue() - 1);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        List<Event> listWeekEvent = new ArrayList<>();

        for (Event e : eventList) {
            LocalDate temp = e.getDate().toLocalDate();
            if ((temp.isAfter(startOfWeek) || temp.isEqual(startOfWeek)) && (temp.isBefore(endOfWeek) || temp.isEqual(endOfWeek))) {
                listWeekEvent.add(new Event(e));
            }
        }
        return listWeekEvent;
    }
    public static int getCurMaxID()
    {
        int maxID=0;
        for (Event e:eventList)if(e.getID()<(int)1e6)maxID=Math.max(maxID,e.getID());
        return maxID;
    }
    public static int findEvent(int id) {
        int i = 0;
        for (Event e : eventList) {
            if (e.getID() == id) return i;
            i++;
        }
        return -1;
    }

    public static List<Event> getEventList() {
        // Get list of events
        return eventList;
    }
    public void release()
    {
        username="";
        eventList = new ArrayList<>();
    }
}
