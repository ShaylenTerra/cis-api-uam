package com.dw.ngms.cis.persistence.repository.user;

import com.dw.ngms.cis.persistence.domains.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query(value = "SELECT UAR.* FROM USER_ADDITIONAL_ROLE UAR WHERE UAR.USERID = :userId ",
            nativeQuery = true)
    Collection<UserRole> findUserRoleByUserId(final Long userId);

    @Modifying
    @Query(" delete from UserRole  where userRoleId = :userRoleId")
    void deleteByUserRoleId(final Long userRoleId);
}