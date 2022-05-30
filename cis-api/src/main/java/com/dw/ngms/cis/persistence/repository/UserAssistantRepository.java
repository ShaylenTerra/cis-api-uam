package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.persistence.domains.UserAssistant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by nirmal on 2020/11/09.
 */
@Repository
public interface UserAssistantRepository extends JpaRepository<UserAssistant, Long> {



	UserAssistant findByUserIdAndAssistantId(Long userId, Long assistantId);

	void deleteByUserIdAndAssistantId(Long userId, Long assistantId);


}
