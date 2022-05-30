package com.dw.ngms.cis.persistence.domains.cart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartItemNgiData {

    private String notes;

    @JsonProperty("format")
    private CartItemDataFormat cartItemDataFormat;
}
