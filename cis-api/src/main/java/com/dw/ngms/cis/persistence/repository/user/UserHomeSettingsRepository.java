package com.dw.ngms.cis.persistence.repository.user;

import com.dw.ngms.cis.persistence.domains.user.UserHomeSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : prateekgoel
 * @since : 28/11/20, Sat
 **/
@Repository
public interface UserHomeSettingsRepository extends JpaRepository<UserHomeSettings,Long> {

    Optional<UserHomeSettings> findByUserId(final Long userId);
}
