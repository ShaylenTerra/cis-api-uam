package com.dw.ngms.cis.persistence.projection.report.Lodgement;

import com.dw.ngms.cis.persistence.projection.report.DisplayName;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
/**
 * @author : AnkitSaxena
 * @since : 11/04/22, Mon
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor

public class LodgementNotificationReportProjection {
    @CsvBindByName(column = "Reference Number")
    @DisplayName("Reference Number")
    private String referenceNumber;

    @CsvBindByName(column = "Sub Reference Number")
    @DisplayName("Sub Reference Number")
    private String subReferenceNumber;

    @CsvBindByName(column = "Date Received")
    @CsvDate(value = "yyyy-mm-dd  HH:mm:ss")
    @DisplayName("Date Received")
    private Timestamp dateReceived;

    @CsvBindByName(column = "Property Description")
    @DisplayName("Property Description")
    private String propertyDescription;

    @CsvBindByName(column = "Date Allocated")
    @CsvDate(value = "yyyy-mm-dd  HH:mm:ss")
    @DisplayName("Date Allocated")
    private Timestamp dateAllocated;

    @CsvBindByName(column = "User Name")
    @DisplayName("User Name")
    private String userName;

    @CsvBindByName(column = "User Role")
    @DisplayName("User Role")
    private String userRole;

    @CsvBindByName(column = "Date Officer Notified")
    @CsvDate(value = "yyyy-mm-dd  HH:mm:ss")
    @DisplayName("Date Officer Notified")
    private Timestamp dateOfficerNotified;

    @CsvBindByName(column = "Notification Type")
    @DisplayName("Notification Type")
    private String notificationType;

    @CsvBindByName(column = "User Comments")
    @DisplayName("User Comments")
    private String userComments;

    @CsvBindByName(column = "status")
    @DisplayName("Status")
    private String status;


}
