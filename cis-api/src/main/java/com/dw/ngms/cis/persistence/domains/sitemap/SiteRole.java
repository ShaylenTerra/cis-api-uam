package com.dw.ngms.cis.persistence.domains.sitemap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ROLES")
@Deprecated
public class SiteRole {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_sequence")
    @SequenceGenerator(name = "roles_sequence", sequenceName = "ROLES_SEQ", allocationSize = 1)
    @Column(name = "ROLEID", nullable = false)
    private Long roleId;

    @Column(name = "ROLENAME", nullable = true)
    private String roleName;

    @Column(name = "USERTYPEITEMID", nullable = true)
    private Long userTypeItemId;

    @Column(name = "DESCRIPTION", nullable = true, length = 500)
    private String description;

    @Column(name = "ISACTIVE", nullable = true)
    private String isActive;

    @Column(name = "ROLECODE", nullable = true)
    private String roleCode;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ROLE_ID")
    private Set<SiteMapRole> siteMapRoles;
}
