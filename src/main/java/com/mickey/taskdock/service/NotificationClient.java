package com.mickey.taskdock.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class NotificationClient {
	private final RestClient restClient;
	
	public NotificationClient(@Value("${NOTIFICATION_URL:http://notification:8081}") String url) {
		this.restClient = RestClient.builder()
				.baseUrl(url)
				.build();
	}
	
	public String sendNotification() {
		return restClient.get()
				.uri("/")
				.retrieve()
				.body(String.class);
	}
}
