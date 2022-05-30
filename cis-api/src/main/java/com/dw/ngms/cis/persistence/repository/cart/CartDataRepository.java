package com.dw.ngms.cis.persistence.repository.cart;

import com.dw.ngms.cis.persistence.domains.cart.CartData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CartDataRepository extends JpaRepository<CartData, Long> {

    Collection<CartData> findByWorkflowId(final Long workflowId);

}
