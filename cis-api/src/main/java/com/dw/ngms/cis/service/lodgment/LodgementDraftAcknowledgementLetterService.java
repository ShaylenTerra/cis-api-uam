package com.dw.ngms.cis.service.lodgment;

import com.dw.ngms.cis.persistence.domains.Province;
import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementBatch;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraft;
import com.dw.ngms.cis.persistence.domains.workflow.Workflow;
import com.dw.ngms.cis.persistence.repository.ProvinceRepository;
import com.dw.ngms.cis.persistence.repository.UserRepository;
import com.dw.ngms.cis.persistence.repository.lodgement.LodgementBatchRepository;
import com.dw.ngms.cis.persistence.repository.lodgement.LodgementDraftRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowRepository;
import com.dw.ngms.cis.service.dto.lodgment.LodgementBatchDto;
import com.dw.ngms.cis.service.mapper.lodgment.LodgementBatchMapper;
import com.dw.ngms.cis.service.workflow.PdfGeneratorService;
import com.dw.ngms.cis.utilities.FileUtils;
import com.dw.ngms.cis.utilities.TemplateUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

/**
 * @author prateek on 21-04-2022
 */
@Service
@AllArgsConstructor
@Slf4j
public class LodgementDraftAcknowledgementLetterService {

    private final UserRepository userRepository;

    private final ProvinceRepository provinceRepository;

    private final LodgementBatchRepository lodgementBatchRepository;

    private final LodgementDraftRepository lodgementDraftRepository;

    private final WorkflowRepository workflowRepository;

    private final SpringTemplateEngine templateEngine;

    private final PdfGeneratorService pdfGeneratorService;

    private final LodgementBatchMapper lodgementBatchMapper;

    public String generateAckLetter(final Long draftId) {

        Context context = new Context();

        LodgementDraft byDraftId = lodgementDraftRepository.findByDraftId(draftId);

        try {

            if (null != byDraftId) {

                context.setVariable("name", byDraftId.getName());

                Long workflowId = byDraftId.getWorkflowId();

                Workflow byWorkflowId = workflowRepository.findByWorkflowId(workflowId);

                context.setVariable("datedOn", LocalDateTime.now());

                if (null != byWorkflowId) {
                    context.setVariable("referenceNo", byWorkflowId.getReferenceNo());
                }

                Long provinceId = byDraftId.getProvinceId();

                Province byProvinceId = provinceRepository.findByProvinceId(provinceId);

                if (null != byProvinceId) {
                    context.setVariable("province", byProvinceId);
                }

                Long applicant = byDraftId.getApplicantUserId();

                User userByUserId = userRepository.findUserByUserId(applicant);

                if (null != userByUserId) {
                    context.setVariable("user", userByUserId);
                }

                LodgementBatch byDraftId1 = lodgementBatchRepository.findByDraftId(draftId);

                if (null != byDraftId1) {

                    LodgementBatchDto lodgementBatchDto = lodgementBatchMapper
                            .lodgementBatchToLodgementBatchDto(byDraftId1);

                    context.setVariable("lodgementBatch", lodgementBatchDto);
                }

                String processedHtml = templateEngine.process("LDG- Acknowledgement Letter", context);

                String xhtml = TemplateUtils.convertToXhtml(processedHtml);

                String filePath = FileUtils.ROOT_PATH + FileUtils.PATH_SEPARATOR +
                        "lodgement-acknowledgement" + byDraftId.getDraftId() + ".pdf";

                return pdfGeneratorService.generatePdfFile(xhtml, filePath);

            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("Exception occured while creating lodgement acknowledgement letter with cause {}", e.getMessage());
        }

        return null;

    }

}
