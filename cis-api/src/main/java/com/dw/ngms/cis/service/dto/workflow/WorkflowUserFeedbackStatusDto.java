package com.dw.ngms.cis.service.dto.workflow;

import lombok.Data;

import java.util.Collection;
import java.util.List;

/**
 * @author : pragayanshu
 * @since : 2022/03/28, Mon
 **/
@Data
public class WorkflowUserFeedbackStatusDto {

    Long applicantId;
    List<Long> provinces;
}
