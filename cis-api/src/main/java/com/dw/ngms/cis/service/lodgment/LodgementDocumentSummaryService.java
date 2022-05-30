package com.dw.ngms.cis.service.lodgment;

import com.dw.ngms.cis.enums.FeeSimulatorType;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraft;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraftDocument;
import com.dw.ngms.cis.persistence.projection.lodgment.LodgementDocumentSummary;
import com.dw.ngms.cis.persistence.projection.lodgment.LodgementDocumentSummaryDto;
import com.dw.ngms.cis.persistence.repository.lodgement.LodgementDraftDocumentRepository;
import com.dw.ngms.cis.persistence.repository.lodgement.LodgementDraftRepository;
import com.dw.ngms.cis.service.FeeSimulatorService;
import com.dw.ngms.cis.service.dto.FeeSimulatorDto;
import com.dw.ngms.cis.web.vm.FeeSimulatorVm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author prateek on 21-04-2022
 */
@Service
@AllArgsConstructor
public class LodgementDocumentSummaryService {

    private final FeeSimulatorService feeSimulatorService;

    private final LodgementDraftDocumentRepository lodgementDraftDocumentRepository;

    private final LodgementDraftRepository lodgementDraftRepository;


    public Collection<LodgementDocumentSummaryDto> getDocumentSummary(final Long draftId, final Long stepId){
        if (stepId > 0) {
            List<LodgementDocumentSummary> documentSummaryByStepId = lodgementDraftDocumentRepository
                    .getDocumentSummaryByStepId(draftId, stepId);
            return calculateDocumentsCost(documentSummaryByStepId);

        }

        List<LodgementDocumentSummary> documentSummary = lodgementDraftDocumentRepository
                .getDocumentSummary(draftId);
        return calculateDocumentsCost(documentSummary);
    }

    private Collection<LodgementDocumentSummaryDto>
    calculateDocumentsCost(Collection<LodgementDocumentSummary> lodgementDocumentSummaries) {

        List<LodgementDocumentSummaryDto> lodgementDocumentSummaryDtos = new ArrayList<>();
        lodgementDocumentSummaries
                .forEach(lodgementDocumentSummary -> {
                    LodgementDocumentSummaryDto lodgementDocumentSummaryDto = new LodgementDocumentSummaryDto();
                    if (lodgementDocumentSummary.getDocumentItemId().equals(1069L)) {
                        FeeSimulatorVm feeSimulatorVm = new FeeSimulatorVm();
                        feeSimulatorVm.setType(FeeSimulatorType.LODGEMENT);
                        feeSimulatorVm.setCategoryId(0L);
                        feeSimulatorVm.setCategoryTypeId(241L);
                        feeSimulatorVm.setDeliveryMediumId(0L);
                        feeSimulatorVm.setDeliveryMethodId(80L);
                        feeSimulatorVm.setItemCount(0L);
                        feeSimulatorVm.setPaperSize(0L);
                        feeSimulatorVm.setFormatId(0L);
                        FeeSimulatorDto feeSimulatorDto = feeSimulatorService.calculateFee(feeSimulatorVm);
                        Double singleItemCost = feeSimulatorDto.getTotalCost();
                        Double totalCost = singleItemCost * lodgementDocumentSummary.getCount();
                        lodgementDocumentSummaryDto.setCost(singleItemCost);
                        lodgementDocumentSummaryDto.setDocumentType(lodgementDocumentSummary.getDocumentType());
                        lodgementDocumentSummaryDto.setDocumentItemId(lodgementDocumentSummary.getDocumentItemId());
                        lodgementDocumentSummaryDto.setCount(lodgementDocumentSummary.getCount());
                        lodgementDocumentSummaryDto.setPurposeType(lodgementDocumentSummary.getPurposeType());
                        lodgementDocumentSummaryDto.setPurposeItemId(lodgementDocumentSummary.getPurposeItemId());
                        lodgementDocumentSummaryDto.setTotalCost(totalCost);
                    } else {
                        lodgementDocumentSummaryDto.setDocumentType(lodgementDocumentSummary.getDocumentType());
                        lodgementDocumentSummaryDto.setDocumentItemId(lodgementDocumentSummary.getDocumentItemId());
                        lodgementDocumentSummaryDto.setCount(lodgementDocumentSummary.getCount());
                        lodgementDocumentSummaryDto.setPurposeType(lodgementDocumentSummary.getPurposeType());
                        lodgementDocumentSummaryDto.setPurposeItemId(lodgementDocumentSummary.getPurposeItemId());
                        lodgementDocumentSummaryDto.setTotalCost(0.0D);
                        lodgementDocumentSummaryDto.setCost(0.0D);
                    }

                    lodgementDocumentSummaryDtos.add(lodgementDocumentSummaryDto);

                });

        return lodgementDocumentSummaryDtos;
    }


}
