package com.mickey.taskdock.controller;

import com.mickey.taskdock.service.NotificationClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationTestController {
	private final NotificationClient notificationClient;
	
	@GetMapping("/api/test-notification")
	public String testNotification() {
		return notificationClient.sendNotification();
	}
	
	
}
