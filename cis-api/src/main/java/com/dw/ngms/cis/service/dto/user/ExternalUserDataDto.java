package com.dw.ngms.cis.service.dto.user;

import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 15/12/20, Tue
 **/
@Data
public class ExternalUserDataDto {

    private String securityQuestion1;

    private String securityAnswer1;

    private String securityQuestion2;

    private String securityAnswer2;

    private String securityQuestion3;

    private String securityAnswer3;

    private Long externalUserDataId;

    private String userCode;

    private String organizationTypeCode;

    private String organizationTypeName;

    private String ppNo;

    private String practiseName;

    private String postalAddressLine1;

    private String postalAddressLine2;

    private String postalAddressLine3;

    private String postalCode;

    private String communicationModeTypeCode;

    private String communicationModeTypeName;

    private String securityQuestionTypeCode1;

    private String subscribeNews;

    private String subscribeNotifications;

    private String subscribeEvents;

    private String sectorCode;

    private String sectorName;

    private String uploadMultipleFiles;

    private String alternativeEmail;

}
