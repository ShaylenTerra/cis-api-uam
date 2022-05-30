package com.dw.ngms.cis.persistence.domains.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author : prateekgoel
 * @since : 28/11/20, Sat
 **/
@Data
@NoArgsConstructor
@Entity
@Table(name = "USER_SETTINGS")
public class UserHomeSettings {

    @Id
    @Column(name = "SETTINGID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_settings_seq")
    @SequenceGenerator(name = "user_settings_seq", sequenceName = "USER_SETTINGS_SEQ", allocationSize = 1)
    private Long settingId;

    @Column(name = "USERID")
    private Long userId;


    @Column(name = "HOMEPAGE")
    private String homepage;

    @Column(name = "SECTIONJSON")
    private String sections;

}
