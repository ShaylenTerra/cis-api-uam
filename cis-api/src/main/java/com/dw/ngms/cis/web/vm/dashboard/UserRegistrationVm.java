package com.dw.ngms.cis.web.vm.dashboard;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 25/03/21, Thu
 **/
@Data
public class UserRegistrationVm {

    private Collection<Long> provinceIds;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fromDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate toDate;

}