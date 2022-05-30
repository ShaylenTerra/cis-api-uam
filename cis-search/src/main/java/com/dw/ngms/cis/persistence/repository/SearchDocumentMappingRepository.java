package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.persistence.domain.SearchDocumentMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface SearchDocumentMappingRepository extends JpaRepository<SearchDocumentMapping, Long> {

	Collection<SearchDocumentMapping> findByDocumentTypeIdAndDocumentSubTypeId(final Long documentTypeId,
																			   final Long documentSubTypeId);
}
