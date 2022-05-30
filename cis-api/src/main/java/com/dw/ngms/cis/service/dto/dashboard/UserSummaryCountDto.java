package com.dw.ngms.cis.service.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : prateekgoel
 * @since : 15/03/21, Mon
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSummaryCountDto {

    private Long totalTaskCount;

    private Long closedTaskCount;

    private Float turnaroundDuration;

    private Long totalNotifications;

}
