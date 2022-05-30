package com.dw.ngms.cis.cisworkflow.persistence.repository;

import com.dw.ngms.cis.cisworkflow.persistence.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : prateekgoel
 * @since : 07/06/21, Mon
 **/
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(final Long userId);

}
