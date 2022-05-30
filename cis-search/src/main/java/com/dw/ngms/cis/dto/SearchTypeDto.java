package com.dw.ngms.cis.dto;

import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 10/12/20, Thu
 **/
@Data
public class SearchTypeDto {

    private Long searchTypeId;

    private String name;

    private String tag;

    private Long parentSearchTypeId;

    private String description;

    private String controlType;

    private Long isActive;

    private String config;

    private Long templateListItemId;

    private String cartEditable;
}
