package com.dw.ngms.cis.service;

import com.dw.ngms.cis.persistence.domains.UserAssistant;
import com.dw.ngms.cis.persistence.repository.UserAssistantRepository;
import com.dw.ngms.cis.web.response.CreateResponse;
import com.dw.ngms.cis.web.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by nirmal on 2020/11/09.
 */
@Service
public class UserAssistantService {

	@Autowired
	private UserAssistantRepository userAssistantRepository;

	public CreateResponse add(Long userId, Long assistantId) {
		UserAssistant userAssistant = getAssistantByUserIdAndAssistantId(userId, assistantId);
		if (userAssistant != null) {
			return CreateResponse.builder().created(false).build();
		}
		UserAssistant userAssistantResponse = UserAssistant.builder().assistantId(assistantId).userId(userId).build();
		userAssistantRepository.save(userAssistantResponse);
		return CreateResponse.builder().created(true).build();
	}

	public UserAssistant getAssistantByUserIdAndAssistantId(Long userId, Long assistantId) {
		return userAssistantRepository.findByUserIdAndAssistantId(userId, assistantId);
	}

//	public Collection<Map<String, Object>> getAllAssistantsByUserId(Long userId) {
//		return userAssistantRepository.findAssistantsByUserId(userId);
//	}

	@Transactional
	public UpdateResponse removeAssistantByAssistantId(Long userId, Long assistantId) {
		userAssistantRepository.deleteByUserIdAndAssistantId(userId, assistantId);
		return UpdateResponse.builder().update(true).build();
	}

}
