package backend;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static backend.Database.connectDB;

public class Background implements Runnable {
    private Thread thread;
    public void run() {
        try {
            Database.connectDB();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                List<Event> eventList = Database.getDayEvents();
                for (Event event : eventList) {
                    LocalDateTime date = event.getDate();
                    LocalDateTime current = LocalDateTime.now();
                    List<String> participants = Database.getParticipants(event.getID());
                    int time = (date.getDayOfMonth() - current.getDayOfMonth()) * 24 * 60 + (date.getHour() - current.getHour()) * 60 + (date.getMinute() - current.getMinute());
                    for (String participant : participants) {
                        int notify = Database.getNotifyTime(event.getID(), participant);
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
    public void start() {
        if(thread==null) {
            thread = new Thread(this);
            thread.start();
        }
    }
}
