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

    // Working
    // Connect to database
    public static void connectDB() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_User, JDBC_Password);
        statement = connection.createStatement();
        tStatement = connection.createStatement();
    }

    // Working
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

    // Working
    public static String getEmail(String username) throws SQLException {
        ResultSet result = statement.executeQuery("SELECT EMAIL\n" + "FROM ACCOUNTS\n" + "WHERE USERNAME = '" + username + "'");
        result.next();
        return result.getString(1);
    }

    // Working
    public static boolean checkEmail(String email) throws SQLException {
        ResultSet result = statement.executeQuery("SELECT *\n" + "FROM ACCOUNTS\n" + "WHERE EMAIL = '" + email + "'");
        return result.isBeforeFirst();
    }

    // Working
    public static boolean checkUsername(String username) throws SQLException {
        ResultSet result = statement.executeQuery("SELECT *\n" + "FROM ACCOUNTS\n" + "WHERE USERNAME = '" + username + "'");
        return result.isBeforeFirst();
    }

    // Working
    public static void changePassword(String email, String password) throws SQLException {
        password = BCrypt.hashpw(password, BCrypt.gensalt());
        statement.execute("UPDATE ACCOUNTS\n" + "SET HASHPASS = '" + password + "'\n" + "WHERE EMAIL = '" + email + "'");
        statement.execute("COMMIT");
    }

    // Working
    public static void register(String username, String password, String email) throws SQLException {
        password = BCrypt.hashpw(password, BCrypt.gensalt());
        statement.execute("INSERT INTO ACCOUNTS\n" + "VALUES ('" + username + "', '" + password + "', '" + email + "')");
        statement.execute("COMMIT");
    }

    // Working
    public static boolean checkHost(int id, String username) throws SQLException {
        ResultSet result = statement.executeQuery("SELECT EUSERNAME\n" + "FROM APPOINTMENTS\n" + "WHERE EID = " + id + " AND EUSERNAME = '" + username + "'");
        return result.isBeforeFirst();
    }

    // Working
    public static void addEvent(Event event) throws SQLException {
        LocalDateTime date = event.getDate();
        statement.execute("INSERT INTO APPOINTMENTS\n" + "VALUES(" + event.getID() + ", '" + Main.getSession().getUsername() + "', '" + event.getName() + "', '" + event.getLocation() + "', " +
                event.getDuration() + ", " + date.getDayOfMonth() + ", " + date.getMonthValue() + ", " + date.getYear() + ", " + date.getHour() + ", " + date.getMinute() + ", " +
                event.getPriority() + ", '" + event.getMeetinglink() + "')");
        statement.execute("INSERT INTO PARTICIPANTS\n" + "VALUES(" + event.getID() + ", '" + Main.getSession().getEmail() + "', " + event.getTime() + ")");
        statement.execute("COMMIT");
        addParticipants(event.getID(), event.getListParticipants());
    }

    // Working
    // Not host cannot update event
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


    // Working
    // Get list events of a user
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

    // Working
    // Get list of all events in one day for the system
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

    // Working
    public static void deleteEvents(List<Integer> ids) throws SQLException {
        for (Integer id : ids) {
            if (checkHost(id, Main.getSession().getUsername()))
                statement.execute("DELETE FROM APPOINTMENTS\n" + "WHERE EID = " + id);
        }
        statement.execute("COMMIT");
    }

    // Working
    public static void addParticipants(int id, List<String> emails) throws SQLException {
        for (String email : emails) {
            if (checkHost(id, Main.getSession().getUsername()))
                statement.execute("INSERT INTO PARTICIPANTS\n" + "VALUES(" + id + ", '" + email + "', 30)");
        }
        statement.execute("COMMIT");
    }

    // Working
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

    // Working
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

    // Working
    public static int getNotifyTime(int id, String email) throws SQLException, ClassNotFoundException {
        ResultSet result = statement.executeQuery("SELECT PTIME\n" + "FROM PARTICIPANTS\n" + "WHERE PEMAIL = '" + email + "' AND PID = " + id);
        if (result.isBeforeFirst()) {
            result.next();
            return result.getInt(1);
        }
        return 0;
    }

    // Working
    public static void setNotifyTime(int id, String email, int time) throws SQLException, ClassNotFoundException {
        statement.execute("UPDATE PARTICIPANTS\n" + "SET PTIME = " + time + "\nWHERE PID = " + id + " AND PEMAIL = '" + email + "'");
        statement.execute("COMMIT");
    }

    // Working
    public static int getLastInsertID() throws SQLException {
        ResultSet result = statement.executeQuery("SELECT MAX(EID)\n" + "FROM APPOINTMENTS");
        if (result.isBeforeFirst()) {
            result.next();
            return result.getInt(1);
        } else return 0;
    }
}