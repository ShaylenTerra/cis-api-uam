package com.dw.ngms.cis.service.dto.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserProfessionalDto {
    private String ppnNo;
    private String surname;
    private String initials;
    private String postalAddress1;
    private String postalAddress2;
    private String postalAddress3;
    private String postalAddress4;
    private String postalCode;
    private String telephoneNumber;
    private String cellPhoneNo;
    private String faxNo;
    private String courierService;
    private String generalNotes;
    private String email;
    private String description;
    private Long userProfessionalId;
    private String firstName;
    private String restrictedIndicator;
    private String sectionalPlanInd;
    private Long provinceId;
    private String provinceName;
    private Long isValid;
    private Long statusItemId;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime modifiedDate = LocalDateTime.now() ;
    private Long surveyorId;
    private Long sgOfficeId;
    private Long professionTypeItemId;
    private Long userid;
    private Long createdByUserid;
    private Long modifiedByUserid;
    private String businessName;
    private String alternateEmail;

}
