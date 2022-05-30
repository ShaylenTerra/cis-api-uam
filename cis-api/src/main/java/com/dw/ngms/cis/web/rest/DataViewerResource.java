package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.persistence.projection.dataviewer.CustomQueryResultSetProjection;
import com.dw.ngms.cis.service.DataViewerService;
import com.dw.ngms.cis.service.dto.dataviewer.DataViewerConfigurationDto;
import com.dw.ngms.cis.service.dto.dataviewer.DataViewerLogDto;
import com.dw.ngms.cis.service.dto.dataviewer.DataViewerRequestDto;
import com.dw.ngms.cis.web.annotation.ApiPageable;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.util.PaginationUtil;
import com.dw.ngms.cis.web.vm.dataviewer.DataViewerRequestVm;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 11/03/21, Thu
 **/
@BaseResponse
@AllArgsConstructor
@RestController
@RequestMapping(AppConstants.API_BASE_MAPPING + "/data-viewer")
public class DataViewerResource {

    private final DataViewerService dataViewerService;


    /**
     * @return Collection<String>
     */
    @GetMapping("/getObjectType")
    public ResponseEntity<Collection<String>> getType() {
        return ResponseEntity.ok()
                .body(dataViewerService.getAllDistinctType());
    }

    /**
     * @param objectType objectType
     * @return Collection<DataViewerConfigurationDto> {@link DataViewerConfigurationDto}
     */
    @GetMapping("/getObjectName")
    public ResponseEntity<Collection<DataViewerConfigurationDto>>
    getAllObjectName(@RequestParam @NotNull final String objectType) {
        return ResponseEntity.ok()
                .body(dataViewerService.getAllObjectNames(objectType));
    }

    /**
     * @param tableName tableName
     * @return Collection<String>
     */
    @GetMapping("/getObjectColumn")
    public ResponseEntity<Collection<String>> getAllColumnNameForObject(@RequestParam @NotNull final String tableName) {
        return ResponseEntity.ok()
                .body(dataViewerService.getAllColumnForTable(tableName));
    }

    /**
     * @param dataViewerRequestVm {@link DataViewerRequestVm}
     * @return {@link CustomQueryResultSetProjection}
     */
    @PostMapping("/execute/query")
    public ResponseEntity<CustomQueryResultSetProjection> executeCustomQuery(
            @RequestBody @Valid final DataViewerRequestVm dataViewerRequestVm) {
        return ResponseEntity.ok()
                .body(dataViewerService.executeCustomQuery(dataViewerRequestVm));
    }

    /**
     * @param dataViewerLogDto {@link DataViewerLogDto}
     * @return {@link DataViewerLogDto}
     */
    @PostMapping("/dataViewerRequest")
    public ResponseEntity<DataViewerRequestDto> dataViewerDataRequest(
            @RequestBody @Valid final DataViewerLogDto dataViewerLogDto) {
        return ResponseEntity.ok()
                .body(dataViewerService.dataViewerDataRequest(dataViewerLogDto));
    }

    /**
     * @param userId   userId
     * @param pageable {@link Pageable}
     * @return Collection<DataViewerRequestDto>
     */
    @GetMapping("/dataViewerRequest")
    @ApiPageable
    public ResponseEntity<Collection<DataViewerRequestDto>>
    getDataViewerRequest(@RequestParam @NotNull final Long userId, @ApiIgnore Pageable pageable) {
        Page<DataViewerRequestDto> dataViewerRequest = dataViewerService.getDataViewerRequest(userId, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(dataViewerRequest);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(dataViewerRequest.getContent());
    }

}
