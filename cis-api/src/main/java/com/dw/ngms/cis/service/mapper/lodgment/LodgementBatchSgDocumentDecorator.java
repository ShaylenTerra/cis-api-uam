package com.dw.ngms.cis.service.mapper.lodgment;

import com.dw.ngms.cis.persistence.domains.listmanagement.ListItem;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementBatch;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementBatchSgDocument;
import com.dw.ngms.cis.persistence.repository.listmanagement.ListItemRepository;
import com.dw.ngms.cis.persistence.repository.lodgement.LodgementBatchRepository;
import com.dw.ngms.cis.service.dto.lodgment.LodgementBatchSgDocumentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author prateek on 21-04-2022
 */
public abstract class LodgementBatchSgDocumentDecorator implements LodgementBatchSgDocumentMapper  {

    @Autowired
    @Qualifier("delegate")
    private LodgementBatchSgDocumentMapper delegate;

    @Autowired
    private LodgementBatchRepository lodgementBatchRepository;

    @Autowired
    private ListItemRepository listItemRepository;

    @Override
    public LodgementBatchSgDocument lodgementBatchSgDocumentDtoToLodgementBatchSgDocument(LodgementBatchSgDocumentDto lodgementBatchSgDocumentDto) {
        LodgementBatchSgDocument lodgementBatchSgDocument = delegate
                .lodgementBatchSgDocumentDtoToLodgementBatchSgDocument(lodgementBatchSgDocumentDto);

        if(null != lodgementBatchSgDocumentDto) {

            Long batchId = lodgementBatchSgDocumentDto.getBatchId();

            LodgementBatch byBatchId = lodgementBatchRepository.findByBatchId(batchId);

            if(null != byBatchId) {

                lodgementBatchSgDocument.setLodgementBatch(byBatchId);
            }
        }

        return lodgementBatchSgDocument;
    }

    @Override
    public LodgementBatchSgDocumentDto lodgementBatchSgDocumentToLodgementBatchSgDocumentDto(LodgementBatchSgDocument lodgementBatchSgDocument) {
        LodgementBatchSgDocumentDto lodgementBatchSgDocumentDto = delegate
                .lodgementBatchSgDocumentToLodgementBatchSgDocumentDto(lodgementBatchSgDocument);

        if(null != lodgementBatchSgDocument) {

            LodgementBatch lodgementBatch = lodgementBatchSgDocument.getLodgementBatch();

            if(null != lodgementBatch) {
                lodgementBatchSgDocumentDto.setBatchId(lodgementBatch.getBatchId());
            }
            Long docTypeItemId = lodgementBatchSgDocument.getDocTypeItemId();

            ListItem byItemId = listItemRepository.findByItemId(docTypeItemId);

            if(null != byItemId) {
                lodgementBatchSgDocumentDto.setDocumentType(byItemId.getCaption());
            }
        }



        return lodgementBatchSgDocumentDto;
    }
}
