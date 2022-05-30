package com.dw.ngms.cis.persistence.repository.cart;

import com.dw.ngms.cis.persistence.domains.cart.CartDispatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : prateekgoel
 * @since : 11/02/21, Thu
 **/
@Repository
public interface CartDispatchRepository extends JpaRepository<CartDispatch, Long> {

    CartDispatch findByWorkflowId(final Long workflowId);

}
