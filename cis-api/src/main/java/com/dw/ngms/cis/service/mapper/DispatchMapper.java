package com.dw.ngms.cis.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import com.dw.ngms.cis.persistence.domains.dispatch.Dispatch;
import com.dw.ngms.cis.service.dto.dispatch.DispatchDto;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DispatchMapper {

    @Mappings({
            @Mapping(target = "dispatchId", source = "dispatchId"),
            @Mapping(target = "workflowId", source = "workflowId"),
            @Mapping(target = "dispatchDetails", source = "dispatchDetails"),
    })
    Dispatch dispatchDtoToDispatch(DispatchDto dispatchDto);

    @InheritInverseConfiguration
    DispatchDto dispatchToDispatchDto(Dispatch dispatch);

}
