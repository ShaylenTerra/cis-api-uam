package com.dw.ngms.cis.service.reservation;

import com.dw.ngms.cis.service.workflow.ProcessNotification;
import com.dw.ngms.cis.web.vm.workflow.ProcessNotificationsVm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author prateek on 09-02-2022
 */
@Component
@Slf4j
@AllArgsConstructor
public class ReservationExpiryNotification implements ProcessNotification {

    @Override
    public void process(ProcessNotificationsVm processNotificationsVm) throws Exception {

    }
}
