package com.dw.ngms.cis.persistence.repository.fee;

import com.dw.ngms.cis.persistence.domains.fee.FeeScale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : prateekgoel
 * @since : 27/11/20, Fri
 **/
@Repository
public interface FeeScaleRepository extends JpaRepository<FeeScale, Long> {

    FeeScale findByFeeScaleId(final Long feeScaleId);

}
