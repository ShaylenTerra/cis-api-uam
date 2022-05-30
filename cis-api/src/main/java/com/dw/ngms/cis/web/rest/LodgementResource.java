package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.persistence.projection.lodgment.LodgementDocumentSummaryDto;
import com.dw.ngms.cis.persistence.projection.lodgment.LodgmentDraftListingProjection;
import com.dw.ngms.cis.service.dispatch.GlobalDispatchService;
import com.dw.ngms.cis.service.dto.dispatch.DispatchDto;
import com.dw.ngms.cis.service.dto.lodgment.*;
import com.dw.ngms.cis.service.dto.reservation.ReservationOutcomeDto;
import com.dw.ngms.cis.service.lodgment.LodgementDraftService;
import com.dw.ngms.cis.service.lodgment.LodgementDraftStepsService;
import com.dw.ngms.cis.web.annotation.ApiPageable;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.util.PaginationUtil;
import com.dw.ngms.cis.web.vm.LodgementNotificationVm;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Collection;

/**
 * @author prateek on 30-12-2021
 */
@BaseResponse
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.API_BASE_MAPPING + "/lodgement")
public class LodgementResource {


    private final LodgementDraftStepsService lodgementDraftStepsService;

    private final LodgementDraftService lodgementDraftService;

    private final GlobalDispatchService globalDispatchService;

    /**
     * @param pageable pageable
     * @return Collection<LodgementDraftDto>
     */
    @JsonView(LodgementDraftDtoView.Public.class)
    @GetMapping("/getLodgementDrafts")
    @ApiPageable
    public ResponseEntity<Collection<LodgementDraftDto>> getAllLodgementDraft(
            @ApiIgnore @SortDefault(sort = "dated", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<LodgementDraftDto> allLodgementDraft = lodgementDraftService.getAllLodgementDraft(pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(allLodgementDraft);
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(allLodgementDraft.getContent());
    }

    /**
     * @param draftId draftId
     * @return {@link LodgementDraftDto}
     */
    @JsonView({LodgementDraftDtoView.Internal.class})
    @GetMapping("/draft/{draftId}")
    public ResponseEntity<LodgementDraftDto> getLodgementDraft(@PathVariable @NotNull final Long draftId) {
        LodgementDraftDto draft = lodgementDraftService.getDraft(draftId);
        return ResponseEntity
                .ok(draft);
    }

    /**
     * @param draftId draftId
     */
    @DeleteMapping("/draft/{draftId}")
    public void deleteDraft(@PathVariable @NotNull final Long draftId) {
        lodgementDraftService.deleteDraft(draftId);
    }

    /**
     * @param lodgementDraftDto {@link LodgementDraftDto}
     * @return {@link LodgementDraftDto}
     */
    @JsonView(LodgementDraftDtoView.Public.class)
    @PostMapping("/saveDraft")
    public ResponseEntity<LodgementDraftDto>
    saveLodgementDraft(@RequestBody @Validated
                       @JsonView(LodgementDraftDtoView.Public.class) final LodgementDraftDto lodgementDraftDto) {
        return ResponseEntity.ok()
                .body(lodgementDraftService.saveLodgementDraft(lodgementDraftDto));
    }

    /**
     * @param lodgementDraftDto {@link LodgementDraftDto}
     * @return {@link LodgementDraftDto}
     */
    @JsonView(LodgementDraftDtoView.Internal.class)
    @PostMapping("/updateDraft")
    public ResponseEntity<LodgementDraftDto>
    updateLodgementDraft(@RequestBody @Validated
                         @JsonView(LodgementDraftDtoView.Internal.class) final LodgementDraftDto lodgementDraftDto) {
        return ResponseEntity.ok()
                .body(lodgementDraftService.updateLodgementDraft(lodgementDraftDto));
    }

    /**
     * @param lodgementDraftStepsDto {@link LodgementDraftStepsDto}
     * @return {@link LodgementDraftStepsDto}
     */
    @PostMapping("/saveDraftSteps")
    public ResponseEntity<LodgementDraftStepsDto>
    saveLodgementDraftSteps(@RequestBody final LodgementDraftStepsDto lodgementDraftStepsDto) {
        return ResponseEntity.ok()
                .body(lodgementDraftStepsService.saveLodgementDraftSteps(lodgementDraftStepsDto));
    }


    /**
     * @param lodgementDraftAnnexureDto {@link LodgementDraftAnnexureDto}
     * @return {@link  LodgementDraftAnnexureDto}
     */
    @PostMapping(value = "/addAnnexure", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<LodgementDraftAnnexureDto>
    addAnnexure(@ModelAttribute @Valid final LodgementDraftAnnexureDto lodgementDraftAnnexureDto) {
        LodgementDraftAnnexureDto lodgementDraftAnnexureDto1 = lodgementDraftService
                .saveLodgementDocument(lodgementDraftAnnexureDto);
        return ResponseEntity.ok()
                .body(lodgementDraftAnnexureDto1);
    }

    /**
     * @param draftId draftId
     * @return Collection<LodgementDraftAnnexureDto>
     */
    @GetMapping("/getAnnexure")
    public ResponseEntity<Collection<LodgementDraftAnnexureDto>>
    getAnnexure(@RequestParam @NotNull final Long draftId) {
        Collection<LodgementDraftAnnexureDto> allLodgementDraftAnnexures = lodgementDraftService
                .getAllLodgementDraftAnnexures(draftId);
        return ResponseEntity.ok().body(allLodgementDraftAnnexures);
    }

    /**
     * @param annexureId annexureId
     */
    @DeleteMapping(value = "/deleteAnnexure")
    public void removeAnnexure(@RequestParam final Long annexureId) {
        lodgementDraftService.removeAnnuxure(annexureId);
    }

    /**
     * @param annexureId annexureId
     * @return {@link Resource}
     */
    @GetMapping(value = "/getAnnexure/{annexureId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getAnnuxure(@PathVariable final Long annexureId) throws IOException {
        Resource annuxure = lodgementDraftService.getAnnuxure(annexureId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentLength(annuxure.contentLength());
        httpHeaders.setContentDispositionFormData("attachment",
                annuxure.getFilename());
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(annuxure);
    }


    /**
     * @param draftRequestId draftRequestId
     */
    @DeleteMapping("/deleteDraftRequest")
    public void deleteLodgementDraft(@RequestParam @NotNull final Long draftRequestId) {
        lodgementDraftService.deleteDraftRequest(draftRequestId);
    }

    /**
     * @param draftId draftId
     * @return Collection<LodgementDraftStepsDto>
     */
    @GetMapping("/getDraftSteps")
    public ResponseEntity<Collection<LodgementDraftStepsDto>> getAllDraftSteps(@RequestParam @NotNull final Long draftId) {
        Collection<LodgementDraftStepsDto> allDraftSteps = lodgementDraftService.getAllDraftSteps(draftId);
        return ResponseEntity.ok()
                .body(allDraftSteps);
    }


    /**
     * @param stepId stepId
     */
    @DeleteMapping("/deleteDraftSteps/{stepId}")
    public void deleteDraftStep(@PathVariable @NotNull final Long stepId) {
        lodgementDraftService.deleteDraftStep(stepId);
    }

    /**
     * @param userId    userId
     * @param processId processId
     * @param pageable  {@link Pageable}
     * @return Collection<LodgmentDraftListingProjection>
     */
    @GetMapping("/listLodgementDraft")
    @ApiPageable
    public ResponseEntity<Collection<LodgmentDraftListingProjection>>
    getLodgements(@RequestParam @NotNull final Long userId,
                  @RequestParam @NotNull final Long processId,
                  @ApiIgnore @SortDefault(sort = "workflowId", direction = Sort.Direction.DESC) final Pageable pageable) {
        Page<LodgmentDraftListingProjection> allLodgementDraftForUserId = lodgementDraftService
                .getAllLodgementDraftForUserId(userId, processId, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(allLodgementDraftForUserId);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(allLodgementDraftForUserId.getContent());
    }

    /**
     * @param draftId    draftId
     * @param workflowId workflowId
     */
    @GetMapping("/checkoutLodgementDraft")
    public void checkoutLodgementDraft(@RequestParam @NotNull final Long draftId,
                                       @RequestParam @NotNull final Long workflowId) {
        lodgementDraftService.checkoutLodgementDraft(draftId, workflowId);
    }

    /**
     * @param workflowId workflowId
     * @return {@link LodgementDraftDto}
     */
    @GetMapping("/draft")
    public ResponseEntity<LodgementDraftDto> getLodgementDraftByWorkflowId(@RequestParam
                                                                           @NotNull final Long workflowId) {
        LodgementDraftDto lodgementDraftByWorkflowId = lodgementDraftService
                .getLodgementDraftByWorkflowId(workflowId);

        return ResponseEntity.ok().body(lodgementDraftByWorkflowId);
    }

    /**
     * @param lodgementNotificationVm {@link LodgementNotificationVm}
     */
    @PostMapping("/notifyForLodgement")
    public void notifyForLodgement(@RequestBody @Valid final LodgementNotificationVm lodgementNotificationVm) {
        lodgementDraftService.notifyForLodgement(lodgementNotificationVm);
    }

    /**
     * @param lodgementDraftPaymentDto {@link LodgementDraftPaymentDto}
     * @return {@link LodgementDraftPaymentDto}
     */
    @PostMapping(value = "/addDraftPayment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<LodgementDraftPaymentDto>
    addPayment(@ModelAttribute @Valid final LodgementDraftPaymentDto lodgementDraftPaymentDto) {
        LodgementDraftPaymentDto lodgementDraftPaymentDto1 = lodgementDraftService
                .addPayment(lodgementDraftPaymentDto);
        return ResponseEntity.ok()
                .body(lodgementDraftPaymentDto1);
    }

    /**
     * @param lodgementDraftPaymentDto {@link LodgementDraftPaymentDto}
     * @return {@link LodgementDraftPaymentDto}
     */
    @PostMapping("/updateDraftPayment")
    public ResponseEntity<LodgementDraftPaymentDto>
    updateDraftPayment(@RequestBody @Validated final LodgementDraftPaymentDto lodgementDraftPaymentDto) {
        LodgementDraftPaymentDto lodgementDraftPaymentDto1 = lodgementDraftService
                .updateDraftPayment(lodgementDraftPaymentDto);
        return ResponseEntity.ok()
                .body(lodgementDraftPaymentDto1);

    }


    /**
     * @param draftId draftId
     * @return Collection<LodgementDraftPaymentDto>
     */
    @GetMapping("/getDraftPayments")
    public ResponseEntity<Collection<LodgementDraftPaymentDto>>
    getDraftPayments(@RequestParam @NotNull final Long draftId) {
        Collection<LodgementDraftPaymentDto> lodgementDraftPaymentDtos = lodgementDraftService
                .getPayments(draftId);
        return ResponseEntity.ok()
                .body(lodgementDraftPaymentDtos);
    }

    /**
     * @param payId payId
     */
    @DeleteMapping("/deleteDraftPayment")
    public void deleteDraftPaymentDetails(@RequestParam @NotNull final Long payId) {
        lodgementDraftService.deleteDraftPaymentDetails(payId);
    }

    /**
     * @param payId payId
     * @return {@link Resource}
     * @throws IOException iOException {@link IOException}
     */
    @GetMapping(value = "/getPayment/{payId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getDraftPaymentDocument(@PathVariable final Long payId) throws IOException {
        Resource paymentDocument = lodgementDraftService.getDraftPaymentDocument(payId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentLength(paymentDocument.contentLength());
        httpHeaders.setContentDispositionFormData("attachment",
                paymentDocument.getFilename());
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(paymentDocument);
    }

    /**
     * @param searchTerm searchTerm
     * @return Collection<ReservationOutcomeDto>
     */
    @GetMapping("/searchDraftRequest")
    public ResponseEntity<Collection<ReservationOutcomeDto>>
    searchLodgDraftRequest(@RequestParam @NotNull final String searchTerm) {
        Collection<ReservationOutcomeDto> reservationOutcomeDtos = lodgementDraftService
                .searchLodgDraftRequest(searchTerm);
        return ResponseEntity.ok()
                .body(reservationOutcomeDtos);
    }

    /**
     * @param referenceNo referenceNo
     * @param draftId     draftId
     * @return Collection<LodgementDraftStepsDto>
     */
    @GetMapping("/addStepsByReservationRef")
    public ResponseEntity<Collection<LodgementDraftStepsDto>> addStepsByReservationRef(@RequestParam final String referenceNo,
                                                                                       @RequestParam final String fileRefNo,
                                                                                       @RequestParam final String name,
                                                                                       @RequestParam @NotNull final Long draftId) {
        Collection<LodgementDraftStepsDto> lodgementDraftStepsDtos = null;
        if(StringUtils.isNotBlank(referenceNo)) {
            lodgementDraftStepsDtos = lodgementDraftService
                    .addStepsByReservationRef(referenceNo, draftId);
        }
        if(StringUtils.isNotBlank(fileRefNo)) {
            lodgementDraftStepsDtos = lodgementDraftService
                    .addStepsByReservationFileRef(fileRefNo, draftId);
        }
        if(StringUtils.isNotBlank(name)) {
            lodgementDraftStepsDtos = lodgementDraftService
                    .addStepsByReservationName(name, draftId);
        }

        return ResponseEntity.ok()
                .body(lodgementDraftStepsDtos);
    }

    /**
     * @param lodgementDraftRequestDto {@link LodgementDraftRequestDto}
     * @return LodgementDraftRequestDto {@link LodgementDraftRequestDto}
     */
    @PostMapping("/addRequestToDraftStep")
    public ResponseEntity<LodgementDraftRequestDto> addRequest(@RequestBody @Validated final LodgementDraftRequestDto lodgementDraftRequestDto) {
        LodgementDraftRequestDto lodgementDraftRequestDto1 = lodgementDraftService
                .addRequestToDraftStep(lodgementDraftRequestDto);
        return ResponseEntity.ok()
                .body(lodgementDraftRequestDto1);
    }

    /**
     * @param requestId requestId
     */
    @DeleteMapping("/removeRequest")
    public void removeRequestFromStep(@RequestParam @NotNull final Long requestId) {
        lodgementDraftService.removeRequestFromStep(requestId);
    }

    /**
     * @param lodgementDraftDocumentDto {@link  LodgementDraftDocumentDto}
     * @return {@link  LodgementDraftDocumentDto}
     */
    @PostMapping(value = "/uploadLdgResDetailDoc", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @JsonView(LodgementDraftDocumentDtoView.Internal.class)
    public ResponseEntity<LodgementDraftDocumentDto>
    uploadDocument(@ModelAttribute @Valid
                   @JsonView(LodgementDraftDocumentDtoView.External.class) final LodgementDraftDocumentDto lodgementDraftDocumentDto) {

        LodgementDraftDocumentDto lodgementDraftDocumentDto1 = lodgementDraftService
                .uploadReservationRequestDoc(lodgementDraftDocumentDto);

        return ResponseEntity.ok()
                .body(lodgementDraftDocumentDto1);
    }

    /**
     * @param documentId documentId
     */
    @DeleteMapping("/deleteLdgResDocument")
    public void deleteLodgementDraftDocument(@RequestParam @NotNull final Long documentId) {
        lodgementDraftService.deleteLodgementDraftDocument(documentId);
    }

    /**
     * @param documentId documentId
     * @return {@link Resource}
     * @throws IOException iOException {@link IOException}
     */
    @GetMapping(value = "/getLdgResDetailDoc", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadLdgResDetailDoc(@RequestParam @NotNull final Long documentId) throws IOException {
        Resource resDetailsDocument = lodgementDraftService.getDraftResDetailDoc(documentId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentLength(resDetailsDocument.contentLength());
        httpHeaders.setContentDispositionFormData("attachment",
                resDetailsDocument.getFilename());
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(resDetailsDocument);
    }

    /**
     * @param requestId requestId
     * @return Collection<LodgementDraftDocumentDto>
     */
    @GetMapping("/getAllDocumentForRequest")
    public ResponseEntity<Collection<LodgementDraftDocumentDto>>
    getALlDocumentForRequest(@RequestParam final Long requestId) {
        Collection<LodgementDraftDocumentDto> lodgementDraftDocumentDtos = lodgementDraftService
                .getAllDocumentForRequestId(requestId);
        return ResponseEntity.ok().body(lodgementDraftDocumentDtos);
    }

    @GetMapping("/getLodgementAllDocument")
    public ResponseEntity<Collection<LodgementDraftDocumentDto>> getAllDocs(@RequestParam @NotNull final Long draftId) {
        Collection<LodgementDraftDocumentDto> lodgementDraftDocumentDtos = lodgementDraftService
                .getAllDocuments(draftId);
        return ResponseEntity.ok()
                .body(lodgementDraftDocumentDtos);
    }

    /**
     * @param draftId draftId
     * @param stepId  stepId
     * @return Collection<LodgementDocumentSummaryDto>
     */
    @GetMapping("/documentSummary")
    public ResponseEntity<Collection<LodgementDocumentSummaryDto>> getDocumentSummary(@RequestParam @NotNull final Long draftId,
                                                                                      @RequestParam @NotNull final Long stepId) {
        Collection<LodgementDocumentSummaryDto> documentSummary = lodgementDraftService
                .getDocumentSummary(draftId, stepId);
        return ResponseEntity.ok()
                .body(documentSummary);

    }

    /**
     * @param draftId draftId
     * @return {@link Resource}
     * @throws IOException ioexception
     */
    @GetMapping(value = "/generatePerformaInvoice", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> generatePdf(@RequestParam @NotNull final Long draftId) throws IOException {

        final Resource resource = lodgementDraftService.generatePerformaInvoice(draftId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        httpHeaders.setContentLength(resource.contentLength());
        httpHeaders.setContentDispositionFormData("attachment", "performa-" + draftId + ".pdf");
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(resource);
    }


    /**
     * @param lodgementBatchDto {@link LodgementBatchDto}
     * @return {@link LodgementBatchDto}
     */
    @PostMapping("/issueBatch")
    public ResponseEntity<LodgementBatchDto>
    issueBatchForLodgement(@RequestBody @Validated final LodgementBatchDto lodgementBatchDto) {
        LodgementBatchDto lodgementBatchDto1 = lodgementDraftService
                .issueBatch(lodgementBatchDto);
        return ResponseEntity.ok()
                .body(lodgementBatchDto1);
    }

    /**
     * @param draftId draftId
     * @return {@link LodgementBatchDto}
     */
    @GetMapping("/getBatchDetails")
    public ResponseEntity<LodgementBatchDto> getBatchDetails(@RequestParam @NotNull final Long draftId) {
        LodgementBatchDto lodgementBatchDto = lodgementDraftService
                .getbatchDetails(draftId);
        return ResponseEntity.ok()
                .body(lodgementBatchDto);
    }

    /**
     * @param draftId draftId
     * @return {@link Resource}
     * @throws IOException ioException
     */
    @GetMapping(value = "/getLdgDraftAcknowledement", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> getLodgementDraftAcknowledgementLetter(@RequestParam @NotNull final Long draftId) throws IOException {
        final Resource resource = lodgementDraftService.generateAcknowledgementLetter(draftId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        httpHeaders.setContentLength(resource.contentLength());
        httpHeaders.setContentDispositionFormData("attachment", "ldg-ack-" + draftId + ".pdf");
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(resource);
    }


    /**
     * @param workflowId workflowId
     * @return {@link DispatchDto}
     */
    @GetMapping("/getDispatchDetails")
    public ResponseEntity<DispatchDto> getDispatchDetails(@RequestParam @NotNull final Long workflowId) {
        DispatchDto dispatchByWorkflowId = globalDispatchService
                .getDispatchByWorkflowId(workflowId);
        return ResponseEntity.ok().body(dispatchByWorkflowId);
    }

    /**
     * @param dispatchId dispatchId
     */
    @DeleteMapping("/dispatch/{dispatchId}")
    public void deleteDispatch(@PathVariable @NotNull final Long dispatchId) {
        globalDispatchService.deleteDispatch(dispatchId);
    }

    /**
     * @param dispatchDto {@link DispatchDto}
     * @return {@link DispatchDto}
     */
    @PostMapping("/saveDispatch")
    public ResponseEntity<DispatchDto>
    saveDispatch(@RequestBody @Validated final DispatchDto dispatchDto) {
        return ResponseEntity.ok()
                .body(globalDispatchService.saveDispatch(dispatchDto));
    }

    /**
     * @param dispatchDto {@link DispatchDto}
     * @return {@link DispatchDto}
     */
    @PostMapping("/updateDispatch")
    public ResponseEntity<DispatchDto>
    updateDispatch(@RequestBody @Validated final DispatchDto dispatchDto) {
        return ResponseEntity.ok()
                .body(globalDispatchService.updateDispatch(dispatchDto));
    }

}
