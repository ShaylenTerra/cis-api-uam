package com.dw.ngms.cis.persistence.domains.user;

import com.dw.ngms.cis.persistence.domains.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER_META_DATA")
public class UserMetaData implements Serializable {

    @Id
    @Column(name = "USERMETADATAID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_meta_data_id_sequence")
    @SequenceGenerator(name = "user_meta_data_id_sequence", sequenceName = "USER_META_DATA_SEQ", allocationSize = 1)
    private Long userMetaDataId;

    @Column(name = "USERCODE")
    private String userCode;

    @Column(name="PPNNO")
    private String ppnNo;

    @Column(name="PRACTISENAME")
    private String practiseName;

    @Column(name="POSTALADDRESSLINE1")
    private String postalAddressLine1;

    @Column(name="POSTALADDRESSLINE2")
    private String postalAddressLine2;

    @Column(name="POSTALADDRESSLINE3")
    private String postalAddressLine3;

    @Column(name="POSTALCODE")
    private String postalCode;

    @Column(name="SECURITYANSWER1")
    private String securityAnswer1;

    @Column(name="SECURITYANSWER2")
    private String securityAnswer2;

    @Column(name="SECURITYANSWER3")
    private String securityAnswer3;

    @Column(name = "CREATEDDATE")
    private LocalDateTime createdDate;

    @Column(name = "SUBSCRIBENEWS")
    private Long subscribeNews;

    @Column(name = "SUBSCRIBENOTIFICATIONS")
    private Long subscribeNotifications;

    @Column(name = "SUBSCRIBEEVENTS")
    private Long subscribeEvents;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "USERID", nullable = false)
    @JsonIgnore
    private User user;

    @Column(name = "ALTERNATIVEEMAIL")
    private String alternativeEmail;

    @Column(name = "ORGANIZATIONTYPEITEMID")
    private Long organizationTypeItemId;

    @Column(name = "COMMUNICATIONMODETYPEITEMID")
    private Long communicationTypeItemId;

    @Column(name="SECURITYQUESTIONTYPEITEMID1")
    private Long securityQuestionItemId1;

    @Column(name="SECURITYQUESTIONTYPEITEMID2")
    private Long securityQuestionItemId2;

    @Column(name="SECURITYQUESTIONTYPEITEMID3")
    private Long securityQuestionItemId3;

    @Column(name="SECTORITEMID")
    private Long sectorItemId;

}
