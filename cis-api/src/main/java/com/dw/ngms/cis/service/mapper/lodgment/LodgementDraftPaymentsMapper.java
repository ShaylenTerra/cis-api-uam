package com.dw.ngms.cis.service.mapper.lodgment;

import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraftPayment;
import com.dw.ngms.cis.service.dto.lodgment.LodgementDraftPaymentDto;
import org.mapstruct.*;

import java.time.LocalDateTime;

/**
 * @author prateek on 06-04-2022
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {LocalDateTime.class}
)
@DecoratedWith(LodgementDraftPaymentsDecorator.class)
public interface LodgementDraftPaymentsMapper {

    @InheritInverseConfiguration
    LodgementDraftPaymentDto lodgementDraftPaymentToLodgementDraftPaymentDto(LodgementDraftPayment lodgementDraftPayment);

    @Mappings({
            @Mapping(target = "payId", source = "payId"),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "notes", source = "notes"),
            @Mapping(target = "amount", source = "amount", numberFormat = "#.00"),
            @Mapping(target = "payDate", source = "payDate"),
            @Mapping(target = "payMethodItemId", source = "payMethodItemId"),
            @Mapping(target = "receiptNo", source = "receiptNo"),
            @Mapping(target = "refNumber", source = "refNumber"),
            @Mapping(target = "statusItemId", source = "statusItemId", defaultValue = "1046L"),
            @Mapping(target = "dated", source = "dated",  defaultExpression = "java(LocalDateTime.now())"),
            @Mapping(target = "docName", source = "docName")
    })
    LodgementDraftPayment lodgementDraftPaymentDtoToLodgementDraftPayment(LodgementDraftPaymentDto lodgementDraftPaymentDto);

}
