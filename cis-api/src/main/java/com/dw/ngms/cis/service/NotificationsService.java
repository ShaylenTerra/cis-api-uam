package com.dw.ngms.cis.service;

import com.dw.ngms.cis.persistence.domains.Notifications;
import com.dw.ngms.cis.persistence.domains.user.UserNotification;
import com.dw.ngms.cis.persistence.repository.NotificationsRepository;
import com.dw.ngms.cis.persistence.repository.user.UserNotificationRepository;
import com.dw.ngms.cis.service.dto.user.UserNotificationDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 29/12/20, Tue
 **/
@Service
@Slf4j
@AllArgsConstructor
public class NotificationsService {

	private final NotificationsRepository notificationsRepository;
private final UserNotificationRepository userNotificationRepository;
	/**
	 * @param userTypeItemId userTypeItemId
	 * @param pageable       {@link Pageable}
	 * @return Page<Notifications> {@link Notifications}
	 */
	public Page<Notifications> listTopNotificationsByUserTypeItemId(Long userTypeItemId, Pageable pageable) {
		return notificationsRepository.findByNotificationUserTypesItemId(userTypeItemId, pageable);
	}

	/**
	 * @param userNotificationDto userTypeItemId

	 */
	public void addUserNotification(UserNotificationDto userNotificationDto) {
		UserNotification userNotification = new UserNotification(null, new Date(), userNotificationDto.getLoggedInUserId(), userNotificationDto.getNotifyUserId(),
				userNotificationDto.getSubject(), userNotificationDto.getDescription(), userNotificationDto.getContextId(), userNotificationDto.getContextTypeId(), 1);
		userNotificationRepository.save(userNotification);
	}
}
