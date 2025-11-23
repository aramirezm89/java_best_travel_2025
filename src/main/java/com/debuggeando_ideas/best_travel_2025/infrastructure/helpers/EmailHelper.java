package com.debuggeando_ideas.best_travel_2025.infrastructure.helpers;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailHelper {

    private final JavaMailSender mailSender;
    @Value("${email.sender}")
    private String sender;
    public EmailHelper(JavaMailSender mailSender){
            this.mailSender = mailSender;
        }
    public void sendEmail(String to, String subject, String product){
        MimeMessage message = mailSender.createMimeMessage();
        String htmlContent = "";

        try {
            message.setFrom(new InternetAddress(sender));
            message.setRecipients(MimeMessage.RecipientType.TO, to);
            message.setContent(htmlContent, "text/html; charset=utf-8");
            mailSender.send(message);
        } catch (MessagingException e) {
           log.error("Error sending email", e);
        }
    }

}
