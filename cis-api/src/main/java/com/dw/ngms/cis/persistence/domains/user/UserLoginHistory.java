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
@Table(name = "USER_LOGIN_HISTORY")
public class UserLoginHistory {

    @Id
    @GeneratedValue(generator = "user_login_history_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_login_history_seq", sequenceName = "USER_LOGIN_HISTORY_SEQ", allocationSize = 1)
    @Column(name = "HISTORY_ID")
    private Long historyId;

    @Column(name = "TOKENID")
    private Long tokenId;

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "TOKEN")
    private String token;

    @Column(name = "EXPIRE")
    private LocalDateTime expire;

    @Column(name = "IP_ADDRESS")
    private String ipAddress;

    @Column(name = "CREATEDDATE")
    private LocalDateTime createdDate;


}
