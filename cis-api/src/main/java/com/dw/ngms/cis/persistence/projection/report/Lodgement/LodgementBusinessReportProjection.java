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

public class LodgementBusinessReportProjection {
    @CsvBindByName(column = "ProvinceName")
    @DisplayName("ProvinceName")
    private String ProvinceName;

    @CsvBindByName(column = "ProcessName")
    @DisplayName("ProcessName")
    private String ProcessName;

    @CsvBindByName(column = "ModuleName")
    @DisplayName("ModuleName")
    private String ModuleName;

    @CsvBindByName(column = "Dated")
    @CsvDate(value = "yyyy-mm-dd  HH:mm:ss")
    @DisplayName("Dated")
    private Timestamp Dated;

    @CsvBindByName(column = "userName")
    @DisplayName("userName")
    private String userName;

    @CsvBindByName(column = "context")
    @DisplayName("context")
    private String context;

    @CsvBindByName(column = "BusinessRuleDetails")
    @DisplayName("BusinessRuleDetails")
    private String BusinessRuleDetails;

}
