package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.enums.UserLeaveCalendarStatus;
import com.dw.ngms.cis.persistence.domains.document.UserDocument;
import com.dw.ngms.cis.service.dto.user.UserLeaveCalendarDto;
import com.dw.ngms.cis.service.user.UserLeaveCalendarService;
import com.dw.ngms.cis.web.annotation.ApiPageable;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.util.PaginationUtil;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 04/12/20, Fri
 **/
@BaseResponse
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.API_BASE_MAPPING + "/user/leave")
public class UserLeaveCalendarResource {

    private final UserLeaveCalendarService userLeaveCalendarService;


    /**
     * @param userId   userId of user
     * @param pageable {@link Pageable}
     * @return collection of UserLeaveCalendarDto
     */
    @GetMapping
    @ApiPageable
    public ResponseEntity<Collection<UserLeaveCalendarDto>> getAllLeavesForUser(@NotNull @RequestParam final Long userId,
                                                                                @ApiIgnore final Pageable pageable) {
        Page<UserLeaveCalendarDto> allUserLeavesByUserId = userLeaveCalendarService.getAllUserLeavesByUserId(userId, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(allUserLeavesByUserId);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(allUserLeavesByUserId.getContent());
    }

    /**
     * @param roleId   userId of user
     * @param pageable {@link Pageable}
     * @return collection of UserLeaveCalendarDto
     */
    @GetMapping("/leaveForReview")
    @ApiPageable
    public ResponseEntity<Collection<UserLeaveCalendarDto>> getAllLeavesForManager(@NotNull @RequestParam final Long roleId,
                                                                                   @ApiIgnore final Pageable pageable) {
        Page<UserLeaveCalendarDto> allUserLeavesByUserId = userLeaveCalendarService.getAllUserLeavesByManager(roleId, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(allUserLeavesByUserId);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(allUserLeavesByUserId.getContent());
    }

    /**
     * @param userLeaveCalendarDto {@link UserLeaveCalendarDto}
     * @return UserLeaveCalendarDto
     */
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserLeaveCalendarDto>
    saveUserLeave(@ModelAttribute @NotNull final UserLeaveCalendarDto userLeaveCalendarDto) {
        return ResponseEntity.ok()
                .body(userLeaveCalendarService.addUserLeave(userLeaveCalendarDto));
    }

    /**
     * @param leaveId leaveid
     * @param status  status[ACTIVE/INACTIVE]
     * @return int value how many rows get affected by update operation.
     */
    @PostMapping("/activate")
    public ResponseEntity<?> activateUserLeave(@RequestParam @NotNull final Long leaveId,
                                               UserLeaveCalendarStatus status, final String note,final Long loggedInUser) {
        return ResponseEntity.ok()
                .body(userLeaveCalendarService.updateLeaveStatus(leaveId, status, note,loggedInUser));
    }

    /**
     * @param userLeaveCalendarDto {@link UserLeaveCalendarDto}
     */
    @PostMapping("/deleteLeave")
    public void deleteLeave(@RequestBody @Valid final UserLeaveCalendarDto userLeaveCalendarDto) {
        userLeaveCalendarService.deleteLeave(userLeaveCalendarDto);
    }

    /**
     * @param contextId contextID
     * @return {@link Resource}
     */
    @GetMapping(value = "/download",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadSupportingDocument(@RequestParam @NotNull final Long contextId,
                                                               @NotNull final Long contextTypeId) throws IOException {
        UserDocument userDocument = userLeaveCalendarService.getDocument(contextId, contextTypeId);
        Resource resource = userLeaveCalendarService.getUserDocument(contextId, contextTypeId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentLength(resource.contentLength());
        httpHeaders.setContentDispositionFormData("attachment", userDocument.getFileName());

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(resource);

    }

    /**
     *
     * @param contextId contextId
     * @param contextTypeId contextTypeId
     * @return {@link UserDocument}
     */

    @GetMapping("/getLeaveDocument")
    public ResponseEntity<UserDocument> getLeaveDocument(@RequestParam @NotNull final Long contextId,
                                                         @NotNull final Long contextTypeId) {
        UserDocument userDocument = userLeaveCalendarService.getDocument(contextId, contextTypeId);
        return ResponseEntity.ok()
                 .body(userDocument);
    }

}

