package com.dw.ngms.cis.service.dto.workflow;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : prateekgoel
 * @since : 26/03/21, Fri
 **/
@Data
public class LpiNoteDto {

    private String lpi;

    private String notes;

    private Long noteType;

    private Long userId;

    private LocalDateTime dated;

}
