package backend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides necessary attributes and functions for the current session
 * @author comrang-altf4
 */
public class Sess1on {
    private String username = "";
    private String password = "";
    private String email = "";
    private String code = "";
    public static Boolean isCreatingEvent=false;
    public static Boolean deleteEvent=false;
    public static Event tempEvent = new Event();
    public static List<Event> eventList = new ArrayList<>();
    public static boolean saveClicked=false;
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

    /**
     * this function returns all user events in a desired week
     * @param refWeek reference week
     * @return  list of user events in the desired week
     */
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

    /**
     * this function finds user event with specific ID in list of user events
     * @param id user event ID
     * @return  index of wanted user event
     */
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
    public static void sortEvent()
    {
        for (int i=0;i< eventList.size()-1;i++)
            for (int j=i+1;j<eventList.size();j++)
                if (eventList.get(i).getDate().isAfter(eventList.get(j).getDate()))
                {
                    Event temp=new Event(eventList.get(i));
                    eventList.set(i,eventList.get(j));
                    eventList.set(j,temp);
                }
    }
    /**
     * release the current session
     */
    public void release()
    {
        username="";
        eventList = new ArrayList<>();
    }
}
