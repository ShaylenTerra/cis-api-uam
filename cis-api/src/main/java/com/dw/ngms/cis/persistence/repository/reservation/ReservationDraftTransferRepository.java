package com.dw.ngms.cis.persistence.repository.reservation;

import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraft;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftTransfer;
import com.dw.ngms.cis.persistence.projection.reservation.ReservationTransferListProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author prateek on 20-03-2022
 */
@Repository
public interface ReservationDraftTransferRepository extends JpaRepository<ReservationDraftTransfer,Long> {

    List<ReservationDraftTransfer> findAllByDraftId(final Long draftId);

    @Query(value = "SELECT\n" +
            "    RO.OUTCOME_ID as outcomeId,\n" +
            "    DESIGNATION as designation,\n" +
            "    LPI as lpi,\n" +
            "    W.WORKFLOWID as workflowId,\n" +
            "    STATUS.CAPTION AS status,\n" +
            "    REASON.CAPTION as reason,\n" +
            "    RO.ISSUE_DATE as issueDate,\n" +
            "    RO.EXPIRY_DATE as expiryDate,\n" +
            "    trunc(RO.EXPIRY_DATE) - trunc(RO.ISSUE_DATE) as expiryInDays,\n" +
            "    W.REFERENCE_NO as referenceNumber,\n" +
            "    RD.NAME as name,\n" +
            "    L.CAPTION as provinceName,\n" +
            "    U.FirstName as firstName,\n" +
            "    U.SurName as surName,\n" +
            "    RDT.TRANSFERID as transferId\n" +
            "FROM    RES_DRAFT  RD   inner join  RES_DRAFT_TRANSFER RDT on RD.DRAFT_ID = RDT.DRAFTID\n" +
            "                        INNER JOIN RESERVATION_OUTCOME RO on  RDT.OUTCOME_ID =   RO.OUTCOME_ID\n" +
            "                        LEFT OUTER JOIN M_LIST_ITEM STATUS ON RO.STATUS_ITEM_ID = STATUS.ITEMID\n" +
            "                        LEFT OUTER JOIN M_LIST_ITEM REASON ON RO.REASON_ITEM_ID = REASON.ITEMID\n" +
            "                        LEFT OUTER JOIN WORKFLOW W ON RO.WORKFLOW_ID = W.WORKFLOWID\n" +
            "                        LEFT OUTER JOIN USERS U ON  RO.OWNER_USERID = U.USERID\n" +
            "                        LEFT OUTER JOIN LOCATION L ON RD.PROVINCE_ID = L.BOUNDARYID\n" +
            "where RD.DRAFT_ID=:draftId"
            ,nativeQuery = true)
    List<ReservationTransferListProjection> loadReservationTransferListing(final Long draftId);

}
