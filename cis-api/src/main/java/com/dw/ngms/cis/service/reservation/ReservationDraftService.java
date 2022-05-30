package com.dw.ngms.cis.service.reservation;

import com.dw.ngms.cis.dto.SgdataParcelsDto;
import com.dw.ngms.cis.enums.ReservationReason;
import com.dw.ngms.cis.enums.StorageContext;
import com.dw.ngms.cis.mappers.SgDataParcelsMapper;
import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.domains.prepackage.Location;
import com.dw.ngms.cis.persistence.domains.reservation.*;
import com.dw.ngms.cis.persistence.domains.user.SecurityUser;
import com.dw.ngms.cis.persistence.projection.reservation.ReservationDraftListingProjection;
import com.dw.ngms.cis.persistence.projection.reservation.ReservationRecordIdVerificationProjection;
import com.dw.ngms.cis.persistence.projection.reservation.ReservationTransferListProjection;
import com.dw.ngms.cis.persistence.repository.SgdataParcelsRepository;
import com.dw.ngms.cis.persistence.repository.UserRepository;
import com.dw.ngms.cis.persistence.repository.prepackage.LocationRepository;
import com.dw.ngms.cis.persistence.repository.reservation.*;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowActionRepository;
import com.dw.ngms.cis.security.CurrentLoggedInUser;
import com.dw.ngms.cis.service.EmailServiceHandler;
import com.dw.ngms.cis.service.FileStorageService;
import com.dw.ngms.cis.service.dto.reservation.*;
import com.dw.ngms.cis.service.mapper.reservation.*;
import com.dw.ngms.cis.service.report.ProductivityReportGenerator;
import com.dw.ngms.cis.utilities.FileUtils;
import com.dw.ngms.cis.web.vm.reservation.ReservationNotificationVm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class ReservationDraftService {

    private final CurrentLoggedInUser currentLoggedInUser;

    private final ReservationDraftRepository reservationDraftRepository;

    private final ReservationDraftMapper reservationDraftMapper;

    private final ReservationDraftDocumentsRepository reservationDraftDocumentsRepository;

    private final ReservationDraftDocumentsMapper reservationDraftDocumentsMapper;

    private final FileStorageService fileStorageService;

    private final SgdataParcelsRepository sgdataParcelsRepository;

    private final SgDataParcelsMapper sgDataParcelsMapper;

    private final ReservationDraftRequestMapper reservationDraftRequestMapper;

    private final ReservationDraftRequestRepository reservationDraftRequestRepository;

    private final ReservationDraftStepsRepository reservationDraftStepsRepository;

    private final ReservationDraftStepsMapper reservationDraftStepsMapper;

    private final ReservationDraftRequestOutcomeRepository reservationDraftRequestOutcomeRepository;

    private final ReservationDraftRequestOutcomeMapper reservationDraftRequestOutcomeMapper;

    private final LocationRepository locationRepository;

    private final ReservationDraftNumberingHelper reservationDraftNumberingHelper;

    private final ReservationConditionMapper reservationConditionMapper;

    private final ReservationConditionRepository reservationConditionRepository;

    private final ReservationAcknowledgementLetterService reservationAcknowledgementLetterService;

    private final UserRepository userRepository;

    private final EmailServiceHandler emailServiceHandler;

    private final WorkflowActionRepository workflowActionRepository;

    private final ObjectMapper objectMapper;

    private final ReservationDraftTransferRepository reservationDraftTransferRepository;

    private final ReservationDraftTransferMapper reservationDraftTransferMapper;


    public Page<ReservationDraftDto> getAllReservationDraft(final Long processId,
                                                            final Pageable pageable) {
        SecurityUser user = currentLoggedInUser.getUser();
        Page<ReservationDraft> shortReservationDrafts = reservationDraftRepository.findShortReservationDrafts(user.getUserId(),
                user.getUserId(),
                processId,
                pageable);
        return shortReservationDrafts
                .map(reservationDraftMapper::reservationDraftToReservationDraftDto);
    }

    public ReservationDraftDto saveReservationDraft
            (final ReservationDraftDto reservationDraftDto) {
        ReservationDraft reservationDraft = reservationDraftMapper.
                reservationDraftDtoToReservationDraft(reservationDraftDto);

        reservationDraft.setIsActive(1L);

        ReservationDraft saveReservationDraft = reservationDraftRepository.save(reservationDraft);
        return reservationDraftMapper.reservationDraftToReservationDraftDto(saveReservationDraft);
    }

    public ReservationDraftDto updateReservationDraft
            (final ReservationDraftDto reservationDraftDto) {
        ReservationDraft reservationDraft = reservationDraftMapper.
                reservationDraftDtoToReservationDraft(reservationDraftDto);

        ReservationDraft saveReservationDraft = reservationDraftRepository.save(reservationDraft);
        return reservationDraftMapper.reservationDraftToReservationDraftDto(saveReservationDraft);
    }

    public ReservationDraftDocumentsDto saveReservationDocument(final ReservationDraftDocumentsDto reservationDraftDocumentsDto) {


        // save object in db
        ReservationDraftDocument reservationDraftDocument = reservationDraftDocumentsMapper
                .reservationDraftDocumentDtoToReservationDraftDocument(reservationDraftDocumentsDto);
        reservationDraftDocumentsRepository.save(reservationDraftDocument);

        // save file on disk
        String targetLocation = StorageContext.RESERVATION_DRAFT.getStorageContext() +
                FileUtils.PATH_SEPARATOR +
                reservationDraftDocument.getDocumentId();

        String fileName = fileStorageService.storeFile(reservationDraftDocumentsDto.getDocument(), targetLocation);

        // set fileName
        reservationDraftDocument.setName(fileName);

        // save final reservationDraft
        ReservationDraftDocument savedReservationDraftDocument = reservationDraftDocumentsRepository.save(reservationDraftDocument);


        ReservationDraftDocumentsDto reservationDraftDocumentsDto1 = reservationDraftDocumentsMapper
                .reservationDraftDocumentToReservationDraftDocumentDto(savedReservationDraftDocument);

        reservationDraftDocumentsDto1.setDocumentName(fileName);

        return reservationDraftDocumentsDto1;

    }

    /**
     * @param documentId documentId
     */
    public void removeAnnuxure(final Long documentId) {
        ReservationDraftDocument byDocumentId = reservationDraftDocumentsRepository.findByDocumentId(documentId);
        String name = byDocumentId.getName();
        reservationDraftDocumentsRepository.deleteById(documentId);
        String targetLocation = StorageContext.RESERVATION_DRAFT.getStorageContext() +
                FileUtils.PATH_SEPARATOR +
                documentId +
                FileUtils.EXTENSION_SEPARATOR +
                FileUtils.getFileExtension(name);

        fileStorageService.deleteFile(targetLocation);
    }

    public org.springframework.core.io.Resource getAnnuxure(Long documentId) {
        ReservationDraftDocument byDocumentId = reservationDraftDocumentsRepository.findByDocumentId(documentId);
        String name = byDocumentId.getName();
        String targetFileName = StorageContext.RESERVATION_DRAFT.getStorageContext() +
                FileUtils.PATH_SEPARATOR +
                documentId +
                FileUtils.EXTENSION_SEPARATOR +
                FileUtils.getFileExtension(name);
        return fileStorageService.loadFileAsResource(targetFileName);
    }

    /**
     * @param draftId draftId
     * @return {@link ReservationDraftDto}
     */
    public ReservationDraftDto getDraft(final Long draftId) {
        ReservationDraft byDraftId = reservationDraftRepository.findByDraftId(draftId);
        if (null != byDraftId) {
            return reservationDraftMapper.reservationDraftToReservationDraftDto(byDraftId);
        }
        return null;
    }

    /**
     * @param searchTerm searchTerm
     * @param provinceId provinceId
     * @return Collection<SgdataParcelsDto>
     */
    public Collection<SgdataParcelsDto> searchReservationRequests(final String searchTerm,
                                                                  final Long provinceId,
                                                                  final Long locationId) {
        String search = "%" + searchTerm + "%";
        return sgdataParcelsRepository.findUsingTextSearchForReservationDraft(search,
                        provinceId,
                        locationId,
                        search,
                        search).stream()
                .map(sgDataParcelsMapper::sgdataParcelsToSgdataParcelsDto)
                .collect(Collectors.toList());
    }

    /**
     * @param reservationDraftRequestDto {@link ReservationDraftRequestDto}
     * @return {@link ReservationDraftRequestDto}
     */
    public ReservationDraftRequestDto
    addReservationDraftRequest(final ReservationDraftRequestDto reservationDraftRequestDto) {
        ReservationDraftRequest reservationDraftRequest = reservationDraftRequestMapper
                .reservationDraftRequestDtoToReservationDraftRequest(reservationDraftRequestDto);
        ReservationDraftRequest save = reservationDraftRequestRepository.save(reservationDraftRequest);
        return reservationDraftRequestMapper.reservationDraftRequestToReservationDraftRequestDto(save);
    }

    /**
     * @param stepId stepId
     * @return Collection<ReservationDraftRequestDto>
     */
    public Collection<ReservationDraftRequestDto> getAllReservationRequestForStepId(final Long stepId) {
        ReservationDraftSteps byStepId = reservationDraftStepsRepository.findByStepId(stepId);
        List<ReservationDraftRequest> reservationDraftRequests = reservationDraftRequestRepository
                .findByReservationDraftSteps(byStepId);
        return reservationDraftRequests.stream()
                .map(reservationDraftRequestMapper::reservationDraftRequestToReservationDraftRequestDto)
                .collect(Collectors.toList());

    }

    /**
     * @param reservationDraftStepsDto {@link ReservationDraftStepsDto}
     * @return {@link ReservationDraftStepsDto}
     */
    public ReservationDraftStepsDto addDraftSteps(final ReservationDraftStepsDto reservationDraftStepsDto) {
        ReservationDraftSteps reservationDraftSteps = reservationDraftStepsMapper
                .reservationDraftStepsDtoToReservationDraftSteps(reservationDraftStepsDto);

        ReservationDraftSteps savedReservationDraftStep = reservationDraftStepsRepository.save(reservationDraftSteps);

        Long stepNo = savedReservationDraftStep.getStepNo();
        if (stepNo > 1L) {
            // add outcome of previous step to res_draft_request with given stepNo.
            Long previousStepNo = stepNo - 1;
            ReservationDraftSteps byStepId = reservationDraftStepsRepository
                    .findByReservationDraftAndStepNo(savedReservationDraftStep.getReservationDraft(), previousStepNo);
            if (null != byStepId) {
                List<ReservationDraftRequestOutcome> byReservationDraftSteps = reservationDraftRequestOutcomeRepository
                        .findByReservationDraftSteps(byStepId);
                if (CollectionUtils.isNotEmpty(byReservationDraftSteps)) {
                    byReservationDraftSteps.forEach(reservationDraftRequestOutcome -> {
                        ReservationDraftRequest reservationDraftRequest = new ReservationDraftRequest();
                        reservationDraftRequest.setReservationDraftSteps(savedReservationDraftStep);
                        reservationDraftRequest.setDraftOutcomeId(reservationDraftRequestOutcome.getDraftOutcomeId());
                        reservationDraftRequest.setDesignation(reservationDraftRequestOutcome.getDesignation());
                        reservationDraftRequest.setLocation(reservationDraftRequestOutcome.getLocation());
                        reservationDraftRequest.setLocationId(reservationDraftRequestOutcome.getLocationId());
                        reservationDraftRequest.setParcel(reservationDraftRequestOutcome.getParcel());
                        reservationDraftRequest.setPortion(reservationDraftRequestOutcome.getPortion());
                        reservationDraftRequest.setLpi(reservationDraftRequestOutcome.getLpi());
                        reservationDraftRequest.setRecordTypeId(reservationDraftRequestOutcome.getRecordTypeId());
                        reservationDraftRequestRepository.save(reservationDraftRequest);
                    });
                }
            }
        }

        ReservationDraftSteps byStepId = reservationDraftStepsRepository
                .findByStepId(savedReservationDraftStep.getStepId());

        return reservationDraftStepsMapper.reservationDraftStepsToReservationDraftStepsDto(byStepId);
    }

    /**
     * @param stepId stepId
     * @return Collection<ReservationDraftRequestOutcomeDto>
     */
    public Collection<ReservationDraftRequestOutcomeDto> getDraftRequestOutcome(final Long stepId) {
        ReservationDraftSteps byStepId = reservationDraftStepsRepository.findByStepId(stepId);

        List<ReservationDraftRequestOutcome> byReservationDraftSteps = reservationDraftRequestOutcomeRepository
                .findByReservationDraftSteps(byStepId);

        if (CollectionUtils.isNotEmpty(byReservationDraftSteps)) {
            return byReservationDraftSteps.stream()
                    .map(reservationDraftRequestOutcomeMapper
                            ::reservationDraftRequestOutcomeToReservationDraftRequestOutcomeDto)
                    .collect(Collectors.toList());
        }

        return null;
    }

    public void deleteDraftRequest(final Long draftRequestId) {
        reservationDraftRequestRepository.deleteById(draftRequestId);
    }

    /**
     * @param draftId draftId
     * @return Collection<ReservationDraftStepsDto>
     */
    public Collection<ReservationDraftStepsDto> getAllDraftSteps(Long draftId) {

        ReservationDraft byDraftId = reservationDraftRepository.findByDraftId(draftId);

        if (null != byDraftId) {

            List<ReservationDraftSteps> allByReservationDraft = reservationDraftStepsRepository
                    .findAllByReservationDraftOrderByStepId(byDraftId);

            if (CollectionUtils.isNotEmpty(allByReservationDraft)) {

                return allByReservationDraft.stream()
                        .map(reservationDraftStepsMapper::reservationDraftStepsToReservationDraftStepsDto)
                        .collect(Collectors.toList());
            }

        }

        return null;
    }

    /**
     * @param draftId draftId
     * @return Collection<ReservationDraftDocumentsDto>
     */
    public Collection<ReservationDraftDocumentsDto> getDraftDocuments(final Long draftId) {

        ReservationDraft byDraftId = reservationDraftRepository.findByDraftId(draftId);

        if (null != byDraftId) {

            List<ReservationDraftDocument> allByReservationDraftOrderByDocumentId = reservationDraftDocumentsRepository
                    .findAllByReservationDraftOrderByDocumentId(byDraftId);

            if (CollectionUtils.isNotEmpty(allByReservationDraftOrderByDocumentId)) {
                return allByReservationDraftOrderByDocumentId.stream()
                        .map(reservationDraftDocumentsMapper::reservationDraftDocumentToReservationDraftDocumentDto)
                        .collect(Collectors.toList());
            }
        }

        return null;
    }

    /**
     * @param reservationDraftStepsDto {@link ReservationDraftStepsDto}
     * @return Collection<ReservationDraftRequestOutcomeDto>
     */
    public Collection<ReservationDraftRequestOutcomeDto>
    processDraftStepsRequest(final ReservationDraftStepsDto reservationDraftStepsDto) {

        ReservationDraftSteps reservationDraftSteps = reservationDraftStepsMapper
                .reservationDraftStepsDtoToReservationDraftSteps(reservationDraftStepsDto);
        // update reservationDraftSteps first
        ReservationDraftSteps savedDraftSteps = reservationDraftStepsRepository
                .save(reservationDraftSteps);
        // get data for steId
        Long reasonItemId = savedDraftSteps.getReasonItemId();
        Long locationIdFromDraftRequest = getLocationIdFromDraftRequest(savedDraftSteps);
        Location locationByBoundaryId = locationRepository.findLocationByBoundaryId(locationIdFromDraftRequest);
        String mdbCode = "";
        String algorithm = "";
        if (null != locationByBoundaryId) {
            mdbCode = locationByBoundaryId.getMdbcode();
            String reservationSystem = locationByBoundaryId.getReservationSystem();
            if (StringUtil.isBlank(reservationSystem)) {
                Long parentBoundaryId = locationByBoundaryId.getParentBoundaryId();

                Location locationByBoundaryId1 = locationRepository.findLocationByBoundaryId(parentBoundaryId);
                algorithm = locationByBoundaryId1.getReservationSystem();
            }
        }

        if (reasonItemId.equals(ReservationReason.CONSOLIDATION.getReservationReason())) {
            return processForConsolidation(savedDraftSteps, algorithm, mdbCode);
        } else if (reasonItemId.equals(ReservationReason.SUBDIVISION.getReservationReason())) {
            return processForSubDivision(savedDraftSteps, algorithm, mdbCode);
        } else if (reasonItemId.equals(ReservationReason.LEASE_ERVEN.getReservationReason())) {
            return processForLease(savedDraftSteps, algorithm, mdbCode);
        } else if (reasonItemId.equals(ReservationReason.RE_DESIGNATION.getReservationReason())) {
            return processForReDesignation(savedDraftSteps);
        } else if (reasonItemId.equals(ReservationReason.CREATION_OF_TOWN.getReservationReason())) {
            return processForCreationOfTown(savedDraftSteps);
        } else if(reasonItemId.equals(ReservationReason.EXTENSION_OF_TOWNSHIPS.getReservationReason()) ||
                reasonItemId.equals(ReservationReason.RELAYOUT.getReservationReason()) ) {
            return processForExtensionOfTownship(savedDraftSteps);
        } else if(reasonItemId.equals(ReservationReason.CREATION_OF_FARMS.getReservationReason())) {
            return processForCreationOfFarms(savedDraftSteps);
        } else if(reasonItemId.equals(ReservationReason.STREET_CLOSURE.getReservationReason())) {

        } else if(reasonItemId.equals(ReservationReason.PUBLIC_PLACE_CLOSURE.getReservationReason())) {

        } else if(reasonItemId.equals(ReservationReason.PROCLAMATION.getReservationReason())) {

        } else if(reasonItemId.equals(ReservationReason.EXCISION.getReservationReason())) {

        }

        return null;
    }

    /**
     * @param draftId draftId
     */
    public void deleteDraft(final Long draftId) {
        reservationDraftRepository.deleteById(draftId);
    }

    /**
     * @param stepId stepId
     */
    public void deleteDraftStep(final Long stepId) {
        reservationDraftStepsRepository.deleteById(stepId);
    }


    /**
     * @param userId    userId
     * @param processId processId
     * @param pageable  {@link Pageable}
     * @return {@link  ReservationDraftListingProjection}
     */
    public Page<ReservationDraftListingProjection> getAllReservationDraftForUserId(final Long userId,
                                                                                   final Long processId,
                                                                                   final Pageable pageable) {
        return reservationDraftRepository.loadReservationListing(userId, processId, pageable);
    }

    /**
     * @param draftId    draftId
     * @param workflowId workflowId
     */
    public void checkoutDraft(Long draftId, Long workflowId) {
        ReservationDraft byDraftId = reservationDraftRepository.findByDraftId(draftId);
        if (null != byDraftId) {
            byDraftId.setWorkflowId(workflowId);
            reservationDraftRepository.save(byDraftId);
        }
    }

    /**
     * @param workflowId workflowId
     * @return {@link ReservationDraftDto}
     */
    public ReservationDraftDto getReservationDraftByWorkflowId(final Long workflowId) {
        ReservationDraft byWorkflowId = reservationDraftRepository.findByWorkflowId(workflowId);
        return reservationDraftMapper.reservationDraftToReservationDraftDto(byWorkflowId);
    }


    public void landPartialPortionIssue(final Long draftId) {
        reservationDraftNumberingHelper.issueParcelPortionNumbering(draftId);
    }

    /**
     * @param reservationConditionDto {@link ReservationConditionDto}
     * @return {@link ReservationConditionDto}
     */
    public ReservationConditionDto saveReservationCondition(ReservationConditionDto reservationConditionDto) {
        ReservationCondition reservationCondition = reservationConditionMapper
                .reservationConditionDtoToReservationCondition(reservationConditionDto);
        ReservationCondition save = reservationConditionRepository.save(reservationCondition);
        return reservationConditionMapper.reservationConditionToReservationConditionDto(save);
    }

    /**
     * @param draftId draftId
     * @return Collection<ReservationConditionDto>
     */
    public Collection<ReservationConditionDto> getReservationConditionByDraftId(final Long draftId) {
        List<ReservationCondition> allByDraftId = reservationConditionRepository
                .findAllByDraftIdOrderByConditionId(draftId);
        return allByDraftId.stream()
                .map(reservationConditionMapper::reservationConditionToReservationConditionDto)
                .collect(Collectors.toList());
    }

    /**
     * @param conditionId conditionId
     */
    public void deleteReservationCondition(final Long conditionId) {
        reservationConditionRepository.deleteById(conditionId);
    }

    /**
     * @param draftId draftId
     * @return {@link Resource}
     */
    public Resource generateAcknowledgementLetter(final Long draftId) {
        return reservationAcknowledgementLetterService.generateAcknowledgementLetter(draftId);
    }

    public void notifyForReservation(final ReservationNotificationVm reservationNotificationVm) {
        Long workflowId = reservationNotificationVm.getWorkflowId();
        ReservationDraft byWorkflowId = reservationDraftRepository.findByWorkflowId(workflowId);
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
                    reservationNotificationVm.getTemplateId(),
                    reservationNotificationVm.getReferenceNumber(),
                    reservationNotificationVm.getContext());
        }


    }

    /**
     * @param recordId recordId
     * @return Collection<ReservationRecordIdVerificationProjection>
     */
    public Collection<ReservationRecordIdVerificationProjection> verifyRecord(final Long recordId) {
        return reservationDraftRepository.verifyByRecordId(recordId);
    }

    /**
     * @param draftId   draftId
     * @param outcomeId outcomeId
     * @return {@link ReservationDraftTransferDto}
     */
    public Collection<ReservationTransferListProjection> addDraftToTransfer(final Long draftId, final Long outcomeId) {
        ReservationDraftTransfer reservationDraftTransfer = new ReservationDraftTransfer();
        reservationDraftTransfer.setDraftId(draftId);
        reservationDraftTransfer.setOutcomeId(outcomeId);
        ReservationDraftTransfer savedTransfer = reservationDraftTransferRepository.save(reservationDraftTransfer);
        return reservationDraftTransferRepository.loadReservationTransferListing(draftId);
    }

    /**
     * @param transferId transferId
     */
    public void deleteTransferDraft(Long transferId) {
        reservationDraftTransferRepository.deleteById(transferId);
    }

    /**
     * @param draftId draftId
     * @return Collection<ReservationDraftTransferDto>
     */
    public Collection<ReservationTransferListProjection> getAllReservationDraftTransfer(final Long draftId) {
        return reservationDraftTransferRepository
                .loadReservationTransferListing(draftId);


    }


    public enum SubdivisionParcels {
        A(1), B(2), C(3), D(4), E(5), F(6), G(7), H(8),
        I(9), J(10), K(11), L(12), M(13), N(14), O(15),
        P(16), Q(17), R(18), S(19), T(20),
        U(21), V(22), W(23), X(24), Y(25), Z(26);

        private final Integer code;

        SubdivisionParcels(Integer code) {
            this.code = code;
        }

        private Integer getValue() {
            return this.code;
        }

        public static SubdivisionParcels of(final Integer value) {
            return Stream.of(SubdivisionParcels.values())
                    .filter(subdivisionParcels -> subdivisionParcels.getValue().equals(value))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Argument"));
        }

    }

    private Collection<ReservationDraftRequestOutcomeDto>
    processForCreationOfTown(final ReservationDraftSteps reservationDraftSteps) {
        Set<ReservationDraftRequest> reservationDraftRequests = reservationDraftSteps.getReservationDraftRequests();
        Long noOfParcels = reservationDraftSteps.getParcelRequested();
        Long stepNo = reservationDraftSteps.getStepNo();
        String otherData = reservationDraftSteps.getOtherData();
        Long locationId = null;
        String locationName = null;
        try {

            JsonNode jsonNode = objectMapper.readTree(otherData);
            locationName = jsonNode.get("toLocationName").asText();
            locationId = jsonNode.get("toLocationId").asLong();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("exception occurred while parsing other data");
        }
        Collection<ReservationDraftRequestOutcome> reservationDraftRequestOutcomes = new LinkedList<>();
        if(CollectionUtils.isNotEmpty(reservationDraftRequests)) {
            ReservationDraftRequest reservationDraftRequest = reservationDraftRequests.stream()
                    .findFirst().orElseGet(null);

            if (null != reservationDraftRequest) {
                for (int i = 1; i <= noOfParcels; i++) {
                    ReservationDraftRequestOutcome reservationDraftRequestOutcome =
                            new ReservationDraftRequestOutcome();
                    reservationDraftRequestOutcome.setReservationDraftSteps(reservationDraftRequest.getReservationDraftSteps());

                    String parcel = "S" + stepNo + "-" + SubdivisionParcels.of(i).name();
                    String designation = "Erf " + parcel + " " + locationName;
                    reservationDraftRequestOutcome.setParcel(parcel);
                    reservationDraftRequestOutcome.setPortion("00000");
                    reservationDraftRequestOutcome.setRecordTypeId(reservationDraftRequest.getRecordTypeId());
                    reservationDraftRequestOutcome.setDesignation(designation);
                    reservationDraftRequestOutcome.setLocation(locationName);
                    reservationDraftRequestOutcome.setLocationId(locationId);
                    reservationDraftRequestOutcomes.add(reservationDraftRequestOutcome);
                }
            }

        }

        List<ReservationDraftRequestOutcome> reservationDraftRequestOutcomes1 = reservationDraftRequestOutcomeRepository
                .saveAll(reservationDraftRequestOutcomes);

        return reservationDraftRequestOutcomes1.stream()
                .map(reservationDraftRequestOutcomeMapper
                        ::reservationDraftRequestOutcomeToReservationDraftRequestOutcomeDto)
                .collect(Collectors.toList());
    }

    private Collection<ReservationDraftRequestOutcomeDto>
    processForCreationOfFarms(ReservationDraftSteps reservationDraftSteps) {
        Set<ReservationDraftRequest> reservationDraftRequests = reservationDraftSteps.getReservationDraftRequests();
        Long noOfParcels = reservationDraftSteps.getParcelRequested();
        Long stepNo = reservationDraftSteps.getStepNo();
        String otherData = reservationDraftSteps.getOtherData();
        Long locationId = null;
        String locationName = null;
        try {

            JsonNode jsonNode = objectMapper.readTree(otherData);
            locationName = jsonNode.get("toLocationName").asText();
            locationId = jsonNode.get("toLocationId").asLong();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("exception occurred while parsing other data");
        }
        Collection<ReservationDraftRequestOutcome> reservationDraftRequestOutcomes = new LinkedList<>();
        if(CollectionUtils.isNotEmpty(reservationDraftRequests)) {
            ReservationDraftRequest reservationDraftRequest = reservationDraftRequests.stream()
                    .findFirst().orElseGet(null);

            if (null != reservationDraftRequest) {
                for (int i = 1; i <= noOfParcels; i++) {
                    ReservationDraftRequestOutcome reservationDraftRequestOutcome =
                            new ReservationDraftRequestOutcome();
                    reservationDraftRequestOutcome.setReservationDraftSteps(reservationDraftRequest.getReservationDraftSteps());

                    String parcel = "S" + stepNo + "-" + SubdivisionParcels.of(i).name();
                    String designation = "The farm No " + parcel + " " + locationName;
                    reservationDraftRequestOutcome.setParcel(parcel);
                    reservationDraftRequestOutcome.setPortion("00000");
                    reservationDraftRequestOutcome.setDesignation(designation);
                    reservationDraftRequestOutcome.setLocation(locationName);
                    reservationDraftRequestOutcome.setLocationId(locationId);
                    reservationDraftRequestOutcomes.add(reservationDraftRequestOutcome);
                }
            }

        }

        List<ReservationDraftRequestOutcome> reservationDraftRequestOutcomes1 = reservationDraftRequestOutcomeRepository
                .saveAll(reservationDraftRequestOutcomes);

        return reservationDraftRequestOutcomes1.stream()
                .map(reservationDraftRequestOutcomeMapper
                        ::reservationDraftRequestOutcomeToReservationDraftRequestOutcomeDto)
                .collect(Collectors.toList());
    }

    private Collection<ReservationDraftRequestOutcomeDto>
    processForExtensionOfTownship(ReservationDraftSteps reservationDraftSteps) {
        Set<ReservationDraftRequest> reservationDraftRequests = reservationDraftSteps.getReservationDraftRequests();
        Long noOfParcels = reservationDraftSteps.getParcelRequested();
        Long stepNo = reservationDraftSteps.getStepNo();
        String otherData = reservationDraftSteps.getOtherData();
        Long locationId = null;
        String locationName = null;
        try {

            JsonNode jsonNode = objectMapper.readTree(otherData);
            locationName = jsonNode.get("toLocationName").asText();
            locationId = jsonNode.get("toLocationId").asLong();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("exception occurred while parsing other data");
        }
        Collection<ReservationDraftRequestOutcome> reservationDraftRequestOutcomes = new LinkedList<>();
        if(CollectionUtils.isNotEmpty(reservationDraftRequests)) {
            ReservationDraftRequest reservationDraftRequest = reservationDraftRequests.stream()
                    .findFirst().orElseGet(null);

            if (null != reservationDraftRequest) {
                for (int i = 1; i <= noOfParcels; i++) {
                    ReservationDraftRequestOutcome reservationDraftRequestOutcome =
                            new ReservationDraftRequestOutcome();
                    reservationDraftRequestOutcome.setReservationDraftSteps(reservationDraftRequest.getReservationDraftSteps());

                    String parcel = "S" + stepNo + "-" + SubdivisionParcels.of(i).name();
                    String designation = "Erf " + parcel + " " + locationName;
                    reservationDraftRequestOutcome.setParcel(parcel);
                    reservationDraftRequestOutcome.setPortion("00000");
                    reservationDraftRequestOutcome.setDesignation(designation);
                    reservationDraftRequestOutcome.setLocation(locationName);
                    reservationDraftRequestOutcome.setLocationId(locationId);
                    reservationDraftRequestOutcomes.add(reservationDraftRequestOutcome);
                }
            }

        }

        List<ReservationDraftRequestOutcome> reservationDraftRequestOutcomes1 = reservationDraftRequestOutcomeRepository
                .saveAll(reservationDraftRequestOutcomes);

        return reservationDraftRequestOutcomes1.stream()
                .map(reservationDraftRequestOutcomeMapper
                        ::reservationDraftRequestOutcomeToReservationDraftRequestOutcomeDto)
                .collect(Collectors.toList());
    }

    private Collection<ReservationDraftRequestOutcomeDto> processForReDesignation(final ReservationDraftSteps reservationDraftSteps) {
        Set<ReservationDraftRequest> reservationDraftRequests = reservationDraftSteps.getReservationDraftRequests();
        Long stepNo = reservationDraftSteps.getStepNo();
        String otherData = reservationDraftSteps.getOtherData();
        Long locationId = null;
        String locationName = null;
        try {

            JsonNode jsonNode = objectMapper.readTree(otherData);
            locationName = jsonNode.get("toLocationName").asText();
            locationId = jsonNode.get("toLocationId").asLong();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("exception occurred while parsing other data");
        }
        Collection<ReservationDraftRequestOutcome> reservationDraftRequestOutcomes = new LinkedList<>();
        String finalLocationName = locationName;
        Long finalLocationId = locationId;
        reservationDraftRequests.forEach(ProductivityReportGenerator.withCounter((i, reservationDraftRequest) -> {
            ReservationDraftRequestOutcome reservationDraftRequestOutcome = new ReservationDraftRequestOutcome();
            reservationDraftRequestOutcome.setReservationDraftSteps(reservationDraftRequest.getReservationDraftSteps());
            reservationDraftRequestOutcome.setRecordTypeId(reservationDraftRequest.getRecordTypeId());
            String parcel = "S" + stepNo + "-" + SubdivisionParcels.of(i + 1).name();
            String designation = "Erf " + parcel + " " + finalLocationName;
            reservationDraftRequestOutcome.setParcel(parcel);
            reservationDraftRequestOutcome.setPortion("00000");
            reservationDraftRequestOutcome.setDesignation(designation);
            reservationDraftRequestOutcome.setLocation(finalLocationName);
            reservationDraftRequestOutcome.setLocationId(finalLocationId);
            reservationDraftRequestOutcomes.add(reservationDraftRequestOutcome);
        }));

        List<ReservationDraftRequestOutcome> reservationDraftRequestOutcomes1 = reservationDraftRequestOutcomeRepository
                .saveAll(reservationDraftRequestOutcomes);

        return reservationDraftRequestOutcomes1.stream()
                .map(reservationDraftRequestOutcomeMapper
                        ::reservationDraftRequestOutcomeToReservationDraftRequestOutcomeDto)
                .collect(Collectors.toList());

    }

    private Collection<ReservationDraftRequestOutcomeDto> processForLease(final ReservationDraftSteps reservationDraftSteps,
                                                                          final String algorithm,
                                                                          final String mdbCode) {
        Set<ReservationDraftRequest> reservationDraftRequests = reservationDraftSteps.getReservationDraftRequests();

        Long noOfParcels = reservationDraftSteps.getParcelRequested();
        Long stepNo = reservationDraftSteps.getStepNo();
        Collection<ReservationDraftRequestOutcome> reservationDraftRequestOutcomes = new LinkedList<>();
        if (CollectionUtils.isNotEmpty(reservationDraftRequests)) {
            ReservationDraftRequest reservationDraftRequest = reservationDraftRequests.stream()
                    .findFirst().orElseGet(null);
            if (null != reservationDraftRequest) {
                for (int i = 1; i <= noOfParcels; i++) {
                    ReservationDraftRequestOutcome reservationDraftRequestOutcome =
                            new ReservationDraftRequestOutcome();
                    reservationDraftRequestOutcome.setReservationDraftSteps(reservationDraftRequest.getReservationDraftSteps());
                    reservationDraftRequestOutcome.setPortion(reservationDraftRequest.getPortion());
                    reservationDraftRequestOutcome.setParcel(reservationDraftRequest.getParcel());
                    String designation = "Lease S" + stepNo + "-" + SubdivisionParcels.of(i).name();
                    String finalDesignation = designation + " " + reservationDraftRequest.getDesignation();
                    reservationDraftRequestOutcome.setDesignation(finalDesignation);
                    reservationDraftRequestOutcome.setRecordTypeId(reservationDraftRequest.getRecordTypeId());
                    reservationDraftRequestOutcome.setLocation(reservationDraftRequest.getLocation());
                    reservationDraftRequestOutcome.setLocationId(reservationDraftRequest.getLocationId());
                    reservationDraftRequestOutcomes.add(reservationDraftRequestOutcome);
                }
            }
        }

        List<ReservationDraftRequestOutcome> reservationDraftRequestOutcomes1 = reservationDraftRequestOutcomeRepository
                .saveAll(reservationDraftRequestOutcomes);

        return reservationDraftRequestOutcomes1.stream()
                .map(reservationDraftRequestOutcomeMapper
                        ::reservationDraftRequestOutcomeToReservationDraftRequestOutcomeDto)
                .collect(Collectors.toList());
    }

    private Collection<ReservationDraftRequestOutcomeDto> processForSubDivision(ReservationDraftSteps reservationDraftSteps,
                                                                                final String algorithm,
                                                                                final String mdbCode) {

        Set<ReservationDraftRequest> reservationDraftRequests = reservationDraftSteps.getReservationDraftRequests();
        Long noOfParcels = reservationDraftSteps.getParcelRequested();
        Long stepNo = reservationDraftSteps.getStepNo();
        if (CollectionUtils.isNotEmpty(reservationDraftRequests)) {
            Collection<ReservationDraftRequestOutcome> reservationDraftRequestOutcomes = new LinkedList<>();
            ReservationDraftRequest reservationDraftRequest = reservationDraftRequests.stream()
                    .findFirst().orElseGet(null);
            boolean ifPortionExists = checkIfPortionExists(reservationDraftRequests);
            if ("TRANSVAAL".equalsIgnoreCase(algorithm)) {
                if (!ifPortionExists) {
                    if (null != reservationDraftRequest) {
                        for (int i = 1; i <= noOfParcels; i++) {
                            ReservationDraftRequestOutcome reservationDraftRequestOutcome =
                                    new ReservationDraftRequestOutcome();
                            reservationDraftRequestOutcome.setReservationDraftSteps(reservationDraftRequest.getReservationDraftSteps());
                            String portion = "Portion S" + stepNo + "-" + SubdivisionParcels.of(i).name();
                            reservationDraftRequestOutcome.setPortion(portion);
                            reservationDraftRequestOutcome.setParcel(reservationDraftRequest.getParcel());
                            String designation = portion + " of Erf " + StringUtils.stripStart(reservationDraftRequest.getParcel(), "0") + " "
                                    + reservationDraftRequest.getLocation();
                            reservationDraftRequestOutcome.setDesignation(designation);
                            reservationDraftRequestOutcome.setRecordTypeId(reservationDraftRequest.getRecordTypeId());
                            reservationDraftRequestOutcome.setLocation(reservationDraftRequest.getLocation());
                            reservationDraftRequestOutcome.setLocationId(reservationDraftRequest.getLocationId());
                            reservationDraftRequestOutcomes.add(reservationDraftRequestOutcome);
                        }
                    }

                } else {
                    if (null != reservationDraftRequest) {
                        for (int i = 1; i <= noOfParcels; i++) {
                            ReservationDraftRequestOutcome reservationDraftRequestOutcome =
                                    new ReservationDraftRequestOutcome();
                            reservationDraftRequestOutcome.setReservationDraftSteps(reservationDraftRequest.getReservationDraftSteps());
                            String portion = reservationDraftRequest.getPortion();
                            reservationDraftRequestOutcome.setPortion(portion);
                            reservationDraftRequestOutcome.setParcel(reservationDraftRequest.getParcel());
                            String designation = "Portion S" + stepNo + "-" + SubdivisionParcels.of(i).name()
                                    + " (of " + StringUtils.stripStart(portion, "0") + ")"
                                    + "of Erf " + StringUtils.stripStart(reservationDraftRequest.getParcel(), "0")
                                    + " " + reservationDraftRequest.getLocation();
                            reservationDraftRequestOutcome.setDesignation(designation);
                            reservationDraftRequestOutcome.setRecordTypeId(reservationDraftRequest.getRecordTypeId());
                            reservationDraftRequestOutcome.setLocation(reservationDraftRequest.getLocation());
                            reservationDraftRequestOutcome.setLocationId(reservationDraftRequest.getLocationId());
                            reservationDraftRequestOutcomes.add(reservationDraftRequestOutcome);
                        }
                    }

                }

            } else {
                // handle for cape algorithm
                if (!ifPortionExists) {
                    if (null != reservationDraftRequest) {
                        for (int i = 1; i <= noOfParcels; i++) {
                            ReservationDraftRequestOutcome reservationDraftRequestOutcome =
                                    new ReservationDraftRequestOutcome();
                            reservationDraftRequestOutcome.setReservationDraftSteps(reservationDraftRequest.getReservationDraftSteps());
                            reservationDraftRequestOutcome.setParcel(reservationDraftRequest.getParcel());
                            String designation = "Erf S" + stepNo + "-" + SubdivisionParcels.of(i).name()
                                    + "  " + reservationDraftRequest.getLocation();
                            reservationDraftRequestOutcome.setDesignation(designation);
                            reservationDraftRequestOutcome.setRecordTypeId(reservationDraftRequest.getRecordTypeId());
                            reservationDraftRequestOutcome.setLocation(reservationDraftRequest.getLocation());
                            reservationDraftRequestOutcome.setLocationId(reservationDraftRequest.getLocationId());
                            reservationDraftRequestOutcomes.add(reservationDraftRequestOutcome);
                        }
                    }
                }
            }
            List<ReservationDraftRequestOutcome> reservationDraftRequestOutcomes1 = reservationDraftRequestOutcomeRepository
                    .saveAll(reservationDraftRequestOutcomes);

            return reservationDraftRequestOutcomes1.stream()
                    .map(reservationDraftRequestOutcomeMapper
                            ::reservationDraftRequestOutcomeToReservationDraftRequestOutcomeDto)
                    .collect(Collectors.toList());
        }

        return null;
    }


    private Collection<ReservationDraftRequestOutcomeDto> processForConsolidation(final ReservationDraftSteps reservationDraftSteps,
                                                                                  final String algorithm,
                                                                                  final String mdbCode) {
        Set<ReservationDraftRequest> reservationDraftRequests = reservationDraftSteps.getReservationDraftRequests();

        if (CollectionUtils.isNotEmpty(reservationDraftRequests)) {

            boolean ifPortionExists = checkIfPortionExists(reservationDraftRequests);

            if (ifPortionExists) {
                // it's a case of erf-portion
                ReservationDraftRequestOutcome reservationDraftRequestOutcome = handleConsolidationErfPortion(reservationDraftSteps,
                        algorithm, mdbCode);
                ReservationDraftRequestOutcomeDto reservationDraftRequestOutcomeDto = reservationDraftRequestOutcomeMapper
                        .reservationDraftRequestOutcomeToReservationDraftRequestOutcomeDto(reservationDraftRequestOutcome);
                return Collections.singletonList(reservationDraftRequestOutcomeDto);
            } else {
                // it is a case of erf only
                ReservationDraftRequestOutcome reservationDraftRequestOutcome = handleConsolidationErf(reservationDraftSteps,
                        algorithm, mdbCode);
                ReservationDraftRequestOutcomeDto reservationDraftRequestOutcomeDto = reservationDraftRequestOutcomeMapper
                        .reservationDraftRequestOutcomeToReservationDraftRequestOutcomeDto(reservationDraftRequestOutcome);
                return Collections.singletonList(reservationDraftRequestOutcomeDto);
            }
        }
        return null;
    }

    private ReservationDraftRequestOutcome handleConsolidationErf(ReservationDraftSteps reservationDraftSteps,
                                                                  final String algorithm,
                                                                  final String mdbCode) {
        Set<ReservationDraftRequest> reservationDraftRequests = reservationDraftSteps.getReservationDraftRequests();
        Optional<ReservationDraftRequest> firstDraftRequest = reservationDraftRequests.stream().findFirst();
        if (firstDraftRequest.isPresent()) {
            ReservationDraftRequest reservationDraftRequest = firstDraftRequest.get();
            ReservationDraftRequestOutcome reservationDraftRequestOutcome = new ReservationDraftRequestOutcome();
            Long stepId = reservationDraftSteps.getStepId();
            ReservationDraftSteps byStepId = reservationDraftStepsRepository.findByStepId(stepId);
            reservationDraftRequestOutcome.setReservationDraftSteps(byStepId);
            reservationDraftRequestOutcome.setLocation(reservationDraftRequest.getLocation());
            reservationDraftRequestOutcome.setLocationId(reservationDraftRequest.getLocationId());
            reservationDraftRequestOutcome.setRecordTypeId(reservationDraftRequest.getRecordTypeId());
            Long stepNo = reservationDraftSteps.getStepNo();
            String parcel = "S" + stepNo + " - A";
            String designation = "Erf " + parcel + " " + reservationDraftRequest.getLocation();
            reservationDraftRequestOutcome.setDesignation(designation);
            reservationDraftRequestOutcome.setParcel(parcel);
            reservationDraftRequestOutcome.setPortion(reservationDraftRequest.getPortion());
            return reservationDraftRequestOutcomeRepository
                    .save(reservationDraftRequestOutcome);
        }
        return null;
    }

    private ReservationDraftRequestOutcome handleConsolidationErfPortion(ReservationDraftSteps reservationDraftSteps,
                                                                         final String algorithm,
                                                                         final String mdbCode) {
        Set<ReservationDraftRequest> reservationDraftRequests = reservationDraftSteps.getReservationDraftRequests();
        Optional<ReservationDraftRequest> firstDraftRequest = reservationDraftRequests.stream().findFirst();
        if (firstDraftRequest.isPresent()) {
            ReservationDraftRequest reservationDraftRequest = firstDraftRequest.get();
            ReservationDraftRequestOutcome reservationDraftRequestOutcome = new ReservationDraftRequestOutcome();
            Long stepId = reservationDraftSteps.getStepId();
            ReservationDraftSteps byStepId = reservationDraftStepsRepository.findByStepId(stepId);
            reservationDraftRequestOutcome.setReservationDraftSteps(byStepId);
            reservationDraftRequestOutcome.setLocation(reservationDraftRequest.getLocation());
            reservationDraftRequestOutcome.setLocationId(reservationDraftRequest.getLocationId());
            reservationDraftRequestOutcome.setRecordTypeId(reservationDraftRequest.getRecordTypeId());
            Long stepNo = reservationDraftSteps.getStepNo();
            String portion = "S" + stepNo + " - A";
            String parcel = reservationDraftRequest.getParcel();
            String designation = "Portion " + portion + " of Erf " + StringUtils.stripStart(parcel, "0")
                    + "  " + reservationDraftRequest.getLocation();
            reservationDraftRequestOutcome.setDesignation(designation);
            reservationDraftRequestOutcome.setParcel(parcel);
            reservationDraftRequestOutcome.setPortion(portion);
            return reservationDraftRequestOutcomeRepository
                    .save(reservationDraftRequestOutcome);
        }
        return null;
    }

    private Long getLocationIdFromDraftRequest(ReservationDraftSteps reservationDraftSteps) {
        Optional<Long> locationId = reservationDraftSteps.getReservationDraftRequests()
                .stream()
                .findFirst()
                .map(ReservationDraftRequest::getLocationId);

        return locationId.orElse(null);

    }

    private boolean checkIfPortionExists(final Collection<ReservationDraftRequest> reservationDraftRequests) {
        return reservationDraftRequests.stream()
                .anyMatch(reservationDraftRequest -> !StringUtils.equalsIgnoreCase("00000", reservationDraftRequest
                        .getPortion()));
    }

    /**
     * @param pageable {@link Pageable}
     * @return {@link  ReservationDraftListingProjection}
     */
    public Page<ReservationTransferListProjection> getAllReservationTransfers(final Pageable pageable) {
        return reservationDraftRepository.loadReservationTransferListing(pageable);
    }
}
