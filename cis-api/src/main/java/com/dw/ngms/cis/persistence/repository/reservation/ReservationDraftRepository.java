package com.dw.ngms.cis.persistence.repository.reservation;

import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraft;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraft;
import com.dw.ngms.cis.persistence.projection.reservation.ReservationDraftListingProjection;
import com.dw.ngms.cis.persistence.projection.reservation.ReservationRecordIdVerificationProjection;
import com.dw.ngms.cis.persistence.projection.reservation.ReservationTransferListProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ReservationDraftRepository extends JpaRepository<ReservationDraft, Long> {

    ReservationDraft findByDraftId(final Long draftId);

    ReservationDraft findByFileRef(final String fileRef);

    ReservationDraft findByName(final String ldgName);

    ReservationDraft findByWorkflowId(final Long workflowId);

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
            "    PROV.PROVINCENAME as provinceName,\n" +
            "   PROV.PROVINCEID as provinceId \n"+
            "FROM WORKFLOW WF\n" +
            "         INNER JOIN TRANSACTIONS TRN on WF.TRANSACTIONID = TRN.TRANSACTIONID\n" +
            "         INNER JOIN WORKFLOWPROCESS WFP ON WF.PROCESSID = WFP.PROCESSID\n" +
            "         INNER JOIN USERS U on U.USERID = TRN.USERID\n" +
            "         INNER JOIN WORKFLOWPROCESS_MATRIX INT_STATUS on WF.INTERNAL_STATUS_ID = INT_STATUS.ID\n" +
            "         INNER JOIN WORKFLOWPROCESS_MATRIX EXT_STATUS on WF.EXTERNAL_STATUS_ID = EXT_STATUS.ID\n" +
            "         left JOIN WORKFLOWPROCESS_MATRIX M_PRIORITY on WF.PRIORITY_FLAG = M_PRIORITY.ID\n" +
            "         left JOIN WORKFLOW_ACTION W_ACTION on WF.WORKFLOWID = W_ACTION.WORKFLOWID and W_ACTION.ACTIONTAKEN is null\n" +
            "         left JOIN WORKFLOWPROCESS_MATRIX INT_W_ACTIONREQUIRED on W_ACTION.ActionRequired = INT_W_ACTIONREQUIRED.ID\n" +
            "         INNER JOIN RES_DRAFT  RES on WF.WORKFLOWID = RES.WORKFLOW_ID\n" +
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
                    "         INNER JOIN RES_DRAFT  RES on WF.WORKFLOWID = RES.WORKFLOW_ID\n" +
                    "         INNER JOIN M_PROVINCES PROV on RES.PROVINCE_ID = PROV.PROVINCEID\n" +
                    "WHERE WFP.PARENT_PROCESSID =:processId\n" +
                    "  and  ( TRN.USERID =:userId   or RES.APPLICANT_USERID=:userId)")
    Page<ReservationDraftListingProjection> loadReservationListing(final Long userId,
                                                                   final Long processId,
                                                                   final Pageable pageable);

    @Query(value = "select RD from ReservationDraft RD" +
            " where RD.workflowId is null and ( RD.applicantUserId =:applicantUserId or RD.userId =:userId ) " +
            " and RD.processId =:processId")
    Page<ReservationDraft> findShortReservationDrafts(final Long userId,
                                                      final Long applicantUserId,
                                                      final Long processId,
                                                      final Pageable pageable);

    @Query(value = "SELECT \n" +
            "    WF.REFERENCE_NO as referenceNo,\n" +
            "    U.FIRSTNAME as firstName,\n" +
            "    U.SURNAME as surName,\n" +
            "    WPM.Description as status,\n" +
            "    MLI.CAPTION as caption,\n" +
            "    WF.WORKFLOWID as workflowId\n" +
            "from RES_DRAFT RD\n" +
            "         inner join WORKFLOW WF  on RD.WORKFLOW_ID = wf.WORKFLOWID\n" +
            "         inner join RES_DRAFT_STEPS RDS on RDS.DRAFT_ID = RD.DRAFT_ID\n" +
            "         inner join  RES_DRAFT_REQUEST   RDR on RDR.step_id = RDS.step_id\n" +
            "         inner join WORKFLOWPROCESS_MATRIX WPM on wf.External_Status_ID = WPM.ID\n" +
            "         inner join USERS  U on U.USERID = RD.Applicant_USERID\n" +
            "         inner join M_LIST_ITEM MLI   on MLI.ITEMID = RDS.REASON_ITEM_ID\n" +
            "where RDR.RECORD_ID= :recordId", nativeQuery = true)
    Collection<ReservationRecordIdVerificationProjection> verifyByRecordId(final Long recordId);


    @Query(value = "SELECT\n" +
            "    RO.OUTCOME_ID as outcomeId,\n" +
            "    DESIGNATION as designation,\n" +
            "    LPI as lpi,\n" +
            "    W.WORKFLOWID as workflowId,\n"+
            "    STATUS.CAPTION AS status,\n" +
            "    REASON.CAPTION as reason,\n" +
            "    RO.ISSUE_DATE as issueDate,\n" +
            "    RO.EXPIRY_DATE as expiryDate,\n" +
            "    trunc(RO.EXPIRY_DATE) - trunc(RO.ISSUE_DATE) as expiryInDays,\n" +
            "    W.REFERENCE_NO as referenceNumber,\n" +
            "    RD.NAME as name,\n" +
            "    L.CAPTION as provinceName,\n" +
            "    U.FirstName as firstName,\n" +
            "    U.SurName as surName\n" +
            "FROM RESERVATION_OUTCOME RO\n" +
            "         LEFT OUTER JOIN M_LIST_ITEM STATUS ON RO.STATUS_ITEM_ID = STATUS.ITEMID\n" +
            "         LEFT OUTER JOIN M_LIST_ITEM REASON ON RO.REASON_ITEM_ID = REASON.ITEMID\n" +
            "         LEFT OUTER JOIN WORKFLOW W ON RO.WORKFLOW_ID = W.WORKFLOWID\n" +
            "         INNER JOIN RES_DRAFT RD ON RO.DRAFT_ID = RD.DRAFT_ID\n" +
            "         LEFT OUTER JOIN USERS U ON  RO.OWNER_USERID = U.USERID\n" +
            "         LEFT OUTER JOIN LOCATION L ON RD.PROVINCE_ID = L.BOUNDARYID",
            nativeQuery = true,
            countQuery = "SELECT\n" +
                    "    count(*)\n" +
                    "FROM RESERVATION_OUTCOME RO\n" +
                    "         LEFT OUTER JOIN M_LIST_ITEM STATUS ON RO.STATUS_ITEM_ID = STATUS.ITEMID\n" +
                    "         LEFT OUTER JOIN M_LIST_ITEM REASON ON RO.REASON_ITEM_ID = REASON.ITEMID\n" +
                    "         LEFT OUTER JOIN WORKFLOW W ON RO.WORKFLOW_ID = W.WORKFLOWID\n" +
                    "         INNER JOIN RES_DRAFT RD ON RO.DRAFT_ID = RD.DRAFT_ID\n" +
                    "         LEFT OUTER JOIN USERS U ON  RO.OWNER_USERID = U.USERID\n" +
                    "         LEFT OUTER JOIN LOCATION L ON RD.PROVINCE_ID = L.BOUNDARYID")
    Page<ReservationTransferListProjection> loadReservationTransferListing(final Pageable pageable);

}
