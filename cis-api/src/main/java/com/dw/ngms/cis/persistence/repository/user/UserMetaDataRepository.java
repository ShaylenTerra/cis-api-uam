package com.dw.ngms.cis.persistence.repository.user;

import com.dw.ngms.cis.persistence.domains.user.UserMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMetaDataRepository extends JpaRepository<UserMetaData,Long> {

    @Query(value = "SELECT UMD.* FROM USER_META_DATA UMD WHERE UMD.USERID = :userId ",
            nativeQuery = true)
    UserMetaData findUserMetaDataByUserId(final Long userId);
}
