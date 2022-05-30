package com.dw.ngms.cis.persistence.repository.reservation;

import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraft;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftSteps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationDraftStepsRepository extends JpaRepository<ReservationDraftSteps, Long> {

    ReservationDraftSteps findByStepId(final Long stepId);

    List<ReservationDraftSteps> findByReservationDraft(final ReservationDraft reservationDraft);

    ReservationDraftSteps findByReservationDraftAndStepNo(final ReservationDraft reservationDraft, final Long stepNo);

    List<ReservationDraftSteps> findAllByReservationDraftOrderByStepId(final ReservationDraft reservationDraft);

}
