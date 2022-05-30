package com.dw.ngms.cis.web.vm;

import com.dw.ngms.cis.enums.FeeSimulatorType;
import lombok.Data;

@Data
public class FeeSimulatorVm {

    private FeeSimulatorType type;

    private Long categoryId;

    private Long categoryTypeId;

    private Long formatId;

    private Long paperSize;

    private Long itemCount;

    private Long deliveryMethodId;

    private Long deliveryMediumId;
}
