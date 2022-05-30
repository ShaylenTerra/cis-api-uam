package com.dw.ngms.cis.service.dto.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserLoginHistoryDto {
    private Long tokenId;
    private Long userId;
    private String token;
    private LocalDateTime expire;
    private String ipAddress;
    private LocalDateTime createdDate;

}
