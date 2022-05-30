package com.dw.ngms.cis.web.vm.dataviewer;

import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 11/03/21, Thu
 **/
@Data
public class DataViewerLogVm {

    private Long userId;

    private String query;

    private String objectName;

    public Long isFtp;

    public Long isEmail;

}
