package com.dw.ngms.cis.service.dispatch;

import org.springframework.stereotype.Service;
import com.dw.ngms.cis.persistence.domains.dispatch.Dispatch;
import com.dw.ngms.cis.persistence.repository.dispatch.DispatchRepository;
import com.dw.ngms.cis.service.dto.dispatch.DispatchDto;
import com.dw.ngms.cis.service.mapper.DispatchMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class GlobalDispatchService {

	private DispatchRepository dispatchRepository;

	private DispatchMapper dispatchMapper;

	/**
	 * @param dispatchId dispatchId
	 * @return {@link DispatchDto}
	 */
	public DispatchDto getDispatch(final Long dispatchId) {
		return dispatchRepository.findById(dispatchId)
				.map(dispatchMapper::dispatchToDispatchDto)
				.orElseGet(() -> null);

	}

	/**
	 *
	 * @param workflowId workflowId
	 * @return {@link DispatchDto}
	 */
	public DispatchDto getDispatchByWorkflowId(final Long workflowId) {
		Dispatch byWorkflowId = dispatchRepository.findByWorkflowId(workflowId);
		return dispatchMapper.dispatchToDispatchDto(byWorkflowId);
	}

	/**
	 *
	 * @param dispatchId dispatchId
	 */
	public void deleteDispatch(final Long dispatchId) {
		dispatchRepository.deleteDispatchByDispatchId(dispatchId);
	}

	/**
	 *
	 * @param dispatchDto {@link DispatchDto}
	 * @return {@link DispatchDto}
	 */
	public DispatchDto saveDispatch(final DispatchDto dispatchDto) {
		Dispatch dispatch = dispatchMapper.dispatchDtoToDispatch(dispatchDto);
		Dispatch responseDispatch = dispatchRepository.save(dispatch);
		return dispatchMapper.dispatchToDispatchDto(responseDispatch);
	}

	/**
	 *
	 * @param dispatchDto {@link DispatchDto}
	 * @return {@link DispatchDto}
	 */
	public DispatchDto updateDispatch(final DispatchDto dispatchDto) {
		Dispatch dispatch = dispatchMapper.dispatchDtoToDispatch(dispatchDto);
		Dispatch save = dispatchRepository.save(dispatch);
		return dispatchMapper.dispatchToDispatchDto(save);
	}

}
