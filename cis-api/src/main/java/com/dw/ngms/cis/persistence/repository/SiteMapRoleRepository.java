package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.persistence.domains.sitemap.SiteMapRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteMapRoleRepository extends JpaRepository<SiteMapRole, Long> {

    SiteMapRole findByRoleId(final Long roleId);

    @Modifying
    int deleteByRoleId(final Long roleId);

}
