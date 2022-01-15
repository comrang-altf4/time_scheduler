package com.example.login_v2;

import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class Database {
    private static Statement statement;
    private final static String JDBC_URL = "jdbc:mysql://localhost:3306/Java";
    private final static String JDBC_User = "root";
    private final static String JDBC_Password = "eKak5CxcBsr";

    private static void connectDB() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_User, JDBC_Password);
        statement = connection.createStatement();
    }

    protected static void addUserDB(String username, String email, String password) throws SQLException, ClassNotFoundException {
        connectDB();
        ResultSet result = statement.executeQuery("SELECT USERNAME\n" +
                "FROM ACCOUNTS\n" + "WHERE USERNAME = '" + username + "' OR EMAIL = '" + email + "';");
        if(result.next())
            System.out.println("Username/Email is already existed");
        else {
            String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            statement.execute("INSERT INTO ACCOUNTS\n" + "VALUES ('" + username + "', '" +
                    hashPassword + "', '" + email + "');");
            System.out.println("New user successfully added");
        }
    }

    protected static void loginUserDB(String username, String password) throws SQLException, ClassNotFoundException {
        connectDB();
        ResultSet result = statement.executeQuery("SELECT HASHPASS\n" +
                "FROM ACCOUNTS\n" + "WHERE USERNAME = '" + username + "';");
        if (!result.isBeforeFirst())
            System.out.println("Wrong username/password!!");
        else {
            result.next();
            if (BCrypt.checkpw(password, result.getString(1)))
                System.out.println("Welcome!!!");
            else
                System.out.println("Wrong username/password!!");
        }
    }

    protected static void changePasswordDB(String email, String newPassword) throws SQLException, ClassNotFoundException {
        connectDB();
        String password = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        statement.execute("UPDATE ACCOUNTS\n" + "SET HASHPASS = '" + password + "'\nWHERE EMAIL= '" + email + "';");
        System.out.println("Successfully change password!!");
    }

    protected static Boolean checkDB(String email) throws SQLException, ClassNotFoundException {
        connectDB();
        ResultSet result = statement.executeQuery("SELECT *\n" + "FROM ACCOUNTS\n" + "WHERE EMAIL = '" + email + "';");
        return result.isBeforeFirst();
    }
}
