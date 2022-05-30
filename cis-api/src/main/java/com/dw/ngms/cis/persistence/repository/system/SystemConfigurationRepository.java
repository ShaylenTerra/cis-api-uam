package com.dw.ngms.cis.persistence.repository.system;

import com.dw.ngms.cis.persistence.domains.system.SystemConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : prateekgoel
 * @since : 18/05/21, Tue
 **/
@Repository
public interface SystemConfigurationRepository extends JpaRepository<SystemConfiguration, Long> {

    SystemConfiguration findByTag(final String tag);

}
