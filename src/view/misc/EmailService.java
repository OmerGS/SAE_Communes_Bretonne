package view.misc;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
* EmailService is a class which allow to send mail. 
* @author O.Gunes
*/
public class EmailService {
    
    /**
    * Send email to an email adress.
    * @param to The receiver of mail.
    * @param subject The subject of mail.
    * @param body The message of mail.
    * @throws MessagingException
    */
    public void sendEmail(String to, String subject, String body) throws MessagingException {
        //We setup the host and port of our mail provider
        String host = "smtp-mail.outlook.com";
        String port = "587";

        //We load connection properties from mail.
        Properties mailId = new Properties();
        try (FileInputStream fis = new FileInputStream("../properties/mail.properties")) {
            mailId.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        //We connect at the mail server with properties.
        String username = mailId.getProperty("mail.username");
        String password = mailId.getProperty("mail.password"); 

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // We get a instance of SMTP session.
        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

        try {
            //Then we create the mail.
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            //Finally we send the mail.
            Transport.send(message);

            System.out.println("Message envoyé avec succès");

        } catch (MessagingException e) {
            throw new MessagingException("Erreur lors de l'envoi de l'e-mail", e);
        }
    }
}
