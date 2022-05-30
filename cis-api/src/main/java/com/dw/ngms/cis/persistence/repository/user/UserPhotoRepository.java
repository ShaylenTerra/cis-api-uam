package com.dw.ngms.cis.persistence.repository.user;

import com.dw.ngms.cis.persistence.domains.user.UserPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : prateekgoel
 * @since : 01/05/21, Sat
 **/
@Repository
public interface UserPhotoRepository extends JpaRepository<UserPhoto, Long> {

    UserPhoto findByUserid(final Long userId);
}
