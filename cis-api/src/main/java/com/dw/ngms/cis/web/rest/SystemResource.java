package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.service.SystemService;
import com.dw.ngms.cis.service.dto.SystemConfigurationDto;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 18/05/21, Tue
 **/
@BaseResponse
@RestController
@RequestMapping(AppConstants.API_BASE_MAPPING + "/system-config")
@AllArgsConstructor
public class SystemResource {

    private final SystemService systemService;

    /**
     * @return Collection<SystemConfigurationDto>
     */
    @GetMapping
    public ResponseEntity<Collection<SystemConfigurationDto>> getAllSystemConfiguration() {
        return ResponseEntity.ok()
                .body(systemService.getAllSystemConfiguration());
    }

    /**
     *
     * @param tag tag
     * @return {@link SystemConfigurationDto}
     */
    @GetMapping("/tag")
    public ResponseEntity<SystemConfigurationDto> getSystemConfigurationByTag(@RequestParam @NotNull final String tag) {
        return ResponseEntity.ok()
                .body(systemService.getSystemConfigurationByTag(tag));
    }

    /**
     * @param systemConfigurationId systemConfigurationId
     * @return {@link SystemConfigurationDto}
     */
    @GetMapping("/{systemConfigurationId}")
    public ResponseEntity<SystemConfigurationDto> getSystemCnfiguration(
            @PathVariable @NotNull final Long systemConfigurationId) {
        return ResponseEntity.ok()
                .body(systemService.getSystemConfiguration(systemConfigurationId));
    }

    /**
     * @param systemConfigurationDto {@link SystemConfigurationDto}
     * @return {@link SystemConfigurationDto}
     */
    @PostMapping
    public ResponseEntity<SystemConfigurationDto> saveSystemConfiguration(
            @RequestBody @Valid final SystemConfigurationDto systemConfigurationDto) {
        final SystemConfigurationDto systemConfigurationDto1 = systemService
                .saveSystemConfiguration(systemConfigurationDto);
        return ResponseEntity.ok()
                .body(systemConfigurationDto1);
    }

    /**
     * @param systemConfigurationId systemConfigurationId
     */
    @DeleteMapping("/{systemConfigurationId}")
    public void deleteSystemConfiguration(@PathVariable final Long systemConfigurationId) {
        systemService.deleteSystemConfiguration(systemConfigurationId);
    }
}
