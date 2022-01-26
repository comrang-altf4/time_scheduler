package backend;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class Database {
    private static Statement statement;
    private final static String JDBC_URL = "jdbc:mysql://localhost:3306/Java";
    private final static String JDBC_User = "root";
    private final static String JDBC_Password = "eKak5CxcBsr";

    // Connect to database
    private static void connectDB() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_User, JDBC_Password);
        statement = connection.createStatement();
    }

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

    public static String getEmail(String username) throws SQLException, ClassNotFoundException {
        connectDB();
        ResultSet result = statement.executeQuery("SELECT EMAIL\n" + "FROM ACCOUNTS\n" + "WHERE USERNAME = '" + username + "';");
        result.next();
        return result.getString(1);
    }

    public static Boolean checkEmail(String email) throws SQLException, ClassNotFoundException {
        connectDB();
        ResultSet result = statement.executeQuery("SELECT *\n" + "FROM ACCOUNTS\n" + "WHERE EMAIL = '" + email + "';");
        return result.isBeforeFirst();
    }

    public static Boolean checkUsername(String username) throws SQLException, ClassNotFoundException {
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
}
