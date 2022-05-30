package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.cart.CartItems;
import com.dw.ngms.cis.service.dto.workflow.InvoiceItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
 * @author : prateekgoel
 * @since : 26/01/21, Tue
 **/
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvoiceItemCartItemMapper {


    @Mappings({
            @Mapping(target = "comments", source = "comment"),
            @Mapping(target = "item", source = "item"),
            @Mapping(target = "details", source = "details"),
            @Mapping(target = "finalCost", source = "finalCost"),
            @Mapping(target = "lpicode", source = "lpiCode"),
            @Mapping(target = "timeRequired", source = "timeRequiredInHrs"),
            @Mapping(target = "sno", source = "srNo"),
            @Mapping(target = "systemEstimate", source = "systemEstimate"),

    })
    CartItems invoiceItemDtoToCartItems(InvoiceItemDto invoiceItemsDto);

}
