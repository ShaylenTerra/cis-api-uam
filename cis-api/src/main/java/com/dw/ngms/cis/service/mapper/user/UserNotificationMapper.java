package com.dw.ngms.cis.service.mapper.user;

import com.dw.ngms.cis.persistence.domains.user.UserNotification;
import com.dw.ngms.cis.service.dto.user.UserNotificationDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserNotificationMapper {
    UserNotificationDto userNotificationTouserNotificationDto(UserNotification userNotification);
}
