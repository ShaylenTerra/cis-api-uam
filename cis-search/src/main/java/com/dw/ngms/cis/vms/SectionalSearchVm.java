package com.dw.ngms.cis.vms;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : prateekgoel
 * @since : 10/01/21, Sun
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class SectionalSearchVm extends SearchVm {

    private String municipalityCode;

    private String schemeNumber;

    private String schemeName;

    private String filingNumber;

    private String sgNumber;

    private Long sectionalCode;

    private String township;

    private String parcel;

    private String portion;

    private String farmName;


}
