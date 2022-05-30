package com.dw.ngms.cis.service;

import com.dw.ngms.cis.enums.StorageContext;
import com.dw.ngms.cis.enums.UserDocumentType;
import com.dw.ngms.cis.enums.UserStatus;
import com.dw.ngms.cis.enums.UserType;
import com.dw.ngms.cis.exception.PasswordMismatchException;
import com.dw.ngms.cis.exception.ResourceNotFoundException;
import com.dw.ngms.cis.exception.UserException;
import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.domains.UserAssistant;
import com.dw.ngms.cis.persistence.domains.document.UserDocument;
import com.dw.ngms.cis.persistence.domains.user.*;
import com.dw.ngms.cis.persistence.repository.RoleRepository;
import com.dw.ngms.cis.persistence.repository.UserAssistantRepository;
import com.dw.ngms.cis.persistence.repository.UserRepository;
import com.dw.ngms.cis.persistence.repository.document.UserDocumentRepository;
import com.dw.ngms.cis.persistence.repository.user.UserMetaDataRepository;
import com.dw.ngms.cis.persistence.repository.user.UserPhotoRepository;
import com.dw.ngms.cis.persistence.repository.user.UserProfessionalRepository;
import com.dw.ngms.cis.persistence.repository.user.UserRoleRepository;
import com.dw.ngms.cis.pubsub.AppEventPublisher;
import com.dw.ngms.cis.security.CurrentLoggedInUser;
import com.dw.ngms.cis.service.dto.ManagerUserDto;
import com.dw.ngms.cis.service.dto.UserDto;
import com.dw.ngms.cis.service.dto.user.*;
import com.dw.ngms.cis.service.event.EventType;
import com.dw.ngms.cis.service.mapper.user.*;
import com.dw.ngms.cis.service.user.UserHomeSettingsService;
import com.dw.ngms.cis.utilities.FileUtils;
import com.dw.ngms.cis.utilities.Messages;
import com.dw.ngms.cis.web.response.UpdateResponse;
import com.dw.ngms.cis.web.vm.ChangeEmailVm;
import com.dw.ngms.cis.web.vm.LoginVm;
import com.dw.ngms.cis.web.vm.PasswordChangeVm;
import com.dw.ngms.cis.web.vm.SecurityInfoVm;
import com.dw.ngms.cis.web.vm.user.UserRegistrationVm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by nirmal on 2020/11/09.
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final AppEventPublisher publisher;

    private final UserMapper userMapper;

    private final UserRoleRepository userRoleRepository;

    private final RoleRepository roleRepository;

    private final UserProfessionalRepository userProfessionalRepository;

    private final FileStorageService fileStorageService;

    private final UserDocumentRepository userDocumentRepository;

    private final UserAssistantRepository userAssistantRepository;

    private final UserAssistantMapper userAssistantMapper;

    private final UserRoleMapper userRoleMapper;

    private final UserMetaDataMapper userMetaDataMapper;

    private final CurrentLoggedInUser currentLoggedInUser;

    private final PasswordEncoder passwordEncoder;

    private final UserPhotoRepository userPhotoRepository;

    private final UserProfessionalMapper userProfessionalMapper;

    private final UserMetaDataRepository userMetaDataRepository;

    private final UserDocumentMapper userDocumentMapper;

    private final Messages messages;

    private final UserHomeSettingsService userHomeSettingsService;

    private final int SHORT_ID_LENGTH = 8;


    /**
     * @param userType {@link UserType}
     * @param pageable {@link Pageable}
     * @return Page<UserDto>
     */
    public Page<UserDto> getAllUserByType(final UserType userType, final Pageable pageable) {
        return userRepository.findAllByUserTypeItemId(userType, pageable)
                .map(userMapper::userToUserDto);
    }


    /**
     * @param userId userId of user
     * @return User
     */
    public User getUserById(final Long userId) {
        return userRepository.getUserById(userId);
    }

    /**
     * @param userId userId of user
     * @return User
     */
    public Collection<UserDto> getAssistantToProfessional(final Long userId) {

        return userRepository.findAssistantsByUserId(userId)
                .stream()
                .map(user -> {
                    UserDto userDto = userMapper.userToUserDto(user);
                    UserAssistant userAssistant = userAssistantRepository.findByUserIdAndAssistantId(userId, userDto.getUserId());
                    userDto.setAssistantStatusId(userAssistant.getStatusId());
                    userDto.setAssistantId(userAssistant.getId());
                    return userDto;
                })
                .collect(Collectors.toList());

    }

    public Boolean verifyIfUserNameExists(final String userName) {
        return userRepository.findUserByUserName(userName) != null;
    }


    public UserDto getUsersByEmail(final String email) {
        return userMapper
                .userToUserDto(userRepository.findByEmailIgnoreCase(email));
    }


    public void changeProfessionalAssistantStatus(final UserAssistantDto userAssistantDto) {
        userAssistantRepository.findById(userAssistantDto.getId())
                .ifPresent(userAssistant -> {
                    userAssistant.setStatusId(userAssistantDto.getStatusId());
                    userAssistantRepository
                            .save(userAssistant);
                    if (userAssistantDto.getStatusId().equals(UserStatus.APPROVED.getUserStatus())) {
                        publisher.publishEvent(userAssistantDto, EventType.ASSISTANT_APPROVED_PLS);
                    }

                });

    }


    /**
     * @param userRegistrationVm {@link UserRegistrationVm}
     * @return {@link UserDto}
     */
    @Transactional
    public String registerUser(final UserRegistrationVm userRegistrationVm) {


        User user = userRepository.findByEmailIgnoreCase(userRegistrationVm.getEmail());

        if (user != null)
            throw new ResourceNotFoundException("User already registered !!");

        User convertedUser = userMapper.userRegistrationVmToUser(userRegistrationVm);

        convertedUser.setStatusId(UserStatus.ACTIVE.getUserStatus());
        convertedUser.setResetPassword(1L);
        Optional<UserRole> first = convertedUser.getUserRoles()
                .stream()
                .filter(userRole -> userRole.getIsPrimary() == 1)
                .findFirst();
        String message = messages.getMessage("UserService.success.registration");

        if (first.isPresent()) {
            Long approvalRequired = roleRepository.findByRoleId(first.get().getRoleId()).getApprovalRequired();

            if (null != approvalRequired && approvalRequired == 1) {
                convertedUser.setStatusId(UserStatus.PENDING.getUserStatus());
                message = messages.getMessage("UserService.pending.registration");
            }
        }


        final User savedUser = userRepository.save(convertedUser);

        if (savedUser.getUserTypeItemId().equals(UserType.INTERNAL)) {
            message = messages.getMessage("UserService.internal.registration");
        }

        first.ifPresent(userRole -> {
            if (Arrays.asList(16L, 21L).contains(userRole.getRoleId())) {

                MultipartFile uploadDocument = userRegistrationVm.getUploadDocument();
                final String storageContext = StorageContext.USER.getStorageContext();
                final Long userId = savedUser.getUserId();
                final String targetLocation = storageContext + FileUtils.PATH_SEPARATOR + userId;
                final String fileName = fileStorageService.storeFile(uploadDocument, targetLocation);

                UserDocument userDocument = new UserDocument();
                userDocument.setUserId(savedUser.getUserId());
                userDocument.setFileName(fileName);
                userDocument.setContentType(FileUtils.getFileExtension(uploadDocument.getOriginalFilename()));
                userDocument.setContext("USER_REGISTRATION_DOCS");
                userDocument.setCreatedOn(LocalDateTime.now());
                userDocument.setDocumentTypeId(UserDocumentType.USER_REGISTRATION_DOCUMENT);
                userDocument.setIsActive(1L);
                // save user document in db
                userDocumentRepository.save(userDocument);

                //update userId in USER_PROFESSIONAL against ppnno.
                userProfessionalRepository
                        .updateUserProfessionalUserId(userRegistrationVm.
                                getPpNo(), savedUser.getUserId());


            } else if (userRole.getRoleId() == 15) {

                UserProfessional parentProfessionalUser = userProfessionalRepository
                        .findByPpnNo(userRegistrationVm.getPpNo());
                UserAssistant userAssistant = new UserAssistant(null, parentProfessionalUser.getUserId(),
                        savedUser.getUserId(), UserStatus.PENDING.getUserStatus());
                userAssistantRepository.save(userAssistant);

            }
        });

        //send this email when user is approved/Active.

        if (savedUser.getStatusId().equals(UserStatus.ACTIVE.getUserStatus())) {
            publisher.publishEvent(savedUser, EventType.USER_REGISTRATION);
        }
        //Send the Admin notification and applicant that your request is pending with admin
        //add one entry in user notification
        //add one entry in user transaction.
        if (savedUser.getStatusId().equals(UserStatus.PENDING.getUserStatus())) {
            publisher.publishEvent(savedUser, EventType.USER_REGISTRATION_PENDING);
        }

        // by default save user settings
        UserHomeSettingsDto userHomeSettingsDto = new UserHomeSettingsDto();
        userHomeSettingsDto.setUserId(savedUser.getUserId());
        userHomeSettingsDto.setHomepage("home");
        userHomeSettingsDto.setSections("{\"ActiveTask\":true,\"Notifications\":true,\"MyRequests\":true,\"RequestStatus\":true}");
        userHomeSettingsService.saveUserHomeSetting(userHomeSettingsDto);
        return message;

    }

    public Boolean saveProfilePicture(final MultipartFile multipartFile) {
        final SecurityUser user = currentLoggedInUser.getUser();
        UserPhoto byUserid = userPhotoRepository.findByUserid(user.getUserId());
        if (null != byUserid) {
            try {
                byUserid.setPhoto(multipartFile.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            userPhotoRepository.save(byUserid);
        } else {
            byUserid = new UserPhoto();
            byUserid.setUserid(user.getUserId());
            try {
                byUserid.setPhoto(multipartFile.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        userPhotoRepository.save(byUserid);
        return true;
    }

    public boolean verifyByPpnNo(final String ppnNo) {
        return userProfessionalRepository.findByPpnNo(ppnNo) != null;
    }

    /**
     * @param ppnNo practitioner no
     * @return 1/0
     */
    public Boolean verifyByPpnNoAndUserStatus(final String ppnNo) {
        return userRepository
                .getUserByPpnNoAndUserStatus(ppnNo, UserStatus.ACTIVE.getUserStatus()) != null;
    }

    /**
     * Add assistant for the professional
     *
     * @param userAssistantDto {@link UserAssistantDto}
     * @return {@link UserAssistantDto}
     */
    public UserAssistantDto addAssistant(final UserAssistantDto userAssistantDto) {

        final UserAssistant userAssistant = userAssistantRepository
                .save(userAssistantMapper.userAssistantDtoToUserAssistant(userAssistantDto));
        return userAssistantMapper.userAssistantToUserAssistantDto(userAssistant);
    }

    /**
     * de link assistant from the profession
     *
     * @param userAssistantDto {@link UserAssistantDto}
     */
    public void removeAssistant(UserAssistantDto userAssistantDto) {
        userAssistantRepository.deleteById(userAssistantDto.getId());

        if (userAssistantDto.getStatusId().equals(UserStatus.REJECTED.getUserStatus())) {
            publisher.publishEvent(userAssistantDto, EventType.ASSISTANT_REJECTED_PLS);
        } else {
            publisher.publishEvent(userAssistantDto, EventType.ASSISTANT_UNLINKED_PLS);
        }
    }

    /**
     * @param userId userId
     * @return Collection<UserRoleDto>
     */
    public Collection<UserRoleDto> getUserRoles(final Long userId) {
        final User userByUserId = userRepository.findUserByUserId(userId);
        return userByUserId.getUserRoles()
                .stream()
                .map(userRoleMapper::userRoleToUserRoleDto)
                .collect(Collectors.toList());
    }

    /**
     * @param userId userId
     * @return {@link UserMetaDataDto}
     */
    public UserMetaDataDto getUserAdditionalInfo(final Long userId) {
        final User userByUserId = userRepository.findUserByUserId(userId);

        final UserMetaData userMetaData = userByUserId.getUserMetaData();

        return userMetaDataMapper.userMetaDataToUserMetaDataDto(userMetaData);
    }

    /**
     * @param passwordChangeVm {@link PasswordChangeVm}
     * @return {@link UpdateResponse}
     */
    public UpdateResponse updateUserPasswordByEmail(final PasswordChangeVm passwordChangeVm) {
        final User user = userRepository.findByEmailIgnoreCase(passwordChangeVm.getEmail());
        if (null != user) {
            final String password = user.getPassword();
            if (passwordEncoder.matches(passwordChangeVm.getOldPassword(), password)) {
                user.setPassword(passwordEncoder.encode(passwordChangeVm.getNewPassword()));
                user.setResetPassword(0L);
                user.setTempPassword(passwordChangeVm.getNewPassword());
                final User save = userRepository.save(user);
                publisher.publishEvent(save, EventType.USER_PASSWORD_RESET);
                return UpdateResponse.builder().update(true).build();
            } else {
                throw new PasswordMismatchException(messages.getMessage("UserService.passwordMismatch"));
            }
        } else {
            throw new UsernameNotFoundException(messages.getMessage("UserService.userNotFound",
                    Collections.singletonList(passwordChangeVm.getEmail())));
        }
    }

    /**
     * @param changeEmailVm {@link ChangeEmailVm}
     * @return {@link UpdateResponse}
     */
    public UpdateResponse updateUserEmailInfo(final ChangeEmailVm changeEmailVm) {
        User user = this.userRepository.getUserByIdAndEmail(changeEmailVm.getUserId(), changeEmailVm.getOldEmail());
        if (user == null)
            return UpdateResponse.builder().update(false).build();

        user.setEmail(changeEmailVm.getNewEmail());
        user.setUserName(changeEmailVm.getNewEmail());
        User updatedUser = userRepository.save(user);
        publisher.publishEvent(updatedUser, EventType.USER_CHANGED_EMAIL);
        return UpdateResponse.builder().update(true).build();
    }

    /**
     * @param securityInfoVm {@link SecurityInfoVm}
     * @return {@link UpdateResponse}
     */
    public UpdateResponse updateSecurityQuestionInfo(final SecurityInfoVm securityInfoVm) {
        final User userByUserId = userRepository.findUserByUserId(securityInfoVm.getUserId());
        if (null != userByUserId) {
            final UserMetaData userMetaData = userByUserId.getUserMetaData();
            userMetaData.setSecurityQuestionItemId1(securityInfoVm.getSecurityQuestionTypeItemId1());
            userMetaData.setSecurityQuestionItemId2(securityInfoVm.getSecurityQuestionTypeItemId2());
            userMetaData.setSecurityQuestionItemId3(securityInfoVm.getSecurityQuestionTypeItemId3());
            userMetaData.setSecurityAnswer1(passwordEncoder.encode(securityInfoVm.getSecurityAnswer1()));
            userMetaData.setSecurityAnswer2(passwordEncoder.encode(securityInfoVm.getSecurityAnswer2()));
            userMetaData.setSecurityAnswer3(passwordEncoder.encode(securityInfoVm.getSecurityAnswer3()));
            userRepository.save(userByUserId);
            return UpdateResponse.builder().update(true).build();
        }
        return UpdateResponse.builder().update(false).build();
    }

    /**
     * @param userId userId
     * @param status status
     * @return {@link UpdateResponse}
     */
    public UpdateResponse updateUserStatus(Long userId, UserStatus status) {
        User user = userRepository.getUserById(userId);

        if (user == null)
            return UpdateResponse.builder().update(false).build();


        if (status.equals(UserStatus.UNLOCK)) {
            user.setStatusId(UserStatus.ACTIVE.getUserStatus());
        } else if (status.equals(UserStatus.APPROVED)) {
            user.setStatusId(UserStatus.ACTIVE.getUserStatus());
            final String s = RandomStringUtils.randomAlphanumeric(SHORT_ID_LENGTH);
            user.setTempPassword(s);
            user.setPassword(passwordEncoder.encode(s));

        } else {
            user.setStatusId(status.getUserStatus());
        }

        User updatedUser = userRepository.save(user);

        if (status.equals(UserStatus.ACTIVE))
            publisher.publishEvent(updatedUser, EventType.USER_ACTIVATE);
        else if (status.equals(UserStatus.INACTIVE))
            publisher.publishEvent(updatedUser, EventType.USER_DEACTIVATE);
        else if (status.equals(UserStatus.APPROVED)) {
            publisher.publishEvent(user, EventType.USER_APPROVED);
        } else if (status.equals(UserStatus.REJECTED))
            publisher.publishEvent(updatedUser, EventType.USER_REJECTED);
        else if (status.equals(UserStatus.LOCK))
            publisher.publishEvent(updatedUser, EventType.USER_LOCKED);

        return UpdateResponse.builder().update(true).build();
    }

    /**
     * @param emailId emailId
     * @return {@link SecurityInfoVm}
     */
    public SecurityInfoVm getUserSecurityQuestions(final String emailId) {
        final User byEmail = userRepository.findByEmailIgnoreCase(emailId);
        if (null == byEmail) {
            throw new UsernameNotFoundException(messages.getMessage("UserService.userNameNotFound",
                    Collections.singleton(emailId)));
        }
        final UserMetaData userMetaData = byEmail.getUserMetaData();
        if (null != userMetaData) {
            SecurityInfoVm securityInfoVm = new SecurityInfoVm();
            securityInfoVm.setUserId(byEmail.getUserId());
            securityInfoVm.setSecurityQuestionTypeItemId1(userMetaData.getSecurityQuestionItemId1());
            securityInfoVm.setSecurityQuestionTypeItemId2(userMetaData.getSecurityQuestionItemId2());
            securityInfoVm.setSecurityQuestionTypeItemId3(userMetaData.getSecurityQuestionItemId3());
            securityInfoVm.setSecurityAnswer1(userMetaData.getSecurityAnswer1());
            securityInfoVm.setSecurityAnswer2(userMetaData.getSecurityAnswer2());
            securityInfoVm.setSecurityAnswer3(userMetaData.getSecurityAnswer3());

            return securityInfoVm;
        }

        return null;
    }

    /**
     * @param securityQuestionTypeItemId securityQuestionTypeItemId
     * @param answer                     answer
     * @return Boolean 1/0
     */
    public Boolean verifySecurityAnswer(final Long securityQuestionTypeItemId, final String answer, final String email) {
        final User userByEmail = userRepository.findByEmailIgnoreCase(email);

        if (null != userByEmail) {
            final UserMetaData userMetaData = userByEmail.getUserMetaData();
            if (null != userMetaData) {
                if (securityQuestionTypeItemId.equals(userMetaData.getSecurityQuestionItemId1())) {
                    return passwordEncoder.matches(answer, userMetaData.getSecurityAnswer1());
                } else if (securityQuestionTypeItemId.equals(userMetaData.getSecurityQuestionItemId2())) {
                    return passwordEncoder.matches(answer, userMetaData.getSecurityAnswer2());
                } else if (securityQuestionTypeItemId.equals(userMetaData.getSecurityQuestionItemId3())) {
                    return passwordEncoder.matches(answer, userMetaData.getSecurityAnswer3());
                }
            }
        }
        return false;
    }

    public Boolean resetForgotPassword(final String email) {
        final User userById = userRepository.findByEmailIgnoreCase(email);
        final String s = RandomStringUtils.randomAlphanumeric(SHORT_ID_LENGTH);
        userById.setTempPassword(s);
        userById.setPassword(passwordEncoder.encode(s));
        userById.setResetPassword(1L);
        final User save = userRepository.save(userById);
        publisher.publishEvent(userById, EventType.USER_FORGOT_PASSWORD);
        return true;
    }

    public UserRoleDto addAdditionalRole(final UserRoleDto userRoleDto) {
        final UserRole userRole = userRoleMapper.userRoleDtoToUserRole(userRoleDto);
        final UserRole save = userRoleRepository.save(userRole);
        return userRoleMapper.userRoleToUserRoleDto(save);
    }


    /**
     * @param userBasicInfoDto {@link UserBasicInfoDto}
     * @return {@link UserBasicInfoDto}
     */
    public UserBasicInfoDto updateUserBasicInfo(UserBasicInfoDto userBasicInfoDto) {

        User userByUserId = userRepository.findUserByUserId(userBasicInfoDto.getUserId());
        userByUserId.setStatusId(userBasicInfoDto.getStatusId());
        userByUserId.setCountryCode(userBasicInfoDto.getCountryCode());
        userByUserId.setTitleItemId(userBasicInfoDto.getTitleItemId());
        userByUserId.setFirstName(userBasicInfoDto.getFirstName());
        userByUserId.setSurname(userBasicInfoDto.getSurname());
        userByUserId.setTelephoneNo(userBasicInfoDto.getTelephoneNo());
        userByUserId.setMobileNo(userBasicInfoDto.getMobileNo());
        userByUserId.setProvinceId(userBasicInfoDto.getProvinceId());

        final User save = userRepository.save(userByUserId);

        publisher.publishEvent(save, EventType.USER_PROFILE_UPDATE);

        return userMapper.userToUserBasicInfoDto(save);

    }


    @Deprecated
    public UserDto authenticateExternalUser(final LoginVm loginVm) {
        User user = this.userRepository.findByUserNameAndUserTypeItemId(loginVm.getUsername(), UserType.EXTERNAL);
        if (null == user) {
            throw new UserException(" Username  not found ");
        }

        if (user.getStatusId().equals(UserStatus.PENDING.getUserStatus())) {
            throw new UserException(" User  is in pending status");
        }

        if (user.getStatusId().equals(UserStatus.INACTIVE.getUserStatus())) {
            throw new UserException(" User  is in inactive status");
        }

        if (user.getStatusId().equals(UserStatus.LOCK.getUserStatus())) {
            throw new UserException(" User is Locked ");
        }

        if (passwordEncoder.matches(loginVm.getPassword(), user.getPassword())) {
            //publisher.publishEvent(user, EventType.USER_LOGGED_IN_SUCCESSFULLY);
            return userMapper.userToUserDto(user);
        } else {
            throw new UserException("username " + loginVm.getUsername() + " or password " + loginVm.getPassword() + " not found");
        }
    }


    /**
     * @param userId userId
     * @return {@link UserDto}
     */
    public UserDto getUserByUserId(final Long userId) {
        User userByUserId = userRepository.findUserByUserId(userId);
        if (null != userByUserId)
            return userMapper.userToUserDto(userByUserId);
        return null;
    }

    public byte[] getProfilePicture() {
        final SecurityUser user = currentLoggedInUser.getUser();
        return getPicture(user.getUserId());
    }

    public byte[] getProfilePicture(final Long userId) {
        return getPicture(userId);
    }

    private byte[] getPicture(final Long userId) {

        final UserPhoto byUserid = userPhotoRepository.findByUserid(userId);
        if (null != byUserid) {
            return byUserid.getPhoto();
        } else {

            try {
                final InputStream inputStream = new ClassPathResource("user.jpeg")
                        .getInputStream();
                return FileCopyUtils.copyToByteArray(inputStream);

            } catch (IOException e) {
                log.error(" user default image file not found ");
                e.printStackTrace();
            }

        }

        return null;

    }

    /**
     * Get professional users based on professional user type.
     *
     * @param userType userType
     * @param pageable {@link Pageable}
     * @return Page<UserProfessionalDto>
     */
    public Page<UserProfessionalDto> getProfessional(UserType userType, Pageable pageable) {
        return userProfessionalRepository.findAllByProfessionTypeItemId(userType.getUserType(), pageable)
                .map(userProfessionalMapper::userProfessionalToUserProfessionalDTO);

    }

    /**
     * @param userProfessionalDto {@link UserProfessionalDto}
     * @return {@link UserProfessionalDto}
     */
    public UserProfessionalDto saveUserProfessional(final UserProfessionalDto userProfessionalDto) {
        UserProfessional userProfessional = userProfessionalRepository.findByPpnNo(userProfessionalDto.getPpnNo());
        if ((userProfessionalDto.getUserProfessionalId() == 0 || userProfessionalDto.getUserProfessionalId() == null)
                && userProfessional != null) {
            throw new ResourceNotFoundException("User exists !!");
        }
        return userProfessionalMapper.userProfessionalToUserProfessionalDTO(
                userProfessionalRepository.save(
                        userProfessionalMapper.userProfessionalDTOToUserProfessional(userProfessionalDto)));
    }

    /**
     * @param editableUserMetaDataDto {@link EditableUserMetaDataDto}
     * @return {@link EditableUserMetaDataDto}
     */
    public EditableUserMetaDataDto saveUserMetaData(final EditableUserMetaDataDto editableUserMetaDataDto) {
        final UserMetaData userMetaData = userMetaDataMapper.editableUserMetaDataDtoToUserMetaData(editableUserMetaDataDto);
        userMetaData.setUser(userRepository.findUserByUserId(editableUserMetaDataDto.getUserID()));
        return userMetaDataMapper
                .userMetaDataToEditableUserMetaDataDto(userMetaDataRepository.save(userMetaData));
    }

    /**
     * @param userId userId
     * @return EditableUserMetaDataDto {@link EditableUserMetaDataDto}
     */
    public EditableUserMetaDataDto getUserMetaData(final Long userId) {
        final UserMetaData userMetaData = userMetaDataRepository.findUserMetaDataByUserId(userId);
        if (null != userMetaData) {
            final EditableUserMetaDataDto editableUserMetaDataDto = userMetaDataMapper
                    .userMetaDataToEditableUserMetaDataDto(userMetaData);
            editableUserMetaDataDto.setUserID(userId);
            return editableUserMetaDataDto;
        }

        return null;
    }

    /**
     * @param userId userId
     * @return Collection<UserDocumentDto>
     */
    public Collection<UserDocumentDto> getUserDocument(final Long userId,
                                                       final UserDocumentType documentTypeId) {
        final Collection<UserDocument> userDocument = userDocumentRepository
                .findUserDocumentByUserIdAndDocumentTypeIdAndIsActive(userId, documentTypeId, 1L);
        return userDocument.stream()
                .map(userDocumentMapper::userDocumentToUserDocumentDto)
                .collect(Collectors.toList());
    }

    /**
     * @param documentId documentId
     * @return {@link Resource}
     */
    public Resource downloadUserDocument(final Long documentId) {
        final UserDocument byDocumentId = userDocumentRepository.findByDocumentId(documentId);
        String targetFileName = StorageContext.USER.getStorageContext() +
                FileUtils.PATH_SEPARATOR +
                byDocumentId.getUserId() +
                FileUtils.EXTENSION_SEPARATOR +
                byDocumentId.getContentType();
        log.debug("loading user document from location {}", targetFileName);
        return fileStorageService.loadFileAsResource(targetFileName);
    }

    public User getProvincialUserByProvinceIdAndRoleId(final Long provinceId, final Long roleId) {
        Collection<User> users = userRepository.findUserByProvinceIdAndRoleId(provinceId, roleId);
        if (null != users && users.size() > 0) {
            return (User) users.toArray()[0];
        }
        return null;
    }

    public UserDto getUserByEmailAndRoleId(final String email, final Long roleId) {
        return userMapper.userToUserDto(
                userRepository.findUserByEmailAndRoleId(email, roleId));
    }

    /**
     * @param roleId     roleId
     * @param provinceId provinceId
     * @return Collection<UserDto>
     */
    public Collection<UserDto> getUserForDecision(final Long roleId, final Long provinceId) {
        final List<UserDto> collect = userRepository.getUserForDecision(roleId, provinceId)
                .stream().map(userMapper::userToUserDto)
                .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(collect)) {
            return collect;
        } else {
            final User userByUserId = userRepository.findUserByUserId(1522L);
            return Collections.singletonList(userMapper.userToUserDto(userByUserId));
        }

    }

    /**
     * @param sectionItemId sectionItemId
     * @param provinceId    provinceId
     * @return Collection<ManagerUserDto>
     */
    public Collection<ManagerUserDto> getManagerBySection(Long sectionItemId, Long provinceId) {
        Collection<User> users = userRepository
                .searchUserBySectionAndProvinceId(sectionItemId, provinceId);
        return users.stream().map(user -> {
            ManagerUserDto managerUserDto = new ManagerUserDto();
            managerUserDto.setUserId(user.getUserId());
            managerUserDto.setUserName(StringUtils.defaultIfEmpty(user.getFirstName(), "") + " " +
                    StringUtils.defaultIfEmpty(user.getSurname(), ""));
            return managerUserDto;
        }).collect(Collectors.toSet());

    }

    /**
     * @param emailAddress emailAddress
     * @return 1/0
     */
    public Boolean validateEmailAvailability(String emailAddress) {
        User byEmailAndUserTypeItemId = userRepository
                .findByEmailIgnoreCase(emailAddress);
        return null == byEmailAndUserTypeItemId;
    }

    public Collection<UserDto> searchUsers(String searchKey) {
        return userRepository.searchUsers(searchKey)
                .stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    /**
     * @param assistantId userId of user
     * @return User
     */
    public Collection<UserDto> getProfessionalsToAssistance(final Long assistantId) {

        return userRepository.findProfessionalByAssistanceId(assistantId)
                .stream()
                .map(user -> {
                    UserDto userDto = userMapper.userToUserDto(user);
                    userDto.setAssistantStatusId(106L);
                    userDto.setAssistantId(assistantId);
                    return userDto;
                })
                .collect(Collectors.toList());

    }

    public UserProfessionalDto getProfessionalByPPNo(String ppnNumber) {
        UserProfessional parentProfessionalUser = userProfessionalRepository
                .findByPpnNo(ppnNumber);
        return userProfessionalMapper.userProfessionalToUserProfessionalDTO(parentProfessionalUser);
    }
}
