package backend;

import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
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

    public static String sendVerificationCode(String receiver) throws MessagingException {
        Message message = sendEmail(receiver);
        String verification = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
        message.setSubject("Verification code!!");
        message.setText("Your code is: " + verification);
        Transport.send(message);
        return verification;
    }
}