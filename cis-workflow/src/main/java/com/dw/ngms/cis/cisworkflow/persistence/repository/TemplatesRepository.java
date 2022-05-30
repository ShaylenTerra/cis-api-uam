package com.dw.ngms.cis.cisworkflow.persistence.repository;

import com.dw.ngms.cis.cisworkflow.persistence.domain.MTemplates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Repository
public interface TemplatesRepository extends JpaRepository<MTemplates, Long> {

    List<MTemplates> findByIsActive(@Param("isActive") Long paramInt);

    MTemplates findByTemplateName(final String templateName);
}
