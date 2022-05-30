package com.dw.ngms.cis.service.mapper.user;

import com.dw.ngms.cis.persistence.domains.user.UserMetaData;
import com.dw.ngms.cis.service.dto.user.EditableUserMetaDataDto;
import com.dw.ngms.cis.service.dto.user.UserMetaDataDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMetaDataMapper {

    UserMetaDataDto userMetaDataToUserMetaDataDto(UserMetaData userMetaData);

    UserMetaData userMetaDataDtoToUserMetaData(UserMetaDataDto userMetaDataDto);

    EditableUserMetaDataDto userMetaDataToEditableUserMetaDataDto(UserMetaData userMetaData);

    UserMetaData editableUserMetaDataDtoToUserMetaData(EditableUserMetaDataDto editableUserMetaDataDto);
}
