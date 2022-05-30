package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.persistence.domains.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author : Name
 * @since : 19/11/20, Thu
 **/
@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

    Template findByTemplateName(String templateName);

    Template findByTemplateId(final Long templateId);

    Collection<Template> findByItemIdModule(final Long itemIdModule);

}
