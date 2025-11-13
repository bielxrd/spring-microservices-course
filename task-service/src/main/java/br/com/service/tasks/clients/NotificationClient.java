package br.com.service.tasks.clients;

import br.com.service.tasks.dtos.NotificationRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service")
public interface NotificationClient {

    @PostMapping("/notification")
    void sendNotification(@RequestBody NotificationRequestDTO notificationRequest);
}
