package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.persistence.domains.TemplatesAudit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TemplatesAuditRepository extends JpaRepository<TemplatesAudit, Long> {

    Page<TemplatesAudit> findAllByTemplateIdOrderByDatedDesc(final Long templateId,
                                                                        final Pageable pageable);

}
