package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.service.FeeService;
import com.dw.ngms.cis.service.dto.fee.*;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 27/11/20, Fri
 **/
@BaseResponse
@RestController
@AllArgsConstructor
@RequestMapping(value = AppConstants.API_BASE_MAPPING + "/fee")
public class FeeResource {

    private final FeeService feeService;

    /**
     * @return collection of feeCategoryDto
     */
    @GetMapping("/category")
    public ResponseEntity<Collection<FeeCategoryDto>> getAllFeeCategory() {
        return ResponseEntity.ok()
                .body(feeService.getAllFeeCategory());
    }

    /**
     * @param feeCategoryDto fee category dto
     * @return fee category dto
     */
    @PostMapping("/category")
    public ResponseEntity<FeeCategoryDto> saveFeeCategory(@RequestBody @Valid final FeeCategoryDto feeCategoryDto) {
        return ResponseEntity.ok()
                .body(feeService.saveFeeCategory(feeCategoryDto));
    }

    /**
     * @return collection of FeeSubCategoryDto
     */
    @GetMapping("/subcategory")
    public ResponseEntity<Collection<FeeSubCategoryDto>> getFeeSubCategoryByCategoryId(@RequestParam @NotNull final Long categoryId) {
        return ResponseEntity.ok()
                .body(feeService.getSubCategoryByCategoryId(categoryId));
    }

    /**
     * @param feeSubCategoryDto fee sub category dto
     * @return FeeSubCategoryDto
     */
    @PostMapping("/subcategory")
    public ResponseEntity<FeeSubCategoryDto> saveFeeSubCategory(@RequestBody @Valid final FeeSubCategoryDto feeSubCategoryDto) {
        return ResponseEntity.ok()
                .body(feeService.saveFeeSubCategory(feeSubCategoryDto));
    }

    /**
     * @return collection of {@link FeeTypeDto}
     */
    @GetMapping("/type")
    public ResponseEntity<Collection<FeeTypeDto>> getAllFeeType() {
        return ResponseEntity.ok()
                .body(feeService.getAllFeeType());
    }

    /**
     * @param feeTypeDto {@link FeeTypeDto}
     * @return FeeTypeDto
     */
    @PostMapping("/type")
    public ResponseEntity<FeeTypeDto> saveFeeType(@RequestBody @Valid final FeeTypeDto feeTypeDto) {
        return ResponseEntity.ok()
                .body(feeService.saveFeeType(feeTypeDto));
    }

    /**
     * @return collection of {@link FeeScaleDto}
     */
    @GetMapping("/scale")
    public ResponseEntity<Collection<FeeScaleDto>> getAllFeeScale() {
        return ResponseEntity.ok()
                .body(feeService.getAllFeeScale());
    }

    /**
     * @param feeScaleDto fee scale dto
     * @return Optional of FeeScaleDto
     */
    @PostMapping(value = "/scale", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FeeScaleDto> saveFeeScale(
            @ModelAttribute @Valid final FeeScaleDto feeScaleDto) {

        return ResponseEntity.ok()
                .body(feeService.saveFeeScale(feeScaleDto));
    }

    @GetMapping(value = "/scale/doc", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getScaleDoc(@RequestParam @NotNull final Long feeScaleId,
                                                @RequestParam @NotNull final String fileName) {
        Resource feeScaleDoc = feeService.getFeeScaleDoc(feeScaleId, fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + feeScaleDoc.getFilename() + "\"")
                .body(feeService.getFeeScaleDoc(feeScaleId, fileName));
    }

    /**
     * @param subCategoryId subCategoryId
     * @param feeScaleId    feeScaleId
     * @return FeeMasterDto {@link FeeMasterDto}
     */
    @GetMapping("/master")
    public ResponseEntity<FeeMasterDto> getFeeMasterUsingName(@RequestParam @NotNull final Long subCategoryId,
                                                              @RequestParam @NotNull final Long feeScaleId) {
        return ResponseEntity.ok()
                .body(feeService.getFeeMasterBySubcategoryAndScale(subCategoryId, feeScaleId));
    }


    /**
     * @param feeMasterDto fee master dto
     * @return FeeMasterDto
     */
    @PostMapping("/master")
    public ResponseEntity<FeeMasterDto> saveFeeMaster(@RequestBody @Valid final FeeMasterDto feeMasterDto) {
        return ResponseEntity.ok()
                .body(feeService.saveFeeMaster(feeMasterDto));
    }

}
