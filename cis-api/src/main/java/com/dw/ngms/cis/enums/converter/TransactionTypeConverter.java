package com.dw.ngms.cis.enums.converter;

import com.dw.ngms.cis.enums.TransactionType;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

/**
 * @author : prateekgoel
 * @since : 29/12/20, Tue
 **/
@Converter(autoApply = true)
@Slf4j
public class TransactionTypeConverter implements AttributeConverter<TransactionType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TransactionType attribute) {
        log.debug(" converting status to String ");
        if (null == attribute) {
            return null;
        }
        return attribute.getTransactionType();
    }

    @Override
    public TransactionType convertToEntityAttribute(Integer dbData) {
        log.debug(" converting string value {} to ApprovalStatus ", dbData);
        if (null == dbData)
            return null;
        return Stream.of(TransactionType.values()).filter(c -> c.getTransactionType().equals(dbData)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
