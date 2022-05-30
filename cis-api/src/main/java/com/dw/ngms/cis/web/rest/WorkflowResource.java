package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.persistence.domains.cart.RequesterInformation;
import com.dw.ngms.cis.persistence.domains.cart.SearchDetails;
import com.dw.ngms.cis.persistence.domains.workflow.WorkflowActionProductivityView;
import com.dw.ngms.cis.persistence.domains.workflow.WorkflowProcessData;
import com.dw.ngms.cis.persistence.projection.*;
import com.dw.ngms.cis.persistence.projection.workflow.*;
import com.dw.ngms.cis.service.dto.TextToDocDto;
import com.dw.ngms.cis.service.dto.user.UserNotificationDto;
import com.dw.ngms.cis.service.dto.workflow.*;
import com.dw.ngms.cis.service.workflow.WorkflowService;
import com.dw.ngms.cis.web.annotation.ApiPageable;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.response.DeleteResponse;
import com.dw.ngms.cis.web.response.JsonRawResponse;
import com.dw.ngms.cis.web.response.UpdateResponse;
import com.dw.ngms.cis.web.util.PaginationUtil;
import com.dw.ngms.cis.web.vm.AddDiariseDateVm;
import com.dw.ngms.cis.web.vm.workflow.*;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import java.util.Collections;

/**
 * @author : prateekgoel
 * @since : 23/12/20, Wed
 **/
@BaseResponse
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.API_BASE_MAPPING + "/workflow")
public class WorkflowResource {

    private final WorkflowService workflowService;

    /**
     * @param userId userId of logged In user
     * @return Collection<InboxProjection>
     */
    @GetMapping("/lists")
    @ApiPageable
    public ResponseEntity<Collection<InboxProjection>>
    loadInbox(@RequestParam @NotNull final Long userId,
              @ApiIgnore
              @PageableDefault(size = 300)
              @SortDefault(sort = "allocatedOn", direction = Sort.Direction.DESC) final Pageable pageable) {
        Page<InboxProjection> inboxProjections = workflowService.loadInbox(userId, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(inboxProjections);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(inboxProjections.getContent());
    }


    /**
     * @param userId userId of logged in user
     * @return Collection<DashboardRequestProjection>
     */
    @GetMapping("/listByUser")
    @ApiPageable
    public ResponseEntity<Collection<DashboardRequestProjection>>
    loadDashboardRequest(@RequestParam @NotNull final Long userId,
                         @ApiIgnore @SortDefault(sort = "workflowId", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<DashboardRequestProjection> dashboardRequestProjections = workflowService.loadDashBoardRequest(userId, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(dashboardRequestProjections);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(dashboardRequestProjections.getContent());
    }

    /**
     * @param userId userId of logged in user
     * @return Collection<DashboardRequestProjection>
     */
    @GetMapping("/queryByUser")
    @ApiPageable
    public ResponseEntity<Collection<DashboardRequestProjection>>
    loadDashboardQuery(@RequestParam @NotNull final Long userId,
                       @ApiIgnore @SortDefault(sort = "workflowId", direction = Sort.Direction.DESC) final Pageable pageable) {
        Page<DashboardRequestProjection> dashboardRequestProjections = workflowService.loadDashBoardQuery(userId, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(dashboardRequestProjections);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(dashboardRequestProjections.getContent());
    }

    /**
     * @param userId      userId of logged in user
     * @param referenceNo referenceNo.
     * @return DashboardRequestProjection
     */
    @GetMapping("/dashboardSearch")
    public ResponseEntity<DashboardRequestProjection>
    searchByReferenceNoAndUserId(@RequestParam @NotNull final Long userId,
                                 @RequestParam @NotNull final String referenceNo) {
        return ResponseEntity.ok().body(workflowService.searchByReferenceNoAndUserId(referenceNo, userId));
    }

    /**
     * @param referenceNo referenceNo
     * @return DashboardRequestProjection
     */
    @GetMapping("/seachByReference")
    public ResponseEntity<DashboardRequestProjection> searchByReferenceNo(@RequestParam @NotNull final String referenceNo) {
        return ResponseEntity.ok()
                .body(workflowService.searchByReferenceNo(referenceNo));
    }

    /**
     * @param workflowId workflowId
     * @return {@link WorkflowProjection}
     */
    @GetMapping("/")
    public ResponseEntity<WorkflowProjection> getWorkFlow(@RequestParam @NotNull final Long workflowId) {
        return ResponseEntity.ok()
                .body(workflowService.getWorkflow(workflowId));
    }

    /**
     * @param workflowId workflowId
     * @return <Collection<WorkflowTasksProjection>>
     */
    @GetMapping("/tasks")
    @ApiPageable
    public ResponseEntity<Collection<WorkflowTasksProjection>>
    loadTaskFlow(@RequestParam @NotNull final Long workflowId,
                 @ApiIgnore @SortDefault(sort = "postedOn", direction = Sort.Direction.DESC) final Pageable pageable) {

        Page<WorkflowTasksProjection> workflowTasksProjections = workflowService.loadTaskFlow(workflowId, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(workflowTasksProjections);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(workflowTasksProjections.getContent());
    }

    /**
     * @param workflowId workflowId
     * @return Collection<WorkflowDocuments>
     */
    @GetMapping("/documents")
    @ApiPageable
    public ResponseEntity<Collection<WorkflowDocuments>> getWorkflowDocument(@RequestParam @NotNull final Long workflowId,
                                                                             @ApiIgnore final Pageable pageable) {
        Page<WorkflowDocuments> workflowDocuments = workflowService.getWorkflowDocuments(workflowId, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(workflowDocuments);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(workflowDocuments.getContent());
    }

    /**
     * @param documentId documentId
     * @return Resource {@link Resource}
     * @throws IOException exception
     */
    @GetMapping(value = "/downloadSupportingDocs", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadWorkflowSupportingDocs(@RequestParam final Long documentId) throws IOException {
        Resource resource = workflowService.downloadSupportingDocs(documentId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentLength(resource.contentLength());
        httpHeaders.setContentDispositionFormData("attachment",
                resource.getFilename());
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(resource);
    }

    /**
     * @param userId userId of logged in user
     * @return Collection<InboxProjection>
     */
    @GetMapping("/list/reassign")
    public ResponseEntity<Collection<InboxProjection>>
    getAllWorkflowToReassign(@RequestParam @NotNull final Long userId,
                             @ApiIgnore @SortDefault(sort = "allocatedOn", direction = Sort.Direction.DESC) final Pageable pageable) {
        Page<InboxProjection> inboxProjections = workflowService.loadAllWorkflowForReassign(userId, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(inboxProjections);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(inboxProjections.getContent());
    }

    /**
     * @param reassignWorkflowDto {@link ReassignWorkflowDto}
     * @return {@link UpdateResponse}
     */
    @PostMapping("/reassign")
    ResponseEntity<UpdateResponse> reassignWorkflow(@RequestBody @Valid final ReassignWorkflowDto reassignWorkflowDto) {
        Boolean aBoolean = workflowService.reassignWorkflow(reassignWorkflowDto);
        UpdateResponse build = UpdateResponse.builder()
                .update(aBoolean)
                .build();
        return ResponseEntity.ok().body(build);
    }

    /**
     *
     * @param sendToSectionWorkflowDto sendToSectionWorkflowDto
     * @return UpdateResponse {@link UpdateResponse}
     */
    @PostMapping("/sendToSection")
    ResponseEntity<UpdateResponse>
    sendToSectionWorkflow(@RequestBody @Valid final SendToSectionWorkflowDto sendToSectionWorkflowDto) {
        Boolean aBoolean = workflowService.assignToSectionWorkflow(sendToSectionWorkflowDto);
        UpdateResponse build = UpdateResponse.builder()
                .update(aBoolean)
                .build();
        return ResponseEntity.ok().body(build);
    }

    /**
     *
     * @param workflowId workflowId
     * @return  RequesterInformation {@link RequesterInformation}
     */
    @GetMapping("/requesterInfo")
    public ResponseEntity<RequesterInformation> getRequesterInformation(@RequestParam @NotNull final Long workflowId) {
        return ResponseEntity.ok()
                .body(workflowService.getRequesterInformation(workflowId));
    }

    /**
     * @param addDiariseDateVm {{@link AddDiariseDateVm}
     * @return {@link UpdateResponse}
     */
    @PostMapping("/addToDiary")
    public ResponseEntity<UpdateResponse> addDiariseDate(@RequestBody @Valid final AddDiariseDateVm addDiariseDateVm) {
        Boolean aBoolean = workflowService.addDiariseDate(addDiariseDateVm);
        UpdateResponse build = UpdateResponse.builder()
                .update(aBoolean)
                .build();
        return ResponseEntity.ok().body(build);
    }

    /**
     * @param workflowExpediteTaskVm {@link WorkflowExpediteTaskVm}
     * @return {@link UpdateResponse}
     */
    @PostMapping("/expiditeTask")
    public ResponseEntity<UpdateResponse> expediteTask(@RequestBody @Valid final WorkflowExpediteTaskVm workflowExpediteTaskVm) {
        Boolean aBoolean = workflowService.expediteTask(workflowExpediteTaskVm);
        UpdateResponse build = UpdateResponse.builder()
                .update(aBoolean)
                .build();
        return ResponseEntity.ok().body(build);
    }

    /**
     * @return Collection of {@link WorkflowProcessMatrixDto}
     */
    @GetMapping("/priority")
    public ResponseEntity<Collection<WorkflowProcessMatrixDto>> getPriorityFlag() {
        return ResponseEntity.ok()
                .body(workflowService.getWorkflowPriorityFlag());
    }

    /**
     * @param cancelTaskVm {@link CancelTaskVm}
     * @return {@link UpdateResponse}
     */
    @PostMapping("/cancelTask")
    public ResponseEntity<UpdateResponse> cancelTask(@RequestBody @Valid final CancelTaskVm cancelTaskVm) {
        Boolean aBoolean = workflowService.cancelTask(cancelTaskVm);
        UpdateResponse build = UpdateResponse.builder()
                .update(aBoolean)
                .build();
        return ResponseEntity.ok().body(build);
    }

    /**
     * @param workflowId workflowId
     * @return Collection<ReferralProjection>
     */
    @GetMapping("/referral")
    @ApiPageable
    public ResponseEntity<Collection<ReferralProjection>> getAllReferrals(@NotNull final Long workflowId,
                                                                          @ApiIgnore final Pageable pageable) {
        Page<ReferralProjection> referrals = workflowService.getReferrals(workflowId, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(referrals);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(referrals.getContent());
    }

    /**
     * @param workflowId workflowId
     * @return Collection<WorkflowReferralInputProjection>>
     */
    @GetMapping("/referralInputData/{workflowId}")
    public ResponseEntity<InboxProjection>
    getReferralInputData(@PathVariable final Long workflowId) {
        return ResponseEntity.ok()
                .body(workflowService.getReferralInput(workflowId));
    }

    /**
     * @param uploadWorkflowDocsVm {@link UploadWorkflowDocsVm}
     * @return {@link WorkflowDocumentDto}
     */
    @PostMapping(value = "/uploadDocs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WorkflowDocumentDto> uploadSupportingDocs(
            @ModelAttribute @Valid UploadWorkflowDocsVm uploadWorkflowDocsVm) {
        return ResponseEntity.ok()
                .body(workflowService.uploadSupportingDocs(uploadWorkflowDocsVm));
    }


    /**
     * @param documentId documentId
     * @param workflowId workflowId
     * @return DeleteResponse
     */
    @GetMapping("/deleteDocs")
    public ResponseEntity<DeleteResponse> deleteWorkflowDocs(@RequestParam @NotNull final Long documentId,
                                                             @RequestParam final Long workflowId) {
        Boolean aBoolean = workflowService.deleteWorkflowDocs(documentId, workflowId);
        DeleteResponse build = DeleteResponse.builder()
                .isDeleted(aBoolean)
                .build();
        return ResponseEntity.ok().body(build);
    }

    /**
     * @param workflowId workflowId
     * @return Collection<String>
     */
    @GetMapping("/requestItem")
    public ResponseEntity<JsonRawResponse> getInformationRequestItem(@RequestParam @NotNull final Long workflowId) {
        JsonRawResponse build = JsonRawResponse.builder()
                .json(workflowService.getRequestInformationItem(workflowId))
                .build();
        return ResponseEntity.ok()
                .body(build);
    }

    /**
     * @param workflowId workflowId
     * @return JsonRawResponse {@link JsonRawResponse}
     */
    @GetMapping("/requestWorkflowItem")
    public ResponseEntity<WorkflowProcessData> getWorkflowBasedItem(@RequestParam @NotNull final Long workflowId) {
        return ResponseEntity.ok()
                .body(workflowService.getWorkflowItem(workflowId));
    }


    @PostMapping("/notifications")
    private void notificationForWorkflowRequest(@RequestBody @Valid final
                                                ProcessNotificationsVm processNotificationsVm) throws Exception {
        workflowService.workflowNotification(processNotificationsVm);
    }

    /**
     * @param workflowId workflowId
     * @return processData
     */
    @GetMapping("/requestTypeData")
    public ResponseEntity<JsonRawResponse> getWorkflowDataForRequestType(@RequestParam @NotNull final Long workflowId) {
        JsonRawResponse build = JsonRawResponse.builder()
                .json(Collections.singletonList(workflowService.getNotifyManagerAndQueryAndReferralData(workflowId)))
                .build();
        return ResponseEntity.ok()
                .body(build);
    }

    /**
     * @param paymentDto {@link PaymentDto}
     * @return {@link PaymentDto}
     */
    @PostMapping(value = "/addProofOfPayment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PaymentDto> uploadProofOfPayment(@ModelAttribute @Valid
                                                                   PaymentDto paymentDto) {
        return ResponseEntity.ok()
                .body(workflowService.uploadProofOfPayment(paymentDto));
    }

    /**
     * @param workflowId     workflowId
     * @param documentTypeId documentTypeId
     * @return WorkflowDocumentDto
     */
    @GetMapping("/getPaymentDocumentInfo")
    public ResponseEntity<WorkflowDocumentDto> getPaymentDocument(@RequestParam final Long workflowId,
                                                                  @RequestParam final Long documentTypeId) {
        return ResponseEntity.ok()
                .body(workflowService.getPaymentDocument(workflowId, documentTypeId));
    }

    /**
     * @param documentId documentId
     * @return Resource
     */
    @GetMapping(value = "/getPaymentDocument", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getPaymentFile(final Long documentId) {
        return ResponseEntity.ok()
                .body(workflowService.getPaymentFile(documentId));

    }

    /**
     * @param workflowId workflowId
     * @return {@link PaymentDto}
     */
    @GetMapping("/payment")
    public ResponseEntity<PaymentDto> getPaymentInfo(@RequestParam @NotNull final Long workflowId) {
        return ResponseEntity.ok()
                .body(workflowService.getPaymentInfoByWorkflowId(workflowId));
    }

    /**
     * @param workflowMarkAsPendingVm {@link WorkflowMarkAsPendingVm}
     * @return {@link UpdateResponse}
     */
    @PostMapping("/markPending")
    public ResponseEntity<UpdateResponse> markWorkflowPending(@RequestBody @NotNull final WorkflowMarkAsPendingVm workflowMarkAsPendingVm) {
        Boolean aBoolean = workflowService.markWorkflowPending(workflowMarkAsPendingVm);
        UpdateResponse build = UpdateResponse.builder()
                .update(aBoolean)
                .build();

        return ResponseEntity.ok().body(build);
    }

    /**
     * @param workflowChangeProvinceVM {@link WorkflowChangeProvinceVM}
     * @return {@link UpdateResponse}
     */
    @PostMapping("/changeProvince")
    public ResponseEntity<UpdateResponse> changeWorkflowProvince(
            @RequestBody @Valid final WorkflowChangeProvinceVM workflowChangeProvinceVM) {
        Boolean aBoolean = workflowService.changeWorkflowProvince(workflowChangeProvinceVM);
        UpdateResponse build = UpdateResponse.builder()
                .update(aBoolean)
                .build();

        return ResponseEntity.ok().body(build);

    }

    /**
     * @return Collection<WorkflowProcessProjection> {@link WorkflowProcessProjection}
     */
    @GetMapping("/simulateProcess")
    public ResponseEntity<Collection<WorkflowProcessProjection>> getWorkflowProcesses() {
        return ResponseEntity.ok()
                .body(workflowService.getWorkflowProcess());
    }

    @GetMapping("/productivity")
    public void createProductivityForWorkflow(@RequestParam final Long workflowId) {
        workflowService.createProductivityForWorkflow(workflowId);
    }

    /**
     * @param workflowId workflowId
     * @param pageable   {@link Pageable}}
     * @return Collection<WorkflowActionProductivityView> {@link WorkflowActionProductivityView}
     */
    @GetMapping("/taskDurationProductivityDetails")
    @ApiPageable
    public ResponseEntity<Collection<WorkflowActionProductivityView>>
    getTaskDurationDetails(@RequestParam final Long workflowId,
                           @ApiIgnore
                           @SortDefault(sort = "productivityDate", direction = Sort.Direction.ASC) final Pageable pageable) {
        Page<WorkflowActionProductivityView> workflowActionTaskDuration = workflowService
                .getWorkflowActionTaskDuration(workflowId, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(workflowActionTaskDuration);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(workflowActionTaskDuration.getContent());

    }

    /**
     * @param userId userId of logged in user
     * @return Collection<DashboardRequestProjection>
     */
    @GetMapping("/userNotifications")
    @ApiPageable
    public ResponseEntity<Collection<UserNotificationDto>>
    getUserNotifications(@RequestParam @NotNull final Long userId, @ApiIgnore final Pageable pageable) {
        Page<UserNotificationDto> userNotificationDtos = workflowService.getUserNotifications(userId, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(userNotificationDtos);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(userNotificationDtos.getContent());
    }

    /**
     * @param referenceNumber referenceNumber
     * @return Collection<String>
     */
    @GetMapping("/searchDetailsByReferenceNo")
    public ResponseEntity<Collection<SearchDetails>> getCartDataFromReferenceNumber(
            @RequestParam @NotNull final String referenceNumber) {
        return ResponseEntity.ok()
                .body(workflowService.getSearchDataFromReferenceNo(referenceNumber));
    }

    /**
     * @param textToDocDto {@link TextToDocDto}
     * @return {@link WorkflowDocumentDto}
     */
    @PostMapping("/uploadText")
    public ResponseEntity<WorkflowDocumentDto> saveTextAsDoc(@RequestBody @Valid TextToDocDto textToDocDto) {

        return ResponseEntity.ok()
                .body(workflowService.saveTextAsDoc(textToDocDto));

    }

    /**
     * @param lpiNoteDto {@link LpiNoteDto}
     * @return {@link LpiNoteDto}
     */
    @PostMapping("/lpiNotes")
    public ResponseEntity<LpiNoteDto> saveLpiNote(@RequestBody @Valid final LpiNoteDto lpiNoteDto) {
        return ResponseEntity.ok()
                .body(workflowService.saveLpiNotes(lpiNoteDto));
    }

    /**
     * @param lpi lpi
     * @return Collection<LpiNoteDto>
     */
    @GetMapping("/lpiNotes")
    public ResponseEntity<Collection<NotesTimelineProjection>> getLpiNotes(final String lpi) {
        return ResponseEntity.ok()
                .body(workflowService.getLpiNotes(lpi));
    }


    /**
     * @param closeTaskVm {@link CloseTaskVm}
     * @return 1/0
     */
    @PostMapping("/closeTask")
    public ResponseEntity<UpdateResponse> closeWorkflowTask(@RequestBody final CloseTaskVm closeTaskVm) {
        final Boolean aBoolean = workflowService.closeWorkflowTask(closeTaskVm);
        final UpdateResponse build = UpdateResponse.builder().update(aBoolean).build();
        return ResponseEntity.ok().body(build);
    }

    /**
     * @param reOpenTaskVm {@link ReOpenTaskVm}
     * @return 1/0
     */
    @PostMapping("/reOpenTask")
    public ResponseEntity<UpdateResponse> reOpenTask(@RequestBody final ReOpenTaskVm reOpenTaskVm) {
        final Boolean aBoolean = workflowService.reOpenTask(reOpenTaskVm);
        final UpdateResponse build = UpdateResponse.builder().update(aBoolean).build();
        return ResponseEntity.ok().body(build);
    }

    /**
     * Save Workflow user feedback.
     * @param workflowUserFeedbackDto {@link WorkflowUserFeedbackDto}
     * @return {@link WorkflowUserFeedbackDto}
     */
    @PostMapping("/saveWorkflowUserFeedback")
    ResponseEntity<WorkflowUserFeedbackDto> saveWorkflowUserFeedback(@RequestBody @Validated final WorkflowUserFeedbackDto workflowUserFeedbackDto) {
        return ResponseEntity
                .ok()
                .body(workflowService.saveWorkflowUserFeedback(workflowUserFeedbackDto));
    }

    @GetMapping("/applicantWorkflowFeedbacks/{applicantId}")
    public ResponseEntity<Collection<WorkflowUserFeedbackDto>> getApplicantFeedbacks(@PathVariable @NotNull final Long applicantId) {
        return ResponseEntity.ok()
                .body(workflowService.getWorkflowUserFeedback(applicantId));
    }

    /**
     * get applicant feedback for yearly status
     * @param workflowUserFeedbackStatusDto {@link WorkflowUserFeedbackStatusDto}
     * @return Collection<WorkflowUserFeedbackProjection>
     */
    @PostMapping("/getWorkflowUserFeedbackYearlyStatus")
    ResponseEntity<Collection<WorkflowUserFeedbackProjection>>
    saveWorkflowUserFeedback(@RequestBody @Validated
                             final WorkflowUserFeedbackStatusDto workflowUserFeedbackStatusDto) {
        return ResponseEntity
                .ok()
                .body(workflowService.getWorkflowUserFeedbackYearlyStatus(workflowUserFeedbackStatusDto));
    }
    /**
     * @param RecordId RecordId
     * @return Collection<WorkflowParcelProjection>>
     */


    @GetMapping("/workflowParcel/{RecordId}")
    public ResponseEntity<Collection<WorkflowParcelProjection>>
    getParcelData(@PathVariable final Long RecordId) {
        Collection<WorkflowParcelProjection> projection =
                workflowService.getParcelData(RecordId);
        return ResponseEntity.ok()
                .body(projection);

    }

    /**
     *
     * @param workflowId workflowId
     * @return Collection<WorkflowProcessTimelineProjection>
     */
    @GetMapping("/workflowProcessTimeline")
    public ResponseEntity<Collection<WorkflowProcessTimelineProjection>>
    getWorkflowProcessTimeline(@RequestParam @NotNull final Long workflowId) {
        Collection<WorkflowProcessTimelineProjection> workflowProcessTimeline = workflowService
                .getWorkflowProcessTimeline(workflowId);
        return ResponseEntity.ok()
                .body(workflowProcessTimeline);
    }
}
