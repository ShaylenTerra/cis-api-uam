package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.persistence.projection.dashboard.*;
import com.dw.ngms.cis.persistence.views.VUsers;
import com.dw.ngms.cis.service.DashBoardService;
import com.dw.ngms.cis.service.dto.dashboard.*;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.vm.dashboard.InformationRequestVm;
import com.dw.ngms.cis.web.vm.dashboard.ProcessSummaryVm;
import com.dw.ngms.cis.web.vm.dashboard.UserRegistrationVm;
import com.dw.ngms.cis.web.vm.dashboard.UserSummaryVm;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 12/03/21, Fri
 **/
@BaseResponse
@AllArgsConstructor
@RestController
@RequestMapping(AppConstants.API_BASE_MAPPING + "/dashboard")
public class DashboardResource {


    private final DashBoardService dashBoardService;

    /**
     * @return Collection<VwWorkflowProductivity>
     */
    @GetMapping("/process")
    public ResponseEntity<Collection<ProcessProjection>> getAllProcess() {
        return ResponseEntity.ok()
                .body(dashBoardService.getAllProcess());
    }

    /**
     * @return {@link UserRegistrationCountDto}
     */
    @PostMapping("/userRegistration/getTopCounters")
    public ResponseEntity<UserRegistrationCountDto> getUseRegistrationCounters(@RequestBody @Valid final UserRegistrationVm userRegistrationVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.getUserRegistrationCounter(userRegistrationVm));
    }

    /**
     * @return Collection<VUsers>
     */
    @PostMapping("/userRegistration/getData")
    public ResponseEntity<Collection<VUsers>> getAllUserRegistrationData(@RequestBody @Valid final UserRegistrationVm userRegistrationVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.getAllData(userRegistrationVm));
    }


    /**
     * @param userSummaryVm {@link UserSummaryVm}
     * @return count
     */
    @PostMapping("/userSummary/getTopCounters")
    public ResponseEntity<UserSummaryCountDto> getUserSummaryTotalTask(@RequestBody @Valid final UserSummaryVm userSummaryVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.getUserSummaryCounters(userSummaryVm));
    }

    /**
     * @param userSummaryVm {@link UserSummaryVm}
     * @return Collection<UserSummaryMonthlyTaskDistribution>
     */
    @PostMapping("/userSummary/monthlyTaskDuration")
    public ResponseEntity<Collection<UserSummaryMonthlyTaskDistribution>>
    userSummaryGetMonthlyTaskDuration(@RequestBody @Valid final UserSummaryVm userSummaryVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.userSummaryGetMonthlyTaskDistribution(userSummaryVm));
    }

    /**
     * @param userSummaryVm {@link UserSummaryVm}
     * @return Collection<UserSummaryDistributionByAction>
     */
    @PostMapping("/userSummary/distributionByAction")
    public ResponseEntity<Collection<UserSummaryDistributionByAction>>
    getUserSummaryDistributionByAction(@RequestBody @Valid final UserSummaryVm userSummaryVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.userSummaryGetDistributionByAction(userSummaryVm));
    }

    /**
     * @param userSummaryVm {@link UserSummaryVm}
     * @return Collection<UserSummaryAlertDetails>
     */
    @PostMapping("/userSummary/alertDetails")
    public ResponseEntity<Collection<UserSummaryAlertDetails>>
    getUserSummaryAlertDetails(@RequestBody @Valid final UserSummaryVm userSummaryVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.userSummaryGetAlertDetails(userSummaryVm));
    }

    /**
     * @param userSummaryVm {@link UserSummaryVm}
     * @return Collection<UserSummaryTaskList>
     */
    @PostMapping("/userSummary/taskList")
    public ResponseEntity<Collection<UserSummaryTaskList>>
    getUserSummaryTaskList(@RequestBody @Valid final UserSummaryVm userSummaryVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.userSummaryGetTaskList(userSummaryVm));
    }

    /**
     * @param userSummaryVm {@link UserSummaryVm}
     * @return Collection<UserSummaryMonthlyTaskDistribution>
     */
    @PostMapping("/userSummary/beforeTurnAroundTime")
    public ResponseEntity<Collection<UserSummaryMonthlyTaskDistribution>>
    getUserSummaryTaskBeforeTurnaroundTime(@RequestBody @Valid final UserSummaryVm userSummaryVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.userSummaryGetTaskBeforeTurnaroundTime(userSummaryVm));
    }

    /**
     * @param userSummaryVm {@link UserSummaryVm}
     * @return Collection<UserSummaryMonthlyTaskDistribution>
     */
    @PostMapping("/userSummary/afterTurnaroundTime")
    public ResponseEntity<Collection<UserSummaryMonthlyTaskDistribution>>
    getUserSummaryTaskAfterTurnaroundTime(@RequestBody @Valid final UserSummaryVm userSummaryVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.userSummaryGetTaskAfterTurnaroundTime(userSummaryVm));
    }

    /**
     * @return {@link ProcessSummaryCounterDto}
     */
    @PostMapping("/processSummary/getTopCounters")
    public ResponseEntity<ProcessSummaryCounterDto> getTopCounters(@RequestBody @Valid final ProcessSummaryVm processSummaryVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.getProcessSummaryCounters(processSummaryVm));
    }

    /**
     * @param processSummaryVm {@link ProcessSummaryVm}
     * @return average count
     */
    @PostMapping("/processSummary/taskDistributionByRole")
    public ResponseEntity<Collection<ProcessSummaryTaskDistributionByRole>> getTaskDistributionByRole(
            @RequestBody @Valid final ProcessSummaryVm processSummaryVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.processSummaryGetTaskDistributionByRole(processSummaryVm));
    }

    /**
     * @param processSummaryVm {@link ProcessSummaryVm}
     * @return average count
     */
    @PostMapping("/processSummary/taskDistributionByUser")
    public ResponseEntity<Collection<ProcessSummaryTaskDistributionByUser>> getTaskDistributionByUser(
            @RequestBody @Valid final ProcessSummaryVm processSummaryVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.processSummaryGetTaskDistributionByUser(processSummaryVm));
    }

    /**
     * @param processSummaryVm {@link ProcessSummaryVm}
     * @return average count
     */
    @PostMapping("/processSummary/taskDistributionByMonth")
    public ResponseEntity<Collection<ProcessSummaryTaskDistributionByMonth>> getTaskDistributionByMonth(
            @RequestBody @Valid final ProcessSummaryVm processSummaryVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.processSummaryGetTaskDistributionByMonth(processSummaryVm));
    }

    /**
     * @param processSummaryVm {@link ProcessSummaryVm}
     * @return average count
     */
    @PostMapping("/processSummary/taskDistributionBeforeTurnaroundTime")
    public ResponseEntity<Collection<ProcessSummaryTaskDistributionByMonth>> getTaskDistributionBeforeTurnaroundTine(
            @RequestBody @Valid final ProcessSummaryVm processSummaryVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.processSummaryGetTaskDistributionBeforeTurnaroundTime(processSummaryVm));
    }

    /**
     * @param processSummaryVm {@link ProcessSummaryVm}
     * @return average count
     */
    @PostMapping("/processSummary/taskDistributionAfterTurnaroundTime")
    public ResponseEntity<Collection<ProcessSummaryTaskDistributionByMonth>> getTaskDistributionAfterTurnaroundTine(
            @RequestBody @Valid final ProcessSummaryVm processSummaryVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.processSummaryGetTaskDistributionAfterTurnaroundTime(processSummaryVm));
    }

    /**
     * @return {@link InformationRequestCounterDto}
     */
    @PostMapping("/informationRequest/getTopCounters")
    public ResponseEntity<InformationRequestCounterDto> getTopCountersForProcessSummary(
            @RequestBody @Valid final InformationRequestVm informationRequestVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.getInformationRequestCounter(informationRequestVm));
    }

    /**
     * @return Collection<ProcessSummaryRequestProduct>
     */
    @PostMapping("/informationRequest/openRequest")
    public ResponseEntity<Collection<InformationRequestProduct>> getProcessSummaryOpenRequests(
            @RequestBody @Valid final InformationRequestVm informationRequestVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.getInformationRequestOpenProduct(informationRequestVm));
    }

    /**
     * @return Collection<ProcessSummaryRequestProduct>
     */
    @PostMapping("/informationRequest/closedRequest")
    public ResponseEntity<Collection<InformationRequestProduct>> getProcessSummaryClosedRequests(
            @RequestBody @Valid final InformationRequestVm informationRequestVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.getInformationRequestClosedProduct(informationRequestVm));
    }

    /**
     * @return Collection<ProcessSummaryMonthlyRequestDistribution>
     */
    @PostMapping("/informationRequest/requestDistributionMonthly")
    public ResponseEntity<Collection<InformationRequestMonthlyRequestDistribution>>
    getProcessSummaryRequestDistributionMonthly(@RequestBody @Valid final InformationRequestVm informationRequestVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.getInformationRequestMonthlyRequestDistribution(informationRequestVm));
    }

    /**
     * @return Collection<ProcessSummaryMonthlyInvoiceDistribution>
     */
    @PostMapping("/informationRequest/invoiceDistributionMonthly")
    public ResponseEntity<Collection<InformationRequestMonthlyInvoiceDistribution>>
    getProcessSummaryInvoiceDistributionMonthly(@RequestBody @Valid final InformationRequestVm informationRequestVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.getInformationRequestMonthlyInvoiceDistribution(informationRequestVm));
    }

    /**
     * @return Collection<ProcessSummaryInvoiceStatus>
     */
    @PostMapping("/informationRequest/invoiceOverviewStatus")
    public ResponseEntity<Collection<InformationRequestInvoiceStatus>>
    getProcessSummaryInvoiceOverviewStatus(@RequestBody @Valid final InformationRequestVm informationRequestVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.getInformationRequestInvoiceStatusOverview(informationRequestVm));
    }

    /**
     * @return Collection<ProcessSummaryBilling>
     */
    @PostMapping("/informationRequest/billing")
    public ResponseEntity<Collection<InformationRequestBilling>>
    getProcessSummaryBilling(@RequestBody @Valid final InformationRequestVm informationRequestVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.getInformationRequestBillingByProvince(informationRequestVm));
    }

    /**
     * @return Collection<ProcessSummaryManagerAlert>
     */
    @PostMapping("/informationRequest/managerAlerts")
    public ResponseEntity<Collection<InformationRequestManagerAlert>>
    getProcessSummaryManagerAlerts(@RequestBody @Valid final InformationRequestVm informationRequestVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.getInformationRequestManagerAlerts(informationRequestVm));
    }


    /**
     * @return Collection<ProcessSummaryNotification>
     */
    @PostMapping("/informationRequest/alertDetails")
    public ResponseEntity<Collection<InformationRequestNotification>>
    getProcessSummaryAlterDetails(@RequestBody @Valid final InformationRequestVm informationRequestVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.getInformationRequestNotification(informationRequestVm));
    }

    /**
     * @return Collection<DashboardMapView>
     */
    @GetMapping("/mapview")
    public ResponseEntity<Collection<DashboardMapView>> getDashboardView() {
        return ResponseEntity.ok()
                .body(dashBoardService.getDashboardMapView());
    }

    /**
     * @return {@link ReservationHeaderDto}
     */
    @PostMapping("/reservationRequest/getTopCounters")
    public ResponseEntity<ReservationHeaderDto> getTopCountersForReservationHeader(
            @RequestBody @Valid final InformationRequestVm informationRequestVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.getReservationHeaderCounter(informationRequestVm));
    }

    /**
     * @return Collection<ReservationRequestProduct>
     */
    @PostMapping("/reservationRequest/openRequest")
    public ResponseEntity<Collection<ReservationRequestProduct>> getReservationOpenRequest(
            @RequestBody @Valid final InformationRequestVm informationRequestVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.getReservationOpenRequest(informationRequestVm));
    }

    /**
     * @return Collection<ProcessSummaryRequestProduct>
     */
    @PostMapping("/reservationRequest/closedRequest")
    public ResponseEntity<Collection<ReservationRequestProduct>> getReservationCloseRequest(
            @RequestBody @Valid final InformationRequestVm informationRequestVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.getReservationCloseRequest(informationRequestVm));
    }

    /**
     * @return Collection<ProcessSummaryMonthlyRequestDistribution>
     */
    @PostMapping("/reservationRequest/requestDistributionMonthly")
    public ResponseEntity<Collection<ReservationRequestMonthlyRequestDistribution>>
    getReservationRequestDistribution(@RequestBody @Valid final InformationRequestVm informationRequestVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.getReservationRequestDistribution(informationRequestVm));
    }

    /**
     * @return Collection<ReservationStatusYearlyProjection>
     */
    @PostMapping("/reservationRequest/reservationStatusYearly")
    public ResponseEntity<Collection<ReservationStatusYearlyProjection>>
    getReservationStatusYearly(@RequestBody @Valid final InformationRequestVm informationRequestVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.getReservationStatusYearly(informationRequestVm));
    }

    /**
     * @return Collection<ProcessSummaryMonthlyRequestDistribution>
     */
    @PostMapping("/reservationRequest/reservationDetails")
    public ResponseEntity<Collection<ReservationRequestDetails>>
    getReservationDetails(@RequestBody @Valid final InformationRequestVm informationRequestVm) {
        return ResponseEntity.ok()
                .body(dashBoardService.getReservationDetails(informationRequestVm));
    }
}
