package com.dw.ngms.cis.service.dto.province;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceDto {

    private Long provinceId;

    private String provinceName;

    private String provinceShortName;

    private String orgCode;

    private String orgName;

    private String description;

    @JsonProperty("provinceAddress")
    private ProvinceAddressDto provinceAddressDto;
}
