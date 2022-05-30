package com.dw.ngms.cis.persistence.domains.province;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PROVINCE_ADDRESS")
public class ProvinceAddress {

    @Id
    @GeneratedValue(generator = "province_address_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "province_address_seq", sequenceName = "PROVINCE_ADDRESS_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PROVINCE_EMAIL")
    private String provinceEmail;

    @Column(name = "PROVINCE_CONTACT_NUMBER")
    private String provinceContactNumber;

    @Column(name = "PROVINCE_ADDRESS")
    private String provinceAddress;

    @Column(name = "PROVINCE_ID")
    private Long provinceId;


}
