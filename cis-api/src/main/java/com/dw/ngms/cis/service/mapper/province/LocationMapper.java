package com.dw.ngms.cis.service.mapper.province;

import com.dw.ngms.cis.persistence.domains.prepackage.Location;
import com.dw.ngms.cis.service.dto.province.LocationDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author : pragayanshu
 * @since : 2021/12/24, Fri
 **/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocationMapper {
    LocationDto LocationToLocationDto(Location location);
    Location LocationDtoToLocation(LocationDto locationDto);
}
