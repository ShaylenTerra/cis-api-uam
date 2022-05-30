package com.dw.ngms.cis.service;

import com.dw.ngms.cis.enums.FeeSimulatorType;
import com.dw.ngms.cis.persistence.domains.fee.FeeSubCategory;
import com.dw.ngms.cis.persistence.projection.fee.InvoiceItemCostProjection;
import com.dw.ngms.cis.persistence.repository.fee.FeeCategoryRepository;
import com.dw.ngms.cis.persistence.repository.fee.FeeMasterRepository;
import com.dw.ngms.cis.persistence.repository.fee.FeeSubCategoryRepository;
import com.dw.ngms.cis.service.dto.FeeSimulatorDto;
import com.dw.ngms.cis.service.dto.simulator.LodegementCategoryDto;
import com.dw.ngms.cis.service.dto.simulator.LodegementTypeDto;
import com.dw.ngms.cis.web.vm.FeeSimulatorVm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class FeeSimulatorService {

    private final FeeMasterRepository feeMasterRepository;

    private final FeeCategoryRepository feeCategoryRepository;

    private final FeeSubCategoryRepository feeSubCategoryRepository;

    public FeeSimulatorDto calculateFee(final FeeSimulatorVm feeSimulatorVm) {

        FeeSimulatorType type = feeSimulatorVm.getType();

        FeeSimulatorDto feeSimulatorDto = new FeeSimulatorDto();

        double totalCost = 0.0D;

        if (type.equals(FeeSimulatorType.RESERVATION)) {
            // calculate fee for reservation

            InvoiceItemCostProjection itemCost = feeMasterRepository.getItemCost(-100L,
                    feeSimulatorVm.getDeliveryMethodId(),
                    0L,
                    0L,
                    0L);

            if (null != itemCost) {
                    totalCost += itemCost.getFee();
            }

            feeSimulatorDto.setTotalCost(totalCost);
            feeSimulatorDto.setType(feeSimulatorVm.getType());

            return feeSimulatorDto;


        } else if (type.equals(FeeSimulatorType.INFORMATION_REQUEST)) {

            InvoiceItemCostProjection itemCost = feeMasterRepository.getItemCost(feeSimulatorVm.getCategoryId(),
                    feeSimulatorVm.getFormatId(),
                    feeSimulatorVm.getPaperSize(),
                    feeSimulatorVm.getCategoryTypeId(),0L);

            if (null != itemCost) {
                totalCost += (itemCost.getFee()*feeSimulatorVm.getItemCount());
            }

            InvoiceItemCostProjection itemCostDeliveryMethod = feeMasterRepository.getItemCost(-100L,
                    feeSimulatorVm.getDeliveryMethodId(),
                    0L,
                    0L,
                    0L);

            if (null != itemCostDeliveryMethod) {
                totalCost += itemCostDeliveryMethod.getFee();
            }

            InvoiceItemCostProjection itemCostDeliveryMedium = feeMasterRepository.getItemCost(-100L,
                    feeSimulatorVm.getDeliveryMediumId(),
                    0L,
                    0L,
                    0L);

            if (null != itemCostDeliveryMedium) {
                totalCost += itemCostDeliveryMedium.getFee();
            }

            feeSimulatorDto.setTotalCost(totalCost);
            feeSimulatorDto.setType(feeSimulatorVm.getType());

            return feeSimulatorDto;

        } else if (type.equals(FeeSimulatorType.LODGEMENT)) {
            // calculate fee  for lodgement

            InvoiceItemCostProjection itemCost = feeMasterRepository
                    .getLodegementItemCost(feeSimulatorVm.getCategoryTypeId());

            if (null != itemCost) {
                totalCost += itemCost.getFee();
            }

            InvoiceItemCostProjection itemCostDeliveryMethod = feeMasterRepository.getItemCost(-100L,
                    feeSimulatorVm.getDeliveryMethodId(),
                    0L,
                    0L,
                    0L);

            if (null != itemCostDeliveryMethod) {
                totalCost += itemCostDeliveryMethod.getFee();
            }

            feeSimulatorDto.setTotalCost(totalCost);
            feeSimulatorDto.setType(feeSimulatorVm.getType());

            return feeSimulatorDto;


        } else if (type.equals(FeeSimulatorType.NGI)) {
            // calculate fee for ngi

            InvoiceItemCostProjection itemCost = feeMasterRepository.getItemCost(16L,
                    feeSimulatorVm.getFormatId(),
                    feeSimulatorVm.getPaperSize(),
                    feeSimulatorVm.getCategoryTypeId(),
                    0L);

            if (null != itemCost) {
                totalCost += itemCost.getFee();
            }

            InvoiceItemCostProjection itemCostDeliveryMethod = feeMasterRepository.getItemCost(-100L,
                    feeSimulatorVm.getDeliveryMethodId(),
                    0L,
                    0L,
                    0L);

            if (null != itemCostDeliveryMethod) {
                totalCost += itemCostDeliveryMethod.getFee();
            }

            InvoiceItemCostProjection itemCostDeliveryMedium = feeMasterRepository.getItemCost(-100L,
                    feeSimulatorVm.getDeliveryMediumId(),
                    0L,
                    0L,
                    0L);

            if (null != itemCostDeliveryMedium) {
                totalCost += itemCostDeliveryMedium.getFee();
            }

            feeSimulatorDto.setTotalCost(totalCost);
            feeSimulatorDto.setType(feeSimulatorVm.getType());

            return feeSimulatorDto;

        }

        return null;

    }

    /**
     * @return Collection<LodegementTypeDto>
     */
    public Collection<LodegementTypeDto> getLodegementType() {
        return feeCategoryRepository.getAllLodegementType();
    }

    /**
     * @param categoryId categoryId
     * @return Collection<LodegementCategoryDto>
     */
    public Collection<LodegementCategoryDto> getLodegementCategories(final Long categoryId) {
        Collection<FeeSubCategory> byCategoryId = feeSubCategoryRepository.findByCategoryId(categoryId);
        return byCategoryId.stream().map(feeSubCategory -> {
            LodegementCategoryDto lodegementCategoryDto = new LodegementCategoryDto();
            lodegementCategoryDto.setSubCategoryId(feeSubCategory.getFeeSubCategoryId());
            lodegementCategoryDto.setName(feeSubCategory.getName());
            return lodegementCategoryDto;
        }).collect(Collectors.toList());
    }

}
