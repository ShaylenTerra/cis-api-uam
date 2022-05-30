package com.dw.ngms.cis.persistence.domains.cart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 14/05/21, Fri
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartItemIncludeCoordinate {

    @JsonProperty("type")
    private CartItemDataType cartItemDataType;

}
