package com.dw.ngms.cis.service.dto.cart;

import com.dw.ngms.cis.persistence.domains.cart.SearchDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 10/02/21, Wed
 **/
@Data
public class CartInvoiceItemDto {

    private SearchDetails searchDetails;

    @JsonProperty("cartInvoiceItems")
    private Collection<CartItemsInvoiceDto> cartItems;

    private Long templateListItemId;

}
