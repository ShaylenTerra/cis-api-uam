package com.dw.ngms.cis.service.workflow;

import com.dw.ngms.cis.web.vm.workflow.ProcessNotificationsVm;

/**
 * @author : prateekgoel
 * @since : 31/05/21, Mon
 **/
public interface ProcessNotification {

    void process(final ProcessNotificationsVm processNotificationsVm) throws Exception;
}
