package backend;

import org.mindrot.jbcrypt.BCrypt;
import project.Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Database {
    private static Statement statement;
    private final static String JDBC_URL = "jdbc:mysql://localhost:3306/Java";
    private final static String JDBC_User = "root";
    private final static String JDBC_Password = "eKak5CxcBsr";

    // Connect to database
    private static void connectDB() throws ClassNotFoundException, SQLException {
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_User, JDBC_Password);
//        statement = connection.createStatement();
    }

    public static boolean login(String username, String password) throws SQLException, ClassNotFoundException {
//        connectDB();
//
//        // Result from query
//        ResultSet result = statement.executeQuery("SELECT HASHPASS\n" + "FROM ACCOUNTS\n" + "WHERE USERNAME = '" + username + "';");
//
//        // Check if query returns null
//        if (result.isBeforeFirst()) {
//            result.next();
//            // Check password
//            return BCrypt.checkpw(password, result.getString(1));
//        }
        return false;
    }

    public static String getEmail(String username) throws SQLException, ClassNotFoundException {
        connectDB();
        ResultSet result = statement.executeQuery("SELECT EMAIL\n" + "FROM ACCOUNTS\n" + "WHERE USERNAME = '" + username + "';");
        result.next();
        return result.getString(1);
    }

    public static boolean checkEmail(String email) throws SQLException, ClassNotFoundException {
        connectDB();
        ResultSet result = statement.executeQuery("SELECT *\n" + "FROM ACCOUNTS\n" + "WHERE EMAIL = '" + email + "';");
        return result.isBeforeFirst();
    }

    public static boolean checkUsername(String username) throws SQLException, ClassNotFoundException {
        connectDB();
        ResultSet result = statement.executeQuery("SELECT *\n" + "FROM ACCOUNTS\n" + "WHERE USERNAME = '" + username + "';");
        return result.isBeforeFirst();
    }

    public static void changePassword(String email, String password) throws SQLException, ClassNotFoundException {
        connectDB();
        password = BCrypt.hashpw(password, BCrypt.gensalt());
        statement.execute("UPDATE ACCOUNTS\n" + "SET HASHPASS = '" + password + "'\n" + "WHERE EMAIL = '" + email + "';");
    }

    public static void register(String username, String password, String email) throws SQLException, ClassNotFoundException {
        connectDB();
        password = BCrypt.hashpw(password, BCrypt.gensalt());
        statement.execute("INSERT INTO ACCOUNTS\n" + "VALUES ('" + username + "', '" + password + "', '" + email + "');");
    }

    public static boolean checkID(int id) throws SQLException, ClassNotFoundException {
        connectDB();
        ResultSet result = statement.executeQuery("SELECT *\n" + "FROM APPOINTMENTS\n" + "WHERE EID = " + id + ";");
        return result.isBeforeFirst();
    }

    public static void addEvent(Event event) throws SQLException, ClassNotFoundException {
        connectDB();
        Calendar calendar = event.getDate();
        if(event.getID()<1e6) {
            statement.execute("UPDATE APPOINTMENTS\n" + "SET EUSERNAME = '" + Main.getSession().getUsername() + "', ENAME = '" + event.getName() + "', ELOCATION = '" + event.getLocation() + "', EDURATION = " + event.getDuration() + ", EDAY = " +
                    calendar.get(Calendar.DAY_OF_MONTH) + ", EMONTH = " + calendar.get(Calendar.MONTH) + ", EYEAR = " + calendar.get(Calendar.YEAR) + ", EHOUR = " + calendar.get(Calendar.HOUR) + ", EMINUTE = " +
                    calendar.get(Calendar.MINUTE) + ", EPRIORITY = " + event.getPriority() + "\n" + "WHERE EID = " + event.getID() + ";");
        }
        else
            statement.execute("INSERT INTO APPOINTMENTS(EUSERNAME, ENAME, ELOCATION, EDURATION, EDAY, EMONTH, EYEAR, EHOUR, EMINUTE, EPRIORITY)\n" + "VALUES('" +  Main.getSession().getUsername() + "', '" + event.getName() + "', '" + event.getLocation() + "', " +
                    event.getDuration() + ", " + calendar.get(Calendar.DAY_OF_MONTH) + ", " + calendar.get(Calendar.MONTH) + ", " + calendar.get(Calendar.YEAR) + ", " + calendar.get(Calendar.HOUR) + ", " + calendar.get(Calendar.MINUTE) + ", " +
                    event.getPriority() + ");");
    }

    public static List<Event> getEvents() throws SQLException, ClassNotFoundException {
        connectDB();
        ResultSet result = statement.executeQuery("SELECT *\n" + "FROM APPOINTMENTS\n" + "WHERE EUSERNAME = '" + Main.getSession().getUsername() + "';");
        List<Event> events = new ArrayList<Event>();
        while (result.next()) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, result.getInt(6));
            calendar.set(Calendar.MONTH, result.getInt(7));
            calendar.set(Calendar.YEAR, result.getInt(8));
            calendar.set(Calendar.HOUR, result.getInt(9));
            calendar.set(Calendar.MINUTE, result.getInt(10));
            Event event = new Event(result.getInt(1), result.getString(3), result.getString(4), result.getInt(5), calendar, result.getInt(11));
            events.add(event);
        }
        return events;
    }
}
