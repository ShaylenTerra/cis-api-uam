package com.dw.ngms.cis.persistence.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "SEARCH_DETAILS_SHARE")
public class SearchDetailsShare {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "search_details_share_seq")
    @SequenceGenerator(sequenceName = "SEARCH_DETAILS_SHARE_SEQ", allocationSize = 1, name = "search_details_share_seq")
    @Column(name = "SHARE_ID")
    private Long shareId;

    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

    @Column(name = "DATED")
    private LocalDateTime dated;

    @Column(name = "RECORD_ID")
    private Long recordId;

    @Column(name = "USERID")
    private Long userId;

}
