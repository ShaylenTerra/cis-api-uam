package com.dw.ngms.cis.service.workflow;

import com.dw.ngms.cis.cisemailer.service.Mail;
import com.dw.ngms.cis.cisemailer.util.MailUtils;
import com.dw.ngms.cis.enums.DeliveryMedium;
import com.dw.ngms.cis.enums.DeliveryMethod;
import com.dw.ngms.cis.persistence.domains.Template;
import com.dw.ngms.cis.persistence.domains.cart.CartDispatchAdditionalInfo;
import com.dw.ngms.cis.persistence.repository.TemplateRepository;
import com.dw.ngms.cis.service.EmailServiceHandler;
import com.dw.ngms.cis.service.dto.EmailDto;
import com.dw.ngms.cis.utilities.CartUtils;
import com.dw.ngms.cis.utilities.TemplateUtils;
import com.dw.ngms.cis.web.vm.workflow.ProcessNotificationsVm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * @author : prateekgoel
 * @since : 31/05/21, Mon
 **/
@Component
@AllArgsConstructor
@Slf4j
public class DispatchNotification implements ProcessNotification {

    private final CartUtils cartUtils;

    private final EmailServiceHandler emailServiceHandler;

    private final TemplateUtils templateUtils;

    private final TemplateRepository templateRepository;

    @Override
    public void process(final ProcessNotificationsVm processNotificationsVm) throws Exception {

        final CartDispatchAdditionalInfo cartDispatchAdditionalInfo = cartUtils
                .cartDispatchAdditionalInfo(processNotificationsVm.getWorkflowId());

        final Template byTemplateId = templateRepository.findByTemplateId(processNotificationsVm.getTemplateId());

        final EmailDto emailFromEmailTemplate = templateUtils.getEmailFromEmailTemplate(byTemplateId.getEmailDetails());

        try (FileOutputStream fileOutputStream = new FileOutputStream("/tmp/docs.zip")) {

            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

            cartUtils.zippedDispatchedDocument(processNotificationsVm.getWorkflowId(), zipOutputStream);

            zipOutputStream.close();

            Resource resource = new FileSystemResource("/tmp/docs.zip");

            Map<String, String> placeholder = new HashMap<>();
            placeholder.put("firstname", cartDispatchAdditionalInfo.getCollectorName());
            placeholder.put("surname", cartDispatchAdditionalInfo.getCollectorSurname());
            placeholder.put("referenceNumber", cartDispatchAdditionalInfo.getReferenceNumber());
            if (DeliveryMethod.ELECTRONIC.equals(cartDispatchAdditionalInfo.getDeliveryMethod())) {
                // chk if delivery medium is email/Ftp
                placeholder.put("dispatchMethod", cartDispatchAdditionalInfo.getDeliveryMethod().name());
                if (DeliveryMedium.FTP.equals(cartDispatchAdditionalInfo.getDeliveryMedium())) {
                    final String ftpLocation = cartUtils.uploadDispatchDocsOnFtp(processNotificationsVm.getWorkflowId());
                    placeholder.put("dispatchMedium", cartDispatchAdditionalInfo.getDeliveryMedium().name());
                    placeholder.put("ftpLocation", ftpLocation);
                    final String body = MailUtils.substituteStringToken(emailFromEmailTemplate.getBody(), placeholder);
                    final Mail mail = Mail.builder()
                            .to(cartDispatchAdditionalInfo.getPrimaryEmail())
                            .isMultipart(Boolean.TRUE)
                            .isHtml(Boolean.TRUE)
                            .subject(emailFromEmailTemplate.getSubject())
                            .body(body)
                            .resources(Collections.singleton(resource)).build();
                    emailServiceHandler.sendEmail(mail);

                } else if (DeliveryMedium.EMAIL.equals(cartDispatchAdditionalInfo.getDeliveryMedium())) {
                    placeholder.put("dispatchMedium", cartDispatchAdditionalInfo.getDeliveryMedium().name());
                    final String body = MailUtils.substituteStringToken(emailFromEmailTemplate.getBody(), placeholder);
                    final Mail mail = Mail.builder()
                            .to(cartDispatchAdditionalInfo.getPrimaryEmail())
                            .isMultipart(Boolean.TRUE)
                            .isHtml(Boolean.TRUE)
                            .subject(emailFromEmailTemplate.getSubject())
                            .body(body)
                            .resources(Collections.singleton(resource)).build();
                    emailServiceHandler.sendEmail(mail);

                }
            } else if (DeliveryMethod.POST.equals(cartDispatchAdditionalInfo.getDeliveryMethod()) ||
                    DeliveryMethod.COURIER.equals(cartDispatchAdditionalInfo.getDeliveryMethod())) {
                placeholder.put("dispatchMethod", cartDispatchAdditionalInfo.getDeliveryMethod().name());
                placeholder.put("contactPerson", cartDispatchAdditionalInfo.getContactPerson());
                placeholder.put("collectorContactName", cartDispatchAdditionalInfo.getCollectorName());
                placeholder.put("collectorContactNumber", cartDispatchAdditionalInfo.getCollectorContactNumber());
                placeholder.put("courierName", cartDispatchAdditionalInfo.getCourierName());
                placeholder.put("postalAddressLine1", cartDispatchAdditionalInfo.getPostalAddressLine1());
                placeholder.put("postalAddressLine2", cartDispatchAdditionalInfo.getPostalAddressLine2());
                placeholder.put("postalAddressLine3", cartDispatchAdditionalInfo.getPostalAddressLine3());
                placeholder.put("postalCode", cartDispatchAdditionalInfo.getPostalCode());

                final String body = MailUtils.substituteStringToken(emailFromEmailTemplate.getBody(), placeholder);
                final Mail mail = Mail.builder()
                        .to(cartDispatchAdditionalInfo.getPrimaryEmail())
                        .isMultipart(Boolean.FALSE)
                        .isHtml(Boolean.TRUE)
                        .subject(emailFromEmailTemplate.getSubject())
                        .body(body)
                        .build();
                emailServiceHandler.sendEmail(mail);

            } else if (DeliveryMethod.COLLECTION.equals(cartDispatchAdditionalInfo.getDeliveryMethod())) {
                placeholder.put("dispatchMethod", cartDispatchAdditionalInfo.getDeliveryMethod().name());
                final String body = MailUtils.substituteStringToken(emailFromEmailTemplate.getBody(), placeholder);
                final Mail mail = Mail.builder()
                        .to(cartDispatchAdditionalInfo.getPrimaryEmail())
                        .isMultipart(Boolean.FALSE)
                        .isHtml(Boolean.TRUE)
                        .subject(emailFromEmailTemplate.getSubject())
                        .body(body)
                        .build();
                emailServiceHandler.sendEmail(mail);

            } else {
                log.debug(" No delivery Method subscribed for workflowId [{}]", processNotificationsVm.getWorkflowId());
            }
        } catch (FileNotFoundException e) {
            log.error(" exception occur while sending dispatch email due to [{}] ", e.getMessage());
        } catch (IOException e) {
            log.error(" exception occur while sending dispatch email due to [{}]", e.getMessage());
        }

    }

}
