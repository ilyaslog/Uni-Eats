// EmailSenderService.java
package best.unieats.sign_up;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import org.springframework.stereotype.Component;

@Service
public class EmailSenderService {

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private String port;

 
    public void sendSimpleEmail(String email, String message, String subject) throws MessagingException {
        // Configure properties for connecting to the SMTP server
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.connectiontimeout", "5000");
        props.put("mail.smtp.timeout", "5000");
        props.put("mail.smtp.writetimeout", "5000");

        // Create a session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a MimeMessage object
            Message mimeMessage = new MimeMessage(session);

            // Set the sender's email address
            mimeMessage.setFrom(new InternetAddress(username));

            // Set the recipient's email address
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

            // Set the email subject
            mimeMessage.setSubject(subject);

            // Set the email content
            mimeMessage.setText(message);

            // Send the email
            Transport.send(mimeMessage);

            // Print confirmation message
            System.out.println("Email sent successfully to: " + email);
        } catch (MessagingException e) {
            // Print error message if sending fails
            System.err.println("Failed to send email to " + email + ": " + e.getMessage());
            throw e;
        }
    }
}
