package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.system.SystemConfiguration;
import com.dw.ngms.cis.service.dto.SystemConfigurationDto;
import org.mapstruct.Mapper;

/**
 * @author : prateekgoel
 * @since : 18/05/21, Tue
 **/
@Mapper(componentModel = "spring")
public interface SystemConfigurationMapper {

    SystemConfiguration systemConfigurationDtoToSystemConfiguration(SystemConfigurationDto systemConfigurationDto);

    SystemConfigurationDto systemConfigurationToSystemConfigurationDto(SystemConfiguration systemConfiguration);

}
