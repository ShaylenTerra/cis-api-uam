package com.dw.ngms.cis.service.mapper.user;

import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.service.dto.UserDto;
import com.dw.ngms.cis.service.dto.user.UserBasicInfoDto;
import com.dw.ngms.cis.web.vm.user.UserRegistrationVm;
import org.mapstruct.*;

/**
 * @author : prateekgoel
 * @since : 22/11/20, Sun
 **/
@Mapper(componentModel = "spring",
        imports = {
                UserRoleMapper.class,
                UserMetaDataMapper.class
        },
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

    @Mappings({
            @Mapping(target = "mobileNo", source = "mobileNo"),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "countryCode", source = "countryCode"),
            @Mapping(target = "userMetaDataDto", source = "userMetaData"),
            @Mapping(target = "userRolesDto", source = "userRoles"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "firstName", source = "firstName"),
            @Mapping(target = "provinceId", source = "provinceId"),
            @Mapping(target = "titleItemId", source = "titleItemId"),
            @Mapping(target = "statusId", source = "statusId"),
            @Mapping(target = "userName", source = "userName"),
            @Mapping(target = "surname", source = "surname"),
            @Mapping(target = "resetPassword", source = "resetPassword")
    })
    UserDto userToUserDto(User user);

    @InheritInverseConfiguration
    User userDtoToUser(UserDto userDto);

    User userRegistrationVmToUser(UserRegistrationVm userRegistrationVm);

    User userBasicInfoDtoToUser(UserBasicInfoDto userBasicInfoDto);

    UserBasicInfoDto userToUserBasicInfoDto(User user);

}
