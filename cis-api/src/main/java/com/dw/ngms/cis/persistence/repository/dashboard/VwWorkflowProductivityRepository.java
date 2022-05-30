package com.dw.ngms.cis.persistence.repository.dashboard;

import com.dw.ngms.cis.persistence.projection.dashboard.*;
import com.dw.ngms.cis.persistence.views.VwWorkflowProductivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 12/03/21, Fri
 **/
public interface VwWorkflowProductivityRepository extends JpaRepository<VwWorkflowProductivity, Long> {

    @Query("SELECT DISTINCT wfp.processId as processId, wfp.processName as processName " +
            "from VwWorkflowProductivity wfp order by wfp.processName")
    Collection<ProcessProjection> getAllProcess();

    @Query(value = "Select count(wfp.WORKFLOWID) from VW_WORKFLOW_PRODUCTIVITY wfp where wfp.INTERNALSTATUS in(21) " +
            "and wfp.DATA_PROVINCE in (:provinces) and wfp.DATERECEIVED between  :fromDate and :toDate", nativeQuery = true)
    Long getDispatchedRequestCount(final Collection<Integer> provinces, final LocalDate fromDate, final LocalDate toDate);

    @Query(value = "Select sum(wfp.PAYMENT_AMOUNT)  from VW_WORKFLOW_PRODUCTIVITY wfp where wfp.PROCESSID in (1)" +
            "and wfp.DATA_PROVINCE in (:provinces) and wfp.DATERECEIVED between  :fromDate and :toDate", nativeQuery = true)
    Long getPaymentAmount(final Collection<Integer> provinces, final LocalDate fromDate, final LocalDate toDate);

    @Query(value = "Select sum(wfp.INVOICE_AMOUNT) from VW_WORKFLOW_PRODUCTIVITY wfp where wfp.PROCESSID in (1) " +
            "and  wfp.DATA_PROVINCE in (:provinces) and wfp.DATERECEIVED between  :fromDate and :toDate", nativeQuery = true)
    Long getInvoiceAmount(final Collection<Integer> provinces, final LocalDate fromDate, final LocalDate toDate);


    @Query(value = "Select count(wfp.WORKFLOWID) from VW_WORKFLOW_PRODUCTIVITY wfp where wfp.PROCESSID in (1)" +
            "and  wfp.DATA_PROVINCE in (:provinces) and wfp.DATERECEIVED between  :fromDate and :toDate", nativeQuery = true)
    Long getTotalTaskInProgress(final Collection<Integer> provinces, final LocalDate fromDate, final LocalDate toDate);


    @Query(value = "Select count(wfp.WORKFLOWID) from VW_WORKFLOW_PRODUCTIVITY wfp where wfp.PROCESSID in (:processId)" +
            "and  wfp.DATA_PROVINCE in (:provinceIds) and wfp.DATERECEIVED between  :fromDate and :toDate", nativeQuery = true)
    Long getTotalTaskInProgress(final Collection<Integer> provinceIds,
                                final LocalDate fromDate,
                                final LocalDate toDate,
                                final Long processId);

    @Query(value = "Select\n" +
            "    count(PROCESSID)\n" +
            "from\n" +
            "    VW_WORKFLOW_PRODUCTIVITY vwwork\n" +
            "where vwwork.COMPLETEDON is not null" +
            "  and  vwwork.DATA_PROVINCE IN (:provinceIds) " +
            " and vwwork.DATERECEIVED between  :fromDate and :toDate \n" +
            " and vwwork.PROCESSID in (:processId)", nativeQuery = true)
    Long getClosedTask(final Collection<Integer> provinceIds,
                       final LocalDate fromDate,
                       final LocalDate toDate,
                       final Long processId);

    @Query(value = "Select\n" +
            "    avg(PRODUCTIVITY_MINUTES)\n" +
            "from\n" +
            "    VW_WORKFLOW_PRODUCTIVITY vwwork where processId in (:processId) " +
            "  and vwwork.DATA_PROVINCE IN (:provinceIds) " +
            " and vwwork.DATERECEIVED between  :fromDate and :toDate \n",
            nativeQuery = true)
    Float getAverageTurnaroundDuration(final Collection<Integer> provinceIds,
                                       final LocalDate fromDate,
                                       final LocalDate toDate,
                                       final Long processId);


    @Query(value = "Select  count(1)  from VW_WF_NOTIFICATIONS alrt where alrt.DATERECEIVED between  :fromDate and :toDate " +
            " and alrt.PROCESSID in (:processId)" +
            "and alrt.DATA_PROVINCE in (:provinceIds)", nativeQuery = true)
    Long getNotificationTotalCount(final Collection<Integer> provinceIds,
                                   final LocalDate fromDate,
                                   final LocalDate toDate,
                                   final Long processId);

    @Query(value = "Select REQUESTOR_ROLE as role,\n" +
            "       count(1) as noOfRequest \n" +
            "from VW_WORKFLOW_PRODUCTIVITY " +
            "  where  DATA_PROVINCE IN (:provinceIds) " +
            " and DATERECEIVED between  :fromDate and :toDate \n" +
            "and REQUESTOR_ROLE is not null and  processId in (:processId) " +
            "group by REQUESTOR_ROLE ", nativeQuery = true)
    Collection<ProcessSummaryTaskDistributionByRole> getTaskDistributionByRole(final Collection<Integer> provinceIds,
                                                                               final LocalDate fromDate,
                                                                               final LocalDate toDate,
                                                                               final Long processId);


    @Query(value = "Select wfp.REQUESTOR as requestor,\n" +
            "  count(1) as totalTasks\n" +
            "from VW_WORKFLOW_PRODUCTIVITY wfp\n" +
            " where wfp.PROCESSID in (:processId)\n" +
            " and wfp.DATERECEIVED between :fromDate and :toDate" +
            " and wfp.DATA_PROVINCE in (:provinceIds)" +
            "group by Requestor order by 2 desc", nativeQuery = true)
    Collection<ProcessSummaryTaskDistributionByUser> processSummaryGetTaskDistributionByUser(final Collection<Integer> provinceIds,
                                                                                             final LocalDate fromDate,
                                                                                             final LocalDate toDate,
                                                                                             final Long processId);

    @Query(value = "Select\n" +
            "    to_char(vwwork.DATERECEIVED,'MON-yy') as month,\n" +
            "    to_number(to_char(vwwork.DATERECEIVED,'mmyy'), '9999') as month1,\n" +
            "    count(1) as totalTask\n" +
            "from VW_WORKFLOW_PRODUCTIVITY vwwork\n" +
            " where processId in (:processId)\n" +
            " and vwwork.DATERECEIVED between :fromDate and :toDate" +
            " and vwwork.DATA_PROVINCE in (:provinceIds)" +
            "group by to_char(vwwork.DATERECEIVED,'MON-yy')," +
            " to_number(to_char(vwwork.DATERECEIVED,'mmyy'),'9999') " +
            " order by to_number(to_char(vwwork.DATERECEIVED,'mmyy'), '9999') ",
            nativeQuery = true)
    Collection<ProcessSummaryTaskDistributionByMonth> processSummaryGetTaskDistributionByMonth(final Collection<Integer> provinceIds,
                                                                                               final LocalDate fromDate,
                                                                                               final LocalDate toDate,
                                                                                               final Long processId);


    @Query(value = "Select\n" +
            "    to_char(vwwork.DATERECEIVED,'MON-yy') as month,\n" +
            "    to_number(to_char(vwwork.DATERECEIVED,'mmyy'), '9999') as month1,\n" +
            "    count(1) as totalTask\n" +
            "from\n" +
            "    VW_WORKFLOW_PRODUCTIVITY vwwork\n" +
            "where PRODUCTIVITY_MINUTES <= (TRUNAROUND_DURATION*8*60)\n" +
            " and vwwork.DATERECEIVED between :fromDate and :toDate" +
            " and vwwork.DATA_PROVINCE in (:provinceIds)" +
            " and vwwork.processId in (:processId) \n" +
            "group by to_char(vwwork.DATERECEIVED,'MON-yy'), " +
            " to_number(to_char(vwwork.DATERECEIVED,'mmyy'), '9999') " +
            " order by to_number(to_char(vwwork.DATERECEIVED,'mmyy'), '9999')",
            nativeQuery = true)
    Collection<ProcessSummaryTaskDistributionByMonth> processSummaryGetTaskDistributionBeforeTurnaroundTime(final Collection<Integer> provinceIds,
                                                                                                            final LocalDate fromDate,
                                                                                                            final LocalDate toDate,
                                                                                                            final Long processId);

    @Query(value = "Select\n" +
            "    to_char(vwwork.DATERECEIVED,'MON-yy') as month,\n" +
            "    to_number(to_char(vwwork.DATERECEIVED,'mmyy'), '9999') as month1,\n" +
            "    count(1) as totalTask\n" +
            "from\n" +
            "    VW_WORKFLOW_PRODUCTIVITY vwwork\n" +
            "where PRODUCTIVITY_MINUTES > (TRUNAROUND_DURATION*8*60)\n" +
            " and vwwork.DATERECEIVED between :fromDate and :toDate" +
            " and vwwork.DATA_PROVINCE in (:provinceIds)" +
            " and vwwork.processId in (:processId)\n" +
            "group by to_char(vwwork.DATERECEIVED,'MON-yy')," +
            " to_number(to_char(vwwork.DATERECEIVED,'mmyy'), '9999')" +
            "order by to_number(to_char(vwwork.DATERECEIVED,'mmyy'), '9999')",
            nativeQuery = true)
    Collection<ProcessSummaryTaskDistributionByMonth> getTaskDistributionAfterTurnaroundTime(final Collection<Integer> provinceIds,
                                                                                             final LocalDate fromDate,
                                                                                             final LocalDate toDate,
                                                                                             final Long processId);


    @Query(value = "Select\n" +
            "    vwwork.DATA_PROVINCE_NAME as province,\n" +
            "    sum(vwwork.INVOICE_AMOUNT) as invoice,\n" +
            "    sum(vwwork.PAYMENT_AMOUNT) as paid\n" +
            "from\n" +
            "    VW_WORKFLOW_PRODUCTIVITY vwwork\n" +
            " where vwwork.DATA_PROVINCE in (:provinceIds) \n" +
            " and vwwork.DATERECEIVED between :fromDate and :toDate \n" +
            "group by vwwork.DATA_PROVINCE_NAME", nativeQuery = true)
    Collection<InformationRequestBilling> getInformationRequestBillingByProvince(final Collection<Integer> provinceIds,
                                                                                 final LocalDate fromDate,
                                                                                 final LocalDate toDate);


    @Query(value = "Select\n" +
            "    vwwork.INTERNALSTATUSCAPTION as status,\n" +
            "    sum(vwwork.INVOICE_AMOUNT) as totalInvoice\n" +
            "from\n" +
            "    VW_WORKFLOW_PRODUCTIVITY vwwork\n" +
            " where vwwork.DATA_PROVINCE in (:provinceIds) \n" +
            " and vwwork.DATERECEIVED between :fromDate and :toDate \n" +
            "group by vwwork.INTERNALSTATUSCAPTION", nativeQuery = true)
    Collection<InformationRequestInvoiceStatus> getInformationRequestInvoiceOverviewStatus(final Collection<Integer> provinceIds,
                                                                                           final LocalDate fromDate,
                                                                                           final LocalDate toDate);

    @Query(value = "Select\n" +
            "    vwwork.Data_PROVINCE_NAME as province,\n" +
            "    to_char(vwwork.DATERECEIVED,'MON-yy') as month,\n" +
            "    to_number(to_char(vwwork.DATERECEIVED,'mmyy'), '9999') as month1,\n" +
            "    sum(Invoice_Amount) as totalInvoice\n" +
            "from\n" +
            "    VW_WORKFLOW_PRODUCTIVITY vwwork\n" +
            " where vwwork.DATA_PROVINCE in (:provinceIds) \n" +
            " and vwwork.DATERECEIVED between :fromDate and :toDate \n" +
            "group by vwwork.Data_PROVINCE_NAME," +
            "to_char(vwwork.DATERECEIVED,'MON-yy')," +
            "to_number(to_char(vwwork.DATERECEIVED,'mmyy'), '9999')" +
            " order by to_number(to_char(vwwork.DATERECEIVED,'mmyy'), '9999')",
            nativeQuery = true)
    Collection<InformationRequestMonthlyInvoiceDistribution>
    getInformationRequestMonthlyInvoiceDistribution(final Collection<Integer> provinceIds,
                                                    final LocalDate fromDate,
                                                    final LocalDate toDate);

    @Query(value = "Select\n" +
            "    vwwork.Data_PROVINCE_NAME as province,\n" +
            "    to_char(vwwork.DATERECEIVED,'MON-yy') as month,\n" +
            "    to_number(to_char(vwwork.DATERECEIVED,'mmyy'), '9999') as month1,\n" +
            "    count(1) as totalRequest\n" +
            "from\n" +
            "    VW_WORKFLOW_PRODUCTIVITY vwwork\n" +
            " where vwwork.DATA_PROVINCE in (:provinceIds) \n" +
            " and vwwork.DATERECEIVED between :fromDate and :toDate \n" +
            "group by vwwork.Data_PROVINCE_NAME," +
            "to_char(vwwork.DATERECEIVED,'MON-yy')," +
            "to_number(to_char(vwwork.DATERECEIVED,'mmyy'), '9999') " +
            " order by to_number(to_char(vwwork.DATERECEIVED,'mmyy'), '9999')",
            nativeQuery = true)
    Collection<InformationRequestMonthlyRequestDistribution>
    getInformationRequestMonthlyRequestDistribution(final Collection<Integer> provinceIds,
                                                    final LocalDate fromDate,
                                                    final LocalDate toDate);

    @Query(value = "SELECT\n" +
            "    ItmIn.item as product,\n" +
            "    count(1) as totalProduct\n" +
            "FROM\n" +
            "    cart_items ItmIn\n" +
            "        inner join VW_WORKFLOW_PRODUCTIVITY work on\n" +
            "            ITmIN.WORKFLOWID = work.WORKFLOWID\n" +
            "where work.INTERNALSTATUS in(21)\n" +
            " and work.DATA_PROVINCE in (:provinceIds) \n" +
            " and work.DATERECEIVED between :fromDate and :toDate \n" +
            "group by  ItmIn.item", nativeQuery = true)
    Collection<InformationRequestProduct> getInformationRequestClosedRequestProduct(final Collection<Integer> provinceIds,
                                                                                    final LocalDate fromDate,
                                                                                    final LocalDate toDate);

    @Query(value = "SELECT\n" +
            "    ItmIn.item as product,\n" +
            "    count(1) as totalProduct\n" +
            "FROM\n" +
            "    cart_items ItmIn\n" +
            "        inner join VW_WORKFLOW_PRODUCTIVITY work on\n" +
            "            ITmIN.WORKFLOWID = work.WORKFLOWID\n" +
            "where work.INTERNALSTATUS in(7,14)\n" +
            " and work.DATA_PROVINCE in (:provinceIds) \n" +
            " and work.DATERECEIVED between :fromDate and :toDate \n" +
            "group by  ItmIn.item", nativeQuery = true)
    Collection<InformationRequestProduct> getInformationRequestOpenRequestProduct(final Collection<Integer> provinceIds,
                                                                                  final LocalDate fromDate,
                                                                                  final LocalDate toDate);

    //reservation dashboard queries total request
    @Query(value = "Select distinct count(res.WORKFLOWID)\n" +
            "From VW_WH_RESERVATION_REQUEST  res inner join  vw_WH_WORKFLOW_MASTER work on res.WORKFLOWID = work.WORKFLOWID\n" +
            "where work.Config_PARENT_PROCESSID in(229)  \n" +
            "and work.REQUEST_PROVINCE_ID in (:provinces) and work.TASK_STARTED between  :fromDate and :toDate", nativeQuery = true)
    Long getTotalReservationTaskInProgress(final Collection<Integer> provinces, final LocalDate fromDate, final LocalDate toDate);

    //reservation Number of parcel requested
    @Query(value = "Select sum(res.NO_OF_REQUEST)\n" +
            "From VW_WH_RESERVATION_REQUEST  res inner join  vw_WH_WORKFLOW_MASTER work on res.WORKFLOWID = work.WORKFLOWID\n" +
            "where work.Config_PARENT_PROCESSID in(229) \n" +
            "and work.REQUEST_PROVINCE_ID in (:provinces) and work.TASK_STARTED between  :fromDate and :toDate",nativeQuery = true)
    Long getTotalParcelRequestedReservation(final Collection<Integer> provinces, final LocalDate fromDate, final LocalDate toDate);


    //no of parcel reserved
    @Query(value = "Select sum(res.NO_OF_ISSUED)\n" +
            "From VW_WH_RESERVATION_REQUEST  res inner join  vw_WH_WORKFLOW_MASTER work on res.WORKFLOWID = work.WORKFLOWID\n" +
            "where work.Config_PARENT_PROCESSID in(229)\n" +
            "and work.TASK_INTERNALSTATUS in (237)and work.REQUEST_PROVINCE_ID in (:provinces) and work.TASK_STARTED between  :fromDate and :toDate",nativeQuery = true)
    Long getTotalParcelReserved(final Collection<Integer> provinces, final LocalDate fromDate, final LocalDate toDate);

    //request processed
    @Query(value = "Select count (distinct res.WORKFLOWID)\n" +
            "From VW_WH_RESERVATION_REQUEST  res inner join  vw_WH_WORKFLOW_MASTER work on res.WORKFLOWID = work.WORKFLOWID\n" +
            "where work.Config_PARENT_PROCESSID in(229) and work.TASK_INTERNALSTATUS in (20,237)\n" +
            "  and work.REQUEST_PROVINCE_ID in (:provinces) and work.TASK_STARTED between  :fromDate and :toDate",nativeQuery = true)
    Long getTotalProcessedResRequest(final Collection<Integer> provinces, final LocalDate fromDate, final LocalDate toDate);


    @Query(value = "Select  res.RES_TYPE as reservationType, count(1) reservationTypeCount\n" +
            "From VW_WH_RESERVATION_REQUEST  res inner join  vw_WH_WORKFLOW_MASTER work on res.WORKFLOWID = work.WORKFLOWID\n" +
            "where work.Config_PARENT_PROCESSID in(229) and TASK_COMPLETEDON is null and work.REQUEST_PROVINCE_ID in (:provinces) and work.TASK_STARTED between  :fromDate and :toDate\n" +
            "group by res.RES_TYPE",nativeQuery = true)
    Collection<ReservationRequestProduct> getReservationOpenRequest(final Collection<Integer> provinces, final LocalDate fromDate, final LocalDate toDate);

    @Query(value = "Select  res.RES_TYPE as reservationType, count(1) reservationTypeCount\n" +
            "From VW_WH_RESERVATION_REQUEST  res inner join  vw_WH_WORKFLOW_MASTER work on res.WORKFLOWID = work.WORKFLOWID\n" +
            "where work.Config_PARENT_PROCESSID in(229) and TASK_COMPLETEDON is not null and work.REQUEST_PROVINCE_ID in (:provinces) and work.TASK_STARTED between  :fromDate and :toDate\n" +
            "group by res.RES_TYPE",nativeQuery = true)
    Collection<ReservationRequestProduct> getReservationCloseRequest(final Collection<Integer> provinces, final LocalDate fromDate, final LocalDate toDate);


    @Query(value = "Select work.REQUEST_PROVINCE_NAME as Province,\n" +
            "to_char(work.TASK_STARTED,'MON-yy') as Month,\n" +
            "to_number(to_char(work.TASK_STARTED,'mmyy'), '9999') as month1,\n" +
            "count(res.WORKFLOWID) as totalRequest\n" +
            "From VW_WH_RESERVATION_REQUEST  res inner join  vw_WH_WORKFLOW_MASTER work on res.WORKFLOWID = work.WORKFLOWID\n" +
            "where work.Config_PARENT_PROCESSID in(229) --and work.REQUEST_PROVINCE_ID in (:provinces) and work.TASK_STARTED between  :fromDate and :toDate\n" +
            "group by work.REQUEST_PROVINCE_NAME, to_char(work.TASK_STARTED,'MON-yy'),to_number(to_char(work.TASK_STARTED,'mmyy'), '9999')\n" +
            "order by to_number(to_char(work.TASK_STARTED,'mmyy'), '9999')",nativeQuery = true)
    Collection<ReservationRequestMonthlyRequestDistribution> getReservationRequestDistribution(final Collection<Integer> provinces, final LocalDate fromDate, final LocalDate toDate);


    @Query(value = "Select work.TASK_INTERNALSTATUS_CAPTION as status,\n" +
            "    to_char(work.TASK_STARTED,'yyyy') as month,\n" +
            "    count(res.WORKFLOWID) as totalRequest\n" +
            "From VW_WH_RESERVATION_REQUEST  res inner join  vw_WH_WORKFLOW_MASTER work on res.WORKFLOWID = work.WORKFLOWID\n" +
            "where work.Config_PARENT_PROCESSID in(229) --and work.REQUEST_PROVINCE_ID in (:provinces) and work.TASK_STARTED between  :fromDate and :toDate\n" +
            "group by work.TASK_INTERNALSTATUS_CAPTION, to_char(work.TASK_STARTED,'yyyy'),to_number(to_char(work.TASK_STARTED,'yyyy'), '9999')\n" +
            "order by to_number(to_char(work.TASK_STARTED,'yyyy'), '9999')",nativeQuery = true)
    Collection<ReservationStatusYearlyProjection> getReservationStatusYearly(final Collection<Integer> provinces, final LocalDate fromDate, final LocalDate toDate);

    @Query(value="Select res.APPLICANT_FULLNAME as userName, work.REFERENCE_NO as referenceNo\n" +
            ", res.RES_TYPE as reservationType,work.TASK_STARTED dated\n" +
            ",work.TASK_INTERNALSTATUS_CAPTION as status\n" +
            "From VW_WH_RESERVATION_REQUEST  res inner join  vw_WH_WORKFLOW_MASTER work on res.WORKFLOWID = work.WORKFLOWID\n" +
            "where work.Config_PARENT_PROCESSID in(229) and work.REQUEST_PROVINCE_ID in (:provinces) and work.TASK_STARTED between  :fromDate and :toDate",nativeQuery =  true)
    Collection<ReservationRequestDetails> getReservationDetails(final Collection<Integer> provinces, final LocalDate fromDate, final LocalDate toDate);

}
