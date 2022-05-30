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
 * @since : 2021/06/18, Fri
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessRuleProjection {

    @CsvBindByName(column = "Province Name")
    @DisplayName("Province Name")
    private String province;

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

    @CsvBindByName(column = "Business Rule Details")
    @DisplayName("Business Rule Details")
    private String  businessRuleDetails;

}
