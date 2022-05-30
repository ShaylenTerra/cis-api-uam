package com.dw.ngms.cis.service.mapper.lodgment;

import com.dw.ngms.cis.persistence.domains.lodgement.LodgementBatch;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraft;
import com.dw.ngms.cis.persistence.repository.lodgement.LodgementDraftRepository;
import com.dw.ngms.cis.service.dto.lodgment.LodgementBatchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author prateek on 22-04-2022
 */
public abstract class LodgementBatchDecorator implements LodgementBatchMapper {

    @Autowired
    @Qualifier("delegate")
    private LodgementBatchMapper delegate;

    @Autowired
    private LodgementDraftRepository lodgementDraftRepository;

    @Override
    public LodgementBatch lodgementBatchDtoToLodgementBatch(LodgementBatchDto lodgementBatchDto) {

        return delegate
                .lodgementBatchDtoToLodgementBatch(lodgementBatchDto);
    }

    @Override
    public LodgementBatchDto lodgementBatchToLodgementBatchDto(LodgementBatch lodgementBatch) {

        return delegate
                .lodgementBatchToLodgementBatchDto(lodgementBatch);
    }
}
