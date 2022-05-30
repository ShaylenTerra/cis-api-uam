package com.dw.ngms.cis.persistence.repository.lodgement;

import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraftRequest;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationOutcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author prateek on 04-04-2022
 */
@Repository
public interface LodgementDraftRequestRepository
        extends JpaRepository<LodgementDraftRequest,Long> {


    LodgementDraftRequest findByRequestId(final Long requestId);

}
