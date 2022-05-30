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
public class ReservationProductionProjection {

    @CsvBindByName(column = "REFERENCE_NO")
    @DisplayName("Ref#")
    private String referenceNumber;

    @CsvBindByName(column = "Property Category")
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

    @CsvBindByName(column = "Date Received Scrutinize")
    @DisplayName("Date Received")
    private Timestamp scrutinizerReceived;

    @CsvBindByName(column = "Scrutinizer Name")
    @DisplayName("Name")
    private String scrutnizerName;

    @CsvBindByName(column = "Scrutinizer Productivity (Minutes)")
    @DisplayName("Productivity (Minutes)")
    private Long scrutnizerTimeTaken;

    @CsvBindByName(column = "Date Reserved")
    @DisplayName("Date Reserved")
    private Timestamp dateReserved;

    @CsvBindByName(column = "Productivity Minutes")
    @DisplayName("Productivity Minutes")
    private Long taskTotalProductivity;

    @CsvBindByName(column = "Productivity Minutes")
    @DisplayName("Productivity Minutes")
    private Long totalProductivity;
}
