package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.persistence.projection.fee.InvoiceItemCostProjection;
import com.dw.ngms.cis.service.InvoiceService;
import com.dw.ngms.cis.service.dto.EmailDto;
import com.dw.ngms.cis.service.dto.cart.CartInvoiceItemDto;
import com.dw.ngms.cis.service.dto.workflow.InvoiceItemsDto;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.request.InvoiceItemCostVm;
import com.dw.ngms.cis.web.response.UpdateResponse;
import com.dw.ngms.cis.web.vm.InvoiceTemplateVm;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 26/01/21, Tue
 **/
@BaseResponse
@AllArgsConstructor
@RestController
@RequestMapping(AppConstants.API_BASE_MAPPING + "/invoice")
public class InvoiceResource {

    private final InvoiceService invoiceService;

    /**
     * @param invoiceItemsDto {@link InvoiceItemsDto}
     * @return {@link UpdateResponse}
     */
    @PostMapping("/partialSave")
    public ResponseEntity<UpdateResponse> partialSaveInvoiceData(@RequestBody @Valid final InvoiceItemsDto invoiceItemsDto) {
        Boolean aBoolean = invoiceService.partialSaveInvoiceDetails(invoiceItemsDto);
        UpdateResponse build = UpdateResponse.builder()
                .update(aBoolean)
                .build();
        return ResponseEntity.ok().body(build);

    }

    /**
     * @param invoiceItemsDto {@link InvoiceItemsDto}
     * @return {@link UpdateResponse}
     */
    @PostMapping("/save")
    public ResponseEntity<UpdateResponse>
    saveInvoiceData(@RequestBody @Valid final InvoiceItemsDto invoiceItemsDto) throws JsonProcessingException {
        Boolean aBoolean = invoiceService.saveInvoiceDetails(invoiceItemsDto);
        UpdateResponse build = UpdateResponse.builder()
                .update(aBoolean)
                .build();
        return ResponseEntity.ok().body(build);

    }

    /**
     * @param workflowId workflowId
     * @return Collection<CartInvoiceItemDto>
     */
    @GetMapping("/getInvoiceTemplateData")
    public ResponseEntity<Collection<CartInvoiceItemDto>> getInvoiceData(@RequestParam final Long workflowId) {
        return ResponseEntity.ok()
                .body(invoiceService.generateInvoiceData(workflowId));
    }

    /**
     * @param workflowId workflowId
     * @return {@link Resource}
     */
    @GetMapping(value = "/generate", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> generatePdf(@RequestParam final Long workflowId) throws IOException {

        final Resource resource = invoiceService.generateInvoicePdf(workflowId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        httpHeaders.setContentLength(resource.contentLength());
        httpHeaders.setContentDispositionFormData("attachment", "invoice.pdf");
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(resource);
    }

    /**
     * @param workflowId workflowId
     * @return {@link Resource}
     */
    @GetMapping(value = "/download", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> downloadInvoice(@RequestParam @NotNull final Long workflowId) throws IOException {
        Resource invoice = invoiceService.getInvoice(workflowId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        httpHeaders.setContentLength(invoice.contentLength());
        httpHeaders.setContentDispositionFormData("attachment", "invoice.pdf");
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(invoice);

    }

    /**
     * @param invoiceItemCostVm {@link InvoiceItemCostVm}
     * @return {@link InvoiceItemCostProjection}
     */
    @PostMapping("/items/cost")
    public ResponseEntity<InvoiceItemCostProjection>
    getInvoiceItemCosting(@RequestBody @Valid final InvoiceItemCostVm invoiceItemCostVm) {
        return ResponseEntity.ok()
                .body(invoiceService.getInvoiceItemCost(invoiceItemCostVm));
    }

    /**
     * @return {@link EmailDto}
     */
    @GetMapping("/previewEmail/{workflowId}")
    public ResponseEntity<EmailDto> getInvoiceEmailPreview(@PathVariable final Long workflowId) {
        return ResponseEntity.ok()
                .body(invoiceService.getPreviewMail(workflowId));
    }

    /**
     * EmailDto
     *
     * @param invoiceTemplateVm {@link InvoiceTemplateVm}
     * @return {@link EmailDto}
     */
    @PostMapping("/previewEmail")
    public ResponseEntity<EmailDto> updateInvoiceEmailTemplate(
            @RequestBody @Valid final InvoiceTemplateVm invoiceTemplateVm) {
        return ResponseEntity.ok()
                .body(invoiceService.updateInvoiceEmailTemplate(invoiceTemplateVm));
    }

}
