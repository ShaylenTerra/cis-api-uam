package com.dw.ngms.cis.persistence.projection.report;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author : prateekgoel
 * @since : 05/05/21, Wed
 **/
@Data
public class UserLoginSummaryProjection {

    @CsvBindByName(column = "Login Date")
    @CsvDate(value = "yyyy-mm-dd  HH:mm:ss")
    @DisplayName("Login Date")
    private Timestamp loginDate;

    @CsvBindByName(column = "Total User")
    @DisplayName("Total User")
    private Long totalUser;

    @CsvBindByName(column = "Province Name")
    @DisplayName("Province Name")
    private String provinceName;

}
