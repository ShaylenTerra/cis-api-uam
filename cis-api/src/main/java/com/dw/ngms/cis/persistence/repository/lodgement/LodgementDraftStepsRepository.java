package com.dw.ngms.cis.persistence.repository.lodgement;

import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraft;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraftStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LodgementDraftStepsRepository extends JpaRepository<LodgementDraftStep, Long> {

    LodgementDraftStep findByStepId(final Long stepId);

    List<LodgementDraftStep> findByLodgementDraft(final LodgementDraft lodgementDraft);

    LodgementDraftStep findByLodgementDraftAndStepNo(final LodgementDraft lodgementDraft, final Long stepNo);

    List<LodgementDraftStep> findAllByLodgementDraftOrderByStepId(final LodgementDraft lodgementDraft);

}
