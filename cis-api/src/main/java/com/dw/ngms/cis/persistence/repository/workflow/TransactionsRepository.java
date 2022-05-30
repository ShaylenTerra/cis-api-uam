package com.dw.ngms.cis.persistence.repository.workflow;

import com.dw.ngms.cis.persistence.domains.workflow.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : prateekgoel
 * @since : 28/12/20, Mon
 **/
@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
}
