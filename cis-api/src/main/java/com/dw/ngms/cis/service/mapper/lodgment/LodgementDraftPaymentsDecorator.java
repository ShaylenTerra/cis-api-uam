package com.dw.ngms.cis.service.mapper.lodgment;

import com.dw.ngms.cis.persistence.domains.listmanagement.ListItem;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraft;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraftPayment;
import com.dw.ngms.cis.persistence.repository.listmanagement.ListItemRepository;
import com.dw.ngms.cis.persistence.repository.lodgement.LodgementDraftRepository;
import com.dw.ngms.cis.service.dto.lodgment.LodgementDraftPaymentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author prateek on 08-04-2022
 */
public abstract class LodgementDraftPaymentsDecorator implements LodgementDraftPaymentsMapper {

    @Autowired
    @Qualifier("delegate")
    private LodgementDraftPaymentsMapper delegate;

    @Autowired
    private LodgementDraftRepository lodgementDraftRepository;

    @Autowired
    private ListItemRepository listItemRepository;

    /**
     * @param lodgementDraftPaymentDto {@link LodgementDraftPaymentDto}
     * @return {@link LodgementDraftPayment}
     */
    @Override
    public LodgementDraftPayment lodgementDraftPaymentDtoToLodgementDraftPayment(LodgementDraftPaymentDto lodgementDraftPaymentDto) {
        LodgementDraftPayment lodgementDraftPayment = delegate
                .lodgementDraftPaymentDtoToLodgementDraftPayment(lodgementDraftPaymentDto);

        Long draftId = lodgementDraftPaymentDto.getDraftId();

        if (null != draftId) {

            LodgementDraft byDraftId = lodgementDraftRepository.findByDraftId(draftId);

            lodgementDraftPayment.setLodgementDraft(byDraftId);
        }

        return lodgementDraftPayment;
    }

    /**
     * @param lodgementDraftPayment {@link LodgementDraftPayment}
     * @return {@link LodgementDraftPaymentDto}
     */
    @Override
    public LodgementDraftPaymentDto lodgementDraftPaymentToLodgementDraftPaymentDto(LodgementDraftPayment lodgementDraftPayment) {
        LodgementDraftPaymentDto lodgementDraftPaymentDto = delegate
                .lodgementDraftPaymentToLodgementDraftPaymentDto(lodgementDraftPayment);

        LodgementDraft lodgementDraft = lodgementDraftPayment.getLodgementDraft();

        if (null != lodgementDraft) {

            Long draftId = lodgementDraft.getDraftId();

            lodgementDraftPaymentDto.setDraftId(draftId);
        }

        Long payMethodItemId = lodgementDraftPayment.getPayMethodItemId();

        ListItem byItemId = listItemRepository.findByItemId(payMethodItemId);

        if(null != byItemId) {

            lodgementDraftPaymentDto.setPaymentMethod(byItemId.getCaption());   
        }

        Long statusItemId = lodgementDraftPayment.getStatusItemId();

        ListItem byItemId1 = listItemRepository.findByItemId(statusItemId);

        if(null != byItemId1) {

            lodgementDraftPaymentDto.setStatus(byItemId1.getCaption());
        }

        return lodgementDraftPaymentDto;
    }
}
