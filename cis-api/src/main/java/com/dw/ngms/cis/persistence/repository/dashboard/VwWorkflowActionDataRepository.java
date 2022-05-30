package com.dw.ngms.cis.persistence.repository.dashboard;

import com.dw.ngms.cis.persistence.projection.dashboard.UserSummaryAlertDetails;
import com.dw.ngms.cis.persistence.projection.dashboard.UserSummaryDistributionByAction;
import com.dw.ngms.cis.persistence.projection.dashboard.UserSummaryMonthlyTaskDistribution;
import com.dw.ngms.cis.persistence.projection.dashboard.UserSummaryTaskList;
import com.dw.ngms.cis.persistence.views.VwWorkflowActionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 15/03/21, Mon
 **/
@Repository
public interface VwWorkflowActionDataRepository extends JpaRepository<VwWorkflowActionData, Long> {

    @Query(value = "Select count(wfa.WORKFLOWID)  from VW_WORKFLOW_ACTION_DATA wfa where   wfa.USERID=:userId " +
            "and wfa.POSTEDON between :fromDate and :toDate", nativeQuery = true)
    Long getTotalTask(final Long userId, final LocalDate fromDate, final LocalDate toDate);

    @Query(value = "Select count(wfa.WORKFLOWID) from VW_WORKFLOW_ACTION_DATA wfa where  wfa.actedOn is not null " +
            "and  (:userId is null or wfa.USERID = :userId) and wfa.POSTEDON between :fromDate and :toDate", nativeQuery = true)
    Long getClosedTask(final Long userId, final LocalDate fromDate, final LocalDate toDate);

    @Query(value = "Select avg(wfa.PRODUCTIVITY_MINUTES) from VW_WORKFLOW_ACTION_DATA wfa where  wfa.USERID=:userId " +
            "and wfa.POSTEDON between :fromDate and :toDate ", nativeQuery = true)
    Float getAverageTurnaroundDuration(final Long userId, final LocalDate fromDate, final LocalDate toDate);

    @Query(value = "Select  count(wfn.REFERENCE_NO) from VW_WF_NOTIFICATIONS wfn  WHERE  wfn.USERID = :userId " +
            "and wfn.DATERECEIVED between :fromDate and :toDate ", nativeQuery = true)
    Long getNoOfNotification(final Long userId, final LocalDate fromDate, final LocalDate toDate);

    @Query(value = "Select\n" +
            "to_char(vwwork.POSTEDON,'MON-yy') as month, count(1) as totalTask,\n" +
            "to_number(to_char(vwwork.POSTEDON,'mmyy'), '9999') as month1\n" +
            "from VW_WORKFLOW_ACTION_DATA vwwork\n" +
            "where (:userId is null or vwwork.USERID=:userId) and  vwwork.POSTEDON >= :fromDate and vwwork.POSTEDON <= :toDate\n" +
            "group by to_char(vwwork.POSTEDON,'MON-yy') , " +
            " to_number(to_char(vwwork.POSTEDON,'mmyy'), '9999') " +
            " order by to_number(to_char(vwwork.POSTEDON,'mmyy'), '9999')",
            nativeQuery = true)
    Collection<UserSummaryMonthlyTaskDistribution> getMonthlyTaskDistribution(final Long userId,
                                                                              final LocalDate fromDate,
                                                                              final LocalDate toDate);

    @Query(value = "Select\n" +
            "    vwwork.ACTION_REQUIRED as action,\n" +
            "    count(1) as totalTask\n" +
            "from VW_WORKFLOW_ACTION_DATA vwwork\n" +
            "where (:userId is null or vwwork.USERID=:userId) and  vwwork.POSTEDON between :fromDate and :toDate \n" +
            "group by vwwork.ACTION_REQUIRED", nativeQuery = true)
    Collection<UserSummaryDistributionByAction> getDistributionByAction(final Long userId,
                                                                        final LocalDate fromDate,
                                                                        final LocalDate toDate);


    @Query(value = "Select alrt.REFERENCE_NO as referenceNo,\n" +
            "       alrt.USER_NAME as userName,\n" +
            "       alrt.DATERECEIVED as dated,\n" +
            "       alrt.NOTIFICATION_TYPE as notificationType,\n" +
            "       alrt.INTERNALSTATUSCAPTION as status \n" +
            "from VW_WF_NOTIFICATIONS alrt where (:userId is null or alrt.USERID=:userId) \n" +
            "   and alrt.DATERECEIVED between :fromDate  and :toDate order by alrt.DATERECEIVED DESC ", nativeQuery = true)
    Collection<UserSummaryAlertDetails> getAllByUseridAndDateReceivedBetween(final Long userId,
                                                                             final LocalDate fromDate,
                                                                             final LocalDate toDate);


    @Query(value = "Select\n" +
            "    Action.ACTION_REQUIRED as context,\n" +
            "    Action.POSTEDON as postedOn,\n" +
            "    Action.FullName as fullName,\n" +
            "    Action.ActedON as actedOn,\n" +
            "    Action.PRODUCTIVITY_MINUTES as productivityMinutes,\n" +
            "    wf.REFERENCE_NO as referenceNo,\n" +
            "    wf.PROCESSNAME as processName,\n" +
            "    wf.INTERNALSTATUSCAPTION as internalStatusCaption,\n" +
            "    Action.WORKFLOWID as workflowId\n" +
            "from VW_WORKFLOW_ACTION_DATA  Action\n" +
            "    inner join VW_WORKFLOW_PRODUCTIVITY  wf on wf.WORKFLOWID = Action.WORKFLOWID\n" +
            "where (:userId is null or Action.USERID=:userId) and  Action.POSTEDON between :fromDate and :toDate order by Action.POSTEDON DESC",
            nativeQuery = true)
    Collection<UserSummaryTaskList> getTaskList(final Long userId,
                                                final LocalDate fromDate,
                                                final LocalDate toDate);


    @Query(value = "Select\n" +
            "    to_char(vwwork.DATERECEIVED,'MON-yy') as month,\n" +
            "    to_number(to_char(vwwork.DATERECEIVED,'mmyy'), '9999'),\n" +
            "    count(1) as totalTask\n" +
            "from\n" +
            "    VW_WORKFLOW_PRODUCTIVITY vwwork\n" +
            "where vwwork.PRODUCTIVITY_MINUTES <= (vwwork.TRUNAROUND_DURATION*8*60)\n" +
            "  and (:userId is null or vwwork.USERID=:userId)\n" +
            "   and DATERECEIVED between :fromDate and :toDate\n" +
            "group by to_char(vwwork.DATERECEIVED,'MON-yy')," +
            " to_number(to_char(vwwork.DATERECEIVED,'mmyy'), '9999')" +
            " order by to_number(to_char(vwwork.DATERECEIVED,'mmyy'), '9999')",
            nativeQuery = true)
    Collection<UserSummaryMonthlyTaskDistribution> getTasksBeforeTurnaroundTime(final Long userId,
                                                                                final LocalDate fromDate,
                                                                                final LocalDate toDate);


    @Query(value = "Select\n" +
            "    to_char(vwwork.DATERECEIVED,'MON-yy') as month,\n" +
            "    to_number(to_char(vwwork.DATERECEIVED,'mmyy'), '9999'),\n" +
            "    count(1) as totalTask\n" +
            "from\n" +
            "    VW_WORKFLOW_PRODUCTIVITY vwwork\n" +
            "where PRODUCTIVITY_MINUTES > (TRUNAROUND_DURATION*8*60)\n" +
            "  And (:userId is null or vwwork.USERID=:userId) \n" +
            "   and vwwork.DATERECEIVED between :fromDate and :toDate \n" +
            "group by  to_char(vwwork.DATERECEIVED,'MON-yy'), " +
            " to_number(to_char(vwwork.DATERECEIVED,'mmyy'), '9999') " +
            " order by to_number(to_char(vwwork.DATERECEIVED,'mmyy'), '9999') ",
            nativeQuery = true)
    Collection<UserSummaryMonthlyTaskDistribution> getTasksAfterTurnaroundTime(final Long userId,
                                                                               final LocalDate fromDate,
                                                                               final LocalDate toDate);


}
