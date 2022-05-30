package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.persistence.domain.SearchType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : prateekgoel
 * @since : 07/12/20, Mon
 **/
@Repository
public interface SearchTypeRepository extends JpaRepository<SearchType, Long> {

	List<SearchType> findAllByParentSearchTypeIdAndIsActive(final Long parentSearchTypeId,
												 final Long isActive,
												 final Pageable pageable);

}
