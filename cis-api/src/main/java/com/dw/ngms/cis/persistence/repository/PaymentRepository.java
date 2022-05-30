package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.persistence.domains.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

/**
 * @author : prateekgoel
 * @since : 06/01/21, Wed
 **/
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Modifying
    int deleteByWorkflowId(final Long workflowId);

    Payment findByWorkflowId(final Long workflowId);

}
