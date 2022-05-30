package com.dw.ngms.cis.service.mapper.user;

import com.dw.ngms.cis.persistence.domains.user.UserRole;
import com.dw.ngms.cis.service.dto.user.UserRoleDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
@DecoratedWith(UserRoleMapperDecorator.class)
public interface UserRoleMapper {

    UserRole userRoleDtoToUserRole(UserRoleDto userRoleDto);

    UserRoleDto userRoleToUserRoleDto(UserRole userRole);

}
