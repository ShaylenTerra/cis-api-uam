package com.dw.ngms.cis.service.dto.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserTransactionDto {
    private Long userTransactionId;
    private String context;
    private Long statusItemId;
    private Long userId;
    private String note;
    private LocalDateTime createdDate;
}
