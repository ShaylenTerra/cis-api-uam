package com.dw.ngms.cis.persistence.domains.system;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "SYSTEM_CONFIGURATION")
public class SystemConfiguration {

    @Id
    @GeneratedValue(generator = "system_configuration_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "system_configuration_seq", sequenceName = "SYSTEM_CONFIGURATION_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TAG")
    private String tag;

    @Column(name = "TAG_VALUE")
    private String tagValue;

    @Column(name = "CREATEDON")
    private LocalDateTime createdOn = LocalDateTime.now();

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "CAPTION")
    private String caption;

}
