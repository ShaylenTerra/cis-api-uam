package com.dw.ngms.cis.service.dto.user;

import lombok.Data;

import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 19/12/20, Sat
 **/
@Data
public class UserDelegationsDto {

    private Long id;

    private Long userId;

    private Long delegateUserId;

    private Date fromDate;

    private Date toDate;

    private String description;

    private Long statusItemId;

}
