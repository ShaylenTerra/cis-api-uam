package com.dw.ngms.cis.persistence.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "V_USERS")
public class VUsers {

    @Id
    @Column(name = "USERID")
    private Long userId;

    @Column(name = "USERCREATIONDATE")
    private LocalDate userCreationDate;

    @Column(name = "FULLNAME")
    private String fullName;

    @Column(name = "USERTYPEITEMID")
    private Long userTypeItemId;

    @Column(name = "USERTYPE")
    private String userType;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "STATUSITEMID")
    private Long statusItemId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "ROLE_ID")
    private Long roleId;

    @Column(name = "ROLENAME")
    private String roleName;

    @Column(name = "ADDITIONAL_ROLEID")
    private String additionalRoleId;

    @Column(name = "ADDITIONAL_ROLENAME")
    private String additionalRoleName;

    @Column(name = "PROVINCE_ID")
    private Long provinceId;

    @Column(name = "PROVINCE_NAME")
    private String provinceName;

    @Column(name = "ORGANIZATION_ID")
    private Long organizationId;

    @Column(name = "ORGANIZATION_NAME")
    private String organizationName;

    @Column(name = "SECTOR_ID")
    private Long sectorId;

    @Column(name = "SECTOR_NAME")
    private String sectorName;

    @Column(name = "SECTION_ID")
    private Long sectionId;

    @Column(name = "SECTION_NAME")
    private String sectionName;

}
