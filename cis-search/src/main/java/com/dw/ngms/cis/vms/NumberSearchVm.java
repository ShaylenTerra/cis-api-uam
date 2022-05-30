package com.dw.ngms.cis.vms;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NumberSearchVm extends SearchVm {

    private String sgNo;

    private String compilationNo;

    private String lpi;

    private String filingNo;

    private String leaseNo;

    private String surveyRecordNo;

    private String deedNo;

    private String municipalityCode;

}
