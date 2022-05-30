package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.prepackage.PrePackageExecution;
import com.dw.ngms.cis.service.dto.prepackage.PrePackageExecutionDto;
import org.mapstruct.Mapper;

/**
 * @author : prateekgoel
 * @since : 23/03/21, Tue
 **/
@Mapper(componentModel = "spring")
public interface PrePackageExecutionMapper {

    PrePackageExecution prepackageExecutionDtoToPrePackageExecution(PrePackageExecutionDto prePackageExecutionDto);

    PrePackageExecutionDto prepackageExecutionToPrePackageExecutionDto(PrePackageExecution prePackageExecution);

}
