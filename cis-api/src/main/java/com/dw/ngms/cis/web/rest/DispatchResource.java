package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.persistence.projection.WorkflowDocuments;
import com.dw.ngms.cis.persistence.projection.cart.CartDispatchDocView;
import com.dw.ngms.cis.service.cart.DispatchService;
import com.dw.ngms.cis.service.dto.cart.CartDispatchDto;
import com.dw.ngms.cis.service.dto.cart.CartDispatchItemDto;
import com.dw.ngms.cis.web.annotation.ApiPageable;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.response.UpdateResponse;
import com.dw.ngms.cis.web.util.PaginationUtil;
import com.dw.ngms.cis.web.vm.cart.CartItemDispatchVm;
import com.dw.ngms.cis.web.vm.cart.DispatchDocumentUploadVm;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Collection;
import java.util.zip.ZipOutputStream;

/**
 * @author : prateekgoel
 * @since : 11/02/21, Thu
 **/
@BaseResponse
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.API_BASE_MAPPING + "/dispatch")
public class DispatchResource {

    private final DispatchService dispatchService;

    /**
     * @param dispatchDocumentUploadVm {@link DispatchDocumentUploadVm}
     */
    @PostMapping("/uploadCartItemsDocs")
    public void uploadSupportingDocs(
            @ModelAttribute @Valid final DispatchDocumentUploadVm dispatchDocumentUploadVm) {

        dispatchService.addSupportingDocs(dispatchDocumentUploadVm);

    }

    /**
     * @param cartItemId cartItemId
     * @return Collection<WorkflowDocuments>
     */
    @GetMapping("/getCartItemDocs")
    @ApiPageable
    public ResponseEntity<Collection<WorkflowDocuments>> getCartItemDocument(@RequestParam @NotNull final Long cartItemId,
                                                                             @ApiIgnore final Pageable pageable) {
        Page<WorkflowDocuments> workflowDocuments = dispatchService.getCartItemDocuments(cartItemId, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(workflowDocuments);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(workflowDocuments.getContent());
    }

    /**
     * @param documentId documentId
     * @return {@link Resource}
     * @throws IOException IoException {@link IOException}
     */
    @GetMapping(value = "/downloadCartItemsDoc",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadCartItemDocuments(@RequestParam @NotNull final Long documentId) throws IOException {
        Resource cartItemDocs = dispatchService.getCartItemDocs(documentId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentLength(cartItemDocs.contentLength());
        httpHeaders.setContentDispositionFormData("attachment",
                dispatchService.getCartItemDocumentName(documentId));
        return ResponseEntity.ok()
                .body(cartItemDocs);
    }

    /**
     * @param cartItemDispatchVm {@link CartItemDispatchVm}
     * @return {@link UpdateResponse}
     */
    @PostMapping("/addCartItemDispatchInfo")
    public ResponseEntity<UpdateResponse> addDispatchInformationForCartItem(
            @RequestBody @Valid CartItemDispatchVm cartItemDispatchVm) {
        Boolean aBoolean = dispatchService.addDispatchInformation(cartItemDispatchVm);
        UpdateResponse build = UpdateResponse.builder()
                .update(aBoolean)
                .build();
        return ResponseEntity.ok()
                .body(build);
    }

    /**
     * @param workflowId workflowId
     * @return Collection<CartInvoiceItemDto>
     */
    @GetMapping("/getDispatchTemplateData")
    public ResponseEntity<Collection<CartDispatchItemDto>> getInvoiceData(@RequestParam final Long workflowId) {
        return ResponseEntity.ok()
                .body(dispatchService.generateDispatchData(workflowId));
    }

    /**
     * @param cartDispatchDto {@link CartDispatchDto}
     * @return {@link CartDispatchDto}
     */
    @PostMapping("/addDispatchDetails")
    public ResponseEntity<CartDispatchDto> addDispatchDetails(@RequestBody @Valid final CartDispatchDto cartDispatchDto) {
        return ResponseEntity.ok()
                .body(dispatchService.addCartDispatchInformation(cartDispatchDto));
    }

    /**
     * @param workflowId workflowId
     * @return CartDispatchDocView {@link CartDispatchDocView}
     */
    @GetMapping("/getDocDetails")
    public ResponseEntity<CartDispatchDocView> getDispatchDocs(@RequestParam @NotNull final Long workflowId) {
        return ResponseEntity.ok()
                .body(dispatchService.getAllDispatchDocsForWorkflow(workflowId));
    }

    /**
     * @param workflowId          workflowId
     * @param httpServletResponse {@link HttpServletResponse}
     * @throws IOException exception
     */
    @GetMapping(value = "/downloadDispatchDocs", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadZippedDocs(@RequestParam @NotNull final Long workflowId, HttpServletResponse httpServletResponse) throws IOException {
        ZipOutputStream zipOutputStream = new ZipOutputStream(httpServletResponse.getOutputStream());
        dispatchService.downloadZippedDispatchDocs(workflowId, zipOutputStream);
        zipOutputStream.close();
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        httpServletResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=docs.zip");

    }

    /**
     * @param workflowId workflowId
     */
    @GetMapping("/uploadDispatchDocsToFtp")
    public void uploadDispatchDocumentToFtp(@RequestParam final Long workflowId) {
        dispatchService.uploadDocsOnFtp(workflowId);
    }

    /**
     * @param workflowId workflowId
     */
    @GetMapping("/sendDispatchEmail/{workflowId}")
    public void sendDispatchEmail(@PathVariable @NotNull Long workflowId) throws Exception {
        dispatchService.sendEmailNotificationForDispatchItems(workflowId);
    }


}
