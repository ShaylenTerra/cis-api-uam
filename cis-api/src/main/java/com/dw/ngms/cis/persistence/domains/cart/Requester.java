package com.dw.ngms.cis.persistence.domains.cart;

import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 30/05/21, Sun
 **/
@Data
public class Requester {

    private String firstName;

    private String surName;

    private String contactNo;

    private String email;

    private String fax;

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String postalCode;

    private String description;

    private String notes;
}
