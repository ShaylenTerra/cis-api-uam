package com.dw.ngms.cis.persistence.repository.cart;

import com.dw.ngms.cis.persistence.domains.cart.CartStageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CartStageDataRepository extends JpaRepository<CartStageData, Long> {

	Collection<CartStageData> findByUserIdAndProvinceIdAndSearchTypeIdAndDataKey(final Long userId,
																				 final Long provinceId,
																				 final Long searchTypeId,
																				 final String dataKey);

	Collection<CartStageData> findByCartId(final Long cartId);

	int deleteCartStageDataByIdAndCartId(final Long id, final Long cartId);
}
