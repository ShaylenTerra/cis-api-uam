package com.dw.ngms.cis.persistence.projection.report;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author : prateekgoel
 * @since : 16/06/21, Wed
 **/
@Data
public class UserRegistrationQuarterlyProjection {

    @CsvBindByName(column = "Province Name")
    @DisplayName("Province Name")
    private String provinceName;

    @CsvBindByName(column = "User Type")
    @DisplayName("User Type")
    private String userType;

    @CsvBindByName(column = "Year")
    @DisplayName("Year")
    private String year;

    @CsvBindByName(column = "Quarter")
    @DisplayName("Quarter")
    private String quarter;

    @CsvBindByName(column = "Total User Registration")
    @DisplayName("Total User Registration")
    private Long totalUserRegistration;

    @CsvBindByName(column = "Registration Date")
    @CsvDate(value = "yyyy-mm-dd  HH:mm:ss")
    @DisplayName("Registration Date")
    private Timestamp registrationDate;

}
