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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InformationRequestCounterDto {

    private Long totalRequestCount;

    private Long dispatchedRequestCount;

    private Long invoiceAmount;

    private Long paymentAmount;

}
