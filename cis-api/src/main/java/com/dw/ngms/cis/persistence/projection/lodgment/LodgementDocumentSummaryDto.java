package com.dw.ngms.cis.persistence.projection.lodgment;

import lombok.Data;

/**
 * @author prateek on 20-04-2022
 */
@Data
public class LodgementDocumentSummaryDto {

    private Long documentItemId;

    private Long purposeItemId;

    private Integer count;

    private String documentType;

    private String purposeType;

    private Double cost;

    private Double totalCost;

}
