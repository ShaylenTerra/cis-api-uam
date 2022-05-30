package com.dw.ngms.cis.service.lodgment;

import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraftStep;
import com.dw.ngms.cis.persistence.repository.lodgement.LodgementDraftStepsRepository;
import com.dw.ngms.cis.service.dto.lodgment.LodgementDraftStepsDto;
import com.dw.ngms.cis.service.mapper.lodgment.LodgementDraftStepsMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class LodgementDraftStepsService {

    private final LodgementDraftStepsRepository lodgementDraftStepsRepository;

    private final LodgementDraftStepsMapper lodgementDraftStepsMapper;

    public LodgementDraftStepsDto saveLodgementDraftSteps
            (final LodgementDraftStepsDto reservationDraftStepsDto) {
        LodgementDraftStep lodgementDraftStep = lodgementDraftStepsMapper
                .lodgmentDraftStepsDtoToLodgmentDraftSteps(reservationDraftStepsDto);

        LodgementDraftStep save = lodgementDraftStepsRepository.save(lodgementDraftStep);
        return lodgementDraftStepsMapper.lodgmentDraftStepsToLodgmentDraftStepsDto(save);
    }
}
