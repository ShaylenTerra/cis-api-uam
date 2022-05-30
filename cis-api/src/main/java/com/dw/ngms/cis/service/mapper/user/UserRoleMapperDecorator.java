package com.dw.ngms.cis.service.mapper.user;

import com.dw.ngms.cis.persistence.domains.user.UserRole;
import com.dw.ngms.cis.persistence.repository.ProvinceRepository;
import com.dw.ngms.cis.persistence.repository.RoleRepository;
import com.dw.ngms.cis.persistence.repository.listmanagement.ListItemRepository;
import com.dw.ngms.cis.service.dto.user.UserRoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author : prateekgoel
 * @since : 08/05/21, Sat
 **/
public abstract class UserRoleMapperDecorator implements UserRoleMapper {

    @Autowired
    @Qualifier("delegate")
    private UserRoleMapper delegate;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ListItemRepository listItemRepository;

    @Override
    public UserRoleDto userRoleToUserRoleDto(UserRole userRole) {
        final UserRoleDto userRoleDto = delegate.userRoleToUserRoleDto(userRole);

        userRoleDto.setProvinceName(provinceRepository.findByProvinceId(userRoleDto.getProvinceId()).getProvinceName());
        userRoleDto.setRoleName(roleRepository.findByRoleId(userRoleDto.getRoleId()).getRoleName());
        if (null != userRoleDto.getSectionItemId())
            userRoleDto.setSectionName(listItemRepository.findByItemId(userRoleDto.getSectionItemId()).getCaption());
        return userRoleDto;

    }
}
