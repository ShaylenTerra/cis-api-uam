package com.dw.ngms.cis.persistence.repository.dispatch;

import com.dw.ngms.cis.service.dto.dispatch.DispatchDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dw.ngms.cis.persistence.domains.dispatch.Dispatch;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Hrusikesh
 *
 */
@Repository
public interface DispatchRepository extends JpaRepository<Dispatch, Long> {

	Dispatch findByDispatchId(final Long dispatchId);

	Dispatch findByWorkflowId(final Long workflowId);
	
	@Transactional
	void deleteDispatchByDispatchId(final Long dispatchId);

}
