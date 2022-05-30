package com.dw.ngms.cis.service.dto.workflow;

import com.dw.ngms.cis.service.dto.cart.CartItemsInvoiceDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 26/01/21, Tue
 **/
@Data
public class InvoiceItemsDto {

    @Size(min = 1)
    @JsonProperty("cartInvoiceItems")
    private Collection<CartItemsInvoiceDto> cartItemsInvoiceDtos;

    @NotNull
    private Long workflowId;

    private Double totalInvoiceCost;

    @NotNull
    private Long userId;

    private String notes;

}
