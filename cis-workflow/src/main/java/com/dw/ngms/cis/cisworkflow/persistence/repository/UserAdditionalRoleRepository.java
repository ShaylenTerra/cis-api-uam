package com.dw.ngms.cis.cisworkflow.persistence.repository;

import com.dw.ngms.cis.cisworkflow.persistence.domain.UserAdditionalRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 26/05/21, Wed
 **/
@Repository
public interface UserAdditionalRoleRepository extends JpaRepository<UserAdditionalRole,Long> {

    @Query(value = "SELECT U.USERID FROM USER_ADDITIONAL_ROLE UAR\n" +
            "INNER JOIN USERS U on UAR.USERID = U.USERID\n" +
            "WHERE UAR.PROVINCEID = :provinceId AND\n" +
            "      U.USERTYPEITEMID = 3 AND\n" +
            "      U.STATUSITEMID = 108 AND\n" +
            "      UAR.ROLEID = :roleId", nativeQuery = true)
    Collection<Long> findByProvinceIdAndRoleId(final Long provinceId, final Long roleId);

}
