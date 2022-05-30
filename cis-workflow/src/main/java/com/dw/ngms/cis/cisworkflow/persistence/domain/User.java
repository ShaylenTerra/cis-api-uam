package com.dw.ngms.cis.cisworkflow.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author : prateekgoel
 * @since : 07/06/21, Mon
 **/
@Data
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @Column(name = "USERID", updatable = false,insertable = false)
    private Long userId;

    @Column(name = "USERCODE")
    private String userCode;

    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "USERTYPEITEMID")
    private Long userTypeItemId;

    @Column(name = "PASSWORD")
    @JsonIgnore
    private String password;

    @Transient
    @JsonIgnore
    private String tempPassword;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "SURNAME")
    private String surName;

    @Column(name = "MOBILENO")
    private String mobileNo;

    @Column(name = "TELEPHONENO")
    private String telephoneNo;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "STATUSITEMID")
    private Long statusId;

    @Column(name = "CREATEDDATE")
    @JsonIgnore
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "LASTUPDATEDDATE")
    @JsonIgnore
    private LocalDateTime lastUpdatedDate = LocalDateTime.now();

    @Column(name = "COUNTRYCODE")
    private String countryCode;

    @Column(name = "TITLEITEMID")
    private Long titleItemId;

    @Column(name = "PROVINCEID")
    private Long provinceId;

}
