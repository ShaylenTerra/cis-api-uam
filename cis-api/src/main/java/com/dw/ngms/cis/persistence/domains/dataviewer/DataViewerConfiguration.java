package com.dw.ngms.cis.persistence.domains.dataviewer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DATA_VIEWER_CONFIGURATION")
public class DataViewerConfiguration {

    @Id
    @Column(name = "OBJECT_NAME")
    private String objectName;

    @Column(name = "OBJECT_TYPE")
    private String objectType;

    @Column(name = "ACTIVE", columnDefinition = "CHAR")
    private String active;

    @Column(name = "ISPROVINCE", columnDefinition = "CHAR")
    private String isProvince;

    @Column(name = "DESCRIPTION")
    private String description;

}
