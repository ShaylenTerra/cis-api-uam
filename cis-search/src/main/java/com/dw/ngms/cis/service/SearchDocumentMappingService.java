package com.dw.ngms.cis.service;

import com.dw.ngms.cis.dto.SearchDocumentMappingDto;
import com.dw.ngms.cis.mappers.SearchDocumentMappingMapper;
import com.dw.ngms.cis.persistence.repository.SearchDocumentMappingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SearchDocumentMappingService {

	private final SearchDocumentMappingRepository searchDocumentMappingRepository;

	private final SearchDocumentMappingMapper searchDocumentMappingMapper;

	public Collection<SearchDocumentMappingDto> getByDocumentTypeAndDocumentSubtype(final Long documentTypeId,
																					final Long documentSubTypeId) {
		return searchDocumentMappingRepository.findByDocumentTypeIdAndDocumentSubTypeId(documentTypeId, documentSubTypeId)
				.stream()
				.map(searchDocumentMappingMapper::searchDocumentMappingToSearchDocumentMappingDto)
				.collect(Collectors.toList());
	}
}
