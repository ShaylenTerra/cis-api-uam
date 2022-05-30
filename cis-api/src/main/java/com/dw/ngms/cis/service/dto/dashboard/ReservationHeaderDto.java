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
public class ReservationHeaderDto {

    private Long totalRequestCount;

    private Long numberOfParcelRequested;

    private Long numberOfParcelReserved;

    private Long requestProcessed;

}
