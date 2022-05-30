package com.dw.ngms.cis.service.dto.user;

import lombok.Data;

import java.util.Date;

/**
 * @author : pragayanshu
 * @since : 26/02/2021, Fri
 **/
@Data
public class UserNotificationDto {

    private Long Id;
    private String initials;
    private String userName;
    private String subject;
    private String description;
    private long contextId;
    private long contextTypeId;
    private Date createOn;
    private Long loggedInUserId;
    private Long notifyUserId;
}
