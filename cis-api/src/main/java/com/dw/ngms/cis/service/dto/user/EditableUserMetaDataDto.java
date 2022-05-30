package com.dw.ngms.cis.service.dto.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EditableUserMetaDataDto {
    private Long userMetaDataId;

    private String userCode;

    private String ppnNo;

    private String practiseName;

    private String postalAddressLine1;

    private String postalAddressLine2;

    private String postalAddressLine3;

    private String postalCode;

    private String securityAnswer1;

    private String securityAnswer2;

    private String securityAnswer3;

    private LocalDateTime createdDate;

    private Long subscribeNews;

    private Long subscribeNotifications;

    private Long subscribeEvents;

    private Long userID ;

    private String alternativeEmail;

    private Long organizationTypeItemId;

    private Long communicationTypeItemId;

    private Long securityQuestionItemId1;

    private Long securityQuestionItemId2;

    private Long securityQuestionItemId3;

    private Long sectorItemId;


}
