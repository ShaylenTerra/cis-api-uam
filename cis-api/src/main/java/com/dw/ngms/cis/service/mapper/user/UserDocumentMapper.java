package com.dw.ngms.cis.service.mapper.user;

import com.dw.ngms.cis.enums.UserDocumentType;
import com.dw.ngms.cis.persistence.domains.document.UserDocument;
import com.dw.ngms.cis.service.dto.user.UserDocumentDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author : prateekgoel
 * @since : 07/05/21, Fri
 **/
@Mapper(componentModel = "spring")
public interface UserDocumentMapper {

    @InheritInverseConfiguration
    UserDocument userDocumentDtoToUserDocument(UserDocumentDto userDocumentDto);

    @Mappings({
            @Mapping(target = "documentTypeId", source = "documentTypeId"),
            @Mapping(target = "id", source = "documentId"),
            @Mapping(target = "contextId", source = "contextId"),
            @Mapping(target = "contextTypeId", source = "contextTypeId"),
            @Mapping(target = "context", source = "context"),
            @Mapping(target = "createdOn", source = "createdOn"),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "isActive", source = "isActive"),
            @Mapping(target = "contentType", source = "contentType"),
            @Mapping(target = "fileName", source = "fileName"),
    })
    UserDocumentDto userDocumentToUserDocumentDto(UserDocument userDocument);


    default Long map(UserDocumentType userDocumentType) {
        if (null == userDocumentType)
            return null;

        return userDocumentType.getDocumentType();
    }

    default UserDocumentType map(Long value) {
        return UserDocumentType.of(value);
    }


}
