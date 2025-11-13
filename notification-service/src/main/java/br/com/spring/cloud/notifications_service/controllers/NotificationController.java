package br.com.spring.cloud.notifications_service.controllers;

import br.com.spring.cloud.notifications_service.dtos.NotificationRequestDTO;
import br.com.spring.cloud.notifications_service.services.NotificationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping()
    public ResponseEntity<Void> sendNotification(@RequestBody NotificationRequestDTO notificationRequestDTO) {
        log.info("Sending notification to email: {} with message: {}", notificationRequestDTO.email(), notificationRequestDTO.message());
        this.notificationService.sendEmail(notificationRequestDTO);
        return ResponseEntity.ok().build();
    }
}
