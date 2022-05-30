package com.dw.ngms.cis.service;

import com.dw.ngms.cis.enums.UserStatus;
import com.dw.ngms.cis.persistence.projection.dashboard.*;
import com.dw.ngms.cis.persistence.repository.dashboard.VUsersRepository;
import com.dw.ngms.cis.persistence.repository.dashboard.VwWfNotificationsRepository;
import com.dw.ngms.cis.persistence.repository.dashboard.VwWorkflowActionDataRepository;
import com.dw.ngms.cis.persistence.repository.dashboard.VwWorkflowProductivityRepository;
import com.dw.ngms.cis.persistence.views.VUsers;
import com.dw.ngms.cis.service.dto.dashboard.*;
import com.dw.ngms.cis.web.vm.dashboard.InformationRequestVm;
import com.dw.ngms.cis.web.vm.dashboard.ProcessSummaryVm;
import com.dw.ngms.cis.web.vm.dashboard.UserRegistrationVm;
import com.dw.ngms.cis.web.vm.dashboard.UserSummaryVm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 12/03/21, Fri
 **/
@Service
@AllArgsConstructor
@Slf4j
public class DashBoardService {


    private final VUsersRepository vUsersRepository;

    private final VwWfNotificationsRepository vwWfNotificationsRepository;

    private final VwWorkflowActionDataRepository vwWorkflowActionDataRepository;

    private final VwWorkflowProductivityRepository vwWorkflowProductivityRepository;

    public Collection<ProcessProjection> getAllProcess() {
        return vwWorkflowProductivityRepository.getAllProcess();
    }


    /**
     * @return {@link UserRegistrationCountDto}
     */
    public UserRegistrationCountDto getUserRegistrationCounter(final UserRegistrationVm userRegistrationVm) {

        return UserRegistrationCountDto.builder()
                .totalRegisteredUsers(vUsersRepository.findAllDistinctEmails(userRegistrationVm.getProvinceIds(),
                        userRegistrationVm.getFromDate(),
                        userRegistrationVm.getToDate()))
                .totalActiveUsers(vUsersRepository.findAllByStatus(UserStatus.ACTIVE.getUserStatus(),
                        userRegistrationVm.getProvinceIds(),
                        userRegistrationVm.getFromDate(),
                        userRegistrationVm.getToDate()))
                .totalLockedUsers(vUsersRepository.findAllByStatus(UserStatus.LOCK.getUserStatus(),
                        userRegistrationVm.getProvinceIds(),
                        userRegistrationVm.getFromDate(),
                        userRegistrationVm.getToDate()))
                .totalActiveParticipants(vUsersRepository.findActiveParticipatingUsers(userRegistrationVm.getProvinceIds(),
                        userRegistrationVm.getFromDate(),
                        userRegistrationVm.getToDate()))
                .build();
    }

    /**
     * @param userSummaryVm {@link UserSummaryVm}
     * @return {@link UserSummaryCountDto}
     */
    public UserSummaryCountDto getUserSummaryCounters(final UserSummaryVm userSummaryVm) {
        return UserSummaryCountDto.builder()
                .closedTaskCount(vwWorkflowActionDataRepository.getClosedTask(
                        userSummaryVm.getUserId(),
                        userSummaryVm.getFromDate(),
                        userSummaryVm.getToDate()))
                .totalTaskCount(vwWorkflowActionDataRepository.getTotalTask(
                        userSummaryVm.getUserId(),
                        userSummaryVm.getFromDate(),
                        userSummaryVm.getToDate()))
                .turnaroundDuration(vwWorkflowActionDataRepository.getAverageTurnaroundDuration(
                        userSummaryVm.getUserId(),
                        userSummaryVm.getFromDate(),
                        userSummaryVm.getToDate()))
                .totalNotifications(vwWorkflowActionDataRepository.getNoOfNotification(
                        userSummaryVm.getUserId(),
                        userSummaryVm.getFromDate(),
                        userSummaryVm.getToDate()))
                .build();
    }

    /**
     * @return Collection<VUsers>
     */
    public Collection<VUsers> getAllData(final UserRegistrationVm userRegistrationVm) {
        return vUsersRepository.findByProvinceIdInAndUserCreationDateBetween(userRegistrationVm.getProvinceIds(),
                userRegistrationVm.getFromDate(),
                userRegistrationVm.getToDate());
    }


    /**
     * @param userSummaryVm {@link UserSummaryVm}
     * @return Collection<UserSummaryMonthlyTaskDistribution>
     */
    public Collection<UserSummaryMonthlyTaskDistribution> userSummaryGetMonthlyTaskDistribution(
            final UserSummaryVm userSummaryVm) {
        return vwWorkflowActionDataRepository.getMonthlyTaskDistribution(
                userSummaryVm.getUserId(),
                userSummaryVm.getFromDate(),
                userSummaryVm.getToDate());
    }

    /**
     * @param userSummaryVm {@link UserSummaryVm}
     * @return Collection<UserSummaryDistributionByAction>
     */
    public Collection<UserSummaryDistributionByAction> userSummaryGetDistributionByAction(
            final UserSummaryVm userSummaryVm) {
        return vwWorkflowActionDataRepository.getDistributionByAction(
                userSummaryVm.getUserId(),
                userSummaryVm.getFromDate(),
                userSummaryVm.getToDate());
    }

    /**
     * @param userSummaryVm {@link UserSummaryVm}
     * @return Collection<UserSummaryAlertDetails>
     */
    public Collection<UserSummaryAlertDetails> userSummaryGetAlertDetails(
            final UserSummaryVm userSummaryVm) {
        return vwWorkflowActionDataRepository.getAllByUseridAndDateReceivedBetween(
                userSummaryVm.getUserId(),
                userSummaryVm.getFromDate(),
                userSummaryVm.getToDate());
    }

    /**
     * @param userSummaryVm {@link UserSummaryVm}
     * @return Collection<UserSummaryTaskList>
     */
    public Collection<UserSummaryTaskList> userSummaryGetTaskList(
            final UserSummaryVm userSummaryVm) {
        return vwWorkflowActionDataRepository.getTaskList(userSummaryVm.getUserId(),
                userSummaryVm.getFromDate(), userSummaryVm.getToDate());
    }

    /**
     * @param userSummaryVm {@link UserSummaryVm}
     * @return Collection<UserSummaryMonthlyTaskDistribution>
     */
    public Collection<UserSummaryMonthlyTaskDistribution> userSummaryGetTaskBeforeTurnaroundTime(
            final UserSummaryVm userSummaryVm) {
        return vwWorkflowActionDataRepository.getTasksBeforeTurnaroundTime(userSummaryVm.getUserId(),
                userSummaryVm.getFromDate(), userSummaryVm.getToDate());
    }

    /**
     * @param userSummaryVm {@link UserSummaryVm}
     * @return Collection<UserSummaryMonthlyTaskDistribution>
     */
    public Collection<UserSummaryMonthlyTaskDistribution> userSummaryGetTaskAfterTurnaroundTime(
            final UserSummaryVm userSummaryVm) {
        return vwWorkflowActionDataRepository.getTasksAfterTurnaroundTime(userSummaryVm.getUserId(),
                userSummaryVm.getFromDate(), userSummaryVm.getToDate());
    }


    /**
     * @return ProcessSummaryCounterDto
     */
    public ProcessSummaryCounterDto getProcessSummaryCounters(final ProcessSummaryVm processSummaryVm) {
        return ProcessSummaryCounterDto.builder()
                .totalNotification(vwWorkflowProductivityRepository.getNotificationTotalCount(
                        processSummaryVm.getProvinceIds(),
                        processSummaryVm.getFromDate(),
                        processSummaryVm.getToDate(),
                        processSummaryVm.getProcessId()))
                .closedTask(vwWorkflowProductivityRepository.getClosedTask(
                        processSummaryVm.getProvinceIds(),
                        processSummaryVm.getFromDate(),
                        processSummaryVm.getToDate(),
                        processSummaryVm.getProcessId()))
                .totalTask(vwWorkflowProductivityRepository.getTotalTaskInProgress(
                        processSummaryVm.getProvinceIds(),
                        processSummaryVm.getFromDate(),
                        processSummaryVm.getToDate(),
                        processSummaryVm.getProcessId()))
                .turnaroundDuration(vwWorkflowProductivityRepository.getAverageTurnaroundDuration(
                        processSummaryVm.getProvinceIds(),
                        processSummaryVm.getFromDate(),
                        processSummaryVm.getToDate(),
                        processSummaryVm.getProcessId()))
                .build();
    }


    /**
     * @return Collection<ProcessSummaryTaskDistributionByRole>
     */
    public Collection<ProcessSummaryTaskDistributionByRole> processSummaryGetTaskDistributionByRole(
            final ProcessSummaryVm processSummaryVm) {
        return vwWorkflowProductivityRepository.getTaskDistributionByRole(
                processSummaryVm.getProvinceIds(),
                processSummaryVm.getFromDate(),
                processSummaryVm.getToDate(),
                processSummaryVm.getProcessId());
    }

    /**
     * @param processSummaryVm {@link ProcessSummaryVm}
     * @return Collection<ProcessSummaryTaskDistributionByUser>
     */
    public Collection<ProcessSummaryTaskDistributionByUser> processSummaryGetTaskDistributionByUser(
            final ProcessSummaryVm processSummaryVm) {
        return vwWorkflowProductivityRepository.processSummaryGetTaskDistributionByUser(
                processSummaryVm.getProvinceIds(),
                processSummaryVm.getFromDate(),
                processSummaryVm.getToDate(),
                processSummaryVm.getProcessId());
    }

    /**
     * @param processSummaryVm {@link ProcessSummaryVm}
     * @return Collection<ProcessSummaryTaskDistributionByMonth>
     */
    public Collection<ProcessSummaryTaskDistributionByMonth> processSummaryGetTaskDistributionByMonth(
            final ProcessSummaryVm processSummaryVm) {
        return vwWorkflowProductivityRepository
                .processSummaryGetTaskDistributionByMonth(processSummaryVm.getProvinceIds(),
                        processSummaryVm.getFromDate(),
                        processSummaryVm.getToDate(),
                        processSummaryVm.getProcessId());
    }

    /**
     * @param processSummaryVm {@link ProcessSummaryVm}
     * @return Collection<ProcessSummaryTaskDistributionByMonth>
     */
    public Collection<ProcessSummaryTaskDistributionByMonth> processSummaryGetTaskDistributionBeforeTurnaroundTime(
            final ProcessSummaryVm processSummaryVm) {
        return vwWorkflowProductivityRepository.processSummaryGetTaskDistributionBeforeTurnaroundTime(
                processSummaryVm.getProvinceIds(),
                processSummaryVm.getFromDate(),
                processSummaryVm.getToDate(),
                processSummaryVm.getProcessId());
    }

    /**
     * @param processSummaryVm {@link ProcessSummaryVm}
     * @return Collection<ProcessSummaryTaskDistributionByMonth>
     */
    public Collection<ProcessSummaryTaskDistributionByMonth> processSummaryGetTaskDistributionAfterTurnaroundTime(
            final ProcessSummaryVm processSummaryVm) {
        return vwWorkflowProductivityRepository.getTaskDistributionAfterTurnaroundTime(
                processSummaryVm.getProvinceIds(),
                processSummaryVm.getFromDate(),
                processSummaryVm.getToDate(),
                processSummaryVm.getProcessId());
    }

    /**
     * @param informationRequestVm {@link InformationRequestVm}
     * @return {@link InformationRequestCounterDto}
     */
    public InformationRequestCounterDto getInformationRequestCounter(final InformationRequestVm informationRequestVm) {
        return InformationRequestCounterDto.builder()
                .dispatchedRequestCount(vwWorkflowProductivityRepository.getDispatchedRequestCount(
                        informationRequestVm.getProvinceIds(),
                        informationRequestVm.getFromDate(),
                        informationRequestVm.getToDate()))
                .invoiceAmount(vwWorkflowProductivityRepository.getInvoiceAmount(
                        informationRequestVm.getProvinceIds(),
                        informationRequestVm.getFromDate(),
                        informationRequestVm.getToDate()))
                .totalRequestCount(vwWorkflowProductivityRepository.getTotalTaskInProgress(
                        informationRequestVm.getProvinceIds(),
                        informationRequestVm.getFromDate(),
                        informationRequestVm.getToDate()))
                .paymentAmount(vwWorkflowProductivityRepository.getPaymentAmount(
                        informationRequestVm.getProvinceIds(),
                        informationRequestVm.getFromDate(),
                        informationRequestVm.getToDate()))
                .build();
    }

    /**
     * @param informationRequestVm {@link InformationRequestVm}
     * @return Collection<InformationRequestProduct>
     */
    public Collection<InformationRequestProduct>
    getInformationRequestOpenProduct(final InformationRequestVm informationRequestVm) {
        return vwWorkflowProductivityRepository
                .getInformationRequestOpenRequestProduct(informationRequestVm.getProvinceIds(),
                        informationRequestVm.getFromDate(),
                        informationRequestVm.getToDate());
    }

    /**
     * @param informationRequestVm {@link InformationRequestVm}
     * @return Collection<InformationRequestProduct>
     */
    public Collection<InformationRequestProduct>
    getInformationRequestClosedProduct(final InformationRequestVm informationRequestVm) {
        return vwWorkflowProductivityRepository
                .getInformationRequestClosedRequestProduct(informationRequestVm.getProvinceIds(),
                        informationRequestVm.getFromDate(),
                        informationRequestVm.getToDate());
    }

    /**
     * @param informationRequestVm {@link InformationRequestVm}
     * @return Collection<InformationRequestMonthlyRequestDistribution>
     */
    public Collection<InformationRequestMonthlyRequestDistribution>
    getInformationRequestMonthlyRequestDistribution(final InformationRequestVm informationRequestVm) {
        return vwWorkflowProductivityRepository
                .getInformationRequestMonthlyRequestDistribution(informationRequestVm.getProvinceIds(),
                        informationRequestVm.getFromDate(),
                        informationRequestVm.getToDate());
    }

    /**
     * @param informationRequestVm {@link InformationRequestVm}
     * @return Collection<InformationRequestMonthlyInvoiceDistribution>
     */
    public Collection<InformationRequestMonthlyInvoiceDistribution>
    getInformationRequestMonthlyInvoiceDistribution(final InformationRequestVm informationRequestVm) {
        return vwWorkflowProductivityRepository
                .getInformationRequestMonthlyInvoiceDistribution(informationRequestVm.getProvinceIds(),
                        informationRequestVm.getFromDate(),
                        informationRequestVm.getToDate());
    }

    /**
     * @param informationRequestVm {@link InformationRequestVm}
     * @return Collection<InformationRequestInvoiceStatus>
     */
    public Collection<InformationRequestInvoiceStatus>
    getInformationRequestInvoiceStatusOverview(final InformationRequestVm informationRequestVm) {
        return vwWorkflowProductivityRepository.getInformationRequestInvoiceOverviewStatus(
                informationRequestVm.getProvinceIds(),
                informationRequestVm.getFromDate(),
                informationRequestVm.getToDate());
    }

    /**
     * @param informationRequestVm {@link InformationRequestVm}
     * @return Collection<InformationRequestBilling>
     */
    public Collection<InformationRequestBilling>
    getInformationRequestBillingByProvince(final InformationRequestVm informationRequestVm) {
        return vwWorkflowProductivityRepository.getInformationRequestBillingByProvince(
                informationRequestVm.getProvinceIds(),
                informationRequestVm.getFromDate(),
                informationRequestVm.getToDate());
    }

    /**
     * @param informationRequestVm {@link InformationRequestVm}
     * @return Collection<InformationRequestNotification>
     */
    public Collection<InformationRequestNotification>
    getInformationRequestNotification(final InformationRequestVm informationRequestVm) {
        return vwWfNotificationsRepository.getInformationRequestNotification(
                informationRequestVm.getProvinceIds(),
                informationRequestVm.getFromDate(),
                informationRequestVm.getToDate());
    }

    /**
     * @param informationRequestVm {@link InformationRequestVm}
     * @return Collection<InformationRequestManagerAlert>
     */
    public Collection<InformationRequestManagerAlert>
    getInformationRequestManagerAlerts(final InformationRequestVm informationRequestVm) {
        return vwWfNotificationsRepository.getInformationRequestManagerAlerts(
                informationRequestVm.getProvinceIds(),
                informationRequestVm.getFromDate(),
                informationRequestVm.getToDate());
    }

    /**
     * @return Collection<DashboardMapView>
     */
    public Collection<DashboardMapView> getDashboardMapView() {
        return vUsersRepository.getDashboardMapView();
    }


    /**
     * @param informationRequestVm {@link InformationRequestVm}
     * @return {@link ReservationHeaderDto}
     */
    public ReservationHeaderDto getReservationHeaderCounter(final InformationRequestVm informationRequestVm) {
        return ReservationHeaderDto.builder()
                .totalRequestCount(vwWorkflowProductivityRepository.getTotalReservationTaskInProgress(
                        informationRequestVm.getProvinceIds(),
                        informationRequestVm.getFromDate(),
                        informationRequestVm.getToDate()))
                .numberOfParcelRequested(vwWorkflowProductivityRepository.getTotalParcelRequestedReservation(
                        informationRequestVm.getProvinceIds(),
                        informationRequestVm.getFromDate(),
                        informationRequestVm.getToDate()))
                .numberOfParcelReserved(vwWorkflowProductivityRepository.getTotalParcelReserved(
                        informationRequestVm.getProvinceIds(),
                        informationRequestVm.getFromDate(),
                        informationRequestVm.getToDate()))
                .requestProcessed(vwWorkflowProductivityRepository.getTotalProcessedResRequest(
                        informationRequestVm.getProvinceIds(),
                        informationRequestVm.getFromDate(),
                        informationRequestVm.getToDate()))
                .build();
    }

    /**
     * @param informationRequestVm {@link InformationRequestVm}
     * @return Collection<ReservationRequestProduct>
     */
    public Collection<ReservationRequestProduct>
    getReservationOpenRequest(final InformationRequestVm informationRequestVm) {
        return vwWorkflowProductivityRepository
                .getReservationOpenRequest(informationRequestVm.getProvinceIds(),
                        informationRequestVm.getFromDate(),
                        informationRequestVm.getToDate());
    }


    /**
     * @param informationRequestVm {@link InformationRequestVm}
     * @return Collection<ReservationRequestProduct>
     */
    public Collection<ReservationRequestProduct>
    getReservationCloseRequest(final InformationRequestVm informationRequestVm) {
        return vwWorkflowProductivityRepository
                .getReservationCloseRequest(informationRequestVm.getProvinceIds(),
                        informationRequestVm.getFromDate(),
                        informationRequestVm.getToDate());
    }

    /**
     * @param informationRequestVm {@link InformationRequestVm}
     * @return Collection<ReservationRequestMonthlyRequestDistribution>
     */
    public Collection<ReservationRequestMonthlyRequestDistribution>
    getReservationRequestDistribution(final InformationRequestVm informationRequestVm) {
        return vwWorkflowProductivityRepository
                .getReservationRequestDistribution(informationRequestVm.getProvinceIds(),
                        informationRequestVm.getFromDate(),
                        informationRequestVm.getToDate());
    }


    /**
     * @param informationRequestVm {@link InformationRequestVm}
     * @return Collection<ReservationRequestMonthlyRequestDistribution>
     */
    public Collection<ReservationStatusYearlyProjection>
    getReservationStatusYearly(final InformationRequestVm informationRequestVm) {
        return vwWorkflowProductivityRepository
                .getReservationStatusYearly(informationRequestVm.getProvinceIds(),
                        informationRequestVm.getFromDate(),
                        informationRequestVm.getToDate());
    }

    /**
     * @param informationRequestVm {@link InformationRequestVm}
     * @return Collection<ReservationDetailProjection>
     */
    public Collection<ReservationRequestDetails>
    getReservationDetails(final InformationRequestVm informationRequestVm) {
        return vwWorkflowProductivityRepository
                .getReservationDetails(informationRequestVm.getProvinceIds(),
                        informationRequestVm.getFromDate(),
                        informationRequestVm.getToDate());
    }

}
