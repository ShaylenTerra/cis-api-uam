package com.dw.ngms.cis.persistence.domains.report;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : prateekgoel
 * @since : 12/02/21, Fri
 **/
@Data
@AllArgsConstructor
public class Productivity {

    private Long provinceId;

    private Long actionId;

    private LocalDateTime postedOn;

    private LocalDateTime actedOn;

    private Long userId;

}
