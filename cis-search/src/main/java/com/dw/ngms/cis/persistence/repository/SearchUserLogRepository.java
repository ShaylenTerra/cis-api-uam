package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.persistence.domain.SearchUserLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : prateekgoel
 * @since : 07/12/20, Mon
 **/
public interface SearchUserLogRepository extends JpaRepository<SearchUserLog, Long> {

    Page<SearchUserLog> findByUserId(final Long userId, final Pageable pageable);
}
