package com.dw.ngms.cis.service.mapper.province;

import com.dw.ngms.cis.persistence.domains.Province;
import com.dw.ngms.cis.service.dto.province.ProvinceDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = ProvinceAddressMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProvinceMapper {

    @Mappings({
            @Mapping(target = "provinceId", source = "provinceId"),
            @Mapping(target = "provinceShortName", source = "provinceShortName"),
            @Mapping(target = "provinceName", source = "provinceName"),
            @Mapping(target = "provinceAddressDto", source = "provinceAddress"),
            @Mapping(target = "orgCode", source = "orgCode"),
            @Mapping(target = "orgName", source = "orgName"),
            @Mapping(target = "description", source = "description"),
    })
    ProvinceDto provinceToProvinceDto(Province province);

    @InheritInverseConfiguration
    Province provinceDtoToProvince(ProvinceDto provinceDto);

}
