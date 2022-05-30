package com.dw.ngms.cis.persistence.repository.lodgement;

import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraft;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraftDocument;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraftRequest;
import com.dw.ngms.cis.persistence.projection.lodgment.LodgementDocumentSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author prateek on 04-04-2022
 */
@Repository
public interface LodgementDraftDocumentRepository
        extends JpaRepository<LodgementDraftDocument, Long> {

    List<LodgementDraftDocument> findByLodgementDraft(final LodgementDraft lodgementDraft);

    LodgementDraftDocument findByDocumentId(final Long documentId);

    List<LodgementDraftDocument> findByLodgementDraftRequest(final LodgementDraftRequest draftRequest);


    @Query(value = "select distinct(LDD.DOCUMENT_ITEMID) as documentItemId,\n" +
            "       count(LDD.DOCUMENT_ITEMID) as count,\n" +
            "       MLI.CAPTION as documentType,\n" +
            "       MLIP.CAPTION as purposeType,\n" +
            "       LDD.PURPOSE_ITEMID as purposeItemId\n" +
            "from LDG_DRAFT_DOCUMENT LDD\n" +
            "    inner join M_LIST_ITEM MLI on MLI.ITEMID = LDD.DOCUMENT_ITEMID\n" +
            "    inner join M_LIST_ITEM MLIP on MLIP.ITEMID = LDD.PURPOSE_ITEMID\n" +
            "    where LDD.STEP_ID= :stepId and LDD.DRAFT_ID =:draftId \n" +
            "group by LDD.DOCUMENT_ITEMID , MLI.CAPTION , MLIP.CAPTION,LDD.PURPOSE_ITEMID ORDER BY LDD.DOCUMENT_ITEMID",
            nativeQuery = true)
    List<LodgementDocumentSummary> getDocumentSummaryByStepId(final Long draftId, final Long stepId);

    @Query(value = "select distinct(LDD.DOCUMENT_ITEMID) as documentItemId,\n" +
            "       count(LDD.DOCUMENT_ITEMID) as count,\n" +
            "       MLI.CAPTION as documentType,\n" +
            "       MLIP.CAPTION as purposeType,\n" +
            "       LDD.PURPOSE_ITEMID as purposeItemId\n" +
            "from LDG_DRAFT_DOCUMENT LDD\n" +
            "    inner join M_LIST_ITEM MLI on MLI.ITEMID = LDD.DOCUMENT_ITEMID\n" +
            "    inner join M_LIST_ITEM MLIP on MLIP.ITEMID = LDD.PURPOSE_ITEMID\n" +
            "    where LDD.DRAFT_ID= :draftId  \n" +
            "group by LDD.DOCUMENT_ITEMID , MLI.CAPTION , MLIP.CAPTION,LDD.PURPOSE_ITEMID ORDER BY LDD.DOCUMENT_ITEMID ",
            nativeQuery = true)
    List<LodgementDocumentSummary> getDocumentSummary(final Long draftId);

}
