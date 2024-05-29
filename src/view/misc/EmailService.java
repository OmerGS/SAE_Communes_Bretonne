package view.misc;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {
    
    public void sendEmail(String to, String subject, String body) throws MessagingException {
        String host = "smtp-mail.outlook.com";
        String port = "587";

        Properties mailId = new Properties();
        try (FileInputStream fis = new FileInputStream("../properties/mail.properties")) {
            mailId.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Informations de connexion à la base de données
        String username = mailId.getProperty("mail.username");
        String password = mailId.getProperty("mail.password"); 

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Obtenez une session SMTP
        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

        try {
            // Créez un message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            // Envoyer le message
            Transport.send(message);

            System.out.println("Message envoyé avec succès");

        } catch (MessagingException e) {
            throw new MessagingException("Erreur lors de l'envoi de l'e-mail", e);
        }
    }
}
