package com.dw.ngms.cis.enums.converter;

import com.dw.ngms.cis.enums.LdapUserStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

/**
 * @author : prateekgoel
 * @since : 25/11/20, Wed
 **/
@Converter(autoApply = true)
public class LdapUserStatusConvertor implements AttributeConverter<LdapUserStatus, Integer> {


    @Override
    public Integer convertToDatabaseColumn(LdapUserStatus ldapUserStatus) {
        if (null == ldapUserStatus)
            return null;

        return ldapUserStatus.getLdapUserStatus();
    }

    @Override
    public LdapUserStatus convertToEntityAttribute(Integer dbData) {
        return Stream.of(LdapUserStatus.values())
                .filter(u -> u.getLdapUserStatus().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
