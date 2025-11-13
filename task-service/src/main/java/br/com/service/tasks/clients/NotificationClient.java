package br.com.service.tasks.clients;

import br.com.service.tasks.dtos.NotificationRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service")
public interface NotificationClient {

    @PostMapping("/notifications")
    ResponseEntity<Void> sendNotification(@RequestBody NotificationRequestDTO notificationRequest);
}
