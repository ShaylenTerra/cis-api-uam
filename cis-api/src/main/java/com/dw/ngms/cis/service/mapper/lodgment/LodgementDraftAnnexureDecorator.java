package com.dw.ngms.cis.service.mapper.lodgment;

import com.dw.ngms.cis.persistence.domains.listmanagement.ListItem;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraft;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraftAnnexure;
import com.dw.ngms.cis.persistence.repository.listmanagement.ListItemRepository;
import com.dw.ngms.cis.persistence.repository.lodgement.LodgementDraftRepository;
import com.dw.ngms.cis.service.dto.lodgment.LodgementDraftAnnexureDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author prateek on 08-04-2022
 */
public abstract class LodgementDraftAnnexureDecorator implements LodgementDraftAnnexureMapper{

    @Autowired
    @Qualifier("delegate")
    private LodgementDraftAnnexureMapper delegate;

    @Autowired
    private LodgementDraftRepository lodgementDraftRepository;

    @Autowired
    private ListItemRepository listItemRepository;

    @Override
    public LodgementDraftAnnexure lodgementDraftAnnexureDtoToLodgementDraftAnnexure(LodgementDraftAnnexureDto lodgementDraftAnnexureDto) {
        LodgementDraftAnnexure lodgementDraftAnnexure = delegate
                .lodgementDraftAnnexureDtoToLodgementDraftAnnexure(lodgementDraftAnnexureDto);

        if(null != lodgementDraftAnnexureDto) {

            Long draftId = lodgementDraftAnnexureDto.getDraftId();

            LodgementDraft byDraftId = lodgementDraftRepository.findByDraftId(draftId);

            if(null != byDraftId) {

                lodgementDraftAnnexure.setLodgementDraft(byDraftId);
            }
        }

        return lodgementDraftAnnexure;
    }

    @Override
    public LodgementDraftAnnexureDto lodgementDraftAnnexureToLodgementDraftAnnexureDto(LodgementDraftAnnexure lodgementDraftAnnexure) {
        LodgementDraftAnnexureDto lodgementDraftAnnexureDto = delegate
                .lodgementDraftAnnexureToLodgementDraftAnnexureDto(lodgementDraftAnnexure);
        if(null != lodgementDraftAnnexure) {

            LodgementDraft lodgementDraft = lodgementDraftAnnexure.getLodgementDraft();

            lodgementDraftAnnexureDto.setDraftId(lodgementDraft.getDraftId());

            Long typeItemId = lodgementDraftAnnexure.getTypeItemId();

            ListItem byItemId = listItemRepository.findByItemId(typeItemId);

            if(null != byItemId) {
                lodgementDraftAnnexureDto.setType(byItemId.getCaption());
            }
        }


        return lodgementDraftAnnexureDto;
    }
}
