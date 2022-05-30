package com.dw.ngms.cis.persistence.repository.lodgement;

import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraft;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraftAnnexure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pragayanshu on 25-03-2022
 */
@Repository
public interface LodgementDraftAnnexureRepository extends JpaRepository<LodgementDraftAnnexure,Long> {

    LodgementDraftAnnexure findByAnnexureId(final Long annexureId);

    List<LodgementDraftAnnexure> findAllByLodgementDraftOrderByAnnexureId(final LodgementDraft lodgementDraft);

}
