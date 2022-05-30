package com.dw.ngms.cis.enums.converters;

import com.dw.ngms.cis.enums.UserTypes;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

/**
 * @author : prateekgoel
 * @since : 14/12/20, Mon
 **/
@Converter(autoApply = true)
@Slf4j
public class UserTypesConverter implements AttributeConverter<UserTypes, String>,
        org.springframework.core.convert.converter.Converter<String, UserTypes> {

    @Override
    public String convertToDatabaseColumn(UserTypes attribute) {
        log.debug(" converting status to String ");
        if (null == attribute) {
            return null;
        }

        return attribute.getUserTypes();
    }

    @Override
    public UserTypes convertToEntityAttribute(String dbData) {
        log.debug(" converting string value {} to UserTypes ", dbData);
        if (null == dbData)
            return null;

        return Stream.of(UserTypes.values()).filter(c -> c.getUserTypes().equals(dbData)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }


    @Override
    public UserTypes convert(String source) {
        return Stream.of(UserTypes.values()).filter(c -> c.getUserTypes().equals(source)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
