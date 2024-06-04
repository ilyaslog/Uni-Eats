package best.unieats.sign_up;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import org.springframework.stereotype.Component;

@Service
public class EmailVerificationService {

    @Autowired
    private EmailSenderService senderService;
    

    public void sendVerificationCode(String email, String verificationCode) {
        try {
            senderService.sendSimpleEmail(email, "Your verification code is: " + verificationCode, "Verification Email");
            System.out.println("Verification email sent successfully!");
        } catch (MessagingException e) {
            System.err.println("Failed to send verification email: " + e.getMessage());
            // You can handle the exception here, such as logging or informing the user
        }
    }
}
