package com.dw.ngms.cis.service.dto.dataviewer;

import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 11/03/21, Thu
 **/
@Data
public class DataViewerLogDto {

    public Long isFtp;
    public Long isEmail;
    private Long userid;
    private String query;
    private String objectName;
}
