package com.dw.ngms.cis.service;

import com.dw.ngms.cis.cisemailer.service.EmailService;
import com.dw.ngms.cis.cisemailer.service.Mail;
import com.dw.ngms.cis.configuration.AppPropertiesConfig;
import com.dw.ngms.cis.persistence.domains.Template;
import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.repository.TemplateRepository;
import com.dw.ngms.cis.service.dto.EmailDto;
import com.dw.ngms.cis.service.dto.dataviewer.DataViewerRequestNotification;
import com.dw.ngms.cis.service.dto.prepackage.PrePackageSubscriptionExecution;
import com.dw.ngms.cis.service.dto.prepackage.PrePackageSubscriptionNotification;
import com.dw.ngms.cis.service.dto.workflow.DispatchEmailNotificationDto;
import com.dw.ngms.cis.utilities.TemplateUtils;
import com.dw.ngms.cis.web.vm.EmailVm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author : prateekgoel
 * @since : 19/11/20, Thu
 * <p>
 * Service for sending emails.
 * <p>
 * We use the {@link org.springframework.scheduling.annotation.Async}
 * annotation to send emails asynchronously.
 **/
@Service
@Slf4j
public class EmailServiceHandler {

    private final EmailService emailService;

    private final TemplateRepository templateRepository;

    private final AppPropertiesConfig appPropertiesConfig;

    private final TemplateUtils templateUtils;

    public EmailServiceHandler(EmailService emailService,
                               TemplateRepository templateRepository,
                               AppPropertiesConfig appPropertiesConfig,
                               TemplateUtils templateUtils) {
        this.emailService = emailService;
        this.templateRepository = templateRepository;
        this.appPropertiesConfig = appPropertiesConfig;
        this.templateUtils = templateUtils;
    }


    public void sendEmail(final Mail mail) {

        if (StringUtils.isBlank(mail.getTo())) {
            log.debug("Email address doesn't exist for user ");
            return;
        }
        emailService.sendMail(mail);
    }


    private void sendEmailFromTemplate(final String to,
                                       final Long templateId,
                                       final Boolean isHtml,
                                       final Boolean isMultipart,
                                       final Collection<Resource> resources,
                                       final Map<String, String> placeHolders) {

        if (StringUtils.isBlank(to)) {
            log.debug("Email doesn't exist for user ");
            return;
        }


        Template byTemplateName = templateRepository.findByTemplateId(templateId);

        if (null != byTemplateName) {
            String emailTemplate = byTemplateName.getEmailDetails();
            final EmailDto mailFromEmailTemplate = templateUtils.getEmailFromEmailTemplate(emailTemplate);
            if (null != mailFromEmailTemplate && StringUtils.isNotBlank(mailFromEmailTemplate.getBody())) {
                final Mail mailContent = emailService.getMailContent(mailFromEmailTemplate.getBody(), placeHolders);
                mailContent.setTo(to);
                mailContent.setSubject(mailFromEmailTemplate.getSubject());
                if (isMultipart) {
                    mailContent.setMultipart(true);
                    mailContent.setResources(resources);
                }
                mailContent.setHtml(isHtml);
                emailService.sendMail(mailContent);

            } else {

                log.debug(" aborting sending mail no body found for templateId {} ", templateId);

            }
        } else {
            log.debug(" aborting sending mail  template {} Not found !!", templateId);
        }


    }

    private void sendEmailFromTemplateWithCC(final String to,
                                             final List<String> cc,
                                             final Long templateId,
                                             final Boolean isHtml,
                                             final Boolean isMultipart,
                                             final Collection<Resource> resources,
                                             final Map<String, String> placeHolders) {

        if (StringUtils.isBlank(to)) {
            log.debug("Email doesn't exist for user ");
            return;
        }


        Template byTemplateName = templateRepository.findByTemplateId(templateId);

        if (null != byTemplateName) {
            String emailTemplate = byTemplateName.getEmailDetails();
            final EmailDto mailFromEmailTemplate = templateUtils.getEmailFromEmailTemplate(emailTemplate);
            if (null != mailFromEmailTemplate && StringUtils.isNotBlank(mailFromEmailTemplate.getBody())) {
                final Mail mailContent = emailService.getMailContent(mailFromEmailTemplate.getBody(), placeHolders);
                mailContent.setTo(to);
                mailContent.setSubject(mailFromEmailTemplate.getSubject());
                if (isMultipart) {
                    mailContent.setMultipart(true);
                    mailContent.setResources(resources);
                }
                if (CollectionUtils.isNotEmpty(cc)) {
                    String[] strings = cc.toArray(new String[0]);
                    mailContent.setCc(strings);
                }
                mailContent.setHtml(isHtml);
                emailService.sendMail(mailContent);

            } else {

                log.debug(" aborting sending mail no body found for templateId {} ", templateId);

            }
        } else {
            log.debug(" aborting sending mail  template {} Not found !!", templateId);
        }


    }

    public String getMailBodyContent(final String template, final Map<String, String> placeHolders) {
        return emailService.getMailBodyContent(template, placeHolders);
    }

    @Async
    public void sendActivationEmail(User user) {
        Map<String, String> placeHolders = new HashMap<>();
        placeHolders.put("firstname", user.getFirstName());
        placeHolders.put("surname", user.getSurname());
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user.getEmail(), 30L, true, false, null, placeHolders);
    }

    @Async
    public void sendDeactivationEmail(User user) {
        Map<String, String> placeHolders = new HashMap<>();
        placeHolders.put("firstname", user.getFirstName());
        placeHolders.put("surname", user.getSurname());
        log.debug("Sending deactivation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user.getEmail(), 31L, true, false, null, placeHolders);
    }

    @Async
    public void sendRejectionEmail(User user) {
        Map<String, String> placeHolders = new HashMap<>();
        placeHolders.put("firstname", user.getFirstName());
        placeHolders.put("surname", user.getSurname());
        log.debug("Sending rejection email to '{}'", user.getEmail());
        sendEmailFromTemplate(user.getEmail(), 67L, true, false, null, placeHolders);
    }

    @Async
    public void sendApprovalEmail(User user) {
        Map<String, String> placeHolders = new HashMap<>();
        placeHolders.put("firstname", user.getFirstName());
        placeHolders.put("surname", user.getSurname());
        placeHolders.put("password", user.getTempPassword());
        log.debug("Sending approval email to '{}'", user.getEmail());
        sendEmailFromTemplate(user.getEmail(), 68L, true, false, null, placeHolders);
    }

    @Async
    public void sendProfileUpdateEmail(User user) {
        Map<String, String> placeHolders = new HashMap<>();
        placeHolders.put("firstname", user.getFirstName());
        placeHolders.put("surname", user.getSurname());
        log.debug("Sending approval email to '{}'", user.getEmail());
        sendEmailFromTemplate(user.getEmail(), 69L, true, false, null, placeHolders);
    }

    @Async
    public void sendRegistrationEmail(User user) {
        Map<String, String> placeHolders = new HashMap<>();
        placeHolders.put("firstname", user.getFirstName());
        placeHolders.put("surname", user.getSurname());
        placeHolders.put("password", user.getTempPassword());
        log.debug("Sending registration email to '{}'", user);
        sendEmailFromTemplate(user.getEmail(), 27L, true, false, null, placeHolders);
    }

    @Async
    public void sendRegistrationPendingEmail(User user) {
        Map<String, String> placeHolders = new HashMap<>();
        placeHolders.put("firstname", user.getFirstName());
        placeHolders.put("surname", user.getSurname());
        log.debug("Sending registration email to '{}'", user);
        sendEmailFromTemplate(user.getEmail(), 62L, true, false, null, placeHolders);
    }

    @Async
    public void sendRegistrationApprovalEmailToAdminApproval(User user) {
        Map<String, String> placeHolders = new HashMap<>();
        placeHolders.put("firstname", user.getFirstName());
        placeHolders.put("surname", user.getSurname());
        log.debug("Sending registration email to '{}'", user);
        sendEmailFromTemplate(user.getEmail(), 63L, true, false, null, placeHolders);
    }

    @Async
    public void sendInternalRegistrationEmail(User user) {
        Map<String, String> placeHolders = new HashMap<>();
        placeHolders.put("firstname", user.getFirstName());
        placeHolders.put("surname", user.getSurname());
        log.debug("Sending registration email to '{}'", user.getEmail());
        sendEmailFromTemplate(user.getEmail(), 47L, true, false, null, placeHolders);
    }

    @Async
    public void sendChangePasswordMail(User user) {
        Map<String, String> placeHolders = new HashMap<>();
        placeHolders.put("firstname", user.getFirstName());
        placeHolders.put("surname", user.getSurname());
        log.debug("Sending change password email to '{}'", user.getEmail());
        sendEmailFromTemplate(user.getEmail(), 41L, true, false, null, placeHolders);
    }

    @Async
    public void sendEmailUpdateMail(User user) {
        Map<String, String> placeHolders = new HashMap<>();
        placeHolders.put("firstname", user.getFirstName());
        placeHolders.put("surname", user.getSurname());
        log.debug("Sending email update email to '{}'", user.getEmail());
        sendEmailFromTemplate(user.getEmail(), 1L, true, false, null, placeHolders);
    }

    @Async
    public void sendPasswordForgotMail(User user) {
        Map<String, String> placeHolders = new HashMap<>();
        placeHolders.put("firstname", user.getFirstName());
        placeHolders.put("surname", user.getSurname());
        placeHolders.put("username", user.getUserName());
        placeHolders.put("password", user.getTempPassword());
        log.debug("Sending email forgot password  email to '{}'", user.getEmail());
        sendEmailFromTemplate(user.getEmail(), 87L, true, false, null, placeHolders);
    }

    @Async
    public void sendWorkflowMail(final Map<String, String> placeHolders,
                                 final Long templateId,
                                 final String email) {

        log.debug("Sending email workflow notification to '{}'", email);
        sendEmailFromTemplate(email, templateId,
                true, false, null, placeHolders);
    }

    @Async
    public void sendPrepackageNotificationMail(
            final PrePackageSubscriptionNotification prePackageSubscriptionNotification) {
        Map<String, String> placeHolders = new HashMap<>();
        placeHolders.put("Name", prePackageSubscriptionNotification.getFullName());
        placeHolders.put("REFERENCE_NUMBER", prePackageSubscriptionNotification.getReferenceId());
        log.debug("Sending email prepackage notification to '{}'", prePackageSubscriptionNotification.getEmailId());
        sendEmailFromTemplate(prePackageSubscriptionNotification.getEmailId(),
                prePackageSubscriptionNotification.getTemplateId(),
                true, false, null,
                placeHolders);
    }

    @Async
    public void sendPrepackageExecutionMail(final PrePackageSubscriptionExecution prePackageSubscriptionExecution) {
        Map<String, String> placeHolders = new HashMap<>();
        placeHolders.put("NAME", prePackageSubscriptionExecution.getFullName());
        placeHolders.put("RefNo", prePackageSubscriptionExecution.getRefNo());
        placeHolders.put("Name", prePackageSubscriptionExecution.getPrepackageName());
        placeHolders.put("details", prePackageSubscriptionExecution.getDetails());
        placeHolders.put("ftp", prePackageSubscriptionExecution.getFtpLink());
        log.debug("Sending email prepackage execution  to '{}'", prePackageSubscriptionExecution.getEmailId());
        sendEmailFromTemplate(prePackageSubscriptionExecution.getEmailId(),
                prePackageSubscriptionExecution.getTemplateId(),
                true, false, null,
                placeHolders);
    }

    @Async
    public void sendDataViewerRequestNotificationMail(DataViewerRequestNotification dataViewerRequestNotification) {
        Map<String, String> placeHolders = new HashMap<>();
        placeHolders.put("NAME", dataViewerRequestNotification.getFullName());
        placeHolders.put("REFNO", dataViewerRequestNotification.getReferenceNo());
        placeHolders.put("QUERY", dataViewerRequestNotification.getQuery());
        placeHolders.put("FTP", dataViewerRequestNotification.getFtpLink());
        log.debug("Sending data viewer request email to '{}'", dataViewerRequestNotification.getEmailId());
        sendEmailFromTemplate(dataViewerRequestNotification.getEmailId(),
                dataViewerRequestNotification.getTemplateId(),
                true, false, null,
                placeHolders);
    }

    @Async
    public void sendReservationMailNotification(final Map<String, String> placeHolders,
                                 final Long templateId,
                                 final String email) {

        log.debug("Sending email reservation notification to '{}'", email);
        sendEmailFromTemplate(email, templateId,
                true, false, null, placeHolders);
    }


    @Async
    public void sendReservationActionEmail(final List<String> ccList,
                                           final User user,
                                           final Long templateId,
                                           final String referenceNumber,
                                           final String context) {

        Map<String, String> placeHolders = new HashMap<>();
        placeHolders.put("firstName", user.getFirstName());
        placeHolders.put("surName", user.getSurname());
        placeHolders.put("referenceNumber",referenceNumber);
        placeHolders.put("context", context);
        sendEmailFromTemplateWithCC(user.getEmail(),
                ccList,
                templateId,
                true, false, null,
                placeHolders);

    }


    @Async
    public void sendExternalEmail(final EmailVm emailVm) {

        Map<String, String> contentEntry = emailVm.getContentEntry();
        sendEmailFromTemplate(emailVm.getSendToEmailId(), emailVm.getTemplateId(),
                true, false, null, contentEntry);

    }


}
