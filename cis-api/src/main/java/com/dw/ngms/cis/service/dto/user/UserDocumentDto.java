package com.dw.ngms.cis.service.dto.user;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : prateekgoel
 * @since : 07/05/21, Fri
 **/
@Data
public class UserDocumentDto {

    private Long id;

    private Long contextId;

    private Long contextTypeId;

    private String context;

    private LocalDateTime createdOn;

    private Long userId;

    private Long isActive;

    private Long documentTypeId;

    private String contentType;

    private String fileName;
}
