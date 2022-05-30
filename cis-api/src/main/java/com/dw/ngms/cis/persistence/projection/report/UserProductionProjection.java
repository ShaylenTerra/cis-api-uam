package com.dw.ngms.cis.persistence.projection.report;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author : pragayanshu
 * @since : 2021/06/19, Sat
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProductionProjection {

    @CsvBindByName(column = "Reference Number")
    @DisplayName("Reference Number")
    private String referenceNumber;

    @CsvBindByName(column = "Date Received")
    @CsvDate(value = "yyyy-mm-dd  HH:mm:ss")
    @DisplayName("Date Received")
    private Timestamp dateReceived;

    @CsvBindByName(column = "Product Category")
    @DisplayName("Product Category")
    private String productCategory;

    @CsvBindByName(column = "Product Item")
    @DisplayName("Product Item")
    private String productItem;

    @CsvBindByName(column = "Product Quantity")
    @DisplayName("Product Quantity")
    private Long productQuantity;

    @CsvBindByName(column = "Requester Name")
    @DisplayName("Name")
    private String requester;

    @CsvBindByName(column = "Requester Type")
    @DisplayName("Type")
    private String requesterType;

    @CsvBindByName(column = "Requester Role")
    @DisplayName("Role")
    private String requesterRole;

    @CsvBindByName(column = "Requester Sector")
    @DisplayName("Sector")
    private String requesterSector;

    @CsvBindByName(column = "Date Allocated Officer")
    @CsvDate(value = "yyyy-mm-dd  HH:mm:ss")
    @DisplayName("Date Allocated")
    private Timestamp dateAllocated;

    @CsvBindByName(column = "Officer Name")
    @DisplayName("Name")
    private String officer;

    @CsvBindByName(column = "Date Completed")
    @CsvDate(value = "yyyy-mm-dd  HH:mm:ss")
    @DisplayName("Date Completed")
    private Timestamp dateCompleted;

    @CsvBindByName(column = "Action Context")
    @DisplayName("Action Context")
    private String actionContext;

    @CsvBindByName(column = "Productivity (Minutes)")
    @DisplayName("Productivity (Minutes)")
    private Long productivityMinutes;

    @CsvBindByName(column = "Cost")
    @DisplayName("Cost")
    private Long cost;

    @CsvBindByName(column = "Invoice Number")
    @DisplayName("Invoice Number")
    private String invoiceNumber;

    @CsvBindByName(column = "Status")
    @DisplayName("Status")
    private String status;

}
