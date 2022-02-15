/**
 * This class is the background of the application which is used to send reminders in specific time.
 * This class is meant to be running on a server.
 *
 * @author Huy To Quang
 */
package backend;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


public class Background implements Runnable {
    private static Thread thread;
    private static boolean signal = true;

    /**
     * This function starts the application of the thread
     */
    public void run() {
        try {
            Database.connectDB();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        while (signal) {
            try {
                // Get all events in today
                List<Event> eventList = Database.getDayEvents();

                for (Event event : eventList) {
                    LocalDateTime date = event.getDate();
                    LocalDateTime current = LocalDateTime.now();
                    List<String> participants = Database.getParticipants(event.getID());
                    long time = Duration.between(current, date).toMinutes();
                    for (String participant : participants) {
                        int notify = Database.getNotifyTime(event.getID(), participant);
                        // Compare if the time has come to send reminder
                        if (notify >= time && time >= 0) {
                            Email.sendReminder(participant, event, time);
                            Database.setNotifyTime(event.getID(), participant, -1);
                        }
                    }
                }
                IdentityManagement.updateToDB();
                Thread.sleep(30 * 1000);
            } catch (SQLException | ClassNotFoundException | MessagingException | InterruptedException exception) {
                signal = false;
            }
        }
    }

    public static void stop() throws SQLException {
        thread.interrupt();
        Database.closeConnectionDB();
    }

    /**
     * This function constructs a new thread if there is no thread running.
     */
    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }
}
