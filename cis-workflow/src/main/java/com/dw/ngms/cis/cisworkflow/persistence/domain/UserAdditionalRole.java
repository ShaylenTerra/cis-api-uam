package com.dw.ngms.cis.cisworkflow.persistence.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "USER_ADDITIONAL_ROLE")
public class UserAdditionalRole {

    @Id
    @Column(name = "UARID")
    private Long uarId;

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "PROVINCEID")
    private Long provinceId;

    @Column(name = "SECTION_ITEMID")
    private Long sectionItemId;

    @Column(name = "ISPRIMARY")
    private Long isPrimary;

    @Column(name = "ROLEID")
    private Long roleId;

    @Column(name = "ASSIGNEDDATE")
    private LocalDateTime assignedDate;

}
