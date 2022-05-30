package com.dw.ngms.cis.service;

import com.dw.ngms.cis.enums.UserType;
import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by swaroop on 2019/03/24.
 */
@Service
public class LoginService {

	@Autowired
	LoginRepository loginRepository;

	public User findByLoginName(String userName) {
		return this.loginRepository.findByLoginName(userName);
	}

	public User findExternalUserByLoginName(String userName) {
//        return this.loginRepository.findUserByLoginNameAndUserTypeItemId(userName);
		return this.loginRepository.findByUserNameAndUserTypeItemId(userName, UserType.EXTERNAL);
	}
}
