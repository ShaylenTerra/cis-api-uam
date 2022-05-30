package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.dto.SgdataParcelsDto;
import com.dw.ngms.cis.persistence.projection.reservation.ReservationDraftListingProjection;
import com.dw.ngms.cis.persistence.projection.reservation.ReservationRecordIdVerificationProjection;
import com.dw.ngms.cis.persistence.projection.reservation.ReservationTransferListProjection;
import com.dw.ngms.cis.service.dto.reservation.*;
import com.dw.ngms.cis.service.reservation.ReservationDraftService;
import com.dw.ngms.cis.service.reservation.ReservationDraftStepsService;
import com.dw.ngms.cis.web.annotation.ApiPageable;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.util.PaginationUtil;
import com.dw.ngms.cis.web.vm.reservation.ReservationNotificationVm;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
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
@RequestMapping(AppConstants.API_BASE_MAPPING + "/reservation")
public class ReservationResource {


    private final ReservationDraftService reservationDraftService;

    private final ReservationDraftStepsService reservationDraftStepsService;

    /**
     * @param pageable pageable
     * @return Collection<ReservationDraftDto>
     */
    @JsonView(ReservationDraftDtoView.Public.class)
    @GetMapping("/getDraft")
    @ApiPageable
    public ResponseEntity<Collection<ReservationDraftDto>> getAllReservationDraft(
            @RequestParam @NotNull final Long processId,
            @ApiIgnore @SortDefault(sort = "dated", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ReservationDraftDto> allReservationDraft = reservationDraftService.getAllReservationDraft(processId,pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(allReservationDraft);
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(allReservationDraft.getContent());
    }

    /**
     * @param draftId draftId
     * @return ReservationDraftDto
     */
    @JsonView({ReservationDraftDtoView.Internal.class})
    @GetMapping("/draft/{draftId}")
    public ResponseEntity<ReservationDraftDto> getReservationDraft(@PathVariable @NotNull final Long draftId) {
        ReservationDraftDto reservationDraft = reservationDraftService.getDraft(draftId);
        return ResponseEntity
                .ok(reservationDraft);
    }

    /**
     * @param draftId draftId
     */
    @DeleteMapping("/draft/{draftId}")
    public void deleteDraft(@PathVariable @NotNull final Long draftId) {
        reservationDraftService.deleteDraft(draftId);
    }

    /**
     * @param reservationDraftDto {@link ReservationDraftDto}
     * @return {@link ReservationDraftDto}
     */
    @JsonView(ReservationDraftDtoView.Public.class)
    @PostMapping("/saveDraft")
    public ResponseEntity<ReservationDraftDto>
    saveReservationDraft(@RequestBody @Validated
                         @JsonView(ReservationDraftDtoView.Public.class) final ReservationDraftDto reservationDraftDto) {
        return ResponseEntity.ok()
                .body(reservationDraftService.saveReservationDraft(reservationDraftDto));
    }

    /**
     * @param reservationDraftDto reservationDraftDto
     * @return {@link ReservationDraftDto}
     */
    @JsonView(ReservationDraftDtoView.Internal.class)
    @PostMapping("/updateDraft")
    public ResponseEntity<ReservationDraftDto>
    updateReservationDraft(@RequestBody @Validated
                           @JsonView(ReservationDraftDtoView.Internal.class) final ReservationDraftDto reservationDraftDto) {
        return ResponseEntity.ok()
                .body(reservationDraftService.updateReservationDraft(reservationDraftDto));
    }

    /**
     * @param reservationDraftStepsDto {@link ReservationDraftStepsDto}
     * @return {@link ReservationDraftStepsDto}
     */
    @PostMapping("/saveDraftSteps")
    public ResponseEntity<ReservationDraftStepsDto>
    saveReservationDraftSteps(@RequestBody final ReservationDraftStepsDto reservationDraftStepsDto) {
        return ResponseEntity.ok()
                .body(reservationDraftStepsService.saveReservationDraftSteps(reservationDraftStepsDto));
    }


    /**
     * @param reservationDraftDocumentsDto {@link ReservationDraftDocumentsDto}
     * @return {@link  ReservationDraftDocumentsDto}
     */
    @PostMapping(value = "/addAnnexure", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ReservationDraftDocumentsDto>
    addAnnexure(@ModelAttribute @Valid final ReservationDraftDocumentsDto reservationDraftDocumentsDto) {
        ReservationDraftDocumentsDto reservationDraftDocumentsDto1 = reservationDraftService
                .saveReservationDocument(reservationDraftDocumentsDto);
        return ResponseEntity.ok(reservationDraftDocumentsDto1);
    }

    /**
     * @param draftId draftId
     * @return Collection<ReservationDraftDocumentsDto>
     */
    @GetMapping("/getAnnexure")
    public ResponseEntity<Collection<ReservationDraftDocumentsDto>>
    getAnnexure(@RequestParam @NotNull final Long draftId) {
        Collection<ReservationDraftDocumentsDto> reservationDraftDocumentsDtos = reservationDraftService
                .getDraftDocuments(draftId);
        return ResponseEntity.ok().body(reservationDraftDocumentsDtos);
    }

    /**
     * @param documentId documentId
     */
    @DeleteMapping(value = "/deleteAnnexure")
    public void removeAnnexure(@RequestParam final Long documentId) {
        reservationDraftService.removeAnnuxure(documentId);
    }

    /**
     * @param documentId documentId
     * @return {@link Resource}
     */
    @GetMapping(value = "/getAnnexure/{documentId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getAnnuxure(@PathVariable final Long documentId) throws IOException {
        Resource annuxure = reservationDraftService.getAnnuxure(documentId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentLength(annuxure.contentLength());
        httpHeaders.setContentDispositionFormData("attachment",
                annuxure.getFilename());
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(annuxure);
    }

    /**
     * @param searchTerm searchTerm
     * @param provinceId provinceId
     * @param locationId locationId
     * @return Collection<SgdataParcelsDto>
     */
    @GetMapping("/getRequest")
    public ResponseEntity<Collection<SgdataParcelsDto>>
    searchReservationDraftStepRequest(@RequestParam final String searchTerm,
                                      @RequestParam final Long provinceId,
                                      @RequestParam(required = false, defaultValue = "0") final Long locationId) {
        Collection<SgdataParcelsDto> sgdataParcelsDtos = reservationDraftService
                .searchReservationRequests(searchTerm, provinceId, locationId);
        return ResponseEntity.ok(sgdataParcelsDtos);
    }

    /**
     * @param reservationDraftRequestDto {@link ReservationDraftRequestDto}
     * @return reservationDraftRequestDto {@link ReservationDraftRequestDto}
     */
    @PostMapping("/addDraftRequest")
    public ResponseEntity<ReservationDraftRequestDto> addReservationDraftRequest(
            @RequestBody @Validated final ReservationDraftRequestDto reservationDraftRequestDto) {
        ReservationDraftRequestDto savedReservationDraftRequest = reservationDraftService
                .addReservationDraftRequest(reservationDraftRequestDto);
        return ResponseEntity.ok(savedReservationDraftRequest);
    }

    /**
     * @param stepId stepId
     * @return Collection<ReservationDraftRequestDto>
     */
    @GetMapping("/getDraftRequest")
    public ResponseEntity<Collection<ReservationDraftRequestDto>>
    getAllReservationDraftRequest(@RequestParam @NotNull final Long stepId) {
        Collection<ReservationDraftRequestDto> reservationDraftRequestDtos = reservationDraftService
                .getAllReservationRequestForStepId(stepId);
        return ResponseEntity.ok(reservationDraftRequestDtos);
    }

    /**
     * @param draftRequestId draftRequestId
     */
    @DeleteMapping("/deleteDraftRequest")
    public void deleteReservationDraft(@RequestParam @NotNull final Long draftRequestId) {
        reservationDraftService.deleteDraftRequest(draftRequestId);
    }

    /**
     * @param draftId draftId
     * @return Collection<ReservationDraftStepsDto>
     */
    @GetMapping("/getDraftSteps")
    public ResponseEntity<Collection<ReservationDraftStepsDto>> getAllDraftSteps(@RequestParam @NotNull final Long draftId) {
        Collection<ReservationDraftStepsDto> reservationDraftStepsDtos = reservationDraftService.getAllDraftSteps(draftId);
        return ResponseEntity.ok()
                .body(reservationDraftStepsDtos);
    }

    /**
     * @param reservationDraftStepsDto {@link ReservationDraftStepsDto}
     * @return {@link ReservationDraftStepsDto}
     */
    @JsonView(ReservationDraftStepsDtoViews.Internal.class)
    @PostMapping("/addDraftStep")
    public ResponseEntity<ReservationDraftStepsDto>
    addDraftSteps(
            @RequestBody @Validated
            @JsonView(ReservationDraftStepsDtoViews.Public.class) final ReservationDraftStepsDto reservationDraftStepsDto) {
        ReservationDraftStepsDto reservationDraftStepsDto1 = reservationDraftService
                .addDraftSteps(reservationDraftStepsDto);
        return ResponseEntity.ok().body(reservationDraftStepsDto1);
    }

    @DeleteMapping("/deleteDraftSteps/{stepId}")
    public void deleteDraftStep(@PathVariable @NotNull final Long stepId) {
        reservationDraftService.deleteDraftStep(stepId);
    }

    /**
     * @param stepId stepId
     * @return Collection<ReservationDraftRequestOutcomeDto>
     */
    @GetMapping("/getStepsOutcome/{stepId}")
    public ResponseEntity<Collection<ReservationDraftRequestOutcomeDto>>
    getDraftRequestOutcome(@PathVariable final Long stepId) {
        Collection<ReservationDraftRequestOutcomeDto> draftRequestOutcome = reservationDraftService
                .getDraftRequestOutcome(stepId);
        return ResponseEntity.ok().body(draftRequestOutcome);
    }

    /**
     * @param reservationDraftStepsDto {@link ReservationDraftStepsDto}
     * @return Collection<ReservationDraftRequestOutcomeDto>
     */
    @PostMapping("/processDraftStepsRequests")
    public ResponseEntity<Collection<ReservationDraftRequestOutcomeDto>>
    processDraftStepsRequest(@RequestBody @Validated
                             @JsonView(ReservationDraftStepsDtoViews.Internal.class) final ReservationDraftStepsDto reservationDraftStepsDto) {
        Collection<ReservationDraftRequestOutcomeDto> reservationDraftRequestOutcomeDtos = reservationDraftService
                .processDraftStepsRequest(reservationDraftStepsDto);

        return ResponseEntity.ok().body(reservationDraftRequestOutcomeDtos);
    }

    /**
     * @param userId   userId
     * @param processId processId
     * @param pageable {@link Pageable}
     * @return Collection<ReservationDraftListingProjection>
     */
    @GetMapping("/listReservationDraft")
    @ApiPageable
    public ResponseEntity<Collection<ReservationDraftListingProjection>>
    getReservations(@RequestParam @NotNull final Long userId,
                    @RequestParam @NotNull final Long processId,
                    @ApiIgnore @SortDefault(sort = "workflowId", direction = Sort.Direction.DESC) final Pageable pageable) {
        Page<ReservationDraftListingProjection> allReservationDraftForUserId = reservationDraftService
                .getAllReservationDraftForUserId(userId,processId, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(allReservationDraftForUserId);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(allReservationDraftForUserId.getContent());
    }

    /**
     * @param draftId    draftId
     * @param workflowId workflowId
     */
    @GetMapping("/checkoutDraft")
    public void checkoutDraft(@RequestParam @NotNull final Long draftId,
                              @RequestParam @NotNull final Long workflowId) {
        reservationDraftService.checkoutDraft(draftId, workflowId);
    }

    /**
     * @param workflowId workflowId
     * @return {@link ReservationDraftDto}
     */
    @GetMapping("/draft")
    public ResponseEntity<ReservationDraftDto> getReservationDraftByWorkflowId(@RequestParam
                                                                               @NotNull final Long workflowId) {
        ReservationDraftDto reservationDraftDto = reservationDraftService
                .getReservationDraftByWorkflowId(workflowId);

        return ResponseEntity.ok().body(reservationDraftDto);
    }

    /**
     * @param draftId draftId
     */
    @GetMapping("/generateNumbering")
    public void generateNumberingForLandParcel(@RequestParam final Long draftId) {
        reservationDraftService.landPartialPortionIssue(draftId);
    }

    /**
     * @param draftId draftId
     * @return Collection<ReservationConditionDto>
     */
    @GetMapping("/getResCondition")
    public ResponseEntity<Collection<ReservationConditionDto>>
    getReservationConditions(@RequestParam @NotNull final Long draftId) {
        Collection<ReservationConditionDto> reservationConditionDtos = reservationDraftService
                .getReservationConditionByDraftId(draftId);
        return ResponseEntity.ok()
                .body(reservationConditionDtos);
    }

    /**
     * @param reservationConditionDto {@link ReservationConditionDto}
     * @return {@link ReservationConditionDto}
     */
    @PostMapping("/saveResCond")
    public ResponseEntity<ReservationConditionDto>
    saveReservationCondition(@RequestBody @Validated final ReservationConditionDto reservationConditionDto) {
        ReservationConditionDto reservationConditionDto1 = reservationDraftService
                .saveReservationCondition(reservationConditionDto);
        return ResponseEntity.ok().body(reservationConditionDto1);
    }

    /**
     * @param conditionId conditionId
     */
    @DeleteMapping("/deleteResCond")
    public void deleteReservationCondition(final Long conditionId) {
        reservationDraftService.deleteReservationCondition(conditionId);
    }


    /**
     * @param draftId draftId
     * @return {@link Resource}
     * @throws IOException IOException
     */
    @GetMapping(value = "/downloadAckLetter", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> generateAcknowledgementLetter(@RequestParam @NotNull final Long draftId)
            throws IOException {
        Resource resource = reservationDraftService.generateAcknowledgementLetter(draftId);
        HttpHeaders httpHeaders = new HttpHeaders();
        if (null != resource)
            httpHeaders.setContentLength(resource.contentLength());

        httpHeaders.setContentDispositionFormData("attachment",
                resource.getFilename());
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(resource);
    }

    /**
     * @param reservationNotificationVm {@link ReservationNotificationVm}
     */
    @PostMapping("/notifyForReservation")
    public void notifyForReservation(@RequestBody @Valid final ReservationNotificationVm reservationNotificationVm) {
        reservationDraftService.notifyForReservation(reservationNotificationVm);
    }

    /**
     * @param recordId recordId
     * @return Collection<ReservationRecordIdVerificationProjection>
     */
    @GetMapping("/verifyRecord/{recordId}")
    public ResponseEntity<Collection<ReservationRecordIdVerificationProjection>> verifyRecord(@PathVariable final Long recordId) {
        Collection<ReservationRecordIdVerificationProjection> reservationRecordIdVerificationProjections =
                reservationDraftService.verifyRecord(recordId);
        return ResponseEntity.ok()
                .body(reservationRecordIdVerificationProjections);
    }

    /**
     * @param pageable {@link Pageable}
     * @return Collection<ReservationDraftListingProjection>
     */
    @GetMapping("/listReservationTransfer")
    @ApiPageable
    public ResponseEntity<Collection<ReservationTransferListProjection>>
    getReservationTransfers(@ApiIgnore @SortDefault(sort = "outcomeId", direction = Sort.Direction.DESC) final Pageable pageable) {
        Page<ReservationTransferListProjection> allReservationTransfers = reservationDraftService
                .getAllReservationTransfers(pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(allReservationTransfers);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(allReservationTransfers.getContent());
    }

    /**
     *
     * @param draftId draftId
     * @param outcomeId outcomeId
     * @return {@link  ReservationDraftTransferDto}
     */
    @GetMapping("/addDraftToTransfer")
    public ResponseEntity<Collection<ReservationTransferListProjection>> addDraftToTransfer(@RequestParam @NotNull final Long draftId,
                                                @RequestParam @NotNull final Long outcomeId){
        Collection<ReservationTransferListProjection> reservationTransferListProjections = reservationDraftService
                .addDraftToTransfer(draftId, outcomeId);
        return ResponseEntity.ok()
                .body(reservationTransferListProjections);
    }

    /**
     *
     * @param transferId transferId
     */
    @DeleteMapping("/deleteTransferDraft")
    public void deleteDraftTransfer(@RequestParam final Long transferId){
        reservationDraftService.deleteTransferDraft(transferId);
    }

    @GetMapping("/getAllDraftTransfer")
    public ResponseEntity<Collection<ReservationTransferListProjection>>
    getAllTransferForDraftId(@RequestParam final Long draftId) {
        Collection<ReservationTransferListProjection> reservationDraftTransferDtos = reservationDraftService
                .getAllReservationDraftTransfer(draftId);
        return ResponseEntity.ok()
                .body(reservationDraftTransferDtos);
    }


}
