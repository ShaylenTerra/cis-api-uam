package com.dw.ngms.cis.service.mapper.lodgment;

import com.dw.ngms.cis.persistence.domains.listmanagement.ListItem;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraft;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraftStep;
import com.dw.ngms.cis.persistence.repository.listmanagement.ListItemRepository;
import com.dw.ngms.cis.persistence.repository.lodgement.LodgementDraftRepository;
import com.dw.ngms.cis.service.dto.lodgment.LodgementDraftStepsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Collection;

/**
 * @author prateek on 12-04-2022
 */
public abstract class LodgementDraftStepsDecorator implements LodgementDraftStepsMapper {

    @Autowired
    @Qualifier("delegate")
    private LodgementDraftStepsMapper delegate;

    @Autowired
    private LodgementDraftRepository lodgementDraftRepository;

    @Autowired
    private ListItemRepository listItemRepository;

    @Override
    public LodgementDraftStep lodgmentDraftStepsDtoToLodgmentDraftSteps(LodgementDraftStepsDto lodgementDraftStepsDto) {
        LodgementDraftStep lodgementDraftStep = delegate
                .lodgmentDraftStepsDtoToLodgmentDraftSteps(lodgementDraftStepsDto);
        if(null != lodgementDraftStepsDto) {

            Long draftId = lodgementDraftStepsDto.getDraftId();
            if (null != draftId && draftId > 0L) {
                LodgementDraft byDraftId = lodgementDraftRepository.findByDraftId(draftId);
                if (null != byDraftId)
                    lodgementDraftStep.setLodgementDraft(byDraftId);
            }
        }
        return lodgementDraftStep;
    }

    @Override
    public LodgementDraftStepsDto lodgmentDraftStepsToLodgmentDraftStepsDto(LodgementDraftStep lodgementDraftStep) {
        LodgementDraftStepsDto lodgementDraftStepsDto = delegate
                .lodgmentDraftStepsToLodgmentDraftStepsDto(lodgementDraftStep);

        if(null != lodgementDraftStep) {
            LodgementDraft lodgementDraft = lodgementDraftStep.getLodgementDraft();

            if (null != lodgementDraft) {
                Long draftId = lodgementDraft.getDraftId();
                lodgementDraftStepsDto.setDraftId(draftId);
            }

            Long purposeItemId = lodgementDraftStep.getPurposeItemId();

            ListItem byItemId = listItemRepository.findByItemId(purposeItemId);

            if (null != byItemId) {
                lodgementDraftStepsDto.setRequestReason(byItemId.getCaption());
            }

            Long documentItemId = lodgementDraftStep.getDocumentItemId();

            ListItem byItemId1 = listItemRepository.findByItemId(documentItemId);

            if (null != byItemId1) {

                lodgementDraftStepsDto.setDocumentType(byItemId1.getCaption());

            }

        }


        return lodgementDraftStepsDto;
    }
}
