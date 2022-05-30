package com.dw.ngms.cis.persistence.projection.report.Lodgement;

import com.dw.ngms.cis.persistence.projection.report.DisplayName;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class LodgementProductionReportProjection {
    @CsvBindByName(column = "LodgementReference")
    @DisplayName("LodgementReference")
    private String lodgementReference;

    @CsvBindByName(column = "Professionalpractitioner")
    @DisplayName("Professionalpractitioner")
    private String professionalpractitioner;

    @CsvBindByName(column = "DateSubmitted")
    @CsvDate(value = "yyyy-mm-dd  HH:mm:ss")
    @DisplayName("DateSubmitted")
    private Timestamp dateSubmitted;

    @CsvBindByName(column = "Batch")
    @DisplayName("Batch")
    private String batch;

    @CsvBindByName(column = "SG")
    @DisplayName("SG")
    private String sG;

    @CsvBindByName(column = "SRNumber")
    @DisplayName("SRNumber")
    private String sRNumber;

    @CsvBindByName(column = "LodgementType")
    @DisplayName("LodgementType")
    private String lodgementType;

    @CsvBindByName(column = "LodgementSubType")
    @DisplayName("LodgementSubType")
    private String lodgementSubType;

    @CsvBindByName(column = "ParcelDescription")
    @DisplayName("ParcelDescription")
    private String parcelDescription;

    @CsvBindByName(column = "Ervens")
    @DisplayName("Ervens")
    private String ervens;


    @CsvBindByName(column = "DateLodged")
    @CsvDate(value = "yyyy-mm-dd  HH:mm:ss")
    @DisplayName("DateLodged")
    private Timestamp dateLodged;

    @CsvBindByName(column = "TurnAroundTime")
    @DisplayName("TurnAroundTime")
    private String turnAroundTime;

    @CsvBindByName(column = "Officer")
    @DisplayName("Officer")
    private String officer;

    @CsvBindByName(column = "Scrutinizer")
    @DisplayName("Scrutinizer")
    private String scrutinizer;

    @CsvBindByName(column = "Status")
    @DisplayName("Status")
    private String status;

    @CsvBindByName(column = "DateVerified")
    @CsvDate(value = "yyyy-mm-dd  HH:mm:ss")
    @DisplayName("DateVerified")
    private Timestamp dateVerified;

    @CsvBindByName(column = "AmountPaid")
    @DisplayName("AmountPaid")
    private String amountPaid;

    @CsvBindByName(column = "PaymentMethod")
    @DisplayName("PaymentMethod")
    private String paymentMethod;

    @CsvBindByName(column = "ReceiptNumber")
    @DisplayName("ReceiptNumber")
    private String receiptNumber;

}
