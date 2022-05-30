package com.dw.ngms.cis.persistence.repository.reservation;

import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftSteps;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author prateek on 01-02-2022
 */
@Repository
public interface ReservationOutcomeRepository extends JpaRepository<ReservationOutcome,Long> {

    @Query("select max(R.portion) from ReservationOutcome R where R.locationId =:locationId and R.parcel =:parcel")
    Long getMaxPortionForLocationIdAndParcel(final Long locationId, final Long parcel);

    @Query("select max(R.parcel) from ReservationOutcome R where R.locationId =:locationId")
    Long getMaxParcelForLocationId(final Long locationId);

    ReservationOutcome findByOutcomeId(final Long outcomeId);

    List<ReservationOutcome> findByReservationDraftStepsOrderByParcelAscPortionAsc(final ReservationDraftSteps reservationDraftSteps);

    List<ReservationOutcome> findByDesignationContainingIgnoreCase(final String searchTerm);
}
