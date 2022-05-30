package com.dw.ngms.cis.persistence.repository.cart;

import com.dw.ngms.cis.persistence.domains.cart.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 26/01/21, Tue
 **/
@Repository
public interface CartItemRepository extends JpaRepository<CartItems, Long> {

    Collection<CartItems> findAllByWorkflowIdOrderBySno(final Long workflowId);

    @Modifying
    int deleteAllByWorkflowId(final Long workflowId);

}
