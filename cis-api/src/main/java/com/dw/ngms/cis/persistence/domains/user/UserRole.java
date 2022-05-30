package com.dw.ngms.cis.persistence.domains.user;

import com.dw.ngms.cis.persistence.domains.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER_ADDITIONAL_ROLE")
public class UserRole implements Serializable {

    @Id
    @Column(name = "UARID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_additional_role_id_sequence")
    @SequenceGenerator(name = "user_additional_role_id_sequence", sequenceName = "USER_ADDITIONAL_ROLE_SEQ", allocationSize = 1)
    private Long userRoleId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USERID", nullable = false)
    @JsonIgnore
    private User user;

    @Column(name = "PROVINCEID")
    private Long provinceId;

    @Column(name = "SECTION_ITEMID")
    private Long sectionItemId;

    @Column(name = "ISPRIMARY")
    private Long isPrimary;

    @Column(name = "ROLEID")
    private Long roleId;

    @Column(name = "ASSIGNEDDATE")
    private LocalDateTime assignedDate;

}
