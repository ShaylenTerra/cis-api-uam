package com.dw.ngms.cis.service.lodgment;

import com.dw.ngms.cis.persistence.domains.Province;
import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraft;
import com.dw.ngms.cis.persistence.domains.workflow.Workflow;
import com.dw.ngms.cis.persistence.projection.lodgment.LodgementDocumentSummaryDto;
import com.dw.ngms.cis.persistence.repository.ProvinceRepository;
import com.dw.ngms.cis.persistence.repository.UserRepository;
import com.dw.ngms.cis.persistence.repository.lodgement.LodgementDraftRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowRepository;
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
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author prateek on 21-04-2022
 */
@Service
@AllArgsConstructor
@Slf4j
public class LodgementPerformaInvoiceGenerationService {

    private final UserRepository userRepository;

    private final ProvinceRepository provinceRepository;

    private final LodgementDocumentSummaryService lodgementDocumentSummaryService;

    private final LodgementDraftRepository lodgementDraftRepository;

    private final SpringTemplateEngine templateEngine;

    private final PdfGeneratorService pdfGeneratorService;

    public String generatePerformaInvoice(final Long draftId) {

        Context context = new Context();

        try {

            LodgementDraft byDraftId = lodgementDraftRepository.findByDraftId(draftId);

            if (null != byDraftId) {


                context.setVariable("datedOn", LocalDateTime.now());

                context.setVariable("referenceNo", byDraftId.getDraftId());

                context.setVariable("name", byDraftId.getName());

                Long applicantUserId = byDraftId.getApplicantUserId();

                User userByUserId = userRepository.findUserByUserId(applicantUserId);

                if (null != userByUserId) {
                    context.setVariable("user", userByUserId);
                }

                Long provinceId = byDraftId.getProvinceId();

                Province byProvinceId = provinceRepository.findByProvinceId(provinceId);

                if (null != byProvinceId) {
                    // attach province object to context
                    context.setVariable("province", byProvinceId);
                }

                Collection<LodgementDocumentSummaryDto> documentSummary = lodgementDocumentSummaryService
                        .getDocumentSummary(draftId, 0L);

                List<LodgementDocumentSummaryDto> finalDocumentSummary = documentSummary.stream()
                        .filter(lodgementDocumentSummaryDto -> !lodgementDocumentSummaryDto.getCost().equals(0.0D))
                        .collect(Collectors.toList());

                // setting document summary
                context.setVariable("docSummary", finalDocumentSummary);

                double sum = finalDocumentSummary.stream()
                        .mapToDouble(LodgementDocumentSummaryDto::getTotalCost)
                        .sum();

                context.setVariable("totalCost", sum);


                String processedHtml = templateEngine.process("LDG- Perform Invoice", context);

                String xhtml = TemplateUtils.convertToXhtml(processedHtml);

                String filePath = FileUtils.ROOT_PATH + FileUtils.PATH_SEPARATOR +
                        "performa-" + byDraftId.getDraftId() + ".pdf";

                return pdfGeneratorService.generatePdfFile(xhtml, filePath);

            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error(" Exception occured while creating performa invoice with cause {}",e.getMessage());
        }

        return null;
    }
}
