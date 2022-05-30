package com.dw.ngms.cis.service.dto;

import com.dw.ngms.cis.enums.FeeSimulatorType;
import lombok.Data;

@Data
public class FeeSimulatorDto {

    private FeeSimulatorType type;

    private Double totalCost;

    private String toolTip;

    private String postFix;

}
