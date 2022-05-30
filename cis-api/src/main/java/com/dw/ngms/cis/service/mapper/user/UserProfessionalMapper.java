package com.dw.ngms.cis.service.mapper.user;

import com.dw.ngms.cis.persistence.domains.user.UserProfessional;
import com.dw.ngms.cis.service.dto.user.UserProfessionalDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author : pragayanshu
 * @since : 2021/04/24, Sat
 **/
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
@DecoratedWith(UserProfessionalDecorator.class)
public interface UserProfessionalMapper {

   UserProfessionalDto userProfessionalToUserProfessionalDTO(UserProfessional userProfessional);

   UserProfessional userProfessionalDTOToUserProfessional(UserProfessionalDto userProfessionalDTO);


}
