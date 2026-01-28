
package com.trainSync.notification.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.antlr.v4.runtime.misc.TestRig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.niamedtech.expo.exposerversdk.ExpoPushNotificationClient;
import com.niamedtech.expo.exposerversdk.request.PushNotification;
import com.niamedtech.expo.exposerversdk.response.TicketResponse;
import com.trainSync.config.exception.GlobalExceptionHandler;
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
	
	private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	public PushNotificationService(UserDetailsRepository userDetailsRepository, PushNotificationTokenRepository pushNotificationTokenRepository) {
		this.userDetailsRepository = userDetailsRepository;
		this.pushNotificationTokenRepository = pushNotificationTokenRepository;
	}
	
	
	/**
	 * common method to send push notification
	 */
	public void sendPushNotification(List<String> tokens, String title, String message, Map<String, Object> data) {
		
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
		
		pushNotification.setData(data);

		List<PushNotification> notifications = new ArrayList<>();
		notifications.add(pushNotification);
		
		try {
			 client.sendPushNotifications(notifications);
		} catch (IOException e) {
			log.error("SENDING PUSH NOTIFICATION FAILED");
		}

	}
	
	/**
	 * 
	 * @param userId
	 * @param pushNotificationToken
	 * @param platform
	 */
	public void savePushNotificationToken(UUID userId, String pushNotificationToken, String platform) {
		
		//no need to save if token already in db
		if(pushNotificationTokenRepository.existsByUser_IdAndToken(userId, pushNotificationToken)) {
			return;
		}
		
		UserDetails userDetails = userDetailsRepository.findById(userId).get();
		
		PushNotificationToken token = PushNotificationToken.builder()
				.user(userDetails)
				.token(pushNotificationToken)
				.platform(platform)
				.status("ACTIVE")
				.build();
		pushNotificationTokenRepository.save(token);
		
	}
	
	
	public static void main(String[] args) {
	
			List<String> tokens = new ArrayList<>();
			tokens.add("ExponentPushToken[JBYOMYNvpKH7ZGHWsSBQBK]");
			PushNotificationService service = new PushNotificationService(null, null);
			service.sendPushNotification(tokens, "Test Title", "This is a test message from TrainSync backend.", null);
	
	}

	/**
	 * 
	 * @param userId
	 * @param pushNotificationToken
	 */
	public void removePushNotifToken(UUID userId, String pushNotificationToken) {
		PushNotificationToken token = pushNotificationTokenRepository.findByUser_IdAndToken(userId, pushNotificationToken);
		if(token==null) {
			return;
		}
		pushNotificationTokenRepository.deleteById(token.getId());
	}


	

}
