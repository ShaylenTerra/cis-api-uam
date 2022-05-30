package com.dw.ngms.cis.service.mapper.user;

import com.dw.ngms.cis.enums.UserStatus;
import com.dw.ngms.cis.enums.UserType;
import com.dw.ngms.cis.persistence.domains.Roles;
import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.domains.user.UserMetaData;
import com.dw.ngms.cis.persistence.domains.user.UserRole;
import com.dw.ngms.cis.persistence.repository.ProvinceRepository;
import com.dw.ngms.cis.persistence.repository.RoleRepository;
import com.dw.ngms.cis.persistence.repository.UserRepository;
import com.dw.ngms.cis.persistence.repository.listmanagement.ListItemRepository;
import com.dw.ngms.cis.service.dto.UserDto;
import com.dw.ngms.cis.service.dto.user.UserBasicInfoDto;
import com.dw.ngms.cis.service.dto.user.UserMetaDataDto;
import com.dw.ngms.cis.web.vm.user.UserRegistrationVm;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author : prateekgoel
 * @since : 25/04/21, Sun
 **/
public abstract class UserMapperDecorator implements UserMapper {

    private final int SHORT_ID_LENGTH = 8;

    @Autowired
    @Qualifier("delegate")
    private UserMapper delegate;

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Autowired
    @Qualifier("roleRepository")
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private ListItemRepository listItemRepository;


    @Override
    public User userRegistrationVmToUser(UserRegistrationVm userRegistrationVm) {

        final Long userId = userRepository.getLastUserId().getUserId();

        String userCode = "USR000" + userId;
        User user = new User();
        user.setUserName(userRegistrationVm.getUserName());
        user.setCreatedDate(LocalDateTime.now());
        user.setEmail(userRegistrationVm.getEmail());
        user.setUserCode(userCode);
        user.setFirstName(userRegistrationVm.getFirstName());
        user.setLastUpdatedDate(LocalDateTime.now());
        user.setMobileNo(userRegistrationVm.getMobile());
        final Long roleId = userRegistrationVm.getRoleId();
        if (!userRegistrationVm.getUserTypeItemId().getUserType().equals(UserType.INTERNAL.getUserType())) {
            if (null != roleId) {
                final Roles byRoleId = roleRepository.findByRoleId(roleId);
                if (null != byRoleId && byRoleId.getApprovalRequired() == 0) {
                    final String s = RandomStringUtils.randomAlphanumeric(SHORT_ID_LENGTH);
                    user.setTempPassword(s);
                    user.setPassword(passwordEncoder.encode(s)); //encrypt the password.
                    user.setStatusId(UserStatus.ACTIVE.getUserStatus());
                } else {
                    user.setStatusId(UserStatus.PENDING.getUserStatus());
                }
            }
        }

        user.setProvinceId(userRegistrationVm.getPrimaryProvinceId());
        //If user status based on the user Role type

        user.setSurname(userRegistrationVm.getLastName());
        user.setTelephoneNo(userRegistrationVm.getMobile()); //Set Mobile number
        user.setTitleItemId(userRegistrationVm.getTitleItemId()); //
        user.setUserTypeItemId(userRegistrationVm.getUserTypeItemId());
        user.setCountryCode(userRegistrationVm.getCountryCode());

        //set role
        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setProvinceId(userRegistrationVm.getPrimaryProvinceId());
        userRole.setSectionItemId(userRegistrationVm.getSectionId());
        userRole.setIsPrimary(1L);
        userRole.setAssignedDate(LocalDateTime.now());
        userRole.setRoleId(roleId);
        userRoles.add(userRole);
        user.setUserRoles(userRoles);
        //set meta data
        UserMetaData userMetaData = new UserMetaData();
        userMetaData.setCreatedDate(LocalDateTime.now());
        userMetaData.setUserCode(userCode);
        userMetaData.setAlternativeEmail(userRegistrationVm.getAlternateEmail());
        userMetaData.setCommunicationTypeItemId(userRegistrationVm.getCommunicationTypeId());
        userMetaData.setOrganizationTypeItemId(userRegistrationVm.getOrganizationTypeId());
        userMetaData.setPostalAddressLine1(userRegistrationVm.getAddrLine1());
        userMetaData.setPostalAddressLine2(userRegistrationVm.getAddrLine2());
        userMetaData.setPostalAddressLine3(userRegistrationVm.getAddrLine3());
        userMetaData.setPostalCode(userRegistrationVm.getPostalCode());
        userMetaData.setPpnNo(userRegistrationVm.getPpNo());
        userMetaData.setPractiseName(userRegistrationVm.getPracticeName());
        userMetaData.setSectorItemId(userRegistrationVm.getSectorId());
        userMetaData.setSecurityAnswer1(passwordEncoder.encode(userRegistrationVm.getSecurityA1()));
        userMetaData.setSecurityAnswer2(passwordEncoder.encode(userRegistrationVm.getSecurityA2()));
        userMetaData.setSecurityAnswer3(passwordEncoder.encode(userRegistrationVm.getSecurityA3()));
        userMetaData.setSecurityQuestionItemId1(userRegistrationVm.getSecurityQItemId1());
        userMetaData.setSecurityQuestionItemId2(userRegistrationVm.getSecurityQItemId2());
        userMetaData.setSecurityQuestionItemId3(userRegistrationVm.getSecurityQItemId3());
        userMetaData.setSubscribeEvents(userRegistrationVm.getSubscribeEvents());
        userMetaData.setSubscribeNotifications(userRegistrationVm.getSubscribeNotifications());
        userMetaData.setSubscribeNews(userRegistrationVm.getSubscribeNews());
        userMetaData.setUser(user);
        user.setUserMetaData(userMetaData);
        return user;
    }

    @Override
    public UserDto userToUserDto(User user) {
        final UserDto userDto = delegate.userToUserDto(user);
        final Long statusId = user.getStatusId();
        if (null != statusId) {
            userDto.setStatus(listItemRepository.findByItemId(statusId).getCaption());
        }

        final Long userType = user.getUserTypeItemId().getUserType();
        if (null != userType) {
            userDto.setUserType(listItemRepository.findByItemId(userType).getCaption());
        }

        final Long titleItemId = user.getTitleItemId();
        if (null != titleItemId) {
            userDto.setTitle(listItemRepository.findByItemId(titleItemId).getCaption());
        }
        final Long provinceId = user.getProvinceId();
        if (null != provinceId) {
            userDto.setProvince(provinceRepository.findByProvinceId(provinceId).getProvinceName());
        }

        userDto.getUserRolesDto().forEach(userRoleDto -> {

            roleRepository.findById(userRoleDto.getRoleId())
                    .ifPresent(roles -> userRoleDto.setRoleName(roles.getRoleName()));

            final Long provinceId1 = userRoleDto.getProvinceId();
            if (null != provinceId1) {
                userRoleDto.setProvinceName(provinceRepository.findByProvinceId(provinceId1).getProvinceName());
            }
            if (null != userRoleDto.getSectionItemId()) {
                userRoleDto.setSectionName(listItemRepository.findByItemId(userRoleDto.getSectionItemId()).getCaption());
            }
        });

        UserMetaDataDto userMetaDataDto = userDto.getUserMetaDataDto();
        if (null != userMetaDataDto) {
            final Long organizationTypeItemId = userMetaDataDto.getOrganizationTypeItemId();
            if (null != organizationTypeItemId) {
                userMetaDataDto.setOrganizationType(listItemRepository.findByItemId(organizationTypeItemId).getCaption());
            }
            final Long sectorItemId = userMetaDataDto.getSectorItemId();
            if (null != sectorItemId) {

                userMetaDataDto.setSector(listItemRepository.findByItemId(sectorItemId).getCaption());
            }
        }
        userDto.setUserMetaDataDto(userMetaDataDto);

        return userDto;
    }

    @Override
    public User userBasicInfoDtoToUser(UserBasicInfoDto userBasicInfoDto) {
        User userByUserId = userRepository.findUserByUserId(userBasicInfoDto.getUserId());
        userByUserId.setStatusId(userBasicInfoDto.getStatusId());
        userByUserId.setCountryCode(userBasicInfoDto.getCountryCode());
        userByUserId.setTitleItemId(userBasicInfoDto.getTitleItemId());
        userByUserId.setFirstName(userBasicInfoDto.getFirstName());
        userByUserId.setSurname(userBasicInfoDto.getSurname());
        userByUserId.setTelephoneNo(userBasicInfoDto.getTelephoneNo());
        userByUserId.setMobileNo(userBasicInfoDto.getMobileNo());
        userByUserId.setProvinceId(userBasicInfoDto.getProvinceId());

        return userByUserId;
    }
}
