package com.dw.ngms.cis.service.dto;

import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 10/03/21, Wed
 **/
@Data
public class TextToDocDto {

    private Long workflowId;

    private String text;

    private String comment;

    private Long userId;

    private Long documentTypeId;

    private String extension;

}
