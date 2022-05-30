package com.dw.ngms.cis.enums.converter;

import com.dw.ngms.cis.enums.OfficeTimingType;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

/**
 * @author : prateekgoel
 * @since : 05/12/20, Sat
 **/
@Slf4j
@Converter(autoApply = true)
public class OfficeTimingTypeConverter implements AttributeConverter<OfficeTimingType, String> {

    @Override
    public String convertToDatabaseColumn(OfficeTimingType officeTimingType) {
        log.debug(" converting officeTimingType to String ");
        if (null == officeTimingType)
            return null;

        return officeTimingType.getOfficeTimingType();
    }

    @Override
    public OfficeTimingType convertToEntityAttribute(String dbData) {
        log.debug(" converting string value {} to ApprovalStatus ", dbData);
        if (null == dbData)
            return null;

        return Stream.of(OfficeTimingType.values()).filter(c -> c.getOfficeTimingType().equals(dbData)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
