package com.dw.ngms.cis.persistence.projection.report;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : pragayanshu
 * @since : 2021/06/23, Wed
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateQuarterlyProjection {

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


}
