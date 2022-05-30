package com.dw.ngms.cis.persistence.repository.user;

import com.dw.ngms.cis.persistence.domains.user.UserLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : pragayanshu
 * @since : 2021/04/25, Sun
 **/
@Repository
public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory,Long> {
}
