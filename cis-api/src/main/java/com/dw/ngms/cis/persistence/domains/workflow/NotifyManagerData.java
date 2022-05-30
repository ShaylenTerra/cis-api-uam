package com.dw.ngms.cis.persistence.domains.workflow;

import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 20/06/21, Sun
 **/
@Data
public class NotifyManagerData {

    private String provinceName;
    private String searchType;
    private String searchFilter;
    private String searchNumber;
    private String comment;
}
