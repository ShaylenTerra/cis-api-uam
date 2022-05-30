package com.dw.ngms.cis.persistence.repository.user;

import com.dw.ngms.cis.persistence.domains.user.internal.UserDelegations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author : prateekgoel
 * @since : 19/12/20, Sat
 **/
@Repository
public interface UserDelegationsRepository extends JpaRepository<UserDelegations, Long> {

    Page<UserDelegations> findAllByUserId(final Long userId, Pageable pageable);

    UserDelegations findByDelegationId(final Long delegationId);

}
