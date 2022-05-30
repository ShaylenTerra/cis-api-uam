package com.dw.ngms.cis.service.workflow;

import com.dw.ngms.cis.enums.ProcessTemplateType;
import com.dw.ngms.cis.service.reservation.ReservationNewRequestNotification;
import com.dw.ngms.cis.service.reservation.ReservationRequestRejectionNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Optional;

/**
 * @author : prateekgoel
 * @since : 31/05/21, Mon
 **/
@Component
public class ProcessNotificationFactory {

    private final InvoiceNotification invoiceNotification;
    private final PaymentVerificationNotification paymentVerificationNotification;
    private final PaymentUploadNotification paymentUploadNotification;
    private final DispatchNotification dispatchNotification;
    private final QueryNotification queryNotification;
    private final NotifyManagerLog notifyManagerLog;
    private final InformationRequestNotification informationRequestNotification;
    private final NotifyManagerOutcomeNotification notifyManagerOutcomeNotification;
    private final QueryOutcomeNotification queryOutcomeNotification;
    private final AutoDispatchNotification autoDispatchNotification;
    private final ReservationNewRequestNotification reservationNewRequestNotification;
    private final ReservationRequestRejectionNotification reservationRequestRejectionNotification;
    private final EnumMap<ProcessTemplateType, ProcessNotification> processNotifications =
            new EnumMap<>(ProcessTemplateType.class);

    @Autowired
    public ProcessNotificationFactory(InvoiceNotification invoiceNotification,
                                      PaymentVerificationNotification paymentVerificationNotification,
                                      PaymentUploadNotification paymentUploadNotification,
                                      DispatchNotification dispatchNotification,
                                      QueryNotification queryNotification,
                                      NotifyManagerLog notifyManagerLog,
                                      InformationRequestNotification informationRequestNotification,
                                      NotifyManagerOutcomeNotification notifyManagerOutcomeNotification,
                                      QueryOutcomeNotification queryOutcomeNotification,
                                      AutoDispatchNotification autoDispatchNotification,
                                      ReservationNewRequestNotification reservationNewRequestNotification,
                                      ReservationRequestRejectionNotification reservationRequestRejectionNotification) {
        this.invoiceNotification = invoiceNotification;
        this.paymentVerificationNotification = paymentVerificationNotification;
        this.paymentUploadNotification = paymentUploadNotification;
        this.dispatchNotification = dispatchNotification;
        this.queryNotification = queryNotification;
        this.notifyManagerLog = notifyManagerLog;
        this.informationRequestNotification = informationRequestNotification;
        this.notifyManagerOutcomeNotification = notifyManagerOutcomeNotification;
        this.queryOutcomeNotification = queryOutcomeNotification;
        this.autoDispatchNotification = autoDispatchNotification;
        this.reservationNewRequestNotification = reservationNewRequestNotification;
        this.reservationRequestRejectionNotification = reservationRequestRejectionNotification;
    }

    public ProcessNotificationFactory buildFactory() {
        processNotifications.put(ProcessTemplateType.INVOICE, invoiceNotification);
        processNotifications.put(ProcessTemplateType.PAYMENT_CONFIRMATION, paymentVerificationNotification);
        processNotifications.put(ProcessTemplateType.PAYMENT_UPLOAD, paymentUploadNotification);
        processNotifications.put(ProcessTemplateType.DISPATCH, dispatchNotification);
        processNotifications.put(ProcessTemplateType.QUERY, queryNotification);
        processNotifications.put(ProcessTemplateType.NOTIFY_MANAGER, notifyManagerLog);
        processNotifications.put(ProcessTemplateType.INFORMATION_REQUEST, informationRequestNotification);
        processNotifications.put(ProcessTemplateType.NOTIFY_MANAGER_OUTCOME, notifyManagerOutcomeNotification);
        processNotifications.put(ProcessTemplateType.QUERY_OUTCOME, queryOutcomeNotification);
        processNotifications.put(ProcessTemplateType.AUTO_DISPATCH,autoDispatchNotification);
        processNotifications.put(ProcessTemplateType.RES_NEW_REQUEST_NOTIFICATION,reservationNewRequestNotification);
        processNotifications.put(ProcessTemplateType.RES_REQUEST_REJECTION_LETTER,reservationRequestRejectionNotification);
        return this;
    }

    public Optional<ProcessNotification> getProcessNotification(ProcessTemplateType templateType) {
        return Optional.ofNullable(processNotifications.get(templateType));
    }
}
