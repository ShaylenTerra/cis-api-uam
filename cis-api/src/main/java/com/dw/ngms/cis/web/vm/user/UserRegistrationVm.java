package com.dw.ngms.cis.web.vm.user;

import com.dw.ngms.cis.enums.UserType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @author : prateekgoel
 * @since : 26/04/21, Mon
 **/
@Data
public class UserRegistrationVm {

    private String userName;

    @NotNull
    private String email;

    private String alternateEmail;

    private Long primaryProvinceId;


    private Long roleId;

    private Long titleItemId;

    private String firstName;

    private String lastName;

    private String mobile;

    private Long organizationTypeId;

    private String organization;

    private Long sectorId;

    private String addrLine1;

    private String addrLine2;

    private String addrLine3;

    private String postalCode;

    private Long securityQItemId1;

    private String securityA1;

    private Long securityQItemId2;

    private String securityA2;

    private Long securityQItemId3;

    private String securityA3;

    private Long subscribeNews;

    private Long subscribeNotifications;

    private Long subscribeEvents;

    private String ppNo;

    private String practiceName;

    private MultipartFile uploadDocument;

    private Long sectionId;

    private Long communicationTypeId;

    private String countryCode;

    @NotNull
    private UserType userTypeItemId;

    private String internalUsername;

}
