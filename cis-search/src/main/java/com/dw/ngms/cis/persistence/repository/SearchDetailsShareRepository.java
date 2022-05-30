package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.persistence.domain.SearchDetailsShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : prateekgoel
 * @since : 26/06/21, Sat
 **/
@Repository
public interface SearchDetailsShareRepository extends JpaRepository<SearchDetailsShare,Long> {

    SearchDetailsShare findByShareId(final Long shareId);

}
