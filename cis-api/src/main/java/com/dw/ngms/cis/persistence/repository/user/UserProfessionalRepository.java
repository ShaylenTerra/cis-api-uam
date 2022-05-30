package com.dw.ngms.cis.persistence.repository.user;

import com.dw.ngms.cis.persistence.domains.user.UserProfessional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author : pragayanshu
 * @since : 2021/04/24, Sat
 **/
@Repository
public interface UserProfessionalRepository extends JpaRepository<UserProfessional,Long> {


    @Modifying
    @Query(value = "UPDATE USER_PROFESSIONAL UP SET UP.USERID = :userId " +
                   "WHERE UP.PPNNO = :ppnNo",
            nativeQuery = true)
    int updateUserProfessionalUserId(final String ppnNo,final Long userId);

    UserProfessional findByPpnNo(final String ppnNo);

    Page<UserProfessional> findAllByProfessionTypeItemId(final Long professionTypeId, final Pageable pageable);

}
