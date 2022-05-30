package com.dw.ngms.cis.persistence.repository.fee;

import com.dw.ngms.cis.persistence.domains.fee.FeeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : prateekgoel
 * @since : 27/11/20, Fri
 **/
@Repository
public interface FeeTypeRepository extends JpaRepository<FeeType, Long> {
}
