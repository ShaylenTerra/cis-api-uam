package com.dw.ngms.cis.service.dto.examination;

/**
 * @author Shaylen Budhu on 30-05-2022
 */

import lombok.Data;
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
