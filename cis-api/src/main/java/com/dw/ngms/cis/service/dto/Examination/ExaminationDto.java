package com.dw.ngms.cis.service.dto.examination;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ExaminationDto {

    private Long examId;

    private String name;

    private LocalDateTime createdDate;

    private Long lodgementId;

    private Long allocatedUserId;

    private Long workflowId;

    private Long statusId;
}
