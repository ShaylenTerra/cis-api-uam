package com.dw.ngms.cis.persistence.domains;

import com.dw.ngms.cis.enums.UserType;
import com.dw.ngms.cis.persistence.domains.user.UserMetaData;
import com.dw.ngms.cis.persistence.domains.user.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by nirmal on 2019/03/24.
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS")
public class User implements Serializable {

    @Id
    @Column(name = "USERID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_sequence")
    @SequenceGenerator(name = "user_id_sequence", sequenceName = "USERS_SEQ", allocationSize = 1)
    private Long userId;

    @Column(name = "USERCODE")
    private String userCode;

    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "USERTYPEITEMID")
    private UserType userTypeItemId;

    @Column(name = "PASSWORD")
    @JsonIgnore
    private String password;

    @Transient
    @JsonIgnore
    private String tempPassword;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "SURNAME")
    private String surname;

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

    @Column(name = "RESETPASSWORD")
    private Long resetPassword;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserMetaData userMetaData;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserRole> userRoles = new LinkedHashSet<>();

    public User(String firstName, String surname, String email, String userName) {
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.userName = userName;
    }
}
