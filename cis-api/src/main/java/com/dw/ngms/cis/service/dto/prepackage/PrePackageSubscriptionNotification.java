package com.dw.ngms.cis.service.dto.prepackage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : prateekgoel
 * @since : 21/01/21, Thu
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrePackageSubscriptionNotification {

    private Long userId;

    private Long templateId;

    private String referenceId;

    private String fullName;

    private String emailId;

}
