package com.dw.ngms.cis.service.dto.examination.dockets;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public class MasterDocketDto {

    private Long masterId;

    private String batchNo;

    private String sgNo;

    private String description;

    private String srNo;

    private LocalDateTime dateReceived;

    private Long NoOfDgms;

    private Long planSizeAcceptable;

    private String landSurveyor;

    private String companyName;

    private String submittedBy;

    private String returnTo;

    private String refNumber;

    private String fileReference;

    private String receivedBy;

    private Long examinationFees;

    private String checkedBy;

    private String recieptNo;

    private Long docketTypeItemId;

    private Long docketSubTypeId;


}
