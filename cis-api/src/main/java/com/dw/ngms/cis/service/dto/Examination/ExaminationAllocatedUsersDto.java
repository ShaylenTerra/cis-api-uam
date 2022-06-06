package com.dw.ngms.cis.service.dto.examination;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
public class ExaminationAllocatedUsersDto {
    
    private Long examAllocatedId;
    
    private Long scrutinizerId;

    private Long examinerId;
    
    private Long updatedByUserId;

    private LocalDateTime modifiedDate;

    private LocalDateTime createDate;

    private String comments;

    private Long examId;
}
