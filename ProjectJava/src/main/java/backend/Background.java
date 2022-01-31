package backend;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public class Background implements Runnable {
    private Thread thread;

    public void run() {
        while (true) {
            try {
                List<Event> eventList = Database.getDayEvents();
                for (Event event : eventList) {
                    Calendar calendar = event.getDate();
                    Calendar current = Calendar.getInstance();
                    List<String> participants = Database.getParticipants(event.getID());
                    int time = (calendar.get(Calendar.HOUR_OF_DAY) - current.get(Calendar.HOUR_OF_DAY)) * 60 + calendar.get(Calendar.MINUTE) - current.get(Calendar.MINUTE);
                    for (String participant : participants) {
                        int notify = Database.getNotifyTime(event.getID(), participant);
                        if(notify==time)
                            Email.sendReminder(participant, event, notify);
                    }
                }
                Thread.sleep(1000 * 30);

            } catch (SQLException|ClassNotFoundException|InterruptedException|MessagingException exception) {
                exception.printStackTrace();
            }
        }
    }
    public void start() {
        if(thread==null) {
            thread = new Thread(this);
            thread.start();
        }
    }
}
