package com.dw.ngms.cis.web.vm;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by nirmal on 2020/11/21.
 */
@Data
public class SecurityInfoVm {

    @NotNull(message = "user id must not be null")
    private Long userId;

    private Long securityQuestionTypeItemId1;

    private Long securityQuestionTypeItemId2;

    private Long securityQuestionTypeItemId3;

    private String securityAnswer1;

    private String securityAnswer2;

    private String securityAnswer3;

}
