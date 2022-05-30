package com.dw.ngms.cis.service.mapper.lodgment;

import com.dw.ngms.cis.persistence.domains.listmanagement.ListItem;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraft;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraftDocument;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraftRequest;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraftStep;
import com.dw.ngms.cis.persistence.repository.listmanagement.ListItemRepository;
import com.dw.ngms.cis.persistence.repository.lodgement.LodgementDraftRepository;
import com.dw.ngms.cis.persistence.repository.lodgement.LodgementDraftRequestRepository;
import com.dw.ngms.cis.persistence.repository.lodgement.LodgementDraftStepsRepository;
import com.dw.ngms.cis.service.dto.lodgment.LodgementDraftDocumentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author prateek on 12-04-2022
 */
public abstract class LodgementDraftDocumentDecorator implements LodgementDraftDocumentMapper {

    @Autowired
    @Qualifier("delegate")
    private LodgementDraftDocumentMapper delegate;

    @Autowired
    private LodgementDraftStepsRepository lodgementDraftStepsRepository;

    @Autowired
    private LodgementDraftRequestRepository lodgementDraftRequestRepository;

    @Autowired
    private ListItemRepository listItemRepository;

    @Autowired
    private LodgementDraftRepository lodgementDraftRepository;

    @Override
    public LodgementDraftDocumentDto lodgementDraftDocumentToLodgementDraftDocumentDto(LodgementDraftDocument lodgementDraftDocument) {
        LodgementDraftDocumentDto lodgementDraftDocumentDto = delegate.lodgementDraftDocumentToLodgementDraftDocumentDto(lodgementDraftDocument);

        if(null != lodgementDraftDocument) {
            LodgementDraftStep lodgementDraftStep = lodgementDraftDocument.getLodgementDraftStep();
            if (null != lodgementDraftStep) {
                Long stepId = lodgementDraftStep.getStepId();
                lodgementDraftDocumentDto.setStepId(stepId);
            }

            LodgementDraftRequest lodgementDraftRequest = lodgementDraftDocument.getLodgementDraftRequest();
            if (null != lodgementDraftRequest) {
                Long requestId = lodgementDraftRequest.getRequestId();
                lodgementDraftDocumentDto.setRequestId(requestId);
            }

            Long documentItemId = lodgementDraftDocument.getDocumentItemId();

            ListItem byItemId = listItemRepository.findByItemId(documentItemId);

            if (null != byItemId) {

                lodgementDraftDocumentDto.setDocumentType(byItemId.getCaption());
            }

            Long purposeItemId = lodgementDraftDocument.getPurposeItemId();

            ListItem byItemId1 = listItemRepository.findByItemId(purposeItemId);

            if (null != byItemId1) {
                lodgementDraftDocumentDto.setPurposeType(byItemId1.getCaption());
            }

            LodgementDraft lodgementDraft = lodgementDraftDocument.getLodgementDraft();

            if(null != lodgementDraft) {
                lodgementDraftDocumentDto.setDraftId(lodgementDraft.getDraftId());
            }

        }

        return lodgementDraftDocumentDto;
    }

    @Override
    public LodgementDraftDocument lodgementDraftDocumentDtoToLodgementDraftDocument(LodgementDraftDocumentDto lodgementDraftDocumentDto) {
        LodgementDraftDocument lodgementDraftDocument = delegate
                .lodgementDraftDocumentDtoToLodgementDraftDocument(lodgementDraftDocumentDto);

        if(null != lodgementDraftDocumentDto) {

            Long stepId = lodgementDraftDocumentDto.getStepId();

            LodgementDraftStep byStepId = lodgementDraftStepsRepository.findByStepId(stepId);

            if (null != byStepId) {
                lodgementDraftDocument.setLodgementDraftStep(byStepId);
            }

            Long requestId = lodgementDraftDocumentDto.getRequestId();

            LodgementDraftRequest byRequestId = lodgementDraftRequestRepository.findByRequestId(requestId);
            if (null != byRequestId) {
                lodgementDraftDocument.setLodgementDraftRequest(byRequestId);
            }

            Long draftId = lodgementDraftDocumentDto.getDraftId();

            LodgementDraft byDraftId = lodgementDraftRepository.findByDraftId(draftId);

            if(null != byDraftId) {
                lodgementDraftDocument.setLodgementDraft(byDraftId);
            }
        }


        return lodgementDraftDocument;
    }
}
