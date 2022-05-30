package com.dw.ngms.cis.persistence.domains.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "USER_NOTIFICATION")
public class UserNotification {

    @Id
    @Column(name = "NOTIFICATION_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_notifications_seq")
    @SequenceGenerator(name = "user_notifications_seq", sequenceName = "USER_NOTIFICATIONS_SEQ", allocationSize = 1)
    private Long id ;

    @Column(name = "CREATED_ON")
    private Date createOn;

    @Column(name = "CREATEDBY_USER_ID")
    private long createdByUserId;

    @Column(name = "CREATEDFOR_USER_ID")
    private long createdForUserId;

    @Column(name = "SUBJECT")
    private String subject;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CONTEXT_ID")
    private long contextId;

    @Column(name = "CONTEXT_TYPE_ID")
    private long contextTypeId;

    @Column(name = "ACTIVE")
    private Integer active;

}
