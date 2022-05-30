package com.dw.ngms.cis.service.mapper.user;

import com.dw.ngms.cis.persistence.domains.user.UserHomeSettings;
import com.dw.ngms.cis.service.dto.user.UserHomeSettingsDto;
import org.mapstruct.Mapper;

/**
 * @author : prateekgoel
 * @since : 28/11/20, Sat
 **/
@Mapper(componentModel = "spring")
public interface UserHomeSettingsMapper {

    UserHomeSettings userHomeSettingsDtoToUserHomeSettings(UserHomeSettingsDto userHomeSettingsDto);

    UserHomeSettingsDto userHomeSettingToUserHomeSettingsDto(UserHomeSettings  userHomeSettings);
}
