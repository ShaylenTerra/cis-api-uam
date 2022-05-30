package com.dw.ngms.cis.persistence.domains.cart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : prateekgoel
 * @since : 09/02/21, Tue
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartJsonData {

    @JsonProperty("searchDetails")
    public SearchDetails searchDetails;

    @JsonProperty("cartData")
    private CartItemData cartItemData;

    @JsonProperty("templateListItemId")
    private Long templateListItemId;
}
