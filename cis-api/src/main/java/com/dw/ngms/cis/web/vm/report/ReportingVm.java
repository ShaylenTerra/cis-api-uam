package com.dw.ngms.cis.web.vm.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 06/02/21, Sat
 **/
@Data
public class ReportingVm {

    @NotNull
    private Long reportId;

    @NotNull
    private Collection<Long> provinces;

    @JsonIgnore
    private Collection<String> provinceName;

    private Collection<String> userType;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fromDate;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate toDate;

    private String reportFormat;
}
