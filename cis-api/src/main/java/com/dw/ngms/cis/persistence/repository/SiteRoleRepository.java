package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.persistence.domains.sitemap.SiteRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value="siteRoleRepository")
public interface SiteRoleRepository extends JpaRepository<SiteRole,Long> {
    SiteRole findByRoleName(final String roleName);
}

