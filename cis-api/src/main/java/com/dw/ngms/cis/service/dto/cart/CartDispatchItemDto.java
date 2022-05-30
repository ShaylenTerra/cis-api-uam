package com.dw.ngms.cis.service.dto.cart;

import com.dw.ngms.cis.persistence.domains.cart.CartDispatchAdditionalInfo;
import com.dw.ngms.cis.persistence.domains.cart.SearchDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 11/02/21, Thu
 **/
@Data
public class CartDispatchItemDto {

    private SearchDetails searchDetails;

    @JsonProperty("cartDispatchItems")
    private Collection<CartItemDispatchDto> cartItems;

    private CartDispatchAdditionalInfo cartDispatchAdditionalInfo;

    private Long templateListItemId;

}
