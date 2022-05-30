package com.dw.ngms.cis.service.dto.user;

import lombok.Data;

/**
 * @author : pragayanshu
 * @since : 2021/04/27, Tue
 **/
@Data
public class UserAssistantDto {
    private Long id;
    private Long userId;
    private Long assistantId;
    private Long statusId;
    private String comment;
}
