package com.dw.ngms.cis.persistence.repository.prepackage;

import com.dw.ngms.cis.persistence.domains.prepackage.PrePackageExecution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : prateekgoel
 * @since : 04/01/21, Mon
 **/
@Repository
public interface PrePackageExecutionRepository extends JpaRepository<PrePackageExecution, Long> {

    Page<PrePackageExecution> findAllBySubscriptionId(final Long subscriptionId, final Pageable pageable);
}
