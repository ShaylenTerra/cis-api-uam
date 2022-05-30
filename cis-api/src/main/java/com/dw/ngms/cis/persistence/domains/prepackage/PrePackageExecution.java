package com.dw.ngms.cis.persistence.domains.prepackage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author : prateekgoel
 * @since : 04/01/21, Mon
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRE_PACKAGE_EXCUTION")
public class PrePackageExecution {

    @Id
    @Column(name = "EXE_UID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pre_package_execution_sequence")
    @SequenceGenerator(name = "pre_package_execution_sequence", sequenceName = "PRE_PACKAGE_EXECUTION_SEQ", allocationSize = 1)
    private Long execId;

    @Column(name = "SUBSCRIPTION_ID")
    private Long subscriptionId;

    @Column(name = "EXCUTION_DATE")
    private LocalDateTime executionDate;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "NEXT_EXCUTION_DATE")
    private LocalDateTime nextExecutionDate;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "FTP_LOCATION")
    private String ftpLocation;

    @Column(name = "NOTIFICATION_STATUS")
    private String notificationStatus;

}
