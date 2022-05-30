package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.persistence.domains.sitemap.SiteMap;
import com.dw.ngms.cis.persistence.domains.sitemap.SiteRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteMapRepository extends JpaRepository<SiteMap, Long> {
    SiteRole findByDescription(final String description);


}


