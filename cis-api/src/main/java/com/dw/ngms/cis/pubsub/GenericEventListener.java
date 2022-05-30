package com.dw.ngms.cis.pubsub;

import com.dw.ngms.cis.enums.UserType;
import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.domains.user.UserNotification;
import com.dw.ngms.cis.persistence.domains.user.UserTransaction;
import com.dw.ngms.cis.persistence.repository.user.UserNotificationRepository;
import com.dw.ngms.cis.persistence.repository.user.UserTransactionRepository;
import com.dw.ngms.cis.service.EmailServiceHandler;
import com.dw.ngms.cis.service.UserService;
import com.dw.ngms.cis.service.dto.UserDto;
import com.dw.ngms.cis.service.dto.dataviewer.DataViewerRequestNotification;
import com.dw.ngms.cis.service.dto.prepackage.PrePackageSubscriptionExecution;
import com.dw.ngms.cis.service.dto.prepackage.PrePackageSubscriptionNotification;
import com.dw.ngms.cis.service.dto.user.UserAssistantDto;
import com.dw.ngms.cis.service.event.GenericNotificationEvent;
import com.dw.ngms.cis.service.mapper.user.UserMapper;
import com.dw.ngms.cis.utilities.Messages;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 20/11/20, Fri
 **/
@Component
@Slf4j
@AllArgsConstructor
public class GenericEventListener {

    private final EmailServiceHandler emailServiceHandler;
    private final UserTransactionRepository userTransactionRepository;
    private final UserService userService;
    private final UserNotificationRepository userNotificationRepository;
    private final Messages messages;
    private final UserMapper userMapper;

    @Async
    @EventListener(condition = "#event.eventType.name()  == 'INTERNAL_USER_REGISTRATION'")
    public void onApplicationEvent(@NotNull final GenericNotificationEvent<User> event) {
        log.debug("received event of type {}", event.getEventType());
        emailServiceHandler.sendInternalRegistrationEmail(event.getContent());
    }

    @Async
    @EventListener(condition = "#event.eventType.name() == 'USER_REGISTRATION'")
    @Transactional(propagation = Propagation.NESTED)
    public void triggerRegistrationEmail(@NotNull final GenericNotificationEvent<User> event) {
        log.debug("received event of type {}", event.getEventType());
        User user = event.getContent();
        UserTransaction userTransaction = new UserTransaction();
        userTransaction.setUserId(user.getUserId());
        userTransaction.setContext(event.getEventType().name());
        userTransaction.setNote(event.getEventType().name());
        userTransaction.setCreatedDate(LocalDateTime.now());
        userTransaction.setStatusItemId(user.getStatusId());
        userTransactionRepository.save(userTransaction);
        if(user.getUserTypeItemId().equals(UserType.EXTERNAL)) {
            emailServiceHandler.sendRegistrationEmail(user);
        }
        if(user.getUserTypeItemId().equals(UserType.INTERNAL)) {
            emailServiceHandler.sendInternalRegistrationEmail(user);
        }

    }

    @Transactional(propagation = Propagation.NESTED)
    @Async
    @EventListener(condition = "#event.eventType.name() == 'USER_REGISTRATION_PENDING'")
    public void triggerProfessionalRegistrationEmail(@NotNull final GenericNotificationEvent<User> event) {
        log.debug("received event of type {}", event.getEventType());

        User user = event.getContent();
        UserTransaction userTransaction = new UserTransaction();
        userTransaction.setUserId(user.getUserId());
        userTransaction.setContext(event.getEventType().name());
        userTransaction.setNote(messages.getMessage("UserService.registration.pending"));
        userTransaction.setCreatedDate(LocalDateTime.now());
        userTransaction.setStatusItemId(user.getStatusId());
        userTransactionRepository.save(userTransaction);

        //Insert a record in Notification table? Who is the approval
        User provinceSystemAdministrator = userService.getProvincialUserByProvinceIdAndRoleId(user.getProvinceId(), 35L);
        UserNotification userNotification = new UserNotification();
        userNotification.setCreateOn(new Date());
        userNotification.setCreatedByUserId(user.getUserId());
        userNotification.setCreatedForUserId(provinceSystemAdministrator.getUserId());
        userNotification.setSubject(user.getUserTypeItemId().name() + " registered");
        userNotification.setDescription(user.getFirstName() + " registered");
        userNotification.setContextId(user.getUserId());
        userNotification.setContextTypeId(5067L);
        userNotification.setActive(1);
        userNotificationRepository.save(userNotification);
        emailServiceHandler.sendRegistrationPendingEmail(user);
        emailServiceHandler.sendRegistrationApprovalEmailToAdminApproval(provinceSystemAdministrator);
    }

    @Async
    @EventListener(condition = "#event.eventType.name() == 'USER_PASSWORD_RESET'")
    public void triggerPasswordChangeEmail(@NotNull final GenericNotificationEvent<User> event) {
        log.debug("received event of type {}", event.getEventType());
        User user = event.getContent();
        emailServiceHandler.sendChangePasswordMail(user);
    }

    @Async
    @EventListener(condition = "#event.eventType.name() == 'USER_CHANGED_EMAIL'")
    public void triggerEmailChange(@NotNull final GenericNotificationEvent<User> event) {
        log.debug("received event of type {}", event.getEventType());
        User user = event.getContent();
        emailServiceHandler.sendEmailUpdateMail(user);
    }

    @Async
    @EventListener(condition = "#event.eventType.name() == 'USER_ACTIVATE'")
    public void triggerActivationEmail(@NotNull final GenericNotificationEvent<User> event) {
        log.debug("received event of type {}", event.getEventType());
        User user = event.getContent();
        emailServiceHandler.sendActivationEmail(user);
    }

    @Async
    @EventListener(condition = "#event.eventType.name() == 'USER_DEACTIVATE'")
    public void triggerDeactivationEmail(@NotNull final GenericNotificationEvent<User> event) {
        log.debug("received event of type {}", event.getEventType());
        User user = event.getContent();
        emailServiceHandler.sendDeactivationEmail(user);
    }

    @Async
    @EventListener(condition = "#event.eventType.name() == 'USER_REJECTED'")
    public void triggerRejectionEmail(@NotNull final GenericNotificationEvent<User> event) {
        log.debug("received event of type {}", event.getEventType());
        User user = event.getContent();
        emailServiceHandler.sendRejectionEmail(user);
    }

    @Async
    @EventListener(condition = "#event.eventType.name() == 'USER_APPROVED'")
    public void triggerApprovalEmail(@NotNull final GenericNotificationEvent<User> event) {
        log.debug("received event of type {}", event.getEventType());
        User user = event.getContent();
        emailServiceHandler.sendApprovalEmail(user);
    }

    @Async
    @EventListener(condition = "#event.eventType.name() == 'USER_PROFILE_UPDATE'")
    public void triggerProfileUpdateEmail(@NotNull final GenericNotificationEvent<User> event) {
        log.debug("received event of type {}", event.getEventType());
        User user = event.getContent();
        emailServiceHandler.sendProfileUpdateEmail(user);
    }

    @Async
    @EventListener(condition = "#event.eventType.name() == 'USER_FORGOT_PASSWORD' ")
    public void triggerForgotPasswordEvent(@NotNull final GenericNotificationEvent<User> event) {
        log.debug("received event of type {}", event.getEventType());
        User user = event.getContent();
        emailServiceHandler.sendPasswordForgotMail(user);
    }

	/*@Async
	@EventListener(condition = "#event.eventType.name() == 'WORKFLOW_NOTIFICATIONS' ")
	public void triggerWorkflowNotificationEvent(@NotNull final GenericNotificationEvent<WorkflowNotification> event) {
		log.debug("received event of type {}", event.getEventType());
		WorkflowNotification workflowNotification = event.getContent();
		emailServiceHandler.sendWorkflowNotificationMail(workflowNotification);
	}*/

    @Async
    @EventListener(condition = "#event.eventType.name() == 'PREPACKAGE_SUBSCRIPTION_NOTIFICATION'")
    public void triggerPrepackageSubscriptionEvent(
            @NotNull final GenericNotificationEvent<PrePackageSubscriptionNotification> event) {
        log.debug("received event of type {}", event.getEventType());
        PrePackageSubscriptionNotification prePackageSubscriptionNotification = event.getContent();
        emailServiceHandler.sendPrepackageNotificationMail(prePackageSubscriptionNotification);
    }


    @Async
    @EventListener(condition = "#event.eventType.name() == 'PREPACKAGE_SUBSCRIPTION_EXECUTION'")
    public void triggerPrepackageSubscriptionExecutionEvent(
            @NotNull final GenericNotificationEvent<PrePackageSubscriptionExecution> event) {
        log.debug("received event of type {}", event.getEventType());
        PrePackageSubscriptionExecution prePackageSubscriptionExecution = event.getContent();
        emailServiceHandler.sendPrepackageExecutionMail(prePackageSubscriptionExecution);
    }

    @Async
    @EventListener(condition = "#event.eventType.name() == 'DATA_VIEWER_REQUEST'")
    public void triggerDataViewerRequestNotificationEvent(
            @NotNull final GenericNotificationEvent<DataViewerRequestNotification> event) {
        log.debug("received event of type {}", event.getEventType());
        final DataViewerRequestNotification dataViewerRequestNotification = event.getContent();
        emailServiceHandler.sendDataViewerRequestNotificationMail(dataViewerRequestNotification);
    }

    @Async
    @EventListener(condition = "#event.eventType.name() == 'DATA_VIEWER_REQUEST_PROCESSED'")
    public void triggerDataViewerRequestProcessedNotificationEvent(
            @NotNull final GenericNotificationEvent<DataViewerRequestNotification> event) {
        log.debug("received event of type {}", event.getEventType());
        final DataViewerRequestNotification dataViewerRequestNotification = event.getContent();
        emailServiceHandler.sendDataViewerRequestNotificationMail(dataViewerRequestNotification);
    }


    @Async
    @EventListener(condition = "#event.eventType.name() == 'ASSISTANT_APPROVED_PLS'")
    public void triggerAssistantApproval(@NotNull final GenericNotificationEvent<UserAssistantDto> event) {
        log.debug("received event of type {}", event.getEventType());

        UserAssistantDto userAssistantDto = event.getContent();
        UserDto assistantUserDto = userService.getUserByUserId(userAssistantDto.getAssistantId());
        userTransaction(assistantUserDto, event.getEventType().name(), userAssistantDto.getComment());
        //Insert a record in Notification table whom notifies
        UserDto userNotified = userService.getUserByUserId(userAssistantDto.getUserId());
        userNotification(assistantUserDto, userNotified, 426L
                , assistantUserDto.getFirstName() + " approved by " + userNotified.getFirstName()
                , "approved by land surveyor", 1, userAssistantDto.getId());
    }

    @Async
    @EventListener(condition = "#event.eventType.name() == 'ASSISTANT_UNLINKED_PLS'")
    public void triggerAssistantUnlink(@NotNull final GenericNotificationEvent<UserAssistantDto> event) {
        log.debug("received event of type {}", event.getEventType());

        UserAssistantDto userAssistantDto = event.getContent();
        UserDto assistantUserDto = userService.getUserByUserId(userAssistantDto.getAssistantId());
        userTransaction(assistantUserDto, event.getEventType().name(), userAssistantDto.getComment());
        //Insert a record in Notification table whom notifies
        UserDto userNotified = userService.getUserByUserId(userAssistantDto.getUserId());
        userNotification(assistantUserDto, userNotified, 426L
                , assistantUserDto.getFirstName() + " Unliked by " + userNotified.getFirstName()
                , "unliked by land surveyor", 1, userAssistantDto.getId());
    }

    @Async
    @EventListener(condition = "#event.eventType.name() == 'ASSISTANT_REJECTED_PLS'")
    public void triggerAssistantReject(@NotNull final GenericNotificationEvent<UserAssistantDto> event) {
        log.debug("received event of type {}", event.getEventType());

        UserAssistantDto userAssistantDto = event.getContent();
        UserDto assistantUserDto = userService.getUserByUserId(userAssistantDto.getAssistantId());
        userTransaction(assistantUserDto, event.getEventType().name(), userAssistantDto.getComment());
        //Insert a record in Notification table whom notifies
        UserDto userNotified = userService.getUserByUserId(userAssistantDto.getUserId());
        userNotification(assistantUserDto, userNotified, 426L
                , assistantUserDto.getFirstName() + " rejected by " + userNotified.getFirstName()
                , "rejected by land surveyor", 1, userAssistantDto.getId());
    }

    private void userNotification(UserDto user, UserDto userNotified, Long contextTypeId, String subject, String description, Integer isActive, Long contextID) {
        UserNotification userNotification = new UserNotification();
        userNotification.setCreateOn(new Date());
        userNotification.setCreatedByUserId(user.getUserId());
        userNotification.setCreatedForUserId(userNotified.getUserId());
        userNotification.setSubject(subject);
        userNotification.setDescription(description);
        userNotification.setContextId(user.getUserId());
        userNotification.setContextTypeId(contextTypeId);
        userNotification.setActive(isActive);
        userNotificationRepository.save(userNotification);
    }

    private void userTransaction(UserDto user, String context, String note) {
        UserTransaction userTransaction = new UserTransaction();
        userTransaction.setUserId(user.getUserId());
        userTransaction.setContext(context);
        userTransaction.setNote(note);
        userTransaction.setCreatedDate(LocalDateTime.now());
        userTransaction.setStatusItemId(user.getStatusId());
        userTransactionRepository.save(userTransaction);
    }

}
