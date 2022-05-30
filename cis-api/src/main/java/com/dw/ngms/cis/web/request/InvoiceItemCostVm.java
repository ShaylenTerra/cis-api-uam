package com.dw.ngms.cis.web.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author : prateekgoel
 * @since : 26/01/21, Tue
 **/
@Data
public class InvoiceItemCostVm {

    @NotNull
    private Long searchDataTypeId;

    private Long formatListItemId;

    private Long paperSizeListItemId;

    private Long subTypeListItemId;

    private Long dataTypeListItemId;

}
