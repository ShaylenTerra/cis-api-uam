package com.dw.ngms.cis.service;

import com.dw.ngms.cis.cisemailer.service.Mail;
import com.dw.ngms.cis.configuration.SearchProperties;
import com.dw.ngms.cis.enums.ProcessTemplateType;
import com.dw.ngms.cis.persistence.domain.SgdataDocuments;
import com.dw.ngms.cis.persistence.domain.number.SgdataParcels;
import com.dw.ngms.cis.persistence.domains.Template;
import com.dw.ngms.cis.persistence.repository.TemplateRepository;
import com.dw.ngms.cis.persistence.repository.UserRepository;
import com.dw.ngms.cis.service.dto.EmailDto;
import com.dw.ngms.cis.utilities.FileUtils;
import com.dw.ngms.cis.utilities.TemplateUtils;
import com.dw.ngms.cis.web.vm.SearchDetailsShareVm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : prateekgoel
 * @since : 26/06/21, Sat
 **/
@Service
@AllArgsConstructor
@Slf4j
public class ShareSearchDetailsService {

    private final SearchService searchService;

    private final EmailServiceHandler emailServiceHandler;

    private final SearchProperties searchProperties;

    private final TemplateRepository templateRepository;

    private final TemplateUtils templateUtils;

    private final UserRepository userRepository;

    @Async
    public void shareSearchDetails(final SearchDetailsShareVm searchDetailsShareVm) {
        final SgdataParcels searchByRecordId = searchService.getSearchByRecordId(searchDetailsShareVm.getRecordId());

        final Collection<SgdataDocuments> documents = searchService.getDocuments(searchDetailsShareVm.getRecordId());

        final SearchProperties.ImagePrefixPath imagePrefixPath = searchProperties.getImagePrefixPath();

        List<Resource> resources = documents.stream().map(sgdataDocuments -> {
            try {

                return FileUtils
                        .writeInputStreamToFile(new URL(imagePrefixPath.getUrlPrefix() + sgdataDocuments.getUrl())
                                .openStream(), sgdataDocuments.getUrl());

            } catch (IOException e) {
                log.error("error occurred while fetching images for recordId [{}]", searchDetailsShareVm.getRecordId());
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());

        if (null != searchByRecordId && CollectionUtils.isNotEmpty(resources)) {
            String details = searchByRecordId.getLpi() + FileUtils.HYPHEN + searchByRecordId.getSgNo() +
                    FileUtils.HYPHEN + searchByRecordId.getProvince();

            final Template byTemplateId = templateRepository.findByTemplateId(ProcessTemplateType.SHARE_INFORMATION
                    .getTemplateType());

            final EmailDto emailFromEmailTemplate = templateUtils.getEmailFromEmailTemplate(byTemplateId.getEmailDetails());
            if (null != byTemplateId) {
                final String userNameUsingUserId = userRepository.getUserNameUsingUserId(searchDetailsShareVm.getUserId());
                Map<String, String> placeHolder = new HashMap<>();
                placeHolder.put("details", details);
                placeHolder.put("loggedUserName", userNameUsingUserId);
                placeHolder.put("email", searchDetailsShareVm.getEmailId());

                final String mailBodyContent = emailServiceHandler
                        .getMailBodyContent(emailFromEmailTemplate.getBody(), placeHolder);

                final Mail mail = Mail.builder()
                        .isHtml(Boolean.TRUE)
                        .isMultipart(Boolean.TRUE)
                        .to(searchDetailsShareVm.getEmailId())
                        .resources(resources)
                        .subject(emailFromEmailTemplate.getSubject())
                        .body(mailBodyContent).build();

                emailServiceHandler.sendEmail(mail);

                logToSearchDetailsShare(searchDetailsShareVm.getEmailId(), searchDetailsShareVm.getUserId());

            } else {
                log.debug(" email template not registered for templateId [{}]", ProcessTemplateType.SHARE_INFORMATION
                        .getTemplateType());
            }

        }
    }

    private void logToSearchDetailsShare(final String email, final Long userId) {
        searchService.saveSearchDetailShare(email, userId);
    }

}
