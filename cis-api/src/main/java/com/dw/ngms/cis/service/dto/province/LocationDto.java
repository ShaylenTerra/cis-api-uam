package com.dw.ngms.cis.service.dto.province;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : pragayanshu
 * @since : 2021/12/24, Fri
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private Long boundaryId;
    private String caption;
    private String reservationSystem;
}
