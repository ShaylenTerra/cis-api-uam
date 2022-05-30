package com.dw.ngms.cis.service.user;

import com.dw.ngms.cis.enums.StorageContext;
import com.dw.ngms.cis.enums.UserDocumentType;
import com.dw.ngms.cis.enums.UserLeaveCalendarStatus;
import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.domains.document.UserDocument;
import com.dw.ngms.cis.persistence.domains.listmanagement.ListItem;
import com.dw.ngms.cis.persistence.domains.user.SecurityUser;
import com.dw.ngms.cis.persistence.domains.user.UserLeaveCalendar;
import com.dw.ngms.cis.persistence.domains.user.UserNotification;
import com.dw.ngms.cis.persistence.repository.UserRepository;
import com.dw.ngms.cis.persistence.repository.document.UserDocumentRepository;
import com.dw.ngms.cis.persistence.repository.listmanagement.ListItemRepository;
import com.dw.ngms.cis.persistence.repository.user.UserLeaveCalenderRepository;
import com.dw.ngms.cis.persistence.repository.user.UserNotificationRepository;
import com.dw.ngms.cis.security.CurrentLoggedInUser;
import com.dw.ngms.cis.service.FileStorageService;
import com.dw.ngms.cis.service.dto.user.UserLeaveCalendarDto;
import com.dw.ngms.cis.service.mapper.user.UserLeaveCalendarMapper;
import com.dw.ngms.cis.utilities.FileUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 11/02/21, Thu
 **/

@Service
@AllArgsConstructor
@Slf4j
public class UserLeaveCalendarService {

    private final UserLeaveCalenderRepository userLeaveCalenderRepository;

    private final UserDocumentRepository userDocumentRepository;

    private final UserLeaveCalendarMapper userLeaveCalendarMapper;

    private final FileStorageService fileStorageService;

    private final UserRepository userRepository;

    private final UserNotificationRepository userNotificationRepository;

    private final ListItemRepository listItemRepository;

    private final CurrentLoggedInUser currentLoggedInUser;

    /**
     * @param userLeaveCalendarDto userLeaveCalendarDto
     * @return UserLeaveCalendarDto
     */
    public UserLeaveCalendarDto addUserLeave(final UserLeaveCalendarDto userLeaveCalendarDto) {
        userLeaveCalendarDto.setStatus(UserLeaveCalendarStatus.PENDING);
        UserLeaveCalendarDto userLeaveCalendarDto1 = userLeaveCalendarMapper
                .userLeaveCalendarToUserLeaveCalendarDto(
                        userLeaveCalenderRepository
                                .save(userLeaveCalendarMapper.userLeaveCalendarDtoToUserLeaveCalendar(userLeaveCalendarDto))
                );
        MultipartFile supportingDocument = userLeaveCalendarDto.getMultipartFile();

        //insert record in documents
        if (supportingDocument != null) {
            UserDocument userDocument = new UserDocument();
            userDocument.setContextId(userLeaveCalendarDto1.getLeaveId());
            userDocument.setUserId(userLeaveCalendarDto1.getUserId());
            userDocument.setContext("Supporting Documents Leave");
            userDocument.setIsActive(1L);
            userDocument.setContextTypeId(5052L);
            userDocument.setCreatedOn(LocalDateTime.now());
            userDocument.setDocumentTypeId(UserDocumentType.USER_LEAVE_CALENDAR);
            userDocument.setContentType(FileUtils.getFileExtension(supportingDocument.getOriginalFilename()));
            final UserDocument savedUserDocument = userDocumentRepository.save(userDocument);
            //save file on disk
            final String targetLocation = StorageContext.NEW_LEAVE.getStorageContext() +
                    FileUtils.PATH_SEPARATOR +
                    userDocument.getDocumentId();
            final String document = fileStorageService.storeFile(supportingDocument, targetLocation);
            savedUserDocument.setFileName(document);
            userDocumentRepository.save(savedUserDocument);

        }
        UserNotification userNotification = new UserNotification();
        userNotification.setCreateOn(new Date());
        userNotification.setCreatedByUserId(userLeaveCalendarDto1.getUserId());
        userNotification.setCreatedForUserId(userLeaveCalendarDto1.getUserId());
        userNotification.setSubject("new leave created");
        userNotification.setDescription(userLeaveCalendarDto1.getDescription());
        userNotification.setContextId(userLeaveCalendarDto1.getLeaveId());
        userNotification.setContextTypeId(5053L);
        userNotification.setActive(1);
        userNotificationRepository.save(userNotification);


        return userLeaveCalendarDto1;


    }

    /**
     * @param userId   user id of user
     * @param pageable {@link Pageable}
     * @return Page<UserLeaveCalendarDto>
     */
    public Page<UserLeaveCalendarDto> getAllUserLeavesByUserId(final Long userId, Pageable pageable) {
        return userLeaveCalenderRepository.findAllByUserId(userId, pageable)
                .map(userLeaveCalendar -> {
                    UserLeaveCalendarDto userLeaveCalendarDto = userLeaveCalendarMapper
                            .userLeaveCalendarToUserLeaveCalendarDto(userLeaveCalendar);
                    //Set document count
                    userLeaveCalendarDto.setDocumentCount(userDocumentRepository
                            .countByContextTypeIdAndContextId(5052L, userLeaveCalendarDto.getLeaveId()));
                    //Set userName
                    User user = userRepository.getUserById(userLeaveCalendarDto.getUserId());
                    if (user != null) {
                        userLeaveCalendarDto.setUserName(user.getFirstName() + " " + user.getSurname());
                    }

                    //Leave type caption
                    ListItem listItem = listItemRepository.findByItemId(userLeaveCalendarDto.getLeaveTypeId());
                    if (listItem != null) {
                        userLeaveCalendarDto.setLeaveTypeCaption(listItem.getCaption());
                    }
                    return userLeaveCalendarDto;
                });
    }

    /**
     * @param roleId   role id of user
     * @param pageable {@link Pageable}
     * @return Page<UserLeaveCalendarDto>
     */
    public Page<UserLeaveCalendarDto> getAllUserLeavesByManager(final Long roleId, Pageable pageable) {

        Page<UserLeaveCalendarDto> userLeaveCalendarDtos = null;
        SecurityUser user1 = currentLoggedInUser.getUser();

        Long provinceId = user1.getProvinceId();
        //41, 35
        //leave reviewer is national admin
        if(roleId.equals(41L)) {
            userLeaveCalendarDtos = nationalAdminLeaveReviewer(pageable);
        }
        else if(roleId.equals(35L)) {
            //leave reviewer is national admin
            userLeaveCalendarDtos = provincialAdminLeaveReviewer(provinceId,pageable);
        }
        else {
            //if it reviewer id reporting manager
            userLeaveCalendarDtos = reportingManagerLeaveReviewer(user1.getUserId(),pageable);
        }
        return userLeaveCalendarDtos;

    }

    private Page<UserLeaveCalendarDto> nationalAdminLeaveReviewer(Pageable pageable) {
        return userLeaveCalenderRepository.findByOrderByLeaveIdDesc(pageable)
                .map(userLeaveCalendar -> {
                    UserLeaveCalendarDto userLeaveCalendarDto = userLeaveCalendarMapper
                            .userLeaveCalendarToUserLeaveCalendarDto(userLeaveCalendar);
                    userLeaveCalendarDto.setDocumentCount(userDocumentRepository
                            .countByContextTypeIdAndContextId(5052L, userLeaveCalendarDto.getLeaveId()));
                    //Set userName
                    User user = userRepository.getUserById(userLeaveCalendarDto.getUserId());
                    if (user != null) {
                        String userName = user.getFirstName() + " " + user.getSurname();
                        userLeaveCalendarDto.setUserName(userName);
                    }
                    //Leave type caption
                    ListItem listItem = listItemRepository.findByItemId(userLeaveCalendarDto.getLeaveTypeId());
                    if (listItem != null) {
                        userLeaveCalendarDto.setLeaveTypeCaption(listItem.getCaption());
                    }
                    return userLeaveCalendarDto;
                });
    }

    private Page<UserLeaveCalendarDto> provincialAdminLeaveReviewer(final Long provinceID,Pageable pageable) {
        return userLeaveCalenderRepository.searchUserLeaveForProvincialAdmin(provinceID,pageable)
                .map(userLeaveCalendar -> {
                    UserLeaveCalendarDto userLeaveCalendarDto = userLeaveCalendarMapper
                            .userLeaveCalendarToUserLeaveCalendarDto(userLeaveCalendar);
                    userLeaveCalendarDto.setDocumentCount(userDocumentRepository
                            .countByContextTypeIdAndContextId(5052L, userLeaveCalendarDto.getLeaveId()));
                    //Set userName
                    User user = userRepository.getUserById(userLeaveCalendarDto.getUserId());
                    if (user != null) {
                        String userName = user.getFirstName() + " " + user.getSurname();
                        userLeaveCalendarDto.setUserName(userName);
                    }
                    //Leave type caption
                    ListItem listItem = listItemRepository.findByItemId(userLeaveCalendarDto.getLeaveTypeId());
                    if (listItem != null) {
                        userLeaveCalendarDto.setLeaveTypeCaption(listItem.getCaption());
                    }
                    return userLeaveCalendarDto;
                });
    }

    private Page<UserLeaveCalendarDto> reportingManagerLeaveReviewer(final Long userId,Pageable pageable) {
        return userLeaveCalenderRepository.searchUserLeaveForLoggedInUser(userId,pageable)
                .map(userLeaveCalendar -> {
                    UserLeaveCalendarDto userLeaveCalendarDto = userLeaveCalendarMapper
                            .userLeaveCalendarToUserLeaveCalendarDto(userLeaveCalendar);
                    userLeaveCalendarDto.setDocumentCount(userDocumentRepository
                            .countByContextTypeIdAndContextId(5052L, userLeaveCalendarDto.getLeaveId()));
                    //Set userName
                    User user = userRepository.getUserById(userLeaveCalendarDto.getUserId());
                    if (user != null) {
                        String userName = user.getFirstName() + " " + user.getSurname();
                        userLeaveCalendarDto.setUserName(userName);
                    }
                    //Leave type caption
                    ListItem listItem = listItemRepository.findByItemId(userLeaveCalendarDto.getLeaveTypeId());
                    if (listItem != null) {
                        userLeaveCalendarDto.setLeaveTypeCaption(listItem.getCaption());
                    }
                    return userLeaveCalendarDto;
                });
    }

    /**
     * @param leaveId leaveId of leave
     * @return number of rows affected
     */
    @Transactional
    public int updateLeaveStatus(final Long leaveId, UserLeaveCalendarStatus userLeaveCalendarStatus, final String note, final long loggedInUserId) {
        UserLeaveCalendar userLeaveCalendar = userLeaveCalenderRepository.findByLeaveId(leaveId);
        UserNotification userNotification = new UserNotification(null, new Date(), loggedInUserId, userLeaveCalendar.getUserId(),
                userLeaveCalendarStatus.name(), note, leaveId, 5053L, 1);
        userNotificationRepository.save(userNotification);
        return userLeaveCalenderRepository.updateLeaveStatus(leaveId, userLeaveCalendarStatus, note);
    }

    /**
     * @param userLeaveCalendarDto {@link UserLeaveCalendarDto}
     */
    public void deleteLeave(final UserLeaveCalendarDto userLeaveCalendarDto) {
        userLeaveCalenderRepository.deleteById(userLeaveCalendarDto.getLeaveId());
    }

    /**
     * @param contextId     contextId
     * @param contextTypeID contextTypeID
     * @return {@link UserDocument}
     */
    public Resource getUserDocument(final Long contextId, final Long contextTypeID) {
        UserDocument userDocument = userDocumentRepository
                .findByContextTypeIdAndContextId(contextTypeID, contextId);
        String targetLocation = StorageContext.NEW_LEAVE.getStorageContext() +
                FileUtils.PATH_SEPARATOR +
                userDocument.getDocumentId() +
                FileUtils.EXTENSION_SEPARATOR +
                userDocument.getContentType();
        log.debug("loading user leave document from location {} ", targetLocation);
        return fileStorageService.loadFileAsResource(targetLocation);
    }

    /**
     * @param contextId     contextId
     * @param contextTypeID contextTypeID
     * @return {@link UserDocument}
     */
    public UserDocument getDocument(final Long contextId, final Long contextTypeID) {
        return userDocumentRepository
                .findByContextTypeIdAndContextId(contextTypeID, contextId);
    }


    public Boolean checkIfDateExistsInUserLeave(final Long userId, final LocalDate date) {
        UserLeaveCalendar ifDateExistsBetweenStartAndEndDate = userLeaveCalenderRepository
                .findIfDateExistsBetweenStartAndEndDate(userId, date);

        return null != ifDateExistsBetweenStartAndEndDate;
    }
}
