import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

class SQLConnection {
    private static Statement statement;
    private static Connection connection;
    SQLConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Java","root","eKak5CxcBsr");
            statement = connection.createStatement();
        }
        catch (Exception E) {
            System.out.println(E);
        }
    }

    public static void addUserDB(User info) {
        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT USERNAME\n" +
                    "FROM ACCOUNTS\n" + "WHERE USERNAME = '" + info.username +
                    "' AND EMAIL = '" + info.email + "' ;");
            if (result.next())
                System.out.println("Username/Email is already existed");
            else {
                info.password = BCrypt.hashpw(info.password, BCrypt.gensalt());
                statement.execute("INSERT INTO ACCOUNTS\n" +
                        "VALUES ('" + info.username + "', '"+ info.password + "', '" + info.email + "');");
                System.out.println("New user successfully added");
            }
        }
        catch (Exception E) {
            System.out.println(E);
        }
    }

}

class User {
    protected String username;
    protected String password;
    protected String email;
    User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}

class Main {
    public static void main(String[] args) {
        SQLConnection begin = new SQLConnection();
        User user1 = new User("HowardSu1", "123456789", "howardsu@gmail.com");
        begin.addUserDB(user1);
    }
}