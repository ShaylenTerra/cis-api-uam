package com.dw.ngms.cis.service.workflow;

import com.dw.ngms.cis.enums.ProcessTemplateType;
import com.dw.ngms.cis.enums.StorageContext;
import com.dw.ngms.cis.enums.TransactionType;
import com.dw.ngms.cis.persistence.domains.Payment;
import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.domains.cart.CartData;
import com.dw.ngms.cis.persistence.domains.cart.CartJsonData;
import com.dw.ngms.cis.persistence.domains.cart.RequesterInformation;
import com.dw.ngms.cis.persistence.domains.cart.SearchDetails;
import com.dw.ngms.cis.persistence.domains.user.UserRole;
import com.dw.ngms.cis.persistence.domains.workflow.*;
import com.dw.ngms.cis.persistence.projection.WorkflowDocuments;
import com.dw.ngms.cis.persistence.projection.*;
import com.dw.ngms.cis.persistence.projection.workflow.*;
import com.dw.ngms.cis.persistence.repository.PaymentRepository;
import com.dw.ngms.cis.persistence.repository.UserRepository;
import com.dw.ngms.cis.persistence.repository.cart.CartDataRepository;
import com.dw.ngms.cis.persistence.repository.cart.CartRepository;
import com.dw.ngms.cis.persistence.repository.user.UserNotificationRepository;
import com.dw.ngms.cis.persistence.repository.workflow.*;
import com.dw.ngms.cis.pubsub.AppEventPublisher;
import com.dw.ngms.cis.service.FileStorageService;
import com.dw.ngms.cis.service.UserService;
import com.dw.ngms.cis.service.dto.TextToDocDto;
import com.dw.ngms.cis.service.dto.user.UserNotificationDto;
import com.dw.ngms.cis.service.dto.workflow.*;
import com.dw.ngms.cis.service.mapper.*;
import com.dw.ngms.cis.service.mapper.user.UserNotificationMapper;
import com.dw.ngms.cis.service.report.ProductivityReportGenerator;
import com.dw.ngms.cis.utilities.CartUtils;
import com.dw.ngms.cis.utilities.FileUtils;
import com.dw.ngms.cis.utilities.WorkflowUtils;
import com.dw.ngms.cis.web.annotation.ApiPageable;
import com.dw.ngms.cis.web.vm.AddDiariseDateVm;
import com.dw.ngms.cis.web.vm.workflow.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : prateekgoel
 * @since : 22/12/20, Tue
 **/
@Service
@Slf4j
@AllArgsConstructor
public class WorkflowService {

    private final WorkflowRepository workflowRepository;

    private final TransactionsRepository transactionsRepository;

    private final WorkflowActionRepository workflowActionRepository;

    private final WorkflowNotesRepository workflowNotesRepository;

    private final WorkflowProcessMatrixRepository workflowProcessMatrixRepository;

    private final WorkflowProcessMatrixMapper workflowProcessMatrixMapper;

    private final CartRepository cartRepository;

    private final CartDataRepository cartDataRepository;

    private final FileStorageService fileStorageService;

    private final WorkflowDocumentRepository workflowDocumentRepository;

    private final WorkflowDocumentMapper workflowDocumentMapper;

    private final UserService userService;

    private final AppEventPublisher publisher;

    private final PaymentRepository paymentRepository;

    private final PaymentMapper paymentMapper;

    private final WorkflowProcessRepository workflowProcessRepository;

    private final ProductivityReportGenerator productivityReportGenerator;

    private final WorkflowActionProductivityRepository workflowActionProductivityRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final UserNotificationMapper userNotificationMapper;
    private final UserRepository userRepository;

    private final ObjectMapper mapper;

    private final LpiNotesRepository lpiNotesRepository;

    private final LpiNotesMapper lpiNotesMapper;

    private final CartUtils cartUtils;

    private final ProcessNotificationFactory processNotificationFactory;

    private final WorkflowUtils workflowUtils;

    private final WorkflowUserFeedbackRepository workflowUserFeedbackRepository;

    private final WorkflowUserFeedbackMapper workflowUserFeedbackMapper;

    /**
     * @param userId userId of logged in user
     * @return Collection<InboxProjection>
     */
    public Page<InboxProjection> loadInbox(final Long userId, final Pageable pageable) {
        log.debug(" fetching inbox details for userId {}", userId);
        final User userById = userService.getUserById(userId);
        if (null != userById) {
            final Set<UserRole> userRoles = userById.getUserRoles();
            final Optional<Long> first = userRoles.stream()
                    .filter(userRole -> userRole.getIsPrimary().equals(1L))
                    .map(UserRole::getRoleId)
                    .findFirst();
            if (first.isPresent() && first.get().equals(41L)) {
                return workflowRepository.loadInboxForNationalAdmin(pageable);
            }

            // check for provincial admin
            List<Long> provinceIds = userRoles.stream()
                    .filter(userRole -> userRole.getRoleId().equals(35L))
                    .map(UserRole::getProvinceId)
                    .collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(provinceIds)) {

                return workflowRepository.loadInboxForProvincialAdmin(userId,provinceIds,pageable);
            }

            // check for other users
            return workflowRepository.loadInboxByUsers(userId, pageable);

        }
        return null;
    }

    /**
     * @param userId userId of logged in user
     * @return InboxProjection
     */
    public Page<InboxProjection> loadAllWorkflowForReassign(final Long userId, final Pageable pageable) {
        log.debug(" fetching inbox details for userId {}", userId);
        return workflowRepository.loadWorkflowForReassign(userId, pageable);
    }

    /**
     * @param userId userId of logged in user
     * @return Collection<DashboardRequestProjection>
     */
    @ApiPageable
    public Page<DashboardRequestProjection> loadDashBoardRequest(final Long userId, final Pageable pageable) {
        log.debug(" getting dashboard requests for userId {} ", userId);
        return workflowRepository.loadRequestForUser(userId, pageable);
    }

    /**
     * @param userId userId of logged in user
     * @return Collection<DashboardRequestProjection>
     */
    public Page<DashboardRequestProjection> loadDashBoardQuery(final Long userId, final Pageable pageable) {
        log.debug(" getting dashboard requests for userId {} ", userId);
        return workflowRepository.loadQueryForUser(userId, pageable);
    }

    /**
     * @param referenceNo referenceNo
     * @param userId      userId of logged in user
     * @return {@link DashboardRequestProjection}
     */
    public DashboardRequestProjection searchByReferenceNoAndUserId(final String referenceNo, final Long userId) {
        String refNo = StringUtils.upperCase(StringUtils.deleteWhitespace(referenceNo));
        log.debug(" searching for referenceNo [{}]", refNo);
        return workflowRepository.searchByReferenceNoAndUserId(userId, refNo);
    }

    /**
     * @param referenceNo referenceNo.
     * @return {@link DashboardRequestProjection}
     */
    public DashboardRequestProjection searchByReferenceNo(final String referenceNo) {
        String refNo = StringUtils.upperCase(StringUtils.deleteWhitespace(referenceNo));
        log.debug(" searching for referenceNo [{}]", refNo);
        return workflowRepository.findUsingReferenceNumber(refNo);
    }

    /**
     * @param workflowId requested workflowId
     * @return WorkflowProjection
     */
    public WorkflowProjection getWorkflow(final Long workflowId) {
        log.debug(" fetching workflow for id {}", workflowId);
        return workflowRepository.loadWorkflow(workflowId);
    }

    /**
     * @param workflowId workflowId
     * @return Collection<TaskFlowProjection>
     */
    public Page<WorkflowTasksProjection> loadTaskFlow(final Long workflowId, final Pageable pageable) {
        log.debug(" fetching task flow for workflowId {} ", workflowId);
        return workflowRepository.getTasksForWorkflow(workflowId, pageable);
    }

    /**
     * @param workflowId workflowId
     * @return Collection<WorkflowDocuments>
     */
    public Page<WorkflowDocuments> getWorkflowDocuments(final Long workflowId, final Pageable pageable) {
        log.debug(" getting supporting documents for workflowId {}  ", workflowId);
        return workflowRepository.getDocumentsForWorkflow(workflowId, pageable);
    }

    /**
     * @param reassignWorkflowDto {@link ReassignWorkflowDto}
     * @return boolean value returning true/false
     */
    public Boolean reassignWorkflow(final ReassignWorkflowDto reassignWorkflowDto) {
        // insert data into transaction table
        Transactions transactions = Transactions.builder()
                .ttype(TransactionType.REASSIGN)
                .token("1")
                .summary(reassignWorkflowDto.getNotes())
                .comments(reassignWorkflowDto.getNotes())
                .userId(reassignWorkflowDto.getLoggedInUser())
                .startTime(new Date())
                .executionTime(0L)
                .build();

        transactionsRepository.save(transactions);

        // create copy of workflowAction based on actionId and update existing record for actedOn and actionTaken
        Collection<Long> actionIds = reassignWorkflowDto.getActionIds();
        actionIds.forEach(actionId -> {
            Optional<WorkflowAction> workflowAction = workflowActionRepository.findById(actionId);
            workflowAction.ifPresent(workflowAction1 -> {
                WorkflowAction clonedAction = (WorkflowAction) SerializationUtils.clone(workflowAction.get());
                clonedAction.setActedOn(LocalDateTime.now());
                clonedAction.setActionTaken(5L);
                workflowActionRepository.save(clonedAction);

                workflowAction1.setUserId(reassignWorkflowDto.getReassignedToUser());
                workflowAction1.setActionId(null);
                workflowActionRepository.save(workflowAction1);
            });

        });
        return true;
    }

    /**
     *
     * @param sendToSectionWorkflowDto sendToSectionWorkflowDto
     * @return 1/0
     */
    public Boolean assignToSectionWorkflow(final SendToSectionWorkflowDto sendToSectionWorkflowDto) {
        // insert data into transaction table
        Transactions transactions = Transactions.builder()
                .ttype(TransactionType.ASSIGN_TO_SECTION)
                .token("1")
                .summary(sendToSectionWorkflowDto.getNotes())
                .comments(sendToSectionWorkflowDto.getNotes())
                .userId(sendToSectionWorkflowDto.getLoggedInUser())
                .startTime(new Date())
                .executionTime(0L)
                .build();

        transactionsRepository.save(transactions);

        // create copy of workflowAction based on actionId and update existing record for actedOn and actionTaken
        Collection<Long> actionIds = sendToSectionWorkflowDto.getActionIds();
        actionIds.forEach(actionId -> {
            Optional<WorkflowAction> workflowAction = workflowActionRepository.findById(actionId);
            workflowAction.ifPresent(workflowAction1 -> {
                WorkflowAction clonedAction = (WorkflowAction) SerializationUtils.clone(workflowAction.get());
                clonedAction.setActedOn(LocalDateTime.now());
                clonedAction.setActionTaken(5L);
                workflowActionRepository.save(clonedAction);

                workflowAction1.setUserId(sendToSectionWorkflowDto.getReassignedToUser());
                workflowAction1.setActionId(null);
                workflowActionRepository.save(workflowAction1);
            });

        });
        return true;
    }

    /**
     * @param addDiariseDateVm {@link AddDiariseDateVm}
     * @return whether diarise date updated or not
     */
    public Boolean addDiariseDate(final AddDiariseDateVm addDiariseDateVm) {
        Transactions transactions = Transactions.builder()
                .ttype(TransactionType.DIARISE)
                .token("1")
                .summary(addDiariseDateVm.getComment())
                .comments(addDiariseDateVm.getComment())
                .userId(addDiariseDateVm.getUserId())
                .startTime(new Date())
                .executionTime(0L)
                .build();
        Transactions savedTransactions = transactionsRepository.save(transactions);

        WorkflowNotes workflowNotes = new WorkflowNotes();
        workflowNotes.setWorkflowId(addDiariseDateVm.getWorkflowId());
        workflowNotes.setContext("ADD INTO DIARY");
        workflowNotes.setTransactionId(savedTransactions.getTransactionId());
        workflowNotes.setNote(addDiariseDateVm.getComment());
        workflowNotesRepository.save(workflowNotes);

        WorkflowAction workflowAction = workflowActionRepository.findByActionId(addDiariseDateVm.getActionId());
        workflowAction.setDiariseDate(addDiariseDateVm.getDiariseDate());
        workflowAction.setNote(addDiariseDateVm.getComment());
        workflowActionRepository.save(workflowAction);
        return true;
    }

    /**
     * @param workflowExpediteTaskVm {@link WorkflowExpediteTaskVm}
     * @return whether priority flag updated or not
     */
    public Boolean expediteTask(final WorkflowExpediteTaskVm workflowExpediteTaskVm) {
        Transactions transactions = Transactions.builder()
                .ttype(TransactionType.EXPEDITE)
                .token("1")
                .summary(workflowExpediteTaskVm.getNotes())
                .comments(workflowExpediteTaskVm.getNotes())
                .userId(workflowExpediteTaskVm.getUserId())
                .startTime(new Date())
                .executionTime(0L)
                .build();
        Transactions savedTransactions = transactionsRepository.save(transactions);

        WorkflowNotes workflowNotes = new WorkflowNotes();
        workflowNotes.setWorkflowId(workflowExpediteTaskVm.getWorkflowId());
        workflowNotes.setContext("Expedite Task");
        workflowNotes.setTransactionId(savedTransactions.getTransactionId());
        workflowNotes.setNote(workflowExpediteTaskVm.getNotes());
        workflowNotesRepository.save(workflowNotes);

        Workflow workflow = workflowRepository.findByWorkflowId(workflowExpediteTaskVm.getWorkflowId());
        workflow.setPriorityFlag(workflowExpediteTaskVm.getPriorityFlag());
        workflowRepository.save(workflow);
        return true;
    }

    /**
     * @return {@link WorkflowProcessMatrixDto}
     */
    public Collection<WorkflowProcessMatrixDto> getWorkflowPriorityFlag() {
        return workflowProcessMatrixRepository.findByTagAndActive("FLAG", 1L)
                .stream().map(workflowProcessMatrixMapper::workflowProcessMatrixToWorkflowProcessMatrixDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * @param cancelTaskVm {@link CancelTaskVm}
     */
    public Boolean cancelTask(final CancelTaskVm cancelTaskVm) {
        Transactions build = Transactions.builder()
                .ttype(TransactionType.CANCEL_TASK)
                .token("1")
                .summary(cancelTaskVm.getNotes())
                .comments(cancelTaskVm.getNotes())
                .userId(cancelTaskVm.getUserId())
                .startTime(new Date())
                .executionTime(0L)
                .build();
        Transactions savedTransactions = transactionsRepository.save(build);

        WorkflowNotes workflowNotes = new WorkflowNotes();
        workflowNotes.setWorkflowId(cancelTaskVm.getWorkflowId());
        workflowNotes.setContext("Cancel Task");
        workflowNotes.setTransactionId(savedTransactions.getTransactionId());
        workflowNotes.setNote(cancelTaskVm.getNotes());
        workflowNotesRepository.save(workflowNotes);

        Workflow byWorkflowId = workflowRepository.findByWorkflowId(cancelTaskVm.getWorkflowId());
        byWorkflowId.setStatusId(2L);
        byWorkflowId.setInternalStatusId(20L);
        byWorkflowId.setExternalStatusId(20L);
        workflowRepository.save(byWorkflowId);

        WorkflowAction byActionId = workflowActionRepository.findByActionId(cancelTaskVm.getActionId());
        byActionId.setActionTaken(19L);
        byActionId.setTransactionId(savedTransactions.getTransactionId());
        byActionId.setNote("NOTE" + cancelTaskVm.getNotes());
        byActionId.setActedOn(LocalDateTime.now());
        workflowActionRepository.save(byActionId);
        return true;

    }

    /**
     * @param workflowId workflow id
     * @return requester info json
     */
    public RequesterInformation getRequesterInformation(final Long workflowId) {
        return cartUtils.getRequesterInformation(workflowId);
    }

    /**
     * @param workflowId workflowId
     * @return Collection<ReferralProjection>
     */
    public Page<ReferralProjection> getReferrals(final Long workflowId, final Pageable pageable) {
        return workflowRepository.getReferralInput(workflowId, pageable);
    }

    /**
     * @param uploadWorkflowDocsVm {@link UploadWorkflowDocsVm}
     * @return {@link WorkflowDocumentDto}
     */
    public WorkflowDocumentDto uploadSupportingDocs(final UploadWorkflowDocsVm uploadWorkflowDocsVm) {

        com.dw.ngms.cis.persistence.domains.workflow.WorkflowDocuments workflowDocuments =
                workflowDocumentMapper.uploadWorkflowDocsVmToWorkflowDocuments(uploadWorkflowDocsVm);

        //workflowDocuments.setDocName(uploadWorkflowDocsVm.getFile().getOriginalFilename());
        workflowDocuments.setExtension(FileUtils.getFileExtension(uploadWorkflowDocsVm.getFile().getOriginalFilename()));
        workflowDocuments.setUploadedOn(new Date());
        workflowDocuments.setSizeKb(uploadWorkflowDocsVm.getFile().getSize() / 1024);
        workflowDocuments.setUploadRefId(uploadWorkflowDocsVm.getCartId());
        com.dw.ngms.cis.persistence.domains.workflow.WorkflowDocuments saveDoc = workflowDocumentRepository.save(workflowDocuments);

        final String targetLocation = StorageContext.WORKFLOW.getStorageContext() +
                FileUtils.PATH_SEPARATOR +
                saveDoc.getDocumentId();

        final String storedFile = fileStorageService.storeFile(uploadWorkflowDocsVm.getFile(), targetLocation);

        saveDoc.setDocName(storedFile);
        final com.dw.ngms.cis.persistence.domains.workflow.WorkflowDocuments save = workflowDocumentRepository.save(saveDoc);

        return workflowDocumentMapper.workflowDocumentsToWorkflowDocumentsDto(save);

    }

    /**
     * @param documentId documentId
     * @param workflowId workflowId
     */
    public Boolean deleteWorkflowDocs(final Long documentId, final Long workflowId) {
        com.dw.ngms.cis.persistence.domains.workflow.WorkflowDocuments documents =
                workflowDocumentRepository.findWorkflowDocumentsByDocumentIdAndWorkflowId(documentId, workflowId);
        workflowDocumentRepository.delete(documents);
        final String location = StorageContext.WORKFLOW.getStorageContext() +
                FileUtils.PATH_SEPARATOR+
                documents.getDocumentId() +
                FileUtils.EXTENSION_SEPARATOR +
                documents.getExtension();
        return fileStorageService.deleteFile(location);
    }

    public Collection<String> getRequestInformationItem(final Long workflowId) {
        Collection<CartData> cartData = cartDataRepository.findByWorkflowId(workflowId);
        return cartData.stream()
                .map(CartData::getJsonData)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * @param workflowId workflowId
     * @return process data String
     */
    public WorkflowProcessData getWorkflowItem(final Long workflowId) {

        return workflowUtils.getProcessData(workflowId);

    }

    /**
     * @param processNotificationsVm {@link ProcessNotificationsVm}
     */
    @Async
    public void workflowNotification(final ProcessNotificationsVm processNotificationsVm) throws Exception {
        final ProcessNotificationFactory processNotificationFactory = this.processNotificationFactory.buildFactory();

        final ProcessNotification processNotification = processNotificationFactory
                .getProcessNotification(ProcessTemplateType.of(processNotificationsVm.getTemplateId()))
                .orElseThrow(() -> new IllegalArgumentException("Unregistered Process Template Type"));

        processNotification.process(processNotificationsVm);
    }

    /**
     * @param workflowId workflowId
     * @return processData String
     */
    public String getNotifyManagerAndQueryAndReferralData(final Long workflowId) {
        Workflow byWorkflowId = workflowRepository.findByWorkflowId(workflowId);
        return byWorkflowId.getProcessData();
    }

    /**
     * @param paymentDto {@link PaymentDto}
     * @return {@link PaymentDto}
     */
    public PaymentDto uploadProofOfPayment(PaymentDto paymentDto) {

        com.dw.ngms.cis.persistence.domains.workflow.WorkflowDocuments workflowDocuments =
                workflowDocumentMapper.proofOfPaymentDtoToWorkflowDocuments(paymentDto);
        workflowDocuments.setExtension(FileUtils.getFileExtension(paymentDto.getFile().getOriginalFilename()));
        workflowDocuments.setUploadedOn(new Date());
        workflowDocuments.setSizeKb(paymentDto.getFile().getSize() / 1024);
        workflowDocuments.setUploadRefId(paymentDto.getWorkflowId());

        com.dw.ngms.cis.persistence.domains.workflow.WorkflowDocuments documents = workflowDocumentRepository.save(workflowDocuments);
        // store document in workflow document
        final String targetLocation = StorageContext.WORKFLOW.getStorageContext() +
                FileUtils.PATH_SEPARATOR +
                documents.getDocumentId();

        final String fileName = fileStorageService.storeFile(paymentDto.getFile(), targetLocation);

        documents.setDocName(fileName);
        workflowDocumentRepository.save(documents);

        Payment payment1 = paymentMapper.proofOfPaymentDtoToPayment(paymentDto);
        payment1.setLastUpdatedOn(LocalDateTime.now());
        Payment payment = paymentRepository.save(payment1);

        return paymentMapper.paymentToProofOfPaymentDto(payment);

    }


    /**
     * @param workflowId workflowId
     * @return {@link PaymentDto}
     */
    public PaymentDto getPaymentInfoByWorkflowId(final Long workflowId) {
        return paymentMapper
                .paymentToProofOfPaymentDto(paymentRepository.findByWorkflowId(workflowId));
    }


    /**
     * @param workflowMarkAsPendingVm {@link WorkflowMarkAsPendingVm}
     * @return Boolean
     */
    @Transactional
    public Boolean markWorkflowPending(final WorkflowMarkAsPendingVm workflowMarkAsPendingVm) {
        Transactions build = Transactions.builder()
                .ttype(TransactionType.MARK_AS_PENDING)
                .token("1")
                .summary(workflowMarkAsPendingVm.getNotes())
                .comments(workflowMarkAsPendingVm.getNotes())
                .userId(workflowMarkAsPendingVm.getUserId())
                .startTime(new Date())
                .executionTime(0L)
                .build();
        Transactions savedTransactions = transactionsRepository.save(build);

        WorkflowNotes workflowNotes = new WorkflowNotes();
        workflowNotes.setWorkflowId(workflowMarkAsPendingVm.getWorkflowId());
        workflowNotes.setContext("MARK AS PENDING");
        workflowNotes.setTransactionId(savedTransactions.getTransactionId());
        workflowNotes.setNote(workflowMarkAsPendingVm.getNotes());
        workflowNotesRepository.save(workflowNotes);

        return 1 == workflowRepository.workflowMarkPending(workflowMarkAsPendingVm.getWorkflowId());
    }

    /**
     * @param workflowChangeProvinceVM {@link WorkflowChangeProvinceVM}
     * @return Boolean
     */
    public Boolean changeWorkflowProvince(final WorkflowChangeProvinceVM workflowChangeProvinceVM) {
        Workflow byWorkflowId = workflowRepository.findByWorkflowId(workflowChangeProvinceVM.getWorkflowId());
        String referenceNo = byWorkflowId.getReferenceNo();
        String newReferenceNo = referenceNo.substring(0, referenceNo.length() - 2);
        String finalReferenceNo = newReferenceNo + workflowChangeProvinceVM.getProvinceShortName();
        byWorkflowId.setReferenceNo(finalReferenceNo);
        byWorkflowId.setProvinceId(workflowChangeProvinceVM.getToBeChangedProvinceId());
        workflowRepository.save(byWorkflowId);
        return true;
    }


    /**
     * @param workflowId     workflowId
     * @param documentTypeId documentTypeId
     * @return WorkflowDocumentDto
     */
    public WorkflowDocumentDto getPaymentDocument(Long workflowId, Long documentTypeId) {

        return workflowDocumentMapper.workflowDocumentsToWorkflowDocumentsDto(workflowDocumentRepository
                .findWorkflowDocumentsByWorkflowIdAndDocumentTypeId(workflowId, documentTypeId));

    }

    /**
     * @param documentId documentId
     * @return {@link Resource}
     */
    public Resource getPaymentFile(final Long documentId) {
        final com.dw.ngms.cis.persistence.domains.workflow.WorkflowDocuments workflowDocumentsByDocumentId =
                workflowDocumentRepository.findWorkflowDocumentsByDocumentId(documentId);
        final String location = StorageContext.WORKFLOW.getStorageContext() +
                FileUtils.PATH_SEPARATOR +
                workflowDocumentsByDocumentId.getDocumentId() +
                FileUtils.EXTENSION_SEPARATOR +
                workflowDocumentsByDocumentId.getExtension();
        log.debug(" loading payment file from location {}", location);
        return fileStorageService.loadFileAsResource(location);
    }

    /**
     * @return Collection<WorkflowProcessProjection> {@link WorkflowProcessProjection}
     */
    public Collection<WorkflowProcessProjection> getWorkflowProcess() {
        return workflowProcessRepository.getAllProcess(1L, -1L);
    }

    /**
     * @param workflowId workflowId
     */
    public void createProductivityForWorkflow(final Long workflowId) {
        productivityReportGenerator.generateWorkflowProductivity(workflowId);
    }

    /**
     * @param workflowId workflowId
     * @param pageable   {@link Pageable}
     * @return Page<WorkflowActionProductivityView>
     */
    public Page<WorkflowActionProductivityView> getWorkflowActionTaskDuration(final Long workflowId,
                                                                              final Pageable pageable) {

        return workflowActionProductivityRepository.findAllByWorkflowId(workflowId, pageable);

    }

    /**
     * @param documentId documentId
     * @return Resource {@link Resource}
     */
    public Resource downloadSupportingDocs(final Long documentId) {
        final com.dw.ngms.cis.persistence.domains.workflow.WorkflowDocuments workflowDocumentsByDocumentId =
                workflowDocumentRepository.findWorkflowDocumentsByDocumentId(documentId);
        String fileName = StorageContext.WORKFLOW.getStorageContext() +
                FileUtils.PATH_SEPARATOR +
                workflowDocumentsByDocumentId.getDocumentId() +
                FileUtils.EXTENSION_SEPARATOR +
                workflowDocumentsByDocumentId.getExtension();
        log.debug("loading workflow supporting document from location {} for documentId {}", fileName, documentId);
        return fileStorageService.loadFileAsResource(fileName);
    }


    /**
     * @param referenceNumber referenceNumber
     * @return Collection<String>
     */
    public Collection<SearchDetails> getSearchDataFromReferenceNo(final String referenceNumber) {
        DashboardRequestProjection usingReferenceNumber = workflowRepository.findUsingReferenceNumber(referenceNumber);
        if (null != usingReferenceNumber) {
            Long workflowId = usingReferenceNumber.getWorkflowId();
            Collection<CartData> byWorkflowId = cartDataRepository.findByWorkflowId(workflowId);
            return byWorkflowId.stream()
                    .map(cartData -> {
                        String jsonData = cartData.getJsonData();
                        try {

                            CartJsonData cartJsonData = mapper.readValue(jsonData, CartJsonData.class);
                            return cartJsonData.getSearchDetails();

                        } catch (JsonProcessingException e) {
                            log.error("error while parsing cart data from reference Number [{}]", referenceNumber);
                            e.printStackTrace();
                        }
                        return null;
                    })
                    .collect(Collectors.toCollection(LinkedList::new));
        }

        return Collections.emptyList();
    }

    /**
     * @param userId userId of logged in user
     * @return Collection<DashboardRequestProjection>
     */
    public Page<UserNotificationDto> getUserNotifications(final Long userId, final Pageable pageable) {

        return userNotificationRepository.findAllByCreatedForUserIdOrderByIdDesc(userId, pageable)
                .map(userNotification -> {
                    UserNotificationDto userNotificationDto = userNotificationMapper.userNotificationTouserNotificationDto(userNotification);
                    //Set initials
                    //Set userName
                    User user = userRepository.getUserById(userNotification.getCreatedByUserId());
                    String name = user.getFirstName() + ' ' + user.getSurname();
                    userNotificationDto.setUserName(name);
                    userNotificationDto.setInitials(getInitials(name));
                    return userNotificationDto;
                });
    }

    public WorkflowDocumentDto saveTextAsDoc(TextToDocDto textToDocDto) {
        com.dw.ngms.cis.persistence.domains.workflow.WorkflowDocuments workflowDocuments =
                new com.dw.ngms.cis.persistence.domains.workflow.WorkflowDocuments();

        workflowDocuments.setWorkflowId(textToDocDto.getWorkflowId());
        workflowDocuments.setDocumentTypeId(textToDocDto.getDocumentTypeId());
        workflowDocuments.setNotes(textToDocDto.getComment());
        workflowDocuments.setUserId(textToDocDto.getUserId());
        workflowDocuments.setUploadedOn(new Date());
        workflowDocuments.setExtension("txt");
        workflowDocuments.setDocName("wtt.txt");
        com.dw.ngms.cis.persistence.domains.workflow.WorkflowDocuments save =
                workflowDocumentRepository.save(workflowDocuments);

        try {

            File wtt = File.createTempFile("wtt", ".txt");
            FileWriter writer = new FileWriter(wtt);
            writer.write(textToDocDto.getText());
            writer.close();

            final String targetLocation = StorageContext.WORKFLOW.getStorageContext() +
                    FileUtils.PATH_SEPARATOR +
                    save.getDocumentId();
            fileStorageService.storeFile(wtt, targetLocation);

        } catch (IOException e) {
            e.printStackTrace();
        }

        WorkflowDocumentDto workflowDocumentDto = workflowDocumentMapper.workflowDocumentsToWorkflowDocumentsDto(save);
        log.debug(" file saved successfully with content [{}] ", workflowDocumentDto);

        return workflowDocumentDto;

    }


    /**
     * @param lpiNoteDto {@link LpiNotes}
     * @return {@link LpiNoteDto}
     */
    public LpiNoteDto saveLpiNotes(final LpiNoteDto lpiNoteDto) {
        final LpiNotes lpiNotes = lpiNotesMapper.lpiNotesDtoToLpiNotes(lpiNoteDto);
        lpiNotes.setDated(LocalDateTime.now());
        final LpiNotes save = lpiNotesRepository.save(lpiNotes);
        return lpiNotesMapper.lipiNoteToLpiNotesDto(save);
    }



    /**
     * @param lpi lpi
     * @return Collection<LpiNotes>
     */
    public Collection<NotesTimelineProjection> getLpiNotes(final String lpi) {
        return lpiNotesRepository.findAllNotesUsingLpi(lpi);
    }

    private String getInitials(String name) {
        StringBuilder initials = new StringBuilder(String.valueOf(Character.toUpperCase(name.charAt(0))));
        for (int i = 1; i < name.length() - 1; i++) {
            if (name.charAt(i) == ' ') {
                initials.append(Character.toUpperCase(name.charAt(i + 1)));
            }
        }
        return initials.toString();
    }


    /**
     *
     * @param closeTaskVm {@link CloseTaskVm}
     * @return 1/0
     */
    public Boolean closeWorkflowTask(final CloseTaskVm closeTaskVm) {
        final Workflow byWorkflowId = workflowRepository.findByWorkflowId(closeTaskVm.getWorkflowId());
        if(null != byWorkflowId) {
            byWorkflowId.setInternalStatusId(226L);
            byWorkflowId.setExternalStatusId(226L);
            workflowRepository.save(byWorkflowId);
        }

        final WorkflowAction byActionId = workflowActionRepository.findByActionId(closeTaskVm.getActionId());
        if(null != byActionId) {
            WorkflowAction clonedAction = (WorkflowAction) SerializationUtils.clone(byActionId);
            byActionId.setActedOn(LocalDateTime.now());
            byActionId.setActionTaken(225L);
            workflowActionRepository.save(byActionId);

            clonedAction.setActionId(null);
            clonedAction.setNote(closeTaskVm.getNotes());
            clonedAction.setActionRequired(224L);
            clonedAction.setActedOn(null);
            clonedAction.setActionTaken(null);
            clonedAction.setPostedOn(LocalDateTime.now());
            workflowActionRepository.save(clonedAction);
        }

        return true;
    }

    /**
     *
     * @param reOpenTaskVm {@link ReOpenTaskVm}
     * @return 1/0
     */
    public Boolean reOpenTask(final ReOpenTaskVm reOpenTaskVm) {
        final Workflow byWorkflowId = workflowRepository.findByWorkflowId(reOpenTaskVm.getWorkflowId());
        if(null != byWorkflowId) {
            byWorkflowId.setInternalStatusId(7L);
            byWorkflowId.setExternalStatusId(7L);
            workflowRepository.save(byWorkflowId);
        }

        final WorkflowAction byActionId = workflowActionRepository.findByActionId(reOpenTaskVm.getActionId());
        if(null != byActionId) {
            WorkflowAction clonedAction = (WorkflowAction) SerializationUtils.clone(byActionId);
            byActionId.setActedOn(LocalDateTime.now());
            byActionId.setActionTaken(225L);
            workflowActionRepository.save(byActionId);

            clonedAction.setActionId(null);
            clonedAction.setUserId(reOpenTaskVm.getUserId());
            clonedAction.setActionRequired(15L);
            clonedAction.setActedOn(null);
            clonedAction.setActionTaken(null);
            clonedAction.setPostedOn(LocalDateTime.now());
            clonedAction.setNote(reOpenTaskVm.getNotes());
            workflowActionRepository.save(clonedAction);

        }
        return true;
    }

    /**
     *
     * @param workflowId workflowId
     * @return Collection<WorkflowReferralInputProjection>
     */
    public InboxProjection getReferralInput(final Long workflowId){
        return workflowRepository.getReferralInputData(workflowId);
    }

    /**
     * Save Workflow user feedback.
     * @param workflowUserFeedbackDto {@link WorkflowUserFeedbackDto}
     * @return {@link WorkflowUserFeedbackDto}
     */
    public WorkflowUserFeedbackDto saveWorkflowUserFeedback(final WorkflowUserFeedbackDto workflowUserFeedbackDto){
       WorkflowUserFeedback workflowUserFeedback = workflowUserFeedbackMapper.workflowUserFeedbackDtoToWorkflowUserFeedback(workflowUserFeedbackDto);
        WorkflowUserFeedback savedFeedback = workflowUserFeedbackRepository.save(workflowUserFeedback);
        return workflowUserFeedbackMapper
               .workflowUserFeedbackToWorkflowUserFeedbackDto(savedFeedback);
    }

    /**
     * get all the feedbacks for applicant
     * @param applicantId applicantId
     * @return Collection<WorkflowUserFeedbackDto>
     */
    public Collection<WorkflowUserFeedbackDto> getWorkflowUserFeedback(final Long applicantId){
        List<WorkflowUserFeedback> workflowUserFeedbacksByToUser =   workflowUserFeedbackRepository.findByToUserId(applicantId);
        return workflowUserFeedbacksByToUser.stream()
                .map(workflowUserFeedbackMapper::workflowUserFeedbackToWorkflowUserFeedbackDto)
                .collect(Collectors.toList());
    }

    /**
     * get applicant feedback for yearly status
     * @param workflowUserFeedbackStatusDto {@link WorkflowUserFeedbackStatusDto}
     * @return List<WorkflowUserFeedbackProjection>
     */
    public List<WorkflowUserFeedbackProjection>
    getWorkflowUserFeedbackYearlyStatus(final WorkflowUserFeedbackStatusDto workflowUserFeedbackStatusDto){
        List<Long> provinces = workflowUserFeedbackStatusDto.getProvinces();
        Long applicantId = workflowUserFeedbackStatusDto.getApplicantId();
        return workflowUserFeedbackRepository
                .getAllFeedbackByApplicationIdAndProvince();
    }
    /**
     *
     * @param RecordId RecordId
     * @return Collection<WorkflowParcelProjection>
     */
    public Collection<WorkflowParcelProjection> getParcelData(final Long RecordId){
        return workflowRepository.findbyRecordID(RecordId);
    }

    /**
     *
     * @param workflowId workflowId
     * @return Collection<WorkflowProcessTimelineProjection>
     */
    public Collection<WorkflowProcessTimelineProjection> getWorkflowProcessTimeline(final Long workflowId) {
        return workflowRepository.getWorkflowProcessTimeline(workflowId);
    }
}
