package com.dw.ngms.cis.service.reservation;

import com.dw.ngms.cis.persistence.domains.Province;
import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.domains.listmanagement.ListItem;
import com.dw.ngms.cis.persistence.domains.province.ProvinceAddress;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationCondition;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraft;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftSteps;
import com.dw.ngms.cis.persistence.domains.user.UserMetaData;
import com.dw.ngms.cis.persistence.domains.workflow.Workflow;
import com.dw.ngms.cis.persistence.repository.ProvinceRepository;
import com.dw.ngms.cis.persistence.repository.TemplateRepository;
import com.dw.ngms.cis.persistence.repository.UserRepository;
import com.dw.ngms.cis.persistence.repository.listmanagement.ListItemRepository;
import com.dw.ngms.cis.persistence.repository.province.ProvinceAddressRepository;
import com.dw.ngms.cis.persistence.repository.reservation.ReservationConditionRepository;
import com.dw.ngms.cis.persistence.repository.reservation.ReservationDraftRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowRepository;
import com.dw.ngms.cis.service.mapper.reservation.ReservationDraftStepsMapper;
import com.dw.ngms.cis.service.workflow.PdfGeneratorService;
import com.dw.ngms.cis.utilities.FileUtils;
import com.dw.ngms.cis.utilities.TemplateUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author prateek on 04-02-2022
 */
@Service
@Slf4j
@AllArgsConstructor
public class ReservationAcknowledgementLetterService {

    private final ReservationDraftRepository reservationDraftRepository;

    private final WorkflowRepository workflowRepository;

    private final ReservationConditionRepository reservationConditionRepository;

    private final UserRepository userRepository;

    private final ProvinceAddressRepository provinceAddressRepository;

    private final SpringTemplateEngine templateEngine;

    private final PdfGeneratorService pdfGeneratorService;

    private final ProvinceRepository provinceRepository;

    private final ReservationDraftStepsMapper reservationDraftStepsMapper;

    /**
     * @param draftId draftId
     * @return {@link Resource}
     */
    public Resource generateAcknowledgementLetter(final Long draftId) {

        try {

            ReservationDraft byDraftId = reservationDraftRepository.findByDraftId(draftId);

            if (null != byDraftId) {

                Context context = new Context();

                Set<ReservationDraftSteps> reservationDraftSteps = byDraftId.getReservationDraftSteps();
                List<ReservationDraftSteps> collect = reservationDraftSteps.stream()
                        .map(reservationDraftStepsMapper::reservationDraftStepsToReservationDraftSteps)
                        .collect(Collectors.toList());
                context.setVariable("steps", collect);

                List<ReservationCondition> allByDraftId = reservationConditionRepository
                        .findAllByDraftIdOrderByConditionId(draftId);
                context.setVariable("conditions",allByDraftId);

                LocalDateTime dated = byDraftId.getDated();
                context.setVariable("draftDate",dated.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

                Long provinceId = byDraftId.getProvinceId();

                ProvinceAddress byProvinceId = provinceAddressRepository.findByProvinceId(provinceId);

                if (null != byProvinceId) {
                    String provinceAddress = byProvinceId.getProvinceAddress();
                    context.setVariable("provinceAddress", provinceAddress);
                    context.setVariable("provinceName", byProvinceId);
                }

                Province byProvinceId1 = provinceRepository.findByProvinceId(provinceId);
                if(null != byProvinceId1) {
                    context.setVariable("provinceName", byProvinceId1.getProvinceName());
                }

                Long workflowId = byDraftId.getWorkflowId();

                Workflow byWorkflowId = workflowRepository.findByWorkflowId(workflowId);

                if (null != byWorkflowId) {
                    context.setVariable("referenceNumber", byWorkflowId.getReferenceNo());
                }

                Long applicantUserId = byDraftId.getApplicantUserId();

                User userByUserId = userRepository.findUserByUserId(applicantUserId);
                String firstName = userByUserId.getFirstName();
                String finalName = "";
                if (StringUtils.isNotBlank(firstName)) {
                    finalName = firstName;
                }
                String surname = userByUserId.getSurname();
                if (StringUtils.isNotBlank(surname)) {
                    finalName += " " + surname;
                }
                context.setVariable("reqName", finalName);

                String email = userByUserId.getEmail();
                context.setVariable("reqEmail", email);

                String mobile = userByUserId.getMobileNo();
                context.setVariable("reqMobile", mobile);

                UserMetaData userMetaData = userByUserId.getUserMetaData();
                if (null != userMetaData) {
                    String finalAddress = "";
                    String postalAddressLine1 = userMetaData.getPostalAddressLine1();
                    if (StringUtils.isNotBlank(postalAddressLine1)) {
                        finalAddress = postalAddressLine1;
                    }
                    String postalAddressLine2 = userMetaData.getPostalAddressLine2();
                    if (StringUtils.isNotBlank(postalAddressLine2)) {
                        finalAddress += " " + postalAddressLine2;
                    }
                    String postalAddressLine3 = userMetaData.getPostalAddressLine3();
                    if (StringUtils.isNotBlank(postalAddressLine3)) {
                        finalAddress += " " + postalAddressLine3;
                    }
                    context.setVariable("reqAddress", finalAddress);
                }

                context.setVariable("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

                String processedHtml = templateEngine.process("RES-Acknowledgement Letter", context);

                String xhtml = TemplateUtils.convertToXhtml(processedHtml);

                String filePath = FileUtils.ROOT_PATH + FileUtils.PATH_SEPARATOR +
                        "Acknowledgement-" + byWorkflowId.getReferenceNo() + ".pdf";

                String generatedFilePath = pdfGeneratorService.generatePdfFile(xhtml, filePath);

                return new FileSystemResource(generatedFilePath);
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error(" unsupported encoding error occurred while processing acknowledgment letter for reservation ");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("exception occurred while processing acknowledgment letter for reservation ");
        }

        return null;
    }




}
