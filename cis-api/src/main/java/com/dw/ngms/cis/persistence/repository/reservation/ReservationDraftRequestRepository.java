package com.dw.ngms.cis.persistence.repository.reservation;

import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftRequest;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftSteps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author prateek on 04-01-2022
 */
@Repository
public interface ReservationDraftRequestRepository extends JpaRepository<ReservationDraftRequest,Long> {

    List<ReservationDraftRequest> findByReservationDraftSteps(final ReservationDraftSteps reservationDraftSteps);

    ReservationDraftRequest findByDraftRequestId(final Long requestId);

}
