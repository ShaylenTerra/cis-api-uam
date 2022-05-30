package com.dw.ngms.cis.persistence.projection.report;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author : pragayanshu
 * @since : 2021/06/23, Wed
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateHistoryProjection {

    @CsvBindByName(column = "User Creation Date")
    @CsvDate(value = "yyyy-mm-dd  HH:mm:ss")
    @DisplayName("User Creation Date")
    private Timestamp userCreationDate;


    @CsvBindByName(column = "Last Login Date")
    @CsvDate(value = "yyyy-mm-dd  HH:mm:ss")
    @DisplayName("Last Login Date")
    private Timestamp lastLoginDate;

    @CsvBindByName(column = "Full Name")
    @DisplayName("Full Name")
    private String fullName;

    @CsvBindByName(column = "User Type")
    @DisplayName("User Type")
    private String userType;

    @CsvBindByName(column = "Organisation")
    @DisplayName("Organisation")
    private String organisation;

    @CsvBindByName(column = "Sector")
    @DisplayName("Sector")
    private String sector;

    @CsvBindByName(column = "Role")
    @DisplayName("Role")
    private String Role;

    @CsvBindByName(column = "Province")
    @DisplayName("Province")
    private String province;

    @CsvBindByName(column = "User Name")
    @DisplayName("User Name")
    private String userName;

    @CsvBindByName(column = "Status")
    @DisplayName("Status")
    private String status;

    @CsvBindByName(column = "Additional Roles")
    @DisplayName("Additional Roles")
    private String additionalRole;

    @CsvBindByName(column = "Section")
    @DisplayName("Section")
    private String Section;
}
