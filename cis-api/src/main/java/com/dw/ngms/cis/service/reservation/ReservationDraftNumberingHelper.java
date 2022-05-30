package com.dw.ngms.cis.service.reservation;

import com.dw.ngms.cis.persistence.domains.listmanagement.ListItemData;
import com.dw.ngms.cis.persistence.domains.prepackage.Location;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraft;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftRequest;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftRequestOutcome;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationOutcome;
import com.dw.ngms.cis.persistence.repository.listmanagement.ListItemDataRepository;
import com.dw.ngms.cis.persistence.repository.prepackage.LocationRepository;
import com.dw.ngms.cis.persistence.repository.reservation.ReservationDraftRepository;
import com.dw.ngms.cis.persistence.repository.reservation.ReservationOutcomeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author prateek on 02-02-2022
 */
@Service
@Slf4j
@AllArgsConstructor
public class ReservationDraftNumberingHelper {

    private final ReservationDraftRepository reservationDraftRepository;

    private final ReservationOutcomeRepository reservationOutcomeRepository;

    private final ListItemDataRepository listItemDataRepository;

    private final LocationRepository locationRepository;


    public void issueParcelPortionNumbering(final Long draftId){
        ReservationDraft byDraftId = reservationDraftRepository.findByDraftId(draftId);

        if(null != byDraftId) {
            Long applicantUserId = byDraftId.getApplicantUserId();
            byDraftId.getReservationDraftSteps().forEach(reservationDraftSteps -> {

                Long reasonItemId = reservationDraftSteps.getReasonItemId();

                ReservationDraftRequest reservationDraftRequest = reservationDraftSteps
                        .getReservationDraftRequests().stream().findFirst().get();

                Set<ReservationDraftRequestOutcome> reservationDraftRequestOutcomes = reservationDraftSteps
                        .getReservationDraftRequestOutcomes();

                reservationDraftRequestOutcomes.forEach(reservationDraftRequestOutcome -> {

                    String portion = reservationDraftRequestOutcome.getPortion();

                    if(checkIfDashPresent(portion)) {
                        // get maximum portion issue for this locationId
                        Long locationId = reservationDraftRequestOutcome.getLocationId();
                        String parcel = reservationDraftRequestOutcome.getParcel();
                        Long numericParcel = null;
                        if(StringUtils.isNotBlank(parcel)) {
                            numericParcel = Long.parseLong(parcel);
                        }
                        Long maxPortionForLocationId = reservationOutcomeRepository
                                .getMaxPortionForLocationIdAndParcel(locationId,numericParcel);

                        long allocatedPortion;
                        if(null != maxPortionForLocationId) {
                            allocatedPortion = maxPortionForLocationId + 1;
                        } else {
                            allocatedPortion = 1L;
                        }
                        ReservationOutcome reservationOutcome = new ReservationOutcome();
                        reservationOutcome.setRequestId(reservationDraftRequest.getDraftRequestId());
                        reservationOutcome.setPortion(allocatedPortion);
                        reservationOutcome.setDraftId(draftId);
                        String newDesignation = "Portion " + allocatedPortion + " of Erf " +
                                StringUtils.stripStart(parcel,"0") +
                                " " +
                                reservationDraftRequestOutcome.getLocation();
                        reservationOutcome.setDesignation(newDesignation);
                        reservationOutcome.setParcel(numericParcel);
                        reservationOutcome.setLocationId(locationId);
                        reservationOutcome.setLocationName(reservationDraftRequestOutcome.getLocation());
                        reservationOutcome.setWorkflowId(byDraftId.getWorkflowId());
                        reservationOutcome.setReservationDraftSteps(reservationDraftSteps);
                        reservationOutcome.setIssueDate(LocalDateTime.now());
                        reservationOutcome.setReasonItemId(reasonItemId);

                        Location locationByBoundaryId = locationRepository.findLocationByBoundaryId(locationId);

                        if(null != locationByBoundaryId) {
                            String lpi = locationByBoundaryId.getMdbcode() +
                                    StringUtils.leftPad(reservationDraftRequestOutcome.getParcel(),8,"0") +
                                    StringUtils.leftPad(allocatedPortion+"",5,"0");
                            reservationOutcome.setLpi(lpi);
                        }

                        reservationOutcome.setStatusItemId(806L);
                        reservationOutcome.setOwnerUserId(applicantUserId);

                        ListItemData byItemId = listItemDataRepository.findByItemId(reasonItemId);
                        if(null != byItemId) {
                            Long days = byItemId.getData1();
                            LocalDateTime localDateTime = LocalDateTime.now().plusDays(days);
                            reservationOutcome.setExpiryDate(localDateTime);
                        }

                        reservationOutcomeRepository.save(reservationOutcome);

                    }

                    String parcel = reservationDraftRequestOutcome.getParcel();
                    if(checkIfDashPresent(parcel)) {
                        Long locationId = reservationDraftRequestOutcome.getLocationId();
                        Long maxParcelForLocationId = reservationOutcomeRepository
                                .getMaxParcelForLocationId(locationId);

                        long allocatedParcel;
                        if(null != maxParcelForLocationId) {
                            allocatedParcel = maxParcelForLocationId + 1;
                        } else {
                            allocatedParcel = 1L;
                        }

                        ReservationOutcome reservationOutcome = new ReservationOutcome();
                        reservationOutcome.setPortion(Long.parseLong(portion));
                        reservationOutcome.setRequestId(reservationDraftRequest.getDraftRequestId());
                        reservationOutcome.setDraftId(draftId);
                        String design = " Erf "
                                + StringUtils.stripStart(allocatedParcel+"","0")
                                +  "  "
                                + reservationDraftRequestOutcome.getLocation();
                        reservationOutcome.setDesignation(design);
                        reservationOutcome.setParcel(allocatedParcel);
                        reservationOutcome.setLocationId(locationId);
                        reservationOutcome.setLocationName(reservationDraftRequestOutcome.getLocation());
                        reservationOutcome.setWorkflowId(byDraftId.getWorkflowId());
                        reservationOutcome.setReservationDraftSteps(reservationDraftSteps);
                        reservationOutcome.setIssueDate(LocalDateTime.now());
                        reservationOutcome.setReasonItemId(reasonItemId);

                        Location locationByBoundaryId = locationRepository.findLocationByBoundaryId(locationId);

                        if(null != locationByBoundaryId) {
                            String lpi = locationByBoundaryId.getMdbcode() +
                                    StringUtils.leftPad(allocatedParcel+"",8,"0") +
                                    StringUtils.leftPad(portion,5,"0") ;
                            reservationOutcome.setLpi(lpi);
                        }

                        reservationOutcome.setStatusItemId(806L);
                        reservationOutcome.setOwnerUserId(applicantUserId);

                        ListItemData byItemId = listItemDataRepository.findByItemId(reasonItemId);
                        if(null != byItemId) {
                            Long days = byItemId.getData1();
                            LocalDateTime localDateTime = LocalDateTime.now().plusDays(days);
                            reservationOutcome.setExpiryDate(localDateTime);
                        }

                        reservationOutcomeRepository.save(reservationOutcome);

                    }

                });
            });


        }
    }


    private Boolean checkIfDashPresent(final String str) {
        return StringUtils.containsAny(str,"-");
    }
}
