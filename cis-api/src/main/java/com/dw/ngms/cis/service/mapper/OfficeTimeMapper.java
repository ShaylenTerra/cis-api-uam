package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.enums.OfficeTimingType;
import com.dw.ngms.cis.persistence.domains.OfficeTimings;
import com.dw.ngms.cis.service.dto.OfficeTimeDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author : prateekgoel
 * @since : 05/12/20, Sat
 **/
@Mapper(componentModel = "spring")
public interface OfficeTimeMapper {

    @Mappings({
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "fromDate", source = "fromDate"),
            @Mapping(target = "toDate", source = "toDate"),
            @Mapping(target = "fromTime", source = "fromTime"),
            @Mapping(target = "toTime", source = "toTime"),
            @Mapping(target = "isActive", source = "isActive"),
            @Mapping(target = "officeTimeId", source = "officeTimeId"),
            @Mapping(target = "officeTimingType", source = "officeTimingType"),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "provinceId", source = "provinceId")

    })
    OfficeTimeDto officeTimeToOfficeTimeDto(OfficeTimings officeTimings);

    @InheritInverseConfiguration
    OfficeTimings officeTimingDtoToOfficeTiming(OfficeTimeDto officeTimeDto);


    default String toString(OfficeTimingType officeTimingType) {
        return officeTimingType.name();
    }

    default OfficeTimingType toEnum(String code) {
        return OfficeTimingType.of(code);
    }


}
