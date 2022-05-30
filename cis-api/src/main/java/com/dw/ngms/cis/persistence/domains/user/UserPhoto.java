package com.dw.ngms.cis.persistence.domains.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "USER_PHOTO")
public class UserPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_photo_seq")
    @SequenceGenerator(name = "user_photo_seq", sequenceName = "USER_PHOTO_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Lob
    @Column(name = "PHOTO")
    private byte[] photo;

    @Column(name = "USERID")
    private Long userid;

}
