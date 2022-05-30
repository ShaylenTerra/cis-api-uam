package com.dw.ngms.cis.service;

import com.dw.ngms.cis.enums.StorageContext;
import com.dw.ngms.cis.persistence.domains.fee.*;
import com.dw.ngms.cis.persistence.repository.fee.*;
import com.dw.ngms.cis.service.dto.fee.*;
import com.dw.ngms.cis.service.mapper.*;
import com.dw.ngms.cis.utilities.FileUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.util.FileUtil;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * @author : prateekgoel
 * @since : 11/01/21, Mon
 **/
@Service
@Slf4j
@AllArgsConstructor
public class FeeService {

    private final FeeSubCategoryRepository feeSubCategoryRepository;

    private final FeeSubCategoryMapper feeSubCategoryMapper;

    private final FeeTypeMapper feeTypeMapper;

    private final FeeTypeRepository feeTypeRepository;

    private final FeeScaleRepository feeScaleRepository;

    private final FeeScaleMapper feeScaleMapper;

    private final FeeMasterRepository feeMasterRepository;

    private final FeeMasterMapper feeMasterMapper;

    private final FeeCategoryRepository feeCategoryRepository;

    private final FeeCategoryMapper feeCategoryMapper;

    private final FileStorageService fileStorageService;

    /**
     * @param categoryId categoryId
     * @return Collection<FeeSubCategoryDto>
     */
    public Collection<FeeSubCategoryDto> getSubCategoryByCategoryId(final Long categoryId) {
        log.debug(" getting all FeeSubCategory for categoryId {}  ", categoryId);
        return feeSubCategoryRepository.findByCategoryId(categoryId)
                .stream()
                .map(feeSubCategoryMapper::feeSubCategoryToFeeSubCategoryDto)
                .collect(Collectors.toCollection(LinkedList::new));

    }

    /**
     * @param feeSubCategoryDto {@link FeeSubCategoryDto}
     * @return {@link FeeSubCategoryDto}
     */
    public FeeSubCategoryDto saveFeeSubCategory(final FeeSubCategoryDto feeSubCategoryDto) {

        log.debug(" persisting feeSubCategory {} in db ", feeSubCategoryDto);

        FeeSubCategory feeSubCategory = feeSubCategoryRepository
                .save(feeSubCategoryMapper.feeSubCategoryDtoToFeeSubCategory(feeSubCategoryDto));
        return feeSubCategoryMapper.feeSubCategoryToFeeSubCategoryDto(feeSubCategory);

    }

    /**
     * @return collection of FeeTypeDto
     */
    public Collection<FeeTypeDto> getAllFeeType() {
        log.debug(" get all feeType ");
        return feeTypeRepository.findAll()
                .stream()
                .map(feeTypeMapper::feeTypeToFeeTypeDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * @param feeTypeDto {@link FeeTypeDto}
     * @return FeeTypeDto
     */
    public FeeTypeDto saveFeeType(final FeeTypeDto feeTypeDto) {
        log.debug(" saving feeType {} to db  ", feeTypeDto);

        FeeType feeType = feeTypeRepository.save(feeTypeMapper.feeTypeDtoToFeeType(feeTypeDto));
        return feeTypeMapper.feeTypeToFeeTypeDto(feeType);
    }

    /**
     * @return list of feeScaleDto
     */
    public Collection<FeeScaleDto> getAllFeeScale() {
        return feeScaleRepository.findAll()
                .stream()
                .map(feeScaleMapper::feeScaleToFeeScaleDto)
                .collect(Collectors.toList());
    }

    /**
     * @param feeScaleDto {@link FeeScaleDto}
     * @return {@link FeeScaleDto}
     */
    public FeeScaleDto saveFeeScale(final FeeScaleDto feeScaleDto) {

        FeeScale feeScale = feeScaleRepository.save(feeScaleMapper.feeScaleDtoToFeeScale(feeScaleDto));

        String targetLocation = StorageContext.FEE_CONFIGURATION.getStorageContext() +
                FileUtils.PATH_SEPARATOR +
                feeScale.getFeeScaleId();
        log.debug(" saving fee scale at location {}", targetLocation);

        String fileName = fileStorageService.storeFile(feeScaleDto.getMultipartFile(), targetLocation);

        log.debug(" saved fee sclae document with name {}", fileName);

        feeScale.setDocumentName(fileName);

        feeScaleRepository.save(feeScale);

        feeScaleDto.setFileName(fileName);

        FeeScale byFeeScaleId = feeScaleRepository.findByFeeScaleId(feeScale.getFeeScaleId());
        byFeeScaleId.setDocumentName(fileName);
        feeScaleRepository.save(byFeeScaleId);

        return feeScaleMapper.feeScaleToFeeScaleDto(byFeeScaleId);

    }

    public Resource getFeeScaleDoc(final Long feeScaleId, final String fileName) {

        final FeeScale byFeeScaleId = feeScaleRepository.findByFeeScaleId(feeScaleId);
        String location = StorageContext.FEE_CONFIGURATION.getStorageContext() +
                FileUtils.PATH_SEPARATOR +
                byFeeScaleId.getFeeScaleId() +
                FileUtils.EXTENSION_SEPARATOR +
                FileUtils.getFileExtension(byFeeScaleId.getDocumentName());

        log.debug(" loading fee scale from location {}", location);

        return fileStorageService.loadFileAsResource(location);
    }

    /**
     * @param feeMasterDto {@link FeeMasterDto}
     * @return {@link FeeMasterDto}
     */
    public FeeMasterDto saveFeeMaster(final FeeMasterDto feeMasterDto) {

        return feeMasterMapper
                .feeMasterToFeeMasterDto(feeMasterRepository
                        .save(feeMasterMapper.feeMasterDtoToFeeMaster(feeMasterDto)));

    }

    /**
     * @param subCategoryId subCategoryId
     * @param scaleId       scaleId
     * @return {@link FeeMasterDto}
     */
    public FeeMasterDto getFeeMasterBySubcategoryAndScale(final Long subCategoryId, final Long scaleId) {
        FeeMaster byFeeSubCategoryIdAndFeeScaledId = feeMasterRepository
                .findByFeeSubCategoryIdAndFeeScaledId(subCategoryId, scaleId);
        return feeMasterMapper.feeMasterToFeeMasterDto(byFeeSubCategoryIdAndFeeScaledId);
    }

    /**
     * get all category from repository
     *
     * @return collection of FeeCategoryDto
     */
    public Collection<FeeCategoryDto> getAllFeeCategory() {
        return feeCategoryRepository.findAll().stream()
                .map(feeCategoryMapper::feeCategoryToFeeCategoryDto)
                .collect(Collectors.toList());
    }

    /**
     * save feeCategory
     *
     * @param feeCategoryDto fee category dto
     * @return feeCategoryDto {@link com.dw.ngms.cis.persistence.domains.fee.FeeCategory}
     */
    public FeeCategoryDto saveFeeCategory(final FeeCategoryDto feeCategoryDto) {

        FeeCategory feeCategory = feeCategoryRepository
                .save(feeCategoryMapper.feeCategoryDtoToFeeCategory(feeCategoryDto));
        return feeCategoryMapper.feeCategoryToFeeCategoryDto(feeCategory);
    }

}
