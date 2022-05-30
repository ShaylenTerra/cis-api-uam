package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.persistence.domains.Notifications;
import com.dw.ngms.cis.service.NotificationsService;
import com.dw.ngms.cis.service.dto.user.UserNotificationDto;
import com.dw.ngms.cis.web.annotation.ApiPageable;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.util.PaginationUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 29/12/20, Tue
 **/
@BaseResponse
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.API_BASE_MAPPING + "/notifications")
public class NotificationResource {

    private final NotificationsService notificationsService;

    /**
     * @param userTypeItemId user type whether internal or external
     * @return Collection<Notifications> {@link Notifications}
     */
    @GetMapping(value = "/list")
    @ApiPageable
    public ResponseEntity<Collection<Notifications>>
    listIssueLog(@RequestParam @NotNull final String userTypeItemId,
                 @ApiIgnore @SortDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Notifications> notifications = notificationsService
                .listTopNotificationsByUserTypeItemId(Long.parseLong(userTypeItemId), pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(notifications);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(notifications.getContent());
    }

    /**
     *
      * @param userNotificationDto
     */
    @PostMapping("/addUserNotifications")
    public void addUserNotification(@RequestBody @Valid final UserNotificationDto userNotificationDto) {
        notificationsService.addUserNotification(userNotificationDto);
    }
}
