package com.dw.ngms.cis.service.report;

import com.dw.ngms.cis.enums.ReportType;
import com.dw.ngms.cis.service.report.Lodgement.LodgementBusinessRuleReport;
import com.dw.ngms.cis.service.report.Lodgement.LodgementNotificationReport;
import com.dw.ngms.cis.service.report.Lodgement.LodgementProductionReport;
import com.dw.ngms.cis.service.report.reservation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Optional;

/**
 * @author : prateekgoel
 * @since : 15/06/21, Tue
 **/
@Component
public class GenericReportFactory {

    private final UserSummaryReport userSummaryReport;

    private final UserLoginSummaryReport userLoginSummaryReport;

    private final UserHourlyLoginSummaryReport userHourlyLoginSummaryReport;

    private final UserRegistrationQuarterlyReport userRegistrationQuarterlyReport;

    private final UserUpdateQuarterlyReport userUpdateQuarterlyReport;

    private final NotificationReport notificationReport;

    private final ProductionReport productionReport;

    private final RequestStatisticalReport requestStatisticalReport;

    private final UserProductionReport userProductionReport;

    private final UserUpdateHistoryReport userUpdateHistoryReport;

    private final BusinessReport businessReport;

    private final ReservationProductionReport reservationProductionReport;

    private final UserReport userReport;

    private final ReservationNotificationReport reservationNotificationReport;
    private final ReservationStatusReport reservationStatusReport;
    private final ReservationBusinessReport reservationBusinessReport;
    private final LodgementNotificationReport lodgementNotificationReport;
    private final LodgementBusinessRuleReport lodgementBusinessRuleReport;
    private final LodgementProductionReport lodgementProductionReport;

    private final EnumMap<ReportType, GenericReport> registeredReportType = new EnumMap(ReportType.class);

    @Autowired
    public GenericReportFactory(UserSummaryReport userSummaryReport,
                                UserLoginSummaryReport userLoginSummaryReport,
                                UserHourlyLoginSummaryReport userHourlyLoginSummaryReport,
                                UserRegistrationQuarterlyReport userRegistrationQuarterlyReport,
                                UserUpdateQuarterlyReport userUpdateQuarterlyReport,
                                NotificationReport notificationReport,
                                ProductionReport productionReport,
                                RequestStatisticalReport requestStatisticalReport,
                                UserProductionReport userProductionReport,
                                UserUpdateHistoryReport userUpdateHistoryReport,
                                BusinessReport businessReport,
                                ReservationProductionReport reservationProductionReport,
                                UserReport userReport,
                                ReservationNotificationReport reservationNotificationReport,
                                ReservationStatusReport reservationStatusReport,
                                ReservationBusinessReport reservationBusinessReport, LodgementNotificationReport lodgementNotificationReport, LodgementBusinessRuleReport lodgementBusinessRuleReport, LodgementProductionReport lodgementProductionReport) {
        this.userSummaryReport = userSummaryReport;
        this.userLoginSummaryReport = userLoginSummaryReport;
        this.userHourlyLoginSummaryReport = userHourlyLoginSummaryReport;
        this.userRegistrationQuarterlyReport = userRegistrationQuarterlyReport;
        this.userUpdateQuarterlyReport = userUpdateQuarterlyReport;
        this.notificationReport = notificationReport;
        this.productionReport = productionReport;
        this.requestStatisticalReport = requestStatisticalReport;
        this.userProductionReport = userProductionReport;
        this.userUpdateHistoryReport = userUpdateHistoryReport;
        this.businessReport = businessReport;
        this.reservationProductionReport = reservationProductionReport;
        this.userReport = userReport;
        this.reservationNotificationReport = reservationNotificationReport;
        this.reservationStatusReport = reservationStatusReport;
        this.reservationBusinessReport = reservationBusinessReport;
        this.lodgementNotificationReport = lodgementNotificationReport;
        this.lodgementBusinessRuleReport = lodgementBusinessRuleReport;
        this.lodgementProductionReport = lodgementProductionReport;
    }


    public GenericReportFactory buildFactory() {
        registeredReportType.put(ReportType.USER_SUMMARY, userSummaryReport);
        registeredReportType.put(ReportType.USER_LOGIN_SUMMARY, userLoginSummaryReport);
        registeredReportType.put(ReportType.USER_HOURLY_LOGIN_DISTRIBUTION, userHourlyLoginSummaryReport);
        registeredReportType.put(ReportType.USER_REGISTRATION_QUARTERLY, userRegistrationQuarterlyReport);
        registeredReportType.put(ReportType.USER_UPDATE_QUARTERLY, userUpdateQuarterlyReport);
        registeredReportType.put(ReportType.USER_UPDATED_HISTORY, userUpdateHistoryReport);
        registeredReportType.put(ReportType.BUSINESS_RULE, businessReport);
        registeredReportType.put(ReportType.PRODUCTION, productionReport);
        registeredReportType.put(ReportType.USER_PRODUCTION, userProductionReport);
        registeredReportType.put(ReportType.NOTIFICATION, notificationReport);
        registeredReportType.put(ReportType.REQUEST_STATISTICAL, requestStatisticalReport);
        registeredReportType.put(ReportType.RESERVATION_PRODUCTION,reservationProductionReport);
        registeredReportType.put(ReportType.RESERVATION_USER,userReport);
        registeredReportType.put(ReportType.RESERVATION_MANGER_NOTIFICATION,reservationNotificationReport);
        registeredReportType.put(ReportType.RESERVATION_STATUS_REPORT,reservationStatusReport);
        registeredReportType.put(ReportType.RESERVATION_BUSINESS_RULE,reservationBusinessReport);
        registeredReportType.put(ReportType.LODGEMENT_NOTIFICATION_REPORT,lodgementNotificationReport);
        registeredReportType.put(ReportType.LODEGEMENT_BUSINESSRULE_REPORT,lodgementBusinessRuleReport);
        registeredReportType.put(ReportType.LODEGEMENT_PRODUCTION_REPORT, lodgementProductionReport);
        return this;
    }

    public Optional<GenericReport> getReport(ReportType reportType) {
        return Optional.ofNullable(registeredReportType.get(reportType));
    }

}
