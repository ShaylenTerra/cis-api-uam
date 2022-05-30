package com.dw.ngms.cis.persistence.domains.sitemap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "SITEMAP")
public class SiteMap {

    @Id
    @Column(name = "ID")
    private Long id ;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "USER_TYPE")
    private String userType;

    @Column(name = "ICON")
    private String icon;

    @Column(name = "ROUTE")
    private String route;

}
