package com.dw.ngms.cis.persistence.repository.reservation;

import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraft;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author prateek on 31-12-2021
 */
@Repository
public interface ReservationDraftDocumentsRepository extends JpaRepository<ReservationDraftDocument,Long> {

    ReservationDraftDocument findByDocumentId(final Long documentId);

    List<ReservationDraftDocument> findAllByReservationDraftOrderByDocumentId(final ReservationDraft reservationDraft);

}
