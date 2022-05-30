package com.dw.ngms.cis.persistence.repository.workflow;

import com.dw.ngms.cis.persistence.domains.report.Productivity;
import com.dw.ngms.cis.persistence.domains.workflow.Workflow;
import com.dw.ngms.cis.persistence.projection.*;
import com.dw.ngms.cis.persistence.projection.reservation.ReservationDraftListingProjection;
import com.dw.ngms.cis.persistence.projection.workflow.WorkflowParcelProjection;
import com.dw.ngms.cis.persistence.projection.workflow.WorkflowProcessTimelineProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, Long> {

    Workflow findByWorkflowId(final Long workflowId);

    Workflow findByReferenceNo(final String referenceNo);


    @Query(value = "SELECT\n" +
            "        WF.PROCESSID as processId,\n" +
            "        WP.NAME as processName,\n" +
            "        WPM.DESCRIPTION AS actionRequiredDescription,\n" +
            "        WF.WORKFLOWID as workflowId,\n" +
            "        WF.REFERENCE_NO as referenceNo,\n" +
            "        WF.PROVINCEID as provinceId, \n" +
            "        WF.TRUNAROUND_DURATION as turnaroundDuration ,\n" +
            "        WA.ACTIONID as actionId,\n" +
            "        WA.ACTIONREQUIRED as actionRequired,\n" +
            "        WA.ALLOCATEDON as allocatedOn,\n" +
            "        CONCAT ( CONCAT(U.FIRSTNAME, ' ') ,  U.SURNAME )  AS assignedToUser,\n" +
            "        WA.DIARISEDATE as diariseDate,\n" +
            "        WF.INTERNAL_STATUS_ID as internalStatus,\n" +
            "        INT_STATUS.DESCRIPTION as internalStatusCaption,\n" +
            "        WF.EXTERNAL_STATUS_ID as externalStatus,\n" +
            "        EXT_STATUS.DESCRIPTION as externalStatusCaption,\n" +
            "        WF.PRIORITY_FLAG as priorityFlag,\n" +
            "        FLAG.DESCRIPTION as priorityName,\n" +
            "        WA.ESCALATION_TIME as escalationTime,\n" +
            "        WA.CONTEXT as actionContext,  \n" +
            "        WF.TRIGGEREDON as triggeredOn, \n " +
            "        WA.NODEID as nodeId, \n" +
            "        WA.USERID as userId "+
            "    FROM  WORKFLOW_ACTION WA \n" +
            "INNER JOIN WORKFLOW WF ON WA.WORKFLOWID = WF.WORKFLOWID\n" +
            "INNER JOIN WORKFLOWPROCESS WP  ON WF.PROCESSID = WP.PROCESSID\n" +
            "INNER JOIN USERS U ON WA.USERID = U.USERID\n" +
            "INNER JOIN WORKFLOWPROCESS_MATRIX  WPM ON WA.ACTIONREQUIRED = WPM.ID\n" +
            "LEFT JOIN WORKFLOWPROCESS_MATRIX  INT_STATUS  on WF.INTERNAL_STATUS_ID = INT_STATUS.ID\n" +
            "LEFT JOIN WORKFLOWPROCESS_MATRIX  EXT_STATUS on WF.EXTERNAL_STATUS_ID = EXT_STATUS.ID\n" +
            "LEFT JOIN WORKFLOWPROCESS_MATRIX  FLAG  on WF.PRIORITY_FLAG = FLAG.ID\n" +
            "      WHERE   (WA.ACTIONTAKEN IS NULL  and WF.STATUSID=1 )",
            countQuery = "SELECT count(*)\n" +
                    "    FROM  WORKFLOW_ACTION WA \n" +
                    "INNER JOIN WORKFLOW WF ON WA.WORKFLOWID = WF.WORKFLOWID\n" +
                    "INNER JOIN WORKFLOWPROCESS WFP ON WF.PROCESSID = WFP.PROCESSID\n" +
                    "INNER JOIN USERS  U ON WA.USERID = U.USERID\n" +
                    "INNER JOIN WORKFLOWPROCESS_MATRIX  WPM ON WA.ACTIONREQUIRED = WPM.ID\n" +
                    "LEFT JOIN WORKFLOWPROCESS_MATRIX INT_STATUS on WF.INTERNAL_STATUS_ID = INT_STATUS.ID\n" +
                    "LEFT JOIN WORKFLOWPROCESS_MATRIX EXT_STATUS on WF.EXTERNAL_STATUS_ID = EXT_STATUS.ID\n" +
                    "LEFT JOIN WORKFLOWPROCESS_MATRIX FLAG  on WF.PRIORITY_FLAG = FLAG.ID\n" +
                    "      WHERE   (WA.ACTIONTAKEN IS NULL  and WF.STATUSID=1)",
            nativeQuery = true)
    Page<InboxProjection> loadInboxForNationalAdmin(final Pageable pageable);


    @Query(value = "SELECT\n" +
            "        WF.PROCESSID as processId,\n" +
            "        WP.NAME as processName,\n" +
            "        WPM.DESCRIPTION AS actionRequiredDescription,\n" +
            "        WF.WORKFLOWID as workflowId,\n" +
            "        WF.REFERENCE_NO as referenceNo,\n" +
            "        WF.PROVINCEID as provinceId, \n" +
            "        WF.TRUNAROUND_DURATION as turnaroundDuration ,\n" +
            "        WA.ACTIONID as actionId,\n" +
            "        WA.ACTIONREQUIRED as actionRequired,\n" +
            "        WA.ALLOCATEDON as allocatedOn,\n" +
            "        CONCAT ( CONCAT(U.FIRSTNAME, ' ') ,  U.SURNAME )  AS assignedToUser,\n" +
            "        WA.DIARISEDATE as diariseDate,\n" +
            "        WF.INTERNAL_STATUS_ID as internalStatus,\n" +
            "        INT_STATUS.DESCRIPTION as internalStatusCaption,\n" +
            "        WF.EXTERNAL_STATUS_ID as externalStatus,\n" +
            "        EXT_STATUS.DESCRIPTION as externalStatusCaption,\n" +
            "        WF.PRIORITY_FLAG as priorityFlag,\n" +
            "        FLAG.DESCRIPTION as priorityName,\n" +
            "        WA.ESCALATION_TIME as escalationTime,\n" +
            "        WA.CONTEXT as actionContext,  \n" +
            "        WF.TRIGGEREDON as triggeredOn, \n " +
            "        WA.NODEID as nodeId, \n" +
            "        WA.USERID as userId "+
            "    FROM  WORKFLOW_ACTION WA \n" +
            "INNER JOIN WORKFLOW WF ON WA.WORKFLOWID = WF.WORKFLOWID\n" +
            "INNER JOIN WORKFLOWPROCESS WP  ON WF.PROCESSID = WP.PROCESSID\n" +
            "INNER JOIN USERS U ON WA.USERID = U.USERID\n" +
            "INNER JOIN WORKFLOWPROCESS_MATRIX  WPM ON WA.ACTIONREQUIRED = WPM.ID\n" +
            "LEFT JOIN WORKFLOWPROCESS_MATRIX  INT_STATUS  on WF.INTERNAL_STATUS_ID = INT_STATUS.ID\n" +
            "LEFT JOIN WORKFLOWPROCESS_MATRIX  EXT_STATUS on WF.EXTERNAL_STATUS_ID = EXT_STATUS.ID\n" +
            "LEFT JOIN WORKFLOWPROCESS_MATRIX  FLAG  on WF.PRIORITY_FLAG = FLAG.ID\n" +
            "      WHERE   (WA.ACTIONTAKEN IS NULL  and WF.STATUSID=1 ) AND WA.USERID = :userId \n" +
            "        OR  WF.PROVINCEID in :provinceIds ",
            countQuery = "SELECT count(*)\n" +
                    "    FROM  WORKFLOW_ACTION WA \n" +
                    "INNER JOIN WORKFLOW WF ON WA.WORKFLOWID = WF.WORKFLOWID\n" +
                    "INNER JOIN WORKFLOWPROCESS WFP ON WF.PROCESSID = WFP.PROCESSID\n" +
                    "INNER JOIN USERS  U ON WA.USERID = U.USERID\n" +
                    "INNER JOIN WORKFLOWPROCESS_MATRIX  WPM ON WA.ACTIONREQUIRED = WPM.ID\n" +
                    "LEFT JOIN WORKFLOWPROCESS_MATRIX INT_STATUS on WF.INTERNAL_STATUS_ID = INT_STATUS.ID\n" +
                    "LEFT JOIN WORKFLOWPROCESS_MATRIX EXT_STATUS on WF.EXTERNAL_STATUS_ID = EXT_STATUS.ID\n" +
                    "LEFT JOIN WORKFLOWPROCESS_MATRIX FLAG  on WF.PRIORITY_FLAG = FLAG.ID\n" +
                    "      WHERE   (WA.ACTIONTAKEN IS NULL  and WF.STATUSID=1) AND WA.USERID = :userId \n" +
                    "        OR  WF.PROVINCEID in :provinceIds ", nativeQuery = true)
    Page<InboxProjection> loadInboxForProvincialAdmin(final Long userId, final List<Long> provinceIds, final Pageable pageable);


    @Query(value = "SELECT WF.PROCESSID                                as processId,\n" +
            "       WP.NAME                                     as processName,\n" +
            "       WPM.DESCRIPTION                             AS actionRequiredDescription,\n" +
            "       WF.WORKFLOWID                               as workflowId,\n" +
            "       WF.REFERENCE_NO                             as referenceNo,\n" +
            "       WF.PROVINCEID                               as provinceId,\n" +
            "       WF.TRUNAROUND_DURATION                      as turnaroundDuration,\n" +
            "       WA.ACTIONID                                 as actionId,\n" +
            "       WA.ACTIONREQUIRED                           as actionRequired,\n" +
            "       WA.ALLOCATEDON                              as allocatedOn,\n" +
            "       CONCAT(CONCAT(U.FIRSTNAME, ' '), U.SURNAME) AS assignedToUser,\n" +
            "       WA.DIARISEDATE                              as diariseDate,\n" +
            "       WF.INTERNAL_STATUS_ID                       as internalStatus,\n" +
            "       INT_STATUS.DESCRIPTION                      as internalStatusCaption,\n" +
            "       WF.EXTERNAL_STATUS_ID                       as externalStatus,\n" +
            "       EXT_STATUS.DESCRIPTION                      as externalStatusCaption,\n" +
            "       WF.PRIORITY_FLAG                            as priorityFlag,\n" +
            "       FLAG.DESCRIPTION                            as priorityName,\n" +
            "       WA.ESCALATION_TIME                          as escalationTime,\n" +
            "       WA.CONTEXT                                  as actionContext,\n" +
            "       WF.TRIGGEREDON                              as triggeredOn,\n" +
            "       WA.NODEID                                   as nodeId,\n" +
            "       WA.USERID                                   as userId\n" +
            "FROM WORKFLOW_ACTION WA\n" +
            "         INNER JOIN WORKFLOW WF ON WA.WORKFLOWID = WF.WORKFLOWID\n" +
            "         INNER JOIN WORKFLOWPROCESS WP ON WF.PROCESSID = WP.PROCESSID\n" +
            "         INNER JOIN USERS U ON WA.USERID = U.USERID\n" +
            "         INNER JOIN WORKFLOWPROCESS_MATRIX WPM ON WA.ACTIONREQUIRED = WPM.ID\n" +
            "         LEFT JOIN WORKFLOWPROCESS_MATRIX INT_STATUS on WF.INTERNAL_STATUS_ID = INT_STATUS.ID\n" +
            "         LEFT JOIN WORKFLOWPROCESS_MATRIX EXT_STATUS on WF.EXTERNAL_STATUS_ID = EXT_STATUS.ID\n" +
            "         LEFT JOIN WORKFLOWPROCESS_MATRIX FLAG on WF.PRIORITY_FLAG = FLAG.ID\n" +
            "WHERE (WA.ACTIONTAKEN IS NULL and WF.STATUSID = 1)\n" +
            "  AND (WA.USERID = :userId\n" +
            "    OR\n" +
            "       WA.USERID in (\n" +
            "           SELECT USERID\n" +
            "           FROM vw_user_reporting_master_All\n" +
            "           where reporting_userid = :userId\n" +
            "       )\n" +
            "    OR WA.USERID in (\n" +
            "        Select USERID\n" +
            "        from USER_DELEGATIONS WFD\n" +
            "        where WFD.DELEGATE_USERID = :userId\n" +
            "          and WFD.STATUSITEMID = 108\n" +
            "          and SYSDATE >= WFD.FROM_DATE\n" +
            "          and SYSDATE < WFD.TO_DATE + 1\n" +
            "    )\n" +
            "    )",
            countQuery = "SELECT count(*)\n" +
                    "FROM WORKFLOW_ACTION WA\n" +
                    "         INNER JOIN WORKFLOW WF ON WA.WORKFLOWID = WF.WORKFLOWID\n" +
                    "         INNER JOIN WORKFLOWPROCESS WP ON WF.PROCESSID = WP.PROCESSID\n" +
                    "         INNER JOIN USERS U ON WA.USERID = U.USERID\n" +
                    "         INNER JOIN WORKFLOWPROCESS_MATRIX WPM ON WA.ACTIONREQUIRED = WPM.ID\n" +
                    "         LEFT JOIN WORKFLOWPROCESS_MATRIX INT_STATUS on WF.INTERNAL_STATUS_ID = INT_STATUS.ID\n" +
                    "         LEFT JOIN WORKFLOWPROCESS_MATRIX EXT_STATUS on WF.EXTERNAL_STATUS_ID = EXT_STATUS.ID\n" +
                    "         LEFT JOIN WORKFLOWPROCESS_MATRIX FLAG on WF.PRIORITY_FLAG = FLAG.ID\n" +
                    "WHERE (WA.ACTIONTAKEN IS NULL and WF.STATUSID = 1)\n" +
                    "  AND (WA.USERID = :userId\n" +
                    "    OR\n" +
                    "       WA.USERID in (\n" +
                    "           SELECT USERID\n" +
                    "           FROM vw_user_reporting_master_All\n" +
                    "           where reporting_userid = :userId\n" +
                    "       )\n" +
                    "    OR WA.USERID in (\n" +
                    "        Select USERID\n" +
                    "        from USER_DELEGATIONS WFD\n" +
                    "        where WFD.DELEGATE_USERID = :userId\n" +
                    "          and WFD.STATUSITEMID = 108\n" +
                    "          and SYSDATE >= WFD.FROM_DATE\n" +
                    "          and SYSDATE < WFD.TO_DATE + 1\n" +
                    "    )\n" +
                    "    )", nativeQuery = true)
    Page<InboxProjection> loadInboxByUsers(final Long userId, final Pageable pageable);


    @Query(value = "SELECT\n" +
            "        WF.PROCESSID as processId,\n" +
            "        WFA.USERID as userId,\n" +
            "        WFP.NAME as processName,\n" +
            "        WPM.DESCRIPTION AS actionRequiredDescription,\n" +
            "        WF.WORKFLOWID as workflowId,\n" +
            "        WF.REFERENCE_NO as referenceNo,\n" +
            "        WF.PROVINCEID as provinceId, \n" +
            "        WF.TRUNAROUND_DURATION as turnaroundDuration ,\n" +
            "        WFA.ACTIONID as actionId,\n" +
            "        WFA.ACTIONREQUIRED as ACTION_REQUIRED,\n" +
            "        WFA.ALLOCATEDON as allocatedOn,\n" +
            "         CONCAT ( CONCAT(U.FIRSTNAME, ' ') ,  U.SURNAME )  AS assignedToUser,\n" +
            "        WFA.DIARISEDATE as diariseDate,\n" +
            "        WF.INTERNAL_STATUS_ID as internalStatus,\n" +
            "        INT_STATUS.DESCRIPTION as internalStatusCaption,\n" +
            "        WF.EXTERNAL_STATUS_ID as externalStatus,\n" +
            "        EXT_STATUS.DESCRIPTION as externalStatusCaption,\n" +
            "        WF.PRIORITY_FLAG as priorityFlag,\n" +
            "        FLAG.DESCRIPTION as priorityName,\n" +
            "        WFA.ESCALATION_TIME as escalationTime,\n" +
            "        WFA.CONTEXT as actionContext,  \n" +
            "        WF.TRIGGEREDON as triggeredOn \n " +
            "    FROM  WORKFLOW_ACTION WFA \n" +
            "INNER JOIN WORKFLOW WF ON WFA.WORKFLOWID = WF.WORKFLOWID\n" +
            "INNER JOIN WORKFLOWPROCESS WFP  ON WF.PROCESSID = WFP.PROCESSID\n" +
            "INNER JOIN USERS U ON WFA.USERID = U.USERID\n" +
            "INNER JOIN WORKFLOWPROCESS_MATRIX WPM ON WFA.ACTIONREQUIRED = WPM.ID\n" +
            "LEFT JOIN WORKFLOWPROCESS_MATRIX INT_STATUS on WF.INTERNAL_STATUS_ID = INT_STATUS.ID\n" +
            "LEFT JOIN WORKFLOWPROCESS_MATRIX EXT_STATUS on WF.EXTERNAL_STATUS_ID = EXT_STATUS.ID\n" +
            "LEFT JOIN WORKFLOWPROCESS_MATRIX FLAG  on WF.PRIORITY_FLAG = FLAG.ID\n" +
            "      WHERE   (WFA.ACTIONTAKEN IS NULL  and WF.STATUSID=1)\n" +
            "        AND (\n" +
            "            WFA.USERID = :userId \n" +
            "            )",
            countQuery = "SELECT count(*)\n" +
                    "    FROM  WORKFLOW_ACTION WFA \n" +
                    "INNER JOIN WORKFLOW WF ON WFA.WORKFLOWID = WF.WORKFLOWID\n" +
                    "INNER JOIN WORKFLOWPROCESS WFP  ON WF.PROCESSID = WFP.PROCESSID\n" +
                    "INNER JOIN USERS U ON WFA.USERID = U.USERID\n" +
                    "INNER JOIN WORKFLOWPROCESS_MATRIX WPM ON WFA.ACTIONREQUIRED = WPM.ID\n" +
                    "LEFT JOIN WORKFLOWPROCESS_MATRIX INT_STATUS on WF.INTERNAL_STATUS_ID = INT_STATUS.ID\n" +
                    "LEFT JOIN WORKFLOWPROCESS_MATRIX EXT_STATUS on WF.EXTERNAL_STATUS_ID = EXT_STATUS.ID\n" +
                    "LEFT JOIN WORKFLOWPROCESS_MATRIX FLAG  on WF.PRIORITY_FLAG = FLAG.ID\n" +
                    "      WHERE   (WFA.ACTIONTAKEN IS NULL  and WF.STATUSID=1)\n" +
                    "        AND (\n" +
                    "            WFA.USERID = :userId \n" +
                    "            )", nativeQuery = true)
    Page<InboxProjection> loadWorkflowForReassign(final Long userId, final Pageable pageable);


    @Query(value = "SELECT\n" +
            "    WF.PROCESSID as processId,\n" +
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
            "    INT_W_ACTIONREQUIRED.DESCRIPTION as actionRequiredCaption\n" +
            "FROM WORKFLOW WF\n" +
            "         INNER JOIN TRANSACTIONS TRN on WF.TRANSACTIONID = TRN.TRANSACTIONID\n" +
            "         INNER JOIN WORKFLOWPROCESS WFP ON WF.PROCESSID = WFP.PROCESSID\n" +
            "         INNER JOIN USERS U on U.USERID = TRN.USERID\n" +
            "         INNER JOIN WORKFLOWPROCESS_MATRIX INT_STATUS on WF.INTERNAL_STATUS_ID = INT_STATUS.ID\n" +
            "         INNER JOIN WORKFLOWPROCESS_MATRIX EXT_STATUS on WF.EXTERNAL_STATUS_ID = EXT_STATUS.ID\n" +
            "         left JOIN WORKFLOWPROCESS_MATRIX M_PRIORITY on WF.PRIORITY_FLAG = M_PRIORITY.ID\n" +
            "         left JOIN WORKFLOW_ACTION W_ACTION on WF.WORKFLOWID = W_ACTION.WORKFLOWID and W_ACTION.ACTIONTAKEN is null\n" +
            "         left JOIN WORKFLOWPROCESS_MATRIX INT_W_ACTIONREQUIRED on W_ACTION.ActionRequired = INT_W_ACTIONREQUIRED.ID\n" +
            "WHERE   TRN.USERID =:userId and WFP.NAME <> 'Query'",
            countQuery = "SELECT\n" +
                    "    count(*)\n" +
                    "    FROM WORKFLOW WF\n" +
                    "         INNER JOIN TRANSACTIONS TRN on WF.TRANSACTIONID = TRN.TRANSACTIONID\n" +
                    "         INNER JOIN WORKFLOWPROCESS WFP ON WF.PROCESSID = WFP.PROCESSID\n" +
                    "         INNER JOIN USERS U on U.USERID = TRN.USERID\n" +
                    "         INNER JOIN WORKFLOWPROCESS_MATRIX INT_STATUS on WF.INTERNAL_STATUS_ID = INT_STATUS.ID\n" +
                    "         INNER JOIN WORKFLOWPROCESS_MATRIX EXT_STATUS on WF.EXTERNAL_STATUS_ID = EXT_STATUS.ID\n" +
                    "         left JOIN WORKFLOWPROCESS_MATRIX M_PRIORITY on WF.PRIORITY_FLAG = M_PRIORITY.ID\n" +
                    "         left JOIN WORKFLOW_ACTION W_ACTION on WF.WORKFLOWID = W_ACTION.WORKFLOWID and W_ACTION.ACTIONTAKEN is null\n" +
                    "         left JOIN WORKFLOWPROCESS_MATRIX INT_W_ACTIONREQUIRED on W_ACTION.ActionRequired = INT_W_ACTIONREQUIRED.ID\n" +
                    "WHERE   TRN.USERID =:userId and WFP.NAME <> 'Query'", nativeQuery = true)
    Page<DashboardRequestProjection> loadRequestForUser(final Long userId, final Pageable pageable);


    @Query(value = "SELECT\n" +
            "    WF.PROCESSID as processId,\n" +
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
            "    TRN.TRANSACTIONID\n" +
            "FROM WORKFLOW WF\n" +
            "     INNER JOIN TRANSACTIONS TRN on WF.TRANSACTIONID = TRN.TRANSACTIONID\n" +
            "     INNER JOIN WORKFLOWPROCESS WFP ON WF.PROCESSID = WFP.PROCESSID\n" +
            "     INNER JOIN USERS U on U.USERID = TRN.USERID\n" +
            "     INNER JOIN WORKFLOWPROCESS_MATRIX INT_STATUS on WF.INTERNAL_STATUS_ID = INT_STATUS.ID\n" +
            "     INNER JOIN WORKFLOWPROCESS_MATRIX EXT_STATUS on WF.EXTERNAL_STATUS_ID = EXT_STATUS.ID\n" +
            "     left JOIN WORKFLOWPROCESS_MATRIX M_PRIORITY on WF.PRIORITY_FLAG = M_PRIORITY.ID\n" +
            "WHERE   TRN.USERID =:userId and WFP.NAME = 'Query' ",
            countQuery = "SELECT count(*)\n" +
                    "FROM WORKFLOW WF\n" +
                    "     INNER JOIN TRANSACTIONS TRN on WF.TRANSACTIONID = TRN.TRANSACTIONID\n" +
                    "     INNER JOIN WORKFLOWPROCESS WFP ON WF.PROCESSID = WFP.PROCESSID\n" +
                    "     INNER JOIN USERS U on U.USERID = TRN.USERID\n" +
                    "     INNER JOIN WORKFLOWPROCESS_MATRIX INT_STATUS on WF.INTERNAL_STATUS_ID = INT_STATUS.ID\n" +
                    "     INNER JOIN WORKFLOWPROCESS_MATRIX EXT_STATUS on WF.EXTERNAL_STATUS_ID = EXT_STATUS.ID\n" +
                    "     left JOIN WORKFLOWPROCESS_MATRIX M_PRIORITY on WF.PRIORITY_FLAG = M_PRIORITY.ID\n" +
                    "WHERE   TRN.USERID =:userId and WFP.NAME = 'Query' ",
            nativeQuery = true)
    Page<DashboardRequestProjection> loadQueryForUser(final Long userId, final Pageable pageable);





    @Query(value = "SELECT\n" +
            "    WF.PROCESSID as processId,\n" +
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
            "    TRN.TRANSACTIONID\n" +
            "FROM WORKFLOW WF\n" +
            "     INNER JOIN TRANSACTIONS TRN on WF.TRANSACTIONID = TRN.TRANSACTIONID\n" +
            "     INNER JOIN WORKFLOWPROCESS WFP ON WF.PROCESSID = WFP.PROCESSID\n" +
            "     INNER JOIN USERS U on U.USERID = TRN.USERID\n" +
            "     INNER JOIN WORKFLOWPROCESS_MATRIX INT_STATUS on WF.INTERNAL_STATUS_ID = INT_STATUS.ID\n" +
            "     INNER JOIN WORKFLOWPROCESS_MATRIX EXT_STATUS on WF.EXTERNAL_STATUS_ID = EXT_STATUS.ID\n" +
            "     left JOIN WORKFLOWPROCESS_MATRIX M_PRIORITY on WF.PRIORITY_FLAG = M_PRIORITY.ID\n" +
            "WHERE   TRN.USERID =:userId and WF.REFERENCE_NO = :referenceNo", nativeQuery = true)
    DashboardRequestProjection searchByReferenceNoAndUserId(final Long userId, final String referenceNo);


    @Query(value = "SELECT\n" +
            "    WF.PROCESSID as processId,\n" +
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
            "    FLAG.DESCRIPTION as priorityName,\n" +
            "    WF.TRIGGEREDON as triggeredOn,\n" +
            "    U.USERID as userId\n" +
            "FROM WORKFLOW WF\n" +
            "     INNER JOIN WORKFLOWPROCESS WFP ON WF.PROCESSID = WFP.PROCESSID\n" +
            "     INNER JOIN TRANSACTIONS TRN ON WF.TRANSACTIONID = TRN.TRANSACTIONID\n" +
            "     INNER JOIN USERS U ON TRN.USERID = U.USERID\n" +
            "     INNER JOIN  WORKFLOWPROCESS_MATRIX INT_STATUS on WF.INTERNAL_STATUS_ID = INT_STATUS.ID\n" +
            "     INNER JOIN WORKFLOWPROCESS_MATRIX EXT_STATUS on WF.EXTERNAL_STATUS_ID = EXT_STATUS.ID\n" +
            "     LEFT JOIN WORKFLOWPROCESS_MATRIX FLAG  on WF.PRIORITY_FLAG = FLAG.ID\n" +
            "WHERE WF.REFERENCE_NO=:referenceNo", nativeQuery = true)
    DashboardRequestProjection findUsingReferenceNumber(final String referenceNo);

    @Query(value = "SELECT\n" +
            "    WORKFLOW.PROCESSID as processId,\n" +
            "    WorkflowProcess.NAME as processName,\n" +
            "    WORKFLOW.WORKFLOWID as workflowId,\n" +
            "    WORKFLOW.REFERENCE_NO as referenceNo,\n" +
            "    WORKFLOW.TRUNAROUND_DURATION as turnaroundDuration,\n" +
            "    CONCAT ( CONCAT(USERS.FIRSTNAME, ' ') ,  USERS.SURNAME )  AS initiatedUser,\n" +
            "    WORKFLOW.INTERNAL_STATUS_ID as internalStatus,\n" +
            "    INT_STATUS.DESCRIPTION as internalStatusCaption,\n" +
            "    WORKFLOW.EXTERNAL_STATUS_ID as externalStatus,\n" +
            "    EXT_STATUS.DESCRIPTION as externalStatusCaption,\n" +
            "    WORKFLOW.PRIORITY_FLAG as priorityFlag,\n" +
            "    FLAG.DESCRIPTION as priorityName,\n" +
            "    WORKFLOW.TRIGGEREDON as triggeredOn\n" +
            "FROM WORKFLOW\n" +
            "    INNER JOIN WORKFLOWPROCESS ON WORKFLOW.PROCESSID = WORKFLOWPROCESS.PROCESSID\n" +
            "    INNER JOIN TRANSACTIONS ON WORKFLOW.TRANSACTIONID = TRANSACTIONS.TRANSACTIONID\n" +
            "    INNER JOIN USERS ON TRANSACTIONS.USERID = USERS.USERID\n" +
            "    INNER JOIN WORKFLOWPROCESS_MATRIX INT_STATUS on WORKFLOW.INTERNAL_STATUS_ID = INT_STATUS.ID\n" +
            "    INNER JOIN WORKFLOWPROCESS_MATRIX EXT_STATUS on WORKFLOW.EXTERNAL_STATUS_ID = EXT_STATUS.ID\n" +
            "    INNER JOIN WORKFLOWPROCESS_MATRIX FLAG  on WORKFLOW.PRIORITY_FLAG = FLAG.ID\n" +
            "WHERE WORKFLOW.WORKFLOWID = :workflowId", nativeQuery = true)
    WorkflowProjection loadWorkflow(final Long workflowId);

    @Query(value = "SELECT\n" +
            "    WFA.ACTIONID as actionId,\n" +
            "    WFA.TRANSACTIONID as transactionId,\n" +
            "    WFA.USERID as userId,\n" +
            "    WFA.POSTEDON as postedOn,\n" +
            "    WFA.NODEID as noteId,\n" +
            "    WFA.ACTIONREQUIRED as actionRequired,\n" +
            "    WFA.ACTEDON as actedOn,\n" +
            "    WFA.ACTIONTAKEN as actionTakenId,\n" +
            "    WFA.ACTIONTRANSACTIONID as actionTransactionId,\n" +
            "    CONCAT ( CONCAT(USERS.FIRSTNAME, ' ') ,  USERS.SURNAME ) as actionTakenBy,\n" +
            "    WPMX.DESCRIPTION AS actionReq ,\n" +
            "    WPM.DESCRIPTION AS actionTaken,\n" +
            "    WFA.NOTE AS note\n" +
            "FROM  WORKFLOW_ACTION WFA \n" +
            "    INNER JOIN USERS  ON WFA.USERID = USERS.USERID\n" +
            "    INNER JOIN WORKFLOWPROCESS_MATRIX WPMX ON WFA.ACTIONREQUIRED = WPMX.ID\n" +
            "    LEFT  JOIN WORKFLOWPROCESS_MATRIX  WPM  ON WFA.ACTIONTAKEN = WPM.ID\n" +
            "WHERE WORKFLOWID = :workflowId",
            countQuery = "SELECT count(*)\n" +
                    "FROM  WORKFLOW_ACTION WFA \n" +
                    "    INNER JOIN USERS  ON WFA.USERID = USERS.USERID\n" +
                    "    INNER JOIN WORKFLOWPROCESS_MATRIX WPMX ON WFA.ACTIONREQUIRED = WPMX.ID\n" +
                    "    LEFT  JOIN WORKFLOWPROCESS_MATRIX  WPM  ON WFA.ACTIONTAKEN = WPM.ID\n" +
                    "WHERE WORKFLOWID = :workflowId",
            nativeQuery = true)
    Page<WorkflowTasksProjection> getTasksForWorkflow(final Long workflowId, final Pageable pageable);


    @Query(value = "select\n" +
            "       WFD.WORKFLOWID as workflowId,\n" +
            "       WFD.DOCUMENTID as documentId, \n " +
            "       WFD.DOCNAME as documentName,\n" +
            "       WFD.UPLODEDON as uploadedOn,\n" +
            "       WFD.USERID as userId,\n" +
            "       WFD.DOCUMENTTYPEID as documentTypeId,\n" +
            "       MLI.CAPTION as documentType," +
            "       WFD.NOTES as notes,\n" +
            "       WFD.EXTENSION as extension,\n" +
            "       WFD.SIZE_KB as sizeKb,\n" +
            "       WFD.UPLOAD_REF_ID as uploadRefId,\n" +
            "       CONCAT ( CONCAT(URS.FIRSTNAME, ' ') ,  URS.SURNAME ) as uploadedBy\n" +
            "from WORKFLOW_DOCUMENTS WFD \n" +
            "    INNER JOIN USERS URS on URS.USERID = WFD.USERID\n" +
            " INNER JOIN M_LIST_ITEM MLI on WFD.DOCUMENTTYPEID = MLI.ITEMID\n" +
            "where WFD.WORKFLOWID = :workflowId",
            countQuery = "select count(*) \n" +
                    "from WORKFLOW_DOCUMENTS WFD \n" +
                    "    INNER JOIN USERS URS on URS.USERID = WFD.USERID\n" +
                    " INNER JOIN M_LIST_ITEM MLI on WFD.DOCUMENTTYPEID = MLI.ITEMID\n" +
                    "where WORKFLOWID = :workflowId",
            nativeQuery = true)
    Page<WorkflowDocuments> getDocumentsForWorkflow(final Long workflowId, final Pageable pageable);

    @Query(value = "SELECT WF.PROCESSID                                                                           as processId,\n" +
            "       WFP.NAME                                                                                      as processName,\n" +
            "       WF.WORKFLOWID                                                                                 as workflowId,\n" +
            "       WF.REFERENCE_NO                                                                               as referenceNo,\n" +
            "       WF.TRIGGEREDON                                                                                as triggeredOn,\n" +
            "       CONCAT(CONCAT(U.FIRSTNAME, ' '), U.SURNAME)                                                   AS fromUser,\n" +
            "       TRN.COMMENTS                                                                                  as requestNote,\n" +
            "       CONCAT(CONCAT(USER_ACTION.FIRSTNAME, ' '), USER_ACTION.SURNAME)                               AS toUser,\n" +
            "       WF.COMPLETEDON                                                                                as createdOn,\n" +
            "       (Select COMMENTS from TRANSACTIONS trans where trans.TRANSACTIONID = WFA.ACTIONTRANSACTIONID) as referralInput,\n" +
            "       matr.DESCRIPTION                                                                              as status\n" +
            "FROM WORKFLOW WF\n" +
            "         INNER JOIN WORKFLOWPROCESS WFP ON WF.PROCESSID = WFP.PROCESSID\n" +
            "         INNER JOIN TRANSACTIONS TRN on WF.TRANSACTIONID = TRN.TRANSACTIONID\n" +
            "         INNER JOIN USERS U on U.USERID = TRN.USERID\n" +
            "         INNER JOIN WORKFLOW_ACTION WFA ON WFA.WORKFLOWID = WF.WORKFLOWID\n" +
            "         INNER JOIN USERS USER_ACTION ON USER_ACTION.USERID = WFA.USERID\n" +
            "         left JOIN WORKFLOWPROCESS_MATRIX matr on WF.INTERNAL_STATUS_ID = matr.ID\n" +
            "WHERE WF.PARENT_WORKFLOWID = :workflowId",
            countQuery = "SELECT count(*)\n" +
                    "FROM WORKFLOW WF\n" +
                    "         INNER JOIN WORKFLOWPROCESS WFP ON WF.PROCESSID = WFP.PROCESSID\n" +
                    "         INNER JOIN TRANSACTIONS TRN on WF.TRANSACTIONID = TRN.TRANSACTIONID\n" +
                    "         INNER JOIN USERS U on U.USERID = TRN.USERID\n" +
                    "         INNER JOIN WORKFLOW_ACTION WFA ON WFA.WORKFLOWID = WF.WORKFLOWID\n" +
                    "         INNER JOIN USERS USER_ACTION ON USER_ACTION.USERID = WFA.USERID\n" +
                    "         left JOIN WORKFLOWPROCESS_MATRIX matr on WF.INTERNAL_STATUS_ID = matr.ID\n" +
                    "WHERE WF.PARENT_WORKFLOWID = :workflowId",
            nativeQuery = true)
    Page<ReferralProjection> getReferralInput(final Long workflowId, final Pageable pageable);


    @Modifying
    @Query(value = "UPDATE WORKFLOW W SET W.INTERNAL_STATUS_ID = 14 , W.EXTERNAL_STATUS_ID=14 " +
            "WHERE W.WORKFLOWID = :workflowId",
            nativeQuery = true)
    int workflowMarkPending(final Long workflowId);


    @Query(value = "SELECT new com.dw.ngms.cis.persistence.domains.report.Productivity( \n" +
            "      wf.provinceId,\n" +
            "      wfa.actionId,\n" +
            "      wfa.postedOn,\n" +
            "      wfa .actedOn,\n" +
            "      wfa.userId)\n" +
            " FROM Workflow wf\n" +
            "    INNER JOIN WorkflowAction wfa on wfa.workflowId = wf.workflowId\n" +
            "ORDER BY wfa.actionId DESC")
    Collection<Productivity> getProductivityData();


    @Query(value = "SELECT new com.dw.ngms.cis.persistence.domains.report.Productivity( \n" +
            "      wf.provinceId,\n" +
            "      wfa.actionId,\n" +
            "      wfa.postedOn,\n" +
            "      wfa .actedOn,\n" +
            "      wfa.userId)\n" +
            " FROM Workflow wf\n" +
            "    INNER JOIN WorkflowAction wfa on wfa.workflowId = wf.workflowId\n" +
            "    WHERE wf.workflowId = :workflowId " +
            "ORDER BY wfa.actionId DESC")
    Collection<Productivity> getProductivityDataByWorkflowId(final Long workflowId);

    @Query(value = "SELECT\n" +
            "    WF.PROCESSID as processId,\n" +
            "    WP.NAME as processName,\n" +
            "    WPM.DESCRIPTION AS actionRequiredDescription,\n" +
            "    WF.WORKFLOWID as workflowId,\n" +
            "    WF.REFERENCE_NO as referenceNo,\n" +
            "    WF.PROVINCEID as provinceId,\n" +
            "    WF.TRUNAROUND_DURATION as turnaroundDuration ,\n" +
            "    WA.ACTIONID as actionId,\n" +
            "    WA.ACTIONREQUIRED as actionRequired,\n" +
            "    WA.ALLOCATEDON as allocatedOn,\n" +
            "    CONCAT ( CONCAT(U.FIRSTNAME, ' ') ,  U.SURNAME )  AS assignedToUser,\n" +
            "    WA.DIARISEDATE as diariseDate,\n" +
            "    WF.INTERNAL_STATUS_ID as internalStatus,\n" +
            "    INT_STATUS.DESCRIPTION as internalStatusCaption,\n" +
            "    WF.EXTERNAL_STATUS_ID as externalStatus,\n" +
            "    EXT_STATUS.DESCRIPTION as externalStatusCaption,\n" +
            "    WF.PRIORITY_FLAG as priorityFlag,\n" +
            "    FLAG.DESCRIPTION as priorityName,\n" +
            "    WA.ESCALATION_TIME as escalationTime,\n" +
            "    WA.CONTEXT as actionContext,\n" +
            "    WF.TRIGGEREDON as triggeredOn,\n" +
            "    WA.NODEID as nodeId\n" +
            "FROM  WORKFLOW_ACTION WA\n" +
            "          INNER JOIN WORKFLOW WF ON WA.WORKFLOWID = WF.WORKFLOWID\n" +
            "          INNER JOIN WORKFLOWPROCESS WP  ON WF.PROCESSID = WP.PROCESSID\n" +
            "          INNER JOIN USERS U ON WA.USERID = U.USERID\n" +
            "          INNER JOIN WORKFLOWPROCESS_MATRIX  WPM ON WA.ACTIONREQUIRED = WPM.ID\n" +
            "          LEFT JOIN WORKFLOWPROCESS_MATRIX  INT_STATUS  on WF.INTERNAL_STATUS_ID = INT_STATUS.ID\n" +
            "          LEFT JOIN WORKFLOWPROCESS_MATRIX  EXT_STATUS on WF.EXTERNAL_STATUS_ID = EXT_STATUS.ID\n" +
            "          LEFT JOIN WORKFLOWPROCESS_MATRIX  FLAG  on WF.PRIORITY_FLAG = FLAG.ID\n" +
            "where WA.ACTIONTAKEN IS NULL  and WF.STATUSID=1 and wf.WORKFLOWID  in(\n" +
            "    Select wfParent.WORKFLOWID from WORKFLOW wf " +
            " inner join WORKFLOW wfParent on  wf.PARENT_WORKFLOWID = wfParent.WORKFLOWID " +
            " where wf.WORKFLOWID = :workflowId ) ", nativeQuery = true)
    InboxProjection getReferralInputData(final Long workflowId);

    @Query(value = "SELECT RECORD_ID as RecordId,\n" +
            "      REFERENCE_NO as ReferenceNo,\n" +
            "      STATUS as Status,\n" +
            "      PROCESS_NAME as ProcessName,\n" +
            "      PROCESSID as ProcessId,\n" +
            "      WORKFLOW_ID as WorkflowId,\n" +
            "      COMPLETEDON as CompletedOn,\n" +
            "      TRIGGEREDON as TriggeredOn\n" +
            "      FROM vw_wh_parcel_workflows where RECORD_ID = :RecordId", nativeQuery = true)

    Collection<WorkflowParcelProjection> findbyRecordID(final Long RecordId);


    @Query(value = "SELECT \n" +
            "    triggeredon as triggeredOn,\n" +
            "    completedon as completedOn,\n" +
            "    workflow_id as workflowId,\n" +
            "    processid as processId,\n" +
            "    process_name as processName,\n" +
            "    status as status,\n" +
            "    reference_no as referenceNo,\n" +
            "    record_id as recordId,\n" +
            "    summary as summary\n" +
            "FROM \n" +
            "    vw_wh_parcel_workflows where workflow_id=:workflowId", nativeQuery = true)
    Collection<WorkflowProcessTimelineProjection> getWorkflowProcessTimeline(final Long workflowId);
}
