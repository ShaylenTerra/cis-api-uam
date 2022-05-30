package com.dw.ngms.cis.persistence.repository.fee;

import com.dw.ngms.cis.persistence.domains.fee.FeeSubCategory;
import com.dw.ngms.cis.service.dto.simulator.LodegementCategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 27/11/20, Fri
 **/
@Repository
public interface FeeSubCategoryRepository extends JpaRepository<FeeSubCategory, Long> {

    Collection<FeeSubCategory> findByCategoryId(final Long categoryId);

}
