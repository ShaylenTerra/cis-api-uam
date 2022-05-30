package com.dw.ngms.cis.vms;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : prateekgoel
 * @since : 12/01/21, Tue
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class ParcelSearchVm extends SearchVm {

    private String township;

    private String farmName;

    private String parcelNumber;

    private String portion;

    private String municipalityCode;

    private String lpi;


}
