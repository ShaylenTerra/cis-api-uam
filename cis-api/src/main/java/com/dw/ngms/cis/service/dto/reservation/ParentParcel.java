package com.dw.ngms.cis.service.dto.reservation;


import lombok.Data;

@Data
public class ParentParcel {

    private String sgNo;

    private String lpi;

    private String documentType;

    private String documentSubType;

    private String region;

    private String regionTownship;

    private String parcel;

    private String portion;

    private String designation;
}
