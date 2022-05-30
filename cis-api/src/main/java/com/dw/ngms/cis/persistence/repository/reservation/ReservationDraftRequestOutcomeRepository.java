package com.dw.ngms.cis.persistence.repository.reservation;

import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftRequestOutcome;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftSteps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author prateek on 04-01-2022
 */
@Repository
public interface ReservationDraftRequestOutcomeRepository extends JpaRepository<ReservationDraftRequestOutcome,Long> {

    List<ReservationDraftRequestOutcome> findByReservationDraftSteps(final ReservationDraftSteps reservationDraftSteps);

}
