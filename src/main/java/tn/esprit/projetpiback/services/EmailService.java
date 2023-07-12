package tn.esprit.projetpiback.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender emailSender;



    public void sendResetPasswordEmail(String to, String resetPasswordLink) {
        String subject = "Password Reset";
        String body = "Hello,\n\nYou have requested to reset your password. Please click the following link to reset your password:\n\n"
                + resetPasswordLink + "\n\nIf you did not request this change, please ignore this email.\n\nThank you.";

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("bestcamp195@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);

        this.emailSender.send(simpleMailMessage);
        System.out.println(to);


    }
}

