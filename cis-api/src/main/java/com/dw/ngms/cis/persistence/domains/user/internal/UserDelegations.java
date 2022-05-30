package com.dw.ngms.cis.persistence.domains.user.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 19/12/20, Sat
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_DELEGATIONS")
public class UserDelegations {

    @Id
    @GeneratedValue(generator = "user_delegations_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_delegations_seq", sequenceName = "USER_DELEGATIONS_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long delegationId;

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "DELEGATE_USERID")
    private Long delegateUserId;

    @Column(name = "FROM_DATE")
    private Date fromDate;

    @Column(name = "TO_DATE")
    private Date toDate;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUSITEMID")
    private Long statusItemId;

}

