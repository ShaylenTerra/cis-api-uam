package com.dw.ngms.cis.persistence.repository.cart;

import com.dw.ngms.cis.persistence.domains.cart.CartStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartStageRepository extends JpaRepository<CartStage, Long> {

	CartStage findByUserId(final Long userId);

	CartStage findByCartId(final Long cartId);

	int deleteCartStageByCartId(final Long cartId);
}
