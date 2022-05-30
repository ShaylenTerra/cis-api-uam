package com.dw.ngms.cis.dto;

import com.dw.ngms.cis.enums.UserTypes;
import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 10/12/20, Thu
 **/
@Data
public class SearchTypeOfficeMappingDto {

    private Long mapId;

    private Long provinceId;

    private Long searchTypeId;

    private UserTypes userType;

    private Long isActive;
}
