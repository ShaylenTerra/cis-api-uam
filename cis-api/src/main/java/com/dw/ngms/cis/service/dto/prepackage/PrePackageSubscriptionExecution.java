package com.dw.ngms.cis.service.dto.prepackage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : prateekgoel
 * @since : 17/03/21, Wed
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrePackageSubscriptionExecution {

    private Long templateId;

    private String fullName;

    private String prepackageName;

    private String emailId;

    private String ftpLink;

    private String refNo;

    private String details;
}
