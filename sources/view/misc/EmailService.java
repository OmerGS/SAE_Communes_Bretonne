package view.misc;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * The EmailService class provides methods to send emails using SMTP protocol.
 * It loads mail server configuration from a properties file and sends emails
 * using JavaMail API.
 * 
 * This class includes one main method:
 * <ul>
 *   <li>{@link #sendEmail(String, String, String)}: Sends an email with specified recipient, subject, and body.</li>
 * </ul>
 * 
 * @author O.Gunes
 */
public class EmailService {
    
    /**
     * Sends an email to the specified recipient with the given subject and body.
     * 
     * @param to The email address of the recipient.
     * @param subject The subject of the email.
     * @param body The body or content of the email.
     * @throws MessagingException If there is an error while sending the email.
     */
    public void sendEmail(String to, String subject, String body) throws MessagingException {
        // Setup mail server configuration
        String host = "smtp-mail.outlook.com";
        String port = "587";

        // Load mail server connection properties from a properties file
        Properties mailId = new Properties();
        try (FileInputStream fis = new FileInputStream("../properties/mail.properties")) {
            mailId.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Retrieve username and password for authentication
        String username = mailId.getProperty("mail.username");
        String password = mailId.getProperty("mail.password"); 

        // Configure properties for SMTP session    
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Create a session with authentication
        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

        try {
            // Create a new email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            // Send the email
            Transport.send(message);

            System.out.println("Message envoyé avec succès");

        } catch (MessagingException e) {
            throw new MessagingException("Erreur lors de l'envoi de l'e-mail", e);
        }
    }
}
