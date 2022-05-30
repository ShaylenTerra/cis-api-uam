package com.dw.ngms.cis.persistence.repository.user;

import com.dw.ngms.cis.persistence.domains.user.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : pragayanshu
 * @since : 2021/04/24, Sat
 **/
@Repository
public interface UserTransactionRepository extends JpaRepository<UserTransaction,Long> {
}
