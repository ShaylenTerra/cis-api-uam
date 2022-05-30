package com.dw.ngms.cis.persistence.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "VW_WORKFLOW_PRODUCTIVITY")
public class VwWorkflowProductivity {

    @Id
    @Column(name = "PROCESSID")
    private Long processId;

    @Column(name = "CHILD_PROCESSID")
    private Long childProcessId;


    @Column(name = "DATA_PROVINCE")
    private Long dataProvince;

    @Column(name = "DATA_PROVINCE_NAME")
    private String dataProvinceName;

    @Column(name = "PROCESSNAME")
    private String processName;

    @Column(name = "WORKFLOWID")
    private Long workflowId;

    @Column(name = "REFERENCE_NO")
    private String referenceNo;

    @Column(name = "REQUEST_PROVINCE")
    private String requestProvince;

    @Column(name = "DATERECEIVED")
    private LocalDateTime dateReceived;

    @Column(name = "INTERNALSTATUS")
    private Long internalStatus;

    @Column(name = "INTERNALSTATUSCAPTION")
    private String internalStatusCaption;

    @Column(name = "REQUESTOR")
    private String requestor;

    @Column(name = "REQUESTOR_TYPE")
    private String requestorType;

    @Column(name = "REQUESTOR_ROLE")
    private String requestorRole;

    @Column(name = "REQUESTOR_SECTOR")
    private String requestorSector;

    @Column(name = "REQUESTOR_PROVINCE")
    private String requestorProvince;

    @Column(name = "INVOICE_NUMBER")
    private String invoiceNumber;

    @Column(name = "INVOICE_AMOUNT")
    private Long invoiceAmount;

    @Column(name = "INVOICE_DUE_DATE")
    private LocalDateTime invoiceDueDate;

    @Column(name = "PAYMENT_AMOUNT")
    private Long paymentAmount;

    @Column(name = "PAYMENT_DATE")
    private LocalDateTime paymentDate;

    @Column(name = "TRUNAROUND_DURATION")
    private Long trunaroundDuration;

    @Column(name = "COMPLETEDON")
    private LocalDateTime completedOn;

    @Column(name = "PRODUCTCATEGORY")
    private String productCategory;

    @Column(name = "PRODUCTITEMS")
    private String productItems;

    @Column(name = "PRODUCT_QUANTITY")
    private Long productQuantity;

    @Column(name = "PRODUCTIVITY_MINUTES")
    private Long productivityMinutes;

    @Column(name = "PARENT_WORKFLOWID")
    private Long parentWorkflowId;

    @Column(name = "COMMENTS")
    private String comments;

}
