package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.persistence.domain.SearchTemplateAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchTemplateAuditRepository extends JpaRepository<SearchTemplateAudit, Long> {

    SearchTemplateAudit findBySearchTemplateAuditId(final Long searchTemplateAuditId);

}
