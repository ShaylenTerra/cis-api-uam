package com.dw.ngms.cis.service.dto.examination.dockets;

import lombok.Data;

@Data
public class DiagramDocketDto{

    private Long diagramId;

    private String examinerName;

    private String scrutinizerName;

    private String paName;

    private String examinerChecklist;

    private String scrutinizerChecklist;

    private String paCheckList;

    private String designationNumbering;

    private String computing;

    private String registryDispatch;

    private String postApproval;

    private String status;

    private Long examinationId;

}
