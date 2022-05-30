package com.dw.ngms.cis.enums.converter;

import com.dw.ngms.cis.enums.UserType;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

/**
 * @author : prateekgoel
 * @since : 25/11/20, Wed
 **/
@Converter(autoApply = true)
@Slf4j
public class UserTypeConverter implements AttributeConverter<UserType, Long> {

    @Override
    public Long convertToDatabaseColumn(UserType userType) {
        log.debug(" converting userType to String ");
        if (null == userType) {
            return null;
        }
        return userType.getUserType();
    }

    @Override
    public UserType convertToEntityAttribute(Long value) {
        log.debug(" converting string value {} to UserType ", value);
        if (null == value)
            return null;
        return Stream.of(UserType.values())
                .filter(c -> c.getUserType().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

    }
}
