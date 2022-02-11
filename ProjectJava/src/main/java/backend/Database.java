/**
 * This class links the database to the application.
 * @author Huy To Quang
 */

package backend;

import org.mindrot.jbcrypt.BCrypt;
import project.Main;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Statement statement,tStatement;
    private final static String JDBC_URL = "jdbc:oracle:thin:@db1.fb2.frankfurt-university.de:1521:info01";
    private final static String JDBC_User = "S1_student2_18";
    private final static String JDBC_Password = "Ora2221";


    /**
     * This function connects the application to database by using ojdbc.
     * @throws ClassNotFoundException whenever driver cannot be loaded
     * @throws SQLException whenever cannot connect to the database
     */
    public static void connectDB() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_User, JDBC_Password);
        statement = connection.createStatement();
        tStatement = connection.createStatement();
    }

    /**
     * This function authorizes user whenever they log in the application.
     * @param username username that the user entered
     * @param password password that the user entered
     * @return true if the authentication is successful, otherwise false.
     * @throws SQLException whenever there is a problem relating to database
     * @throws ClassNotFoundException whenever driver cannot be loaded
     */
    public static boolean login(String username, String password) throws SQLException, ClassNotFoundException {
        // Result from query
        ResultSet result = statement.executeQuery("SELECT HASHPASS\n" + "FROM ACCOUNTS\n" + "WHERE USERNAME = '" + username + "'");

        // Check if query returns null
        if (result.isBeforeFirst()) {
            result.next();
            // Check password
            return BCrypt.checkpw(password, result.getString(1));
        }
        return false;
    }

    /**
     * This function gets corresponding email according to the username.
     * @param username username of the user
     * @return email of a user as String
     * @throws SQLException whenever there is a problem relating to database
     */
    public static String getEmail(String username) throws SQLException {
        ResultSet result = statement.executeQuery("SELECT EMAIL\n" + "FROM ACCOUNTS\n" + "WHERE USERNAME = '" + username + "'");
        result.next();
        return result.getString(1);
    }

    /**
     * This function checks whether an email is already registered.
     * @param email an email that needed to be checked
     * @return true if an email is already existed, otherwise false.
     * @throws SQLException whenever there is a problem relating to database
     */
    public static boolean checkEmail(String email) throws SQLException {
        ResultSet result = statement.executeQuery("SELECT *\n" + "FROM ACCOUNTS\n" + "WHERE EMAIL = '" + email + "'");
        return result.isBeforeFirst();
    }

    /**
     * This function checks whether a username is already registered.
     * @param username a username that needed to be checked.
     * @return true if username is already existed, otherwise false.
     * @throws SQLException whenever there is a problem relating to database
     */
    public static boolean checkUsername(String username) throws SQLException {
        ResultSet result = statement.executeQuery("SELECT *\n" + "FROM ACCOUNTS\n" + "WHERE USERNAME = '" + username + "'");
        return result.isBeforeFirst();
    }

    /**
     * This function changes password of a user.
     * @param email email of the user that need to change password
     * @param password new password that the user entered
     * @throws SQLException whenever there is a problem relating to database
     */
    public static void changePassword(String email, String password) throws SQLException {
        password = BCrypt.hashpw(password, BCrypt.gensalt());
        statement.execute("UPDATE ACCOUNTS\n" + "SET HASHPASS = '" + password + "'\n" + "WHERE EMAIL = '" + email + "'");
        statement.execute("COMMIT");
    }

    /**
     * This function registers new user and store them in database.
     * @param username username that the user entered
     * @param password password that the user entered
     * @param email email that the user entered
     * @throws SQLException whenever there is a problem relating to database
     */
    public static void register(String username, String password, String email) throws SQLException {
        password = BCrypt.hashpw(password, BCrypt.gensalt());
        statement.execute("INSERT INTO ACCOUNTS\n" + "VALUES ('" + username + "', '" + password + "', '" + email + "')");
        statement.execute("COMMIT");
    }

    /**
     * This function checks whether current user is the host of an event.
     * @param id id of an event that needed to check host
     * @param username username that needed to be checked
     * @return true if they are the host, otherwise false.
     * @throws SQLException whenever there is a problem relating to database
     */
    public static boolean checkHost(int id, String username) throws SQLException {
        ResultSet result = statement.executeQuery("SELECT EUSERNAME\n" + "FROM APPOINTMENTS\n" + "WHERE EID = " + id + " AND EUSERNAME = '" + username + "'");
        return result.isBeforeFirst();
    }

    /**
     * This function adds new event into the database.
     * @param event event that needed to be added to database
     * @throws SQLException whenever there is a problem with database
     */
    public static void addEvent(Event event) throws SQLException {
        LocalDateTime date = event.getDate();
        statement.execute("INSERT INTO APPOINTMENTS\n" + "VALUES(" + event.getID() + ", '" + Main.getSession().getUsername() + "', '" + event.getName() + "', '" + event.getLocation() + "', " +
                event.getDuration() + ", " + date.getDayOfMonth() + ", " + date.getMonthValue() + ", " + date.getYear() + ", " + date.getHour() + ", " + date.getMinute() + ", " +
                event.getPriority() + ", '" + event.getMeetinglink() + "')");
        statement.execute("INSERT INTO PARTICIPANTS\n" + "VALUES(" + event.getID() + ", '" + Main.getSession().getEmail() + "', " + event.getTime() + ")");
        statement.execute("COMMIT");
        addParticipants(event.getID(), event.getListParticipants());
    }

    /**
     * This function updates old event only if current user is the host of the event.
     * @param event event that needed to be updated to the database
     * @throws SQLException whenever there is a problem with database
     * @throws ClassNotFoundException whenever there is a problem with class loading
     */
    public static void updateEvent(Event event) throws SQLException, ClassNotFoundException {
        LocalDateTime date = event.getDate();
        if (checkHost(event.getID(), Main.getSession().getUsername())) {
            statement.execute("UPDATE APPOINTMENTS\n" + "SET EUSERNAME = '" + Main.getSession().getUsername() + "', ENAME = '" + event.getName() + "', ELOCATION = '" + event.getLocation() + "', EDURATION = " + event.getDuration() + ", EDAY = " +
                    date.getDayOfMonth() + ", EMONTH = " + date.getMonthValue() + ", EYEAR = " + date.getYear() + ", EHOUR = " + date.getHour() + ", EMINUTE = " +
                    date.getMinute() + ", EPRIORITY = " + event.getPriority() + ", EHYPERLINK = '" + event.getMeetinglink() + "'\n" + "WHERE EID = " + event.getID() + "");
            updateParticipants(event);
            statement.execute("COMMIT");
        }
    }


    /**
     * This function gets all events of current user.
     * @return all events in a List.
     * @throws SQLException whenever there is a problem with database
     * @throws ClassNotFoundException whenever there is a problem with class loading
     */
    public static List<Event> getEvents() throws SQLException, ClassNotFoundException {
        ResultSet result = tStatement.executeQuery("SELECT *\n" + "FROM APPOINTMENTS JOIN PARTICIPANTS ON (EID = PID)\n" + "WHERE PEMAIL = '" + Main.getSession().getEmail() + "'");
        List<Event> eventList = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.now();
        while (result.next()) {
            LocalDateTime date = localDateTime.withDayOfMonth(result.getInt(6)).withMonth(result.getInt(7)).withYear(result.getInt(8)).withHour(result.getInt(9)).withMinute(result.getInt(10)).withSecond(0).withNano(0);
            Event event = new Event(result.getInt(1), result.getString(3), result.getString(4), result.getInt(5), date, result.getInt(11), result.getString(12));
            event.setListParticipants(getParticipants(event.getID()));
            eventList.add(event);
        }
        return eventList;
    }

    /**
     * This function gets all events that will happen today for sending reminders.
     * @return all events in a List.
     * @throws SQLException whenever there is a problem with database
     * @throws ClassNotFoundException whenever there is problem with class loading
     */
    public static List<Event> getDayEvents() throws SQLException, ClassNotFoundException {
        LocalDateTime localDateTime = LocalDateTime.now();
        List<Event> eventList = new ArrayList<>();
        ResultSet result = statement.executeQuery("SELECT *\n" + "FROM APPOINTMENTS\n" + "WHERE EDAY = " + localDateTime.getDayOfMonth() + " AND EMONTH = " + localDateTime.getMonthValue() +
                " AND EYEAR = " + localDateTime.getYear());

        while (result.next()) {
            LocalDateTime date = localDateTime.withDayOfMonth(result.getInt(6)).withMonth(result.getInt(7)).withYear(result.getInt(8)).withHour(result.getInt(9)).withMinute(result.getInt(10));
            Event event = new Event(result.getInt(1), result.getString(3), result.getString(4), result.getInt(5), date, result.getInt(11), result.getString(12));
            eventList.add(event);
        }
        return eventList;
    }

    /**
     * This function deletes events according to corresponding ids.
     * @param ids list of ids that needed to be deleted
     * @throws SQLException whenever there is a problem with the database
     */
    public static void deleteEvents(List<Integer> ids) throws SQLException {
        for (Integer id : ids) {
            if (checkHost(id, Main.getSession().getUsername()))
                statement.execute("DELETE FROM APPOINTMENTS\n" + "WHERE EID = " + id);
        }
        statement.execute("COMMIT");
    }

    /**
     * This function adds a list of participants to an event
     * @param id id of the event that need to add participants in
     * @param emails list of participants
     * @throws SQLException whenever there is a problem with the database
     */
    public static void addParticipants(int id, List<String> emails) throws SQLException {
        for (String email : emails) {
            if (checkHost(id, Main.getSession().getUsername())&&!email.isEmpty())
                statement.execute("INSERT INTO PARTICIPANTS\n" + "VALUES(" + id + ", '" + email + "', 30)");
        }
        statement.execute("COMMIT");
    }

    /**
     * This function gets all participants of an event.
     * @param id id of the event that needed to get list of participants from
     * @return all participants in a List
     * @throws SQLException whenever there is a problem with the database
     * @throws ClassNotFoundException whenever there is a problem with class loading
     */
    public static List<String> getParticipants(int id) throws SQLException, ClassNotFoundException {
        ResultSet result = statement.executeQuery("SELECT PEMAIL\n" + "FROM PARTICIPANTS\n" + "WHERE PID = " + id);
        List<String> emails = new ArrayList<>();
        boolean flag = true;
        while(result.next()) {
            // Add participant's email to list
            emails.add(result.getString(1));
            if(emails.size()==1&&flag) {
                emails.remove(0);
                flag = false;
            }
        }
        return emails;
    }

    /**
     * This function updates the list of participants of an event in a database.
     * @param event event that needed to be updated new list of participants
     * @throws SQLException whenever there is a problem with the database
     * @throws ClassNotFoundException whenever there is a problem with class loading
     */
    public static void updateParticipants(Event event) throws SQLException, ClassNotFoundException {
        List<String> old = getParticipants(event.getID());
        for(String email: old) {
            statement.execute("DELETE FROM PARTICIPANTS\n" + "WHERE PEMAIL = '" + email + "' AND PID = " + event.getID());
            statement.execute("COMMIT");
        }
        for(String participant: event.getListParticipants()) {
            if(participant.isEmpty())
                continue;
            statement.execute("INSERT INTO PARTICIPANTS\n" + "VALUES(" + event.getID() + ", '" + participant + "', 30)");
        }
        statement.execute("COMMIT");
    }

    /**
     * This function gets notification time for a reminder.
     * @param id id of the event that needed to be reminded
     * @param email email of the participant of the event
     * @return notify time as Integer.
     * @throws SQLException whenever there is a problem with the database.
     * @throws ClassNotFoundException whenever there is a problem with class loading.
     */
    public static int getNotifyTime(int id, String email) throws SQLException, ClassNotFoundException {
        ResultSet result = statement.executeQuery("SELECT PTIME\n" + "FROM PARTICIPANTS\n" + "WHERE PEMAIL = '" + email + "' AND PID = " + id);
        if (result.isBeforeFirst()) {
            result.next();
            return result.getInt(1);
        }
        return 0;
    }

    /**
     * This function sets new notify time for a reminder.
     * @param id id of the event that needed to change new reminding time
     * @param email email of the participant in the event
     * @param time new notify time
     * @throws SQLException whenever there is a problem with the database
     * @throws ClassNotFoundException whenever there is a problem with class loading
     */
    public static void setNotifyTime(int id, String email, int time) throws SQLException, ClassNotFoundException {
        statement.execute("UPDATE PARTICIPANTS\n" + "SET PTIME = " + time + "\nWHERE PID = " + id + " AND PEMAIL = '" + email + "'");
        statement.execute("COMMIT");
    }

    /**
     * This function gets the last event's id that has been added to database.
     * @return last inserted id as Integer.
     * @throws SQLException whenever there is a problem with the database
     */
    public static int getLastInsertID() throws SQLException {
        ResultSet result = statement.executeQuery("SELECT MAX(EID)\n" + "FROM APPOINTMENTS");
        if (result.isBeforeFirst()) {
            result.next();
            return result.getInt(1);
        } else return 0;
    }
}