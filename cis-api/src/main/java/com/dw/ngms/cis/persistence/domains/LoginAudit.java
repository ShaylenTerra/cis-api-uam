package com.dw.ngms.cis.persistence.domains;

import com.dw.ngms.cis.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 24/11/20, Tue
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "LOGIN_SESSION_HISTORY")
public class LoginAudit {

    @Id
    @Column(name = "SESSION_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "login_session_history_sequence")
    @SequenceGenerator(name = "login_session_history_sequence",
            sequenceName = "LOGIN_SESSION_HISTORY_SEQ",
            allocationSize = 1)
    private Long sessionId;

    @Column(name = "USERNAME", nullable = false,unique = true)
    private String username;

    @Column(name = "USER_TYPE")
    private UserType userType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LOGGED_IN_TIME")
    private Date timestamp;
}
