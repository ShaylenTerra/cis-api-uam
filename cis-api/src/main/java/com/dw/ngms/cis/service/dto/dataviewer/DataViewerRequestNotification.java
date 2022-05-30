package com.dw.ngms.cis.service.dto.dataviewer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : prateekgoel
 * @since : 20/03/21, Sat
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataViewerRequestNotification {

    private String referenceNo;

    private String emailId;

    private String fullName;

    private Long templateId;

    private String query;

    private String ftpLink;

}
