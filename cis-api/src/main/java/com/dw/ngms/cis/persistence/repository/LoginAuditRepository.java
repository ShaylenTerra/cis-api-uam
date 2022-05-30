package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.persistence.domains.LoginAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : prateekgoel
 * @since : 24/11/20, Tue
 **/
@Repository
public interface LoginAuditRepository extends JpaRepository<LoginAudit,Long> {
}
