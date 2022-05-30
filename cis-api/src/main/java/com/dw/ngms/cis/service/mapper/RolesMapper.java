package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.Roles;
import com.dw.ngms.cis.service.dto.user.RolesDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author : prateekgoel
 * @since : 26/02/21, Fri
 **/
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RolesMapper {

    Roles rolesDtoToRole(RolesDto rolesDto);

    RolesDto roleToRoleDto(Roles roles);

}
