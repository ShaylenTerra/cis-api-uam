package com.dw.ngms.cis.enums;

import java.util.stream.Stream;

/**
 * @author : prateekgoel
 * @since : 15/06/21, Tue
 **/
public enum ReportType {

    USER_SUMMARY(1L),
    USER_LOGIN_SUMMARY(2L),
    USER_HOURLY_LOGIN_DISTRIBUTION(3L),
    USER_REGISTRATION_QUARTERLY(4L),
    USER_UPDATE_QUARTERLY(5L),
    USER_UPDATED_HISTORY(6L),
    BUSINESS_RULE(7L),
    PRODUCTION(8L),
    USER_PRODUCTION(9L),
    NOTIFICATION(10L),
    REQUEST_STATISTICAL(11L),
    RESERVATION_PRODUCTION(12L),
    RESERVATION_USER(13L),
    RESERVATION_MANGER_NOTIFICATION(14L),
    RESERVATION_STATUS_REPORT(15L),
    RESERVATION_BUSINESS_RULE(16L),
    LODGEMENT_NOTIFICATION_REPORT(23L),
    LODEGEMENT_BUSINESSRULE_REPORT(24L),
    LODEGEMENT_PRODUCTION_REPORT(20L);


    private final Long reportType;

    ReportType(Long reportType) {
        this.reportType = reportType;
    }

    public static ReportType of(Long reportType) {
        return Stream.of(ReportType.values())
                .filter(u -> u.getReportType().equals(reportType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unregistered Report Type"));
    }

    public Long getReportType() {
        return this.reportType;
    }
}
