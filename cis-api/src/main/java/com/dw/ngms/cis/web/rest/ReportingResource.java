package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.service.dto.report.ReportsDto;
import com.dw.ngms.cis.service.report.ReportingService;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.vm.report.ReportingVm;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 06/02/21, Sat
 **/
@BaseResponse
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.API_BASE_MAPPING + "/report")
public class ReportingResource {

    private final ReportingService reportingService;

    /**
     * @param reportModuleId reportModuleId
     * @return Collection<ReportsDto>
     */
    @GetMapping("/{reportModuleId}")
    public ResponseEntity<Collection<ReportsDto>> getAllRoleBasedReportsByModuleId(
            @PathVariable("reportModuleId") final Long reportModuleId) {
        return ResponseEntity.ok()
                .body(reportingService.getAllReportsBasedOnReportModuleId(reportModuleId));
    }


    /**
     * @param reportingVm {@link ReportingVm}
     * @return FileSystemResource {@link FileSystemResource}
     * @throws IOException ioException {@link IOException}
     */
    @PostMapping(produces = {MediaType.APPLICATION_PDF_VALUE,"text/csv"})
    public ResponseEntity<Resource> getUserSummaryReport(
            @RequestBody @Valid final ReportingVm reportingVm) throws IOException {
        Resource userSummaryReport = reportingService.getReport(reportingVm);
        HttpHeaders httpHeaders = new HttpHeaders();
        if("PDF".equalsIgnoreCase(reportingVm.getReportFormat())) {
            httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        }
        httpHeaders.setContentType(new MediaType("text","csv"));
        httpHeaders.setContentLength(userSummaryReport.contentLength());
        httpHeaders.setContentDispositionFormData("attachment", userSummaryReport.getFilename());
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(userSummaryReport);
    }

}
