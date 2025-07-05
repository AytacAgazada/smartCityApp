package com.example.smartcityapp.client;

import com.example.smartcityapp.model.dto.clientDto.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service", url = "${smartcity-notification.url}")
public interface NotificationClient {

    @PostMapping("/notification/send")
    void sendNotification(@RequestBody NotificationRequest request);
}