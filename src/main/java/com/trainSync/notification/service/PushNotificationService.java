
package com.trainSync.notification.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.niamedtech.expo.exposerversdk.ExpoPushNotificationClient;
import com.niamedtech.expo.exposerversdk.request.PushNotification;
import com.niamedtech.expo.exposerversdk.response.TicketResponse;
import com.trainSync.notification.model.PushNotificationToken;
import com.trainSync.notification.repository.PushNotificationTokenRepository;
import com.trainSync.user.model.UserDetails;
import com.trainSync.user.repository.UserDetailsRepository;

/**
 * Author: Sajal Gupta
 * Created on: Jan 20, 2026 10:27:37 AM
 */
@Service
public class PushNotificationService {
	
	private final UserDetailsRepository userDetailsRepository;
	
	private final PushNotificationTokenRepository pushNotificationTokenRepository;
	
	public PushNotificationService(UserDetailsRepository userDetailsRepository, PushNotificationTokenRepository pushNotificationTokenRepository) {
		this.userDetailsRepository = userDetailsRepository;
		this.pushNotificationTokenRepository = pushNotificationTokenRepository;
	}
	
	
	/**
	 * common method to send push notification
	 */
	public void sendPushNotification(List<String> tokens, String title, String message) throws IOException {
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		ExpoPushNotificationClient client = ExpoPushNotificationClient
				.builder()
				.setHttpClient(httpClient)
				//.setAccessToken("TOKEN")
				.build();
		
		PushNotification pushNotification = new PushNotification();
		pushNotification.setTo(tokens);
		pushNotification.setTitle(title);
		pushNotification.setBody(message);
		

		List<PushNotification> notifications = new ArrayList<>();
		notifications.add(pushNotification);
		
		List<TicketResponse.Ticket> response =  client.sendPushNotifications(notifications);

		for (TicketResponse.Ticket ticket : response) {
				System.out.println(ticket.getId());
				System.out.println(ticket.getStatus());
		}

	}
	
	/**
	 * 
	 * @param userId
	 * @param pushNotificationToken
	 * @param platform
	 */
	public void savePushNotificationToken(UUID userId, String pushNotificationToken, String platform) {
		UserDetails userDetails = userDetailsRepository.findById(userId).get();
		PushNotificationToken token = PushNotificationToken.builder()
				.user(userDetails)
				.token(pushNotificationToken)
				.platform(platform)
				.build();
		pushNotificationTokenRepository.save(token);
		
	}

}
