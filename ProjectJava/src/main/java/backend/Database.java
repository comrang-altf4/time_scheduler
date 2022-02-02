package backend;

import org.mindrot.jbcrypt.BCrypt;
import project.Main;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Statement statement;
    private final static String JDBC_URL = "jdbc:mysql://localhost:3306/Java";
    private final static String JDBC_User = "root";
    private final static String JDBC_Password = "eKak5CxcBsr";

    // Working
    // Connect to database
    private static void connectDB() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_User, JDBC_Password);
        statement = connection.createStatement();
    }

    // Working
    public static boolean login(String username, String password) throws SQLException, ClassNotFoundException {
        connectDB();

        // Result from query
        ResultSet result = statement.executeQuery("SELECT HASHPASS\n" + "FROM ACCOUNTS\n" + "WHERE USERNAME = '" + username + "';");

        // Check if query returns null
        if (result.isBeforeFirst()) {
            result.next();
            // Check password
            return BCrypt.checkpw(password, result.getString(1));
        }
        return false;
    }

    // Working
    public static String getEmail(String username) throws SQLException, ClassNotFoundException {
        connectDB();
        ResultSet result = statement.executeQuery("SELECT EMAIL\n" + "FROM ACCOUNTS\n" + "WHERE USERNAME = '" + username + "';");
        result.next();
        return result.getString(1);
    }

    // Working
    public static boolean checkEmail(String email) throws SQLException, ClassNotFoundException {
        connectDB();
        ResultSet result = statement.executeQuery("SELECT *\n" + "FROM ACCOUNTS\n" + "WHERE EMAIL = '" + email + "';");
        return result.isBeforeFirst();
    }

    // Working
    public static boolean checkUsername(String username) throws SQLException, ClassNotFoundException {
        connectDB();
        ResultSet result = statement.executeQuery("SELECT *\n" + "FROM ACCOUNTS\n" + "WHERE USERNAME = '" + username + "';");
        return result.isBeforeFirst();
    }

    // Working
    public static void changePassword(String email, String password) throws SQLException, ClassNotFoundException {
        connectDB();
        password = BCrypt.hashpw(password, BCrypt.gensalt());
        statement.execute("UPDATE ACCOUNTS\n" + "SET HASHPASS = '" + password + "'\n" + "WHERE EMAIL = '" + email + "';");
    }

    // Working
    public static void register(String username, String password, String email) throws SQLException, ClassNotFoundException {
        connectDB();
        password = BCrypt.hashpw(password, BCrypt.gensalt());
        statement.execute("INSERT INTO ACCOUNTS\n" + "VALUES ('" + username + "', '" + password + "', '" + email + "');");
    }

    // Working
    public static boolean checkID(int id) throws SQLException, ClassNotFoundException {
        connectDB();
        ResultSet result = statement.executeQuery("SELECT *\n" + "FROM APPOINTMENTS\n" + "WHERE EID = " + id + ";");
        return result.isBeforeFirst();
    }

    // Working
    public static boolean checkHost(int id, String username) throws SQLException, ClassNotFoundException {
        connectDB();
        ResultSet result = statement.executeQuery("SELECT EUSERNAME\n" + "FROM APPOINTMENTS\n" + "WHERE EID = " + id + " AND EUSERNAME = '" + username + "';");
        return result.isBeforeFirst();
    }

    // Working
    public static void addEvent(Event event, int time) throws SQLException, ClassNotFoundException {
        connectDB();
        LocalDateTime date = event.getDate();
        if(event.getID()<1e6&&checkHost(event.getID(), Main.getSession().getUsername())) {
            statement.execute("UPDATE APPOINTMENTS\n" + "SET EUSERNAME = '" + Main.getSession().getUsername() + "', ENAME = '" + event.getName() + "', ELOCATION = '" + event.getLocation() + "', EDURATION = " + event.getDuration() + ", EDAY = " +
                    date.getDayOfMonth() + ", EMONTH = " + date.getMonthValue() + ", EYEAR = " + date.getYear() + ", EHOUR = " + date.getHour() + ", EMINUTE = " +
                    date.getMinute() + ", EPRIORITY = " + event.getPriority() + "\n" + "WHERE EID = " + event.getID() + ";");
        }
        else {
            statement.execute("INSERT INTO APPOINTMENTS(EUSERNAME, ENAME, ELOCATION, EDURATION, EDAY, EMONTH, EYEAR, EHOUR, EMINUTE, EPRIORITY)\n" + "VALUES('" +  Main.getSession().getUsername() + "', '" + event.getName() + "', '" + event.getLocation() + "', " +
                    event.getDuration() + ", " + date.getDayOfMonth() + ", " + date.getMonthValue() + ", " + date.getYear() + ", " + date.getHour() + ", " + date.getMinute() + ", " +
                    event.getPriority() + ");");
            statement.execute("INSERT INTO PARTICIPANTS\n" + "VALUES(LAST_INSERT_ID(), '" + Main.getSession().getEmail() + "', " + time + ");");
        }
    }

    // Working
    public static List<Event> getEvents() throws SQLException, ClassNotFoundException {
        connectDB();
        ResultSet result = statement.executeQuery("SELECT *\n" + "FROM APPOINTMENTS\n" + "WHERE EUSERNAME = '" + Main.getSession().getUsername() + "';");
        List<Event> eventList = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.now();
        while (result.next()) {
            LocalDateTime date = localDateTime.withDayOfMonth(result.getInt(6)).withMonth(result.getInt(7)).withYear(result.getInt(8)).withHour(result.getInt(9)).withMinute(result.getInt(10));
            Event event = new Event(result.getInt(1), result.getString(3), result.getString(4), result.getInt(5), date, result.getInt(11));
            eventList.add(event);
        }
        return eventList;
    }

    // Working
    public static List<Event> getDayEvents() throws SQLException, ClassNotFoundException {
        connectDB();
        LocalDateTime localDateTime = LocalDateTime.now();
        List<Event> eventList = new ArrayList<>();
        ResultSet result = statement.executeQuery("SELECT *\n" + "FROM APPOINTMENTS\n" + "WHERE EDAY = " + localDateTime.getDayOfMonth() + " AND EMONTH = " + localDateTime.getMonthValue() +
                " AND EYEAR = " + localDateTime.getYear() + ";");

        while (result.next()) {
            LocalDateTime date = localDateTime.withDayOfMonth(result.getInt(6)).withMonth(result.getInt(7)).withYear(result.getInt(8)).withHour(result.getInt(9)).withMinute(result.getInt(10));
            Event event = new Event(result.getInt(1), result.getString(3), result.getString(4), result.getInt(5), date, result.getInt(11));
            eventList.add(event);
        }
        return eventList;
    }

    // Working
    public static void deleteEvents(List<Integer> ids) throws SQLException, ClassNotFoundException {
        connectDB();
        for (Integer id : ids)
            statement.execute("DELETE FROM APPOINTMENTS\n" + "WHERE EID = " + id + ";");
    }

    // Working
    public static void addParticipants(int id, List<String> emails) throws SQLException, ClassNotFoundException {
        connectDB();
        for (String email : emails)
            statement.execute("INSERT INTO PARTICIPANTS\n" + "VALUES(" + id + ", '" + email + "', 30);");
    }

    // Working
    public static List<String> getParticipants(int id) throws SQLException, ClassNotFoundException {
        connectDB();
        ResultSet result = statement.executeQuery("SELECT PEMAIL\n" + "FROM PARTICIPANTS\n" + "WHERE PID = " + id + ";");
        List<String> emails = new ArrayList<>();
        while(result.next()) {
            // Add participant's email to list
            emails.add(result.getString(1));
        }
        return emails;
    }

    // Working
    public static int getNotifyTime(int id, String email) throws SQLException, ClassNotFoundException {
        connectDB();
        ResultSet result = statement.executeQuery("SELECT PTIME\n" + "FROM PARTICIPANTS\n" + "WHERE PEMAIL = '" + email + "' AND PID = " + id + ";");
        if(result.isBeforeFirst())
        {
            result.next();
            return result.getInt(1);
        }
        return 0;
    }
}
