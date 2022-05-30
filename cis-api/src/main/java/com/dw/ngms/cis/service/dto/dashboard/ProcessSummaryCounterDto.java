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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessSummaryCounterDto {

    private Long totalTask;

    private Long closedTask;

    private Float turnaroundDuration;

    private Long totalNotification;

}
