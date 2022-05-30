package com.dw.ngms.cis.service.mapper.user;

import com.dw.ngms.cis.persistence.domains.UserAssistant;
import com.dw.ngms.cis.service.dto.user.UserAssistantDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author : pragayanshu
 * @since : 2021/04/27, Tue
 **/

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserAssistantMapper {

    UserAssistantDto userAssistantToUserAssistantDto(UserAssistant userAssistant);
    UserAssistant userAssistantDtoToUserAssistant(UserAssistantDto userAssistantDto);

}
