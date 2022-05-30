package com.dw.ngms.cis.mappers;

import com.dw.ngms.cis.dto.SearchDocumentMappingDto;
import com.dw.ngms.cis.persistence.domain.SearchDocumentMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author : prateekgoel
 * @since : 13/05/21, Thu
 **/
@Mapper(componentModel = "spring")
public interface SearchDocumentMappingMapper {

    @Mappings({
            @Mapping(target = "dataType", source = "dataType"),
            @Mapping(target = "dataTypeId", source = "dataTypeId")
    })
    SearchDocumentMappingDto searchDocumentMappingToSearchDocumentMappingDto(SearchDocumentMapping searchDocumentMapping);

    SearchDocumentMapping searchDocumentMappingDtoToSearchDocumentMapping(SearchDocumentMappingDto searchDocumentMappingDto);

}
