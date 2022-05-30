package com.dw.ngms.cis.service.dto.province;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProvinceAddressDto {

    private Long id;

    @NotNull
    private Long provinceId;

    @NotNull
    private String provinceEmail;

    @NotNull
    private String provinceContactNumber;

    @NotNull
    private String provinceAddress;
}
