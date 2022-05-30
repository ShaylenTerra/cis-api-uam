package com.dw.ngms.cis.service.mapper.user;

import com.dw.ngms.cis.persistence.domains.user.internal.UserDelegations;
import com.dw.ngms.cis.service.dto.user.UserDelegationsDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author : prateekgoel
 * @since : 19/12/20, Sat
 **/
@Mapper(componentModel = "spring")
public interface UserDelegationMapper {

    @Mappings({
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "toDate", source = "toDate"),
            @Mapping(target = "fromDate", source = "fromDate"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "delegateUserId", source = "delegateUserId"),
            @Mapping(target = "id", source = "delegationId"),
            @Mapping(target = "statusItemId", source = "statusItemId"),
    })
    UserDelegationsDto userDelegationsToUserDelegationDto(UserDelegations userDelegations);

    @InheritInverseConfiguration
    UserDelegations userDelegationsDtoToUserDelegations(UserDelegationsDto userDelegationsDto);

}
