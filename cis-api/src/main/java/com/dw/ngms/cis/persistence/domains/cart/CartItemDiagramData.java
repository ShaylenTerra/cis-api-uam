package com.dw.ngms.cis.persistence.domains.cart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 09/02/21, Tue
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartItemDiagramData {

    @JsonProperty("type")
    private CartItemDataType cartItemDataType;

    @JsonProperty("format")
    private CartItemDataFormat cartItemDataFormat;


}
