package com.dw.ngms.cis.enums.converter;

import com.dw.ngms.cis.enums.UserDocumentType;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

/**
 * @author : prateekgoel
 * @since : 08/05/21, Sat
 **/
@Converter(autoApply = true)
@Slf4j
public class UserDocumentTypeConverter implements AttributeConverter<UserDocumentType, Long> {


    @Override
    public Long convertToDatabaseColumn(UserDocumentType attribute) {
        log.debug(" converting userTypeDocument [{}] to Long ", attribute);
        if (null == attribute) {
            return null;
        }
        return attribute.getDocumentType();
    }

    @Override
    public UserDocumentType convertToEntityAttribute(Long dbData) {
        log.debug(" converting Long value {} to UserType ", dbData);
        if (null == dbData)
            return null;
        return Stream.of(UserDocumentType.values())
                .filter(c -> c.getDocumentType().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
