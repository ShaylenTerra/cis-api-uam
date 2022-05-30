package com.dw.ngms.cis.web.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 20/11/20, Fri
 **/
@Builder
@Data
public class UploadFileResponse {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

}
