package com.dw.ngms.cis.persistence.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "VW_WF_NOTIFICATIONS")
public class VwWfNotifications {

    @Id
    @Column(name = "REFERENCE_NO", insertable = false, updatable = false)
    private String referenceNo;

    @Column(name = "SUB_REFERENCE_NO")
    private String subReferenceNo;

    @Column(name = "DATERECEIVED")
    private LocalDateTime dateReceived;

    @Column(name = "PRODUCTCATEGORY")
    private String productCategory;

    @Column(name = "DATEALLOCATED")
    private LocalDateTime dateAllocated;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USERPROVINCE")
    private String userProvince;

    @Column(name = "USER_ROLE")
    private String userRole;

    @Column(name = "NOTIFICATION_TYPE")
    private String notificationType;

    @Column(name = "USERCOMMENTS")
    private String userComments;

    @Column(name = "PRODUCTIVITY_MINUTES")
    private Long productivityMinutes;

    @Column(name = "INTERNALSTATUSCAPTION")
    private String internalStatusCaption;

    @Column(name = "COMPLETEDON")
    private LocalDateTime completedOn;

    @Column(name = "USERID")
    private Long userid;

}
