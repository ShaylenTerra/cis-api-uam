package com.dw.ngms.cis.service.mapper.examination;

import com.dw.ngms.cis.persistence.domains.examination.ExaminationAllocatedUsers;
import com.dw.ngms.cis.service.dto.examination.ExaminationAllocatedUsersDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ExaminationAllocatedUsersMapper {

    ExaminationAllocatedUsers ExaminationAllocatedUsersDtoToExaminationAllocatedUsers(ExaminationAllocatedUsersDto examinationAllocatedUsersDto);

    @InheritInverseConfiguration
    ExaminationAllocatedUsersDto ExaminationAllocatedUsersToExaminationAllocatedUsersDto(ExaminationAllocatedUsers examinationAllocatedUsers);

}
