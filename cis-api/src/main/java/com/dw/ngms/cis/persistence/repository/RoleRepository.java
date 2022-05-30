package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.enums.UserType;
import com.dw.ngms.cis.persistence.domains.Roles;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 24/11/20, Tue
 **/
@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {

    Roles findByRoleName(String roleName);

    Roles findByRoleId(Long roleId);

    Collection<Roles> findByUserTypeItemIdOrderByRoleName(final UserType userType);

    Collection<Roles> findBySectionItemIdAndIsActiveAndUserTypeItemId(final Long sectionItemId,
                                                                      final Long isActive,
                                                                      final UserType userTypeItemId);

    Collection<Roles> findBySectionItemId(final Long sectionItemId);

}
