package com.dw.ngms.cis.persistence.domains.prepackage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 04/01/21, Mon
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRE_PACKAGE_SUBSCRIPTION")
public class PrePackageSubscription {

    @Id
    @Column(name = "SUBSCRIPTION_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pre_package_subscription_sequence")
    @SequenceGenerator(name = "pre_package_subscription_sequence", sequenceName = "PRE_PACKAGE_SUBSCRIPTION_SEQ", allocationSize = 1)
    private Long subscriptionId;

    @Column(name = "REFRENCENO")
    private String referenceId;

    @Column(name = "PRE_PACKAGE_ID")
    private Long prePackageId;

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "FREQUENCY_ID")
    private String frequencyId;

    @Column(name = "SUBSCRIPTION_DATE")
    private Date subscriptionDate;

    @Column(name = "LOCATION_TYPE_ID")
    private Long locationTypeId;

    @Column(name = "LOCATION_ID")
    private String locationId;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "SUBSCRIPTION_STATUS")
    private Integer subscriptionStatus;

    @Column(name = "PROCESS_DATA")
    private Integer processData;
}
