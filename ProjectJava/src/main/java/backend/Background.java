/**
 * This class is the background of the application which is used to send reminders in specific time.
 * This class is meant to be running on a server.
 * @author Huy To Quang
 */
package backend;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static backend.Database.connectDB;

public class Background implements Runnable {
    private Thread thread;

    /**
     * This function starts the application of the thread
     */
    public void run() {
        try {
            Database.connectDB();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                // Get all events in today
                List<Event> eventList = Database.getDayEvents();

                for (Event event : eventList) {
                    LocalDateTime date = event.getDate();
                    LocalDateTime current = LocalDateTime.now();
                    List<String> participants = Database.getParticipants(event.getID());
                    int time = (date.getDayOfMonth() - current.getDayOfMonth()) * 24 * 60 + (date.getHour() - current.getHour()) * 60 + (date.getMinute() - current.getMinute());
                    for (String participant : participants) {
                        int notify = Database.getNotifyTime(event.getID(), participant);

                        // Compare if the time has come to send reminder
                        if(notify<=time&&notify>=0) {
                            Email.sendReminder(participant, event, notify);
                            Database.setNotifyTime(event.getID(), participant, -1);
                        }
                    }
                }
                Thread.sleep(1000 * 30);

            } catch (SQLException|ClassNotFoundException|InterruptedException|MessagingException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * This function constructs a new thread if there is no thread running.
     */
    public void start() {
        if(thread==null) {
            thread = new Thread(this);
            thread.start();
        }
    }
}
