package com.dw.ngms.cis.persistence.domains.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER_PROFESSIONAL")
public class UserProfessional {

    @Id
    @Column(name = "USER_PROF_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_professional_id_sequence")
    @SequenceGenerator(name = "user_professional_id_sequence", sequenceName = "USER_PROFESSIONAL_SEQ", allocationSize = 1)
    private Long userProfessionalId;

    @Column(name="PPNNO")
    private String ppnNo;

    @Column(name="SURNAME")
    private String surname;

    @Column(name="INITIALS")
    private String initials;

    @Column(name="POSTALADDRESS_1")
    private String postalAddress1;

    @Column(name="POSTALADDRESS_2")
    private String postalAddress2;

    @Column(name="POSTALADDRESS_3")
    private String postalAddress3;

    @Column(name="POSTALADDRESS_4")
    private String postalAddress4;

    @Column(name="POSTALCODE")
    private String postalCode;

    @Column(name="TELEPHONE_NO")
    private String telephoneNumber;

    @Column(name="CELL_PHONE_NO")
    private String cellPhoneNo;

    @Column(name="FAX_NO")
    private String faxNo;

    @Column(name="COURIER_SERVICE")
    private String courierService;

    @Column(name="GENERAL_NOTES")
    private String generalNotes;

    @Column(name="EMAIL")
    private String email;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="FIRSTNAME")
    private String firstName;

    @Column(name="RESTRICTED_IND")
    private String restrictedIndicator;

    @Column(name="SECTIONAL_PLAN_IND")
    private String sectionalPlanInd;

    @Column(name = "PROVINCEID")
    private Long provinceId;

    @Column(name = "ISVALID")
    private Long isValid;

    @Column(name = "STATUS_ITEM_ID")
    private Long statusItemId;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "MODIFIED_DATE")
    private LocalDateTime modifiedDate = LocalDateTime.now();

    @Column(name = "SURVEYOR_ID")
    private Long surveyorId;

    @Column(name = "SG_OFFICE_ID")
    private Long sgOfficeId;

    @Column(name = "PROFESSION_TYPE_ITEMID")
    private Long professionTypeItemId;

    @Column(name="USERID")
    private Long userId;

    @Column(name = "CREATED_BY_USERID")
    private Long createdByUserid;

    @Column(name = "MODIFIED_BY_USERID")
    private Long modifiedByUserId;

    @Column(name = "BUSINESSNAME")
    private String businessName;

    @Column(name = "ALTERNATE_EMAIL")
    private String alternateEmail;

}
