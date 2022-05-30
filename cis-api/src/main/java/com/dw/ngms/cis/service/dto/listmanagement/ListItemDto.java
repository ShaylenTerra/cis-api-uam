package com.dw.ngms.cis.service.dto.listmanagement;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author : prateekgoel
 * @since : 06/12/20, Sun
 **/
@Data
public class ListItemDto {

    private Long itemId;

    private String itemCode;

    @NotNull
    private Long listCode;

    private String caption;

    private String description;

    private Long isActive;

    private Long isDefault;

}
