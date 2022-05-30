package com.dw.ngms.cis.persistence.domains.cart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 09/02/21, Tue
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartItemDataCertType {

    private Long itemId;

    private String caption;

    private String description;
}
