package com.dw.ngms.cis.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class TemplateSearchResultDto<T> {

    private Collection<T> results;

    private Integer totalRecords;

    private Integer foundRecords;

    private Long templateAuditId;

}
