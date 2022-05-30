package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.prepackage.PrePackageSubscription;
import com.dw.ngms.cis.service.dto.prepackage.PrepackageSubscriptionDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author : prateekgoel
 * @since : 08/01/21, Fri
 **/
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PrepackageSubscriptionMapper {

    PrepackageSubscriptionDto prepackageSubscriptionToPrepackageSubscriptionDto(PrePackageSubscription prePackageSubscription);

    PrePackageSubscription prepackageSubscriptionDtoToPrepackageSubscription(PrepackageSubscriptionDto prepackageSubscriptionDto);

}
