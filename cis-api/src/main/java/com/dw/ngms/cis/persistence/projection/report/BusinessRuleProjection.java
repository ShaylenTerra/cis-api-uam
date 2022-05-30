package com.dw.ngms.cis.persistence.projection.report;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author : pragayanshu
 * @since : 2021/06/18, Fri
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessRuleProjection {

    @CsvBindByName(column = "Province Name")
    @DisplayName("Province Name")
    private String province;

    @CsvBindByName(column = "Process Name")
    @DisplayName("Process Name")
    private String processName;

    @CsvBindByName(column = "Module Name")
    @DisplayName("Module Name")
    private String moduleName;

    @CsvBindByName(column = "Dated")
    @CsvDate(value = "yyyy-mm-dd  HH:mm:ss")
    @DisplayName("Dated")
    private Timestamp dated;

    @CsvBindByName(column = "User Name")
    @DisplayName("User Name")
    private String userName;

    @CsvBindByName(column = "Context")
    @DisplayName("Context")
    private String context;

    @CsvBindByName(column = "Turnaround Time")
    @DisplayName("Turnaround Time")
    private Long turnaroundTime;

    @CsvBindByName(column = "Notification Time")
    @DisplayName("Notification Time")
    private Long notificationTime;

    @CsvBindByName(column = "Reference Number Sample")
    @DisplayName("Reference Number Sample")
    private String referenceNumberSample;
}
