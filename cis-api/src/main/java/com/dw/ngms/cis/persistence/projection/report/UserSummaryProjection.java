package com.dw.ngms.cis.persistence.projection.report;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author : prateekgoel
 * @since : 06/02/21, Sat
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSummaryProjection {

    @DisplayName("User Creation Date")
    @CsvBindByName(column = "User Creation Date")
    @CsvDate(value = "yyyy-mm-dd  HH:mm:ss")
    private Timestamp userCreationDate;

    @DisplayName("Last Login Date")
    @CsvBindByName(column = "Last Login Date")
    @CsvDate(value = "yyyy-mm-dd HH:mm:ss")
    private Timestamp lastLoginDate;

    @DisplayName("Full Name")
    @CsvBindByName(column = "Full Name")
    private String fullName;

    @DisplayName("User Type")
    @CsvBindByName(column = "User Type")
    private String userType;

    @DisplayName("Organisation")
    @CsvBindByName(column = "Organisation")
    private String organisation;

    @DisplayName("Sector")
    @CsvBindByName(column = "Sector")
    private String sector;

    @DisplayName("Role")
    @CsvBindByName(column = "Role")
    private String role;

    @CsvBindByName(column = "Province")
    @DisplayName("Province")
    private String province;

    @CsvBindByName(column = "User Name")
    @DisplayName("User Name")
    private String userName;

    @CsvBindByName(column = "Status")
    @DisplayName("Status")
    private String status;

    @CsvBindByName(column = "Additional Role")
    @DisplayName("Additional Role")
    private String additionalRole;

    @CsvBindByName(column = "Section")
    @DisplayName("Section")
    private String section;
}
