package com.dw.ngms.cis.enums.converter;

import com.dw.ngms.cis.enums.UserStatus;
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
public class UserStatusConverter implements AttributeConverter<UserStatus, Long> {

	@Override
	public Long convertToDatabaseColumn(UserStatus status) {
		log.debug(" converting status to String ");
		if (null == status) {
			return null;
		}
		return status.getUserStatus();
	}

	@Override
	public UserStatus convertToEntityAttribute(Long value) {
		log.debug(" converting string value {} to ApprovalStatus ", value);
		if (null == value)
			return null;
		return Stream.of(UserStatus.values()).filter(c -> c.getUserStatus().equals(value)).findFirst()
				.orElseThrow(IllegalArgumentException::new);

	}
}
