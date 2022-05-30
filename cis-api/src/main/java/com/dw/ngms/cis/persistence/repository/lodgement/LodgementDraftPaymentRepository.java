package com.dw.ngms.cis.persistence.repository.lodgement;

import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraft;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraftPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : pragayanshu
 * @since : 2022/03/25, Fri
 **/
@Repository
public interface LodgementDraftPaymentRepository
        extends JpaRepository<LodgementDraftPayment,Long> {

    LodgementDraftPayment findByPayId(final Long payId);

    List<LodgementDraftPayment> findAllByLodgementDraftOrderByPayId(final LodgementDraft lodgementDraft);

}
