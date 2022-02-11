/**
 * This class used for sending email in general.
 * @author Huy To Quang
 */

package backend;

import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
    /**
     * This function connects to smtp.gmail.com host server.
     * Creates session and mime message for sending email.
     * @param receiver
     * @return
     * @throws MessagingException
     */
    private static Message sendEmail(String receiver) throws MessagingException {
        // System's email
        String sender = "no.reply.timescheduler@gmail.com";
        String host = "smtp.gmail.com";

        // Establish connection
        Properties property = System.getProperties();
        property.put("mail.smtp.host", host);
        property.put("mail.smtp.port", "465");
        property.put("mail.smtp.ssl.enable", "true");
        property.put("mail.smtp.auth", "true");

        // Login to system's email
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, "JavaProject2022");
            }
        };

        // Create message
        Session session = Session.getInstance(property, authenticator);
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sender));
        message.addRecipients(Message.RecipientType.TO, receiver);
        return message;
    }

    /**
     * This function sends verification code to user whenever they change password or register new account.
     * The content of the mail is an Integer ranging from 100001 to 999999
     * @param receiver
     * @return
     * @throws MessagingException
     */
    public static String sendVerificationCode(String receiver) throws MessagingException {
        Message message = sendEmail(receiver);
        String verification = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
        message.setSubject("Verification code!!");
        message.setText("Your code is: " + verification);
        Transport.send(message);
        return verification;
    }

    /**
     * This function sends reminder about an event to user.
     * @param receiver
     * @param event
     * @param time
     * @throws MessagingException
     */
    public static void sendReminder(String receiver, Event event, int time) throws MessagingException {
        Message message = sendEmail(receiver);
        message.setSubject("Event reminder!!!");
        message.setText(event.getName() + " at " + event.getLocation() + " is happening in " + time + " minutes!!!");
        Transport.send(message);
    }
}