package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.enums.UserType;
import com.dw.ngms.cis.persistence.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by nirmal on 2020/11/11.
 */
@Repository
public interface LoginRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.userName = :username")
	User findByLoginName(@Param("username") String username);

	@Query("SELECT u FROM User u WHERE u.userName = :username AND u.userTypeItemId = com.dw.ngms.cis.enums.UserType.EXTERNAL")
	User findUserByLoginNameAndUserTypeItemId(@Param("username") String username);

	User findByUserNameAndUserTypeItemId(String userName, UserType userTypeItemId);
}
