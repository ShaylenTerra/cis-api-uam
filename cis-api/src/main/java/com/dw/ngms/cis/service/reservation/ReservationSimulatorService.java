package com.dw.ngms.cis.service.reservation;

import com.dw.ngms.cis.enums.ReservationReason;
import com.dw.ngms.cis.enums.ReservationSubType;
import com.dw.ngms.cis.enums.ReservationType;
import com.dw.ngms.cis.persistence.domain.number.SgdataParcels;
import com.dw.ngms.cis.persistence.domains.listmanagement.ListItem;
import com.dw.ngms.cis.persistence.domains.prepackage.Location;
import com.dw.ngms.cis.persistence.projection.prepackage.PrepackageMajorRegionOrAdminDistrict;
import com.dw.ngms.cis.persistence.repository.SgdataParcelsRepository;
import com.dw.ngms.cis.persistence.repository.listmanagement.ListItemRepository;
import com.dw.ngms.cis.persistence.repository.listmanagement.ListMappingRepository;
import com.dw.ngms.cis.persistence.repository.prepackage.LocationRepository;
import com.dw.ngms.cis.service.dto.reservation.ParentParcel;
import com.dw.ngms.cis.service.dto.reservation.ReservationDetails;
import com.dw.ngms.cis.web.request.ReservationSimulatorRequest;
import com.dw.ngms.cis.web.response.ReservationSimulatorResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationSimulatorService {

    private static final Integer MAX_PARCEL = 90000000;
    private static final Integer MAX_PORTION = 80000;
    private final ListItemRepository listItemRepository;
    private final ListMappingRepository listMappingRepository;
    private final LocationRepository locationRepository;
    private final SgdataParcelsRepository sgdataParcelsRepository;

    /**
     * @param parentItemId parentItemId
     * @param listCode     listCode
     * @return Collection<ListItem>
     */
    public Collection<ListItem> getReservationType(final Long parentItemId, final Long listCode) {
        return listItemRepository.getListItemByParentItemIdAndListCode(parentItemId, listCode);
    }

    /**
     * @param listId       listId
     * @param parentItemId parentItemId
     * @return Collection<ListItem>
     */
    public Collection<ListItem> getReservationSubType(final Long listId, final Long parentItemId) {
        return getReservationType(parentItemId, listId);
    }

    /**
     * @param provinceId provinceId
     * @return Collection<PrepackageMajorRegionOrAdminDistrict>
     */
    public Collection<PrepackageMajorRegionOrAdminDistrict> getTownshipAllotment(final Long provinceId) {
        return locationRepository.getMajorRegion(provinceId, Pageable.unpaged()).getContent();
    }

    /**
     * @param reservationSimulatorRequest {@link ReservationSimulatorRequest}
     * @return {@link ReservationSimulatorResponse}
     */
    public ReservationSimulatorResponse process(ReservationSimulatorRequest reservationSimulatorRequest) {
        // get location details from locationId
        Location locationByBoundaryId = locationRepository.findLocationByBoundaryId(reservationSimulatorRequest.getLocationId());
        ReservationSimulatorResponse reservationSimulatorResponse = new ReservationSimulatorResponse();
        String mdbCode = "";
        if (null != locationByBoundaryId) {
            mdbCode = locationByBoundaryId.getMdbcode();
            if(null != mdbCode && mdbCode.length() == 2){
                mdbCode = "T0" + mdbCode + "0000";
            }
            String reservationSystem = locationByBoundaryId.getReservationSystem();
            reservationSimulatorResponse.setAlgorithm(reservationSystem);
            if (StringUtil.isBlank(reservationSystem)) {
                Long parentBoundaryId = locationByBoundaryId.getParentBoundaryId();

                Location locationByBoundaryId1 = locationRepository.findLocationByBoundaryId(parentBoundaryId);
                String reservationSystem1 = locationByBoundaryId1.getReservationSystem();
                reservationSimulatorResponse.setAlgorithm(reservationSystem1);
            }
        }

        // check for different combination of reservationType and subType
        if (ReservationReason.SUBDIVISION.equals(reservationSimulatorRequest.getReservationReason())) {
            if (reservationSimulatorRequest.getReservationType().equals(ReservationType.ERF)) {
                if (reservationSimulatorRequest.getReservationSubType().equals(ReservationSubType.ERF)) {
                    ParentParcel parentParcel = getParentParcelForSubdivisionErf(reservationSimulatorRequest);
                    if (null != parentParcel) {
                        reservationSimulatorResponse.setParentParcels(Collections.singletonList(parentParcel));
                        Collection<ReservationDetails> reservationDetails =
                                getReservationDetailsForSubdivisionErfErf(reservationSimulatorRequest,
                                        mdbCode,
                                        parentParcel.getParcel(),
                                        parentParcel.getRegionTownship(),
                                        reservationSimulatorResponse.getAlgorithm());
                        reservationSimulatorResponse.setReservationDetails(reservationDetails);
                    }
                } else if (reservationSimulatorRequest.getReservationSubType().equals(ReservationSubType.ERF_PORTION)) {
                    ParentParcel parentParcel = getParentParcelForSubdivisionErfPortion(reservationSimulatorRequest);
                    if (null != parentParcel) {
                        reservationSimulatorResponse.setParentParcels(Collections.singletonList(parentParcel));
                        Collection<ReservationDetails> reservationDetails =
                                getReservationDetailsForSubdivisionErfPortion(reservationSimulatorRequest,
                                        mdbCode,
                                        parentParcel,
                                        reservationSimulatorResponse.getAlgorithm());
                        reservationSimulatorResponse.setReservationDetails(reservationDetails);
                    }
                } else if (reservationSimulatorRequest.getReservationSubType().equals(ReservationSubType.ERF_REMAINDER)) {
                    ParentParcel parentParcel = getParentParcelForSubdivisionErfErfRem(reservationSimulatorRequest);
                    if (null != parentParcel) {
                        reservationSimulatorResponse.setParentParcels(Collections.singletonList(parentParcel));
                        Collection<ReservationDetails> reservationDetails =
                                getReservationDetailsForSubdivisionErfErf(reservationSimulatorRequest,
                                        mdbCode,
                                        parentParcel.getParcel(),
                                        parentParcel.getRegionTownship(),
                                        reservationSimulatorResponse.getAlgorithm());
                        reservationSimulatorResponse.setReservationDetails(reservationDetails);
                    }
                }
            } else if (reservationSimulatorRequest.getReservationType().equals(ReservationType.FARM)) {
                // implement farm algo here
                    if (reservationSimulatorRequest.getReservationSubType().equals(ReservationSubType.SUB_FARM)) {
                        ParentParcel parentParcel =
                                getParentParcelForSubdivisonFarmSubFarm(reservationSimulatorRequest);
                        if (null != parentParcel) {
                            reservationSimulatorResponse.setParentParcels(Collections.singletonList(parentParcel));
                            Collection<ReservationDetails> reservationDetailsForSubdivisionFarmSubFarm =
                                    getReservationDetailsForSubdivisionFarmSubFarm(reservationSimulatorRequest,
                                    mdbCode,
                                    parentParcel.getParcel(),
                                    parentParcel.getRegionTownship(),
                                    reservationSimulatorResponse.getAlgorithm());
                            reservationSimulatorResponse.setReservationDetails(reservationDetailsForSubdivisionFarmSubFarm);
                        }
                    } else if(reservationSimulatorRequest.getReservationSubType().equals(ReservationSubType.SUB_FARM_PORTION)){
                        ParentParcel parentParcel =
                                getParentParcelForSubdivisonFarmSubFarmPortion(reservationSimulatorRequest);
                        if (null != parentParcel) {
                            reservationSimulatorResponse.setParentParcels(Collections.singletonList(parentParcel));
                            Collection<ReservationDetails> reservationDetailsForSubdivisionFarmSubFarm =
                                    getReservationDetailsForSubdivisionFarmSubFarm(reservationSimulatorRequest,
                                            mdbCode,
                                            parentParcel.getParcel(),
                                            parentParcel.getRegionTownship(),
                                            reservationSimulatorResponse.getAlgorithm());
                            reservationSimulatorResponse.setReservationDetails(reservationDetailsForSubdivisionFarmSubFarm);
                        }
                    }

            } else if (reservationSimulatorRequest.getReservationType().equals(ReservationType.LEASE)) {

            }
        } else if (ReservationReason.CONSOLIDATION.equals(reservationSimulatorRequest.getReservationReason())) {
            // handle consolidation request
            if (ReservationType.ERF.equals(reservationSimulatorRequest.getReservationType())) {
                if (ReservationSubType.CON_ERF.equals(reservationSimulatorRequest.getReservationSubType())) {
                    Collection<ParentParcel> parentParcelConsolidationErf =
                            getParentParcelConsolidationErf(reservationSimulatorRequest);
                    if (CollectionUtils.isNotEmpty(parentParcelConsolidationErf)) {
                        reservationSimulatorResponse.setParentParcels(parentParcelConsolidationErf);
                        ReservationDetails reservationDetailsForConsolidationErf =
                                getReservationDetailsForConsolidationErf(parentParcelConsolidationErf, mdbCode);
                        reservationSimulatorResponse.setReservationDetails(Collections
                                .singletonList(reservationDetailsForConsolidationErf));
                    }
                } else if (ReservationSubType.CON_DIFF_ERF_DIFF_PORTION
                        .equals(reservationSimulatorRequest.getReservationSubType())) {
                    Collection<ParentParcel> parentParcelConDiffErfDiffPortion =
                            getParentParcelConDiffErfDiffPortion(reservationSimulatorRequest);
                    if(CollectionUtils.isNotEmpty(parentParcelConDiffErfDiffPortion)) {
                        reservationSimulatorResponse.setParentParcels(parentParcelConDiffErfDiffPortion);
                        ReservationDetails reservationDetailsForConsolidationErf =
                                getReservationDetailsForConsolidationErf(parentParcelConDiffErfDiffPortion, mdbCode);
                        reservationSimulatorResponse.setReservationDetails(Collections
                                .singletonList(reservationDetailsForConsolidationErf));
                    }
                } else if (ReservationSubType.CON_ERF_WITH_DIFF_ERF_PORTION
                        .equals(reservationSimulatorRequest.getReservationSubType())) {
                    Collection<ParentParcel> parentParcelConErfWithDiffErfPortion =
                            getParentParcelConErfWithDiffErfPortion(reservationSimulatorRequest);
                    if(CollectionUtils.isNotEmpty(parentParcelConErfWithDiffErfPortion)) {
                        reservationSimulatorResponse.setParentParcels(parentParcelConErfWithDiffErfPortion);
                        ReservationDetails reservationDetailsForConsolidationErf =
                                getReservationDetailsForConsolidationErf(parentParcelConErfWithDiffErfPortion, mdbCode);
                        reservationSimulatorResponse.setReservationDetails(Collections
                                .singletonList(reservationDetailsForConsolidationErf));
                    }
                } else if (ReservationSubType.CON_SAME_ERF_DIFF_PORTION
                        .equals(reservationSimulatorRequest.getReservationSubType())) {
                    Collection<ParentParcel> parentParcelConSameErfDiffPortion =
                            getParentParcelConSameErfDiffPortion(reservationSimulatorRequest);
                    if(CollectionUtils.isNotEmpty(parentParcelConSameErfDiffPortion)) {
                        reservationSimulatorResponse.setParentParcels(parentParcelConSameErfDiffPortion);
                        ReservationDetails reservationDetailsForConsolidationErf =
                                getReservationDetailsForConsolidationErf(parentParcelConSameErfDiffPortion, mdbCode);
                        reservationSimulatorResponse.setReservationDetails(Collections
                                .singletonList(reservationDetailsForConsolidationErf));
                    }
                }

            }
        }else if(ReservationReason.LEASE_ERVEN.equals(reservationSimulatorRequest.getReservationReason())) {
            if (ReservationType.ERF.equals(reservationSimulatorRequest.getReservationType())) {
                if (ReservationSubType.LEASE_CREATION_ERF.equals(reservationSimulatorRequest.getReservationSubType())) {
                    // get parent parcel
                    ParentParcel parentParcelForLeaseCreationErf =
                            getParentParcelForLeaseCreationErf(reservationSimulatorRequest);
                    // generate outcome
                    if (null != parentParcelForLeaseCreationErf) {
                        reservationSimulatorResponse.setParentParcels(Collections.singletonList(parentParcelForLeaseCreationErf));
                        Collection<ReservationDetails> reservationDetails =
                                getReservationDetailsForLeaseCreationErf(reservationSimulatorRequest,
                                        parentParcelForLeaseCreationErf);
                        reservationSimulatorResponse.setReservationDetails(reservationDetails);
                    }
                } else if(ReservationSubType.LEASE_CREATION_ERF_PORTION.equals(reservationSimulatorRequest.getReservationSubType())) {
                    // get parent parcel
                    ParentParcel parentParcelForLeaseCreationErfPortion =
                            getParentParcelForLeaseCreationErfPortion(reservationSimulatorRequest);
                    if(null != parentParcelForLeaseCreationErfPortion) {
                        reservationSimulatorResponse.setParentParcels(Collections.singletonList(parentParcelForLeaseCreationErfPortion));
                        Collection<ReservationDetails> reservationDetailsForLeaseErfPortion =
                                getReservationDetailsForLeaseErfPortion(reservationSimulatorRequest,
                                        parentParcelForLeaseCreationErfPortion);
                        reservationSimulatorResponse.setReservationDetails(reservationDetailsForLeaseErfPortion);
                    }

                } else if (ReservationSubType.LEASE_CREATION_ERF_REMAINDER
                        .equals(reservationSimulatorRequest.getReservationSubType())){
                    ParentParcel parentParcelForLeaseCreationErfRemainder =
                            getParentParcelForLeaseCreationErfRemainder(reservationSimulatorRequest);
                    if(null != parentParcelForLeaseCreationErfRemainder) {
                        reservationSimulatorResponse.setParentParcels(Collections.singletonList(parentParcelForLeaseCreationErfRemainder));
                        Collection<ReservationDetails> reservationDetailsForLeaseCreationErf =
                                getReservationDetailsForLeaseCreationErf(reservationSimulatorRequest,
                                        parentParcelForLeaseCreationErfRemainder);
                        reservationSimulatorResponse.setReservationDetails(reservationDetailsForLeaseCreationErf);

                    }
                }
            }
        }

        return reservationSimulatorResponse;

    }

    private ParentParcel getParentParcelForLeaseCreationErfRemainder(final ReservationSimulatorRequest reservationSimulatorRequest) {
        return getParentParcelForSubdivisionErfErfRem(reservationSimulatorRequest);
    }


    private ParentParcel
    getParentParcelForSubdivisonFarmSubFarmPortion(final ReservationSimulatorRequest reservationSimulatorRequest) {
        Optional<SgdataParcels> first = sgdataParcelsRepository.findSgdataParcelsForSubdivisionFarmSubFarmPortion("00000",
                reservationSimulatorRequest.getProvinceId(),
                Arrays.asList("SUBDIVISIONAL", "CONSOLIDATION"),
                7L,
                reservationSimulatorRequest.getLocationId()).stream().findFirst();

        if(first.isPresent()) {
            SgdataParcels sgdataParcels = first.get();
            ParentParcel parentParcel = new ParentParcel();
            parentParcel.setParcel(sgdataParcels.getParcel());
            parentParcel.setDocumentType(sgdataParcels.getDocumentType());
            parentParcel.setDocumentSubType(sgdataParcels.getDocumentSubtype());
            parentParcel.setLpi(sgdataParcels.getLpi());
            parentParcel.setRegion(sgdataParcels.getRegion() + "(" + sgdataParcels.getRegistrationTownshipName() + ")");
            parentParcel.setRegionTownship(sgdataParcels.getRegistrationTownshipName());
            parentParcel.setSgNo(sgdataParcels.getSgNo());
            parentParcel.setDesignation(sgdataParcels.getDesignation());
            return parentParcel;
        }

        return null;
    }


    private ParentParcel
    getParentParcelForSubdivisonFarmSubFarm(final ReservationSimulatorRequest reservationSimulatorRequest) {
        Optional<SgdataParcels> first = sgdataParcelsRepository.findSgdataParcelsForSubdivisionFarmSubFarm("00000",
                reservationSimulatorRequest.getProvinceId(),
                Arrays.asList("SUBDIVISIONAL", "CONSOLIDATION"),
                7L,
                reservationSimulatorRequest.getLocationId()).stream().findFirst();

        if(first.isPresent()) {
            SgdataParcels sgdataParcels = first.get();
            ParentParcel parentParcel = new ParentParcel();
            parentParcel.setParcel(sgdataParcels.getParcel());
            parentParcel.setDocumentType(sgdataParcels.getDocumentType());
            parentParcel.setDocumentSubType(sgdataParcels.getDocumentSubtype());
            parentParcel.setLpi(sgdataParcels.getLpi());
            parentParcel.setRegion(sgdataParcels.getRegion() + "(" + sgdataParcels.getRegistrationTownshipName() + ")");
            parentParcel.setRegionTownship(sgdataParcels.getRegistrationTownshipName());
            parentParcel.setSgNo(sgdataParcels.getSgNo());
            parentParcel.setDesignation(sgdataParcels.getDesignation());
            return parentParcel;
        }

        return null;
    }

    private ParentParcel
    getParentParcelForSubdivisionErfErfRem(final ReservationSimulatorRequest reservationSimulatorRequest) {
        Optional<SgdataParcels> first = sgdataParcelsRepository
                .findSgdataParcelsForSubdivisionErfErfRem("00000",
                        reservationSimulatorRequest.getProvinceId(),
                        6L,
                        reservationSimulatorRequest.getLocationId()).stream().findFirst();

        if (first.isPresent()) {
            SgdataParcels sgdataParcels = first.get();
            ParentParcel parentParcel = new ParentParcel();
            parentParcel.setParcel(sgdataParcels.getParcel());
            parentParcel.setDocumentType(sgdataParcels.getDocumentType());
            parentParcel.setDocumentSubType(sgdataParcels.getDocumentSubtype());
            parentParcel.setLpi(sgdataParcels.getLpi());
            parentParcel.setRegion(sgdataParcels.getRegion() + "(" + sgdataParcels.getRegistrationTownshipName() + ")");
            parentParcel.setRegionTownship(sgdataParcels.getRegistrationTownshipName());
            parentParcel.setSgNo(sgdataParcels.getSgNo());
            parentParcel.setDesignation(sgdataParcels.getDesignation());
            return parentParcel;
        }

        return null;
    }

    private ParentParcel getParentParcelForLeaseCreationErf(final ReservationSimulatorRequest reservationSimulatorRequest) {
        return getParentParcelForSubdivisionErf(reservationSimulatorRequest);
    }

    private ParentParcel
    getParentParcelForSubdivisionErf(final ReservationSimulatorRequest reservationSimulatorRequest) {

        Optional<SgdataParcels> first = sgdataParcelsRepository
                .findSgdataParcelsForSubdivisionErf("00000",
                        reservationSimulatorRequest.getProvinceId(),
                        "DIAGRAM",
                        "SUBDIVISIONAL",
                        6L,
                        reservationSimulatorRequest.getLocationId()).stream().findFirst();

        if (first.isPresent()) {
            SgdataParcels sgdataParcels = first.get();
            ParentParcel parentParcel = new ParentParcel();
            parentParcel.setParcel(sgdataParcels.getParcel());
            parentParcel.setDocumentType(sgdataParcels.getDocumentType());
            parentParcel.setDocumentSubType(sgdataParcels.getDocumentSubtype());
            parentParcel.setLpi(sgdataParcels.getLpi());
            parentParcel.setRegion(sgdataParcels.getRegion() + "(" + sgdataParcels.getRegistrationTownshipName() + ")");
            parentParcel.setRegionTownship(sgdataParcels.getRegistrationTownshipName());
            parentParcel.setSgNo(sgdataParcels.getSgNo());
            parentParcel.setDesignation(sgdataParcels.getDesignation());
            return parentParcel;
        }

        return null;
    }

    private ParentParcel getParentParcelForLeaseCreationErfPortion(final ReservationSimulatorRequest reservationSimulatorRequest){
        return getParentParcelForSubdivisionErfPortion(reservationSimulatorRequest);
    }

    private ParentParcel
    getParentParcelForSubdivisionErfPortion(final ReservationSimulatorRequest reservationSimulatorRequest) {
        Optional<SgdataParcels> first = sgdataParcelsRepository
                .findSgdataParcelsForSubdivisionErfPortion("00000",
                        reservationSimulatorRequest.getProvinceId(),
                        "DIAGRAM",
                        "SUBDIVISIONAL",
                        6L,
                        reservationSimulatorRequest.getLocationId()).stream().findFirst();

        if (first.isPresent()) {
            SgdataParcels sgdataParcels = first.get();
            ParentParcel parentParcel = new ParentParcel();
            parentParcel.setParcel(sgdataParcels.getParcel());
            parentParcel.setDocumentType(sgdataParcels.getDocumentType());
            parentParcel.setDocumentSubType(sgdataParcels.getDocumentSubtype());
            parentParcel.setPortion(sgdataParcels.getPortion());
            parentParcel.setLpi(sgdataParcels.getLpi());
            parentParcel.setRegion(sgdataParcels.getRegion() + "(" + sgdataParcels.getRegistrationTownshipName() + ")");
            parentParcel.setRegionTownship(sgdataParcels.getRegistrationTownshipName());
            parentParcel.setSgNo(sgdataParcels.getSgNo());
            parentParcel.setDesignation(sgdataParcels.getDesignation());
            return parentParcel;
        }

        return null;
    }

    private Collection<ReservationDetails>
    getReservationDetailsForLeaseCreationErf(ReservationSimulatorRequest reservationSimulatorRequest,
                                             final ParentParcel parentParcel) {
        Long noOfParcel = reservationSimulatorRequest.getNoOfParcel();
        LinkedList<ReservationDetails> reservationDetails1 = new LinkedList<>();
        String parcel1 = StringUtils.stripStart(parentParcel.getParcel(), "0");
        String region = parentParcel.getRegionTownship();
        String portion = parentParcel.getPortion();
        for (int i = 1; i <= noOfParcel; i++) {
            ReservationDetails reservationDetails = new ReservationDetails();
            reservationDetails.setLpi(parentParcel.getLpi());
            String designation = reservationSimulatorRequest.getReservationSubType().getReservationSubTypeFaceValue() +
                    " " +
                    i +
                    " on Erf " +
                    parcel1 +
                    " " +
                    region;
            reservationDetails.setDesignation(designation);
            reservationDetails.setParcel(parcel1);
            if(StringUtils.isNotBlank(portion)) {

                reservationDetails.setPortion(Integer.parseInt(parentParcel.getPortion()));
            }

            reservationDetails.setLeaseNo(i);
            reservationDetails1.add(reservationDetails);
        }

        return reservationDetails1;
    }

    private Collection<ReservationDetails>
    getReservationDetailsForSubdivisionFarmSubFarmPortion(ReservationSimulatorRequest reservationSimulatorRequest,
                                                   final String mdbCode,
                                                   final String parcel,
                                                   final String region,
                                                   final ParentParcel parentParcel) {
        // generate lpi code
        Long noOfParcel = reservationSimulatorRequest.getNoOfParcel();
        LinkedList<ReservationDetails> reservationDetails1 = new LinkedList<>();

        if(StringUtils.equalsIgnoreCase(parentParcel.getPortion(),"00000")){

        } else {

            for (int i = 1; i <= noOfParcel; i++) {
                ReservationDetails reservationDetails = new ReservationDetails();
                String lpi = mdbCode + parcel + StringUtils.leftPad(i + "", 5, "0");
                reservationDetails.setLpi(lpi);
                Integer allocatedPortion = MAX_PORTION +1;
                String designation = "Portion " + allocatedPortion
                        + " (of "
                        + ")"
                        + " of the "
                        + reservationSimulatorRequest.getReservationSubType().getReservationSubTypeFaceValue()
                        + " " + StringUtils.stripStart(parcel, "0")
                        + " - " + region;
                reservationDetails.setDesignation(designation);
                String parcel1 = StringUtils.stripStart(parcel, "0");
                reservationDetails.setParcel(parcel1);
                Integer portion = i;
                reservationDetails.setPortion(portion);
                reservationDetails1.add(reservationDetails);
            }
        }

        return reservationDetails1;
    }

    private Collection<ReservationDetails>
            getReservationDetailsForSubdivisionFarmSubFarmPortion(){
        return null;
    }

    private Collection<ReservationDetails>
    getReservationDetailsForSubdivisionFarmSubFarm(ReservationSimulatorRequest reservationSimulatorRequest,
                                                   final String mdbCode,
                                                   final String parcel,
                                                   final String region,
                                                   final String algorithm) {
        // generate lpi code
        Long noOfParcel = reservationSimulatorRequest.getNoOfParcel();
        LinkedList<ReservationDetails> reservationDetails1 = new LinkedList<>();
        for (int i = 1; i <= noOfParcel; i++) {
            ReservationDetails reservationDetails = new ReservationDetails();
            String lpi = mdbCode + parcel + StringUtils.leftPad(i + "", 5, "0");
            reservationDetails.setLpi(lpi);
            String designation = "Portion " + i + " of " + reservationSimulatorRequest.getReservationSubType().getReservationSubTypeFaceValue()
                    + " " + StringUtils.stripStart(parcel, "0")
                    + " " + region;
            reservationDetails.setDesignation(designation);
            String parcel1 = StringUtils.stripStart(parcel, "0");
            reservationDetails.setParcel(parcel1);
            Integer portion = i;
            reservationDetails.setPortion(portion);
            reservationDetails1.add(reservationDetails);
        }
        return reservationDetails1;
    }

    private Collection<ReservationDetails>
    getReservationDetailsForSubdivisionErfErf(ReservationSimulatorRequest reservationSimulatorRequest,
                                              final String mdbCode,
                                              final String parcel,
                                              final String region,
                                              final String algorithm) {
        // generate lpi code
        Long noOfParcel = reservationSimulatorRequest.getNoOfParcel();
        LinkedList<ReservationDetails> reservationDetails1 = new LinkedList<>();

        if (algorithm.equals("Transvaal")) {
            for (int i = 1; i <= noOfParcel; i++) {
                ReservationDetails reservationDetails = new ReservationDetails();
                String lpi = mdbCode + parcel + StringUtils.leftPad(i + "", 5, "0");
                reservationDetails.setLpi(lpi);
                String designation = "Portion " + i + " of " + reservationSimulatorRequest.getReservationSubType().getReservationSubTypeFaceValue() + " " + StringUtils.stripStart(parcel, "0") + " " + region;
                reservationDetails.setDesignation(designation);
                String parcel1 = StringUtils.stripStart(parcel, "0");
                reservationDetails.setParcel(parcel1);
                Integer portion = i;
                reservationDetails.setPortion(portion);
                reservationDetails1.add(reservationDetails);
            }
        } else {
            // in case of cape algo there is no change in portion
            for (int i = 1; i <= noOfParcel; i++) {
                ReservationDetails reservationDetails = new ReservationDetails();
                int allocateParcel = MAX_PARCEL + i;
                String lpi = mdbCode + allocateParcel + StringUtils.leftPad("0" + "", 5, "0");
                reservationDetails.setLpi(lpi);
                String designation = reservationSimulatorRequest.getReservationSubType().getReservationSubTypeFaceValue() + " " + allocateParcel + " of " + region;
                reservationDetails.setDesignation(designation);
                reservationDetails.setParcel(allocateParcel + "");
                reservationDetails.setPortion(0);
                reservationDetails1.add(reservationDetails);
            }

        }


        return reservationDetails1;
    }

    private Collection<ReservationDetails> getReservationDetailsForLeaseErfPortion(ReservationSimulatorRequest reservationSimulatorRequest,
                                                                                  ParentParcel parentParcel) {
        Long noOfParcel = reservationSimulatorRequest.getNoOfParcel();
        LinkedList<ReservationDetails> reservationDetails1 = new LinkedList<>();
        String parcel = parentParcel.getParcel();
        String portion = parentParcel.getPortion();
        for (int i = 1; i <= noOfParcel; i++) {
            ReservationDetails reservationDetails = new ReservationDetails();
            reservationDetails.setLpi(parentParcel.getLpi());
            String designation = reservationSimulatorRequest.getReservationSubType().getReservationSubTypeFaceValue() +
                    " " +
                    i +
                    " on "+
                    " Portion " + StringUtils.stripStart(portion,"0") +
                    " of Erf " + StringUtils.stripStart(parcel, "0") +
                    " " +
                    parentParcel.getRegionTownship();

            reservationDetails.setDesignation(designation);
            reservationDetails.setParcel(parcel);
            if(StringUtils.isNotBlank(portion)) {

                reservationDetails.setPortion(Integer.parseInt(portion));
            }
            reservationDetails1.add(reservationDetails);
        }

        return reservationDetails1;

    }

    private Collection<ReservationDetails>
    getReservationDetailsForSubdivisionErfPortion(ReservationSimulatorRequest reservationSimulatorRequest,
                                                  final String mdbCode,
                                                  final ParentParcel parentParcel,
                                                  final String algorithm) {
        // generate lpi code
        Long noOfParcel = reservationSimulatorRequest.getNoOfParcel();
        LinkedList<ReservationDetails> reservationDetails1 = new LinkedList<>();

        if (algorithm.equals("Transvaal")) {
            for (int i = 1; i <= noOfParcel; i++) {
                ReservationDetails reservationDetails = new ReservationDetails();
                String parcel = parentParcel.getParcel();
                String portion = parentParcel.getPortion();
                String actualPortionNumber = StringUtils.stripStart(portion, "0");
                int portionNo = NumberUtils.toInt(actualPortionNumber);
                int newPortionAllotted = portionNo + i;
                String lpi = mdbCode + parcel + StringUtils.leftPad(newPortionAllotted + "", 5, "0");
                reservationDetails.setLpi(lpi);
                String designation = "Portion " + newPortionAllotted + " (of " + actualPortionNumber + ") of "
                        + reservationSimulatorRequest.getReservationSubType().getReservationSubTypeFaceValue()
                        + " " + StringUtils.stripStart(parcel, "0") + " " + parentParcel.getRegionTownship();
                reservationDetails.setDesignation(designation);
                reservationDetails.setParcel(parcel);
                reservationDetails.setPortion(newPortionAllotted);
                reservationDetails1.add(reservationDetails);
            }
        } else {
            // in case of cape algo it is not applicable

        }

        return reservationDetails1;
    }

    private Collection<ParentParcel>
    getParentParcelConsolidationErf(final ReservationSimulatorRequest reservationSimulatorRequest) {
        return sgdataParcelsRepository
                .findSgdataParcelsForConsolidationErf("00000",
                        reservationSimulatorRequest.getProvinceId(),
                        "DIAGRAM",
                        6L,
                        reservationSimulatorRequest.getLocationId()).stream().map(sgdataParcels -> {
                    ParentParcel parentParcel = new ParentParcel();
                    parentParcel.setParcel(sgdataParcels.getParcel());
                    parentParcel.setDocumentType(sgdataParcels.getDocumentType());
                    parentParcel.setDocumentSubType(sgdataParcels.getDocumentSubtype());
                    parentParcel.setPortion(sgdataParcels.getPortion());
                    parentParcel.setLpi(sgdataParcels.getLpi());
                    parentParcel.setRegion(sgdataParcels.getRegion() + "(" + sgdataParcels.getRegistrationTownshipName() + ")");
                    parentParcel.setRegionTownship(sgdataParcels.getRegistrationTownshipName());
                    parentParcel.setSgNo(sgdataParcels.getSgNo());
                    parentParcel.setDesignation(sgdataParcels.getDesignation());
                    return parentParcel;
                }).collect(Collectors.toList());
    }

    private Collection<ParentParcel>
    getParentParcelConDiffErfDiffPortion(final ReservationSimulatorRequest reservationSimulatorRequest) {
        return sgdataParcelsRepository
                .findSgdataParcelsForConDiffErfDiffPortion("00000",
                        reservationSimulatorRequest.getProvinceId(),
                        "DIAGRAM",
                        6L,
                        reservationSimulatorRequest.getLocationId()).stream().map(sgdataParcels -> {
                    ParentParcel parentParcel = new ParentParcel();
                    parentParcel.setParcel(sgdataParcels.getParcel());
                    parentParcel.setDocumentType(sgdataParcels.getDocumentType());
                    parentParcel.setDocumentSubType(sgdataParcels.getDocumentSubtype());
                    parentParcel.setPortion(sgdataParcels.getPortion());
                    parentParcel.setLpi(sgdataParcels.getLpi());
                    parentParcel.setRegion(sgdataParcels.getRegion() + "(" + sgdataParcels.getRegistrationTownshipName() + ")");
                    parentParcel.setRegionTownship(sgdataParcels.getRegistrationTownshipName());
                    parentParcel.setSgNo(sgdataParcels.getSgNo());
                    parentParcel.setDesignation(sgdataParcels.getDesignation());
                    return parentParcel;
                }).collect(Collectors.toList());
    }

    private Collection<ParentParcel>
    getParentParcelConErfWithDiffErfPortion(final ReservationSimulatorRequest reservationSimulatorRequest) {
        return sgdataParcelsRepository
                .findSgdataParcelsForConErfWithDiffErfPortion(
                        reservationSimulatorRequest.getProvinceId(),
                        "DIAGRAM",
                        6L,
                        reservationSimulatorRequest.getLocationId()).stream().map(sgdataParcels -> {
                    ParentParcel parentParcel = new ParentParcel();
                    parentParcel.setParcel(sgdataParcels.getParcel());
                    parentParcel.setDocumentType(sgdataParcels.getDocumentType());
                    parentParcel.setDocumentSubType(sgdataParcels.getDocumentSubtype());
                    parentParcel.setPortion(sgdataParcels.getPortion());
                    parentParcel.setLpi(sgdataParcels.getLpi());
                    parentParcel.setRegion(sgdataParcels.getRegion() + "(" + sgdataParcels.getRegistrationTownshipName() + ")");
                    parentParcel.setRegionTownship(sgdataParcels.getRegistrationTownshipName());
                    parentParcel.setSgNo(sgdataParcels.getSgNo());
                    parentParcel.setDesignation(sgdataParcels.getDesignation());
                    return parentParcel;
                }).collect(Collectors.toList());
    }

    private Collection<ParentParcel>
    getParentParcelConSameErfDiffPortion(final ReservationSimulatorRequest reservationSimulatorRequest) {
        return sgdataParcelsRepository
                .findSgdataParcelsForConSameErfDiffPortion("00000",
                        StringUtils.leftPad(reservationSimulatorRequest.getParcel(),8, "0"),
                        reservationSimulatorRequest.getProvinceId(),
                        "DIAGRAM",
                        6L,
                        reservationSimulatorRequest.getLocationId()).stream().map(sgdataParcels -> {
                    ParentParcel parentParcel = new ParentParcel();
                    parentParcel.setParcel(sgdataParcels.getParcel());
                    parentParcel.setDocumentType(sgdataParcels.getDocumentType());
                    parentParcel.setDocumentSubType(sgdataParcels.getDocumentSubtype());
                    parentParcel.setPortion(sgdataParcels.getPortion());
                    parentParcel.setLpi(sgdataParcels.getLpi());
                    parentParcel.setRegion(sgdataParcels.getRegion() + "(" + sgdataParcels.getRegistrationTownshipName() + ")");
                    parentParcel.setRegionTownship(sgdataParcels.getRegistrationTownshipName());
                    parentParcel.setSgNo(sgdataParcels.getSgNo());
                    parentParcel.setDesignation(sgdataParcels.getDesignation());
                    return parentParcel;
                }).collect(Collectors.toList());
    }

    public ReservationDetails getReservationDetailsForConsolidationErf(final Collection<ParentParcel> parentParcels,
                                                                       final String mdbCode) {
        Optional<ParentParcel> first = parentParcels.stream().findFirst();
        if (first.isPresent()) {
            ReservationDetails reservationDetails = new ReservationDetails();
            ParentParcel parentParcel = first.get();
            String portion = parentParcel.getPortion();
            String allocatedParcel = MAX_PARCEL + 1 + "";
            String lpi = mdbCode + allocatedParcel + "00000";
            reservationDetails.setLpi(lpi);
            String designation = "Erf " + allocatedParcel + " " + parentParcel.getRegionTownship();
            reservationDetails.setDesignation(designation);
            reservationDetails.setParcel(allocatedParcel);
            reservationDetails.setPortion(0);
            return reservationDetails;
        }

        return null;
    }

    public ReservationDetails getReservationDetailsForConSameErfDiffPortion(final Collection<ParentParcel> parentParcels,
                                                                            final String mdbCode) {
        Optional<ParentParcel> first = parentParcels.stream().findFirst();
        if (first.isPresent()) {
            ReservationDetails reservationDetails = new ReservationDetails();
            ParentParcel parentParcel = first.get();
            String portion = parentParcel.getPortion();
            String allocatedParcel = parentParcel.getParcel();
            Integer allocatedPortion = MAX_PORTION + 1;
            String lpi = mdbCode + allocatedParcel + allocatedPortion + "";
            reservationDetails.setLpi(lpi);
            String designation = "Portion  " + allocatedPortion + " of Erf " + allocatedParcel + " " + parentParcel.getRegionTownship();
            reservationDetails.setDesignation(designation);
            reservationDetails.setParcel(allocatedParcel);
            reservationDetails.setPortion(allocatedPortion);
            return reservationDetails;
        }

        return null;
    }

}
