package com.dw.ngms.cis.cisworkflow.persistence.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Getter
@Setter
@Entity
@Table(name = "ROLES")
public class Roles {

    @Id
    @Column(name = "ROLEID")
    private int roleId;

    @Column(name = "ROLENAME")
    private String roleName;

    @Column(name = "USERTYPEITEMID")
    private String userTypeItemId;

    @Column(name = "ROLECODE")
    private String roleCode;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ISACTIVE")
    private String isActive;

    @Column(name = "SECTION_ITEMID")
    private Long sectionItemId;

    @Column(name = "APPROVAL_REQUIRED")
    private Long approvalRequired;

}
