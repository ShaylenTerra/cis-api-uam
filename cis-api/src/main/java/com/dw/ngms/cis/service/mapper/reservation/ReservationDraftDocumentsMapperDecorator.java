package com.dw.ngms.cis.service.mapper.reservation;

import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraft;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftDocument;
import com.dw.ngms.cis.persistence.repository.reservation.ReservationDraftRepository;
import com.dw.ngms.cis.service.dto.reservation.ReservationDraftDocumentsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

/**
 * @author prateek on 31-12-2021
 */
public abstract class ReservationDraftDocumentsMapperDecorator implements ReservationDraftDocumentsMapper {

    @Autowired
    @Qualifier("delegate")
    private ReservationDraftDocumentsMapper delegate;

    @Autowired
    private ReservationDraftRepository reservationDraftRepository;

    @Override
    public ReservationDraftDocument
    reservationDraftDocumentDtoToReservationDraftDocument(ReservationDraftDocumentsDto reservationDraftDocumentsDto) {
        ReservationDraftDocument reservationDraftDocument = delegate
                .reservationDraftDocumentDtoToReservationDraftDocument(reservationDraftDocumentsDto);
        Long draftId = reservationDraftDocumentsDto.getDraftId();
        Optional<ReservationDraft> byId = reservationDraftRepository.findById(draftId);
        if (byId.isPresent()) {
            ReservationDraft reservationDraft = byId.get();
            reservationDraftDocument.setReservationDraft(reservationDraft);
        }

        return reservationDraftDocument;
    }

    @Override
    public ReservationDraftDocumentsDto
    reservationDraftDocumentToReservationDraftDocumentDto(ReservationDraftDocument reservationDraftDocument) {
        ReservationDraftDocumentsDto reservationDraftDocumentsDto = delegate
                .reservationDraftDocumentToReservationDraftDocumentDto(reservationDraftDocument);

        ReservationDraft reservationDraft = reservationDraftDocument.getReservationDraft();
        if (null != reservationDraft) {
            reservationDraftDocumentsDto.setDraftId(reservationDraft.getDraftId());
        }
        return reservationDraftDocumentsDto;
    }
}
