package com.dw.ngms.cis.service.lodgment;

import com.dw.ngms.cis.enums.FeeSimulatorType;
import com.dw.ngms.cis.enums.StorageContext;
import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.domains.lodgement.*;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraft;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftRequest;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationOutcome;
import com.dw.ngms.cis.persistence.domains.user.SecurityUser;
import com.dw.ngms.cis.persistence.domains.workflow.Workflow;
import com.dw.ngms.cis.persistence.projection.lodgment.LodgementDocumentSummary;
import com.dw.ngms.cis.persistence.projection.lodgment.LodgementDocumentSummaryDto;
import com.dw.ngms.cis.persistence.projection.lodgment.LodgmentDraftListingProjection;
import com.dw.ngms.cis.persistence.repository.UserRepository;
import com.dw.ngms.cis.persistence.repository.listmanagement.ListItemRepository;
import com.dw.ngms.cis.persistence.repository.lodgement.*;
import com.dw.ngms.cis.persistence.repository.reservation.ReservationDraftRepository;
import com.dw.ngms.cis.persistence.repository.reservation.ReservationDraftRequestRepository;
import com.dw.ngms.cis.persistence.repository.reservation.ReservationDraftStepsRepository;
import com.dw.ngms.cis.persistence.repository.reservation.ReservationOutcomeRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowActionRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowRepository;
import com.dw.ngms.cis.security.CurrentLoggedInUser;
import com.dw.ngms.cis.service.EmailServiceHandler;
import com.dw.ngms.cis.service.FeeSimulatorService;
import com.dw.ngms.cis.service.FileStorageService;
import com.dw.ngms.cis.service.SmsServiceHandler;
import com.dw.ngms.cis.service.dto.FeeSimulatorDto;
import com.dw.ngms.cis.service.dto.lodgment.*;
import com.dw.ngms.cis.service.dto.reservation.ReservationOutcomeDto;
import com.dw.ngms.cis.service.mapper.lodgment.*;
import com.dw.ngms.cis.service.mapper.reservation.ReservationOutcomeMapper;
import com.dw.ngms.cis.utilities.FileUtils;
import com.dw.ngms.cis.utilities.SystemUtility;
import com.dw.ngms.cis.web.vm.FeeSimulatorVm;
import com.dw.ngms.cis.web.vm.LodgementNotificationVm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class LodgementDraftService {

    private final CurrentLoggedInUser currentLoggedInUser;

    private final LodgementDraftAnnexureRepository lodgementDraftAnnexureRepository;

    private final LodgementDraftAnnexureMapper lodgementDraftAnnexureMapper;

    private final FileStorageService fileStorageService;

    private final LodgementDraftRequestMapper lodgementDraftRequestMapper;

    private final LodgementDraftRequestRepository lodgementDraftRequestRepository;

    private final LodgementDraftStepsRepository lodgementDraftStepsRepository;

    private final LodgementDraftStepsMapper lodgementDraftStepsMapper;

    private final UserRepository userRepository;

    private final EmailServiceHandler emailServiceHandler;

    private final WorkflowActionRepository workflowActionRepository;

    private final LodgementDraftRepository lodgementDraftRepository;

    private final LodgementDraftMapper lodgementDraftMapper;

    private final LodgementDraftPaymentsMapper lodgementDraftPaymentsMapper;

    private final LodgementDraftPaymentRepository lodgementDraftPaymentRepository;

    private final LodgementDraftDocumentMapper lodgementDraftDocumentMapper;

    private final LodgementDraftDocumentRepository lodgementDraftDocumentRepository;

    private final ReservationOutcomeMapper reservationOutcomeMapper;

    private final ReservationOutcomeRepository reservationOutcomeRepository;

    private final ReservationDraftRequestRepository reservationDraftRequestRepository;

    private final ObjectMapper objectMapper;

    private final WorkflowRepository workflowRepository;

    private final ReservationDraftRepository reservationDraftRepository;

    private final ReservationDraftStepsRepository reservationDraftStepsRepository;

    private final ListItemRepository listItemRepository;

    private final LodgementDocumentSummaryService lodgementDocumentSummaryService;

    private final LodgementPerformaInvoiceGenerationService lodgementPerformaInvoiceGenerationService;

    private final SmsServiceHandler smsServiceHandler;

    private final LodgementBatchMapper lodgementBatchMapper;

    private final LodgementBatchRepository lodgementBatchRepository;

    private final LodgementBatchSgDocumentRepository lodgementBatchSgDocumentRepository;

    private final LodgementDraftAcknowledgementLetterService lodgementDraftAcknowledgementLetterService;

    public Page<LodgementDraftDto> getAllLodgementDraft(final Pageable pageable) {
        SecurityUser user = currentLoggedInUser.getUser();
        Page<LodgementDraft> shortLodgementDrafts = lodgementDraftRepository.findShortLodgementDrafts(user.getUserId(),
                user.getUserId(),
                pageable);
        return shortLodgementDrafts
                .map(lodgementDraftMapper::lodgementDraftToLodgementDraftDto);
    }

    public LodgementDraftDto saveLodgementDraft
            (final LodgementDraftDto lodgementDraftDto) {
        LodgementDraft lodgementDraft = lodgementDraftMapper.
                lodgementDraftDtoToLodgementDraft(lodgementDraftDto);

        lodgementDraft.setIsActive(1L);

        LodgementDraft saveLodgementDraft = lodgementDraftRepository.save(lodgementDraft);
        return lodgementDraftMapper.lodgementDraftToLodgementDraftDto(saveLodgementDraft);
    }

    public LodgementDraftDto updateLodgementDraft
            (final LodgementDraftDto lodgementDraftDto) {
        LodgementDraft lodgementDraft = lodgementDraftMapper.
                lodgementDraftDtoToLodgementDraft(lodgementDraftDto);

        LodgementDraft save = lodgementDraftRepository.save(lodgementDraft);
        return lodgementDraftMapper.lodgementDraftToLodgementDraftDto(save);
    }

    public LodgementDraftAnnexureDto saveLodgementDocument(final LodgementDraftAnnexureDto lodgementDraftAnnexureDto) {


        // save object in db
        LodgementDraftAnnexure lodgementDraftAnnexure = lodgementDraftAnnexureMapper
                .lodgementDraftAnnexureDtoToLodgementDraftAnnexure(lodgementDraftAnnexureDto);

        lodgementDraftAnnexureRepository.save(lodgementDraftAnnexure);

        // save file on disk
        String targetLocation = StorageContext.LODGEMENT_DRAFT.getStorageContext() +
                FileUtils.PATH_SEPARATOR +
                lodgementDraftAnnexure.getAnnexureId();

        String fileName = fileStorageService.storeFile(lodgementDraftAnnexureDto.getDocument(), targetLocation);

        // set fileName
        lodgementDraftAnnexure.setName(fileName);

        // save final reservationDraft
        LodgementDraftAnnexure savedLodgementDraftAnnexure = lodgementDraftAnnexureRepository.save(lodgementDraftAnnexure);


        LodgementDraftAnnexureDto lodgementDraftAnnexureDto1 = lodgementDraftAnnexureMapper
                .lodgementDraftAnnexureToLodgementDraftAnnexureDto(savedLodgementDraftAnnexure);

        lodgementDraftAnnexureDto1.setName(fileName);

        return lodgementDraftAnnexureDto1;

    }

    /**
     * @param annexureId annexureId
     */
    public void removeAnnuxure(final Long annexureId) {
        LodgementDraftAnnexure byAnnexureId = lodgementDraftAnnexureRepository.findByAnnexureId(annexureId);
        String name = byAnnexureId.getName();
        lodgementDraftAnnexureRepository.deleteById(annexureId);
        String targetLocation = StorageContext.LODGEMENT_DRAFT.getStorageContext() +
                FileUtils.PATH_SEPARATOR +
                annexureId +
                FileUtils.EXTENSION_SEPARATOR +
                FileUtils.getFileExtension(name);

        fileStorageService.deleteFile(targetLocation);
    }

    public Resource getAnnuxure(Long annexureId) {
        LodgementDraftAnnexure byAnnexureId = lodgementDraftAnnexureRepository.findByAnnexureId(annexureId);
        String name = byAnnexureId.getName();
        String targetFileName = StorageContext.LODGEMENT_DRAFT.getStorageContext() +
                FileUtils.PATH_SEPARATOR +
                annexureId +
                FileUtils.EXTENSION_SEPARATOR +
                FileUtils.getFileExtension(name);
        return fileStorageService.loadFileAsResource(targetFileName);
    }

    /**
     * @param draftId draftId
     * @return {@link LodgementDraftDto}
     */
    public LodgementDraftDto getDraft(final Long draftId) {
        LodgementDraft byDraftId = lodgementDraftRepository.findByDraftId(draftId);
        return lodgementDraftMapper.lodgementDraftToLodgementDraftDto(byDraftId);

    }


    /**
     * @param draftRequestId draftRequestId
     */
    public void deleteDraftRequest(final Long draftRequestId) {
        lodgementDraftRequestRepository.deleteById(draftRequestId);
    }

    /**
     * @param draftId draftId
     * @return Collection<ReservationDraftStepsDto>
     */
    public Collection<LodgementDraftStepsDto> getAllDraftSteps(Long draftId) {

        LodgementDraft byDraftId = lodgementDraftRepository.findByDraftId(draftId);

        if (null != byDraftId) {

            List<LodgementDraftStep> allByLodgementDraftOrderByStepId = lodgementDraftStepsRepository
                    .findAllByLodgementDraftOrderByStepId(byDraftId);

            if (CollectionUtils.isNotEmpty(allByLodgementDraftOrderByStepId)) {

                return allByLodgementDraftOrderByStepId.stream()
                        .map(lodgementDraftStepsMapper::lodgmentDraftStepsToLodgmentDraftStepsDto)
                        .collect(Collectors.toList());
            }

        }

        return null;
    }

    /**
     * @param draftId draftId
     * @return Collection<LodgementDraftAnnexureDto>
     */
    public Collection<LodgementDraftAnnexureDto> getAllLodgementDraftAnnexures(final Long draftId) {

        LodgementDraft byDraftId = lodgementDraftRepository.findByDraftId(draftId);

        if (null != byDraftId) {

            List<LodgementDraftAnnexure> allByLodgementDraftOrderByAnnexureId = lodgementDraftAnnexureRepository
                    .findAllByLodgementDraftOrderByAnnexureId(byDraftId);

            if (CollectionUtils.isNotEmpty(allByLodgementDraftOrderByAnnexureId)) {
                return allByLodgementDraftOrderByAnnexureId.stream()
                        .map(lodgementDraftAnnexureMapper::lodgementDraftAnnexureToLodgementDraftAnnexureDto)
                        .collect(Collectors.toList());
            }
        }

        return null;
    }

    /**
     * @param draftId draftId
     */
    public void deleteDraft(final Long draftId) {
        lodgementDraftRepository.deleteById(draftId);
    }

    /**
     * @param stepId stepId
     */
    public void deleteDraftStep(final Long stepId) {
        lodgementDraftStepsRepository.deleteById(stepId);
    }


    /**
     * @param userId    userId
     * @param processId processId
     * @param pageable  {@link Pageable}
     * @return {@link  LodgmentDraftListingProjection}
     */
    public Page<LodgmentDraftListingProjection> getAllLodgementDraftForUserId(final Long userId,
                                                                              final Long processId,
                                                                              final Pageable pageable) {
        return lodgementDraftRepository.loadgmentListing(userId, processId, pageable);
    }

    /**
     * @param draftId    draftId
     * @param workflowId workflowId
     */
    public void checkoutLodgementDraft(Long draftId, Long workflowId) {
        LodgementDraft byDraftId = lodgementDraftRepository.findByDraftId(draftId);
        if (null != byDraftId) {
            byDraftId.setWorkflowId(workflowId);
            lodgementDraftRepository.save(byDraftId);
        }
    }

    /**
     * @param workflowId workflowId
     * @return {@link LodgementDraftDto}
     */
    public LodgementDraftDto getLodgementDraftByWorkflowId(final Long workflowId) {
        LodgementDraft byWorkflowId = lodgementDraftRepository.findByWorkflowId(workflowId);
        return lodgementDraftMapper.lodgementDraftToLodgementDraftDto(byWorkflowId);
    }


    public void notifyForLodgement(final LodgementNotificationVm lodgementNotificationVm) {
        Long workflowId = lodgementNotificationVm.getWorkflowId();
        LodgementDraft byWorkflowId = lodgementDraftRepository.findByWorkflowId(workflowId);
        if (null != byWorkflowId) {
            Long userId = byWorkflowId.getUserId();
            Long applicantUserId = byWorkflowId.getApplicantUserId();
            // find user to which email is send
            User userByUserId = userRepository.findUserByUserId(applicantUserId);

            // find associated user with workflow
            Collection<Long> allDistinctUserIdForWorkflowId = workflowActionRepository
                    .getAllDistinctUserIdForWorkflowId(workflowId);

            allDistinctUserIdForWorkflowId.add(userId);

            List<String> ccUserList = allDistinctUserIdForWorkflowId.stream()
                    .map(aLong -> {
                        User userByUserId1 = userRepository.findUserByUserId(aLong);
                        if (null != userByUserId1) {
                            return userByUserId1.getEmail();
                        }
                        return null;
                    }).filter(Objects::nonNull)
                    .collect(Collectors.toList());

            emailServiceHandler.sendReservationActionEmail(ccUserList,
                    userByUserId,
                    lodgementNotificationVm.getTemplateId(),
                    lodgementNotificationVm.getReferenceNumber(),
                    lodgementNotificationVm.getContext());

            smsServiceHandler
                    .sendLodgementSmsNotification(userByUserId.getUserId(),
                            lodgementNotificationVm.getTemplateId(),
                            lodgementNotificationVm.getReferenceNumber());
        }


    }

    /**
     * @param lodgementDraftPaymentDto {@link LodgementDraftPaymentDto}
     * @return {@link LodgementDraftPaymentDto}
     */
    public LodgementDraftPaymentDto addPayment(LodgementDraftPaymentDto lodgementDraftPaymentDto) {
        LodgementDraftPayment lodgementDraftPayment = lodgementDraftPaymentsMapper
                .lodgementDraftPaymentDtoToLodgementDraftPayment(lodgementDraftPaymentDto);

        LodgementDraftPayment save1 = lodgementDraftPaymentRepository.save(lodgementDraftPayment);

        // save file on disk
        String targetLocation = StorageContext.LODGEMENT_DRAFT_PAYMENT.getStorageContext() +
                FileUtils.PATH_SEPARATOR +
                lodgementDraftPayment.getPayId();

        String fileName = fileStorageService.storeFile(lodgementDraftPaymentDto.getDocument(), targetLocation);

        log.debug("payment doc saved successfully !! with name {}", fileName);

        // set fileName
        save1.setDocName(fileName);

        // save final reservationDraft
        LodgementDraftPayment save = lodgementDraftPaymentRepository.save(save1);

        return lodgementDraftPaymentsMapper
                .lodgementDraftPaymentToLodgementDraftPaymentDto(save);

    }

    /**
     * @param draftId draftId
     * @return Collection<LodgementDraftPaymentDto>
     */
    public Collection<LodgementDraftPaymentDto> getPayments(final Long draftId) {
        LodgementDraft byDraftId = lodgementDraftRepository.findByDraftId(draftId);
        if (null != byDraftId) {

            return lodgementDraftPaymentRepository
                    .findAllByLodgementDraftOrderByPayId(byDraftId)
                    .stream()
                    .map(lodgementDraftPaymentsMapper::lodgementDraftPaymentToLodgementDraftPaymentDto)
                    .collect(Collectors.toList());

        }
        return null;
    }

    /**
     * @param payId payId
     */
    public void deleteDraftPaymentDetails(final Long payId) {

        LodgementDraftPayment byPayId = lodgementDraftPaymentRepository.findByPayId(payId);
        if (null != byPayId) {
            String docName = byPayId.getDocName();
            lodgementDraftPaymentRepository.deleteById(payId);

            String targetLocation = StorageContext.LODGEMENT_DRAFT_PAYMENT.getStorageContext() +
                    FileUtils.PATH_SEPARATOR +
                    payId +
                    FileUtils.EXTENSION_SEPARATOR +
                    FileUtils.getFileExtension(docName);

            fileStorageService.deleteFile(targetLocation);
        }
    }

    /**
     * @param lodgementDraftPaymentDto {@link LodgementDraftPaymentDto}
     * @return {@link LodgementDraftPaymentDto}
     */
    public LodgementDraftPaymentDto updateDraftPayment(LodgementDraftPaymentDto lodgementDraftPaymentDto) {

        LodgementDraftPayment lodgementDraftPayment = lodgementDraftPaymentsMapper
                .lodgementDraftPaymentDtoToLodgementDraftPayment(lodgementDraftPaymentDto);

        lodgementDraftPayment.setStatusItemId(1047L);

        LodgementDraftPayment save = lodgementDraftPaymentRepository.save(lodgementDraftPayment);

        return lodgementDraftPaymentsMapper.lodgementDraftPaymentToLodgementDraftPaymentDto(save);

    }

    /**
     * @param payId payId
     * @return {@link Resource}
     */
    public Resource getDraftPaymentDocument(final Long payId) {

        LodgementDraftPayment byPayId = lodgementDraftPaymentRepository.findByPayId(payId);
        if (null != byPayId) {
            String docName = byPayId.getDocName();

            String targetFileName = StorageContext.LODGEMENT_DRAFT_PAYMENT.getStorageContext() +
                    FileUtils.PATH_SEPARATOR +
                    payId +
                    FileUtils.EXTENSION_SEPARATOR +
                    FileUtils.getFileExtension(docName);

            return fileStorageService.loadFileAsResource(targetFileName);
        }

        return null;
    }

    /**
     * @param searchTerm searchTerm
     * @return Collection<ReservationOutcomeDto>
     */
    public Collection<ReservationOutcomeDto> searchLodgDraftRequest(String searchTerm) {
        List<ReservationOutcome> byDesignationContainingIgnoreCase = reservationOutcomeRepository.findByDesignationContainingIgnoreCase(searchTerm);
        return byDesignationContainingIgnoreCase
                .stream()
                .map(reservationOutcomeMapper::reservationOutcomeToReservationOutcomeDto)
                .collect(Collectors.toList());
    }

    /**
     * @param lodgementDraftRequestDto {@link LodgementDraftRequestDto}
     * @return {@link LodgementDraftRequestDto}
     */
    public LodgementDraftRequestDto addRequestToDraftStep(LodgementDraftRequestDto lodgementDraftRequestDto) {

        LodgementDraftRequest lodgementDraftRequest = lodgementDraftRequestMapper
                .lodgementDraftRequestDtoToLodgementDraftRequest(lodgementDraftRequestDto);

        Long outcomeIdReservation = lodgementDraftRequest.getOutcomeIdReservation();

        ReservationOutcome byOutcomeId = reservationOutcomeRepository.findByOutcomeId(outcomeIdReservation);

        if (null != byOutcomeId) {

            Long requestId = byOutcomeId.getRequestId();

            ReservationDraftRequest byDraftRequestId = reservationDraftRequestRepository.findByDraftRequestId(requestId);

            if (null != byDraftRequestId) {

                try {

                    byDraftRequestId.setReservationDraftSteps(null);

                    String s = objectMapper.writeValueAsString(byDraftRequestId);

                    lodgementDraftRequest.setParentParcels(s);

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    log.error("Exception occurs while serializing ReservationDraftRequest {}", byDraftRequestId);
                }
            }
        }

        LodgementDraftRequest save = lodgementDraftRequestRepository.save(lodgementDraftRequest);

        return lodgementDraftRequestMapper
                .lodgementDraftRequestToLodgementDraftRequestDto(save);

    }

    /**
     * @param lodgementDraftDocumentDto {@link LodgementDraftDocumentDto}
     * @return {@link LodgementDraftDocumentDto}
     */
    public LodgementDraftDocumentDto uploadReservationRequestDoc(LodgementDraftDocumentDto lodgementDraftDocumentDto) {

        LodgementDraftDocument lodgementDraftDocument = lodgementDraftDocumentMapper
                .lodgementDraftDocumentDtoToLodgementDraftDocument(lodgementDraftDocumentDto);

        LodgementDraftDocument savedDoc = lodgementDraftDocumentRepository.save(lodgementDraftDocument);

        String targetLocation = StorageContext.LODGEMENT_DRAFT_RESERVATION_DETAIL_DOC.getStorageContext()
                + FileUtils.PATH_SEPARATOR
                + savedDoc.getDocumentId();

        String fileName = fileStorageService.storeFile(lodgementDraftDocumentDto.getDocument(), targetLocation);

        savedDoc.setDocumentName(fileName);

        LodgementDraftDocument save = lodgementDraftDocumentRepository.save(savedDoc);

        return lodgementDraftDocumentMapper
                .lodgementDraftDocumentToLodgementDraftDocumentDto(save);
    }

    /**
     * @param documentId documentId
     * @return {@link Resource}
     */
    public Resource getDraftResDetailDoc(Long documentId) {
        LodgementDraftDocument byDocumentId = lodgementDraftDocumentRepository.findByDocumentId(documentId);
        if (null != byDocumentId) {
            String docName = byDocumentId.getDocumentName();

            String targetFileName = StorageContext.LODGEMENT_DRAFT_RESERVATION_DETAIL_DOC.getStorageContext() +
                    FileUtils.PATH_SEPARATOR +
                    documentId +
                    FileUtils.EXTENSION_SEPARATOR +
                    FileUtils.getFileExtension(docName);

            return fileStorageService.loadFileAsResource(targetFileName);
        }

        return null;
    }

    /**
     * @param requestId requestId
     * @return Collection<LodgementDraftDocumentDto>
     */
    public Collection<LodgementDraftDocumentDto> getAllDocumentForRequestId(final Long requestId) {

        LodgementDraftRequest byRequestId = lodgementDraftRequestRepository.findByRequestId(requestId);

        if (null != byRequestId) {

            List<LodgementDraftDocument> byLodgementDraftRequest = lodgementDraftDocumentRepository
                    .findByLodgementDraftRequest(byRequestId);

            return byLodgementDraftRequest.stream()
                    .map(lodgementDraftDocumentMapper::lodgementDraftDocumentToLodgementDraftDocumentDto)
                    .collect(Collectors.toList());

        }

        return null;
    }

    /**
     * @param documentId documentId
     */
    public void deleteLodgementDraftDocument(final Long documentId) {
        LodgementDraftDocument byDocumentId = lodgementDraftDocumentRepository
                .findByDocumentId(documentId);

        if (null != byDocumentId) {
            String documentName = byDocumentId.getDocumentName();

            lodgementDraftDocumentRepository.deleteById(documentId);

            String targetLocation = StorageContext.LODGEMENT_DRAFT_RESERVATION_DETAIL_DOC.getStorageContext() +
                    FileUtils.PATH_SEPARATOR +
                    documentId +
                    FileUtils.EXTENSION_SEPARATOR +
                    FileUtils.getFileExtension(documentName);

            fileStorageService.deleteFile(targetLocation);

        }
    }

    /**
     * @param requestId requestId
     */
    public void removeRequestFromStep(final Long requestId) {
        lodgementDraftRequestRepository.deleteById(requestId);
    }


    public Collection<LodgementDraftStepsDto> addStepsByReservationName(final String name, final Long draftId) {
        ReservationDraft byName = reservationDraftRepository.findByName(name);
        LodgementDraft byDraftId = lodgementDraftRepository.findByDraftId(draftId);
        if(null != byName) {
            reservationDraftStepsRepository
                    .findByReservationDraft(byName)
                    .forEach(SystemUtility.withCounter((counter, reservationDraftSteps) -> {
                        // create steps for lodgement
                        Long reasonItemId = reservationDraftSteps.getReasonItemId();

                        LodgementDraftStep lodgementDraftStep = new LodgementDraftStep();
                        lodgementDraftStep.setLodgementDraft(byDraftId);
                        int i = counter + 1;
                        lodgementDraftStep.setStepNo((long) i);
                        lodgementDraftStep.setDocumentItemId(606L);

                        listItemRepository
                                .getListItemByParentItemIdAndListCode(reasonItemId, 387L)
                                .stream()
                                .findFirst()
                                .ifPresent(listItem -> lodgementDraftStep.setPurposeItemId(listItem.getItemId()));


                        // save lodgement step
                        LodgementDraftStep lodgementDraftStep1 = lodgementDraftStepsRepository
                                .saveAndFlush(lodgementDraftStep);

                        // cretae lodgement request
                        List<ReservationOutcome> byReservationDraftSteps = reservationOutcomeRepository
                                .findByReservationDraftStepsOrderByParcelAscPortionAsc(reservationDraftSteps);

                        byReservationDraftSteps.forEach(reservationOutcome -> {
                            // create lodgement draft step request
                            LodgementDraftRequest lodgementDraftRequest = new LodgementDraftRequest();
                            lodgementDraftRequest.setLodgementDraftStep(lodgementDraftStep1);
                            lodgementDraftRequest.setRequestId(reservationOutcome.getRequestId());
                            lodgementDraftRequest.setDesignation(reservationOutcome.getDesignation());
                            lodgementDraftRequest.setLocationId(reservationOutcome.getLocationId());
                            lodgementDraftRequest.setOutcomeIdReservation(reservationOutcome.getOutcomeId());
                            lodgementDraftRequest.setReservationWorkflowId(reservationOutcome.getWorkflowId());
                            lodgementDraftRequest.setLpi(reservationOutcome.getLpi());

                            Long requestId = reservationOutcome.getRequestId();

                            ReservationDraftRequest byDraftRequestId = reservationDraftRequestRepository
                                    .findByDraftRequestId(requestId);

                            String parentParcel = "";
                            try {
                                if (null != byDraftRequestId) {
                                    byDraftRequestId.setReservationDraftSteps(null);
                                    parentParcel = objectMapper.writeValueAsString(byDraftRequestId);
                                }

                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                                log.error(" Exception occur while serializing ReservationDraftRequest : {}", byDraftRequestId);
                            }

                            lodgementDraftRequest.setParentParcels(parentParcel);

                            // save lodgement draft step request
                            lodgementDraftRequestRepository.save(lodgementDraftRequest);

                        });

                    }));
        }

        // fetch lodgment draft steps based on draftId
        LodgementDraft byDraftId1 = lodgementDraftRepository.findByDraftId(draftId);
        if (null != byDraftId1) {

            return lodgementDraftStepsRepository
                    .findByLodgementDraft(byDraftId1)
                    .stream()
                    .map(lodgementDraftStepsMapper::lodgmentDraftStepsToLodgmentDraftStepsDto)
                    .collect(Collectors.toList());
        }

        return null;

    }

    /**
     *
     * @param fileRefNo fileRefNo
     * @param draftId draftId
     * @return Collection<LodgementDraftStepsDto>
     */
    public Collection<LodgementDraftStepsDto> addStepsByReservationFileRef(final String fileRefNo, final Long draftId) {
        ReservationDraft byFileRef = reservationDraftRepository.findByFileRef(fileRefNo);
        LodgementDraft byDraftId = lodgementDraftRepository.findByDraftId(draftId);
        if(null != byFileRef) {
            reservationDraftStepsRepository
                    .findByReservationDraft(byFileRef)
                    .forEach(SystemUtility.withCounter((counter, reservationDraftSteps) -> {
                        // create steps for lodgement
                        Long reasonItemId = reservationDraftSteps.getReasonItemId();

                        LodgementDraftStep lodgementDraftStep = new LodgementDraftStep();
                        lodgementDraftStep.setLodgementDraft(byDraftId);
                        int i = counter + 1;
                        lodgementDraftStep.setStepNo((long) i);
                        lodgementDraftStep.setDocumentItemId(606L);

                        listItemRepository
                                .getListItemByParentItemIdAndListCode(reasonItemId, 387L)
                                .stream()
                                .findFirst()
                                .ifPresent(listItem -> lodgementDraftStep.setPurposeItemId(listItem.getItemId()));


                        // save lodgement step
                        LodgementDraftStep lodgementDraftStep1 = lodgementDraftStepsRepository
                                .saveAndFlush(lodgementDraftStep);

                        // cretae lodgement request
                        List<ReservationOutcome> byReservationDraftSteps = reservationOutcomeRepository
                                .findByReservationDraftStepsOrderByParcelAscPortionAsc(reservationDraftSteps);

                        byReservationDraftSteps.forEach(reservationOutcome -> {
                            // create lodgement draft step request
                            LodgementDraftRequest lodgementDraftRequest = new LodgementDraftRequest();
                            lodgementDraftRequest.setLodgementDraftStep(lodgementDraftStep1);
                            lodgementDraftRequest.setRequestId(reservationOutcome.getRequestId());
                            lodgementDraftRequest.setDesignation(reservationOutcome.getDesignation());
                            lodgementDraftRequest.setLocationId(reservationOutcome.getLocationId());
                            lodgementDraftRequest.setOutcomeIdReservation(reservationOutcome.getOutcomeId());
                            lodgementDraftRequest.setReservationWorkflowId(reservationOutcome.getWorkflowId());
                            lodgementDraftRequest.setLpi(reservationOutcome.getLpi());

                            Long requestId = reservationOutcome.getRequestId();

                            ReservationDraftRequest byDraftRequestId = reservationDraftRequestRepository
                                    .findByDraftRequestId(requestId);

                            String parentParcel = "";
                            try {
                                if (null != byDraftRequestId) {
                                    byDraftRequestId.setReservationDraftSteps(null);
                                    parentParcel = objectMapper.writeValueAsString(byDraftRequestId);
                                }

                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                                log.error(" Exception occur while serializing ReservationDraftRequest : {}", byDraftRequestId);
                            }

                            lodgementDraftRequest.setParentParcels(parentParcel);

                            // save lodgement draft step request
                            lodgementDraftRequestRepository.save(lodgementDraftRequest);

                        });

                    }));
        }

        // fetch lodgment draft steps based on draftId
        LodgementDraft byDraftId1 = lodgementDraftRepository.findByDraftId(draftId);
        if (null != byDraftId1) {

            return lodgementDraftStepsRepository
                    .findByLodgementDraft(byDraftId1)
                    .stream()
                    .map(lodgementDraftStepsMapper::lodgmentDraftStepsToLodgmentDraftStepsDto)
                    .collect(Collectors.toList());
        }

        return null;

    }

    /**
     * @param referenceNo referenceNo
     */
    public Collection<LodgementDraftStepsDto> addStepsByReservationRef(final String referenceNo,
                                                                       final Long draftId) {

        Workflow byReferenceNo = workflowRepository.findByReferenceNo(referenceNo);

        if (null != byReferenceNo) {

            ReservationDraft byWorkflowId = reservationDraftRepository
                    .findByWorkflowId(byReferenceNo.getWorkflowId());

            LodgementDraft byDraftId = lodgementDraftRepository.findByDraftId(draftId);

            reservationDraftStepsRepository
                    .findByReservationDraft(byWorkflowId)
                    .forEach(SystemUtility.withCounter((counter, reservationDraftSteps) -> {
                        // create steps for lodgement
                        Long reasonItemId = reservationDraftSteps.getReasonItemId();

                        LodgementDraftStep lodgementDraftStep = new LodgementDraftStep();
                        lodgementDraftStep.setLodgementDraft(byDraftId);
                        int i = counter + 1;
                        lodgementDraftStep.setStepNo((long) i);
                        lodgementDraftStep.setDocumentItemId(606L);

                        listItemRepository
                                .getListItemByParentItemIdAndListCode(reasonItemId, 387L)
                                .stream()
                                .findFirst()
                                .ifPresent(listItem -> lodgementDraftStep.setPurposeItemId(listItem.getItemId()));


                        // save lodgement step
                        LodgementDraftStep lodgementDraftStep1 = lodgementDraftStepsRepository
                                .saveAndFlush(lodgementDraftStep);

                        // cretae lodgement request
                        List<ReservationOutcome> byReservationDraftSteps = reservationOutcomeRepository
                                .findByReservationDraftStepsOrderByParcelAscPortionAsc(reservationDraftSteps);

                        byReservationDraftSteps.forEach(reservationOutcome -> {
                            // create lodgement draft step request
                            LodgementDraftRequest lodgementDraftRequest = new LodgementDraftRequest();
                            lodgementDraftRequest.setLodgementDraftStep(lodgementDraftStep1);
                            lodgementDraftRequest.setRequestId(reservationOutcome.getRequestId());
                            lodgementDraftRequest.setDesignation(reservationOutcome.getDesignation());
                            lodgementDraftRequest.setLocationId(reservationOutcome.getLocationId());
                            lodgementDraftRequest.setOutcomeIdReservation(reservationOutcome.getOutcomeId());
                            lodgementDraftRequest.setReservationWorkflowId(reservationOutcome.getWorkflowId());
                            lodgementDraftRequest.setLpi(reservationOutcome.getLpi());

                            Long requestId = reservationOutcome.getRequestId();

                            ReservationDraftRequest byDraftRequestId = reservationDraftRequestRepository
                                    .findByDraftRequestId(requestId);

                            String parentParcel = "";
                            try {
                                if (null != byDraftRequestId) {
                                    byDraftRequestId.setReservationDraftSteps(null);
                                    parentParcel = objectMapper.writeValueAsString(byDraftRequestId);
                                }

                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                                log.error(" Exception occur while serializing ReservationDraftRequest : {}", byDraftRequestId);
                            }

                            lodgementDraftRequest.setParentParcels(parentParcel);

                            // save lodgement draft step request
                            lodgementDraftRequestRepository.save(lodgementDraftRequest);

                        });

                    }));
        }

        // fetch lodgment draft steps based on draftId
        LodgementDraft byDraftId1 = lodgementDraftRepository.findByDraftId(draftId);
        if (null != byDraftId1) {

            return lodgementDraftStepsRepository
                    .findByLodgementDraft(byDraftId1)
                    .stream()
                    .map(lodgementDraftStepsMapper::lodgmentDraftStepsToLodgmentDraftStepsDto)
                    .collect(Collectors.toList());
        }

        return null;
    }

    /**
     * @param draftId draftId
     * @param stepId  stepId
     * @return Collection<LodgementDocumentSummary>
     */
    public Collection<LodgementDocumentSummaryDto> getDocumentSummary(final Long draftId, final Long stepId) {
        return lodgementDocumentSummaryService.getDocumentSummary(draftId, stepId);
    }

    /**
     * @param draftId draftId
     * @return {@link  Resource}
     */
    public Resource generatePerformaInvoice(final Long draftId) {
        String generatedFilePath = lodgementPerformaInvoiceGenerationService.generatePerformaInvoice(draftId);
        return new FileSystemResource(generatedFilePath);
    }


    /**
     * @param lodgementBatchDto {@link LodgementBatchDto}
     * @return {@link LodgementBatchDto}
     */
    public LodgementBatchDto issueBatch(LodgementBatchDto lodgementBatchDto) {
        LodgementBatch lodgementBatch = lodgementBatchMapper
                .lodgementBatchDtoToLodgementBatch(lodgementBatchDto);

        Long maxBatch = lodgementBatchRepository.getMaxBatch(lodgementBatch.getProvinceId());

        if (null != maxBatch && maxBatch > 0) {
            Long batchNo = maxBatch + 1;
            lodgementBatch.setBatchNumber(batchNo);
            LocalDateTime createdOn = lodgementBatch.getCreatedOn();
            int year = createdOn.getYear();
            String batchNumberText = batchNo + "/" + year;
            lodgementBatch.setBatchNumberText(batchNumberText);
        } else {
            Long batchNo = 1L;
            lodgementBatch.setBatchNumber(batchNo);
            LocalDateTime createdOn = lodgementBatch.getCreatedOn();
            int year = createdOn.getYear();
            String batchNumberText = batchNo + "/" + year;
            lodgementBatch.setBatchNumberText(batchNumberText);
        }

        Set<LodgementBatchSgDocument> lodgementBatchSgDocuments = new HashSet<>();
        lodgementDocumentSummaryService
                .getDocumentSummary(lodgementBatchDto.getDraftId(), 0L)
                .forEach(lodgementDocumentSummaryDto -> {

                    Long maxDocumentNumber = lodgementBatchSgDocumentRepository.getMaxDocumentNumber(lodgementBatch.getProvinceId(),
                            lodgementDocumentSummaryDto.getDocumentItemId());

                    if (null != maxDocumentNumber && maxDocumentNumber > 0) {
                        for (int i = 0; i < lodgementDocumentSummaryDto.getCount(); i++) {
                            LodgementBatchSgDocument lodgementBatchSgDocument = new LodgementBatchSgDocument();
                            lodgementBatchSgDocument.setLodgementBatch(lodgementBatch);
                            lodgementBatchSgDocument.setDocTypeItemId(lodgementDocumentSummaryDto.getDocumentItemId());
                            lodgementBatchSgDocument.setProvinceId(lodgementBatch.getProvinceId());
                            Long docNumber = maxDocumentNumber + (i + 1);
                            lodgementBatchSgDocument.setDocNumber(docNumber);
                            int year = LocalDateTime.now().getYear();
                            String docNumberText;
                            if (lodgementDocumentSummaryDto.getDocumentItemId().equals(1068L)) {
                                docNumberText = "SR" + docNumber + "/" + year;
                            } else {
                                docNumberText = docNumber + "/" + year;
                            }
                            lodgementBatchSgDocument.setDocNumberText(docNumberText);
                            lodgementBatchSgDocument.setCreatedOn(LocalDateTime.now());
                            lodgementBatchSgDocuments.add(lodgementBatchSgDocument);
                        }

                    } else {
                        for (int i = 0; i < lodgementDocumentSummaryDto.getCount(); i++) {
                            LodgementBatchSgDocument lodgementBatchSgDocument = new LodgementBatchSgDocument();
                            lodgementBatchSgDocument.setLodgementBatch(lodgementBatch);
                            lodgementBatchSgDocument.setDocTypeItemId(lodgementDocumentSummaryDto.getDocumentItemId());
                            lodgementBatchSgDocument.setProvinceId(lodgementBatch.getProvinceId());
                            Long documentNumber = i + 1L;
                            lodgementBatchSgDocument.setDocNumber(documentNumber);
                            int year = LocalDateTime.now().getYear();
                            String docNumberText;
                            if (lodgementDocumentSummaryDto.getDocumentItemId().equals(1068L)) {
                                docNumberText = "SR" + documentNumber + "/" + year;
                            } else {
                                docNumberText = documentNumber + "/" + year;
                            }
                            lodgementBatchSgDocument.setDocNumberText(docNumberText);
                            lodgementBatchSgDocument.setCreatedOn(LocalDateTime.now());
                            lodgementBatchSgDocuments.add(lodgementBatchSgDocument);
                        }
                    }
                });

        lodgementBatch.setLodgementBatchSgDocuments(lodgementBatchSgDocuments);

        LodgementBatch savedBatch = lodgementBatchRepository.save(lodgementBatch);

        return lodgementBatchMapper.lodgementBatchToLodgementBatchDto(savedBatch);
    }

    /**
     * @param draftId draftId
     * @return {@link Resource}
     */
    public Resource generateAcknowledgementLetter(Long draftId) {
        String generatedFilePath = lodgementDraftAcknowledgementLetterService.generateAckLetter(draftId);
        return new FileSystemResource(generatedFilePath);
    }

    /**
     * @param draftId draftId
     * @return {@link LodgementBatchDto}
     */
    public LodgementBatchDto getbatchDetails(Long draftId) {
            LodgementBatch byDraftId = lodgementBatchRepository.findByDraftId(draftId);

            if (null != byDraftId) {
                return lodgementBatchMapper.lodgementBatchToLodgementBatchDto(byDraftId);
            }
        return null;
    }

    /**
     * @param draftId draftId
     * @return Collection<LodgementDraftDocumentDto>
     */
    public Collection<LodgementDraftDocumentDto> getAllDocuments(Long draftId) {
        LodgementDraft byDraftId = lodgementDraftRepository.findByDraftId(draftId);
        if (null != byDraftId) {

            return lodgementDraftDocumentRepository
                    .findByLodgementDraft(byDraftId)
                    .stream()
                    .map(lodgementDraftDocumentMapper::lodgementDraftDocumentToLodgementDraftDocumentDto)
                    .collect(Collectors.toList());


        }
        return null;
    }
}
