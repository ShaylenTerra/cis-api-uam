package com.dw.ngms.cis.vms;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : prateekgoel
 * @since : 19/01/21, Tue
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class TextSearchVm extends SearchVm {

    private String sgNo;

    private String parcel;

    private String localMunicipalityName;

    private String region;

    private String lpi;
}
