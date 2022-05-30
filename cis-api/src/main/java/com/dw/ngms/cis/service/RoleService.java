package com.dw.ngms.cis.service;

import com.dw.ngms.cis.enums.UserType;
import com.dw.ngms.cis.persistence.domains.Roles;
import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.domains.user.UserRole;
import com.dw.ngms.cis.persistence.repository.ProvinceRepository;
import com.dw.ngms.cis.persistence.repository.RoleRepository;
import com.dw.ngms.cis.persistence.repository.UserRepository;
import com.dw.ngms.cis.persistence.repository.listmanagement.ListItemRepository;
import com.dw.ngms.cis.persistence.repository.user.UserRoleRepository;
import com.dw.ngms.cis.service.dto.user.RolesDto;
import com.dw.ngms.cis.service.dto.user.UserRoleDto;
import com.dw.ngms.cis.service.mapper.RolesMapper;
import com.dw.ngms.cis.service.mapper.user.UserRoleMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author : prateekgoel
 * @since : 24/11/20, Tue
 **/
@Service
@Slf4j
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final RolesMapper rolesMapper;

    private final UserRoleMapper userRoleMapper;

    private final UserRoleRepository userRoleRepository;

    private final UserRepository userRepository;

    private final ProvinceRepository provinceRepository;

    private final ListItemRepository listItemRepository;

    /**
     * @param userType {@link UserType}
     * @return Collection<RolesDto>
     */
    public Collection<RolesDto> listRoles(UserType userType) {
        return roleRepository.findByUserTypeItemIdOrderByRoleName(userType)
                .stream()
                .map(rolesMapper::roleToRoleDto)
                .collect(Collectors.toList());
    }

    /**
     * @param sectionId sectionId
     * @return Collection<RolesDto>
     */
    public Collection<RolesDto> listActiveRolesBySectionId(final Long sectionId) {
        return roleRepository.findBySectionItemIdAndIsActiveAndUserTypeItemId(sectionId, 1L,
                        UserType.INTERNAL)
                .stream()
                .map(rolesMapper::roleToRoleDto)
                .collect(Collectors.toList());
    }

    /**
     * @param sectionId sectionId
     * @return Collection<RolesDto>
     */
    public Collection<RolesDto> listAllRolesBySectionId(final Long sectionId) {
        return roleRepository.findBySectionItemId(sectionId)
                .stream()
                .map(rolesMapper::roleToRoleDto)
                .collect(Collectors.toList());
    }

    /**
     * @param rolesDto {@link RolesDto}
     * @return RolesDto {@link RolesDto}
     */
    public RolesDto addRoles(final RolesDto rolesDto) {

        Roles byRoleId = roleRepository.findByRoleId(rolesDto.getRoleId());
        if (null != byRoleId) {
            byRoleId.setRoleCode(rolesDto.getRoleCode());
            byRoleId.setRoleName(rolesDto.getRoleName());
            byRoleId.setDescription(rolesDto.getDescription());
            byRoleId.setSectionItemId(rolesDto.getSectionItemId());
            byRoleId.setIsActive(rolesDto.getIsActive());
            byRoleId.setApprovalRequired(rolesDto.getApprovalRequired());
        } else {
            byRoleId = rolesMapper.rolesDtoToRole(rolesDto);
        }

        Roles savedRole = roleRepository.save(byRoleId);

        return rolesMapper.roleToRoleDto(savedRole);
    }

    /**
     * @param userId userId
     * @return Collection<UserRoleDto>
     */
    public Collection<UserRoleDto> getRoles(final Long userId) {
        return userRoleRepository.findUserRoleByUserId(userId)
                .stream()
                .map(userRole -> {
                    UserRoleDto userRoleDto = userRoleMapper.userRoleToUserRoleDto(userRole);
                    userRoleDto.setUserId(userId);
                    return userRoleDto;
                }).collect(Collectors.toSet());
    }

    /**
     * @param userRoleDto {@link UserRoleDto}
     * @return UserRoleDto {@link UserRoleDto}
     */
    public UserRoleDto saveUserRole(final UserRoleDto userRoleDto) {
        User user = userRepository.findUserByUserId(userRoleDto.getUserId());
        UserRole userRole = userRoleMapper.userRoleDtoToUserRole(userRoleDto);
        userRole.setUser(user);
        userRole.setAssignedDate(LocalDateTime.now());
        UserRoleDto userRoleDto1 = userRoleMapper.userRoleToUserRoleDto(userRoleRepository.save(userRole));
        userRoleDto1.setUserId(user.getUserId());
        return userRoleDto1;
    }

    /**
     * @param userRoleId userRoleId
     * @return deleted or not
     */
    @Transactional
    public Boolean deleteRoleByUserRoleId(Long userRoleId) {
        userRoleRepository.deleteByUserRoleId(userRoleId);
        return true;
    }
}
