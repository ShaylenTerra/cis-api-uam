package com.dw.ngms.cis.persistence.domains.workflow;

import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 20/06/21, Sun
 **/
@Data
public class QueryData {

    private String issueType;
    private String description;
    private String firstName;
    private String surName;
    private String email;

}
