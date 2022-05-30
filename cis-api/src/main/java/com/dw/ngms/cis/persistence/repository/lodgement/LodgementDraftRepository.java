package com.dw.ngms.cis.persistence.repository.lodgement;

import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraft;
import com.dw.ngms.cis.persistence.projection.lodgment.LodgmentDraftListingProjection;
import com.dw.ngms.cis.persistence.projection.lodgment.LodgmentRecordIdVerificationProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface LodgementDraftRepository extends JpaRepository<LodgementDraft, Long> {

    LodgementDraft findByDraftId(final Long draftId);



    LodgementDraft findByWorkflowId(final Long workflowId);

    @Query(value = "select LD from LodgementDraft LD" +
            " where LD.workflowId is null and ( LD.applicantUserId =:applicantUserId or LD.userId =:userId ) ")
    Page<LodgementDraft> findShortLodgementDrafts(final Long userId,
                                                      final Long applicantUserId,
                                                      final Pageable pageable);

    @Query(value = "SELECT\n" +
            "    WF.PROCESSID as processId,\n" +
            "    RES.DRAFT_ID as draftId,\n" +
            "    WFP.NAME as processName,\n" +
            "    WF.WORKFLOWID as workflowId,\n" +
            "    WF.REFERENCE_NO as referenceNumber,\n" +
            "    WF.TRUNAROUND_DURATION as turnaroundDuration,\n" +
            "    CONCAT ( CONCAT(U.FIRSTNAME, ' ') ,  U.SURNAME )  AS assignedToUser,\n" +
            "    WF.INTERNAL_STATUS_ID as internalStatus,\n" +
            "    INT_STATUS.DESCRIPTION as internalStatusCaption,\n" +
            "    WF.EXTERNAL_STATUS_ID as externalStatus,\n" +
            "    EXT_STATUS.DESCRIPTION as externalStatusCaption,\n" +
            "    WF.PRIORITY_FLAG as priorityFlag,\n" +
            "    M_PRIORITY.DESCRIPTION as priorityName,\n" +
            "    WF.TRIGGEREDON as triggeredOn,\n" +
            "    U.USERID as userId,\n" +
            "    (Select max(WFA.ALLOCATEDON)   from WORKFLOW_ACTION WFA  WHERE  WF.WORKFLOWID = WFA.WORKFLOWID) as lastStatusUpdate,\n" +
            "    W_ACTION.ACTIONID as actionId,\n" +
            "    W_ACTION.NODEID as nodeId,\n" +
            "    W_ACTION.ACTIONREQUIRED as actionRequired,\n" +
            "    INT_W_ACTIONREQUIRED.DESCRIPTION as actionRequiredCaption,\n" +
            "    RES.NAME as reservationName,\n" +
            "    PROV.PROVINCENAME as provinceName\n" +
            "FROM WORKFLOW WF\n" +
            "         INNER JOIN TRANSACTIONS TRN on WF.TRANSACTIONID = TRN.TRANSACTIONID\n" +
            "         INNER JOIN WORKFLOWPROCESS WFP ON WF.PROCESSID = WFP.PROCESSID\n" +
            "         INNER JOIN USERS U on U.USERID = TRN.USERID\n" +
            "         INNER JOIN WORKFLOWPROCESS_MATRIX INT_STATUS on WF.INTERNAL_STATUS_ID = INT_STATUS.ID\n" +
            "         INNER JOIN WORKFLOWPROCESS_MATRIX EXT_STATUS on WF.EXTERNAL_STATUS_ID = EXT_STATUS.ID\n" +
            "         left JOIN WORKFLOWPROCESS_MATRIX M_PRIORITY on WF.PRIORITY_FLAG = M_PRIORITY.ID\n" +
            "         left JOIN WORKFLOW_ACTION W_ACTION on WF.WORKFLOWID = W_ACTION.WORKFLOWID and W_ACTION.ACTIONTAKEN is null\n" +
            "         left JOIN WORKFLOWPROCESS_MATRIX INT_W_ACTIONREQUIRED on W_ACTION.ActionRequired = INT_W_ACTIONREQUIRED.ID\n" +
            "         INNER JOIN LDG_DRAFT  RES on WF.WORKFLOWID = RES.WORKFLOW_ID\n" +
            "         INNER JOIN M_PROVINCES PROV on RES.PROVINCE_ID = PROV.PROVINCEID\n" +
            "WHERE WFP.PARENT_PROCESSID =:processId \n" +
            "  and  ( TRN.USERID =:userId   or RES.APPLICANT_USERID=:userId)",
            nativeQuery = true,
            countQuery = "SELECT\n" +
                    "    count(*)\n" +
                    "FROM WORKFLOW WF\n" +
                    "         INNER JOIN TRANSACTIONS TRN on WF.TRANSACTIONID = TRN.TRANSACTIONID\n" +
                    "         INNER JOIN WORKFLOWPROCESS WFP ON WF.PROCESSID = WFP.PROCESSID\n" +
                    "         INNER JOIN USERS U on U.USERID = TRN.USERID\n" +
                    "         INNER JOIN WORKFLOWPROCESS_MATRIX INT_STATUS on WF.INTERNAL_STATUS_ID = INT_STATUS.ID\n" +
                    "         INNER JOIN WORKFLOWPROCESS_MATRIX EXT_STATUS on WF.EXTERNAL_STATUS_ID = EXT_STATUS.ID\n" +
                    "         left JOIN WORKFLOWPROCESS_MATRIX M_PRIORITY on WF.PRIORITY_FLAG = M_PRIORITY.ID\n" +
                    "         left JOIN WORKFLOW_ACTION W_ACTION on WF.WORKFLOWID = W_ACTION.WORKFLOWID and W_ACTION.ACTIONTAKEN is null\n" +
                    "         left JOIN WORKFLOWPROCESS_MATRIX INT_W_ACTIONREQUIRED on W_ACTION.ActionRequired = INT_W_ACTIONREQUIRED.ID\n" +
                    "         INNER JOIN LDG_DRAFT  RES on WF.WORKFLOWID = RES.WORKFLOW_ID\n" +
                    "         INNER JOIN M_PROVINCES PROV on RES.PROVINCE_ID = PROV.PROVINCEID\n" +
                    "WHERE WFP.PARENT_PROCESSID =:processId\n" +
                    "  and  ( TRN.USERID =:userId   or RES.APPLICANT_USERID=:userId)")
    Page<LodgmentDraftListingProjection> loadgmentListing(final Long userId,
                                                          final Long processId,
                                                          final Pageable pageable);

}
