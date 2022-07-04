package com.dw.ngms.cis.service.dto.examination.dockets;

import lombok.Data;

@Data
public class DocketListDto {

    private Long docketListId;

    private String itemCode;

    private String name;

    private String description;

    private Long parentId;

    private Long docketTypeId;

    private char status;

    private String value;

    private String control;

    private String controlValues;
}
