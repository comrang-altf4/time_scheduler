import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.util.Pair;

public class Database {
    private static Statement statement;
    private final static String JDBC_URL = "jdbc:oracle:thin:@db1.fb2.frankfurt-university.de:1521:info01";
    private final static String JDBC_USER = "S1_student2_18";
    private final static String JDBC_PASSWORD = "Ora2221";
    private final static Scanner scanner = new Scanner(System.in);

    Database() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        statement = connection.createStatement();
    }

    public static List<Pair<String, String>> getUsers() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT USERNAME, EMAIL\n" + "FROM ACCOUNTS");
        List<Pair<String, String>> listOfUsers = new ArrayList<>();
        while (resultSet.next()) {
            Pair<String, String> user = new Pair<>(resultSet.getString(1), resultSet.getString(2));
            listOfUsers.add(user);
        }
        return listOfUsers;
    }

    public static void deleteUser(String username) throws SQLException {
        statement.execute("DELETE FROM ACCOUNTS\n" + "WHERE USERNAME = '" + username + "'");

        statement.execute("COMMIT");
        System.out.println("Deleted!!!");
    }

    public static void updateUser(String username, String email) throws SQLException {
        statement.execute(
                "UPDATE ACCOUNTS\n" + "SET EMAIL = '" + email + "'\n" + "WHERE USERNAME = '" + username + "'");
        statement.execute("COMMIT");
        System.out.println("Updated!!!");
    }
}
