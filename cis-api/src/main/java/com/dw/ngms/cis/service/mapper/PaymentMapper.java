package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.Payment;
import com.dw.ngms.cis.service.dto.workflow.PaymentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import java.time.Clock;
import java.time.LocalDateTime;

/**
 * @author : prateekgoel
 * @since : 06/01/21, Wed
 **/
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {

    @Mappings({
            @Mapping(target = "paymentId", source = "paymentId"),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "paymentDate", source = "paymentDate"),
            @Mapping(target = "workflowId", source = "workflowId"),
            @Mapping(target = "paymentComments", source = "comments"),
            @Mapping(target = "paymentDocumentId", source = "documentTypeId"),
            @Mapping(target = "paymentAmount", source = "paidAmount", defaultValue = "0.0"),
            @Mapping(target = "paymentReferenceId", source = "paymentReferenceNo"),
            @Mapping(target = "invoiceAmount", source = "invoiceAmount"),
            @Mapping(target = "invoiceDueDate", source = "invoiceDueDate"),
            @Mapping(target = "invoiceGenerationDate", source = "invoiceGenerationDate"),
            @Mapping(target = "invoiceComment", source = "invoiceComment"),
    })
    Payment proofOfPaymentDtoToPayment(PaymentDto paymentDto);


    @Mappings({
            @Mapping(target = "paymentId", source = "paymentId"),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "paymentDate", source = "paymentDate"),
            @Mapping(target = "workflowId", source = "workflowId"),
            @Mapping(target = "comments", source = "paymentComments"),
            @Mapping(target = "documentTypeId", source = "paymentDocumentId"),
            @Mapping(target = "paidAmount", source = "paymentAmount", defaultValue = "0.0"),
            @Mapping(target = "paymentReferenceNo", source = "paymentReferenceId"),
            @Mapping(target = "invoiceAmount", source = "invoiceAmount"),
            @Mapping(target = "invoiceDueDate", source = "invoiceDueDate"),
            @Mapping(target = "invoiceGenerationDate", source = "invoiceGenerationDate"),
            @Mapping(target = "invoiceComment", source = "invoiceComment")
    })
    PaymentDto paymentToProofOfPaymentDto(Payment payment);

}
