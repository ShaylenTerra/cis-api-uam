package com.dw.ngms.cis.persistence.projection.report.reservation;

import com.dw.ngms.cis.persistence.projection.report.DisplayName;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author : pragayanshu
 * @since : 2022/02/28, Mon
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationStatusProjection {

    @CsvBindByName(column = "REFERENCE_NO")
    @DisplayName("Ref#")
    private String referenceNumber;

    @CsvBindByName(column = "Property Description")
    @DisplayName("Property Description")
    private String propertyDescription;

    @CsvBindByName(column = "#OfLand Parcel Designation")
    @DisplayName("#OfLand Parcel Designation")
    private String noOfParentDesignation;

    @CsvBindByName(column = "Designation Type")
    @DisplayName("Designation Type")
    private String reservationType;

    @CsvBindByName(column = "#OfLand Parcel Designation")
    @DisplayName("#OfLand Parcel Designation")
    private String noOfRequestedDesignation;

    @CsvBindByName(column = "Date Received")
    @CsvDate(value = "yyyy-mm-dd  HH:mm:ss")
    @DisplayName("Date Received")
    private Timestamp dateReceived;

    @CsvBindByName(column = "Requester Name")
    @DisplayName("Name")
    private String applicantFullName;

    @CsvBindByName(column = "Requester Type")
    @DisplayName("Type")
    private String applicantType;

    @CsvBindByName(column = "Requester Role")
    @DisplayName("Role")
    private String applicantRole;

    @CsvBindByName(column = "Date Received Officer")
    @CsvDate(value = "yyyy-mm-dd  HH:mm:ss")
    @DisplayName("Date Received")
    private Timestamp officerReceived;

    @CsvBindByName(column = "Officer Name")
    @DisplayName("Name")
    private String officerName;

    @CsvBindByName(column = "Officer Productivity (Minutes)")
    @DisplayName("Productivity (Minutes)")
    private Long officerTimeTaken;

    @CsvBindByName(column = "Manager Name")
    @DisplayName("Name")
    private String managerName;

    @CsvBindByName(column = "Received Manager")
    @CsvDate(value = "yyyy-mm-dd  HH:mm:ss")
    @DisplayName("Date Received")
    private Timestamp managerReceived;

    @CsvBindByName(column = "Reservation Status")
    @DisplayName("Reservation Status")
    private String reservationStatus;

    @CsvBindByName(column = "Task Status")
    @DisplayName("Task Status")
    private String taskStatus;
}
