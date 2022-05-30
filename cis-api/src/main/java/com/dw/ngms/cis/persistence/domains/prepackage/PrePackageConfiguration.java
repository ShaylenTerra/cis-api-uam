package com.dw.ngms.cis.persistence.domains.prepackage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author : prateekgoel
 * @since : 04/01/21, Mon
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRE_PACKAGE_CONFIGURATION")
public class PrePackageConfiguration {

    @Id
    @Column(name = "PRE_PACKAGE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pre_package_configuration_sequence")
    @SequenceGenerator(name = "pre_package_configuration_sequence", sequenceName = "PRE_PACKAGE_CONFIG_SEQ", allocationSize = 1)
    private Long prePackageId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SAMPLE_IMAGE")
    private String sampleImage;

    @Column(name = "PRE_PACKAGE_DATA_TYPE")
    private Long prePackageDataType;

    @Column(name = "COST")
    private Long cost;

    @Column(name = "ACTIVE")
    private Integer active;

    @Column(name = "CONFIGRATION_DATA")
    private String configurationData;

    @Column(name = "TRASNACTIONID")
    private Long transactionId;

    @Column(name = "FOLDER")
    private String folder;
}
