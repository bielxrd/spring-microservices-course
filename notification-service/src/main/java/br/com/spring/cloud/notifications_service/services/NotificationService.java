package br.com.spring.cloud.notifications_service.services;

import br.com.spring.cloud.notifications_service.dtos.NotificationRequestDTO;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(NotificationRequestDTO notificationRequestDTO) {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setFrom("noreply@email.com");
        emailMessage.setTo(notificationRequestDTO.email());
        emailMessage.setSubject("Notification service");
        emailMessage.setText(notificationRequestDTO.message());
        mailSender.send(emailMessage);
    }
}
