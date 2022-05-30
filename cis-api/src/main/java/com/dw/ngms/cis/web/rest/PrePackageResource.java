package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.persistence.projection.prepackage.*;
import com.dw.ngms.cis.service.dto.prepackage.PrePackageConfigurationDto;
import com.dw.ngms.cis.service.dto.prepackage.PrePackageExecutionDto;
import com.dw.ngms.cis.service.dto.prepackage.PrepackageSubscriptionDto;
import com.dw.ngms.cis.service.prepackage.PrePackageService;
import com.dw.ngms.cis.web.annotation.ApiPageable;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.response.UpdateResponse;
import com.dw.ngms.cis.web.util.PaginationUtil;
import com.dw.ngms.cis.web.vm.PrepackageSubscriptionNotificationVm;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 04/01/21, Mon
 **/
@BaseResponse
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.API_BASE_MAPPING + "/prepackage")
public class PrePackageResource {

    private final PrePackageService prePackageService;

    /**
     * @return Collection<PrePackageConfigurationDto>
     */
    @GetMapping
    @ApiPageable
    public ResponseEntity<Collection<PrepackageConfigurationProjection>> getAllPrePackageConfigs(
            @ApiIgnore final Pageable pageable) {
        Page<PrepackageConfigurationProjection> prePackageConfigs = prePackageService.getPrePackageConfigs(pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(prePackageConfigs);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(prePackageConfigs.getContent());
    }


    /**
     * @return Collection<PrepackageProvince>
     */
    @GetMapping("/province")
    public ResponseEntity<Collection<PrepackageProvince>> getLocation() {
        return ResponseEntity.ok()
                .body(prePackageService.getAllProvinces());
    }

    /**
     * @param provinceId provinceId
     * @return Collection<PrepackageMunicipality>
     */
    @GetMapping("/municipality")
    public ResponseEntity<Collection<PrepackageMunicipality>> getMunicipality(@RequestParam @NotNull final Long provinceId) {
        return ResponseEntity.ok()
                .body(prePackageService.getMunicipality(provinceId));
    }

    /**
     * @param provinceId provinceId
     * @param pageable   {@link Pageable}
     * @return Collection<PrepackageMinorRegion>
     */
    @GetMapping("/minorRegion")
    @ApiPageable
    public ResponseEntity<Collection<PrepackageMinorRegion>>
    getMinorRegion(@RequestParam @NotNull final Long provinceId,
                   @ApiIgnore @PageableDefault(size = 1000)
                   @SortDefault(sort = "minorRegion", direction = Sort.Direction.ASC) final Pageable pageable) {
        return ResponseEntity.ok()
                .body(prePackageService.getMinorRegion(provinceId, pageable));
    }

    /**
     * @param provinceId provinceId
     * @param pageable   {@link Pageable}
     * @return Collection<PrepackageMajorRegionOrAdminDistrict>
     */
    @GetMapping("/majorRegionOrAdminDistrict")
    @ApiPageable
    public ResponseEntity<Collection<PrepackageMajorRegionOrAdminDistrict>>
    getMajorRegionOrAdminDistrict(@RequestParam final Long provinceId,
                                  @ApiIgnore @PageableDefault(size = 500)
                                  @SortDefault(sort = "majorRegionOrAdminDistrict",
                                          direction = Sort.Direction.ASC) final Pageable pageable) {
        return ResponseEntity.ok()
                .body(prePackageService.getMajorRegionOrAdminDistrict(provinceId, pageable));
    }

    /**
     * @param pageable {@link Pageable}
     * @return Collection<PrepackageConfigurationProjection>
     */
    @GetMapping("/listing")
    @ApiPageable
    public ResponseEntity<Collection<PrepackageConfigurationProjection>>
    getAllPrepackageConfigurations(@ApiIgnore final Pageable pageable) {
        Page<PrepackageConfigurationProjection> prepackageConfiguration = prePackageService
                .getPrepackageConfiguration(pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(prepackageConfiguration);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(prepackageConfiguration.getContent());
    }

    /**
     * @param prePackageConfigurationDto {@link PrePackageConfigurationDto}
     * @return {@link PrePackageConfigurationDto}
     */
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PrePackageConfigurationDto> addPrePackageConfig(
            @ModelAttribute @Valid PrePackageConfigurationDto prePackageConfigurationDto) {

        return ResponseEntity.ok()
                .body(prePackageService.saveConfiguration(prePackageConfigurationDto));
    }

    /**
     * @param prepackageId prepackageId
     * @return Resource
     */
    @GetMapping(value = "/sample/image", produces = {MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_GIF_VALUE,
            MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<Resource> getImageData(@RequestParam final Long prepackageId) {
        return ResponseEntity.ok()
                .body(prePackageService.getPrePackageSampleImage(prepackageId));
    }

    /**
     * @param prepackageSubscriptionDto {@link PrepackageSubscriptionDto}
     * @return {@link PrepackageSubscriptionDto}
     */
    @PostMapping("/subscribe")
    public ResponseEntity<PrepackageSubscriptionDto> subscribeToPrepackage(
            @RequestBody @Valid final PrepackageSubscriptionDto prepackageSubscriptionDto) {

        return ResponseEntity.ok()
                .body(prePackageService.subscribeToPrepackage(prepackageSubscriptionDto));

    }

    /**
     * @param userId   userId
     * @param pageable pageable
     * @return Collection<PrepackageSubscriptionDto>
     */
    @GetMapping("/subscription/list")
    @ApiPageable
    public ResponseEntity<Collection<PrepackageSubscriptionProjection>>
    getAllSubscriptionForUser(@RequestParam @NotNull final Long userId,
                              @ApiIgnore @SortDefault(sort = "subscriptionDate",
                                      direction = Sort.Direction.DESC) final Pageable pageable) {
        Page<PrepackageSubscriptionProjection> allSubscription = prePackageService.getAllSubscription(userId, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(allSubscription);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(allSubscription.getContent());
    }

    /**
     * @param userId             userId
     * @param subscriptionId     subscriptionId
     * @param subscriptionStatus subscriptionStatus
     * @return {@link UpdateResponse}
     */
    @GetMapping("/subscription/status")
    public ResponseEntity<UpdateResponse> updateSubscriptionStatus(@RequestParam @NotNull final Long userId,
                                                                   @RequestParam @NotNull final Long subscriptionId,
                                                                   @RequestParam @NotNull final Integer subscriptionStatus) {
        Boolean aBoolean = prePackageService.updateUserSubscription(userId, subscriptionId, subscriptionStatus);
        UpdateResponse build = UpdateResponse.builder()
                .update(aBoolean)
                .build();
        return ResponseEntity.ok().body(build);
    }

    /**
     * @param prepackageSubscriptionNotificationVm {@link PrepackageSubscriptionNotificationVm}
     */
    @PostMapping("/subscription/notify")
    public void notifyUserForSubscription(
            @RequestBody final PrepackageSubscriptionNotificationVm prepackageSubscriptionNotificationVm) {
        prePackageService.notifyForSubscription(prepackageSubscriptionNotificationVm);
    }

    /**
     * @param subscriptionId subscriptionId
     * @param pageable       {@link Pageable}
     * @return Collection<PrePackageExecutionDto>
     */
    @GetMapping("/subscription/execution/status")
    @ApiPageable
    public ResponseEntity<Collection<PrePackageExecutionDto>> getPrepackageExecutionStatus(
            @RequestParam @NotNull final Long subscriptionId, @ApiIgnore final Pageable pageable) {
        final Page<PrePackageExecutionDto> prepackageExecutionStatus = prePackageService
                .getPrepackageExecutionStatus(subscriptionId, pageable);
        final HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(prepackageExecutionStatus);
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .body(prepackageExecutionStatus.getContent());
    }

    /**
     * @throws IOException IOException
     */
    @GetMapping("/execute/all")
    public void executePrepackageSubscription() throws IOException {
        prePackageService.executeAllSubscriptions();
    }

    /**
     * @throws IOException IOException
     */
    @GetMapping("/execute/change")
    public void executePrepackageSubscriptionChange() throws IOException {
        prePackageService.executeSubscriptionDataChange();
    }


}
