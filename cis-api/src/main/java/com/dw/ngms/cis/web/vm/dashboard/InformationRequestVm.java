package com.dw.ngms.cis.web.vm.dashboard;

import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 25/03/21, Thu
 **/
@Data
public class InformationRequestVm {

    private Collection<Integer> provinceIds;

    private LocalDate fromDate;

    private LocalDate toDate;

}
