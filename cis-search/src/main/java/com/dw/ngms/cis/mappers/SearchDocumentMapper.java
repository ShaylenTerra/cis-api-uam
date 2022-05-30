package com.dw.ngms.cis.mappers;

import com.dw.ngms.cis.dto.SearchDocumentDto;
import com.dw.ngms.cis.persistence.domain.SgdataDocuments;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

/**
 * @author : prateekgoel
 * @since : 10/05/21, Mon
 **/
@Mapper(componentModel = "spring")
@DecoratedWith(SearchDocumentMapperDecorator.class)
public interface SearchDocumentMapper {

    SearchDocumentDto sgdataDocumentToSearchDocumentDto(SgdataDocuments sgdataDocuments);

    SgdataDocuments searchDocumentDtoToSgdataDocument(SearchDocumentDto searchDocumentDto);

}
