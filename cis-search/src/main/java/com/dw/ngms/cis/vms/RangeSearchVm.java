package com.dw.ngms.cis.vms;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : prateekgoel
 * @since : 11/01/21, Mon
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class RangeSearchVm extends SearchVm {

    private String township;

    private String farmName;

    private String parcel;

    private String portionFrom;

    private String portionTo;

    private String sgNo;

    private String parcelFrom;

    private String parcelTo;

    private String municipalityCode;


}
