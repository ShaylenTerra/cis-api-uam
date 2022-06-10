package com.dw.ngms.cis.service.mapper.examination;

import com.dw.ngms.cis.persistence.domains.examination.Examination;
import com.dw.ngms.cis.service.dto.examination.ExaminationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDateTime;

/**
 * @author : Nontokozo
 * @since : 30/05/22, Mon
 **/
@Mapper(componentModel = "spring")
public interface ExaminationMapper {

    @Mappings({

            @Mapping(target = "name", source = "name"),
            @Mapping(target = "createdDate", source = "createdDate"),
            @Mapping(target = "lodgementId", source = "lodgementId"),
            @Mapping(target = "allocatedUserId", source = "allocatedUserId"),
            @Mapping(target = "workflowId", source = "workflowId"),
            @Mapping(target = "statusId", source = "statusId"),
    })

    Examination examinationDtoToExamination (ExaminationDto examinationDto);

    ExaminationDto examinaionToExaminationDto (Examination examination);
}
