package com.dw.ngms.cis.service.dto.user;

import lombok.Data;

@Data
public class UserMetaDataDto {

    private Long userMetaDataId;

    private String ppnNo;

    private String practiseName;

    private String postalAddressLine1;

    private String postalAddressLine2;

    private String postalAddressLine3;

    private String postalCode;

    private String alternativeEmail;

    private Long organizationTypeItemId;

    private String organizationType;

    private Long sectorItemId;

    private String sector;

    private Long subscribeNews;

    private Long subscribeNotifications;

    private Long subscribeEvents;

}
