package com.dw.ngms.cis.persistence.domains.sitemap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "SITEMAP_ROLE")
public class SiteMapRole {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "site_map_role_seq")
    @SequenceGenerator(sequenceName = "SITE_MAP_ROLE_SEQ", allocationSize = 1, name = "site_map_role_seq")
    @Column(name = "ID",nullable = false)
    private Long siteMapRoleID;

    @Column(name = "ACTIVE")
    @Type(type = "true_false")
    private Boolean isAccessible;

    @Column(name = "ROLE_ID")
    private Long roleId;

    @Column(name = "SITEMAPID")
    private Long siteMapId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SITEMAPID", insertable = false, updatable = false)
    private SiteMap siteMap;
}
