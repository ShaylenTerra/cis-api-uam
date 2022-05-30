package com.dw.ngms.cis.service.mapper.province;

import com.dw.ngms.cis.persistence.domains.province.ProvinceAddress;
import com.dw.ngms.cis.service.dto.province.ProvinceAddressDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProvinceAddressMapper {

    @Mappings({
            @Mapping(target = "provinceAddress", source = "provinceAddress"),
            @Mapping(target = "provinceEmail", source = "provinceEmail"),
            @Mapping(target = "provinceContactNumber", source = "provinceContactNumber"),
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "provinceId", source = "provinceId")

    })
    ProvinceAddressDto provinceAddressToProvinceAddressDto(ProvinceAddress provinceAddress);

    @InheritInverseConfiguration
    ProvinceAddress provinceAddressDtoToProvinceAddress(ProvinceAddressDto provinceAddressDto);
}
