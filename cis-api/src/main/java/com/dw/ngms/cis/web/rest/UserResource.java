package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.enums.UserDocumentType;
import com.dw.ngms.cis.enums.UserStatus;
import com.dw.ngms.cis.enums.UserType;
import com.dw.ngms.cis.service.UserService;
import com.dw.ngms.cis.service.dto.ManagerUserDto;
import com.dw.ngms.cis.service.dto.UserDto;
import com.dw.ngms.cis.service.dto.user.*;
import com.dw.ngms.cis.service.user.UserHomeSettingsService;
import com.dw.ngms.cis.web.annotation.ApiPageable;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.response.RestMessageResponse;
import com.dw.ngms.cis.web.response.UpdateResponse;
import com.dw.ngms.cis.web.util.PaginationUtil;
import com.dw.ngms.cis.web.vm.ChangeEmailVm;
import com.dw.ngms.cis.web.vm.LoginVm;
import com.dw.ngms.cis.web.vm.PasswordChangeVm;
import com.dw.ngms.cis.web.vm.SecurityInfoVm;
import com.dw.ngms.cis.web.vm.user.UserRegistrationVm;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by nirmal on 2020/11/11.
 */
@BaseResponse
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.API_BASE_MAPPING + "/user")
public class UserResource {

    private final UserService userService;

    private final UserHomeSettingsService userHomeSettingsService;

    /**
     * @param loginVm {@link LoginVm}
     * @return ResponseEntity<UserControllerResponse>
     */
    @Deprecated
    @PostMapping("/authenticate")
    public ResponseEntity<UserDto> authenticateUser(
            @RequestBody @Valid final LoginVm loginVm) {
        return ResponseEntity.ok()
                .body(userService.authenticateExternalUser(loginVm));
    }

    @GetMapping("/verifyUsername")
    public ResponseEntity<Boolean> verifyIfUserNameExists(@RequestParam final String userName) {
        return ResponseEntity.ok().body(userService.verifyIfUserNameExists(userName));
    }

    /**
     * @param userRegistrationVm {@link UserRegistrationVm}
     * @return {@link RestMessageResponse} saved object in db store
     */
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestMessageResponse> registerUser(@ModelAttribute @Valid final UserRegistrationVm userRegistrationVm) {
        final String response = userService.registerUser(userRegistrationVm);
        return ResponseEntity.ok()
                .body(RestMessageResponse.builder().message(response).build());

    }

    /**
     * @param multipartFile {@link MultipartFile}
     * @return 1/0
     */
    @PostMapping("/saveProfileImage")
    public ResponseEntity<Boolean> saveProfileImage(@RequestPart("image") @NotNull final MultipartFile multipartFile) {
        return ResponseEntity.ok()
                .body(userService.saveProfilePicture(multipartFile));
    }

    @GetMapping(value = "/profileImage", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getUserDisplayImage() {
        return ResponseEntity.ok()
                .body(userService.getProfilePicture());
    }

    @GetMapping(value = "/profileImage/{userId}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getDisplayImageByUserId(@PathVariable final Long userId) {
        return ResponseEntity.ok()
                .body(userService.getProfilePicture(userId));
    }

    /**
     * @param ppnNo ppnNo
     * @return ResponseEntity<PlsUser>
     */
    @GetMapping(value = "/verifyProfessional")
    public ResponseEntity<Boolean> verifyUser(@RequestParam final String ppnNo) {
        return ResponseEntity.ok().body(userService.verifyByPpnNo(ppnNo));
    }

    /**
     * @param ppnNo ppnNo
     * @return ResponseEntity<PlsUser>
     */
    @GetMapping(value = "/verifyProfessionalForAssistant")
    public ResponseEntity<Boolean> verifyProfessionalForAssistant(@RequestParam final String ppnNo) {
        return ResponseEntity.ok().body(userService.verifyByPpnNoAndUserStatus(ppnNo));
    }

    /**
     * Add assistant to the professional
     *
     * @param userAssistantDto {@link UserAssistantDto}
     * @return {@link UserAssistantDto}
     */
    @PostMapping(value = "/addAssistant")
    public ResponseEntity<UserAssistantDto> addAssistant(@RequestBody final UserAssistantDto userAssistantDto) {
        return ResponseEntity.ok().body(userService.addAssistant(userAssistantDto));
    }

    /**
     * de-link the assistant user from the professional
     *
     * @param userAssistantDto {@link UserAssistantDto}
     */
    @PostMapping(value = "/removeAssistant")
    public void removeAssistant(@RequestBody final UserAssistantDto userAssistantDto) {
        userService.removeAssistant(userAssistantDto);
    }

    /**
     * get user details based on the user email
     *
     * @param userEmail userEmail
     * @return {@link UserDto}
     */
    @GetMapping(value = "/searchUserByEmail")
    public ResponseEntity<UserDto> searchUserByEmail(@RequestParam final String userEmail) {
        return ResponseEntity.ok().body(userService.getUsersByEmail(userEmail));
    }

    /**
     * get all the assistant listed under the professional
     *
     * @param userId userId
     * @return Collection<UserDto>
     */
    @GetMapping(value = "/listAssistants")
    public ResponseEntity<Collection<UserDto>> listProfessionalAssistants(@RequestParam final Long userId) {
        return ResponseEntity.ok()
                .body(userService.getAssistantToProfessional(userId));
    }

    /**
     * @param userAssistantDto {@link UserAssistantDto}
     */
    @PostMapping(value = "/changeProfessionalAssistantStatus")
    public void changeProfessionalAssistantStatus(@RequestBody final UserAssistantDto userAssistantDto) {
        userService.changeProfessionalAssistantStatus(userAssistantDto);
    }


    /**
     * @param userType userType
     * @param pageable {@link Pageable}
     * @return Collection<UserDto>
     */
    @GetMapping("/list")
    @ApiPageable
    public ResponseEntity<Collection<UserDto>>
    getAllUserByUserType(@RequestParam @NotNull final UserType userType,
                         @ApiIgnore @PageableDefault(size = 500)
                         @SortDefault(sort = "createdDate", direction = Sort.Direction.DESC) final Pageable pageable) {
        final Page<UserDto> userPage = userService.getAllUserByType(userType, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(userPage);
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(userPage.getContent());
    }

    /**
     * @param userId userId
     * @return Collection<ExternalUserRoleDto>
     */
    @GetMapping("/list/roles")
    public ResponseEntity<Collection<UserRoleDto>> getUserRoleByUserId(
            @RequestParam @NotNull final Long userId) {
        return ResponseEntity.ok()
                .body(userService.getUserRoles(userId));
    }

    /**
     * @param userId userId
     * @return ExternalUserDataDto
     */
    @GetMapping("/list/info")
    private ResponseEntity<UserMetaDataDto> getUserAdditionalInfoByUserId(
            @RequestParam @NotNull final Long userId) {
        return ResponseEntity.ok()
                .body(userService.getUserAdditionalInfo(userId));
    }

    /**
     * @param passwordChangeVm {@link PasswordChangeVm}
     * @return ResponseEntity<UpdateResponse>
     */
    @PostMapping("/updatePassword")
    public ResponseEntity<UpdateResponse> updatePassword(
            @RequestBody @Valid final PasswordChangeVm passwordChangeVm) {
        return ResponseEntity.ok()
                .body(userService.updateUserPasswordByEmail(passwordChangeVm));
    }

    /**
     * @param changeEmailVm {@link ChangeEmailVm}
     * @return ResponseEntity<UpdateResponse>
     */
    @PostMapping("/updateEmail")
    public ResponseEntity<UpdateResponse> updateEmail(
            @RequestBody @Valid final ChangeEmailVm changeEmailVm) {
        return ResponseEntity.ok()
                .body(this.userService.updateUserEmailInfo(changeEmailVm));
    }

    /**
     * @param userBasicInfoDto {@link UserBasicInfoDto}
     * @return ResponseEntity<UpdateResponse>
     */
    @PostMapping("/updatePersonalInfo")
    public ResponseEntity<UserBasicInfoDto> updatePersonalInfo(
            @RequestBody @Valid final UserBasicInfoDto userBasicInfoDto) {
        return ResponseEntity.ok()
                .body(userService.updateUserBasicInfo(userBasicInfoDto));
    }

    /**
     * @param securityInfoVm {@link SecurityInfoVm}
     * @return ResponseEntity<UpdateResponse>
     */
    @PostMapping("/updateSecurityInfo")
    public ResponseEntity<UpdateResponse> updateSecurityInfo(
            @RequestBody @Valid final SecurityInfoVm securityInfoVm) {
        return ResponseEntity.ok()
                .body(userService.updateSecurityQuestionInfo(securityInfoVm));
    }

    /**
     * @param userRoleDto {@link UserRoleDto}
     * @return ResponseEntity<UserRoleDto>
     */
    @PostMapping("/addRoleInfo")
    public ResponseEntity<UserRoleDto> addRoleInfo(
            @RequestBody @Valid final UserRoleDto userRoleDto) {
        return ResponseEntity.ok()
                .body(userService.addAdditionalRole(userRoleDto));
    }

    /**
     * @param userId userId
     * @param status {@link UserStatus }
     * @return ResponseEntity<UpdateResponse>
     */
    @GetMapping("/changeStatus")
    public ResponseEntity<UpdateResponse> changeUserStatus(@RequestParam @NotNull final Long userId,
                                                           @RequestParam @Valid final UserStatus status) {
        return ResponseEntity.ok()
                .body(userService.updateUserStatus(userId, status));
    }

    /**
     * @param email user email
     * @return ResponseEntity<ArrayList < Map < String, Object>>>
     */
    @GetMapping("/listSecurityQuestions")
    public ResponseEntity<SecurityInfoVm> listSecurityQuestionsByEmail(
            @RequestParam @NotNull final String email) {
        return ResponseEntity.ok()
                .body(userService.getUserSecurityQuestions(email));
    }

    /**
     * @param securityQuestionTypeItemId securityQuestionTypeItemId
     * @param answer                     answer
     * @return 1/0
     */
    @GetMapping("/verifySecurityAnswers")
    public ResponseEntity<Boolean> verifySecurityAnswer(@RequestParam final Long securityQuestionTypeItemId,
                                                        @RequestParam final String answer, @RequestParam final String email) {
        boolean isValid = userService.verifySecurityAnswer(securityQuestionTypeItemId, answer, email);
        if (isValid) {
            userService.resetForgotPassword(email);
        }
        return ResponseEntity.ok()
                .body(isValid);
    }

    /**
     * @return 1/0
     */
    @GetMapping("/resetForgotPassword/{email}")
    public ResponseEntity<Boolean> resetForgotPassword(@PathVariable(name = "email") final String email) {
        return ResponseEntity.ok()
                .body(userService.resetForgotPassword(email));
    }


    /**
     * :GET /homeSetting?userId=?
     *
     * @param userId user id of user
     * @return user home page setting
     */
    @GetMapping("/homeSetting")
    public ResponseEntity<UserHomeSettingsDto> getUserHomePageSetting(@RequestParam @NotNull final Long userId) {
        return ResponseEntity.ok()
                .body(userHomeSettingsService.getAllSettings(userId));
    }

    /**
     * :POST /homeSetting { userId: "", settingId: "" homepage: "", sections: "" }
     *
     * @param userHomeSettingsDto {@link UserHomeSettingsDto}
     * @return saved user home setting
     */
    @PostMapping("/homeSetting")
    public ResponseEntity<UserHomeSettingsDto> saveUserHomePageSetting(
            @RequestBody final UserHomeSettingsDto userHomeSettingsDto) {
        return ResponseEntity.ok()
                .body(userHomeSettingsService.saveUserHomeSetting(userHomeSettingsDto));
    }

    /**
     * @param userId userId
     * @return {@link UserDto}
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserInfoByUserId(@PathVariable @NotNull final Long userId) {
        return ResponseEntity.ok()
                .body(userService.getUserByUserId(userId));
    }

    /**
     * @param professionalType {@link UserType}
     * @param pageable         {@link Pageable}
     * @return Collection<UserProfessionalDto>
     */
    @GetMapping("/professionals/{professionalType}")
    @ApiPageable
    public ResponseEntity<Collection<UserProfessionalDto>>
    getProfessionls(@PathVariable @NotNull final UserType professionalType,
                    @ApiIgnore final Pageable pageable) {
        final Page<UserProfessionalDto> userProfessionalDtos = userService.getProfessional(professionalType, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(userProfessionalDtos);
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(userProfessionalDtos.getContent());
    }

    /**
     * @param userProfessionalDto {@link UserProfessionalDto}
     * @return {@link UserProfessionalDto}
     */
    @PostMapping("/professional")
    public ResponseEntity<UserProfessionalDto> saveProfessional(@RequestBody final UserProfessionalDto userProfessionalDto) {
        return ResponseEntity.ok()
                .body(userService.saveUserProfessional(userProfessionalDto));
    }

    /**
     * save user meta data
     *
     * @param editableUserMetaDataDto {@link EditableUserMetaDataDto}
     * @return {@link EditableUserMetaDataDto}
     */
    @PostMapping("/saveUserMetaData")
    public ResponseEntity<EditableUserMetaDataDto> saveUserMetaData(@RequestBody final EditableUserMetaDataDto editableUserMetaDataDto) {
        return ResponseEntity.ok()
                .body(userService.saveUserMetaData(editableUserMetaDataDto));
    }

    /**
     * get user meta data.
     *
     * @param userId userId
     * @return {@link EditableUserMetaDataDto}
     */
    @GetMapping("/userMetaData/{userId}")
    public ResponseEntity<EditableUserMetaDataDto> getUserMetaData(@PathVariable @NotNull final Long userId) {
        return ResponseEntity.ok()
                .body(userService.getUserMetaData(userId));
    }

    /**
     * @param userId userId
     * @return Collection<UserDocumentDto>
     */
    @GetMapping("/document")
    public ResponseEntity<Collection<UserDocumentDto>>
    getUserDocuments(@RequestParam final Long userId,
                     @RequestParam final UserDocumentType documentTypeId) {
        return ResponseEntity.ok()
                .body(userService.getUserDocument(userId, documentTypeId));
    }

    /**
     * @param documentId documentId
     * @return {@link Resource}
     */
    @GetMapping(value = "/download/{documentId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadUserDocument(@PathVariable("documentId") final Long documentId)
            throws IOException {
        final Resource resource = userService.downloadUserDocument(documentId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentLength(resource.contentLength());
        httpHeaders.setContentDispositionFormData("attachment",
                resource.getFilename());
        return ResponseEntity.ok()
                .body(resource);
    }

    /**
     * get active users by email id and roleId
     *
     * @param email  email
     * @param roleId roleId
     * @return {@link UserDto}
     */
    @GetMapping(value = "/userByEmailAndRoleId")
    public ResponseEntity<UserDto> userByEmailAndRoleId(@RequestParam final String email, final Long roleId) {
        return ResponseEntity.ok()
                .body(userService.getUserByEmailAndRoleId(email, roleId));
    }

    /**
     * @param roleId     roleId
     * @param provinceId provinceId
     * @return Collection<UserDto>
     */
    @GetMapping("/decision")
    public ResponseEntity<Collection<UserDto>> getUsersForDecision(@RequestParam final Long roleId,
                                                                   @RequestParam final Long provinceId) {
        return ResponseEntity.ok()
                .body(userService.getUserForDecision(roleId, provinceId));
    }

    /**
     *
     * @param sectionItemId sectionItemId
     * @param provinceId provinceId
     * @return Collection<ManagerUserDto> {@link ManagerUserDto}
     */
    @GetMapping("/getManagerBySection")
    public ResponseEntity<Collection<ManagerUserDto>> getManagerBySectionAndProvince(@RequestParam final Long sectionItemId,
                                                                                     @RequestParam final Long provinceId) {
        return ResponseEntity.ok()
                .body(userService.getManagerBySection(sectionItemId,provinceId));
    }

    /**
     *
     * @param emailAddress emailAddress
     * @return 1/0
     */
    @GetMapping("/emailAvailability")
    public ResponseEntity<Boolean> validateEmailAddressAvailability(@RequestParam @NotNull final String emailAddress) {
        Boolean isAvailable = userService.validateEmailAvailability(emailAddress);
        return ResponseEntity.ok().body(isAvailable);
    }

    @GetMapping("/searchUsers")
    public ResponseEntity<Collection<UserDto>> searchUsers(@RequestParam @NotNull final String searchKey){
        return ResponseEntity.ok()
                .body(userService.searchUsers(searchKey));
    }

    /**
     * get all the assistant listed under the professional
     *
     * @param assistantId userId
     * @return Collection<UserDto>
     */
    @GetMapping(value = "/listProfessionalsPerAssistant")
    public ResponseEntity<Collection<UserDto>> listProfessionalsPerAssistant(@RequestParam final Long assistantId) {
        return ResponseEntity.ok()
                .body(userService.getProfessionalsToAssistance(assistantId));
    }

    /**
     * get user meta data.
     *
     * @param ppnNumber userId
     * @return {@link UserProfessionalDto}
     */
    @GetMapping("/professionalUser/{ppnNumber}")
    public ResponseEntity<UserProfessionalDto> getProfessional(@PathVariable @NotNull final String ppnNumber) {
        return ResponseEntity.ok()
                .body(userService.getProfessionalByPPNo(ppnNumber));
    }
}
