import org.mindrot.jbcrypt.BCrypt;
import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

class SQLConnection {
    private static Statement statement;

    SQLConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Java","root","eKak5CxcBsr");
            statement = connection.createStatement();
        }
        catch (Exception E) {
            System.out.println(E);
        }
    }

    public static void sendEmail(String receiver, String option) {
        String sender = "howard.su.08012001@gmail.com";
        String host = "smtp.gmail.com";

        Properties property = System.getProperties();
        property.put("mail.smtp.host", host);
        property.put("mail.smtp.port", "465");
        property.put("mail.smtp.ssl.enable", "true");
        property.put("mail.smtp.auth", "true");

        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, "HowardSu08012001");
            }
        };

        Session session = Session.getInstance(property, authenticator);
        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.addRecipients(Message.RecipientType.TO, receiver);

            int verification = ThreadLocalRandom.current().nextInt(100000, 1000000);
            message.setSubject("Verification code!!");
            message.setText("Your code is: " + verification);

            Transport.send(message);
            System.out.println("Sent!!");
        }
        catch (Exception E) {
            System.out.println(E);
        }
    }

    public static void addUserDB(User info) {
        try {
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

    public static void loginUser(User info) {
        try {
            ResultSet result = statement.executeQuery("SELECT HASHPASS\n" +
                    "FROM ACCOUNTS\n" + "WHERE USERNAME = '" + info.username + "';");
            result.next();
            if(BCrypt.checkpw(info.password, result.getString(1)))
                System.out.println("Welcome");
            else
                System.out.println("Wrong username/password");
        }
        catch (Exception E) {
            System.out.println(E);
        }
    }
}