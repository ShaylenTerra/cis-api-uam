package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.cart.CartItems;
import com.dw.ngms.cis.service.dto.cart.CartItemDispatchDto;
import com.dw.ngms.cis.service.dto.cart.CartItemsInvoiceDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author : prateekgoel
 * @since : 10/02/21, Wed
 **/
@Mapper(componentModel = "spring")
public interface CartItemsMapper {

    @Mappings({
            @Mapping(target = "systemEstimate", source = "systemEstimate"),
            @Mapping(target = "item", source = "item"),
            @Mapping(target = "cartItemId", source = "cartItemId"),
            @Mapping(target = "workflowId", source = "workflowId"),
            @Mapping(target = "cartDataId", source = "cartDataId"),
            @Mapping(target = "finalCost", source = "finalCost"),
            @Mapping(target = "details", source = "details"),
            @Mapping(target = "comments", source = "comments"),
            @Mapping(target = "lpicode", source = "lpicode"),
            @Mapping(target = "sno", source = "sno"),
            @Mapping(target = "timeRequired", source = "timeRequired"),
            @Mapping(target = "type", source = "type"),
            @Mapping(target = "notes", source = "notes"),
    })
    CartItemsInvoiceDto cartItemsToCartItemsInvoiceDto(CartItems cartItems);

    @InheritInverseConfiguration
    CartItems cartItemsDtoToCartItems(CartItemsInvoiceDto cartItemsInvoiceDto);

    @Mappings({
            @Mapping(target = "type", source = "type"),
            @Mapping(target = "cartItemId", source = "cartItemId"),
            @Mapping(target = "item", source = "item"),
            @Mapping(target = "sno", source = "sno"),
            @Mapping(target = "cartDataId", source = "cartDataId"),
            @Mapping(target = "timeRequired", source = "timeRequired"),
            @Mapping(target = "lpicode", source = "lpicode"),
            @Mapping(target = "details", source = "details"),
            @Mapping(target = "comments", source = "dispatchComment"),
            @Mapping(target = "dispatchStatus", source = "dispatchStatus"),
            @Mapping(target = "workflowId", source = "workflowId"),
            @Mapping(target = "notes", source = "notes")

    })
    CartItemDispatchDto cartItemsToCartItemDispatchDto(CartItems cartItems);

}
